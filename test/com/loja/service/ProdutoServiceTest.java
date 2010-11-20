package com.loja.service;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.loja.model.CategoriaProduto;
import com.loja.model.Produto;
import com.loja.service.impl.CategoriaProdutoServiceImpl;
import com.loja.service.impl.ProdutoServiceImpl;

public class ProdutoServiceTest {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	private ProdutoService produtoService;
	private CategoriaProdutoService categoriaProdutoService;
	
	private Produto produtoSalvo;
	
	
	@BeforeClass
	public void init() {
		emf = Persistence.createEntityManagerFactory("pgPuTest");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		produtoService = new ProdutoServiceImpl();
		categoriaProdutoService = new CategoriaProdutoServiceImpl();
		produtoService.setEntityManager(em);
		categoriaProdutoService.setEntityManager(em);
	}
	
	@AfterTest
	public void afterTest() {
		em.getTransaction().rollback();
		em.close();
	}
	
	public void record() {
		CategoriaProduto categoriaProduto = new CategoriaProduto();
		categoriaProduto.setNome("Laticinios");
		categoriaProdutoService.setCategoriaProduto(categoriaProduto);
		categoriaProdutoService.save();
		
		Produto produto = new Produto();
		produto.setNome("Bomba de gás");
		produto.setCategoriaProduto(categoriaProduto);
		produto.setEstoque(new BigDecimal(2.00));
		produto.setEstoqueMinimo(new BigDecimal(1.00));
		produto.setEstoqueMaximo(new BigDecimal(7.00));
		produtoService.setProduto(produto);
		produtoService.save();
		produtoSalvo = produto;
	}
	
	@Test
	public void create() {
		this.record();
		produtoService.load();
		Assert.assertEquals(1, produtoService.getProdutos().size());
	}
	
	@Test
	public void edit() {
		produtoService.setProduto(produtoSalvo);
		produtoService.editar();
		produtoService.getProduto().setNome("Bomba de gasolina");
		produtoService.save();
		produtoService.find(produtoSalvo.getId());
		Assert.assertEquals(produtoService.getProduto().getNome(), "Bomba de gasolina");
	}
	
	@Test
	public void find() {
		produtoService.find(produtoSalvo.getId());
		Assert.assertEquals(produtoService.getProduto().getNome(), "Bomba de gasolina");
	}
	
	@Test
	public void list() {
		produtoService.load();
		Assert.assertEquals(produtoService.getProdutos().size(), 1);
	}
	
	@Test
	public void remove() {
		produtoService.setProduto(produtoSalvo);
		produtoService.delete();
		produtoService.load();
		Assert.assertEquals(produtoService.getProdutos().size(), 0);
	}
}
