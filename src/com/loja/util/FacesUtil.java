package com.loja.util;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;


public class FacesUtil {
	
	
	
	/*
	 * Metodo que seta uma FacesMessage num item
	 */
	public void viewMessage(String target, String message, Severity tipo) {
		if (FacesContext.getCurrentInstance() != null) {
			ResourceBundle bundle = ResourceBundle.getBundle("SystemMessages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
			FacesMessage facesmessage = new FacesMessage(); 
			facesmessage.setSeverity(tipo);			
			facesmessage.setDetail(bundle.getString(message));
			facesmessage.setSummary(bundle.getString(message));
			FacesContext.getCurrentInstance().addMessage(target, facesmessage); 
		}
	}

}
