package com.robson.biblioteca.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robson.biblioteca.dto.AutorDTO;
import com.robson.biblioteca.exception.DataIntegrityViolationException;
import com.robson.biblioteca.exception.ObjectNotFoundException;
import com.robson.biblioteca.model.Autor;
import com.robson.biblioteca.model.Pessoa;
import com.robson.biblioteca.repository.AutorRepository;
import com.robson.biblioteca.repository.PessoaRepository;

@Service
public class AutorService {

	@Autowired
	private AutorRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ModelMapper mapper;

	public List<Autor> findAll() {

		return repository.findAll();
	}

	public Autor findById(Integer id) {
		Optional<Autor> obj = repository.findById(id);
		return obj.orElse(null);
	}

	public Autor save(AutorDTO obj) {
		findByCpf(obj);
		return repository.save(mapper.map(obj, Autor.class));
	}
	
	public Autor update(AutorDTO obj) {
		if(!repository.existsById(obj.getIdPessoa())) {
	           throw new ObjectNotFoundException("ID: " + obj.getIdPessoa() + " não foi encontrado!");
	        }	
		findByCpf(obj);
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

	public String findAddressByCep(String cep) {
		String json;
		try {
			URL url = new URL("http://viacep.com.br/ws/" + cep + "/json");
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder jsonSb = new StringBuilder();
			br.lines().forEach(l -> jsonSb.append(l.trim()));
			json = jsonSb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return json;
	}

}
