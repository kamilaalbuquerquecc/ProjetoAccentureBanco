package com.banco.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banco.spring.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	
}
