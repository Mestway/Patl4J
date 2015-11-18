package controller.commands;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gdata.util.AuthenticationException;

import business.FachadaManager;
import business.util.LogarException;

import controller.util.SessaoParamsUtils;
import controller.validation.CamposNaoPreenchidosException;
import entitites.AdministradorClinico;
import entitites.Paciente;
import entitites.Usuario;
import entitites.specialists.Especialista;

/**
 * Classe responsavel pelo mecanismo de logar-se
 * @author Administrador
 *
 */
public class DeslogarCommand  extends AbstractCommand{

	public void execute(HttpServletRequest request, HttpServletResponse response) 
	{
		request.getSession().invalidate();
		try {
			response.sendRedirect("index.jsp");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
