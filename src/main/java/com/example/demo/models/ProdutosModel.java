package com.example.demo.models;

public class ProdutosModel extends PadraoModel {

	private String nome;

	private Double preco;

	private String descricao;

	private byte[] foto;

	private int quantidade;

	private Double desconto;
	
	private String idCategoria;
	
	private CategoriaProduto categoria;
	
	public ProdutosModel()
	{
		this.GenerateID();
	}


	public CategoriaProduto getCategoria() {
		return categoria;
	}


	public void setCategoria(CategoriaProduto categoria) {
		this.categoria = categoria;
	}


	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Double getPreco() {
		return this.preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public void setPreco(String preco) {
		if (preco == null) {
			setPreco(0.0);
		} else {
			setPreco(Double.valueOf(preco));
		} 
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public byte[] getFoto() {
		return this.foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	public void setFoto(String foto) {
		if (foto == null) {
			this.foto = null;
		} else {
			this.foto = foto.getBytes();
		}
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
			setDesconto(0.0);
		} else {
			setDesconto(Double.valueOf(desconto));
		} 
	}
	public String getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(String idCategoria) {
		this.idCategoria = idCategoria;
	}		
	



}
