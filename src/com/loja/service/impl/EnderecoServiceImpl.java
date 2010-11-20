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

import com.loja.model.Endereco;
import com.loja.service.EnderecoService;
@Name("enderecoService")
@Scope(ScopeType.CONVERSATION)
public class EnderecoServiceImpl implements Serializable, EnderecoService {
	
private static final long serialVersionUID = 1L;
	
	@In
	private EntityManager entityManager;
	
	@DataModelSelection
	@Out(required=false)
	private Endereco endereco = new Endereco();
	
	@DataModel
	private List<Endereco> enderecos;
	
	@SuppressWarnings("unchecked")
	@Factory("enderecos")
	public void load() {
		setEnderecos(entityManager.createQuery("select e from Endereco as e").getResultList());
	}
	
	public void find(Integer id) {
		setEndereco(entityManager.find(Endereco.class, id));
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public void save() {
		if (endereco.getId() == null) {
			entityManager.persist(endereco);
		} else {
			entityManager.merge(endereco);
		}
		setEndereco(null);
		load();
	}
	
	public void delete() {
		entityManager.remove(endereco);
		setEndereco(null);
		load();
	}
	
	public void editar() {
		setEndereco(endereco);
	}
}
