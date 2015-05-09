package data;

import entitites.Consulta;
import entitites.Paciente;
import entitites.Usuario;

public interface IConsultasDAO {
	public void cadastrarConsulta(Paciente paciente,Usuario profissional,Consulta consulta);

}
