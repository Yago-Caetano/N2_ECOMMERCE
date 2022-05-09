package com.example.demo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.demo.models.StatusPedidoModel;


public class StatusPedidoDAO implements IRepositoryService<StatusPedidoModel> {

	private Connection connection;

	//Constructor
	public StatusPedidoDAO() throws Exception {
		this.connection = DbUtil.CreatesConnectionToMYSQL();
	}
	
	@Override
	public ArrayList<StatusPedidoModel> FillObjectsFromResultSet(ResultSet rs) throws Exception {
		
		ArrayList<StatusPedidoModel> pList = new ArrayList<StatusPedidoModel>();
		  if (!rs.next() ) {
        	this.connection.close();
        	return null;
        } else {

            do {
            	StatusPedidoModel p = new StatusPedidoModel();
	                
	            	p.setId(rs.getString("id"));
	                p.setPedidoStatus(rs.getString("PedidoStatus"));
	                pList.add(p);
            } while (rs.next());
        }
        
        this.connection.close();
		return pList;
	}
	

	@Override
	public void insert(StatusPedidoModel obj) throws Exception {
		
		PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO tbStatusPedido (Id,PedidoStatus) VALUES (?,?)");
        
        // Parameters start with 1
        preparedStatement.setString(1, obj.getId());
        preparedStatement.setString(2, obj.getPedidoStatus());
        preparedStatement.executeUpdate();
        
        this.connection.close();
		
	}

	@Override
	public void update(StatusPedidoModel obj) throws Exception {
		
		 PreparedStatement preparedStatement = connection
                 .prepareStatement("UPDATE tbStatusPedido SET PedidoStatus=?  "  
                                             + "WHERE id=?");
         
         // Parameters start with 1
         // preparedStatement previne SQL Injection...
         preparedStatement.setString(1, obj.getPedidoStatus());
         preparedStatement.setString(2, obj.getId());          
         preparedStatement.executeUpdate();
         this.connection.close();
		
	}

	@Override
	public void delete(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public StatusPedidoModel find(String id) throws Exception {
		
        PreparedStatement preparedStatement = connection.
                prepareStatement("SELECT * FROM n2_ecommerce.tbStatusPedido WHERE id=?");
        
        preparedStatement.setString(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        
        var data =this.FillObjectsFromResultSet(rs);
        
        if (data==null)
        	return null;
        else
        	return data.get(0);
	}

	@Override
	public ArrayList<StatusPedidoModel> findAll() throws Exception {
		
		Statement statement = connection.createStatement();
        
		ResultSet rs = statement.executeQuery("SELECT * FROM tbStatusPedido");
      
		return this.FillObjectsFromResultSet(rs);
	}

}
