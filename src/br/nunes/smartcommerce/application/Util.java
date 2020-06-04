package br.nunes.smartcommerce.application;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.codec.digest.DigestUtils;

public class Util {
	private Util() {
		// para nao permitir uma instancia
	}

	public static String hashSHA256(String valor) {
		return DigestUtils.sha256Hex(valor);
	}

	public static void addErrorMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
	}

	public static void addWarningMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, null));
	}

	public static void addInfoMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
	}
}
