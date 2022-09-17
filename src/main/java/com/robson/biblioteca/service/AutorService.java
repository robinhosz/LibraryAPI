package com.robson.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robson.biblioteca.dto.AutorDTO;
import com.robson.biblioteca.exception.DataIntegrityViolationException;
import com.robson.biblioteca.exception.ObjectNotFoundException;
import com.robson.biblioteca.model.Autor;
import com.robson.biblioteca.model.Endereco;
import com.robson.biblioteca.model.Pessoa;
import com.robson.biblioteca.repository.AutorRepository;
import com.robson.biblioteca.repository.EnderecoRepository;
import com.robson.biblioteca.repository.PessoaRepository;

@Service
public class AutorService {

	@Autowired
	private AutorRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ViaCepService viaCepService;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ModelMapper mapper;

	public List<Autor> findAll() {

		return repository.findAll();
	}

	public Autor findById(Integer id) {
		Optional<Autor> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto com a id: " + id + " não encontrado"));
	}

	public Autor save(AutorDTO obj) {
		findByCpf(obj);
		salvarAutorComCep(obj);
		return repository.save(mapper.map(obj, Autor.class));
	}
	
	public Autor update(AutorDTO obj) {
		if(!repository.existsById(obj.getIdPessoa())) {
	           throw new ObjectNotFoundException("ID: " + obj.getIdPessoa() + " não foi encontrado!");
	        }	
		findByCpf(obj);
		salvarAutorComCep(obj);
		return repository.save(mapper.map(obj, Autor.class));
	}
	
	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);

	}
	
	private void findByCpf(AutorDTO objDto) {
		Optional<Pessoa> pessoa = pessoaRepository.findByCpf(objDto.getCpf());
		
		if (pessoa.isPresent() && !pessoa.get().getIdPessoa().equals(objDto.getIdPessoa())) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema");

		}
	}
	
	private void salvarAutorComCep(AutorDTO autor) {
		// Verificar se o Endereco do Autor já existe (pelo CEP).
		String cep = autor.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			// Caso não exista, integrar com o ViaCEP e persistir o retorno.
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		autor.setEndereco(endereco);
	}

}
