package com.banco.spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banco.spring.exceptions.CustomErrorType;
import com.banco.spring.model.Agencia;
import com.banco.spring.repository.AgenciaRepository;

@RestController
public class AgenciaController {
	@Autowired
	private AgenciaRepository _agenciaRepository;

	private CustomErrorType cet;
	
	@RequestMapping(value = "/agencia", method = RequestMethod.GET)
	public List<Agencia> Listar_Agencias() {
		return _agenciaRepository.findAll();
	}

	@RequestMapping(value = "/agencia/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> Procurar_Agencia(@PathVariable(value = "id") long id) {
		Optional<Agencia> agencia = _agenciaRepository.findById(id);
		if (agencia.isPresent())
			return new ResponseEntity<Agencia>(agencia.get(), HttpStatus.OK);
		else {
			cet = new CustomErrorType("Cliente inexistente!");
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/agencia", method = RequestMethod.POST)
	public ResponseEntity<?> Criar_Agencia(@RequestParam String nomeAgencia, @RequestParam String endereco,
			@RequestParam String telefone) {
		if(telefone.length()!=11) {
			cet = new CustomErrorType("Telefone inválido!");
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.OK);
		}
		else {
			Agencia agencia = new Agencia();
			agencia.setNomeAgencia(nomeAgencia);
			agencia.setEndereco(endereco);
			agencia.setTelefone(telefone);
			_agenciaRepository.save(agencia);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/agencia/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> Atualizar_Agencia(@PathVariable(value = "id") long id, @RequestParam String nomeAgencia, 
			@RequestParam String endereco, @RequestParam String telefone) {
		//refazer codigo
		Optional<Agencia> oldAgencia = _agenciaRepository.findById(id);
		if (oldAgencia.isPresent()) {
			if(telefone.length()!=11) {
				cet = new CustomErrorType("Telefone inválido!");
				return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.OK);
			}
			Agencia agencia = oldAgencia.get();
			agencia.setNomeAgencia(nomeAgencia);
			agencia.setEndereco(endereco);
			agencia.setTelefone(telefone);
			_agenciaRepository.save(agencia);
			return new ResponseEntity<Agencia>(agencia, HttpStatus.OK);
		}
			
		cet = new CustomErrorType("Agencia inexistente!");
		return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.OK);
		
	}

	@RequestMapping(value = "/agencia/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> Deletar_Agencia(@PathVariable(value = "id") long id) {
		Optional<Agencia> agencia = _agenciaRepository.findById(id);
		if (agencia.isPresent()) {
			_agenciaRepository.delete(agencia.get());
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			cet = new CustomErrorType("Agencia inexistente!");
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.OK);
			}
	}
}