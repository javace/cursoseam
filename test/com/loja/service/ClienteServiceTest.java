package com.loja.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.loja.model.Cidade;
import com.loja.model.Cliente;
import com.loja.model.Endereco;
import com.loja.model.Estado;
import com.loja.service.impl.CidadeServiceImpl;
import com.loja.service.impl.ClienteServiceImpl;
import com.loja.service.impl.EnderecoServiceImpl;
import com.loja.service.impl.EstadoServiceImpl;

public class ClienteServiceTest {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	private EnderecoService enderecoService;
	private CidadeService cidadeService;
	private EstadoService estadoService;
	private ClienteService clienteService;
	
	private Cliente clienteSalvo;
	private Cidade cidadeSalvo;
	private Estado estadoSalvo;
	
	
	@BeforeClass
	public void init() {
		emf = Persistence.createEntityManagerFactory("pgPuTest");
		em = emf.createEntityManager();
		enderecoService = new EnderecoServiceImpl();
		cidadeService = new CidadeServiceImpl();
		estadoService = new EstadoServiceImpl();
		clienteService = new ClienteServiceImpl();
		enderecoService.setEntityManager(em);
		cidadeService.setEntityManager(em);
		estadoService.setEntityManager(em);
		clienteService.setEntityManager(em);
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
		
		Cliente cliente = new Cliente();
		cliente.setNome("Jose das Candeias");
		cliente.setEndereco(endereco);
		clienteService.setCliente(cliente);
		clienteService.save();
		clienteSalvo = cliente;
		cidadeSalvo = cidade;
		estadoSalvo = estado;
	}
	
	@Test
	public void create() {
		this.record();
		clienteService.load();
		Assert.assertEquals(1, clienteService.getClientes().size());
	}
	
	@Test
	public void find() {
		clienteService.find(clienteSalvo.getId());
		Assert.assertEquals("Rua do Corrego",clienteService.getCliente().getEndereco().getLogradouro());
	}
	
	@Test
	public void edit() {
		clienteService.find(clienteSalvo.getId());
		clienteService.editar();
		Assert.assertEquals("Jose das Candeias", clienteService.getCliente().getNome());
		clienteService.getCliente().setNome("Jose Maria das Candeias");
		clienteService.save();
		clienteService.find(clienteSalvo.getId());
		Assert.assertEquals("Jose Maria das Candeias", clienteService.getCliente().getNome());
	}
	
	@Test
	public void remove() {
		clienteService.setCliente(clienteSalvo);
		cidadeService.setCidade(cidadeSalvo);
		estadoService.setEstado(estadoSalvo);
		clienteService.delete();
		cidadeService.delete();
		estadoService.delete();
		clienteService.load();
		Assert.assertEquals(0,clienteService.getClientes().size());
	}
}
