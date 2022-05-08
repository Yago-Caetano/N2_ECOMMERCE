package com.example.demo.Beans;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Carrinho {
	
	@Id
	@Basic
	private int quantidade ;
	@Column
	@Basic
	private String produtoId;
	@Column
	@Basic
	private String nome;
	@Column
	@Basic
	private String imagemEmBase64;
	
	public int getQuantidade() {
		return this.quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public void setQuantidade(String quantidade) {
		if (quantidade == null) {
			setQuantidade(0);
		} else {
			setQuantidade(Integer.valueOf(quantidade));
		}
	}
	
	public String getProdutoId() {
		return this.produtoId;
	}
	public void setProdutoId(String produtoId) {
		this.produtoId = produtoId;
	}
	public void setProdutoId(int produtoId) {
		setProdutoId(String.valueOf(produtoId));

	}
	
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String geImagemEmBase64() {
		return this.imagemEmBase64;
	}
	public void setImagemEmBase64(String imagemEmBase64) {
		this.imagemEmBase64 = imagemEmBase64;
	}
}
