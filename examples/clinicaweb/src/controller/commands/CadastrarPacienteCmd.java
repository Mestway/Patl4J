package controller.commands;


import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.FachadaManager;
import business.util.ProfissionalNaoEncontradoException;


import controller.util.MedicamentoParamsUtils;
import controller.util.SessaoParamsUtils;
import controller.util.UsuarioParamsUtils;
import controller.validation.CamposMalPreenchidosException;
import controller.validation.CamposNaoPreenchidosException;
import controller.validation.TiposFormulario;
import controller.validation.ValidarCamposUtils;
import data.util.UsuarioExistenteException;
import entitites.Paciente;
import entitites.specialists.Especialista;

/**
 * Implementa um cadastro de paciente realizado
 * por um profissional cadastrado
 * @author alessandro87
 *
 */
public class CadastrarPacienteCmd extends AbstractCommand{

	public void execute(HttpServletRequest request, HttpServletResponse response) {

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
			

			/**
			 * Capturando os parametros
			 */
			String login = request.getParameter(SessaoParamsUtils.LOGIN);
			String senha = request.getParameter(SessaoParamsUtils.SENHA);
			
			String nome = request.getParameter(UsuarioParamsUtils.NOME);
			String sexo = request.getParameter(UsuarioParamsUtils.SEXO);
			String cpf = request.getParameter(UsuarioParamsUtils.CPF);
			String dataNasc = request.getParameter(UsuarioParamsUtils.DATANASC); //Validar DD/MM/AAAA
			String escolaridade = request.getParameter(UsuarioParamsUtils.ESCOLARIDADE);
			String profissao = request.getParameter(UsuarioParamsUtils.PROFISSAO);
			String estadoCivil = request.getParameter(UsuarioParamsUtils.ESTADOCIVIL);

			/**
			 * dados para contato
			 */

			String cel = request.getParameter(UsuarioParamsUtils.CELULAR);
			String tel = request.getParameter(UsuarioParamsUtils.TELEFONE);
			String email =  request.getParameter(UsuarioParamsUtils.EMAIL);
			String gmail =  request.getParameter(UsuarioParamsUtils.GMAIL);

			/**
			 * dados de endereço
			 */
			String cep = request.getParameter(UsuarioParamsUtils.CEP);
			String rua = request.getParameter(UsuarioParamsUtils.RUA);
			String complemento = request.getParameter(UsuarioParamsUtils.COMPLEMENTO);
			String bairro = request.getParameter(UsuarioParamsUtils.BAIRRO);
			String estado = request.getParameter(UsuarioParamsUtils.ESTADO);
			String cidade = request.getParameter(UsuarioParamsUtils.CIDADE);
			String numero = request.getParameter(UsuarioParamsUtils.NUMERO);//Validar-Int

			/**
			 * Validando Campos - Tipos
			 */
			Vector<String> varNames = new Vector<String>(6);
			varNames.add(SessaoParamsUtils.SENHA);
			varNames.add(UsuarioParamsUtils.NUMERO);
			varNames.add(UsuarioParamsUtils.CPF);
			varNames.add(UsuarioParamsUtils.CEP);
			varNames.add(UsuarioParamsUtils.DATANASC);
			varNames.add(UsuarioParamsUtils.GMAIL);


			Vector<String> varValues = new Vector<String>(6);
			varValues.add(senha);
			varValues.add(numero);
			varValues.add(cpf);
			varValues.add(cep);
			varValues.add(dataNasc);
			varValues.add(gmail);

			Vector<TiposFormulario> variaveis = new Vector<TiposFormulario>(6);
			variaveis.add(TiposFormulario.SENHA);
			variaveis.add(TiposFormulario.INTEIRO);
			variaveis.add(TiposFormulario.CPF);
			variaveis.add(TiposFormulario.CEP);
			variaveis.add(TiposFormulario.DATA);
			variaveis.add(TiposFormulario.GMAIL);

			Vector<Object> list = ValidarCamposUtils.getInstance().validarCampos
			(varNames, varValues, variaveis);

			/**
			 * Pegando as variaveis que passaram de string para algum
			 * tipo especifico.
			 */
			int numI = (Integer)list.get(1);
			Date dataD = (Date)list.get(4);

			Paciente novoPaciente = new Paciente(login, senha, nome, dataD, cpf, cep
					, email, tel,cel);

			novoPaciente.setGmail(gmail);
			novoPaciente.setNumero(numI);
			novoPaciente.setSexo(sexo);
			novoPaciente.setEscolaridade(escolaridade);
			novoPaciente.setProfissao(profissao);
			novoPaciente.setEstadoCivil(estadoCivil);
			novoPaciente.setRua(rua);
			novoPaciente.setComplemento(complemento);
			novoPaciente.setBairro(bairro);
			novoPaciente.setEstado(estado);
			novoPaciente.setCidade(cidade);

			FachadaManager.getInstance().cadastrarUsuario(novoPaciente);
			request.getSession().setAttribute(SessaoParamsUtils.MENSAGEM
					, "Paciente cadastrado com sucesso." );
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


