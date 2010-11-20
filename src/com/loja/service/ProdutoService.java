package com.loja.service;

import java.util.List;

import javax.persistence.EntityManager;

import com.loja.model.Produto;

public interface ProdutoService {

	public void load();

	public void find(Integer id);

	public void setEntityManager(EntityManager entityManager);

	public void setProdutos(List<Produto> produtos);

	public List<Produto> getProdutos();

	public Produto getProduto();

	public void setProduto(Produto produto);

	public void save();

	public void delete();

	public void editar();

}