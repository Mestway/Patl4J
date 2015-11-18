package data.memory;

import java.util.HashSet;
import java.util.Hashtable;
import controller.util.Par;
import data.IMedicamentosDAO;
import entitites.Medicamento;
import entitites.Paciente;
import entitites.specialists.Especialista;

public class MedicamentoMemoryDAO implements IMedicamentosDAO{

	/** IDMedicamento */
	HashSet< Medicamento > medicamentos;
	
	public MedicamentoMemoryDAO() {
		medicamentos = new HashSet<Medicamento>();
		//adicionarUsuariosTemp();
	}
	
//	private void adicionarUsuariosTemp() {
	//}	

	public void cadastrarMedicamento(Medicamento medicamento) {
		medicamento.setIdMedicamento(Integer.toString(medicamentos.size()));
		medicamentos.add(medicamento);
	}
	
	
}
