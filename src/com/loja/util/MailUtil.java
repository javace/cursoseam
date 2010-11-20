package com.loja.util;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.Renderer;

@Name("mailUtil")
@Scope(ScopeType.CONVERSATION)
public class MailUtil {
	
	@In(create = true)
	private Renderer renderer;
	
	public void setRenderer(Renderer renderer) {
		this.renderer = renderer;
	}

	public void sendNovo() {
		renderer = Renderer.instance();
		renderer.render("/templates/email/estoqueBaixo.xhtml");
	}
}
