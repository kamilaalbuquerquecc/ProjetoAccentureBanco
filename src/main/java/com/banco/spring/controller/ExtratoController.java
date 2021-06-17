package com.banco.spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.banco.spring.exceptions.CustomErrorType;
import com.banco.spring.model.ContaCorrente;
import com.banco.spring.model.Extrato;
import com.banco.spring.repository.ContaCorrenteRepository;
import com.banco.spring.repository.ExtratoRepository;

@RestController
public class ExtratoController {
	@Autowired
	private ExtratoRepository _extratoRepository;

	@Autowired
	private ContaCorrenteRepository _contaCorrenteRepository;

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
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/extrato/contaCorrente/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> Procurar_Extrato_ContaCorrente(@PathVariable(value = "id") long id) {
		Optional<ContaCorrente> conta = _contaCorrenteRepository.findById(id);
		if (conta.isPresent()) {
			ContaCorrente contaCorrente = conta.get();
			return new ResponseEntity<>(contaCorrente.getExtrato(), HttpStatus.OK);
		}
		else {
			cet = new CustomErrorType("Conta inexistente!");
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.NOT_FOUND);
		}
	}

}