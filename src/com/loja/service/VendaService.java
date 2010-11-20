package com.loja.service;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.End;

import com.loja.model.ItemVenda;
import com.loja.model.Produto;
import com.loja.model.Venda;
import com.loja.model.exception.VendaEstoqueInsuficienteException;
import com.loja.model.exception.VendaEstoqueMinimoAtingidoException;

public interface VendaService {

	public void load();

	public void find(Integer id);

	public void setEntityManager(EntityManager entityManager);

	public void setVendas(List<Venda> vendas);

	public List<Venda> getVendas();

	public Venda getVenda();

	public void setVenda(Venda venda);

	public void setProduto(Produto produto);

	public Produto getProduto();

	public void setSenha(String senha);

	public String getSenha();

	public void setQuantidade(BigDecimal quantidade);

	public BigDecimal getQuantidade();

	public List<ItemVenda> getItensVenda();

	@End
	public void save() throws VendaEstoqueInsuficienteException,
			VendaEstoqueMinimoAtingidoException;

	public void delete();

	public void editar();

	public void adicionar();

}