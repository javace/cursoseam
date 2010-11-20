package com.loja.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;

import com.loja.model.ItemVenda;
import com.loja.model.Produto;
import com.loja.model.Venda;
import com.loja.model.exception.VendaEstoqueInsuficienteException;
import com.loja.model.exception.VendaEstoqueMinimoAtingidoException;
import com.loja.service.VendaService;
@Name("vendaService")
@Scope(ScopeType.CONVERSATION)
public class VendaServiceImpl implements Serializable, VendaService {
	
private static final long serialVersionUID = 1L;
	
	@In
	private EntityManager entityManager;
	
	@DataModelSelection
	@Out(required=false)
	private Venda venda = new Venda();
	
	@DataModel
	private List<Venda> vendas;
	
	private Produto produto;
	private BigDecimal quantidade;
	private String senha;
	private List<ItemVenda> itensVenda = new ArrayList<ItemVenda>();
	
	@SuppressWarnings("unchecked")
	@Factory("vendas")
	public void load() {
		setVendas(entityManager.createQuery("select e from Venda as e").getResultList());
	}
	
	public void find(Integer id) {
		setVenda(entityManager.find(Venda.class, id));
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	public List<Venda> getVendas() {
		return vendas;
	}
	
	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Produto getProduto() {
		return produto;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha() {
		return senha;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	@End
	public void save() throws VendaEstoqueInsuficienteException, VendaEstoqueMinimoAtingidoException {
			venda.setData(new Date());
			for (ItemVenda itemVenda : this.itensVenda) {
				venda.adicionaProduto(itemVenda);
			}
			entityManager.persist(venda);
			setVenda(new Venda());
			setQuantidade(null);
			load();
	}
	
	public void delete() {
		entityManager.remove(venda);
		setVenda(null);
		load();
	}
	
	public void editar() {
		setVenda(venda);
	}

	public void adicionar() {
		ItemVenda itemVenda = new ItemVenda();
		itemVenda.setProduto(getProduto());
		itemVenda.setQtde(getQuantidade());
		itensVenda.add(itemVenda);
	}
}
