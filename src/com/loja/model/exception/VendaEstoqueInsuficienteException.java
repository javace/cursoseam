package com.loja.model.exception;

public class VendaEstoqueInsuficienteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VendaEstoqueInsuficienteException() {
		super("Qtde maior que o estoque");
	}

}
