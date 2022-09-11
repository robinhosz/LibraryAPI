package com.robson.biblioteca.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.robson.biblioteca.dto.LivroDTO;
import com.robson.biblioteca.model.Livro;
import com.robson.biblioteca.service.LivroService;
import com.robson.biblioteca.utils.MediaType;

@RestController
@RequestMapping(value = "api/livros/v1")
@CrossOrigin("*")
public class LivroController {

	@Autowired
	private LivroService service;
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	public ResponseEntity<List<LivroDTO>> findAll() {
		List<Livro> list = service.findAll();
		List<LivroDTO> listDTO = list.stream().map(obj -> new LivroDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	public ResponseEntity<LivroDTO> create(@RequestBody LivroDTO obj) {
		Livro newObj = service.save(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getIsbn()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
