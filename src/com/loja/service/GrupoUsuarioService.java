package com.loja.service;

import java.util.List;

import javax.persistence.EntityManager;

import com.loja.model.GrupoUsuario;

public interface GrupoUsuarioService {

	public void load();

	public void find(Integer id);

	public void setEntityManager(EntityManager entityManager);

	public List<GrupoUsuario> getGruposUsuario();

	public void save();

	public void editar();

	public void delete();

	public GrupoUsuario getGrupoUsuario();

	public void setGrupoUsuario(GrupoUsuario grupoUsuario);

}