package com.loja.security;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.loja.model.GrupoUsuario;
import com.loja.model.Usuario;
import com.loja.security.Authenticator;

public class AuthenticatorTest {
	
	private EntityManagerFactory emf;
	
	private EntityManager em;
	
	private Credentials credentials = new Credentials();
	
	private Identity identity = new Identity();
	
	@Test
	public void create() {
		GrupoUsuario grupoUsuario = new GrupoUsuario();
		grupoUsuario.setAtivo(true);
		grupoUsuario.setNome("Administrativo");
		grupoUsuario.setRole("ADMINISTRATIVO");
		em.persist(grupoUsuario);
		
		Authenticator authenticator = new Authenticator();
		Usuario usuario = new Usuario();
		usuario.setEmail("r.oliveira@gmail.com");
		usuario.setHierarquia_id(1);
		usuario.setGruposUsuarios(grupoUsuario);
		usuario.setNome("Teste");
		usuario.setSenha(authenticator.toHash("123456"));
		em.persist(usuario);
		Assert.assertNotNull(usuario.getId());
	}
	
	@Test
	public void userAuthenticate() {
		Authenticator authenticator = new Authenticator();
		credentials.setUsername("r.oliveira@gmail.com");
		credentials.setPassword("123456");
		authenticator.setEntityManager(em);
		authenticator.setCredentials(credentials);
		authenticator.setIdentity(identity);
		boolean retorno = false;
		try {
			retorno = authenticator.authenticate();
		} catch (Exception e) {
			System.out.println("pau");
		}
		Assert.assertTrue(retorno);
	}
	
	@Test
	public void authenticateError() {
		Authenticator authenticator = new Authenticator();
		credentials.setUsername("rodrigo.dealer@gmail.com");
		credentials.setPassword("123457");
		authenticator.setEntityManager(em);
		authenticator.setCredentials(credentials);
		authenticator.setIdentity(identity);
		try {
			authenticator.authenticate();
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), "Usuario ou senha invalidos");
		}
	}
	
	@Test
	public void hashTest() {
		Authenticator authenticator = new Authenticator();
		Assert.assertEquals(authenticator.toHash("123"), "202cb962ac59075b964b07152d234b70");
	}
	
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
		emf.close();
		em.getTransaction().rollback();
	}

}
