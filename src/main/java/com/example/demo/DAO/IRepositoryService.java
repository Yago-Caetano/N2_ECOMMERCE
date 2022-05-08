package com.example.demo.DAO;

import java.util.ArrayList;

public interface IRepositoryService<DomainObject> {
	
	public  void insert(DomainObject obj);	
	
	public  void update(DomainObject obj);
	
	public  void delete(int id);
	
	public  DomainObject find(int id);
	
	public  ArrayList<DomainObject> findAll();
	
}