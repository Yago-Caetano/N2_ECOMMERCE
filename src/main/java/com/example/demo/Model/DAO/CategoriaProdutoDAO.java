package com.example.demo.Model.DAO;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.Beans.CategoriaProduto;
import com.example.demo.Beans.Produto;
import com.example.demo.Model.DbUtil;
import com.example.demo.Model.*;


public class CategoriaProdutoDAO implements IRepositoryService<CategoriaProduto> {
	
	private Connection connection;
	
	//Constructor
	public CategoriaProdutoDAO() throws Exception {
		this.connection = DbUtil.CreatesConnectionToMYSQL();
	}	
	
	@Override
	public void insert(CategoriaProduto categoria) {
		
		try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO tbcategoriaprodutos (ID, CATEGORIA) VALUES (?, ?)");
            
            // Parameters start with 1
            preparedStatement.setInt(1, categoria.getId());
            preparedStatement.setString(2, categoria.getCategoria());          
            
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void update(CategoriaProduto categoria) {
		
		try {
			//Java 13 - text block -  """   """
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE tbcategoriaprodutos SET CATEGORIA=? "                     		                           
                                                + "WHERE ID=?");
            
            // Parameters start with 1
            // preparedStatement previne SQL Injection...
            preparedStatement.setString(1, categoria.getCategoria());
            preparedStatement.setInt(2, categoria.getId());        
            
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	@Override
	public CategoriaProduto find(int id) {

        CategoriaProduto cp = new CategoriaProduto();
        
    	try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM n2_ecommerce.tbcategoriaprodutos WHERE ID=?");
            
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                cp.setId(rs.getInt("ID"));
                cp.setCategoria(rs.getString("CATEGORIA"));
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } //try

        return cp;
	}

	@Override
	public void delete(int id) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM tbcategoriaprodutos WHERE ID=?");
			ps.setInt(1, id);
			ps.executeUpdate();
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public ArrayList<CategoriaProduto> findAll() {
		ArrayList<CategoriaProduto> cp = new ArrayList<CategoriaProduto>();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM tbcategoriaprodutos");
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				CategoriaProduto c = new CategoriaProduto();
				c.setId(rs.getInt("ID"));
				c.setCategoria(rs.getString("CATEGORIA"));
				cp.add(c);
			}
			return cp;
		}
		
		catch (SQLException e) {
			e.printStackTrace();			
		}
		return cp;
	}
}