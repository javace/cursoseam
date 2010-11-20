package com.loja.service;

import java.util.List;

import javax.persistence.EntityManager;

import com.loja.model.Fornecedor;

public interface FornecedorService {

	public void load();

	public void find(Integer id);

	public void setEntityManager(EntityManager entityManager);

	public void setFornecedores(List<Fornecedor> fornecedores);

	public List<Fornecedor> getFornecedores();

	public Fornecedor getFornecedor();

	public void setFornecedor(Fornecedor fornecedor);

	public void save();

	public void delete();

	public void editar();

}