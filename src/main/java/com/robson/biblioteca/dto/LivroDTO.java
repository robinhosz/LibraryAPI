package com.robson.biblioteca.dto;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.hateoas.RepresentationModel;

import com.robson.biblioteca.enums.GeneroEnum;
import com.robson.biblioteca.model.Livro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LivroDTO extends RepresentationModel<LivroDTO> implements Serializable{

	private static final long serialVersionUID = 1L;


	private Integer isbn;
	private String titulo;
	private String descricao;
	@Enumerated(EnumType.STRING)
	private GeneroEnum genero;
	private Integer quantidade;
	private Double preco;

	private Integer autor;
	
	public LivroDTO(Livro obj) {
		this.isbn = obj.getIsbn();
		this.titulo = obj.getTitulo();
		this.descricao = obj.getDescricao();
		this.genero = obj.getGenero();
		this.quantidade = obj.getQuantidade();
		this.preco = obj.getPreco();
		this.autor = obj.getAutor().getIdPessoa();
	}

}
