package com.loja.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.loja.model.exception.VendaEstoqueInsuficienteException;
import com.loja.model.exception.VendaEstoqueMinimoAtingidoException;

@Entity
public class Venda implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Date data;
	@ManyToOne
	private Colaborador colaborador;
	@ManyToOne 
	private Cliente cliente;
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="venda_id")
	private List<ItemVenda> itensVenda = new ArrayList<ItemVenda>();
	

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getData() {
		return data;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}

	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}
	
	public void adicionaProduto(ItemVenda itemVenda) throws VendaEstoqueInsuficienteException, VendaEstoqueMinimoAtingidoException {
		if (itemVenda.getQtde().compareTo(itemVenda.getProduto().getEstoque()) > 0) {
			throw new VendaEstoqueInsuficienteException();
		}
		
		if (itemVenda.getProduto().getEstoque().subtract(itemVenda.getQtde()).compareTo(itemVenda.getProduto().getEstoqueMinimo()) < 0) {
			throw new VendaEstoqueMinimoAtingidoException();
		}
		
		BigDecimal estoque = itemVenda.getProduto().getEstoque();
		Produto produto = itemVenda.getProduto();
		produto.setEstoque(estoque.subtract(itemVenda.getQtde()));
		
		this.itensVenda.add(itemVenda);
		itemVenda.setVenda(this);
	}
	
	public Produto debitaEstoque(ItemVenda itemVenda) {
		BigDecimal estoque = itemVenda.getProduto().getEstoque();
		Produto produto = itemVenda.getProduto();
		produto.setEstoque(estoque.subtract(itemVenda.getQtde()));
		return produto;
	}
}
