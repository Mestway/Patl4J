package controller.commands;

import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.FachadaManager;


import controller.util.ConsultaParamsUtils;
import controller.util.Par;
import controller.util.SessaoParamsUtils;
import controller.validation.CamposMalPreenchidosException;
import controller.validation.CamposNaoPreenchidosException;
import controller.validation.TiposFormulario;
import controller.validation.ValidarCamposUtils;
import entitites.Consulta;
import entitites.Paciente;
import entitites.specialists.Especialista;

public class MarcarConsultaCmd extends AbstractCommand{

	public void execute(HttpServletRequest request, HttpServletResponse response) {

		try {
			camposEstaoPreenchidos(request);

			/**
			 * Capturando os parametros
			 */
			String login = request.getParameter(SessaoParamsUtils.LOGIN);

			String data = request.getParameter(ConsultaParamsUtils.DATA); //Validar DD/MM/AAAA
			String hora = request.getParameter(ConsultaParamsUtils.HORA);
			String duracao = request.getParameter(ConsultaParamsUtils.DURACAO);

			/**
			 * Validando Campos - Tipos
			 */
			Vector<String> varNames = new Vector<String>(3);
			varNames.add(ConsultaParamsUtils.DATA);
			varNames.add(ConsultaParamsUtils.HORA);
			varNames.add(ConsultaParamsUtils.DURACAO);


			Vector<String> varValues = new Vector<String>(3);
			varValues.add(data);
			varValues.add(hora);
			varValues.add(duracao);

			Vector<TiposFormulario> variaveis = new Vector<TiposFormulario>(3);
			variaveis.add(TiposFormulario.DATA);
			variaveis.add(TiposFormulario.HORA);
			variaveis.add(TiposFormulario.INTEIRO);

			Vector<Object> list = ValidarCamposUtils.getInstance().validarCampos
			(varNames, varValues, variaveis);

			int duracaoInt = (Integer)list.get(2);
			Par<Integer, Integer> tempo = (Par<Integer, Integer>)list.get(1);
			Date dataConsulta = (Date)list.get(0);

			Paciente paciente  = (Paciente)FachadaManager.getInstance().getUsuario(login);
			String loginlogado = (String)request.getSession().getAttribute(SessaoParamsUtils.LOGIN);
			Especialista especialista= (Especialista)FachadaManager.getInstance().getUsuario(loginlogado);

			Consulta consulta = new Consulta(especialista, paciente, dataConsulta,tempo, duracaoInt);
			
			FachadaManager.getInstance().marcarConsulta(consulta);
			
			request.getSession().setAttribute(SessaoParamsUtils.MENSAGEM
					, "Consulta marcada com sucesso." );
			redirecionarPaginaPrincipal(request,response);

		} catch (CamposNaoPreenchidosException e) {
			try {
				String msg = "Os campos " + e.getCamposErrados().toString() + " não foram preenchidos";
				tratarExcecaoGeradaNumaSessao(msg, request, response);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		catch (CamposMalPreenchidosException e) {
			try {
				String msg = "Os campos " + e.getCamposErrados().toString() + " foram mal preenchidos";
				tratarExcecaoGeradaNumaSessao(msg, request, response);

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		catch (ClassCastException e) {
			try {
				String msg = "Login Inválido";
				tratarExcecaoGeradaNumaSessao(msg, request, response);

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
