package com.banco.spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



public class ErroAgencia {
	static final String NOME_ESTA_VAZIO = "Nome iválido";
	static final String ENDERECO_ESTA_VAZIO = "Endereço iválido";
	static final String TELEFONE_ESTA_VAZIO = "Telefone iválido";
	
	public static ResponseEntity<CustomErrorType> erroNomeEstaVazio() {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(NOME_ESTA_VAZIO)),
				HttpStatus.NOT_FOUND);
	}
	public static ResponseEntity<CustomErrorType> erroEnderecoEstaVazio() {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ENDERECO_ESTA_VAZIO)),
				HttpStatus.NOT_FOUND);
	}
	public static ResponseEntity<CustomErrorType> erroTelefoneEstaVazio() {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(TELEFONE_ESTA_VAZIO)),
				HttpStatus.NOT_FOUND);
	}

}
