package com.loja.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.loja.model.Cargo;
import com.loja.model.Cidade;
import com.loja.model.Colaborador;
import com.loja.model.Endereco;
import com.loja.model.Estado;
import com.loja.model.GrupoUsuario;
import com.loja.model.Usuario;
import com.loja.service.impl.CargoServiceImpl;
import com.loja.service.impl.CidadeServiceImpl;
import com.loja.service.impl.ColaboradorServiceImpl;
import com.loja.service.impl.EnderecoServiceImpl;
import com.loja.service.impl.EstadoServiceImpl;
import com.loja.service.impl.GrupoUsuarioServiceImpl;
import com.loja.service.impl.UsuarioServiceImpl;

public class ColaboradorServiceTest {

	private EntityManagerFactory emf;
	private EntityManager em;
	private CargoService cargoService;
	private EnderecoService enderecoService;
	private UsuarioService usuarioService;
	private ColaboradorService colaboradorService;
	private CidadeService cidadeService;
	private EstadoService estadoService;
	private GrupoUsuarioService grupoUsuarioService;

	private Cargo cargoSalvo;
	private Usuario usuarioSalvo;
	private GrupoUsuario grupoUsuarioSalvo;
	private Colaborador colaboradorSalvo;
	private Estado estadoSalvo;
	private Cidade cidadeSalvo;

	@BeforeClass
	public void init() {
		emf = Persistence.createEntityManagerFactory("pgPuTest");
		em = emf.createEntityManager();
		cargoService = new CargoServiceImpl();
		enderecoService = new EnderecoServiceImpl();
		usuarioService = new UsuarioServiceImpl();
		grupoUsuarioService = new GrupoUsuarioServiceImpl();
		colaboradorService = new ColaboradorServiceImpl();
		cidadeService = new CidadeServiceImpl();
		estadoService = new EstadoServiceImpl();
		cargoService.setEntityManager(em);
		enderecoService.setEntityManager(em);
		usuarioService.setEntityManager(em);
		grupoUsuarioService.setEntityManager(em);
		colaboradorService.setEntityManager(em);
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
		Cargo cargo = new Cargo();
		cargo.setNome("Gerente");
		cargoService.setCargo(cargo);
		cargoService.save();

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

		Colaborador colaborador = new Colaborador();
//		colaborador.setCargo(cargo);
		colaborador.setEndereco(endereco);
		colaborador.setUsuario(usuario);
		colaborador.setCpf("002.342.681-07");
//		colaborador.setDataAdmissao(new Date());
//		colaborador.setDataNascimento(new Date());
		colaborador.setNome("Francisco da Silva");
		colaborador.setRg("2386284");
		colaborador.setEmail("francisco@dasilva.com.br");
		colaboradorService.setColaborador(colaborador);
		colaboradorService.save();

		cargoSalvo = cargo;
		estadoSalvo = estado;
		cidadeSalvo = cidade;
		grupoUsuarioSalvo = grupoUsuario;
		colaboradorSalvo = colaborador;
	}

	@Test
	public void create() {
		this.record();
		colaboradorService.load();
		Assert.assertEquals(1, colaboradorService.getColaboradores().size());
	}

	 @Test
	public void edit() {
		colaboradorService.find(colaboradorSalvo.getId());
		colaboradorService.editar();
		Assert.assertEquals("Francisco da Silva", colaboradorService
				.getColaborador().getNome());
		colaboradorService.getColaborador().setNome("Geraldo da Silva");
		colaboradorService.save();
		colaboradorService.find(colaboradorSalvo.getId());
		Assert.assertEquals("Geraldo da Silva", colaboradorService
				.getColaborador().getNome());
	}

	 @Test
	public void find() {
		colaboradorService.find(colaboradorSalvo.getId());
		Assert.assertEquals("Geraldo da Silva", colaboradorService
				.getColaborador().getNome());
	}

	 @Test
	public void remove() {
		colaboradorService.setColaborador(colaboradorSalvo);
		colaboradorService.delete();

		cargoService.setCargo(cargoSalvo);
		cargoService.delete();

		grupoUsuarioService.setGrupoUsuario(grupoUsuarioSalvo);

		usuarioService.setUsuario(usuarioSalvo);
		usuarioService.delete();

		cidadeService.setCidade(cidadeSalvo);
		cidadeService.delete();

		estadoService.setEstado(estadoSalvo);
		estadoService.delete();

		colaboradorService.load();
		cargoService.load();
		cidadeService.load();
		estadoService.load();
		enderecoService.load();

		Assert.assertEquals(0, colaboradorService.getColaboradores().size());
		Assert.assertEquals(0, cargoService.getCargos().size());
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
