package com.loja.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.loja.model.Cidade;
import com.loja.model.Endereco;
import com.loja.model.Estado;
import com.loja.service.impl.CidadeServiceImpl;
import com.loja.service.impl.EnderecoServiceImpl;
import com.loja.service.impl.EstadoServiceImpl;

public class EnderecoServiceTest {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	private EnderecoService enderecoService;
	private CidadeService cidadeService;
	private EstadoService estadoService;
	
	private Endereco enderecoSalvo;
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
		
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua do Corrego");
		endereco.setNumero("1000");
		endereco.setComplemento("102B");
		endereco.setCep("60125-070");
		endereco.setCidade(cidade);
		enderecoService.setEndereco(endereco);
		enderecoService.save();
		
		enderecoSalvo = endereco;
		cidadeSalvo = cidade;
		estadoSalvo = estado;
	}
	
	@Test
	public void create() {
		this.record();
		enderecoService.load();
		Assert.assertEquals(1, enderecoService.getEnderecos().size());
	}
	
	@Test
	public void edit() {
		enderecoService.find(enderecoSalvo.getId());
		enderecoService.editar();
		Assert.assertEquals("102B",enderecoService.getEndereco().getComplemento());
		enderecoService.getEndereco().setComplemento("102B");
		enderecoService.save();
		enderecoService.find(enderecoSalvo.getId());
		Assert.assertEquals("102B",enderecoService.getEndereco().getComplemento());
	}
	
	@Test
	public void find() {
		enderecoService.find(enderecoSalvo.getId());
		Assert.assertEquals("Rua do Corrego",enderecoService.getEndereco().getLogradouro());
	}
	
	@Test
	public void remove() {
		enderecoService.setEndereco(enderecoSalvo);
		cidadeService.setCidade(cidadeSalvo);
		estadoService.setEstado(estadoSalvo);
		enderecoService.delete();
		cidadeService.delete();
		estadoService.delete();
		enderecoService.load();
		Assert.assertEquals(0,enderecoService.getEnderecos().size());
	}
}