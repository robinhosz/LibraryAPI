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

import com.robson.biblioteca.dto.ClienteDTO;
import com.robson.biblioteca.exception.DataIntegrityViolationException;
import com.robson.biblioteca.exception.ObjectNotFoundException;
import com.robson.biblioteca.model.Cliente;
import com.robson.biblioteca.model.Pessoa;
import com.robson.biblioteca.repository.ClienteRepository;
import com.robson.biblioteca.repository.PessoaRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ModelMapper mapper;

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto com a id: " + id + " não encontrado"));
	}

	public Cliente save(ClienteDTO obj) {
		findByCpf(obj);
		return repository.save(mapper.map(obj, Cliente.class));
	}

	public Cliente update(ClienteDTO obj) {
		if (!repository.existsById(obj.getIdPessoa())) {
			throw new ObjectNotFoundException("ID: " + obj.getIdPessoa() + " não foi encontrado!");
		}
		findByCpf(obj);
		return repository.save(mapper.map(obj, Cliente.class));
	}

	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
	}

	private void findByCpf(ClienteDTO objDto) {
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
