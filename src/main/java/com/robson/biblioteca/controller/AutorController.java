package com.robson.biblioteca.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.robson.biblioteca.dto.AutorDTO;
import com.robson.biblioteca.service.AutorService;
import com.robson.biblioteca.utils.MediaType;

@RestController
@RequestMapping(value = "/api/autores/v1")
@CrossOrigin("*")
public class AutorController {

	@Autowired
	private AutorService service;
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	public ResponseEntity<List<AutorDTO>> findAll() {
		List<AutorDTO> listDTO = service.findAll().stream().map(x -> mapper.map(x, AutorDTO.class)).collect(Collectors.toList());
		listDTO.stream().forEach(l -> l.add(linkTo(methodOn(AutorController.class).findById(l.getIdPessoa())).withSelfRel()));
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	public ResponseEntity<AutorDTO> findById(@PathVariable Integer id){
		AutorDTO obj = mapper.map(service.findById(id), AutorDTO.class);
		obj.add(linkTo(methodOn(AutorController.class).findById(id)).withSelfRel());
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	public ResponseEntity<AutorDTO> create(@RequestBody AutorDTO obj) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(service.save(obj).getIdPessoa()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<AutorDTO> update(@PathVariable Integer id, @RequestBody AutorDTO obj){
		obj.setIdPessoa(id);
		return ResponseEntity.ok().body(mapper.map(service.update(obj), AutorDTO.class));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<AutorDTO> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@GetMapping(value = "/findcep/{cep}")
	public ResponseEntity<Object> findAddresByCep(@PathVariable String cep) {
			return ResponseEntity.status(HttpStatus.CREATED).body(service.findAddressByCep(cep));
	}
	
	
}
