package com.loja.service.impl;

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

import com.loja.model.Fornecedor;
import com.loja.service.FornecedorService;

@Name("fornecedorService")
@Scope(ScopeType.CONVERSATION)
public class FornecedorServiceImpl implements FornecedorService {
	
	private static final long serialVersionUID = 1L;
	
	@In
	private EntityManager entityManager;
	
	@DataModelSelection
	@Out(required=false)
	private Fornecedor fornecedor = new Fornecedor();
	
	@DataModel
	private List<Fornecedor> fornecedores;
	
	@SuppressWarnings("unchecked")
	@Factory("fornecedores")
	public void load() {
		setFornecedores(entityManager.createQuery("select c from Fornecedor as c").getResultList());
	}
	
	public void find(Integer id) {
		setFornecedor(entityManager.find(Fornecedor.class, id));
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}
	
	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}
	
	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	public void save() {
		if (fornecedor.getId() == null) {
			entityManager.persist(fornecedor);
		} else {
			entityManager.merge(fornecedor);
		}
		setFornecedor(null);
		load();
	}
	
	public void delete() {
		entityManager.remove(fornecedor);
		setFornecedor(null);
		load();
	}
	
	public void editar() {
		setFornecedor(fornecedor);
	}

}
