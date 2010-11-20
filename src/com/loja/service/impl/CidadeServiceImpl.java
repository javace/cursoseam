package com.loja.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;

import com.loja.model.Cidade;
import com.loja.model.Estado;
import com.loja.service.CidadeService;

@Name("cidadeService")
@Scope(ScopeType.CONVERSATION)
public class CidadeServiceImpl implements Serializable, CidadeService {

	private static final long serialVersionUID = 1L;

	@In
	private EntityManager entityManager;

	@DataModelSelection
	@Out(required = false)
	private Cidade cidade = new Cidade();

	@DataModel
	private List<Cidade> cidades = new ArrayList<Cidade>();

	private Estado estado = new Estado();

	/* (non-Javadoc)
	 * @see com.loja.service.impl.CidadeService#load()
	 */
	@SuppressWarnings("unchecked")
	public void load() {
		setCidades(entityManager.createQuery("select e from Cidade as e")
				.getResultList());
	}

	/* (non-Javadoc)
	 * @see com.loja.service.impl.CidadeService#loadByEstado()
	 */
	@SuppressWarnings("unchecked")
	public void loadByEstado() {
		setCidades(entityManager.createQuery(
				"select e from Cidade as e where e.estado.id = "
						+ getEstado().getId()).getResultList());
	}

	/* (non-Javadoc)
	 * @see com.loja.service.impl.CidadeService#find(java.lang.Integer)
	 */
	public void find(Integer id) {
		setCidade(entityManager.find(Cidade.class, id));
	}

	/* (non-Javadoc)
	 * @see com.loja.service.impl.CidadeService#setEntityManager(javax.persistence.EntityManager)
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/* (non-Javadoc)
	 * @see com.loja.service.impl.CidadeService#setCidades(java.util.List)
	 */
	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	/* (non-Javadoc)
	 * @see com.loja.service.impl.CidadeService#getCidades()
	 */
	public List<Cidade> getCidades() {
		return cidades;
	}

	/* (non-Javadoc)
	 * @see com.loja.service.impl.CidadeService#getCidade()
	 */
	public Cidade getCidade() {
		return cidade;
	}

	/* (non-Javadoc)
	 * @see com.loja.service.impl.CidadeService#setCidade(com.loja.model.Cidade)
	 */
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	/* (non-Javadoc)
	 * @see com.loja.service.impl.CidadeService#setEstado(com.loja.model.Estado)
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	/* (non-Javadoc)
	 * @see com.loja.service.impl.CidadeService#getEstado()
	 */
	public Estado getEstado() {
		return estado;
	}

	/* (non-Javadoc)
	 * @see com.loja.service.impl.CidadeService#save()
	 */
	public void save() {
		if (cidade.getId() == null) {
			entityManager.persist(cidade);
		} else {
			entityManager.merge(cidade);
		}
		setCidade(null);
	}

	/* (non-Javadoc)
	 * @see com.loja.service.impl.CidadeService#delete()
	 */
	public void delete() {
		entityManager.remove(cidade);
		setCidade(null);
	}

	/* (non-Javadoc)
	 * @see com.loja.service.impl.CidadeService#editar()
	 */
	public void editar() {
		setCidade(cidade);
	}
}
