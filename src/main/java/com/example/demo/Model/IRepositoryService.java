package com.example.demo.Model;

import java.util.ArrayList;

public interface IRepositoryService<DomainObject> {
	
	public  void insert(DomainObject obj);
	
	public  void update(DomainObject obj);
	
	public  void delete(DomainObject obj);
	
	public  DomainObject find(int id);
	
	public  ArrayList<DomainObject> findAll(DomainObject obj);
	
}