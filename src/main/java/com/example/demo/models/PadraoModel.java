package com.example.demo.models;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PadraoModel {
	
	@Id
	private String Id;
	
	public void GenerateID()
	{
		UUID uuid = UUID.randomUUID();
		this.Id=uuid.toString();
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

}
