package controller.commands;

import java.util.Enumeration;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.util.SessaoParamsUtils;
import controller.validation.CamposNaoPreenchidosException;
import entitites.Usuario;
import entitites.util.TipoUsuario;


/**
 * Classe que representa um comando abstrato. 
 * Utilizado para o Padrao de Projeto Command. 
 * @author alessandro87
 *
 */
public abstract class AbstractCommand {

	public abstract void execute(HttpServletRequest request, HttpServletResponse response);

	/**
	 * Verifica se um dado formulario foi todo preenchido 
	 * @param request
	 * @throws CamposNaoPreenchidosException
	 */
	public void camposEstaoPreenchidos(HttpServletRequest request)
	throws CamposNaoPreenchidosException{

		LinkedList<String> names = new LinkedList<String>();

		Enumeration<String> namesParans = request.getParameterNames();
		while (namesParans.hasMoreElements()){
			String nameP = namesParans.nextElement();
			/*Nao preenchido*/
			if(request.getParameter(nameP).equals("")){
				names.add(nameP);
			}
		}
		if(!names.isEmpty()){
			throw new CamposNaoPreenchidosException(names);
		}

	}

	/**
	 * Verifica se um dado formulario foi todo preenchido
	 * levando-se em conta  campos nao obrigatórios
	 * @param request
	 * @param camposNObrigatorios
	 * @throws CamposNaoPreenchidosException
	 */
	public void camposEstaoPreenchidos(HttpServletRequest request
			, LinkedList<String>camposNObrigatorios )
	throws CamposNaoPreenchidosException{

		LinkedList<String> names = new LinkedList<String>();

		Enumeration<String> namesParans = request.getParameterNames();
		while (namesParans.hasMoreElements()){
			String nameP = namesParans.nextElement();
			/*Nao preenchido*/
			if(request.getParameter(nameP).equals("") &&
					!camposNObrigatorios.contains(nameP)){
				names.add(nameP);
			}
		}
		if(!names.isEmpty()){
			throw new CamposNaoPreenchidosException(names);
		}
	}

	/**
	 * Trata a sessao que é disparada durante uma sessao.
	 * @param msg
	 */
	public void tratarExcecaoGeradaNumaSessao(String msg, HttpServletRequest request
			, HttpServletResponse response) {
		try {
			request.getSession().setAttribute(SessaoParamsUtils.MENSAGEM
					, "Erro: " + msg );
			redirecionarPaginaPrincipal(request,response);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void redirecionarPaginaPrincipal(HttpServletRequest request, HttpServletResponse response) 
	{
		try {
			String tipousuario = (String)request.getSession().getAttribute(SessaoParamsUtils.TIPOUSUARIOLOGADO);
			if( tipousuario == null )
			{
				request.getSession().invalidate();
				response.sendRedirect("index.jsp");
			}
			else if (tipousuario.equals(TipoUsuario.ADMINISTRADOR_CLINICO)) {
				response.sendRedirect("administradorclinico/index.jsp");
			}
			else if (tipousuario.equals(TipoUsuario.PACIENTE)) {
				response.sendRedirect("paciente/index.jsp");
			}
			else if ( tipousuario.equals(TipoUsuario.PSICOLOGO) ) {
				response.sendRedirect("especialista/index.jsp");
			}
			else if ( tipousuario.equals(TipoUsuario.PROCTOLOGISTA) ) {
				response.sendRedirect("especialista/index.jsp");
			}
			//TODO: else if (tipousuario.equals(TipoUsuario.SECRETARIO)) {	}
			else {
				response.sendRedirect("index.jsp");
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo utilitario para retirar os parametros inseridos a partir de uma lista 
	 * que separa elementos por virgula
	 * @param entrada
	 * @simbol simbolo que separa
	 * @return
	 */
	public LinkedList<String> retirandoParametrosSeparadosVirgula(String entrada, char simbol){
		
		String entradaSemEspBrancos = entrada.replace(" ", ""); 
		LinkedList<String> parametros = new LinkedList<String>( );
		String[] splitted = entradaSemEspBrancos.split(String.valueOf(simbol));
		for (int i =0; i< splitted.length;i++) {
			parametros.push(splitted[i]);
		}
		
		/*if (entrada.indexOf(simbol) == -1) {
			parametros.add(entrada);
		}
		else {
			while ( entrada.indexOf(simbol) != -1 )
			{
				parametros.add( entrada.substring( 0,entrada.indexOf(simbol) ) );
				if( entrada.indexOf(',') + 1 < entrada.length() )
					entrada = entrada.substring( entrada.indexOf(simbol) + 1 );
				else
					break;
			}
		}*/
		return parametros;
	}

}
