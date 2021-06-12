package com.banco.spring.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.spring.model.Agencia;
import com.banco.spring.repository.AgenciaRepository;


@RestController
@RequestMapping(value= "/agencias")
public class AgenciaController {
	
	@Autowired
	private AgenciaRepository agenciaRep;
	
	@GetMapping
	public ResponseEntity<Agencia> findAll(){
		Agencia agencia = new Agencia(null, "Agencia 1", "Rua das Baratas", "83 987564123");
		Agencia agencia2 = new Agencia(null, "Agencia 2", "Rua das Baratas", "83 987564123");
		agenciaRep.saveAll(Arrays.asList(agencia,agencia2));
		//ok() retorna resposta com sucesso e .bory() retorna o corpo da resposta.
		return ResponseEntity.ok().body(agencia); 
	}

}
