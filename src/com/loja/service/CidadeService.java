package com.loja.service;

import java.util.List;

import javax.persistence.EntityManager;

import com.loja.model.Cidade;
import com.loja.model.Estado;

public interface CidadeService {

	public  void load();

	public  void loadByEstado();

	public  void find(Integer id);

	public  void setEntityManager(EntityManager entityManager);

	public  void setCidades(List<Cidade> cidades);

	public  List<Cidade> getCidades();

	public  Cidade getCidade();

	public  void setCidade(Cidade cidade);

	public  void setEstado(Estado estado);

	public  Estado getEstado();

	public  void save();

	public  void delete();

	public  void editar();

}