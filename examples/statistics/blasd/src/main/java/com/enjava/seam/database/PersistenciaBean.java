package com.enjava.seam.database;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("persistencia")
@Scope(ScopeType.SESSION)
public class PersistenciaBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String entityManager;



	public String getEntityManager() {
            return entityManager;
    }

    public void setEntityManager(String entityManager) {
            this.entityManager = entityManager;
    }
    

}
