package com.example.demo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.example.demo.models.ProdutosModel;

public class ProdutosDAO implements IRepositoryService<ProdutosModel> {

	private Connection connection;

	//Constructor
	public ProdutosDAO() throws Exception {
		this.connection = DbUtil.CreatesConnectionToMYSQL();
	}
	@Override
	public ArrayList<ProdutosModel> FillObjectsFromResultSet(ResultSet rs) throws Exception {
		
		ArrayList<ProdutosModel> pList = new ArrayList<ProdutosModel>();
		  if (!rs.next() ) {
      	this.connection.close();
      	return null;
      } else {

          do {
        	  ProdutosModel p = new ProdutosModel();
              p.setId(rs.getString("ID"));
              p.setNome(rs.getString("NOME"));
              p.setPreco(rs.getDouble("PRECO"));
              p.setDescricao(rs.getString("DESCRICAO"));
              p.setFoto(rs.getBytes("FOTO"));
              p.setQuantidade(rs.getInt("QUANTIDADE"));               
              p.setDesconto(rs.getDouble("DESCONTO"));              
              p.setIdCategoria(rs.getString("IDCATEGORIA"));

	          pList.add(p);
	          
          } while (rs.next());
      }
      
      this.connection.close();
		return pList;
	}

	@Override
	public void insert(ProdutosModel obj) throws Exception {
		PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO tbprodutos (NOME, PRECO, DESCRICAO, FOTO, "
                		+ "QUANTIDADE, DESCONTO, "
                		+ "IDCATEGORIA, ID) VALUES (?, ?, ?, ?, ?, ?, ?,?)");
        
        // Parameters start with 1
        preparedStatement.setString(1, obj.getNome());
        preparedStatement.setDouble(2, obj.getPreco());
        preparedStatement.setString(3, obj.getDescricao());
        preparedStatement.setBytes(4, obj.getFoto());
        preparedStatement.setInt(5, obj.getQuantidade());
        preparedStatement.setDouble(6, obj.getDesconto());          
        preparedStatement.setString(7, obj.getIdCategoria());      
        preparedStatement.setString(8, obj.getId());    
        preparedStatement.executeUpdate();
        this.connection.close();	
	}

	@Override
	public void update(ProdutosModel obj) throws Exception {
		//Java 13 - text block -  """   """
        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE tbprodutos SET NOME=?, " 
                		                           + "PRECO=?, "
                		                           + "DESCRICAO=?, "
                		                           + "FOTO=?, "
                		                           + "QUANTIDADE=?, "
                		                           + "DESCONTO=?, " 
                		                           + "IDCATEGORIA=? "  
                                            + "WHERE ID=?");
        
        // Parameters start with 1
        // preparedStatement previne SQL Injection...
        preparedStatement.setString(1, obj.getNome());
        preparedStatement.setDouble(2, obj.getPreco());
        preparedStatement.setString(3, obj.getDescricao());
        preparedStatement.setBytes(4, obj.getFoto());
        preparedStatement.setInt(5, obj.getQuantidade());
        preparedStatement.setDouble(6, obj.getDesconto());  
        preparedStatement.setString(7, obj.getIdCategoria());

        preparedStatement.setString(8, obj.getId());
        
        preparedStatement.executeUpdate();
        
        this.connection.close();
		
	}

	@Override
	public boolean delete(String id) throws Exception {
		
		PreparedStatement preparedStatement = connection
                .prepareStatement("DELETE FROM tbprodutos WHERE ID=?");
        
        // Parameters start with 1
        preparedStatement.setString(1, id);
        preparedStatement.executeUpdate();
        
        this.connection.close();
        return false;
		
	}

	@Override
	public ProdutosModel find(String id) throws Exception {
		PreparedStatement preparedStatement = connection.
                prepareStatement("SELECT * FROM n2_ecommerce.tbprodutos WHERE ID=?");
        
        preparedStatement.setString(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        
        var data= this.FillObjectsFromResultSet(rs);
        if(data==null)
        	return null;
        else
        	return data.get(0);

	}

	@Override
	public ArrayList<ProdutosModel> findAll() throws Exception {
		
		PreparedStatement preparedStatement = connection.
                prepareStatement("SELECT * FROM tbprodutos");
		
		ResultSet rs = preparedStatement.executeQuery();
		
		return this.FillObjectsFromResultSet(rs);
        
	}

}
