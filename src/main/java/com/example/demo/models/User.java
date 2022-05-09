package com.example.demo.models;


import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


public class User extends PadraoModel{
	

	private String nome;

	private String email;

	private String senha;

	private String cpf;

	private String idTipoUsuario;	

	private boolean statusUsuario;
	
	private TypeUser tipoUsuario;
	
	private ArrayList<EnderecoModel> enderecos;
	
	public TypeUser getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(TypeUser tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	public ArrayList<EnderecoModel> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(ArrayList<EnderecoModel> enderecos) {
		this.enderecos = enderecos;
	}

	public User()
	{
		this.GenerateID();
		this.enderecos = new ArrayList<EnderecoModel>();
		this.tipoUsuario= new TypeUser();
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
