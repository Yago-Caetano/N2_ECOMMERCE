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
public class Pedido {
	
	@Id
	@Basic
	private int id;
	@Column
	@Basic
	private int idStatus;
	@Column
	@Basic
	private int idUsuario;
	@Column
	@Basic
	private int idEndereco;
	@Column
	@Basic
	private Date dataPedido;
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id) {
		if (id == null) {
			setId(0);
		} else {
		 setId(Integer.valueOf(id));
		}
	}
	
	public int getIdStatus() {
		return this.idStatus;
	}
	public void setIdStatus(int idStatus) {
		this.idStatus = idStatus;
	}
	public void setIdStatus(String idStatus) {
		if (idStatus == null) {
			setIdStatus(0);
		} else {
			setIdStatus(Integer.valueOf(idStatus));
		}
	}
	
	public int getIdUsuario() {
		return this.idEndereco;
	}
	public void setIdEndereco(int idEndereco) {
		this.idEndereco = idEndereco;
	}
	public void setIdEndereco(String idEndereco) {
		if (idEndereco == null) {
			setIdEndereco(0);
		} else {
			setIdEndereco(Integer.valueOf(idEndereco));
		}
	}
	
	public int getIdEndereco() {
		return this.idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		if (idUsuario == null) {
			setIdUsuario(0);
		} else {
			setIdUsuario(Integer.valueOf(idUsuario));
		}
	}
	
	public Date getDataPedido() {
		return this.dataPedido;
	}
	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}
	public void setDataPedido(String dataPedido) {
		try { //ConversÃ£o de data pode dar erro!!!
			setDataPedido(brDateFormat(dataPedido));
		} catch (Exception e) {
			e.printStackTrace();
		} //FunÃ§Ã£o brDateFormat definida abaixo...
	}
	
	private Date brDateFormat(String data) throws Exception { 
 		if (data == null || data.equals(""))
 			return null;
 		
         java.sql.Date date = null;
         
         try {
             DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
             date = new java.sql.Date( ((java.util.Date)formatter.parse(data)).getTime() );
         } catch (ParseException e) {            
             throw e;
         }
         return date;
 	}
}
