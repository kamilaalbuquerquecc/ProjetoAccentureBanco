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
import org.springframework.web.bind.annotation.RestController;



import com.banco.spring.model.Agencia;
import com.banco.spring.repository.AgenciaRepository;

@RestController
public class AgenciaController {
	@Autowired
	private AgenciaRepository _agenciaRepository;

	@RequestMapping(value = "/agencia", method = RequestMethod.GET)
	public List<Agencia> Get() {
		return _agenciaRepository.findAll();
	}

	@RequestMapping(value = "/agencia/{id}", method = RequestMethod.GET)
	public ResponseEntity<Agencia> GetById(@PathVariable(value = "id") long id)
	{
		Optional<Agencia> agencia = _agenciaRepository.findById(id);
		if(agencia.isPresent())
			return new ResponseEntity<Agencia>(agencia.get(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/agencia", method =  RequestMethod.POST)
	public Agencia Post(@Valid @RequestBody Agencia agencia)
	{
		return _agenciaRepository.save(agencia);
	}

	@RequestMapping(value = "/agencia/{id}", method =  RequestMethod.PUT)
	public ResponseEntity<Agencia> Put(@PathVariable(value = "id") long id, @Valid @RequestBody Agencia newAgencia)
	{
		Optional<Agencia> oldAgencia = _agenciaRepository.findById(id);
		if(oldAgencia.isPresent()){
			Agencia agencia = oldAgencia.get();
			agencia.setIdAgencia(newAgencia.getIdAgencia());
			agencia.setNomeAgencia(newAgencia.getNomeAgencia());
			agencia.setEndereco(newAgencia.getEndereco());
			agencia.setTelefone(newAgencia.getTelefone());
			_agenciaRepository.save(agencia);
			return new ResponseEntity<Agencia>(agencia, HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/agencia/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id)
	{
		Optional<Agencia> agencia = _agenciaRepository.findById(id);
		if(agencia.isPresent()){
			_agenciaRepository.delete(agencia.get());
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}