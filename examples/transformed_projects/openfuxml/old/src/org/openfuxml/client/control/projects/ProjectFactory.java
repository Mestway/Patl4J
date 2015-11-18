package org.openfuxml.client.control.projects;

import java.util.List;

import org.openfuxml.model.ejb.OfxProject;

public interface ProjectFactory
{
	public List<OfxProject> lProjects(String application);
}
