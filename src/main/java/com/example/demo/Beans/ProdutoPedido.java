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
public class ProdutoPedido {
	
	@Id
	@Basic
	private String idPedido;
	@Column
	@Basic
	private String idProduto;
	@Column
	@Basic
	private int quantidade;
	@Column
	@Basic
	private Double desconto;
	@Column
	@Basic
	private Double preco;
	
	public String getIdPedido() {
		return this.idPedido;
	}
	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}
	
	public String getIdProduto() {
		return this.idProduto;
	}
	public void setIdProduto(String idProduto) {
		this.idProduto = idProduto;
	}
	
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
	
	public Double getDesconto() {
		return this.desconto;
	}
	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}
	public void setDesconto(String desconto) {
		if (desconto == null) {
			setDesconto(0.00);
		} else {
			setDesconto(Double.valueOf(desconto));
		}
	}
			
	public Double getPreco() {
		return this.preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public void setPreco(String preco) {
		if (preco == null) {
			setPreco(0.00);
		} else {
			setPreco(Double.valueOf(preco));
		}
	}
}
