package com.abhijith.reactive.springreactive;

import java.util.HashMap;
import java.util.Map;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UserRepositorySample implements UserRepository {
	
	
	private Map<Integer, User> users = new HashMap<Integer, User>();
	
	public UserRepositorySample() {
		users.put(1, new User(1, "Abhijith"));
		users.put(2, new User(2, "Alaka"));
		users.put(3, new User(3, "Jith"));
		
	}

	@Override
	public Flux<User> getAllUsers() {
		// TODO Auto-generated method stub
		return Flux.fromIterable(this.users.values());
	}

	@Override
	public Mono<User> getUser(Integer id) {
		// TODO Auto-generated method stub
		return Mono.justOrEmpty(this.users.get(id));
	}

	@Override
	public Mono<Void> saveUser(Mono<User> userMono) {
		// TODO Auto-generated method stub
		return userMono.doOnNext(user -> {
			users.put(user.getUserid(), user);
			System.out.format("Saved %s with id %d%n", user, user.getUserid());
		}).thenEmpty(Mono.empty());
	}

	@Override
	public Mono<Void> updateUser(Mono<User> userMono) {
		// TODO Auto-generated method stub
		return userMono.doOnNext(user -> {
			users.put(user.getUserid(), user);
			System.out.format("Saved %s with id %d%n", user, user.getUserid());
		}).thenEmpty(Mono.empty());
	}

	@Override
	public Mono<Void> deleteuser(Integer id) {
		// TODO Auto-generated method stub
		users.remove(id);
		System.out.println("users "+users);
		
		return Mono.empty();
	}
	
	
	

}
