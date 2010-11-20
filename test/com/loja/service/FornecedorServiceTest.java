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
import com.loja.model.Fornecedor;
import com.loja.service.impl.CidadeServiceImpl;
import com.loja.service.impl.EnderecoServiceImpl;
import com.loja.service.impl.EstadoServiceImpl;
import com.loja.service.impl.FornecedorServiceImpl;

public class FornecedorServiceTest {

	private EntityManagerFactory emf;
	private EntityManager em;
	private EnderecoService enderecoService;
	private FornecedorService fornecedorService;
	private CidadeService cidadeService;
	private EstadoService estadoService;

	private Fornecedor fornecedorSalvo;
	private Estado estadoSalvo;
	private Cidade cidadeSalvo;

	@BeforeClass
	public void init() {
		emf = Persistence.createEntityManagerFactory("pgPuTest");
		em = emf.createEntityManager();
		enderecoService = new EnderecoServiceImpl();
		fornecedorService = new FornecedorServiceImpl();
		cidadeService = new CidadeServiceImpl();
		estadoService = new EstadoServiceImpl();
		enderecoService.setEntityManager(em);
		fornecedorService.setEntityManager(em);
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

		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setEndereco(endereco);
		fornecedor.setNome("Fulano e fulano");
		fornecedorService.setFornecedor(fornecedor);
		fornecedorService.save();
		
		estadoSalvo = estado;
		cidadeSalvo = cidade;
		fornecedorSalvo = fornecedor;
	}

	@Test
	public void create() {
		this.record();
		fornecedorService.load();
		Assert.assertEquals(1, fornecedorService.getFornecedores().size());
	}

	 @Test
	public void edit() {
		 fornecedorService.find(fornecedorSalvo.getId());
		 fornecedorService.editar();
		Assert.assertEquals("Fulano e fulano", fornecedorService
				.getFornecedor().getNome());
		fornecedorService.getFornecedor().setNome("Geraldo da Silva comercio de bebidas");
		fornecedorService.save();
		fornecedorService.find(fornecedorSalvo.getId());
		Assert.assertEquals("Geraldo da Silva comercio de bebidas", fornecedorService
				.getFornecedor().getNome());
	}

	 @Test
	public void find() {
		 fornecedorService.find(fornecedorSalvo.getId());
		Assert.assertEquals("Geraldo da Silva comercio de bebidas", fornecedorService
				.getFornecedor().getNome());
	}

	 @Test
	public void remove() {
		fornecedorService.setFornecedor(fornecedorSalvo);
		fornecedorService.delete();

		cidadeService.setCidade(cidadeSalvo);
		cidadeService.delete();

		estadoService.setEstado(estadoSalvo);
		estadoService.delete();

		fornecedorService.load();
		cidadeService.load();
		estadoService.load();
		enderecoService.load();

		Assert.assertEquals(0, fornecedorService.getFornecedores().size());
		Assert.assertEquals(0, cidadeService.getCidades().size());
		Assert.assertEquals(0, estadoService.getEstados().size());
		Assert.assertEquals(0, enderecoService.getEnderecos().size());
	}

	@Test(expectedExceptions = { java.sql.BatchUpdateException.class,
			javax.persistence.PersistenceException.class,
			org.hibernate.exception.ConstraintViolationException.class })
	public void removeAndThrowConstraintException() {
		this.record();
		estadoService.setEstado(estadoSalvo);
		estadoService.delete();
	}
}
