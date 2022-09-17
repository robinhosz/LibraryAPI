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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/autores/v1")
@CrossOrigin("*")
@Tag(name = "Autor", description = "Endpoints for managing autors")
public class AutorController {

	@Autowired
	private AutorService service;
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Finds all Autors", description = "Finds all Autors", tags = {"Autor"}, 
	responses = { 
			@ApiResponse(description = "Success", responseCode = "200", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AutorDTO.class)))}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content), 
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	}  )
	public ResponseEntity<List<AutorDTO>> findAll() {
		List<AutorDTO> listDTO = service.findAll().stream().map(x -> mapper.map(x, AutorDTO.class)).collect(Collectors.toList());
		listDTO.stream().forEach(l -> l.add(linkTo(methodOn(AutorController.class).findById(l.getIdPessoa())).withSelfRel()));
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Finds a Autor", description = "Finds a Autor", tags = {"Autor"}, 
	responses = { 
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = AutorDTO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content), 
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content), 
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	}  )
	public ResponseEntity<AutorDTO> findById(@PathVariable Integer id){
		AutorDTO obj = mapper.map(service.findById(id), AutorDTO.class);
		obj.add(linkTo(methodOn(AutorController.class).findById(id)).withSelfRel());
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Adds a new Autor", description = "Adds a new Autor", tags = {"Autor"}, 
	responses = { 
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = AutorDTO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content), 
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	}  )
	public ResponseEntity<AutorDTO> create(@RequestBody AutorDTO obj) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(service.save(obj).getIdPessoa()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	@Operation(summary = "Updates a Autor", description = "Updates a Autor", tags = {"Autor"}, 
	responses = { 
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = AutorDTO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content), 
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	}  )
	public ResponseEntity<AutorDTO> update(@PathVariable Integer id, @RequestBody AutorDTO obj){
		obj.setIdPessoa(id);
		return ResponseEntity.ok().body(mapper.map(service.update(obj), AutorDTO.class));
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deletes a Autor", description = "Deletes a Autor", tags = {"Autor"}, 
	responses = { 
			@ApiResponse(description = "No content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content), 
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content), 
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	}  )
	public ResponseEntity<AutorDTO> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
		
	}
	
}
