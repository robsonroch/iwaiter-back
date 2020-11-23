package br.com.robson.waiterback.services;

import java.util.Optional;

import br.com.robson.waiterback.entities.User;

public interface UserService {

	User autenticar(String email, String senha);
	
	User saveUser(User usuario);
	
	void validEmail(String email);
	
	Optional<User> getById(Long id);
	
}