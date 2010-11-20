package com.loja.service;

import java.util.List;

import javax.persistence.EntityManager;

import com.loja.model.Colaborador;

public interface ColaboradorService {

	public void load();

	public void find(Integer id);

	public void setEntityManager(EntityManager entityManager);

	public void setColaboradores(List<Colaborador> colaboradores);

	public List<Colaborador> getColaboradores();

	public Colaborador getColaborador();

	public void setColaborador(Colaborador colaborador);

	public String save();

	public void delete();

	public void editar();

}