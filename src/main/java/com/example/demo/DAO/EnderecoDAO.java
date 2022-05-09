package com.example.demo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.demo.models.EnderecoModel;

public class EnderecoDAO implements IRepositoryService<EnderecoModel> {

	
	private Connection connection;

	//Constructor
	public EnderecoDAO() throws Exception {
		
		this.connection = DbUtil.CreatesConnectionToMYSQL();
	}
	
	@Override
	public ArrayList<EnderecoModel> FillObjectsFromResultSet(ResultSet rs) throws Exception{
		ArrayList<EnderecoModel> uList = new ArrayList<EnderecoModel>();
	     if (!rs.next() ) {
         	this.connection.close();
         	return null;
         } 
         else {

             do {
	                	EnderecoModel endereco = new EnderecoModel();               
	                	endereco.setCep(rs.getString("CEP"));
	                	endereco.setCidade(rs.getString("CIDADE"));
	                	endereco.setComplemento(rs.getString("COMPLEMENTO"));
	                	endereco.setId(rs.getString("ID"));
	                	endereco.setNumero(rs.getInt("NUMERO"));
	                	endereco.setRua(rs.getString("RUA"));
	                	endereco.setStatus(rs.getBoolean("STATUSEND"));
	                	endereco.setIdUsuario(rs.getString("id_usuario"));
	                	
	                    uList.add(endereco);
             } while (rs.next());
         }
	     this.connection.close();
	     return uList;
	}

	
	@Override
	public void insert(EnderecoModel obj) throws Exception {
		
		connection.setAutoCommit(false);
		PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO tbenderecos (CEP, CIDADE, COMPLEMENTO, ID, NUMERO, RUA, STATUSEND) VALUES (?, ?, ?, ?, ?, ?, ?)");
        
        // Parameters start with 1
        preparedStatement.setString(1, obj.getCep());          
        preparedStatement.setString(2, obj.getCidade());
        preparedStatement.setString(3, obj.getComplemento());
        preparedStatement.setString(4, obj.getId());   
        preparedStatement.setInt(5, obj.getNumero());
        preparedStatement.setString(6, obj.getRua());
        preparedStatement.setBoolean(7, true);           
        preparedStatement.executeUpdate();

        PreparedStatement ps = connection
                .prepareStatement("INSERT INTO tbusuarioxenderecos(id_usuario, id_endereco) VALUES (?, ?)");
        
        ps.setString(1, obj.getIdUsuario());
        ps.setString(2, obj.getId());
        
        ps.executeUpdate();
        
        connection.commit();
        
        this.connection.close();
		
	}

	@Override
	public void update(EnderecoModel obj) throws Exception {
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
        preparedStatement.setString(1, obj.getCep());          
        preparedStatement.setString(2, obj.getCidade());
        preparedStatement.setString(3, obj.getComplemento());            
        preparedStatement.setInt(4, obj.getNumero());
        preparedStatement.setString(5, obj.getRua());
        preparedStatement.setBoolean(6, true);          
        preparedStatement.setString(7, obj.getId());
        preparedStatement.executeUpdate();    
        this.connection.close();
		
	}

	@Override
	public boolean delete(String id) throws Exception {
		
		PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE tbenderecos SET " 
                							+ " STATUSEND=? "
                                            + " WHERE ID=?");
        
		// Parameters start with 1
        // preparedStatement previne SQL Injection...
        preparedStatement.setBoolean(1, false);          
        preparedStatement.setString(2, id);
        preparedStatement.executeUpdate();    
        this.connection.close();
		return true;
		
	}

	@Override
	public EnderecoModel find(String id) throws Exception {
		EnderecoModel endereco = new EnderecoModel();
            
            // Busco os dados do endereco onde o usuario mora
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM n2_ecommerce.tbEnderecos a "
            		+ " inner join tbUsuarioxEnderecos b on a.id=b.id_endereco where a.Id=?"
            		+ " and statusEnd=?");
            
            ps.setString(1, id);
            ps.setBoolean(2, true);
            ResultSet newResultSet = ps.executeQuery();
            
            var data=this.FillObjectsFromResultSet(newResultSet);
            if(data==null)
            	return null;
            else
            	return data.get(0);

	}

	@Override
	public ArrayList<EnderecoModel> findAll() throws Exception {
		//Ajustar para enviar os dados de forma paginada, usar fun��o SQL "LIMIT" do MySQL
		
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM n2_ecommerce.tbEnderecos a "
        		+ " inner join tbUsuarioxEnderecos b on a.id=b.id_endereco"
        		+ " where statusEnd=?");
        
        ps.setBoolean(1, true);
        ResultSet newResultSet = ps.executeQuery();
                                                       
        return this.FillObjectsFromResultSet(newResultSet);
	}
	public ArrayList<EnderecoModel> findAllAdressUser(String idUser) throws Exception {
		
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM n2_ecommerce.tbEnderecos a "
        		+ " inner join tbUsuarioxEnderecos b on a.id=b.id_endereco WHERE "
        		+ " b.id_usuario=? and statusEnd=?");
		
		ps.setString(1, idUser);
        ps.setBoolean(2, true);
        ResultSet newResultSet = ps.executeQuery();
          
              
        return this.FillObjectsFromResultSet(newResultSet) ;
	}





}
