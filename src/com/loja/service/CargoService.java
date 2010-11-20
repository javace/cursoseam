package com.loja.service;

import java.util.List;

import javax.persistence.EntityManager;

import com.loja.model.Cargo;

public interface CargoService {

	public  void load();

	public  void find(Integer id);

	public  void setEntityManager(EntityManager entityManager);

	public  void setCargos(List<Cargo> cargos);

	public  List<Cargo> getCargos();

	public  Cargo getCargo();

	public  void setCargo(Cargo cargo);

	public  void save();

	public  void delete();

	public  void editar();

}