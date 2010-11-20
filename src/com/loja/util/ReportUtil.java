package com.loja.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("reportUtil")
@Scope(ScopeType.CONVERSATION)
public class ReportUtil {
	
	private Date data = new Date();

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getFormatedDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		return formatter.format(getData());
	}
}
