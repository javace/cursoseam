package com.loja.service;

import java.util.List;

import javax.persistence.EntityManager;

import com.loja.model.Estado;

public interface EstadoService {

	public void load();

	public void setEntityManager(EntityManager entityManager);

	public void setEstados(List<Estado> estados);

	public List<Estado> getEstados();

	public Estado getEstado();

	public void setEstado(Estado estado);

	public void save();

	public void delete();

	public void editar();

	public void find(Integer id);

}