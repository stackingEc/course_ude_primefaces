package com.matoosfe.sisfac.admin.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import com.matoosfe.sisfac.core.AbstractManagedBean;
import com.matoosfe.sisfac.entidad.TipoProducto;
import com.matoosfe.sisfac.negocio.TipoProductoFacade;

/**
 * Clase para administrar el formulario de tipo de producto
 * 
 * @author martosfre
 *
 */
@ManagedBean
@ViewScoped
public class TipoProductoBean extends AbstractManagedBean{

	private TipoProducto tipoProducto;
	private TipoProducto tipoProductoSel;
	private List<TipoProducto> listaTipoProducto;

	@EJB
	private TipoProductoFacade adminTipoProducto;

	public TipoProductoBean() {
		this.tipoProducto = new TipoProducto();
		this.listaTipoProducto = new ArrayList<>();
	}

	/**
	 * @return the tipoProducto
	 */
	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	/**
	 * @param tipoProducto
	 *            the tipoProducto to set
	 */
	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	/**
	 * @return the tipoProductoSel
	 */
	public TipoProducto getTipoProductoSel() {
		return tipoProductoSel;
	}

	/**
	 * @param tipoProductoSel
	 *            the tipoProductoSel to set
	 */
	public void setTipoProductoSel(TipoProducto tipoProductoSel) {
		this.tipoProductoSel = tipoProductoSel;
	}

	/**
	 * @return the listaTipoProducto
	 */
	public List<TipoProducto> getListaTipoProducto() {
		return listaTipoProducto;
	}

	/**
	 * @param listaTipoProducto
	 *            the listaTipoProducto to set
	 */
	public void setListaTipoProducto(List<TipoProducto> listaTipoProducto) {
		this.listaTipoProducto = listaTipoProducto;
	}

	public void seleccionarRegistro(SelectEvent e) {
		this.tipoProductoSel = (TipoProducto) e.getObject();
	}

	/**
	 * Método para guardar un tipo de producto
	 */
	public void guardar() {
		try {
			if (tipoProducto.getTipproCodigo() != 0) {
				adminTipoProducto.actualizar(tipoProducto);
				anadirMensajeInfo("Registro actualizado exitosamente");
			} else {
				adminTipoProducto.guardar(tipoProducto);
				anadirMensajeInfo("Registro guardado exitosamente");
			}
			cargarTipoProductos();
			resetearFormulario();
		} catch (Exception e) {
			anadirMensajeError("No se ha podido guardar:" + e.getMessage());
		}
	}

	public void editar() {
		if (this.tipoProductoSel != null) {
			this.tipoProducto = tipoProductoSel;
		} else {
			anadirMensajeError("Se debe seleccionar un tipo de producto");
		}
	}

	public void eliminar() {
		try {
			if (this.tipoProductoSel != null) {
				adminTipoProducto.eliminar(tipoProductoSel);
				cargarTipoProductos();
				resetearFormulario();
				anadirMensajeInfo("Registro eliminado exitosamente");
			} else {
				anadirMensajeError("Se debe seleccionar un tipo de producto");
			}
		} catch (Exception e) {
			anadirMensajeError("Error al eliminar el registro:" + e.getMessage());
		}
	}

	public void resetearFormulario() {
		this.tipoProducto = new TipoProducto();
		this.tipoProductoSel = null;
	}

	private void cargarTipoProductos() {
		try {
			this.listaTipoProducto = adminTipoProducto.consultarTodos();
		} catch (Exception e) {
			anadirMensajeError("No se ha podido cargar los tipos de productos:" + e.getMessage());
		}

	}

	@PostConstruct
	public void inicializar() {
		cargarTipoProductos();
	}

}
