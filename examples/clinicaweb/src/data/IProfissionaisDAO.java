package data;

import java.util.LinkedList;

import data.util.UsuarioExistenteException;
import data.util.UsuarioNaoEncontradoException;
import entitites.specialists.Especialista;

public interface IProfissionaisDAO {

	LinkedList<Especialista> getProfissionais();

}
