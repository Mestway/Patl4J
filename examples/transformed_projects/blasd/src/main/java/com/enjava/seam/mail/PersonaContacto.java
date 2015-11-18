package com.enjava.seam.mail;

import java.io.Serializable;

import org.jboss.seam.annotations.Name;

@Name("personaContacto")
public class PersonaContacto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String mail;
	String nombre;
	String apellidos;
	
	
	public PersonaContacto(String mail, String nombre, String apellidos) {
		this.mail = mail;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}
	public PersonaContacto() {
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	
}
