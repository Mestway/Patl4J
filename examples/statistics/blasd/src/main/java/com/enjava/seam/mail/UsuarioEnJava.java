package com.enjava.seam.mail;

import java.io.Serializable;
import java.util.Calendar;

import org.jboss.seam.annotations.Name;

@Name("usuarioEnJava")
public class UsuarioEnJava implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public UsuarioEnJava(String mail, String app) {

		this.mail = mail;
		this.app=app;
		this.fecha=Calendar.getInstance();
	}
	Calendar fecha;
	String mail;
	String app;
	
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public Calendar getFecha() {
		return fecha;
	}
	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
}
