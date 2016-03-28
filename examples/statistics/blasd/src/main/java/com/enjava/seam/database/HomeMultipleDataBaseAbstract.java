package com.enjava.seam.database;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.framework.EntityHome;


public abstract class HomeMultipleDataBaseAbstract<E> extends EntityHome<E> {

	@In(value = "persistencia", required = true, create = true)
	PersistenciaBean persistencia;

	@Override
	public EntityManager getEntityManager() {
		String entityManagerHome = persistencia
				.getEntityManager();
		Object instance2 = Component.getInstance(entityManagerHome);
		return (EntityManager) instance2;
	}



}
