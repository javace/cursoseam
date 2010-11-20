package com.loja.service;

import java.util.List;

import javax.persistence.EntityManager;

import com.loja.model.Usuario;

public interface UsuarioService {

	public void load();

	public void setEntityManager(EntityManager entityManager);

	public List<Usuario> getUsuarios();

	public void setUsuarios(List<Usuario> usuarios);

	public Usuario getUsuario();

	public void setUsuario(Usuario usuario);

	public void find(Integer id);

	public void save();

	public void delete();

	public void editar();

}