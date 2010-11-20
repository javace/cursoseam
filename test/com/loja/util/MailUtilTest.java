package com.loja.util;

import org.testng.annotations.Test;

public class MailUtilTest {

	@Test
	public void deveriaEnviar() {
		new MailUtil().sendNovo();
	}
}
