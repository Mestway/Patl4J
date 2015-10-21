package com.enjava.seam.database;


import static org.jboss.seam.ScopeType.STATELESS;
import static org.jboss.seam.annotations.Install.APPLICATION;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityIdentifier;
import org.jboss.seam.framework.Identifier;
import org.jboss.seam.ui.AbstractEntityLoader;

/**
 * Stores entity identifiers under a key, which can be used on a page
 *
 * @author Pete Muir
 */

@Name("org.jboss.seam.ui.entityLoader")
@Install(precedence=APPLICATION, value=true, classDependencies="javax.persistence.EntityManager")
@Scope(STATELESS)
public class JpaEntityLoader extends AbstractEntityLoader<EntityManager>
{
	
	
	public JpaEntityLoader() {
		super();
		// TODO Auto-generated constructor stub
	}

	@In(value = "persistencia", required = true, create = true)
	PersistenciaBean persistencia;

   @Override
   protected Identifier createIdentifier(Object entity)
   {
      return new EntityIdentifier(entity, getPersistenceContext());
   }

   @Override
   protected String getPersistenceContextName()
   {
      return persistencia.getEntityManager();
   }
   
   @Override
   public void validate()
   {
      if (getPersistenceContext() == null)
      {
         throw new IllegalStateException("Unable to access a persistence context. You must either have a SMPC called entityManager or configure one in components.xml");
      }
      
   }
   
   public EntityManager getEntityManager()
   {
      return getPersistenceContext();
   }

   public void setEntityManager(EntityManager entityManager)
   {
      setPersistenceContext(entityManager);
   }
   
}
