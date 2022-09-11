package com.robson.biblioteca.dto;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.robson.biblioteca.model.Endereco;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@JsonPropertyOrder({"id", "nome", "idade", "endereco"})
public class AutorDTO extends RepresentationModel<AutorDTO> implements Serializable{

	private static final long serialVersionUID = 1L;

	//@JsonProperty("id")
	//@Mapping("id")
	private Integer idPessoa;

	private String nome;
	private Integer idade;
	private String cpf;
	private Endereco endereco;
	
	
}
