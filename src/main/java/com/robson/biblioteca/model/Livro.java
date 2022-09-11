package com.robson.biblioteca.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.robson.biblioteca.enums.GeneroEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_livro")
public class Livro implements Serializable{

	private static final long serialVersionUID = 1L;


	@Id
	private Integer isbn;

	private String titulo;
	private String descricao;
	@Enumerated(EnumType.STRING)
	private GeneroEnum genero;
	
	@ManyToOne
	@JoinColumn(name = "autor_id")
	private Autor autor;

}
