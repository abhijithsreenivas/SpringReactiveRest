package com.abhijith.reactive.springreactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

public class UserHandler {
	
	private final UserRepository userRepository;
	
	public UserHandler (UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public Mono<ServerResponse> getAllUsers(ServerRequest request){
	    Flux<User> users = this.userRepository.getAllUsers();
	    return ServerResponse.ok().contentType(APPLICATION_JSON).body(users, User.class); 
	  }
	
	public Mono<ServerResponse> getUser(ServerRequest request){
		int userId = Integer.valueOf(request.pathVariable("id"));
		
		Mono<ServerResponse> notFound = ServerResponse.notFound().build();
		Mono<User> userMono = this.userRepository.getUser(userId);
		
		return userMono.flatMap(user -> ServerResponse.ok().contentType(APPLICATION_JSON).body(userMono,User.class)).switchIfEmpty(notFound);
	}
	
	
	public Mono<ServerResponse> createUser(ServerRequest request){
		Mono<User> user = request.bodyToMono(User.class);
		return ServerResponse.ok().build(this.userRepository.saveUser(user));
	}
	
	public Mono<ServerResponse> updateUser(ServerRequest request){
		Mono<User> user = request.bodyToMono(User.class);
		return ServerResponse.ok().build(this.userRepository.updateUser(user));
	}
	
	
	public Mono<ServerResponse> deleteUser(ServerRequest request){
		int userId = Integer.valueOf(request.pathVariable("id"));
		return ServerResponse.ok().build(this.userRepository.deleteuser(userId));
		
		
	}
	
	
	
	
	

}
