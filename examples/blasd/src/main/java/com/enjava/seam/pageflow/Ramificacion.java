package com.enjava.seam.pageflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Log;

import com.enjava.seam.pageflow.RamificacionI;


@Stateful
@Name("ramificacion")
@Scope(ScopeType.CONVERSATION)
public class Ramificacion implements RamificacionI, Serializable {

	@Remove
	@Destroy
	public void destroy() {

	}

	List<String> froms = new ArrayList<String>();
	@RequestParameter(value = "from")
	String from;

	@RequestParameter(value = "done")
	String done;
	@RequestParameter(value = "rama")
	String rama;

	@Logger
	Log log;

	public String getFrom() {
		int mayorQueUno = froms.size();
		if (done != null && froms.size() > 0) {
			froms.remove(froms.size() - 1);
			done = null;
		}
		if (froms.size() > 0)
			return froms.get(froms.size() - 1);
		else
			return from;
	}

	// se invoca una vez si el parametro param from!= null
	public void setFrom(String s) {
		boolean contiene = froms.contains(s);
		if (!contiene)
			froms.add(s);
		from = s;
		logFroms();
	}

	private void logFroms() {
		log.debug("------------- FROM" + from);
		log.debug("------------- FROMS" + froms);
		log.debug("------------- VIEWID"
				+ FacesContext.getCurrentInstance().getViewRoot().getViewId());
	}

}
