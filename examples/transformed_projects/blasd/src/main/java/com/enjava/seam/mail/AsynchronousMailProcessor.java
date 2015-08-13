package com.enjava.seam.mail;

import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.async.Duration;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.faces.Renderer;

@Name("asynchronousMailProcessor")
@AutoCreate
public class AsynchronousMailProcessor
{
   @Asynchronous
   public void scheduleSend(@Duration long delay, UsuarioEnJava person) {
      try {
         Contexts.getEventContext().set("usuarioEnJava", person);
         Renderer.instance().render("/mailSimple.xhtml");
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   @Asynchronous
   public void scheduleSend(@Duration long delay, MailContacto mc, PersonaContacto pc, String view) {
	   try {
		   
		   Contexts.getEventContext().set("mailContacto", mc);
		   Contexts.getEventContext().set("personaContacto", pc);
		   Renderer.instance().render(view);
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
   }
   @Asynchronous
   public void scheduleSend(@Duration long delay, Object componente, String id, String view) {
	   try {
		   Contexts.getEventContext().set(id, componente);
		   Renderer.instance().render(view);
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
   }
}
