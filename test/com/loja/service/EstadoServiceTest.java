package com.loja.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.loja.model.Estado;
import com.loja.service.impl.EstadoServiceImpl;

public class EstadoServiceTest {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	private EstadoService estadoService;
	
	private Estado estadoSalvo;
	
	
	@BeforeClass
	public void init() {
		emf = Persistence.createEntityManagerFactory("pgPuTest");
		em = emf.createEntityManager();
		estadoService = new EstadoServiceImpl();
		estadoService.setEntityManager(em);
		em.getTransaction().begin();
	}
	
	@AfterTest
	public void afterTest() {
		em.getTransaction().rollback();
		em.close();
	}
	
	public void record() {
		Estado estado = new Estado();
		estado.setNome("GO");
		estadoService.setEstado(estado);
		estadoService.save();
		
		estadoSalvo = estado;
	}
	
	@Test
	public void create() {
		this.record();
		estadoService.load();
		Assert.assertEquals(1, estadoService.getEstados().size());
	}
	
	@Test
	public void find() {
		estadoService.find(estadoSalvo.getId());
		Assert.assertEquals("TO",estadoService.getEstado().getNome());
	}
	
	@Test
	public void edit() {
		estadoService.find(estadoSalvo.getId());
		estadoService.editar();
		Assert.assertEquals("GO", estadoService.getEstado().getNome());
		estadoService.getEstado().setNome("TO");
		estadoService.save();
		estadoService.find(estadoSalvo.getId());
		Assert.assertEquals("TO", estadoService.getEstado().getNome());
	}
	
	@Test
	public void remove() {
		estadoService.setEstado(estadoSalvo);
		estadoService.delete();
		estadoService.load();
		Assert.assertEquals(0,estadoService.getEstados().size());
	}
}
