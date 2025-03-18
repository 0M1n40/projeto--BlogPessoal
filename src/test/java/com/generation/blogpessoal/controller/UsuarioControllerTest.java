package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private UsuarioRepository usuarioRepository;

	/*
	 * Sempre que eu iniciar os meus testes, quero garantir que o banco de dados
	 * esteja vazio. Ao rodar o teste, preciso de um usuário padrão no meu banco de
	 * dados, com: - nome: Root - usuario: root@root.com - senha: 12345678 - foto:
	 * sem foto
	 */
	@BeforeAll
	void start() {
		usuarioRepository.deleteAll();
		
		usuarioService.cadastrarUsuario(new Usuario(null, "Root", "root@root.com", "12345678", " "));
	}

	/*
	 * Criação de teste -> Usuário deve conseguir se cadastrar com sucesso se: -
	 * nome: Thiago - usuario: thiago@email.com - senha: 123456789 - foto: sem foto
	 * 
	 * Método: POST
	 * 
	 * Ao cadastrar, se tudo der certo, espero um resultado Http: 201 - CREATED para
	 * a requisição
	 */
	@Test
	@DisplayName("Cadastrar um usuario")
	public void deveCriarUmUsuario() {
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(
				new Usuario(null, "João", "joao@email.com", "joao1234", ""));

		ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				corpoRequisicao, Usuario.class);

		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
	}
	
	

	/*
	 * Criação de teste -> Não posso conseguir cadastrar 2 usuários com o mesmo
	 * e-mail caso tente cadastrar 2 usuário no meu banco, com o mesmo e-mail, devo
	 * receber um erro Http: 400 - Bad Request vou usar o mesmo modelo de criação de
	 * usuário acima, para fazer esse modelo, um sendo criado direto no banco de
	 * dados e outro, via HTTP, usando o HttpEntity
	 * 
	 * Método: POST
	 */
	@Test
	@DisplayName("Não deve permitir usuario duplicado")
	public void naoDeveDuplicarUsuario() {

		usuarioService.cadastrarUsuario(new Usuario(null, "Mario Oliveira", "mario_oliv@email.com.br", "13465278", "-"));
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(
				new Usuario(null, "Mario Oliveira", "mario_oliv@email.com.br", "13465278", "-"));
		ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				corpoRequisicao, Usuario.class);
		assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
	}

	/*
	 * Criação de teste -> Preciso conseguir atualizar os dados de um usuário tendo
	 * um usuário válido criado no banco, devo ser capaz de conseguir encontra-lo
	 * pelo numero de ID, e modificar os dados dele conforme necessário ao atualizar
	 * o usuário, devo receber um Http 200 - OK
	 * 
	 * Método: PUT
	 */
	@Test
	@DisplayName("Atualizar usuario")
	public void deveAtualizarUmUsuario() {

		Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(
				new Usuario(null, "Mina monteiro", "Mina@email.com.br", "mina1234", "-"));
		Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(), "Mina Monteiro",
				"mina@email.com.br", "mina1234", "-");
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);
		ResponseEntity<Usuario> corpoResposta = testRestTemplate.withBasicAuth("root@root.com", "12345678")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);
		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());

	}

	/*
	 * Criação de teste -> Devo conseguir puxar uma lista de todos os usuários no
	 * banco de dados, ao solicitar isso com um login de usuário válido Criarei
	 * alguns usuários para ter dados no banco de dados, através do cadastrarUsuario
	 * direto da Service, e depois irei executar a solicitação Método: GET
	 * 
	 * Espero que ao fazer essa solicitação, seja recebido um Http 200 - OK
	 */
	@Test
	@DisplayName("Listar todos os usuarios")
	public void deveMostrarTodosUsuarios() {

		usuarioService.cadastrarUsuario(
				new Usuario(null, "Margarida ", "margarida@email.com.br", "margarida123", "-"));

		usuarioService.cadastrarUsuario(
				new Usuario(null, "Solange", "solange@email.com.br", "solange123", "-"));

		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root@root.com", "12345678")
				.exchange("/usuarios/all", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());

	}

}