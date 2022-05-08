package com.example.demo.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbUsuario")
public class User extends PadraoModel{
	
	
    @Column
	private String nome;
	@Column
	private String email;
	@Column
	private String senha;
	@Column
	private String cpf;
	@Column
	private String idTipoUsuario;	
	@Column
	private boolean statusUsuario;
	
	
	public User(String username, String email2, String encode) {
		// TODO Auto-generated constructor stub
	}
	public User()
	{
		this.GenerateID();
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
	public String getIdTipoUsuario() {
		return idTipoUsuario;
	}
	public void setIdTipoUsuario(String idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}
	public boolean isStatusUsuario() {
		return statusUsuario;
	}
	public void setStatusUsuario(boolean statusUsuario) {
		this.statusUsuario = statusUsuario;
	}

    
}
