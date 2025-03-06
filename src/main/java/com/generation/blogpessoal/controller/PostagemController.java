package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;

import jakarta.validation.Valid;

//metodos

@RestController // ela vai dizer para o string que essa controller é de uma api
@CrossOrigin(allowedHeaders = "*", origins = "*") // e de onde vou aceitar conexoes, do keito que está eu aceito de
													// qualquer lugar
@RequestMapping("/postagens") // endereco da controller
public class PostagemController {

	@Autowired // ela cria a instancia, roda oque precisa e fecha sozinha, quando eu precisar.
	//
	private PostagemRepository postagemRepository;

	@Autowired
	private TemaRepository temaRepository;

//SELECT
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll() {
		// ResponseEntity responsavel por criar o json //vai usar a interface // e vai
		// pegar todas
		return ResponseEntity.ok(postagemRepository.findAll());
		// quando eu fizer uma requisiçaõ de get para o /postagem, eu quero retornar do
		// meu postagemRepository o find all
	}

	@GetMapping("/{id}")
	// identifica uma variavel nesse cado é do tipo Long que se chama id
	public ResponseEntity<Postagem> getById(@PathVariable Long id) {
		// ele vai no banco de dados e procura a postagem pela id, se encontrar ele
		// armazena na resposta e retorna
		// se não encontrar ele retorna uma mensagem de erra (404) e fecha a ação.
		return postagemRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

	}

	// retorno de select
	// nome + variavel(nome do titulo)
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}

	// INSERT
//verbo post é usado para fazer o insert no DB
	
//post nome da variavel
	// Valid = checa as validações na model
	// requestBody =
	
	
	@PostMapping
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem) {
		if (postagemRepository.existsById(postagem.getId())) {
			
		
		if (temaRepository.existsById(postagem.getTema().getId()))
			return ResponseEntity.status(HttpStatus.OK)
					.body(postagemRepository.save(postagem));
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não exixte!", null);
	}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	

	// UPDATE
	@PutMapping
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem) {
		if (temaRepository.existsById(postagem.getTema().getId()))
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(postagemRepository.save(postagem));
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não exixte!", null);
		
		// return postagemRepository.findById(postagem.getId())
		/// .map mapeia a postagem que foi localizado passando o Id//
		// .map(resposta ->
		/// ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem)))
		// .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	// DELETE
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {

//TRATANDO E PREVININDO ERROS NO RETNORNO DA POSTAGEM QUE FOI PESQUISADA
		Optional<Postagem> postagem = postagemRepository.findById(id);
		if (postagem.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		postagemRepository.deleteById(id);

	}
}
