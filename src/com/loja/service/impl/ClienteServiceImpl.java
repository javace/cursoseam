package com.loja.service.impl;

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

import com.loja.model.Cliente;
import com.loja.service.ClienteService;

@Name("clienteService")
@Scope(ScopeType.CONVERSATION)
public class ClienteServiceImpl implements ClienteService {
	
	private static final long serialVersionUID = 1L;
	
	@In
	private EntityManager entityManager;
	
	@DataModelSelection
	@Out(required=false)
	private Cliente cliente = new Cliente();
	
	@DataModel
	private List<Cliente> clientes;
	
	@SuppressWarnings("unchecked")
	@Factory("clientes")
	public void load() {
		setClientes(entityManager.createQuery("select c from Cliente as c").getResultList());
	}
	
	public void find(Integer id) {
		setCliente(entityManager.find(Cliente.class, id));
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	public List<Cliente> getClientes() {
		return clientes;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void save() {
		if (cliente.getId() == null) {
			entityManager.persist(cliente);
		} else {
			entityManager.merge(cliente);
		}
		setCliente(null);
		load();
	}
	
	public void delete() {
		entityManager.remove(cliente);
		setCliente(null);
		load();
	}
	
	public void editar() {
		setCliente(cliente);
	}

}
