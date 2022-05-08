package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import com.example.demo.models.PadraoModel;


@Entity
public class TypeUser extends PadraoModel  {

	@Column
	private String tipo;
	
	public TypeUser()
	{
		this.GenerateID();
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


}
