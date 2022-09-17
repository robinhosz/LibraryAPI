package com.robson.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robson.biblioteca.dto.ClienteDTO;
import com.robson.biblioteca.dto.LivroDTO;
import com.robson.biblioteca.exception.DataIntegrityViolationException;
import com.robson.biblioteca.exception.ObjectNotFoundException;
import com.robson.biblioteca.model.Cliente;
import com.robson.biblioteca.model.Endereco;
import com.robson.biblioteca.model.Livro;
import com.robson.biblioteca.model.Pessoa;
import com.robson.biblioteca.repository.ClienteRepository;
import com.robson.biblioteca.repository.EnderecoRepository;
import com.robson.biblioteca.repository.LivroRepository;
import com.robson.biblioteca.repository.PessoaRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired
	private ViaCepService viaCepService;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

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
		salvarClienteComCep(obj);
		return repository.save(mapper.map(obj, Cliente.class));
	}

	public Cliente update(ClienteDTO obj) {
		if (!repository.existsById(obj.getIdPessoa())) {
			throw new ObjectNotFoundException("ID: " + obj.getIdPessoa() + " não foi encontrado!");
		}
		findByCpf(obj);
		salvarClienteComCep(obj);
		return repository.save(mapper.map(obj, Cliente.class));
	}

	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
	}
	
//	public void comprar(LivroDTO obj) {
//		Optional<Livro> livro = livroRepository.findByIsbn(obj.getIsbn());
//		
//		Integer quantidade = livro.get().getQuantidade() - obj.getQuantidade();
//	}

	private void findByCpf(ClienteDTO objDto) {
		Optional<Pessoa> pessoa = pessoaRepository.findByCpf(objDto.getCpf());
		if (pessoa.isPresent() && !pessoa.get().getIdPessoa().equals(objDto.getIdPessoa())) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema");

		}
	}
	
	private void salvarClienteComCep(ClienteDTO cliente) {
		// Verificar se o Endereco do Autor já existe (pelo CEP).
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			// Caso não exista, integrar com o ViaCEP e persistir o retorno.
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		cliente.setEndereco(endereco);
	}

}
