package com.matoosfe.sisfac.admin.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import com.matoosfe.sisfac.core.AbstractManagedBean;
import com.matoosfe.sisfac.entidad.Producto;
import com.matoosfe.sisfac.entidad.TipoProducto;
import com.matoosfe.sisfac.negocio.ProductoFacade;
import com.matoosfe.sisfac.negocio.TipoProductoFacade;

@ManagedBean
@ViewScoped
public class ProductoBean extends AbstractManagedBean {

	private Producto producto;
	private Producto productoSel;
	private List<Producto> listaProductos;
	private List<TipoProducto> listaTipoProductos;

	@EJB
	private ProductoFacade adminProducto;
	@EJB
	private TipoProductoFacade adminTipoProducto;

	public ProductoBean() {
		this.producto = new Producto();
		this.listaProductos = new ArrayList<>();
		this.listaTipoProductos = new ArrayList<>();
	}

	/**
	 * @return the producto
	 */
	public Producto getProducto() {
		return producto;
	}

	/**
	 * @param producto
	 *            the producto to set
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	/**
	 * @return the productoSel
	 */
	public Producto getProductoSel() {
		return productoSel;
	}

	/**
	 * @param productoSel
	 *            the productoSel to set
	 */
	public void setProductoSel(Producto productoSel) {
		this.productoSel = productoSel;
	}

	/**
	 * @return the listaProductos
	 */
	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	/**
	 * @param listaProductos
	 *            the listaProductos to set
	 */
	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	/**
	 * @return the listaTipoProductos
	 */
	public List<TipoProducto> getListaTipoProductos() {
		return listaTipoProductos;
	}

	/**
	 * @param listaTipoProductos
	 *            the listaTipoProductos to set
	 */
	public void setListaTipoProductos(List<TipoProducto> listaTipoProductos) {
		this.listaTipoProductos = listaTipoProductos;
	}

	public void seleccionarRegistro(SelectEvent ev) {
		this.productoSel = (Producto) ev.getObject();
	}

	public void guardar() {
		try {
			if (producto.getProCodigo() == 0) {
				adminProducto.guardar(producto);
				anadirMensajeInfo("Producto registrado exitosamente");
			} else {
				adminProducto.actualizar(producto);
				anadirMensajeInfo("Producto actualizado exitosamente");
			}
			cargarProductos();
			resetearFormulario();
		} catch (Exception e) {
			anadirMensajeError("No se pudo guardar el producto:" + e.getMessage());
		}
	}

	public void editar() {
		if (productoSel != null) {
			this.producto = productoSel;
		} else {
			anadirMensajeError("Se debe seleccionar un registro");
		}
	}

	public void eliminar() {
		try {
			if (productoSel != null) {
				adminProducto.eliminar(productoSel);
				anadirMensajeInfo("Producto eliminado correctamente");
				cargarProductos();
				resetearFormulario();
			} else {
				anadirMensajeError("Se debe seleccionar un registro");
			}
		} catch (Exception e) {
			anadirMensajeError("No se pudo eliminar el registro:" + e.getMessage());
		}
	}

	public void resetearFormulario() {
		this.producto = new Producto();
		this.productoSel = null;
	}

	private void cargarProductos() {
		try {
			this.listaProductos = adminProducto.consultarTodos();
		} catch (Exception e) {
			anadirMensajeError("No se pudo cargar los productos:" + e.getMessage());
		}

	}

	private void cargarTipoProductos() {
		try {
			this.listaTipoProductos = adminTipoProducto.consultarTodos();
		} catch (Exception e) {
			anadirMensajeError("No se pudo cargar los tipos de productos:" + e.getMessage());
		}

	}

	@PostConstruct
	public void inicializar() {
		cargarProductos();
		cargarTipoProductos();
	}

}
