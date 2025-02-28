package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

//metodos



@RestController // ela vai dizer para o string que essa controller é de uma api
@CrossOrigin(allowedHeaders = "*", origins = "*") //e de onde vou aceitar conexoes, do keito que está eu aceito de qualquer lugar
@RequestMapping ("/postagens")//endereco da controller 
public class PostagemController {
	
	@Autowired //ela cria a instancia, roda oque precisa e fecha sozinha, quando eu precisar.
	//
	private PostagemRepository postagemRepository;
	
	
	@GetMapping
	public ResponseEntity<List <Postagem>> getAll(){
		       //ResponseEntity responsavel por criar o json //vai usar a interface // e vai pegar todas
		return ResponseEntity.ok(postagemRepository.findAll());
		//quando eu fizer uma requisiçaõ de get para o /postagem, eu quero retornar do meu postagemRepository o find all
	}

	
	@GetMapping ("/{id}")
	                                         // identifica uma variavel nesse cado é do tipo Long que se chama id
	public ResponseEntity <Postagem> getById(@PathVariable Long id){
		//ele vai no banco de dados e procura a postagem pela id, se encontrar ele armazena na resposta e retorna 
		//se não encontrar ele retorna uma mensagem de erra (404) e fecha a ação.
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta) )
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		
	}
	
	
}

