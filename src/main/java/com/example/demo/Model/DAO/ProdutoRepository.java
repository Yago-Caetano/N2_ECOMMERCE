package com.example.demo.Model.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.Beans.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer>{
	
} 