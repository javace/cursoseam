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

import com.loja.model.Cidade;
import com.loja.model.Colaborador;
import com.loja.model.Endereco;
import com.loja.service.ColaboradorService;
@Name("colaboradorService")
@Scope(ScopeType.CONVERSATION)
public class ColaboradorServiceImpl implements Serializable, ColaboradorService {
	
private static final long serialVersionUID = 1L;
	
	@In
	private EntityManager entityManager;
	
	@DataModelSelection
	@Out(required=false)
	private Colaborador colaborador = new Colaborador();
	
	public ColaboradorServiceImpl() {
		colaborador.setEndereco(new Endereco());
		colaborador.getEndereco().setCidade(new Cidade());
	}

	@DataModel
	private List<Colaborador> colaboradores;
	
	@SuppressWarnings("unchecked")
	@Factory("colaboradores")
	public void load() {
		setColaboradores(entityManager.createQuery("select e from Colaborador as e").getResultList());
	}
	
	public void find(Integer id) {
		setColaborador(entityManager.find(Colaborador.class, id));
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void setColaboradores(List<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}

	public List<Colaborador> getColaboradores() {
		return colaboradores;
	}
	
	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}
	
	public String save() {
		if (colaborador.getId() == null) {
			entityManager.persist(colaborador.getEndereco());
			entityManager.persist(colaborador);
		} else {
			entityManager.merge(colaborador);
		}
		setColaborador(null);
		load();
		return "";
	}
	
	public void delete() {
		entityManager.remove(colaborador);
		setColaborador(null);
		load();
	}
	
	public void editar() {
		setColaborador(colaborador);
	}
}
