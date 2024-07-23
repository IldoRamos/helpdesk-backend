package com.ramos.helpdesk.domain.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ramos.helpdesk.domain.Chamado;

import jakarta.validation.constraints.NotNull;

public class ChamadoDTO {
	
	private Integer id;
	
	@JsonFormat(pattern = "dd/MM/yyyy")	
	private LocalDate dataAbertura = LocalDate.now();
	@JsonFormat(pattern = "dd/MM/yyyy")	
	private LocalDate dataFechamento;
	@NotNull(message = "O campo Prioridade é requerido!")
	private Integer prioridade;
	@NotNull(message = "O campo Status é requerido!")
	private Integer status;
	@NotNull(message = "O campo Titulo é requerido!")
	private String titulo;
	@NotNull(message = "O campo Observações é requerido!")
	private String observacoes;
	@NotNull(message = "O campo Tecnico é requerido!")
	private Integer tecnico;
	@NotNull(message = "O campo Cliente é requerido!")
	private Integer cliente;
	private String nomeTecnico;
	private String nomeCliente;
	public ChamadoDTO(Chamado obj) {
		super();
		this.id = obj.getId();
		this.dataAbertura = obj.getDataAbertura();
		this.dataFechamento = obj.getDataFechamento();
		this.prioridade = obj.getPrioridade().getCodigo();
		this.status = obj.getStatus().getCodigo();
		this.titulo = obj.getTitulo();
		this.observacoes = obj.getObservacoes();
		this.tecnico =obj.getTecnico().getId();
		this.cliente = obj.getCliente().getId();
		this.nomeTecnico = obj.getTecnico().getNome();
		this.nomeCliente = obj.getCliente().getNome();
	}
	public ChamadoDTO() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public LocalDate getDataAbertura() {
		return dataAbertura;
	}
	public LocalDate getDataFechamento() {
		return dataFechamento;
	}
	public Integer getPrioridade() {
		return prioridade;
	}
	public Integer getStatus() {
		return status;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public Integer getTecnico() {
		return tecnico;
	}
	public Integer getCliente() {
		return cliente;
	}
	public String getNomeTecnico() {
		return nomeTecnico;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setDataAbertura(LocalDate dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	public void setDataFechamento(LocalDate dataFechamento) {
		this.dataFechamento = dataFechamento;
	}
	public void setPrioridade(Integer prioridade) {
		this.prioridade = prioridade;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public void setTecnico(Integer tecnico) {
		this.tecnico = tecnico;
	}
	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}
	public void setNomeTecnico(String nomeTecnico) {
		this.nomeTecnico = nomeTecnico;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	
	
	

}
