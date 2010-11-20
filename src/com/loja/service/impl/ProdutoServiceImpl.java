package com.loja.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;

import com.loja.model.Produto;
import com.loja.service.ProdutoService;
@Name("produtoService")
@Scope(ScopeType.CONVERSATION)
public class ProdutoServiceImpl implements Serializable, ProdutoService {
	
private static final long serialVersionUID = 1L;
	
	@In
	private EntityManager entityManager;
	
	@DataModelSelection
	@Out(required=false)
	private Produto produto = new Produto();
	
	@DataModel
	private List<Produto> produtos;
	
	@SuppressWarnings("unchecked")
	@Factory("produtos")
	public void load() {
		setProdutos(entityManager.createQuery("select e from Produto as e").getResultList());
	}
	
	public void find(Integer id) {
		setProduto(entityManager.find(Produto.class, id));
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public void save() {
		if (produto.getId() == null) {
			entityManager.persist(produto);
		} else {
			entityManager.merge(produto);
		}
		setProduto(null);
		load();
	}
	
	public void delete() {
		entityManager.remove(produto);
		setProduto(null);
		load();
	}
	
	public void editar() {
		setProduto(produto);
	}
}
