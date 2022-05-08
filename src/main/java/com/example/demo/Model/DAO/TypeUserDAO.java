package com.example.demo.Model.DAO;

import com.example.demo.Model.IRepositoryService;
import com.example.demo.Beans.TypeUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.example.demo.Model.DbUtil;

// https://github.com/danielscarvalho/FTT-PEOPLE-WEB-DB/blob/master/src/br/edu/cefsa/ftt/ec/dao/PeopleDao.java

/*
 * TODO 1. DONE - Criar tabela no banco de dados MySQL
 * TODO 2. DONE - Criar JavaBean para cada tabela do banco de dados MySQL
 * TODO 3. DONE - Criar a conex�o com o Banco de Dados (connection) DbUtil
 * TODO 4. DONE - Criar a interface Dao - Design Pattern - SQL para objetos vice-versa
 * TODO 5. DONE - Criar o Dao para a tabela "Client"
 * TODO 6. Criar WEB API CRUD para a tabela "Client"
 * TODO 7. DONE - Criar pasta libs e importar driver JDBC para banco de dados (.jar)
 * TODO 8. DONE - Importar .jar do JDBC no Java Build Path
 * 
 * Para N1 2B LP3 - Criar 5 tabelas...
 * Testar com Postman
 * Equipe de 1, 2 ou 3 alunos
 * 
 * 
 */

public class TypeUserDAO  {
	
	private Connection connection;
	
	//Constructor
	public TypeUserDAO() throws Exception {
		this.connection = DbUtil.CreatesConnectionToMYSQL();
	}

	public void insert(TypeUser tipo) throws Exception {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO tbTipoUsuario (Tipo) VALUES (?)");
            
            // Parameters start with 1
            preparedStatement.setString(1, tipo.getTipo());
            preparedStatement.executeUpdate();
            
            this.connection.close();

	}

	public void update(TypeUser tipo) throws Exception {
		

			//Java 13 - text block -  """   """
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE tbTipoUsuario SET Tipo=?  "  
                                                + "WHERE id=?");
            
            // Parameters start with 1
            // preparedStatement previne SQL Injection...
            preparedStatement.setString(1, tipo.getTipo());
            preparedStatement.setInt(2, tipo.getId());          
            preparedStatement.executeUpdate();
            this.connection.close();
		
	} //update

	public void delete(TypeUser produto) throws Exception {
		
	} //delete 

	public TypeUser find(int id) throws Exception {
		
		TypeUser tipo = new TypeUser();

            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM n2_ecommerce.tbTipoUsuario WHERE id=?");
            
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
            	tipo.setId(rs.getInt("id"));
            	tipo.setTipo(rs.getString("Tipo"));
            }
            this.connection.close();

        return tipo;
		
	} //find
	
public TypeUser find(String tipoNome) throws Exception {
		
		TypeUser tipo = new TypeUser();
        
 
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM n2_ecommerce.tbTipoUsuario WHERE Tipo=?");
            
            preparedStatement.setString(1, tipoNome);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
            	tipo.setId(rs.getInt("id"));
            	tipo.setTipo(rs.getString("Tipo"));
            }
            this.connection.close();

        return tipo;
		
	} //find


	public ArrayList<TypeUser> findAll(TypeUser produto)throws Exception {
		//Ajustar para enviar os dados de forma paginada, usar fun��o SQL "LIMIT" do MySQL
		
		ArrayList<TypeUser> pList = new ArrayList<TypeUser>();
        

            Statement statement = connection.createStatement();
            
            ResultSet rs = statement.executeQuery("SELECT * FROM tbTipoUsuario");
            
            while (rs.next()) {
                
            	TypeUser p = new TypeUser();
                
            	p.setId(rs.getInt("id"));
                p.setTipo(rs.getString("Tipo"));
                pList.add(p);
                
            } //while
            this.connection.close();
            
        return pList;
		
	} //findAll
	
	public int count()throws Exception {
		
		int count = -1;
		

			
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT COUNT(1) QTD FROM tbTipoUsuario");
            
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                count = rs.getInt("QTD");
                
            } //if
            this.connection.close();
    

		return count;
		
	} //count
	
	public int maxId()throws Exception {
		
		int maxId = -1;
		
		
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT MAX(ID) MAX_ID FROM tbTipoUsuario");
            
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                maxId = rs.getInt("MAX_ID");
                
            } //if
            this.connection.close();

		return maxId;
		
	} //maxId

} //TypeUserDAO
