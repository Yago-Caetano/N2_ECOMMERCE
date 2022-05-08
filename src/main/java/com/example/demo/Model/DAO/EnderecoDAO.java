package com.example.demo.Model.DAO;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.Beans.*;
import com.example.demo.Model.DbUtil;
import com.example.demo.Model.*;


public class EnderecoDAO {
	
	private Connection connection;
	
	//Constructor
	public EnderecoDAO() throws Exception {
		this.connection = DbUtil.CreatesConnectionToMYSQL();
	}			
	
	public void delete(int id) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM tbenderecos WHERE ID=?");
			ps.setInt(1, id);
			ps.executeUpdate();
			
			PreparedStatement pst = connection.prepareStatement("DELETE FROM tbusuarioxenderecos WHERE id_endereco=?");
			pst.setInt(1, id);
			pst.executeUpdate();
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
			
	public void insert(Endereco e, int idUsuario) {
		
		try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO tbenderecos (CEP, CIDADE, COMPLEMENTO, ID, NUMERO, RUA, STATUSEND) VALUES (?, ?, ?, ?, ?, ?, ?)");
            
            // Parameters start with 1
            
            preparedStatement.setString(1, e.getCep());          
            preparedStatement.setString(2, e.getCidade());
            preparedStatement.setString(3, e.getComplemento());
            preparedStatement.setInt(4, e.getId());
            preparedStatement.setInt(5, e.getNumero());
            preparedStatement.setString(6, e.getRua());
            preparedStatement.setBoolean(7, e.isStatus());            
            preparedStatement.executeUpdate();

            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO tbusuarioxenderecos(id_usuario, id_endereco) VALUES (?, ?)");
            
            ps.setInt(1, idUsuario);
            ps.setInt(2, e.getId());
            
        } catch (SQLException erro) {
            erro.printStackTrace();
        }
		
	}

	public void update(Endereco e) {
		try {
			//Java 13 - text block -  """   """
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE tbenderecos SET CEP=?, "
                    							+ "CIDADE=?, "
                    							+ "COMPLEMENTO=?, "
                    							+ "NUMERO=?, "
                    							+ "RUA=?, "
                    							+ "STATUSEND=? "
                                                + "WHERE ID=?");
            
            // Parameters start with 1
            // preparedStatement previne SQL Injection...
            preparedStatement.setString(1, e.getCep());          
            preparedStatement.setString(2, e.getCidade());
            preparedStatement.setString(3, e.getComplemento());            
            preparedStatement.setInt(4, e.getNumero());
            preparedStatement.setString(5, e.getRua());
            preparedStatement.setBoolean(6, e.isStatus());          
            preparedStatement.setInt(7, e.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException erro) {
            erro.printStackTrace();
        }
		
	}

	// Passa o ID do usuário
	public Endereco find(int id) {

        Endereco endereco = new Endereco();
        
        // idEndereco é o ID do endereço obtido na tabela de relação enderecos e usuarios.
        // idEndereco comeca como -1 para evitar erro de compilação na linha 122.
        int idEndereco = -1;
    	try {
    		
    		// Busco o endereço do usuário pela tabela de relacao de usuarios e enderecos
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM n2_ecommerce.tbusuarioxenderecos WHERE id_usuario=?");
            
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
            	// Consegui o id do endereco onde o usuario mora
                idEndereco = rs.getInt("id_endereco");                
            }
            
            // Busco os dados do endereco onde o usuario mora
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tbenderecos WHERE ID=?");
            
            ps.setLong(1, idEndereco);
            ResultSet newResultSet = ps.executeQuery();
            
            if (rs.next()) {
            	endereco.setCep(newResultSet.getString("CEP"));
            	endereco.setCidade(newResultSet.getString("CIDADE"));
            	endereco.setComplemento(newResultSet.getString("COMPLEMENTO"));
            	endereco.setId(newResultSet.getInt("ID"));
            	endereco.setNumero(newResultSet.getInt("NUMERO"));
            	endereco.setRua(newResultSet.getString("RUA"));
            	endereco.setStatus(newResultSet.getBoolean("STATUSEND"));
            }
            
            return endereco;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } //try

        return endereco;
	}
}