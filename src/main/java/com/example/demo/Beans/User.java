package com.example.demo.Beans;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

import antlr.collections.List;

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
	private LocalDate nascimento;
	@Column
	@Basic
	private String email;
	@Column
	@Basic
	private String senha ;
	@Column
	@Basic
	private String cpf;
	@Column
	@Basic
	private int idTipoUsuario;	
	@Column
	@Basic
	private boolean statusUsuario;
	
    public User(String username, String password, String roles, String permissions){
        this.email = username;
        this.senha = password;
        this.roles = roles;
        this.permissions = permissions;
        this.active = 1;
    }
    
    protected User(){}
    
	private int active;
	
    private String roles = "";

    private String permissions = "";
    
    
    public int getActive() {
        return active;
    }

    public String getRoles() {
        return roles;
    }
    
    public String getPermissions() {
        return permissions;
    }

    public java.util.List<String> getRoleList(){
        if(this.roles.length() > 0){
            return  Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public java.util.List<String> getPermissionList(){
        if(this.permissions.length() > 0){
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }
	
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
	public LocalDate getNascimento() {
		return nascimento;
	}
	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}

	

}