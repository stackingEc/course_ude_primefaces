package com.matoosfe.sisfac.negocio;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.matoosfe.sisfac.entidad.TipoProducto;

@Stateless
public class TipoProductoFacade extends AbstractFacade<TipoProducto> {

	@PersistenceContext(unitName = "sisfacPU")
	private EntityManager em;
	
	public TipoProductoFacade() {
		super(TipoProducto.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
