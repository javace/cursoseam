package com.loja.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jboss.seam.annotations.security.management.UserFirstName;
import org.jboss.seam.annotations.security.management.UserPassword;
import org.jboss.seam.annotations.security.management.UserPrincipal;
import org.jboss.seam.annotations.security.management.UserRoles;

import com.loja.security.Authenticator;

@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@UserFirstName
	private String nome;

	@UserPrincipal
	private String email;

	@UserPassword(hash="md5")
	private String senha;

	@UserRoles
	@ManyToOne(fetch = FetchType.EAGER)
	private GrupoUsuario gruposUsuarios;

	private Integer hierarquia_id;

	public GrupoUsuario getGruposUsuarios() {
		return gruposUsuarios;
	}

	public void setGruposUsuarios(GrupoUsuario gruposUsuarios) {
		this.gruposUsuarios = gruposUsuarios;
	}

	public Integer getHierarquia_id() {
		return hierarquia_id;
	}

	public void setHierarquia_id(Integer hierarquiaId) {
		hierarquia_id = hierarquiaId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public void senhaToMd5() {
		setSenha(new Authenticator().toHash(this.senha));
	}
}
