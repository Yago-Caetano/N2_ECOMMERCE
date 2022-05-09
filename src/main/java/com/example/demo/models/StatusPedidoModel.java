package com.example.demo.models;

public class StatusPedidoModel extends PadraoModel {
	
	public StatusPedidoModel()
	{
		this.GenerateID();
	}
	
	private String pedidoStatus;

	public String getPedidoStatus() {
		return pedidoStatus;
	}

	public void setPedidoStatus(String pedidoStatus) {
		this.pedidoStatus = pedidoStatus;
	}
	
	

}
