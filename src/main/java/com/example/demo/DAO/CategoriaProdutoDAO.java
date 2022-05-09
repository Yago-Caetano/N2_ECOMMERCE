package com.example.demo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.example.demo.models.CategoriaProduto;
import com.example.demo.models.TypeUser;

public class CategoriaProdutoDAO implements IRepositoryService<CategoriaProduto> {

	private Connection connection;

	//Constructor
	public CategoriaProdutoDAO() throws Exception {
		this.connection = DbUtil.CreatesConnectionToMYSQL();
	}
	@Override
	public ArrayList<CategoriaProduto> FillObjectsFromResultSet(ResultSet rs) throws Exception {
		
		ArrayList<CategoriaProduto> pList = new ArrayList<CategoriaProduto>();
		  if (!rs.next() ) {
        	this.connection.close();
        	return null;
        } else {

            do {
            	CategoriaProduto p = new CategoriaProduto();
	                
	            	p.setId(rs.getString("id"));
	                p.setCategoria(rs.getString("Categoria"));
	                pList.add(p);
            } while (rs.next());
        }
        
        this.connection.close();
		return pList;
	}

	@Override
	public void insert(CategoriaProduto obj) throws Exception {
		PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO tbcategoriaprodutos (ID, CATEGORIA) VALUES (?, ?)");
        
        // Parameters start with 1
        preparedStatement.setString(1, obj.getId());
        preparedStatement.setString(2, obj.getCategoria());          
        
        preparedStatement.executeUpdate();
        this.connection.close();
		
	}

	@Override
	public void update(CategoriaProduto obj) throws Exception {
		//Java 13 - text block -  """   """
        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE tbcategoriaprodutos SET CATEGORIA=? "                     		                           
                                            + "WHERE ID=?");
        
        // Parameters start with 1
        // preparedStatement previne SQL Injection...
        preparedStatement.setString(1, obj.getCategoria());
        preparedStatement.setString(2, obj.getId());        
        
        preparedStatement.executeUpdate();
        this.connection.close();
		
	}

	@Override
	public void delete(String id) throws Exception {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM tbcategoriaprodutos WHERE ID=?");
		ps.setString(1, id);
		ps.executeUpdate();
		
	}

	@Override
	public CategoriaProduto find(String id) throws Exception {
		PreparedStatement preparedStatement = connection.
                prepareStatement("SELECT * FROM n2_ecommerce.tbcategoriaprodutos WHERE ID=?");
        
        preparedStatement.setString(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        
        var data=this.FillObjectsFromResultSet(rs);
        if(data==null)
        	return null;
        else
        	return data.get(0);
        
	}

	@Override
	public ArrayList<CategoriaProduto> findAll() throws Exception {
		
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM tbcategoriaprodutos");
		ResultSet rs = ps.executeQuery();

        return this.FillObjectsFromResultSet(rs);
	}

}
