package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.blogpessoal.model.Postagem;



public interface PostagemRepository extends JpaRepository<Postagem, Long>{

//05/03
//matodo especifico para buscar pelo titulo 
	
                              //esse titulo é da coluna do DB  //parametro (titulo)
public List <Postagem> findAllByTituloContainingIgnoreCase    (@Param("titulo")String titulo);
	
}



