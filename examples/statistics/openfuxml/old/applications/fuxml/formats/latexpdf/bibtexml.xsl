<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:bibtex="http://bibtexml.sf.net/">
	<!-- ********************************************************************
		$Id: bibtexml.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
		********************************************************************
		*******************************************************************************
		| openFuXML open source                                                       |
		*******************************************************************************
		| Copyright (c) 2002-2006 openFuXML open source, University of Hagen          |
		|                                                                             |
		| This program is free software; you can redistribute it and/or               |
		| modify it under the terms of the GNU General Public License                 |
		| as published by the Free Software Foundation; either version 2              |
		| of the License, or (at your option) any later version.                      |
		|                                                                             |
		| This program is distributed in the hope that it will be useful,             |
		| but WITHOUT ANY WARRANTY; without even the implied warranty of              |
		| MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the               |
		| GNU General Public License for more details.                                |
		|                                                                             |
		| You should have received a copy of the GNU General Public License           |
		| along with this program; if not, write to the Free Software                 |
		| Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. |
		*******************************************************************************
		******************************************************************** -->	
	<xsl:output method="text"/>
	
	
	<xsl:template match="bibtex:file">
		<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="bibtex:entry">
		<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="bibtex:article|bibtex:book|bibtex:booklet|bibtex:conference|bibtex:inbook|bibtex:incollection|bibtex:inproceedings|
							bibtex:manual|bibtex:masterthesis|bibtex:misc|bibtex:phdthesis|bibtex:proceedings|bibtex:techreport|bibtex:unpublished">
		@<xsl:value-of select="local-name(current())"/>{<xsl:value-of select="parent::bibtex:entry/@id"/>,
		
			<xsl:apply-templates/>}
	</xsl:template>
	
	<xsl:template match="*"><xsl:value-of select="local-name()"/> ={<xsl:value-of select="."/>},
	
	</xsl:template>
	
	<xsl:template match="bibtex:note">note ={<xsl:value-of select="."/>},
	
	</xsl:template>

</xsl:stylesheet>
