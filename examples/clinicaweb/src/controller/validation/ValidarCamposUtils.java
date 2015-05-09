package controller.validation;


import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;


/**
 * Classe utilizada nos comandos para validar os tipos de
 * dados digitados
 * @author alessandro87
 *
 */
public class ValidarCamposUtils {


	private Hashtable<TiposFormulario, ValidarCmd> validacoes;

	private static ValidarCamposUtils instance;

	private ValidarCamposUtils() {
		/**
		 * Inicializando tabela de comandos.
		 */
		validacoes = new Hashtable<TiposFormulario, ValidarCmd>();
		
		validacoes.put(TiposFormulario.INTEIRO, new InteiroValidadoCmd());
		validacoes.put(TiposFormulario.CEP, new CEPValidadoCmd());
		validacoes.put(TiposFormulario.CPF, new CPFValidadoCmd());
		validacoes.put(TiposFormulario.DATA, new DataValidadaCmd());
		validacoes.put(TiposFormulario.GMAIL, new GmailValidadoCmd());
		validacoes.put(TiposFormulario.TELEFONE, new TelefoneValidadoCmd());
		validacoes.put(TiposFormulario.SENHA, new SenhaValidadoCmd());
		validacoes.put(TiposFormulario.HORA, new HoraValidadoCmd());

	}

	public static ValidarCamposUtils getInstance() {

		if (instance == null) {
			instance = new ValidarCamposUtils();
		}
		return instance;
	}

	/**
	 * Valida uma lista de variaveis em strings sob diferentes tipos.
	 * retorna as respectivas variáveis 
	 * @param varNomes
	 * @param valDigitados
	 * @param types
	 */
	public Vector<Object> validarCampos(Collection<String> varNomes, 
			Collection<String> valDigitados,Collection<TiposFormulario> types)
			throws CamposMalPreenchidosException{


		Vector<Object> result = new Vector<Object>();
		LinkedList<String> excecoes = new LinkedList<String>();

		Iterator<String> valDigIterator = valDigitados.iterator();
		Iterator<String> varNomesIterator = varNomes.iterator();
		Iterator<TiposFormulario> tiposIterator = types.iterator();

		while (varNomesIterator.hasNext()){
			String name = varNomesIterator.next();
			String val = valDigIterator.next();
			TiposFormulario type = tiposIterator.next();

			try {
				result.add((validacoes.get(type)).validar(val));
			}
			/*
			 * Pode ser NullException caso os formularios nao sejam preenchidos!
			 * NumFormatException caso tinham sido mal preenchidos
			 */
			catch (Exception e) {
				excecoes.add(name);
			}

		}
		if (!excecoes.isEmpty()){
			throw new CamposMalPreenchidosException(excecoes);
		}
		return result;

	}
}



