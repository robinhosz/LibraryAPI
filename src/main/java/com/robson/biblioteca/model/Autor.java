package com.robson.biblioteca.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_autor")
public class Autor extends Pessoa {

	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@OneToMany(mappedBy = "autor")
	private List<Livro> livros = new ArrayList<>();

	public Autor(Integer idPessoa, String nome, Integer idade, String cpf, Endereco endereco, List<Livro> livros) {
		super(idPessoa, nome, idade, cpf, endereco);
		this.livros = livros;
	}
	
	public Autor(Integer idPessoa, String nome, Integer idade, String cpf, Endereco endereco) {
		super(idPessoa, nome, idade, cpf, endereco);
	}
	
	
}
