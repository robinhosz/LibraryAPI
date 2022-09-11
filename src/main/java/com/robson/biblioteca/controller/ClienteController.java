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

import com.robson.biblioteca.dto.ClienteDTO;
import com.robson.biblioteca.service.ClienteService;
import com.robson.biblioteca.utils.MediaType;

@RestController
@RequestMapping(value = "api/clientes/v1")
@CrossOrigin("*")
public class ClienteController {

	@Autowired
	private ClienteService service;
	@Autowired
	private ModelMapper mapper;

	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<ClienteDTO> listDTO = service.findAll().stream().map(x -> mapper.map(x, ClienteDTO.class))
				.collect(Collectors.toList());
		listDTO.stream()
				.forEach(l -> l.add(linkTo(methodOn(ClienteController.class).findById(l.getIdPessoa())).withSelfRel()));
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML })
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
		ClienteDTO obj = mapper.map(service.findById(id), ClienteDTO.class);
		obj.add(linkTo(methodOn(ClienteController.class).findById(id)).withSelfRel());
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YML })
	public ResponseEntity<ClienteDTO> create(@RequestBody ClienteDTO obj) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(service.save(obj).getIdPessoa()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @RequestBody ClienteDTO obj) {
		obj.setIdPessoa(id);
		return ResponseEntity.ok().body(mapper.map(service.update(obj), ClienteDTO.class));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

	@GetMapping(value = "/findcep/{cep}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML })
	public ResponseEntity<Object> findAddresByCep(@PathVariable String cep) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.findAddressByCep(cep));
	}
}
