package com.robson.biblioteca.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.robson.biblioteca.model.Endereco;
import com.robson.biblioteca.model.Livro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteDTO extends RepresentationModel<ClienteDTO> implements Serializable{

	private static final long serialVersionUID = 1L;

	//@Mapping("id")
	private Integer idPessoa;
	
	private String nome;
	private Integer idade;
	private String cpf;
	private Endereco endereco;
	private List<Livro> livros = new ArrayList<>();
	
}
