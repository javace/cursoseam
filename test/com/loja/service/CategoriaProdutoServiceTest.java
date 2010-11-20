package com.loja.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.loja.model.CategoriaProduto;
import com.loja.service.impl.CategoriaProdutoServiceImpl;

public class CategoriaProdutoServiceTest {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	private CategoriaProdutoService categoriaProdutoService;
	
	private CategoriaProduto categoriaProdutoSalva;
	
	
	@BeforeClass
	public void init() {
		emf = Persistence.createEntityManagerFactory("pgPuTest");
		em = emf.createEntityManager();
		em.getTransaction().begin();
		categoriaProdutoService = new CategoriaProdutoServiceImpl();
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
		categoriaProdutoSalva = categoriaProduto;
	}
	
	@Test
	public void create() {
		this.record();
		categoriaProdutoService.load();
		Assert.assertEquals(1, categoriaProdutoService.getCategoriaProdutos().size());
	}
	
	@Test
	public void edit() {
		categoriaProdutoService.setCategoriaProduto(categoriaProdutoSalva);
		categoriaProdutoService.editar();
		categoriaProdutoService.getCategoriaProduto().setNome("Categoria");
		categoriaProdutoService.save();
	}
	
	@Test
	public void find() {
		categoriaProdutoService.find(categoriaProdutoSalva.getId());
		Assert.assertEquals(categoriaProdutoService.getCategoriaProduto().getNome(), "Categoria");
	}
	
	@Test
	public void list() {
		categoriaProdutoService.load();
		Assert.assertEquals(categoriaProdutoService.getCategoriaProdutos().size(), 1);
	}
	
	@Test
	public void remove() {
		categoriaProdutoService.setCategoriaProduto(categoriaProdutoSalva);
		categoriaProdutoService.delete();
		categoriaProdutoService.load();
		Assert.assertEquals(categoriaProdutoService.getCategoriaProdutos().size(), 0);
	}
}
