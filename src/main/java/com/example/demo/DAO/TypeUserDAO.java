package com.example.demo.DAO;
import com.example.demo.models.TypeUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TypeUserDAO implements IRepositoryService<TypeUser>  {
	
	private Connection connection;
	
	//Constructor
	public TypeUserDAO() throws Exception {
		this.connection = DbUtil.CreatesConnectionToMYSQL();
	}

	@Override
	public void insert(TypeUser obj) throws Exception {
		PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO tbTipoUsuario (Id,Tipo) VALUES (?,?)");
        
        // Parameters start with 1
        preparedStatement.setString(1, obj.getId());
        preparedStatement.setString(2, obj.getTipo());
        preparedStatement.executeUpdate();
        
        this.connection.close();
		
	}

	@Override
	public void update(TypeUser obj) throws Exception {
		 PreparedStatement preparedStatement = connection
                 .prepareStatement("UPDATE tbTipoUsuario SET Tipo=?  "  
                                             + "WHERE id=?");
         
         // Parameters start with 1
         // preparedStatement previne SQL Injection...
         preparedStatement.setString(1, obj.getTipo());
         preparedStatement.setString(2, obj.getId());          
         preparedStatement.executeUpdate();
         this.connection.close();
		
	}

	@Override
	public boolean delete(String id) throws Exception {
		return false;
		
	}

	@Override
	public TypeUser find(String id) throws Exception {
		
        PreparedStatement preparedStatement = connection.
                prepareStatement("SELECT * FROM n2_ecommerce.tbTipoUsuario WHERE id=?");
        
        preparedStatement.setString(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        
        var data =this.FillObjectsFromResultSet(rs);
        
        if (data==null)
        	return null;
        else
        	return data.get(0);

	}
	
	
	@Override
	public ArrayList<TypeUser> findAll() throws Exception {
		//Ajustar para enviar os dados de forma paginada, usar fun��o SQL "LIMIT" do MySQL
		Statement statement = connection.createStatement();
		            
		ResultSet rs = statement.executeQuery("SELECT * FROM tbTipoUsuario");
      
		return this.FillObjectsFromResultSet(rs);
	}

	@Override
	public ArrayList<TypeUser> FillObjectsFromResultSet(ResultSet rs) throws Exception {
		
		ArrayList<TypeUser> pList = new ArrayList<TypeUser>();
		  if (!rs.next() ) {
          	this.connection.close();
          	return null;
          } else {

              do {
              	TypeUser p = new TypeUser();
	                
	            	p.setId(rs.getString("id"));
	                p.setTipo(rs.getString("Tipo"));
	                pList.add(p);
              } while (rs.next());
          }
          
          this.connection.close();
		return pList;
	}

	

} //TypeUserDAO
