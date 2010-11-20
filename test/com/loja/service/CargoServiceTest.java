package com.loja.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.loja.model.Cargo;
import com.loja.service.impl.CargoServiceImpl;

public class CargoServiceTest {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	private CargoService cargoService;
	
	private Cargo cargoSalvo;
	
	
	@BeforeClass
	public void init() {
		emf = Persistence.createEntityManagerFactory("pgPuTest");
		em = emf.createEntityManager();
		cargoService = new CargoServiceImpl();
		cargoService.setEntityManager(em);
		em.getTransaction().begin();
	}
	
	@AfterTest
	public void afterTest() {
		em.getTransaction().rollback();
		em.close();
	}
	
	public void record() {
		Cargo cargo = new Cargo();
		cargo.setNome("Gerente");
		cargoService.setCargo(cargo);
		cargoService.save();
		
		cargoSalvo = cargo;
	}
	
	@Test
	public void create() {
		this.record();
		cargoService.load();
		Assert.assertEquals(1, cargoService.getCargos().size());
	}
	
	@Test
	public void edit() {
		cargoService.find(cargoSalvo.getId());
		cargoService.editar();
		Assert.assertEquals("Gerente", cargoService.getCargo().getNome());
		cargoService.getCargo().setNome("Product Owner");
		cargoService.save();
		cargoService.find(cargoSalvo.getId());
		Assert.assertEquals("Product Owner", cargoService.getCargo().getNome());
	}
	
	@Test
	public void find() {
		cargoService.find(cargoSalvo.getId());
		Assert.assertEquals("Product Owner",cargoService.getCargo().getNome());
	}
	
	@Test
	public void remove() {
		cargoService.setCargo(cargoSalvo);
		cargoService.delete();
		cargoService.load();
		Assert.assertEquals(0,cargoService.getCargos().size());
	}
}
