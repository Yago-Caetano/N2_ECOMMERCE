package com.example.demo.DAO;

import java.util.ArrayList;

public interface IRepositoryService<DomainObject> {
	
	public  void insert(DomainObject obj) throws Exception;	
	
	public  void update(DomainObject obj)throws Exception;
	
	public  void delete(String id)throws Exception;
	
	public  DomainObject find(String id)throws Exception;
	
	public  ArrayList<DomainObject> findAll()throws Exception;
	
}