package com.loja.util;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.loja.model.Usuario;

public class JsonUtilTest {
	
	@Test
	public void toJson() {
		Usuario usuario = new Usuario();
		usuario.setEmail("joao@joao.com.br");
		Assert.assertEquals(new JsonUtil().toJson(Usuario.class, usuario), "{\"usuario\":{\"email\":\"joao@joao.com.br\"}}");
	}
	
	@Test
	public void fromJson() {
		Usuario usuario = (Usuario) new JsonUtil().fromJson(Usuario.class, "{\"usuario\":{\"email\":\"joao@joao.com.br\"}}");
		Assert.assertEquals("joao@joao.com.br", usuario.getEmail());
	}

	@BeforeClass
	public void beforeClass() {
	}

	@AfterClass
	public void afterClass() {
	}
}
