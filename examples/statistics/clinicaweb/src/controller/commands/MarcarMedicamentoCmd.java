package controller.commands;

import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.FachadaManager;

import controller.util.MedicamentoParamsUtils;
import controller.util.Par;
import controller.util.SessaoParamsUtils;
import controller.validation.CamposMalPreenchidosException;
import controller.validation.CamposNaoPreenchidosException;
import controller.validation.TiposFormulario;
import controller.validation.ValidarCamposUtils;
import data.util.UsuarioNaoEncontradoException;
import entitites.Medicamento;
import entitites.Paciente;
import entitites.specialists.Especialista;
import entitites.util.Repeticao;

public class MarcarMedicamentoCmd extends AbstractCommand {

	public void execute(HttpServletRequest request, HttpServletResponse response) 
	{
		try {
			LinkedList<String> camposNaoObrigatorios = new LinkedList<String>();
			camposNaoObrigatorios.add(MedicamentoParamsUtils.OBSERVACOES);
			camposEstaoPreenchidos(request, camposNaoObrigatorios);

			/*
			 * Logins do especialista e do paciente
			 * */
			String especialistaStr = (String)request.getSession().getAttribute(SessaoParamsUtils.LOGIN);
			String pacienteStr = request.getParameter(SessaoParamsUtils.LOGIN);
			Especialista espec = (Especialista)FachadaManager.getInstance().getUsuario(especialistaStr);
			
			Paciente pac = null;
			try {
				pac = (Paciente)FachadaManager.getInstance().getUsuario(pacienteStr);

			} 
			catch (ClassCastException e1) {
				String msg = "O usuário \'"+ pacienteStr +"\' não é um paciente.";
				tratarExcecaoGeradaNumaSessao(msg, request, response);
			}


			/*
			 * Dados do medicamento
			 * */
			String medicamento = request.getParameter(MedicamentoParamsUtils.MEDICAMENTO); 
			String quantidade  = request.getParameter(MedicamentoParamsUtils.QUANTIDADE); 
			String observacoes = request.getParameter(MedicamentoParamsUtils.OBSERVACOES); 

			String data_inicio = request.getParameter(MedicamentoParamsUtils.DATAINICIO); 
			String data_fim    = request.getParameter(MedicamentoParamsUtils.DATAFIM);


			String frequencia  = request.getParameter(MedicamentoParamsUtils.HORAS); 
			LinkedList<String> horasString = this.retirandoParametrosSeparadosVirgula(frequencia, ',');
			LinkedList<Par<Integer,Integer>> horas = new LinkedList<Par<Integer,Integer>>();
			
			for (String str : horasString) {
				Vector<String> varNames = new Vector<String>(1);
				Vector<String> varValues = new Vector<String>(1);
				Vector<TiposFormulario> variaveis = new Vector<TiposFormulario>(1);
				
				varNames.addElement(MedicamentoParamsUtils.HORAS);
				varValues.addElement(str);
				variaveis.addElement(TiposFormulario.HORA);
				
				horas.add((Par<Integer,Integer>)ValidarCamposUtils.getInstance().validarCampos(varNames,varValues,variaveis).get(0));
			}
			
			
			String domingo = request.getParameter(MedicamentoParamsUtils.DOMINGO); 
			String segunda = request.getParameter(MedicamentoParamsUtils.SEGUNDA); 
			String terca   = request.getParameter(MedicamentoParamsUtils.TERCA); 
			String quarta  = request.getParameter(MedicamentoParamsUtils.QUARTA); 
			String quinta  = request.getParameter(MedicamentoParamsUtils.QUINTA); 
			String sexta   = request.getParameter(MedicamentoParamsUtils.SEXTA); 
			String sabado  = request.getParameter(MedicamentoParamsUtils.SABADO); 

			/**
			 * Validando os Campos - Tipos
			 */
			Vector<String> varNames = new Vector<String>(2);
			Vector<String> varValues = new Vector<String>(2);
			Vector<TiposFormulario> variaveis = new Vector<TiposFormulario>(2);

			varNames.add(MedicamentoParamsUtils.DATAINICIO);
			varValues.add(data_inicio);
			variaveis.add(TiposFormulario.DATA);

			varNames.add(MedicamentoParamsUtils.DATAFIM);
			varValues.add(data_fim);
			variaveis.add(TiposFormulario.DATA);


			Vector<Object> list = ValidarCamposUtils.getInstance().validarCampos
			(varNames, varValues, variaveis);


			/**
			 * Pegando as variaveis que passaram de string para algum
			 * tipo especifico.
			 */
			Date dataI = (Date)list.get(0);
			Date dataF = (Date)list.get(1);

			Repeticao repet = new Repeticao();

			repet.setDomingo(false);
			repet.setSegunda(false);
			repet.setTerca(false);
			repet.setQuarta(false);
			repet.setQuinta(false);
			repet.setSexta(false);
			repet.setSabado(false);

			if( domingo != null )
				repet.setDomingo(true);
			if( segunda != null )
				repet.setSegunda(true);
			if( terca != null )
				repet.setTerca(true);
			if( quarta != null )
				repet.setQuarta(true);
			if( quinta != null )
				repet.setQuinta(true);
			if( sexta != null )
				repet.setSexta(true);
			if( sabado != null )
				repet.setSabado(true);

			Medicamento medic = new Medicamento();

			medic.setNome(medicamento);
			medic.setDataInicio(dataI);
			medic.setDataFim(dataF);
			medic.setDose(quantidade);
			medic.setObservacoes(observacoes);
			medic.setRepeticao(repet);
			medic.setProfissional(espec);
			medic.setPaciente(pac);
			medic.setHorarios(horas);

			FachadaManager.getInstance().marcarMedicamento(medic);

			request.getSession().setAttribute(SessaoParamsUtils.MENSAGEM
					, "Medicamento marcado com sucesso." );
			redirecionarPaginaPrincipal(request,response);
		} 
		catch (CamposNaoPreenchidosException e) {
			try {
				String msg = "Os campos " + e.getCamposErrados().toString() + " não foram preenchidos";
				tratarExcecaoGeradaNumaSessao(msg, request, response);
			} 
			catch (Exception e1) {
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
		catch (UsuarioNaoEncontradoException e) {
			try {
				String msg = "Usuário "+ e.getName() +" não existente.";
				tratarExcecaoGeradaNumaSessao(msg, request, response);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}


	}

}
