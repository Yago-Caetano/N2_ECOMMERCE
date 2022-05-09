package com.example.demo.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public interface IRepositoryService<DomainObject> {
	
	public ArrayList<DomainObject> FillObjectsFromResultSet(ResultSet rs) throws Exception;
	
	public  void insert(DomainObject obj) throws Exception;	
	
	public  void update(DomainObject obj)throws Exception;
	
	public  boolean delete(String id)throws Exception;
	
	public  DomainObject find(String id)throws Exception;
	
	public  ArrayList<DomainObject> findAll()throws Exception;
	

	
}