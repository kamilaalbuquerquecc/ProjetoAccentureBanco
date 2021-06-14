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



import com.banco.spring.model.ContaCorrente;
import com.banco.spring.repository.ContaCorrenteRepository;

@RestController
public class ContaCorrenteController {
	@Autowired
	private ContaCorrenteRepository _contaCorrenteRepository;

	@RequestMapping(value = "/contaCorrente", method = RequestMethod.GET)
	public List<ContaCorrente> Get() {
		return _contaCorrenteRepository.findAll();
	}

	@RequestMapping(value = "/contaCorrente/{id}", method = RequestMethod.GET)
	public ResponseEntity<ContaCorrente> GetById(@PathVariable(value = "id") long id)
	{
		Optional<ContaCorrente> contaCorrente = _contaCorrenteRepository.findById(id);
		if(contaCorrente.isPresent())
			return new ResponseEntity<ContaCorrente>(contaCorrente.get(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/contaCorrente", method =  RequestMethod.POST)
	public ContaCorrente Post(@Valid @RequestBody ContaCorrente contaCorrente)
	{
		return _contaCorrenteRepository.save(contaCorrente);
	}

	@RequestMapping(value = "/contaCorrente/{id}", method =  RequestMethod.PUT)
	public ResponseEntity<ContaCorrente> Put(@PathVariable(value = "id") long id, @Valid @RequestBody ContaCorrente newContaCorrente)
	{
		Optional<ContaCorrente> oldContaCorrente = _contaCorrenteRepository.findById(id);
		if(oldContaCorrente.isPresent()){
			ContaCorrente contaCorrente = oldContaCorrente.get();
			contaCorrente.setId(newContaCorrente.getId());
            contaCorrente.setNumeroAgencia(newContaCorrente.getNumeroAgencia());
            contaCorrente.setNumeroContaCorrente(newContaCorrente.getNumeroContaCorrente());
            contaCorrente.setSaldoContaCorrente(newContaCorrente.getSaldoContaCorrente());
			_contaCorrenteRepository.save(contaCorrente);
			return new ResponseEntity<ContaCorrente>(contaCorrente, HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	} 
	

	@RequestMapping(value = "/contaCorrente/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id)
	{
		Optional<ContaCorrente> agencia = _contaCorrenteRepository.findById(id);
		if(agencia.isPresent()){
			_contaCorrenteRepository.delete(agencia.get());
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}