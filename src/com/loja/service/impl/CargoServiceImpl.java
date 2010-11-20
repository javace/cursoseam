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

import com.loja.model.Cargo;
import com.loja.service.CargoService;
@Name("cargoService")
@Scope(ScopeType.CONVERSATION)
public class CargoServiceImpl implements Serializable, CargoService {
	
private static final long serialVersionUID = 1L;
	
	@In
	private EntityManager entityManager;
	
	@DataModelSelection
	@Out(required=false)
	private Cargo cargo = new Cargo();
	
	@DataModel
	private List<Cargo> cargos;
	
	@SuppressWarnings("unchecked")
	@Factory("cargo")
	public void load() {
		setCargos(entityManager.createQuery("select e from Cargo as e").getResultList());
	}
	
	public void find(Integer id) {
		setCargo(entityManager.find(Cargo.class, id));
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}
	
	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	public void save() {
		if (cargo.getId() == null) {
			entityManager.persist(cargo);
		} else {
			entityManager.merge(cargo);
		}
		setCargo(null);
		load();
	}
	
	public void delete() {
		entityManager.remove(cargo);
		setCargo(null);
		load();
	}
	
	public void editar() {
		setCargo(cargo);
	}
}
