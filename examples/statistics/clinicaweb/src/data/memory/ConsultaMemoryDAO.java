package data.memory;

import java.util.Hashtable;

import controller.util.Par;
import data.IConsultasDAO;
import entitites.Consulta;
import entitites.Paciente;
import entitites.Usuario;


public class ConsultaMemoryDAO implements IConsultasDAO{
	/** IdConsulta ==> (Paciente,Especialista)*/
	Hashtable< String, Par<String,String> > consultas;
	
	public ConsultaMemoryDAO() {
		consultas = new Hashtable< String, Par<String,String> >();
		//adicionarUsuariosTemp();
	}
	
//	private void adicionarUsuariosTemp() {
	//}	

	public void cadastrarConsulta(Paciente paciente,Usuario especialista,Consulta consulta) {
		consulta.setIdConsulta(Integer.toString(consultas.size()));
		consultas.put(consulta.getIdConsulta(), new Par(paciente.getLogin(),especialista.getLogin()));		
	}
	
	public  String getIdConsulta(){
		return Integer.toString(consultas.size());
	}
}
