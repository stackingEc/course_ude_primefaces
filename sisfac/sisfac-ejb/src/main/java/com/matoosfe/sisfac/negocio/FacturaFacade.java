package com.matoosfe.sisfac.negocio;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.matoosfe.sisfac.entidad.DetalleFactura;
import com.matoosfe.sisfac.entidad.Factura;

@Stateless
public class FacturaFacade extends AbstractFacade<Factura> {

	@PersistenceContext(unitName = "sisfacPU")
	private EntityManager em;

	public FacturaFacade() {
		super(Factura.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.matoosfe.sisfac.negocio.AbstractFacade#guardar(java.lang.Object)
	 */
	@Override
	public void guardar(Factura factura) throws Exception {
		BigDecimal subtotal = new BigDecimal(0.0);
		for (DetalleFactura detTmp : factura.getDetalleFacturas()) {
			subtotal = subtotal.add(detTmp.getDetfacTotal());
		}

		factura.setFacSubtotal(subtotal);
		factura.setFacImpuesto(factura.getFacSubtotal().multiply(new BigDecimal(0.12)));
		factura.setFacTotal(factura.getFacSubtotal().add(factura.getFacImpuesto()));

		em.persist(factura);
	}

	public List<Factura> buscarFacturas(String busquedaPor, String valorBusPor) {
		List<Factura> listaFacturas = new ArrayList<>();

		TypedQuery<Factura> conBusFac = null;

		if (busquedaPor.equals("Numero")) {
			conBusFac = em.createQuery("Select fac from Factura fac where fac.facNumero like :numeroFac", Factura.class);
			conBusFac.setParameter("numeroFac", "%" + valorBusPor + "%");
		} else if (busquedaPor.equals("Cliente")) {
			conBusFac = em.createQuery(
					"Select fac from Factura fac where fac.cliente.cliNombres like :nombreApellido or fac.cliente.cliApellidos like :nombreApellido",
					Factura.class);
			conBusFac.setParameter("nombreApellido", "%" + valorBusPor + "%");
		} else if (busquedaPor.equals("Estado")) {
			int valorEstado = 0;
			if(valorBusPor.equals("EMITIDO")) {
				valorEstado = 1;
			}else if(valorBusPor.equals("ELIMINADO")) {
				valorEstado = 2;
			}else if(valorBusPor.equals("ANULADO")) {
				valorEstado = 3;
			}
			conBusFac = em.createQuery("Select fac from Factura fac where fac.facEstado = :estadoFac", Factura.class);
			conBusFac.setParameter("estadoFac", valorEstado);
		} else {
			try {
				SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
				Date fechaBus = formatoFecha.parse(valorBusPor);
				conBusFac = em.createQuery("Select fac from Factura fac where fac.facFecha >= :fechaBus", Factura.class);
				conBusFac.setParameter("fechaBus", fechaBus);
			} catch (ParseException e) {
				listaFacturas = new ArrayList<>();
			}
		}
		listaFacturas = conBusFac.getResultList();
		return listaFacturas;
	}

}
