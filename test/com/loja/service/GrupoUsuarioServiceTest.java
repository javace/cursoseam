package com.loja.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.loja.model.GrupoUsuario;
import com.loja.service.impl.GrupoUsuarioServiceImpl;


public class GrupoUsuarioServiceTest {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	private GrupoUsuarioService grupoUsuarioService;

	@BeforeClass
	public void init() {
		emf = Persistence.createEntityManagerFactory("pgPu");
		em = emf.createEntityManager();
		grupoUsuarioService = new GrupoUsuarioServiceImpl();
		grupoUsuarioService.setEntityManager(em);
		
	}

	@Test
	public void list() {
		grupoUsuarioService.load();
	}
	
	@Test
	public void save() {
		GrupoUsuario grupoUsuario = new GrupoUsuario();
		grupoUsuario.setAtivo(true);
		grupoUsuario.setNome("Administrativo");
		grupoUsuario.setRole("ADMINISTRATIVO");
		grupoUsuarioService.setGrupoUsuario(grupoUsuario);
		grupoUsuarioService.save();
		Assert.assertNotNull(grupoUsuarioService.getGrupoUsuario().getId());
		Assert.assertTrue(grupoUsuarioService.getGrupoUsuario().getAtivo());
	}
}
