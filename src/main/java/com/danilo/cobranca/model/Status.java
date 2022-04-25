package com.danilo.cobranca.model;

public enum Status {
	PENDENTE("Pendente"), PAGO("Quitado");
	
	private String descricao;
	
	Status(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
