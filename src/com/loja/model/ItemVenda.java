package com.loja.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ItemVenda implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(cascade=CascadeType.ALL)
	private Produto produto;
	private BigDecimal qtde;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="venda_id")
	private Venda venda;
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setQtde(BigDecimal qtde) {
		this.qtde = qtde;
	}
	
	public BigDecimal getQtde() {
		return qtde;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Venda getVenda() {
		return venda;
	}
}
