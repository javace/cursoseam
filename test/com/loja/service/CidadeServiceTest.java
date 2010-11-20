package com.loja.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.loja.model.Cidade;
import com.loja.model.Estado;
import com.loja.service.impl.CidadeServiceImpl;
import com.loja.service.impl.EnderecoServiceImpl;
import com.loja.service.impl.EstadoServiceImpl;

public class CidadeServiceTest {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	private EnderecoService enderecoService;
	private CidadeService cidadeService;
	private EstadoService estadoService;
	
	private Cidade cidadeSalvo;
	private Estado estadoSalvo;
	
	
	@BeforeClass
	public void init() {
		emf = Persistence.createEntityManagerFactory("pgPuTest");
		em = emf.createEntityManager();
		enderecoService = new EnderecoServiceImpl();
		cidadeService = new CidadeServiceImpl();
		estadoService = new EstadoServiceImpl();
		enderecoService.setEntityManager(em);
		cidadeService.setEntityManager(em);
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
		
		Cidade cidade = new Cidade();
		cidade.setEstado(estado);
		cidade.setNome("Anapolis");
		cidadeService.setCidade(cidade);
		cidadeService.save();
		
		cidadeSalvo = cidade;
		estadoSalvo = estado;
	}
	
	@Test
	public void create() {
		this.record();
		cidadeService.load();
		Assert.assertEquals(1, cidadeService.getCidades().size());
	}
	
	@Test
	public void find() {
		cidadeService.find(cidadeSalvo.getId());
		Assert.assertEquals("Goiania",cidadeService.getCidade().getNome());
	}
	
	@Test
	public void edit() {
		cidadeService.find(cidadeSalvo.getId());
		cidadeService.editar();
		Assert.assertEquals("Anapolis", cidadeService.getCidade().getNome());
		cidadeService.getCidade().setNome("Goiania");
		cidadeService.save();
		cidadeService.find(cidadeSalvo.getId());
		Assert.assertEquals("Goiania", cidadeService.getCidade().getNome());
	}
	
	@Test
	public void remove() {
		cidadeService.setCidade(cidadeSalvo);
		estadoService.setEstado(estadoSalvo);
		cidadeService.delete();
		estadoService.delete();
		cidadeService.load();
		Assert.assertEquals(0,cidadeService.getCidades().size());
	}
	
	@Test
	public void loadByEstado() {
		Estado estado = new Estado();
		estado.setNome("DF");
		estadoService.setEstado(estado);
		estadoService.save();
		
		Cidade cidade = new Cidade();
		cidade.setEstado(estado);
		cidade.setNome("Cidade de testelandia");
		cidadeService.setCidade(cidade);
		cidadeService.save();
		
		cidadeService.setEstado(estado);
		cidadeService.loadByEstado();
		List<Cidade> cidades = cidadeService.getCidades();
		Assert.assertEquals(cidades.size(), 1);
		cidadeService.setCidade(cidade);
		cidadeService.delete();
	}
}
