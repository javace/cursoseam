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

import com.loja.model.Estado;
import com.loja.service.EstadoService;
@Name("estadoService")
@Scope(ScopeType.CONVERSATION)
public class EstadoServiceImpl implements Serializable, EstadoService {
	
	private static final long serialVersionUID = 1L;
	
	@In
	private EntityManager entityManager;
	
	@DataModelSelection
	@Out(required=false)
	private Estado estado = new Estado();
	
	@DataModel
	private List<Estado> estados;
	
	@SuppressWarnings("unchecked")
	@Factory("estados")
	public void load() {
		setEstados(entityManager.createQuery("select e from Estado as e").getResultList());
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Estado> getEstados() {
		return estados;
	}
	
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public void save() {
		if (estado.getId() == null) {
			entityManager.persist(estado);
		} else {
			entityManager.merge(estado);
		}
		setEstado(null);
		load();
	}
	
	public void delete() {
		entityManager.remove(estado);
		setEstado(null);
		load();
	}
	
	public void editar() {
		setEstado(estado);
	}

	public void find(Integer id) {
		setEstado(entityManager.find(Estado.class, id));
	}
}
