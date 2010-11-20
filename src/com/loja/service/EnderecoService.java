package com.loja.service;

import java.util.List;

import javax.persistence.EntityManager;

import com.loja.model.Endereco;

public interface EnderecoService {

	public void load();

	public void find(Integer id);

	public void setEntityManager(EntityManager entityManager);

	public void setEnderecos(List<Endereco> enderecos);

	public List<Endereco> getEnderecos();

	public Endereco getEndereco();

	public void setEndereco(Endereco endereco);

	public void save();

	public void delete();

	public void editar();

}