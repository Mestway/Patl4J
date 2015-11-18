package controller.commands;

import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.FachadaManager;

import controller.util.EspecialistaParamsUtils;
import controller.util.SessaoParamsUtils;
import controller.util.UsuarioParamsUtils;
import controller.validation.CamposMalPreenchidosException;
import controller.validation.CamposNaoPreenchidosException;
import controller.validation.TiposFormulario;
import controller.validation.ValidarCamposUtils;
import data.util.UsuarioExistenteException;
import entitites.specialists.Especialista;
import entitites.specialists.Proctologista;
import entitites.specialists.Psicologo;
import entitites.util.TipoUsuario;

public class CadastrarEspecialistaCmd extends AbstractCommand {

	public void execute(HttpServletRequest request, HttpServletResponse response) 
	{
		try {
			
			LinkedList<String> camposNaoObrigatorios = new LinkedList<String>();
			camposNaoObrigatorios.add(UsuarioParamsUtils.CELULAR);
			camposNaoObrigatorios.add(UsuarioParamsUtils.TELEFONE);
			camposNaoObrigatorios.add(UsuarioParamsUtils.GMAIL);
			camposNaoObrigatorios.add(UsuarioParamsUtils.EMAIL);
			camposNaoObrigatorios.add(UsuarioParamsUtils.RUA);
			camposNaoObrigatorios.add(UsuarioParamsUtils.COMPLEMENTO);
			camposNaoObrigatorios.add(UsuarioParamsUtils.BAIRRO);
			camposNaoObrigatorios.add(UsuarioParamsUtils.ESTADO);
			camposNaoObrigatorios.add(UsuarioParamsUtils.CIDADE);
			camposNaoObrigatorios.add(UsuarioParamsUtils.NUMERO);
			camposEstaoPreenchidos(request, camposNaoObrigatorios);
			
			/*
			 * Dados profissionais
			 * */
			String especialidade = request.getParameter(EspecialistaParamsUtils.ESPECIALIDADE);
			String serv_str = request.getParameter(EspecialistaParamsUtils.SERVICOS);
			String anoInicio =  request.getParameter(EspecialistaParamsUtils.ANO_INICIO);
			
			/* Retira os servicos separados por virgula*/
			LinkedList<String> servicos = this.retirandoParametrosSeparadosVirgula(serv_str, ','); 
				new LinkedList<String>( );
			
			/*
			 * Dados pessoais
			 * */
			String login = request.getParameter(SessaoParamsUtils.LOGIN);
			String senha = request.getParameter(SessaoParamsUtils.SENHA);
			
			String nome = request.getParameter(UsuarioParamsUtils.NOME);
			String cpf = request.getParameter(UsuarioParamsUtils.CPF);

			String email =  request.getParameter(UsuarioParamsUtils.EMAIL);
			String gmail =  request.getParameter(UsuarioParamsUtils.GMAIL);
			
			String dataNasc = request.getParameter(UsuarioParamsUtils.DATANASC); //Validar DD/MM/AAAA

			String sexo = request.getParameter(UsuarioParamsUtils.SEXO);

			String estadoCivil = request.getParameter(UsuarioParamsUtils.ESTADOCIVIL);
			
			String escolaridade = request.getParameter(UsuarioParamsUtils.ESCOLARIDADE);
			
			String cel = request.getParameter(UsuarioParamsUtils.CELULAR);
			String tel = request.getParameter(UsuarioParamsUtils.TELEFONE);

			String rua = request.getParameter(UsuarioParamsUtils.RUA);
			String numero = request.getParameter(UsuarioParamsUtils.NUMERO);//Validar-Int
			String cep = request.getParameter(UsuarioParamsUtils.CEP);
			String complemento = request.getParameter(UsuarioParamsUtils.COMPLEMENTO);
			String bairro = request.getParameter(UsuarioParamsUtils.BAIRRO);
			String estado = request.getParameter(UsuarioParamsUtils.ESTADO);
			String cidade = request.getParameter(UsuarioParamsUtils.CIDADE);

			/**
			 * Validando os Campos - Tipos
			 */
			Vector<String> varNames = new Vector<String>(9);
			Vector<String> varValues = new Vector<String>(9);
			Vector<TiposFormulario> variaveis = new Vector<TiposFormulario>(9);

			varNames.add(SessaoParamsUtils.SENHA);
			varValues.add(senha);
			variaveis.add(TiposFormulario.SENHA);

			varNames.add(UsuarioParamsUtils.NUMERO);
			varValues.add(numero);
			variaveis.add(TiposFormulario.INTEIRO);
			
			varNames.add(UsuarioParamsUtils.TELEFONE);
			varValues.add(tel);
			variaveis.add(TiposFormulario.TELEFONE);
			
			varNames.add(UsuarioParamsUtils.CELULAR);
			varValues.add(cel);
			variaveis.add(TiposFormulario.TELEFONE);

			varNames.add(UsuarioParamsUtils.CPF);
			varValues.add(cpf);
			variaveis.add(TiposFormulario.CPF);
			
			varNames.add(UsuarioParamsUtils.CEP);
			varValues.add(cep);
			variaveis.add(TiposFormulario.CEP);

			varNames.add(UsuarioParamsUtils.DATANASC);
			varValues.add(dataNasc);
			variaveis.add(TiposFormulario.DATA);

			
			varNames.add(UsuarioParamsUtils.GMAIL);
			varValues.add(gmail);
			variaveis.add(TiposFormulario.GMAIL);
			
			varNames.add(EspecialistaParamsUtils.ANO_INICIO);
			varValues.add(anoInicio);
			variaveis.add(TiposFormulario.INTEIRO);

			Vector<Object> list = ValidarCamposUtils.getInstance().validarCampos(varNames, varValues, variaveis);
			
			/**
			 * Pegando as variaveis que passaram de string para algum
			 * tipo especifico.
			 */
			int numI = (Integer)list.get(1);
			Date dataD = (Date)list.get(6);
			
			int anoInicioInt = (Integer)list.get(8);

			Especialista especialista;
			if( especialidade.equals(TipoUsuario.PSICOLOGO) )
			{
				especialista = new Psicologo(login,senha,nome,dataD,cpf,cep,email,tel,cel,servicos,anoInicioInt);
			}
//			if( especialidade.equals(TipoUsuario.PROCTOLOGISTA) )
			//{
				especialista = new Proctologista(login,senha,nome,dataD,cpf,cep,email,tel,cel,servicos,anoInicioInt);
			//}
			
			especialista.setGmail(gmail);
			especialista.setNumero(numI);
			especialista.setSexo(sexo);
			especialista.setEscolaridade(escolaridade);
			especialista.setEstadoCivil(estadoCivil);
			especialista.setRua(rua);
			especialista.setComplemento(complemento);
			especialista.setBairro(bairro);
			especialista.setEstado(estado);
			especialista.setCidade(cidade);

			FachadaManager.getInstance().cadastrarUsuario(especialista);
			
			request.getSession().setAttribute(SessaoParamsUtils.MENSAGEM
					, "Especialista cadastrado com sucesso." );
			redirecionarPaginaPrincipal(request,response);

		} 
		catch (CamposNaoPreenchidosException e) {
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
		catch (UsuarioExistenteException e) {
			try {
				String msg = "O usuario " + e.getNome() + " já existe";
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
