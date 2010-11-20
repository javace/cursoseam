package com.loja.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.loja.model.Usuario;

public class UsuarioTest {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	private Integer idToInteract;
	
	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}
	
	@BeforeClass
	public void init() {
		emf = Persistence.createEntityManagerFactory("pgPuTest");
		em = getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
	}

	@AfterClass
	public void destroy() {
		em.getTransaction().rollback();
	}
	
	@Test
	public void create() {
		Usuario usuario = new Usuario();
		usuario.setEmail("r.oliveira@gmail.com");
		usuario.setHierarquia_id(1);
		usuario.setNome("Teste");
		usuario.setSenha("123");
		em.persist(usuario);
		idToInteract = usuario.getId();
		Assert.assertNotNull(usuario.getId());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void list() {
		List<Usuario> usuarios = em.createQuery("select u from Usuario as u").getResultList();
		Assert.assertNotNull(usuarios);
	}
	
	@Test
	public void findById() {
		Usuario usuario = em.find(Usuario.class, idToInteract);
		Assert.assertNotNull(usuario);
	}
	
	@Test
	public void edit() {
		Usuario usuario = em.find(Usuario.class, idToInteract);
		usuario.setEmail("teste@teste.com.br");
		em.persist(usuario);
		Assert.assertEquals("teste@teste.com.br", usuario.getEmail());
	}
	
	@Test
	public void remove() {
		Usuario usuario = em.find(Usuario.class, idToInteract);
		em.remove(usuario);
		usuario = em.find(Usuario.class, idToInteract);
		Assert.assertNull(usuario);
	}
}
