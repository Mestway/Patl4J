package com.enjava.seam.mail;

import java.io.Serializable;

import org.jboss.seam.annotations.Name;

@Name("mailContacto")
public class MailContacto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String fromName;
	String fromAddress;
	String titulo;
	String contenido;
	
	
	public MailContacto(String fromName, String fromAddress, String titulo,
			String contenido) {
		this.fromName = fromName;
		this.fromAddress = fromAddress;
		this.titulo = titulo;
		this.contenido = contenido;
	}
	public MailContacto() {
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	
	
	
}
