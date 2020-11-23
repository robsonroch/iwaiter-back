package br.com.robson.waiterback.services.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.robson.waiterback.entities.User;
import br.com.robson.waiterback.exception.ErroAutenticacao;
import br.com.robson.waiterback.exception.RegraNegocioException;
import br.com.robson.waiterback.repositories.UserRepository;
import br.com.robson.waiterback.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository repository;
	
	public UserServiceImpl(UserRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public User autenticar(String email, String senha) {
		Optional<User> User = repository.findByEmail(email);
		
		if(!User.isPresent()) {
			throw new ErroAutenticacao("Usuário não encontrado para o email informado.");
		}
		
		if(!User.get().getSenha().equals(senha)) {
			throw new ErroAutenticacao("Senha inválida.");
		}

		return User.get();
	}

	@Override
	@Transactional
	public User saveUser(User User) {
		validEmail(User.getEmail());
		return repository.save(User);
	}

	@Override
	public void validEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
		}
	}

	@Override
	public Optional<User> getById(Long id) {
		return repository.findById(id);
	}

}