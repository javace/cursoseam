package com.loja.service;

import java.util.List;

import javax.persistence.EntityManager;

import com.loja.model.CategoriaProduto;

public interface CategoriaProdutoService {

	public  void load();

	public  void find(Integer id);

	public  void setEntityManager(EntityManager entityManager);

	public  void setProdutos(List<CategoriaProduto> categoriaProdutos);

	public  List<CategoriaProduto> getCategoriaProdutos();

	public  CategoriaProduto getCategoriaProduto();

	public  void setCategoriaProduto(CategoriaProduto categoriaProduto);

	public  void save();

	public  void delete();

	public  void editar();

}