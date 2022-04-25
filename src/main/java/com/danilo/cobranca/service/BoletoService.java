package com.danilo.cobranca.service;

import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.danilo.cobranca.model.Boleto;
import com.danilo.cobranca.model.BoletoView;
import com.danilo.cobranca.model.Status;
import com.danilo.cobranca.repository.BoletoRepository;
import com.danilo.cobranca.util.BoletoUtil;

@Service
public class BoletoService {

	@Autowired
	private BoletoRepository boletoRepository;

	public void salvar(Boleto boleto) {
		try {
			boletoRepository.save(boleto);
		} catch (DateTimeParseException | DataIntegrityViolationException | ConversionFailedException e) {
			throw new IllegalArgumentException("Formato de data inválida.");
		}
	}// salvar

	public void excluir(Long id) {
		Boleto boleto = findById(id);
		boletoRepository.delete(boleto);
	}// excluir

	public String pagar(Long id) {
		Boleto boleto = findById(id);
		if (boleto != null) {
			boleto.setStatus(Status.PAGO);
		}
		boletoRepository.save(boleto);
		return Status.PAGO.getDescricao();
	}// pagar

	public Boleto findById(Long id) {
		return boletoRepository.findById(id);
	}// findById

	public List<Boleto> pesquisar(String descricao) {
		return boletoRepository.findByDescricaoContaining(descricao);
	}// pesquisar

	public BoletoView boletoView(Boleto boleto) {
		BoletoView boletoView = new BoletoView();
		boletoView.setId(boleto.getId());
		boletoView.setDescricao(boleto.getDescricao());
		boletoView.setDataVencimento(BoletoUtil.formatarDataPadraoNormal(boleto.getDataVencimento()));
		boletoView.setValor(BoletoUtil.formatarValorParaReal(String.valueOf(boleto.getValor())));
		boletoView.setStatus(boleto.getStatus());
		return boletoView;
	}// boletoView


}// classe
