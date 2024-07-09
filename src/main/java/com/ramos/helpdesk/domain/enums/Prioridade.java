package com.ramos.helpdesk.domain.enums;

public enum Prioridade {
ABERTO(0,"ROLE_ABERTO"), ANDAMENTO(1,"ROLE_ANDAMENTO"), ENCERRADO(2,"ROLE_ENCERRADO");
	
	private Integer codigo;
	private String descricao;
	
	private Prioridade(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	private static Prioridade toEnum(Integer cod) {
		if(cod==null) {
			return null;
		}
		for(Prioridade x: Prioridade.values()) {
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Status invalido");
	}
	
}
