package com.banco.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banco.spring.model.Extrato;

public interface ExtratoRepository extends JpaRepository<Extrato, Long> {


}
