package com.example.demo.models;

import java.sql.Date;
import java.util.ArrayList;

public class OrdensModel extends PadraoModel{
	
	public OrdensModel()
	{
		this.GenerateID();
		this.produtos= new ArrayList<ProdutosModel>();
	}
	
	private Date Data_pedido;
	
	private String idStatus;
	
	private String idUsuario;
	
	private String idEndereco;
	
	private ArrayList<ProdutosModel> produtos;
	
	private StatusPedidoModel status;
	
	

	public StatusPedidoModel getStatus() {
		return status;
	}

	public void setStatus(StatusPedidoModel status) {
		this.status = status;
	}

	public Date getData_pedido() {
		return Data_pedido;
	}

	public void setData_pedido(Date data_pedido) {
		Data_pedido = data_pedido;
	}

	public String getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(String idStatus) {
		this.idStatus = idStatus;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(String idEndereco) {
		this.idEndereco = idEndereco;
	}

	public ArrayList<ProdutosModel> getProdutos() {
		return produtos;
	}

	public void setProdutos(ArrayList<ProdutosModel> produtos) {
		this.produtos = produtos;
	}
	
	
	

}
