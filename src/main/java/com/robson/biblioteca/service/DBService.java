package com.robson.biblioteca.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robson.biblioteca.enums.GeneroEnum;
import com.robson.biblioteca.model.Autor;
import com.robson.biblioteca.model.Cliente;
import com.robson.biblioteca.model.Endereco;
import com.robson.biblioteca.model.Livro;
import com.robson.biblioteca.repository.LivroRepository;
import com.robson.biblioteca.repository.PessoaRepository;

@Service
public class DBService {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private LivroRepository livroRepository;
	
	public void instanciaDB() {
		Endereco endereco = new Endereco("123", "Rua top", "casa", "Cajueiro", "Jaboatao", "PE", "teste", "teste", "teste", "teste");
		Autor autor = new Autor(null, "Robson", 19, "123", endereco);
		
		Livro livro = new Livro(123, "O sonho", "Livro bom", GeneroEnum.HISTORIA, 50, 50.00, autor);
		
		List<Livro> livros = new ArrayList<>();
		livros.add(livro);
		
		Cliente cliente = new Cliente(null, "Ray", 19, "455", endereco, livros);
		
		pessoaRepository.saveAll(Arrays.asList(autor));
		livroRepository.saveAll(Arrays.asList(livro));
		pessoaRepository.saveAll(Arrays.asList(cliente));
		
	}
}
	
	


