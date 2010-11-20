package com.loja.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.loja.model.Cargo;
import com.loja.model.CategoriaProduto;
import com.loja.model.Cidade;
import com.loja.model.Cliente;
import com.loja.model.Colaborador;
import com.loja.model.Endereco;
import com.loja.model.Estado;
import com.loja.model.Fornecedor;
import com.loja.model.GrupoUsuario;
import com.loja.model.ItemVenda;
import com.loja.model.Produto;
import com.loja.model.Usuario;
import com.loja.model.Venda;
import com.loja.model.exception.VendaEstoqueInsuficienteException;
import com.loja.model.exception.VendaEstoqueMinimoAtingidoException;
import com.loja.service.impl.CargoServiceImpl;
import com.loja.service.impl.CategoriaProdutoServiceImpl;
import com.loja.service.impl.CidadeServiceImpl;
import com.loja.service.impl.ClienteServiceImpl;
import com.loja.service.impl.ColaboradorServiceImpl;
import com.loja.service.impl.EnderecoServiceImpl;
import com.loja.service.impl.EstadoServiceImpl;
import com.loja.service.impl.FornecedorServiceImpl;
import com.loja.service.impl.GrupoUsuarioServiceImpl;
import com.loja.service.impl.ProdutoServiceImpl;
import com.loja.service.impl.UsuarioServiceImpl;
import com.loja.service.impl.VendaServiceImpl;

public class VendaServiceTest {

	private EntityManagerFactory emf;
	private EntityManager em;
	private CargoService cargoService;
	private EnderecoService enderecoService;
	private UsuarioService usuarioService;
	private ColaboradorService colaboradorService;
	private CidadeService cidadeService;
	private EstadoService estadoService;
	private GrupoUsuarioService grupoUsuarioService;
	private ClienteService clienteService;
	private FornecedorService fornecedorService;
	private VendaService vendaService;
	private ProdutoService produtoService;
	private CategoriaProdutoService categoriaProdutoService;
	
	private Cliente clienteSalvo;
	private Colaborador colaboradorSalvo;
	private Fornecedor fornecedorSalvo;
	private Produto produtoSalvo;
	private Venda vendaSalva;

	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}

	@BeforeClass
	public void init() {
		emf = Persistence.createEntityManagerFactory("pgPuTest");
		em = getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		enderecoService = new EnderecoServiceImpl();
		cidadeService = new CidadeServiceImpl();
		estadoService = new EstadoServiceImpl();
		clienteService = new ClienteServiceImpl();
		usuarioService = new UsuarioServiceImpl();
		produtoService = new ProdutoServiceImpl();
		grupoUsuarioService = new GrupoUsuarioServiceImpl();
		colaboradorService = new ColaboradorServiceImpl();
		fornecedorService = new FornecedorServiceImpl();
		vendaService = new VendaServiceImpl();
		cargoService = new CargoServiceImpl();
		categoriaProdutoService = new CategoriaProdutoServiceImpl();
		enderecoService.setEntityManager(em);
		cidadeService.setEntityManager(em);
		estadoService.setEntityManager(em);
		clienteService.setEntityManager(em);
		usuarioService.setEntityManager(em);
		grupoUsuarioService.setEntityManager(em);
		colaboradorService.setEntityManager(em);
		fornecedorService.setEntityManager(em);
		vendaService.setEntityManager(em);
		cargoService.setEntityManager(em);
		produtoService.setEntityManager(em);
		categoriaProdutoService.setEntityManager(em);
	}

	@AfterClass
	public void destroy() {
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
		
		Cargo cargo = new Cargo();
		cargo.setNome("Gerente");
		cargoService.setCargo(cargo);
		cargoService.save();
		
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
		
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setEndereco(endereco);
		fornecedor.setNome("Fulano e fulano");
		fornecedorService.setFornecedor(fornecedor);
		fornecedorService.save();
		
		Cliente cliente = new Cliente();
		cliente.setNome("Jose das Candeias");
		cliente.setEndereco(endereco);
		clienteService.setCliente(cliente);
		clienteService.save();
		clienteSalvo = cliente;
		colaboradorSalvo = colaborador;
		
		CategoriaProduto categoriaProduto = new CategoriaProduto();
		categoriaProduto.setNome("Laticinios");
		categoriaProdutoService.setCategoriaProduto(categoriaProduto);
		categoriaProdutoService.save();
		
		Produto produto = new Produto();
		produto.setCategoriaProduto(categoriaProduto);
		produto.setEstoque(new BigDecimal(900.00));
		produto.setEstoqueMaximo(new BigDecimal(800.00));
		produto.setEstoqueMinimo(new BigDecimal(100.00));
		produto.setFornecedor(fornecedorSalvo);
		produto.setNome("Bombeta");
		produtoService.setProduto(produto);
		produtoService.save();
		produtoSalvo = produto;
	}
	
	@Test
	public void deveriaVender() throws VendaEstoqueInsuficienteException, VendaEstoqueMinimoAtingidoException {
		this.record();
		ItemVenda itemVenda = new ItemVenda();
		itemVenda.setProduto(produtoSalvo);
		itemVenda.setQtde(new BigDecimal(20.00));
		
		vendaService.setProduto(produtoSalvo);
		vendaService.setQuantidade(new BigDecimal(20.00));
		
		Venda venda = new Venda();
		venda.setColaborador(colaboradorSalvo);
		venda.setData(new Date());
		venda.setCliente(clienteSalvo);
		vendaService.adicionar();
		vendaService.setVenda(venda);
		vendaService.save();
		vendaSalva = venda;
		vendaService.setVenda(vendaSalva);
		System.out.println(vendaService.getProduto().getEstoque());
		Assert.assertEquals(venda.getItensVenda().size(), 1);
		Assert.assertEquals(vendaService.getProduto().getEstoque(), new BigDecimal(880.00));
		
	}
	
	@Test
	public void remove() {
		vendaService.setVenda(vendaSalva);
		vendaService.delete();
	}
	
	@Test
	public void listar() {
		vendaService.load();
		Assert.assertEquals(1, vendaService.getVendas().size());
	}
	
	@Test
	public void edit() throws VendaEstoqueInsuficienteException, VendaEstoqueMinimoAtingidoException {
		vendaService.find(vendaSalva.getId());
		Venda venda = vendaService.getVenda();
		vendaService.editar();
		venda.setData(new Date());
		venda.setItensVenda(new ArrayList<ItemVenda>());
		
		ItemVenda itemVenda1 = new ItemVenda();
		itemVenda1.setProduto(produtoSalvo);
		itemVenda1.setQtde(new BigDecimal(100.00));
		
		venda.adicionaProduto(itemVenda1);
		vendaService.save();
	}
	
	@Test(expectedExceptions = VendaEstoqueInsuficienteException.class)
	public void deveriaRetornarVendaEstoqueInsuficienteExceptionAoVender() throws VendaEstoqueInsuficienteException, VendaEstoqueMinimoAtingidoException {
		this.record();
		ItemVenda itemVenda = new ItemVenda();
		itemVenda.setProduto(produtoSalvo);
		itemVenda.setQtde(new BigDecimal(901.00));
		
		Venda venda = new Venda();
		venda.setColaborador(colaboradorSalvo);
		venda.setData(new Date());
		venda.setCliente(clienteSalvo);
		vendaService.setVenda(venda);
		vendaService.getVenda().adicionaProduto(itemVenda);
		vendaService.save();
		vendaService.setVenda(vendaSalva);
	}
	
	@Test(expectedExceptions = VendaEstoqueMinimoAtingidoException.class)
	public void deveriaRetornarVendaEstoqueMinimoAtingidoExceptionAoVender() throws VendaEstoqueInsuficienteException, VendaEstoqueMinimoAtingidoException {
		this.record();
		ItemVenda itemVenda = new ItemVenda();
		itemVenda.setProduto(produtoSalvo);
		itemVenda.setQtde(new BigDecimal(900.00));
		
		Venda venda = new Venda();
		venda.setColaborador(colaboradorSalvo);
		venda.setData(new Date());
		venda.setCliente(clienteSalvo);
		vendaService.setVenda(venda);
		vendaService.getVenda().adicionaProduto(itemVenda);
		vendaService.save();
		vendaService.setVenda(vendaSalva);
	}

}
