package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.blogpessoal.model.Tema;

public interface TemaRepository extends JpaRepository<Tema, Long>{

	// é igual a SELECT * FROM tb_temass WHERE descricao LIKE "%descricao%";
	public List<Tema> findAllByDescricaoContainingIgnoreCase(@Param("descricao")String descricao);//Método de Consulta para buscar temas pela descrição 
}
