package com.example.demo.models;

public class CategoriaProduto extends PadraoModel {

	private String categoria;
	
	public CategoriaProduto()
	{
		this.GenerateID();
	}
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	} 
}
