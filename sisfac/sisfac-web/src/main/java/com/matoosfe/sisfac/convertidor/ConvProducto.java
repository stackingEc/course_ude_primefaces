package com.matoosfe.sisfac.convertidor;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.matoosfe.sisfac.entidad.Producto;
import com.matoosfe.sisfac.negocio.ProductoFacade;

@ManagedBean(name = "convPro")
public class ConvProducto implements Converter {

	@EJB
	private ProductoFacade adminProducto;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (!value.equals("")) {
			Producto prod = adminProducto.consultarPorId(Integer.valueOf(value));
			return prod;
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Producto prod = (Producto) value;
			return String.valueOf(prod.getProCodigo());
		} else {
			return null;
		}
	}

}
