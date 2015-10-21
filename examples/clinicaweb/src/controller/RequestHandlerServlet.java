package controller;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.commands.AbstractCommand;
import controller.commands.AdicionarInformacaoPsicologicaCmd;
import controller.commands.CadastrarPacienteCmd;
import controller.commands.CadastrarEspecialistaCmd;
import controller.commands.DeslogarCommand;
import controller.commands.DesmarcarConsultaCmd;
import controller.commands.DesmarcarMedicamentoCmd;
import controller.commands.LogarCommand;
import controller.commands.MarcarConsultaCmd;
import controller.commands.MarcarMedicamentoCmd;
import controller.util.CommandsUtils;

/**
 * Servlet implementation class RequestHandlerServlet
 * Unico Servlet que trata todas os formularios do Sistema
 */
public class RequestHandlerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private Hashtable<String, AbstractCommand> commands;
	
    /**
     * Default constructor. 
     */
    public RequestHandlerServlet() {
    }

    /**
     * Configura o Servlet adicionando todos os comandos
     * a lista de comandos
     */
    public void init (ServletConfig config) {
    	commands = new Hashtable<String, AbstractCommand>();
    	
    	commands.put(CommandsUtils.cadastrarPacienteCmdString, new CadastrarPacienteCmd());
    	commands.put(CommandsUtils.cadastrarProfissionalCmdString, new CadastrarEspecialistaCmd());
    	commands.put(CommandsUtils.logarCmdString, new LogarCommand());
    	commands.put(CommandsUtils.deslogarCmdString, new DeslogarCommand());
    	commands.put(CommandsUtils.adicionarInformacoesPsicologicas, new AdicionarInformacaoPsicologicaCmd());
    	commands.put(CommandsUtils.marcarConsultaCmdString, new MarcarConsultaCmd());
    	commands.put(CommandsUtils.desmarcarConsultaCmdString, new DesmarcarConsultaCmd());
    	commands.put(CommandsUtils.marcarMedicamentoCmdString, new MarcarMedicamentoCmd());
    	commands.put(CommandsUtils.desmarcarMedicamentoCmdString, new DesmarcarMedicamentoCmd());
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestion = request.getParameter(CommandsUtils.cmdString);
		((AbstractCommand)commands.get(requestion)).execute(request, response);
		
	}

}
