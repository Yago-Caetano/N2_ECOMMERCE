package com.example.demo.Beans;

import java.sql.Date;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

@Entity
public class User {
	
	@Id
	@Basic
	private int id;
	@Column
	@Basic
	private String nome;
	@Column
	@Basic
	private Date nascimento;
	@Column
	@Basic
	private String email;
	@Column
	@Basic
	private String senha;
	@Column
	@Basic
	private String cpf;
	@Column
	@Basic
	private int idTipoUsuario;	
	@Column
	@Basic
	private boolean statusUsuario;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public int getIdTipoUsuario() {
		return idTipoUsuario;
	}
	public void setIdTipoUsuario(int idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}
	public boolean isStatusUsuario() {
		return statusUsuario;
	}
	public void setStatusUsuario(boolean statusUsuario) {
		this.statusUsuario = statusUsuario;
	}
	public Date getNascimento() {
		return nascimento;
	}
	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	

}