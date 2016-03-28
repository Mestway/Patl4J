package data.memory;

import java.util.Hashtable;
import controller.util.Par;
import data.ILoginDAO;
import data.util.UsuarioExistenteException;
import entitites.Usuario;
import entitites.util.TipoUsuario;

public class LoginMemoryDAO implements ILoginDAO{

	/** login ==> (senha,Tipo Usuario)*/
	Hashtable< String, Par<String,String> > usuarios;
	
	public LoginMemoryDAO() {
		usuarios = new Hashtable< String, Par<String,String> >();
		adicionarUsuariosTemp();
	}
	
	private void adicionarUsuariosTemp() {
		usuarios.put(new String("alessandro"), new Par("123456",TipoUsuario.ADMINISTRADOR_CLINICO));
		usuarios.put(new String("tyago"), new Par("123456",TipoUsuario.ADMINISTRADOR_CLINICO));
		usuarios.put(new String("silvano"), new Par("123456",TipoUsuario.ADMINISTRADOR_CLINICO));
		
		usuarios.put(new String("drfreud"), new Par("123456",TipoUsuario.PSICOLOGO));
		usuarios.put(new String("drfinger"), new Par("123456",TipoUsuario.PROCTOLOGISTA));
		
		usuarios.put(new String("uira"), new Par("123456",TipoUsuario.PACIENTE));
		usuarios.put(new String("paulo"), new Par("123456",TipoUsuario.PACIENTE));
	}

	public String autenticar(String login,String senha) 
	{
		if (usuarios.get( login ) == null) {
			return TipoUsuario.NAO_CADASTRADO;
		}
		if ( usuarios.get( login ).getFirst().equals(senha) )
		{
			return usuarios.get( login ).getSecond();
		}
		return TipoUsuario.NAO_CADASTRADO;
	}

	public void cadastrarUsuario(Usuario novoPaciente) throws UsuarioExistenteException {
		if (usuarios.get( novoPaciente.getLogin() ) == null) {
			usuarios.put(novoPaciente.getLogin(), new Par(novoPaciente.getSenha()
					,novoPaciente.getTipoUsuario()));
		}
		else {
			throw new UsuarioExistenteException(novoPaciente.getLogin());
		}
	}

}
