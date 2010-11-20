package com.loja.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.loja.model.GrupoUsuario;
import com.loja.model.Usuario;
import com.loja.service.impl.GrupoUsuarioServiceImpl;
import com.loja.service.impl.UsuarioServiceImpl;

public class UsuarioServiceTest {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	private UsuarioService usuarioService;
	private GrupoUsuarioService grupoUsuarioService;
	
	private Usuario usuarioSalvo;

	@BeforeClass
	public void init() {
		emf = Persistence.createEntityManagerFactory("pgPuTest");
		em = emf.createEntityManager();
		usuarioService = new UsuarioServiceImpl();
		usuarioService.setEntityManager(em);
		grupoUsuarioService = new GrupoUsuarioServiceImpl();
		grupoUsuarioService.setEntityManager(em);
		em.getTransaction().begin();
	}
	
	@AfterClass
	public void destroy() {
		em.getTransaction().rollback();
		em.close();
	}

	@Test
	public void create() {
		GrupoUsuario grupoUsuario = new GrupoUsuario();
		grupoUsuario.setAtivo(true);
		grupoUsuario.setNome("Administrativo");
		grupoUsuario.setRole("ADMINISTRATIVO");
		grupoUsuarioService.setGrupoUsuario(grupoUsuario);
		grupoUsuarioService.save();
		
		Usuario usuario = new Usuario();
		usuario.setEmail("joao@joao.com.br");
		usuario.setGruposUsuarios(grupoUsuario);
		usuario.setHierarquia_id(1);
		usuario.setNome("Joao");
		usuario.setSenha("123456");
		usuarioService.setUsuario(usuario);
		usuarioService.save();
		usuarioSalvo = usuario;
		usuarioService.load();
		Assert.assertEquals(1,usuarioService.getUsuarios().size());
	}
	
	@Test
	public void find() {
		usuarioService.load();
		usuarioService.find(usuarioSalvo.getId());
		Assert.assertEquals("Joao", usuarioService.getUsuario().getNome());
	}
	
	@Test
	public void edit() {
		usuarioService.find(usuarioSalvo.getId());
		usuarioService.editar();
		usuarioService.getUsuario().setSenha("123456");
		Assert.assertEquals("joao@joao.com.br", usuarioService.getUsuario().getEmail());
		usuarioService.getUsuario().setEmail("joao@maria.com.br");
		usuarioService.save();
		usuarioService.find(usuarioSalvo.getId());
		Assert.assertEquals("joao@maria.com.br", usuarioService.getUsuario().getEmail());
	}
	
	@Test
	public void list() {
		usuarioService.load();
		Assert.assertNotNull(usuarioService.getUsuarios());
	}
	
	@Test
	public void remove() {
		usuarioService.setUsuario(usuarioSalvo);
		usuarioService.delete();
	}
}
