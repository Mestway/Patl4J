package entitites;

/**
 * classe estendida por todo objeto persistente
 * 
 */
public abstract class ObjPersistente {

	int id;
	public int getId(){
		return id;
	}

	public void setId( int id ){
		this.id = id;
	}
}
