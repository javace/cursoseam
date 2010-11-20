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

import com.loja.model.Usuario;
import com.loja.service.UsuarioService;
import com.loja.util.FacesUtil;

@Name("usuarioService")
@Scope(ScopeType.CONVERSATION)
public class UsuarioServiceImpl implements Serializable, UsuarioService {

	private static final long serialVersionUID = 1L;
	
//	@In(create=true)
//	private MailUtil mailUtil;
	
	@In
	public EntityManager entityManager;

	@DataModel
	private List<Usuario> usuarios;
	
	@DataModelSelection
	@Out(required=false)
	private Usuario usuario = new Usuario();
	
	@SuppressWarnings("unchecked")
	@Factory("usuarios")
	public void load() {
		setUsuarios(entityManager.createQuery("select u from Usuario as u").getResultList());
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public List<Usuario> getUsuarios() {
		load();
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void find(Integer id) {
		setUsuario(entityManager.find(Usuario.class, id));
	}

	public void save() {
		getUsuario().senhaToMd5();
		if (getUsuario().getId() == null) {
			entityManager.persist(getUsuario());
		} else {
			entityManager.merge(getUsuario());
		}
		new FacesUtil().viewMessage(null, "commons.save", FacesMessage.SEVERITY_INFO);
//		mailUtil.sendNovo();
		setUsuario(null);
		load();
		
	}
	
	public void delete() {
		entityManager.remove(usuario);
		new FacesUtil().viewMessage(null, "commons.delete", FacesMessage.SEVERITY_INFO);
		setUsuario(null);
		
		load();
	}
	
	public void editar() {
		getUsuario().setSenha(null);
		setUsuario(usuario);
	}
}
