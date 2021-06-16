package com.banco.spring.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banco.spring.exceptions.CustomErrorType;
import com.banco.spring.exceptions.ErroCliente;
import com.banco.spring.model.Agencia;
import com.banco.spring.model.Cliente;
import com.banco.spring.repository.AgenciaRepository;
import com.banco.spring.repository.ClienteRepository;

@RestController
public class ClienteController {
	@Autowired
	private ClienteRepository _clienteRepository;
	@Autowired
	private AgenciaRepository _agenciaRepository;

	private CustomErrorType cet;
	
	@RequestMapping(value = "/cliente", method = RequestMethod.GET)
	public List<Cliente> Listar_Clientes() {
		return _clienteRepository.findAll();
	}

	@RequestMapping(value = "/cliente/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> Procurar_Cliente(@PathVariable(value = "id") long id) {
		Optional<Cliente> cliente = _clienteRepository.findById(id);
		if (cliente.isPresent())
			return new ResponseEntity<Cliente>(cliente.get(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/cliente", method = RequestMethod.POST)
	public ResponseEntity<?> Criar_Cliente(@RequestParam long idAgencia, @RequestParam String nome,
			@RequestParam() String cpf, @RequestParam String telefone) {
		Agencia agencia = _agenciaRepository.getOne(idAgencia);
		Cliente cliente = new Cliente();
		if(cpf.length()<11 || cpf.length()>11) {
			cet = new CustomErrorType("CPF inválido!");
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.OK);
			
		}else {
			cliente.setAgencia(agencia);
			cliente.setNome(nome);
			cliente.setCpf(cpf);
			cliente.setTelefone(telefone);
			_clienteRepository.save(cliente);
			return new ResponseEntity<>(HttpStatus.OK);}
			
	}

	@RequestMapping(value = "/cliente/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Cliente> Atualizar_Cliente(@PathVariable(value = "id") long id,
			@Valid @RequestBody Cliente newCliente) {
		Optional<Cliente> oldCliente = _clienteRepository.findById(id);
		if (oldCliente.isPresent()) {
			Cliente cliente = oldCliente.get();
			cliente.setNome(newCliente.getNome());
			cliente.setId(newCliente.getId());
			cliente.setNome(newCliente.getNome());
			cliente.setCpf(newCliente.getCpf());
			cliente.setTelefone(newCliente.getTelefone());
			_clienteRepository.save(cliente);
			return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/cliente/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> Deletar_Cliente(@PathVariable(value = "id") long id) {
		Optional<Cliente> cliente = _clienteRepository.findById(id);
		if (cliente.isPresent()) {
			_clienteRepository.delete(cliente.get());
			return new ResponseEntity<>(HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}