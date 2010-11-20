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

import com.loja.model.CategoriaProduto;
import com.loja.service.CategoriaProdutoService;

@Name("categoriaProdutoService")
@Scope(ScopeType.CONVERSATION)
public class CategoriaProdutoServiceImpl implements Serializable, CategoriaProdutoService {
	
private static final long serialVersionUID = 1L;
	
	@In
	private EntityManager entityManager;
	
	@DataModelSelection
	@Out(required=false)
	private CategoriaProduto categoriaProduto = new CategoriaProduto();
	
	@DataModel
	private List<CategoriaProduto> categoriaProdutos;
	
	@SuppressWarnings("unchecked")
	@Factory("categoriaProdutos")
	public void load() {
		setProdutos(entityManager.createQuery("select e from CategoriaProduto as e").getResultList());
	}
	
	public void find(Integer id) {
		setCategoriaProduto(entityManager.find(CategoriaProduto.class, id));
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void setProdutos(List<CategoriaProduto> categoriaProdutos) {
		this.categoriaProdutos = categoriaProdutos;
	}

	public List<CategoriaProduto> getCategoriaProdutos() {
		return categoriaProdutos;
	}
	
	public CategoriaProduto getCategoriaProduto() {
		return categoriaProduto;
	}

	public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
		this.categoriaProduto = categoriaProduto;
	}
	
	public void save() {
		if (categoriaProduto.getId() == null) {
			entityManager.persist(categoriaProduto);
		} else {
			entityManager.merge(categoriaProduto);
		}
		setCategoriaProduto(null);
		load();
	}
	
	public void delete() {
		entityManager.remove(categoriaProduto);
		setCategoriaProduto(null);
		load();
	}
	
	public void editar() {
		setCategoriaProduto(categoriaProduto);
	}
}
