package com.loja.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.loja.model.Usuario;

public class XMLUtilTest {

	@Test
	public void toXML() {
		Usuario usuario = new Usuario();
		usuario.setNome("Joao");

		StringBuffer retorno = new StringBuffer();
		retorno.append("<nome>Joao</nome>");

		Assert.assertTrue(new XMLUtil().toXML(usuario).contains(retorno));
	}

	@Test
	public void fromXML() {
		StringBuffer xmlUsuario = new StringBuffer();
		xmlUsuario.append("<com.cronos.model.Usuario>");
		xmlUsuario.append("  <nome>Joao</nome>");
		xmlUsuario.append("</com.cronos.model.Usuario>");

		boolean retorno = false;
		Usuario usuario = (Usuario) new XMLUtil()
				.fromXML(xmlUsuario.toString());
		if (usuario != null && usuario.getNome().equals("Joao")) {
			retorno = true;
		}
		Assert.assertTrue(retorno);
	}
}
