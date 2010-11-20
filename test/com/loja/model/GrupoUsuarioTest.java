package com.loja.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.loja.model.GrupoUsuario;

public class GrupoUsuarioTest {
	
	private EntityManagerFactory emf;
	
	private EntityManager em;
	
	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}
	
	private Integer idToInteract;
	
	@BeforeClass
	public void init() {
		emf = Persistence.createEntityManagerFactory("pgPuTest");
		em = getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
	}

	@AfterClass
	public void destroy() {
		em.getTransaction().rollback();
		emf.close();
	}
	
	@Test
	public void create() {
		GrupoUsuario grupoUsuario = new GrupoUsuario();
		grupoUsuario.setAtivo(true);
		grupoUsuario.setNome("Grupo de zeladores");
		grupoUsuario.setRole("ROLE_ZELADOR");
		em.persist(grupoUsuario);
		idToInteract = grupoUsuario.getId();
		Assert.assertNotNull(grupoUsuario.getId());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void list() {
		System.out.println(idToInteract);
		List<GrupoUsuario> gruposUsuario = em.createQuery("select u from GrupoUsuario as u").getResultList();
		Assert.assertNotNull(gruposUsuario);
	}
	
	@Test
	public void findById() {
		GrupoUsuario grupoUsuario = em.find(GrupoUsuario.class, idToInteract);
		Assert.assertNotNull(grupoUsuario);
	}
	
	@Test
	public void edit() {
		GrupoUsuario grupoUsuario = em.find(GrupoUsuario.class, idToInteract);
		grupoUsuario.setNome("Zeladores");
		em.persist(grupoUsuario);
		grupoUsuario = em.find(GrupoUsuario.class, idToInteract);
		Assert.assertEquals("Zeladores", grupoUsuario.getNome());
	}
	
	@Test
	public void remove() {
		GrupoUsuario grupoUsuario = em.find(GrupoUsuario.class, idToInteract);
		System.out.println(grupoUsuario);
		em.remove(grupoUsuario);
		grupoUsuario = em.find(GrupoUsuario.class, idToInteract);
		Assert.assertNull(grupoUsuario);
	}
}
