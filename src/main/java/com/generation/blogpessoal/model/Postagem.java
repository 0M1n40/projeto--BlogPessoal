package com.generation.blogpessoal.model;

//crtl+SHIFT+O importa todas as bibliotecas necessarias de uma vez

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity //Entidade, informa que vai ser uma tabela no DB
@Table (name = "tb_postagens") // define o nome da tabela 
public class Postagem {

	@Id //PRIMARY KEY
	@GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO_INCREMENT
	private Long id;
	
	@NotBlank (message = "O atributo titulo é obrigatorio")// NOT NULL 
	@Size(min = 5, max = 100, message = "O titulo tem que ser maior que 5 e menor que 100") // DEFINE O TAMANHO QUE PRECISA SER NO TITULO
	private String titulo;
	
	@NotBlank (message = "O atributo texto é obrigatorio")
	@Size(min = 5, max = 1000, message = "O texto tem que ser maior que 5 e menor que 1000") 
	private String texto;
	
	@UpdateTimestamp // NA HR QUE A POSTAGEM FOR CRIADA, A DATA E HR É GRAVADA NA TABELA DO DB AUTOMATICAMENTE
	private LocalDateTime data;
	
	
	@ManyToOne // significa que a classe postagem sera o lado n1, e tera um obj da classe tema.
	@JsonIgnoreProperties ("postagem") //
	private Tema tema;// adicionando o OBJ tema (id e descricao)
	
	@ManyToOne 
	@JsonIgnoreProperties ("postagem") 
	private Usuario usuario;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	
	
}
