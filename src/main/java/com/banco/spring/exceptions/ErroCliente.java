package com.banco.spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroCliente {

	static final String CPF_IVALIDO = "CPF iválido";

	public static ResponseEntity<CustomErrorType> erroCpfIvalido() {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(CPF_IVALIDO)),
				HttpStatus.NOT_FOUND);
	}

}
