package com.matoosfe.sisfac.core;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class AbstractManagedBean {

	protected FacesContext obtenerContexto() {
		return FacesContext.getCurrentInstance();
	}
	
	protected void anadirMensajeInfo(String mensaje) {
		anadirMensaje(mensaje, FacesMessage.SEVERITY_INFO);
	}
	
	protected void anadirMensajeError(String mensaje) {
		anadirMensaje(mensaje, FacesMessage.SEVERITY_ERROR);
	}

	private void anadirMensaje(String mensaje, Severity severity) {
		FacesMessage menJSF = new FacesMessage();
		menJSF.setSeverity(severity);
		menJSF.setSummary(mensaje);
		obtenerContexto().addMessage(null, menJSF);
		
	}
	
	
	
	
}
