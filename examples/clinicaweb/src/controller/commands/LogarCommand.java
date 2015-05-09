package controller.commands;


import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import business.FachadaManager;
import business.util.LogarException;

import controller.util.SessaoParamsUtils;
import controller.validation.CamposMalPreenchidosException;
import controller.validation.CamposNaoPreenchidosException;
import controller.validation.TiposFormulario;
import controller.validation.ValidarCamposUtils;
import entitites.AdministradorClinico;
import entitites.Paciente;
import entitites.Usuario;
import entitites.specialists.Especialista;

/**
 * Classe responsavel pelo mecanismo de logar-se
 * @author Administrador
 *
 */
public class LogarCommand  extends AbstractCommand{

	public void execute(HttpServletRequest request, HttpServletResponse response) {

		try {
			this.camposEstaoPreenchidos(request);
			
			String password = request.getParameter(SessaoParamsUtils.SENHA);
			String login = request.getParameter(SessaoParamsUtils.LOGIN);

			Vector<String> names = new Vector<String>();
			names.add(SessaoParamsUtils.SENHA);
			
			Vector<String> valores=  new Vector<String>();
			valores.add(password);
			
			Vector<TiposFormulario> tipos = new Vector<TiposFormulario>();
			tipos.add(TiposFormulario.SENHA);
			
			ValidarCamposUtils.getInstance().validarCampos(names, valores, tipos);
			
			
			Usuario usuarioLogado = FachadaManager.getInstance().logar(login, password);
			adicionarInformacoesSessao(request, usuarioLogado, password);
			
			if( usuarioLogado instanceof Especialista) {
				response.sendRedirect("especialista/index.jsp");
			}
			else if (usuarioLogado instanceof Paciente) {
				response.sendRedirect("paciente/index.jsp");
			}
			else if (usuarioLogado instanceof AdministradorClinico) {
				response.sendRedirect("administradorclinico/index.jsp");
			}
			else
			{
				response.sendRedirect("index.jsp");
			}
		} 
		catch (CamposNaoPreenchidosException e) {
			try {
				request.getSession().setAttribute(SessaoParamsUtils.MENSAGEM
						, "Erro: Os campos " + e.getCamposErrados().toString() + " não foram preenchidos" );
				response.sendRedirect("index.jsp");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
		catch (CamposMalPreenchidosException e) {
			try {
				request.getSession().setAttribute(SessaoParamsUtils.MENSAGEM
						, "Erro: Os campos " + e.getCamposErrados().toString() + " foram mal preenchidos" );
				response.sendRedirect("index.jsp");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
		catch (LogarException e) {
			try {
				request.getSession().setAttribute(SessaoParamsUtils.MENSAGEM
						, "Erro: Usuario/Senha nao encontrados " );
				response.sendRedirect("index.jsp");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param usuario
	 */
	public void adicionarInformacoesSessao(HttpServletRequest request, Usuario usuario, String password) {
		request.getSession().setAttribute(SessaoParamsUtils.LOGIN, usuario.getLogin());
		request.getSession().setAttribute(SessaoParamsUtils.TIPOUSUARIOLOGADO, usuario.getTipoUsuario());
		request.getSession().setAttribute(SessaoParamsUtils.NOMEUSUARIOLOGADO, usuario.getNomeCompleto());
		request.getSession().setAttribute(SessaoParamsUtils.CPFUSUARIOLOGADO, usuario.getCpf());
		request.getSession().setAttribute(SessaoParamsUtils.EMAIL, usuario.getEmail());
	}
	
	
}
