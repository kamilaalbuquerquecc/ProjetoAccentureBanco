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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banco.spring.exceptions.CustomErrorType;
import com.banco.spring.model.Cliente;
import com.banco.spring.model.ContaCorrente;
import com.banco.spring.repository.ClienteRepository;
import com.banco.spring.repository.ContaCorrenteRepository;

@RestController
public class ContaCorrenteController {
	@Autowired
	private ContaCorrenteRepository _contaCorrenteRepository;
	@Autowired
	private ClienteRepository _clienteRepository;

	private CustomErrorType cet;
	
	@RequestMapping(value = "/contaCorrente", method = RequestMethod.GET)
	public List<ContaCorrente> Listar_Contas() {
		return _contaCorrenteRepository.findAll();
	}

	@RequestMapping(value = "/contaCorrente/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> Procurar_Contas(@PathVariable(value = "id") long id) {
		Optional<ContaCorrente> contaCorrente = _contaCorrenteRepository.findById(id);
		if (contaCorrente.isPresent())
			return new ResponseEntity<ContaCorrente>(contaCorrente.get(), HttpStatus.OK);
		else {
			cet = new CustomErrorType("Conta inexistente!");
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/contaCorrente", method = RequestMethod.POST)
	public ResponseEntity<?>  Criar_Conta(@Valid @RequestParam long idCliente, @RequestParam String numeroAgencia,
			@RequestParam String numeroContaCorrente, @RequestParam double saldoContaCorrente) {
		try{
			Cliente cliente = _clienteRepository.getOne(idCliente);
			ContaCorrente contaCorrente = new ContaCorrente();
			contaCorrente.setCliente(cliente);
			contaCorrente.setNumeroAgencia(numeroAgencia);
			contaCorrente.setNumeroContaCorrente(numeroContaCorrente);
			contaCorrente.setSaldoContaCorrente(saldoContaCorrente);
			_contaCorrenteRepository.save(contaCorrente);
			return new ResponseEntity<ContaCorrente>(contaCorrente, HttpStatus.OK);
		}catch(Exception e) {
			cet = new CustomErrorType("Cliente inexistente!");
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/contaCorrente/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> Atualizar_Conta(@PathVariable(value = "id") long id,
			@Valid @RequestBody ContaCorrente newContaCorrente) {
		Optional<ContaCorrente> oldContaCorrente = _contaCorrenteRepository.findById(id);
		if (oldContaCorrente.isPresent()) {
			ContaCorrente contaCorrente = oldContaCorrente.get();
			contaCorrente.setId(newContaCorrente.getId());
			contaCorrente.setNumeroAgencia(newContaCorrente.getNumeroAgencia());
			contaCorrente.setNumeroContaCorrente(newContaCorrente.getNumeroContaCorrente());
			contaCorrente.setSaldoContaCorrente(newContaCorrente.getSaldoContaCorrente());
			_contaCorrenteRepository.save(contaCorrente);
			return new ResponseEntity<ContaCorrente>(contaCorrente, HttpStatus.OK);
		}else {
			cet = new CustomErrorType("Conta inexistente!");
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/contaCorrente/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> Deletar_Conta(@PathVariable(value = "id") long id) {
		Optional<ContaCorrente> contaCorrente = _contaCorrenteRepository.findById(id);
		if (contaCorrente.isPresent()) {
			_contaCorrenteRepository.delete(contaCorrente.get());
			return new ResponseEntity<>( HttpStatus.OK);
		}else {
			cet = new CustomErrorType("Conta inexistente!");
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/contaCorrente/{id}/saque", method =  RequestMethod.PUT)
	public ResponseEntity<?> Saque(@PathVariable(value = "id") long id, float valor)
	{
		Optional<ContaCorrente> conta = _contaCorrenteRepository.findById(id);
		CustomErrorType cet;
		try  {
			ContaCorrente contaCorrente = conta.get();
			if(conta.get().getSaldoContaCorrente()>=valor) {
				contaCorrente.saque(valor);
				_contaCorrenteRepository.save(contaCorrente);
				return new ResponseEntity<>(HttpStatus.OK);}
			else {
				cet = new CustomErrorType("Valor indisponivel para saque!");
				return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.OK);
			}
		} catch (Exception e){
			cet = new CustomErrorType("Conta inexistente");
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/contaCorrente/{id}/deposito", method =  RequestMethod.PUT)
	public ResponseEntity<?> Deposito(@PathVariable(value = "id") long id, double valor)
	{
		Optional<ContaCorrente> conta = _contaCorrenteRepository.findById(id);
		if (conta != null) {
			ContaCorrente contaCorrente = conta.get();
			contaCorrente.deposito(valor);
			_contaCorrenteRepository.save(contaCorrente);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			cet = new CustomErrorType("Conta inexistente!");
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/contaCorrente/{id}/transferencia", method =  RequestMethod.PUT)
	public ResponseEntity<?> Tranferencia(@PathVariable(value = "id") long idPagar,
			@RequestParam long idReceber, @RequestParam double valor)
	{
		Optional<ContaCorrente> transferir = _contaCorrenteRepository.findById(idPagar);
		Optional<ContaCorrente> transferido = _contaCorrenteRepository.findById(idReceber);
		try{
			ContaCorrente pagar = transferir.get();
			ContaCorrente receber = transferido.get();
			if(pagar.getSaldoContaCorrente()>=valor) {
				pagar.transferencia(receber, valor);
				_contaCorrenteRepository.save(pagar);
				_contaCorrenteRepository.save(receber);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				cet = new CustomErrorType("Saldo insuficiente!");
				return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.OK);
			}
		} catch(Exception e) {
			cet = new CustomErrorType("Conta inexistente!");
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.OK);
		}
		}
}
