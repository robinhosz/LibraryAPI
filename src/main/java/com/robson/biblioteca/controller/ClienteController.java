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
import com.robson.biblioteca.dto.ClienteDTO;
import com.robson.biblioteca.service.ClienteService;
import com.robson.biblioteca.utils.MediaType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "api/clientes/v1")
@CrossOrigin("*")
public class ClienteController {

	@Autowired
	private ClienteService service;
	@Autowired
	private ModelMapper mapper;

	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Finds all Clientes", description = "Finds all Clientes", tags = {"Cliente"}, 
	responses = { 
			@ApiResponse(description = "Success", responseCode = "200", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClienteDTO.class)))}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content), 
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	}  )
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<ClienteDTO> listDTO = service.findAll().stream().map(x -> mapper.map(x, ClienteDTO.class))
				.collect(Collectors.toList());
		listDTO.stream()
				.forEach(l -> l.add(linkTo(methodOn(ClienteController.class).findById(l.getIdPessoa())).withSelfRel()));
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML })
	@Operation(summary = "Finds a Cliente", description = "Finds a Cliente", tags = {"Cliente"}, 
	responses = { 
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = ClienteDTO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content), 
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content), 
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	}  )
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
		ClienteDTO obj = mapper.map(service.findById(id), ClienteDTO.class);
		obj.add(linkTo(methodOn(ClienteController.class).findById(id)).withSelfRel());
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YML })
	@Operation(summary = "Adds a new Cliente", description = "Adds a new Cliente", tags = {"Cliente"}, 
	responses = { 
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = ClienteDTO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content), 
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	}  )
	public ResponseEntity<ClienteDTO> create(@RequestBody ClienteDTO obj) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(service.save(obj).getIdPessoa()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	@Operation(summary = "Updates a Cliente", description = "Updates a Cliente", tags = {"Cliente"}, 
	responses = { 
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = ClienteDTO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content), 
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	}  )
	public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @RequestBody ClienteDTO obj) {
		obj.setIdPessoa(id);
		return ResponseEntity.ok().body(mapper.map(service.update(obj), ClienteDTO.class));
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deletes a Cliente", description = "Deletes a Cliente", tags = {"Cliente"}, 
	responses = { 
			@ApiResponse(description = "No content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content), 
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	}  )
	public ResponseEntity<ClienteDTO> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

}
