package com.example.demo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.demo.models.OrdensModel;
import com.example.demo.models.ProdutosModel;

public class OrdensDAO implements IRepositoryService<OrdensModel> {

	private Connection connection;

	//Constructor
	public OrdensDAO() throws Exception {
		this.connection = DbUtil.CreatesConnectionToMYSQL();
	}
	@Override
	public ArrayList<OrdensModel> FillObjectsFromResultSet(ResultSet rs) throws Exception {
		
		ArrayList<OrdensModel> pList = new ArrayList<OrdensModel>();
		  if (!rs.next() ) {
      	this.connection.close();
      	return null;
      } else {

          do {
        	  OrdensModel p = new OrdensModel();
	                
	            	p.setId(rs.getString("id"));
	                p.setData_pedido(rs.getDate("Data_pedido"));
	                p.setIdStatus(rs.getString("idStatus"));
	                p.setIdUsuario(rs.getString("idUsuario"));
	                p.setIdEndereco(rs.getString("idEndereco"));                
	                pList.add(p);
          } while (rs.next());
      }
      
      this.connection.close();
		return pList;
	}

	@Override
	public void insert(OrdensModel obj) throws Exception {
		
		connection.setAutoCommit(false);
		PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO tbPedidos (Id,idStatus,idUsuario,idEndereco) "
                		+ "VALUES (?,?,?,?)");
        
        // Parameters start with 1
        preparedStatement.setString(1, obj.getId());
        preparedStatement.setString(2, obj.getIdStatus());
        preparedStatement.setString(3, obj.getIdUsuario());
        preparedStatement.setString(4, obj.getIdEndereco());
        preparedStatement.executeUpdate();
        
        PreparedStatement ps;
        for(ProdutosModel prod:obj.getProdutos())
        {
            ps = connection
                    .prepareStatement("INSERT INTO tbPedidosxProdutos "
                    		+ "(idPedido,idProduto,Quantidade,Desconto,Preco) "
                    		+ "VALUES (?,?,?,?,?)");
            
            ps.setString(1, obj.getId());
            ps.setString(2, prod.getId());
            ps.setInt(3, prod.getQuantidade());
            ps.setDouble(4, prod.getDesconto());
            ps.setDouble(5, prod.getPreco());
            ps.executeUpdate();
        }

        
        connection.commit();
        this.connection.close();
		
	}
	public void insertNewItensOrder(OrdensModel obj) throws Exception {
		
		connection.setAutoCommit(false);
		
        PreparedStatement ps;
        for(ProdutosModel prod:obj.getProdutos())
        {
            ps = connection
                    .prepareStatement("INSERT INTO tbPedidosxProdutos "
                    		+ "(idPedido,idProduto,Quantidade,Desconto,Preco) "
                    		+ "VALUES (?,?,?,?,?)");
            
            ps.setString(1, obj.getId());
            ps.setString(2, prod.getId());
            ps.setInt(3, prod.getQuantidade());
            ps.setDouble(4, prod.getDesconto());
            ps.setDouble(5, prod.getPreco());
            ps.executeUpdate();
        }

        
        connection.commit();
        this.connection.close();
		
	}
	public void UpdateItensOrder(OrdensModel obj) throws Exception {
		
		connection.setAutoCommit(false);
		
        PreparedStatement ps;
        for(ProdutosModel prod:obj.getProdutos())
        {
            ps = connection
                    .prepareStatement("UPDATE tbPedidosxProdutos "
                    		+ " SET Quantidade=?,Desconto=?,Preco=? "
                    		+ " WHERE idPedido=? AND idProduto=? ");

            ps.setInt(1, prod.getQuantidade());
            ps.setDouble(2, prod.getDesconto());
            ps.setDouble(3, prod.getPreco());
            ps.setString(4, obj.getId());
            ps.setString(5, prod.getId());
            ps.executeUpdate();
        }

        
        connection.commit();
        this.connection.close();
		
	}
	
	public ArrayList<ProdutosModel> FinalizarOrder(OrdensModel obj) throws Exception {
		
		ArrayList<ProdutosModel> pList = new ArrayList<ProdutosModel>();
		
		connection.setAutoCommit(false);
		
        PreparedStatement ps;
        for(ProdutosModel prod:obj.getProdutos())
        {
        	int estoque=0;
            ps = connection
                    .prepareStatement("Select Quantidade from tbProdutos where id=?");
            ps.setString(1, prod.getId());
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
            	estoque=rs.getInt("Quantidade");                      
            
            if(estoque>=prod.getQuantidade())
            {
            	ps = connection
                        .prepareStatement("Update tbProdutos Set Quantidade=? where id=?");
                ps.setInt(1, estoque-prod.getQuantidade());
                ps.setString(2, prod.getId());
                ps.executeUpdate();
            }
            else
            {
            	pList.add(prod);
            	connection.rollback();
            	this.connection.close();
            	return pList;
            }
            
        }
        ps = connection
                .prepareStatement("Update tbPedidos Set idStatus=? where id=? ");
        
        ps.setString(1, "aiwujhac1235");
        ps.setString(2, obj.getId());
        ps.executeUpdate();
        connection.commit();
        this.connection.close();
        return pList;
		
	}

	@Override
	public void update(OrdensModel obj) throws Exception {
		
		 PreparedStatement preparedStatement = connection
                 .prepareStatement("UPDATE tbPedidos SET idStatus=?, "
                 		+ " idUsuario=?, "
                 		+ " idEndereco=? "                		 
                        + " WHERE id=?");
         
         // Parameters start with 1
         // preparedStatement previne SQL Injection...
         preparedStatement.setString(1, obj.getIdStatus());
         preparedStatement.setString(2, obj.getIdUsuario());   
         preparedStatement.setString(3, obj.getIdEndereco()); 
         preparedStatement.setString(4, obj.getId()); 
         preparedStatement.executeUpdate();
         this.connection.close();
		
	}
	
	public void DeleteItensOrder(OrdensModel obj) throws Exception {
		
		connection.setAutoCommit(false);
		
        PreparedStatement ps;
        for(ProdutosModel prod:obj.getProdutos())
        {
            ps = connection
                    .prepareStatement("Delete from tbPedidosxProdutos "
                    		+ " WHERE idPedido=? AND idProduto=?  " );
            
            ps.setString(1, obj.getId());
            ps.setString(2, prod.getId());
            ps.executeUpdate();
        }

        
        connection.commit();
        this.connection.close();
		
	}

	@Override
	public boolean delete(String id) throws Exception {
		 PreparedStatement preparedStatement = connection
                 .prepareStatement("UPDATE tbPedidos SET idStatus='auihdbayuwidh2131313' where id=?");
         
         // Parameters start with 1
         // preparedStatement previne SQL Injection...
         preparedStatement.setString(1, id);
         preparedStatement.executeUpdate();
         this.connection.close();
         
         return false;
		
	}

	@Override
	public OrdensModel find(String id) throws Exception {
		
	       PreparedStatement preparedStatement = connection.
	                prepareStatement("SELECT * FROM n2_ecommerce.tbPedidos WHERE id=?");
	        
	        preparedStatement.setString(1, id);
	        ResultSet rs = preparedStatement.executeQuery();
	        
	        var data =this.FillObjectsFromResultSet(rs);
	        
	        if (data==null)
	        	return null;
	        else
	        	return data.get(0);
	}
	
	

	@Override
	public ArrayList<OrdensModel> findAll() throws Exception {
		
		Statement statement = connection.createStatement();
        
		ResultSet rs = statement.executeQuery("SELECT * FROM tbPedidos");
      
		return this.FillObjectsFromResultSet(rs);
	}
	
	public ArrayList<OrdensModel> findAllByStatus(String idStatus) throws Exception {
		
	       PreparedStatement preparedStatement = connection.
	                prepareStatement("SELECT * FROM n2_ecommerce.tbPedidos WHERE idStatus=?");
	        
	        preparedStatement.setString(1, idStatus);
	        ResultSet rs = preparedStatement.executeQuery();
	            
		return this.FillObjectsFromResultSet(rs);
	}
	
	public ArrayList<OrdensModel> findAllByUser(String idUser) throws Exception {
		
	       PreparedStatement preparedStatement = connection.
	                prepareStatement("SELECT * FROM n2_ecommerce.tbPedidos WHERE idUsuario=?");
	        
	        preparedStatement.setString(1, idUser);
	        ResultSet rs = preparedStatement.executeQuery();
	            
		return this.FillObjectsFromResultSet(rs);
	}
	
	public ArrayList<OrdensModel> findAllByUserAndStatus(String idUser, String idStatus) throws Exception {
		
	       PreparedStatement preparedStatement = connection.
	                prepareStatement("SELECT * FROM n2_ecommerce.tbPedidos WHERE idUsuario=? and "
	                		+ " idStatus=?");
	        
	        preparedStatement.setString(1, idUser);
	        preparedStatement.setString(2, idStatus);
	        ResultSet rs = preparedStatement.executeQuery();
	            
		return this.FillObjectsFromResultSet(rs);
	}
	
	public ArrayList<ProdutosModel> findAllProdutos(String idOrdem) throws Exception {
		
	     PreparedStatement preparedStatement = connection.
	                prepareStatement("select a.id, a.Nome,b.Preco,"
	        				+ " a.Descricao, a.Foto,b.Quantidade,b.Desconto,a.idCategoria"
	        				+ "  from tbProdutos a inner join tbPedidosxProdutos "
	        				+ "b on "
	        				+ "a.id=b.idProduto where b.idPedido=?");
	     
	       preparedStatement.setString(1, idOrdem);
	     ResultSet rs = preparedStatement.executeQuery();

		
		ProdutosDAO dao = new ProdutosDAO();
      
		return dao.FillObjectsFromResultSet(rs);
	}

}
