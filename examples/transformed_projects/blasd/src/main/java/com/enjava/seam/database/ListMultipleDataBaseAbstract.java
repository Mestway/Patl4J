package com.enjava.seam.database;

import javax.persistence.EntityManager;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.framework.EntityQuery;

public abstract class ListMultipleDataBaseAbstract<E> extends EntityQuery<E> {

	@In(value = "persistencia", required = true, create = true)
	PersistenciaBean persistencia;

	@Override
	public EntityManager getEntityManager() {
		String entityManager = persistencia
				.getEntityManager();
		Object instance = Component.getInstance(entityManager);
		return (EntityManager) instance;
	}


}
