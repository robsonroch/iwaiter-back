package br.com.robson.waiterback.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.robson.waiterback.entities.User;
import br.com.robson.waiterback.exception.ErroAutenticacao;
import br.com.robson.waiterback.exception.RegraNegocioException;
import br.com.robson.waiterback.repositories.UserRepository;
import br.com.robson.waiterback.services.impl.UserServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

	@SpyBean
	UserServiceImpl service;
	
	@MockBean
	UserRepository repository;
	
	@Test
	public void deveSalvarUmUser() {
		//cenário
		Mockito.doNothing().when(service).validEmail(Mockito.anyString());
		User user = User.builder()
					.nome("nome")
					.email("email@email.com")
					.senha("senha").build();
		 user.setId(1l);
		
		Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
		
		//acao
		User savedUser = service.saveUser(new User());
		
		//verificao
		Assertions.assertThat(savedUser).isNotNull();
		Assertions.assertThat(savedUser.getId()).isEqualTo(1l);
		Assertions.assertThat(savedUser.getNome()).isEqualTo("nome");
		Assertions.assertThat(savedUser.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(savedUser.getSenha()).isEqualTo("senha");
		
	}
	
	@Test
	public void naoDeveSalvarUmUserComEmailJaCadastrado() {
		//cenario
		String email = "email@email.com";
		User user = User.builder().email(email).build();
		Mockito.doThrow(RegraNegocioException.class).when(service).validEmail(email);
		
		//acao
		org.junit.jupiter.api.Assertions
			.assertThrows(RegraNegocioException.class, () -> service.saveUser(user) ) ;
		
		//verificacao
		Mockito.verify( repository, Mockito.never() ).save(user);
	}
	
	@Test
	public void deveAutenticarUmUserComSucesso() {
		//cenário
		String email = "email@email.com";
		String senha = "senha";
		
		User user = User.builder().email(email).senha(senha).build();
		user.setId(1l);
		Mockito.when( repository.findByEmail(email) ).thenReturn(Optional.of(user));
		
		//acao
		User result = service.autenticar(email, senha);
		
		//verificacao
		Assertions.assertThat(result).isNotNull();
		
	}
	
	@Test
	public void deveLancarErroQUandoNaoEncontrarUserCadastradoComOEmailInformado() {
		
		//cenário
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		//acao
		Throwable exception = Assertions.catchThrowable( () -> service.autenticar("email@email.com", "senha") );
		
		//verificacao
		Assertions.assertThat(exception)
			.isInstanceOf(ErroAutenticacao.class)
			.hasMessage("Usuário não encontrado para o email informado.");
	}
	
	@Test
	public void deveLancarErroQuandoSenhaNaoBater() {
		//cenario
		String senha = "senha";
		User user = User.builder().email("email@email.com").senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
		
		//acao
		Throwable exception = Assertions.catchThrowable( () ->  service.autenticar("email@email.com", "123") );
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha inválida.");
		
	}
	
	@Test
	public void deveValidarEmail() {
		// cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		//acao
		service.validEmail("email@email.com");
	}
	
	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		//cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		//acao
		org.junit.jupiter.api.Assertions
			.assertThrows(RegraNegocioException.class, () -> service.validEmail("email@email.com"));
	}
	
	public static User createUser() {
		 User user = User
				.builder()
				.nome("usuario")
				.email("usuario@email.com")
				.senha("senha")
				.build();
		 user.setId(1l);
		 return user;
	}
}
