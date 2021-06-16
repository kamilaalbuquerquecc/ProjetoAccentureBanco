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

import com.banco.spring.exceptions.CustomErrorType;
import com.banco.spring.model.Extrato;
import com.banco.spring.repository.ExtratoRepository;

@RestController
public class ExtratoController {
	@Autowired
	private ExtratoRepository _extratoRepository;

	private CustomErrorType cet;

	@RequestMapping(value = "/extrato", method = RequestMethod.GET)
	public List<Extrato> Listar_Extrato() {
		return _extratoRepository.findAll();
	}

	@RequestMapping(value = "/extrato/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> Procurar_Extrato(@PathVariable(value = "id") long id)
	{
		Optional<Extrato> extrato = _extratoRepository.findById(id);
		if(extrato.isPresent())
			return new ResponseEntity<Extrato>(extrato.get(), HttpStatus.OK);
		else {
			cet = new CustomErrorType("Extrato inexistente!");
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/extrato", method =  RequestMethod.POST)
	public Extrato Criar_Extrato(@Valid @RequestBody Extrato extrato)
	{
		return _extratoRepository.save(extrato);
	}

	@RequestMapping(value = "/extrato/{id}", method =  RequestMethod.PUT)
	public ResponseEntity<?> Atualizar_Extrato(@PathVariable(value = "id") long id, @Valid @RequestBody Extrato newExtrato)
	{
		Optional<Extrato> oldExtrato = _extratoRepository.findById(id);
		if(oldExtrato.isPresent()){
			Extrato extrato = oldExtrato.get();
			extrato.setId(newExtrato.getId());
			extrato.setDataHoraMovimento(newExtrato.getDataHoraMovimento());
			extrato.setOperacao(newExtrato.getOperacao());
			_extratoRepository.save(extrato);
			return new ResponseEntity<Extrato>(extrato, HttpStatus.OK);
		}
		else {
			cet = new CustomErrorType("Extrato inexistente!");
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.OK);
		}
	} 
	

	@RequestMapping(value = "/extrato/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> Deletar_Extrato(@PathVariable(value = "id") long id)
	{
		Optional<Extrato> extrato = _extratoRepository.findById(id);
		if(extrato.isPresent()){
			_extratoRepository.delete(extrato.get());
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			cet = new CustomErrorType("Extrato inexistente!");
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.OK);
		}
	}
}