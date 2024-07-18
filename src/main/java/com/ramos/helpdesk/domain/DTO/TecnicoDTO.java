package com.ramos.helpdesk.domain.DTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ramos.helpdesk.domain.Pessoa;
import com.ramos.helpdesk.domain.Tecnico;
import com.ramos.helpdesk.domain.enums.Perfil;

import jakarta.validation.constraints.NotNull;

public class TecnicoDTO  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	@NotNull(message = "O campo NOME é requerido")
	protected String nome; 
	@NotNull(message = "O campo CPF é requerido")
	protected String cpf;
	@NotNull(message = "O campo Email é requerido")
	protected String email;
	@NotNull(message = "O campo Senha é requerido")
	protected String senha;
	protected Set<Integer> perfis = new HashSet<>();
	@JsonFormat(pattern = "dd/MM/yyyy")	
	protected LocalDate dataCriacao = LocalDate.now();
	public TecnicoDTO() {
		super();
	}
	public TecnicoDTO(Tecnico obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.perfis = obj.getPerfis().stream()
				.map(x-> x.getCodigo()).collect(Collectors.toSet());
		this.dataCriacao = obj.getDataCriacao();
	}
	public Integer getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public String getCpf() {
		return cpf;
	}
	public String getEmail() {
		return email;
	}
	public String getSenha() {
		return senha;
	}
	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x->Perfil.toEnum(x)).collect(Collectors.toSet());
	}
	public LocalDate getDataCriacao() {
		return dataCriacao;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public void setPerfis(Perfil perfis) {
		this.perfis.add(perfis.getCodigo());
	}
	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
}
