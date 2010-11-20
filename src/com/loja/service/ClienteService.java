package com.loja.service;

import java.util.List;

import javax.persistence.EntityManager;

import com.loja.model.Cliente;

public interface ClienteService {

	public void load();

	public void find(Integer id);

	public void setEntityManager(EntityManager entityManager);

	public void setClientes(List<Cliente> clientes);

	public List<Cliente> getClientes();

	public Cliente getCliente();

	public void setCliente(Cliente cliente);

	public void save();

	public void delete();

	public void editar();

}