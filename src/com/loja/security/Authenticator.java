package com.loja.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

import com.loja.model.Usuario;

@Name("authenticator")
public class Authenticator {

	@In
	EntityManager entityManager;
	@In
	Credentials credentials;
	@In
	Identity identity;
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	
	public void setIdentity(Identity identity) {
		this.identity = identity;
	}

	public boolean authenticate() {
		try {
			Usuario user = (Usuario) entityManager
					.createQuery(
							"from Usuario where email = :username and senha = :password")
					.setParameter("username", credentials.getUsername())
					.setParameter("password", toHash(credentials.getPassword()))
					.getSingleResult();
			identity.addRole(user.getGruposUsuarios().getRole());
			return true;
		} catch (NoResultException e) {
			return false;
		}
			
	}
	
	public String toHash(String stringToHash) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			BigInteger hash = new BigInteger(1, md.digest(stringToHash.getBytes()));
			return hash.toString(16);
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
	}
}