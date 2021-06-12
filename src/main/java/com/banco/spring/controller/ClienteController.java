package com.banco.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.spring.model.Cliente;

@RestController
@RequestMapping(value= "/clientes")
public class ClienteController {
	
	@GetMapping
	public ResponseEntity<Cliente> findAll(){
		Cliente c = new Cliente(1L, "Joana", "70352072440" , "83991580844");
		//ok() retorna resposta com sucesso e .bory() retorna o corpo da resposta.
		return ResponseEntity.ok().body(c); 
	}
	

}
