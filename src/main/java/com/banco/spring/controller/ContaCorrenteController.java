package com.banco.spring.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banco.spring.exceptions.CustomErrorType;
import com.banco.spring.model.Cliente;
import com.banco.spring.model.ContaCorrente;
import com.banco.spring.model.Extrato;
import com.banco.spring.model.enums.Operacao;
import com.banco.spring.repository.ClienteRepository;
import com.banco.spring.repository.ContaCorrenteRepository;
import com.banco.spring.repository.ExtratoRepository;

@RestController
public class ContaCorrenteController {
	@Autowired
	private ContaCorrenteRepository _contaCorrenteRepository;
	@Autowired
	private ClienteRepository _clienteRepository;
	@Autowired
	private ExtratoRepository _extratoRepository;

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
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/contaCorrente", method = RequestMethod.POST)
	public ResponseEntity<?>  Criar_Conta(@Valid @RequestParam long idCliente, @RequestParam String numeroAgencia,
			@RequestParam String numeroContaCorrente) {
		try{
			Cliente cliente = _clienteRepository.getOne(idCliente);
			ContaCorrente contaCorrente = new ContaCorrente();
			contaCorrente.setCliente(cliente);
			contaCorrente.setNumeroAgencia(numeroAgencia);
			contaCorrente.setNumeroContaCorrente(numeroContaCorrente);
			contaCorrente.setSaldoContaCorrente(0);
			_contaCorrenteRepository.save(contaCorrente);

			return new ResponseEntity<ContaCorrente>(contaCorrente, HttpStatus.OK);
		}catch(Exception e) {
			cet = new CustomErrorType("Cliente inexistente!");
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/contaCorrente/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> Atualizar_Conta(@PathVariable(value = "id") long id, String numeroAgencia,
											 String numeroContaCorrente, Double saldoContaCorrente) {
		Optional<ContaCorrente> oldContaCorrente = _contaCorrenteRepository.findById(id);
		if (oldContaCorrente.isPresent()) {
			ContaCorrente contaCorrente = oldContaCorrente.get();

			if (numeroAgencia != null) {
				contaCorrente.setNumeroAgencia(numeroAgencia);
			}
			if (numeroContaCorrente != null) {
				contaCorrente.setNumeroContaCorrente(numeroContaCorrente);
			}
			if (saldoContaCorrente != null) {
				contaCorrente.setSaldoContaCorrente(saldoContaCorrente);
			}
			_contaCorrenteRepository.save(contaCorrente);
			return new ResponseEntity<ContaCorrente>(contaCorrente, HttpStatus.OK);
		}else {
			cet = new CustomErrorType("Conta inexistente!");
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.NOT_FOUND);
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
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/contaCorrente/{id}/saque", method =  RequestMethod.PUT)
	public ResponseEntity<?> Saque(@PathVariable(value = "id") long id, float valor)
	{
		Optional<ContaCorrente> conta = _contaCorrenteRepository.findById(id);
		Optional<Extrato> ext = _extratoRepository.findById(id);
		CustomErrorType cet;
		try  {
			ContaCorrente contaCorrente = conta.get();

			if(conta.get().getSaldoContaCorrente()>=valor) {
				contaCorrente.saque(valor);

				Extrato movimentacao = new Extrato();

				LocalDate agora = LocalDate.now();
				Operacao operacao = Operacao.SAQUE;

				movimentacao.setDataHoraMovimento(agora);
				movimentacao.setOperacao(operacao);
				movimentacao.setValor(valor);

				Set<Extrato> listaExtrato = contaCorrente.getExtrato();
				listaExtrato.add(movimentacao);

				_extratoRepository.save(movimentacao);
				contaCorrente.setExtrato(listaExtrato);
				_contaCorrenteRepository.save(contaCorrente);
				return new ResponseEntity<>(HttpStatus.OK);}
			else {
				cet = new CustomErrorType("Valor indisponivel para saque!");
				return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.FORBIDDEN);
			}
		} catch (Exception e){
			cet = new CustomErrorType("Conta inexistente");
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/contaCorrente/{id}/deposito", method =  RequestMethod.PUT)
	public ResponseEntity<?> Deposito(@PathVariable(value = "id") long id, double valor)
	{
		Optional<ContaCorrente> conta = _contaCorrenteRepository.findById(id);
		Optional<Extrato> ext = _extratoRepository.findById(id);
		if (conta != null) {
			ContaCorrente contaCorrente = conta.get();
			contaCorrente.deposito(valor);

			Extrato movimentacao = new Extrato();

			LocalDate agora = LocalDate.now();
			Operacao operacao = Operacao.DEPOSITO;

			movimentacao.setDataHoraMovimento(agora);
			movimentacao.setOperacao(operacao);
			movimentacao.setValor(valor);

			Set<Extrato> listaExtrato = contaCorrente.getExtrato();
			listaExtrato.add(movimentacao);

			_extratoRepository.save(movimentacao);
			contaCorrente.setExtrato(listaExtrato);
			_contaCorrenteRepository.save(contaCorrente);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			cet = new CustomErrorType("Conta inexistente!");
			return new ResponseEntity<>(cet.getErrorMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/contaCorrente/{id}/transferencia", method =  RequestMethod.PUT)
	public ResponseEntity<?> Transferencia(@RequestParam long idPagar,
			@RequestParam long idReceber, @RequestParam double valor) {
		Optional<ContaCorrente> transferir = _contaCorrenteRepository.findById(idPagar);
		Optional<ContaCorrente> transferido = _contaCorrenteRepository.findById(idReceber);
		try {
			ContaCorrente pagar = transferir.get();
			ContaCorrente receber = transferido.get();
			if (pagar.getSaldoContaCorrente() >= valor) {
				pagar.transferencia(receber, valor);

				Extrato movimentacao = new Extrato();
				Extrato movimentacaoReceber = new Extrato();

				LocalDate agora = LocalDate.now();

				movimentacao.setDataHoraMovimento(agora);
				movimentacaoReceber.setDataHoraMovimento(agora);
				movimentacao.setOperacao(Operacao.TRANSFERENCIA);
				movimentacaoReceber.setOperacao(Operacao.TRANSFERENCIARECEBIDA);
				movimentacao.setValor(valor);
				movimentacaoReceber.setValor(valor);

				Set<Extrato> listaExtrato = pagar.getExtrato();
				Set<Extrato> listaExtratoReceber = receber.getExtrato();

				_extratoRepository.save(movimentacao);
				_extratoRepository.save(movimentacaoReceber);

				listaExtrato.add(movimentacao);
				listaExtratoReceber.add(movimentacaoReceber);

				pagar.setExtrato(listaExtrato);
				receber.setExtrato(listaExtratoReceber);

				_contaCorrenteRepository.save(pagar);
				_contaCorrenteRepository.save(receber);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				cet = new CustomErrorType("Saldo insuficiente!");
				return new ResponseEntity<>(cet.getErrorMessage(), HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			cet = new CustomErrorType("Conta inexistente!");
			return new ResponseEntity<>(cet.getErrorMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/contaCorrente/{id}/recalcularSaldo", method =  RequestMethod.PUT)
	public ResponseEntity<?> Recalcular_Saldo(@PathVariable(value = "id") long id) {
		Optional<ContaCorrente> conta = _contaCorrenteRepository.findById(id);

		List<Extrato> todos = _extratoRepository.findAll();
		ContaCorrente contaCorrente = conta.get();
		Set<Extrato> extratos = contaCorrente.getExtrato();
		double saldo = 0;

		for (Extrato ext : extratos) {
			if (ext.getOperacao() == Operacao.SAQUE || ext.getOperacao() == Operacao.TRANSFERENCIA) {
				saldo -= ext.getValor();
			} else if (ext.getOperacao() == Operacao.DEPOSITO ||
					   ext.getOperacao() == Operacao.TRANSFERENCIARECEBIDA) {
				saldo += ext.getValor();
			}
		}
		try {
			contaCorrente.setSaldoContaCorrente(saldo);
			_contaCorrenteRepository.save(contaCorrente);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			cet = new CustomErrorType("Conta inexistente!");
			return new ResponseEntity<>(cet.getErrorMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
