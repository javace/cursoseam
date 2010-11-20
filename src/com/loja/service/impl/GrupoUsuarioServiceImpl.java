package com.loja.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;

import com.loja.model.GrupoUsuario;
import com.loja.service.GrupoUsuarioService;
import com.loja.util.FacesUtil;

@Name("grupoUsuarioService")
@Scope(ScopeType.CONVERSATION)
public class GrupoUsuarioServiceImpl implements Serializable, GrupoUsuarioService {

	private static final long serialVersionUID = 1L;

	@In
	private EntityManager entityManager;

	@DataModel
	private List<GrupoUsuario> gruposUsuario;
	
	@DataModelSelection
	@Out(required=false)
	private GrupoUsuario grupoUsuario = new GrupoUsuario();
	
	@SuppressWarnings("unchecked")
	@Factory("gruposUsuario")
	public void load() {
		gruposUsuario = entityManager.createQuery("select u from GrupoUsuario as u").getResultList();
	}
	
	public void find(Integer id) {
		setGrupoUsuario(entityManager.find(GrupoUsuario.class, id));
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public List<GrupoUsuario> getGruposUsuario() {
		return gruposUsuario;
	}
	
	public void save() {
		if (getGrupoUsuario().getId() == null) {
			entityManager.persist(grupoUsuario);
		} else {
			entityManager.merge(grupoUsuario);
		}
		new FacesUtil().viewMessage(null, "commons.save", FacesMessage.SEVERITY_INFO);
		setGrupoUsuario(null);
		load();
	}
	
	public void editar() {
		setGrupoUsuario(grupoUsuario);
	}
	
	public void delete() {
		entityManager.remove(grupoUsuario);
		new FacesUtil().viewMessage(null, "commons.delete", FacesMessage.SEVERITY_INFO);
		setGrupoUsuario(null);
		load();
	}

	public GrupoUsuario getGrupoUsuario() {
		return this.grupoUsuario;
	}

	public void setGrupoUsuario(GrupoUsuario grupoUsuario) {
		this.grupoUsuario = grupoUsuario;
	}
}
