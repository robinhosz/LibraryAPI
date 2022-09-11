package com.robson.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robson.biblioteca.dto.LivroDTO;
import com.robson.biblioteca.exception.DataIntegrityViolationException;
import com.robson.biblioteca.model.Autor;
import com.robson.biblioteca.model.Livro;
import com.robson.biblioteca.repository.LivroRepository;

@Service
public class LivroService {

	@Autowired
	private LivroRepository repository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private AutorService autorService;

	@Autowired
	private ModelMapper mapper;
	
	public List<Livro> findAll() {

		return repository.findAll();
	}

	public Livro save(LivroDTO obj) {
		findByIsbn(obj);
		return repository.save(newLivro(obj));
	}

	public void findByIsbn(LivroDTO obj) {
		Optional<Livro> livro = repository.findByIsbn(obj.getIsbn());
		if (livro.isPresent() && !livro.get().getIsbn().equals(obj.getIsbn())) {
			throw new DataIntegrityViolationException(
					"Livro com a ISBN: " + obj.getIsbn() + " Já cadastrado no sistema!");
		}
	}
	
	private Livro newLivro(LivroDTO obj) {
		Autor autor = autorService.findById(obj.getAutor());
		
		Livro livro = new Livro();
		if(obj.getIsbn() != null) {
			livro.setIsbn(obj.getIsbn());
		}
		
		
		livro.setAutor(autor);
		livro.setGenero(obj.getGenero());
		livro.setDescricao(obj.getDescricao());
		livro.setTitulo(obj.getTitulo());
		
		return livro;
	}

}
