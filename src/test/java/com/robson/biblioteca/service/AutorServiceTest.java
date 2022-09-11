package com.robson.biblioteca.service;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.robson.biblioteca.dto.AutorDTO;
import com.robson.biblioteca.model.Autor;
import com.robson.biblioteca.model.Endereco;
import com.robson.biblioteca.model.Livro;
import com.robson.biblioteca.repository.AutorRepository;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class AutorServiceTest {

	public static final String NOME = "Jose";
	public static final Endereco ENDERECO = new Endereco();
	public static final String CPF = "11155";
	public static final Integer IDADE = 19;
	public static final List<Livro> LIVROS = new ArrayList<>();
	public static final Integer IDPESSOA = 1;
	public static final int INDEX = 0;
	public static final String EMAIL_JA_CADASTRADO_NO_SISTEMA = "Email já cadastrado no sistema";
	public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";

	@InjectMocks
	private AutorService service;

	@Mock
	private AutorRepository repository;

	@Mock
	private ModelMapper mapper;

	private Autor autor;

	private AutorDTO autorDTO;

	private Optional<Autor> optionalAutor;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		startAutor();
	}

	@Test
	void testFindById() {
		   when(repository.findById(anyInt())).thenReturn(optionalAutor);

	        Autor response = service.findById(IDPESSOA);

	        assertNotNull(response);
	        assertEquals(Autor.class, response.getClass());
	        assertEquals(IDPESSOA, response.getIdPessoa());
	        assertEquals(NOME, response.getNome());
	        assertEquals(IDADE, response.getIdade());
	        assertEquals(CPF, response.getCpf());
	        assertEquals(ENDERECO, response.getEndereco());
	        assertEquals(LIVROS, response.getLivros());
	}

	private void startAutor() {
		autor = new Autor(IDPESSOA, NOME, IDADE, CPF, ENDERECO, LIVROS);
		autorDTO = new AutorDTO(IDPESSOA, NOME, IDADE, CPF, ENDERECO);
		optionalAutor = Optional.of(new Autor(IDPESSOA, NOME, IDADE, CPF, ENDERECO, LIVROS));
	}

}
