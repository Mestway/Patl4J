<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<!-- ********************************************************************
     $Id: templates.xsl,v 1.3 2007/01/11 13:38:26 gebhard Exp $
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
     
     Description:
     This file contains the template functions. Many functions uses this templates
     
     Use in Pass 1 to 5:
	
	*******************************************************************
	Beschreibung:
	Diese Datei enthält templates, die in fast allen Funktionen benötigt werden.
	Sie stellen wesentliche Informationen, wie z.B. die standardattribute bereit
     ********************************************************************
-->
	<xsl:template name="ebene">
		<xsl:value-of select="count(ancestor-or-self::abschnitt)"/>
	</xsl:template>

	<xsl:template name="lang">
		<xsl:if test="@xml:lang">
			<xsl:attribute name="lang"><xsl:value-of select="@xml:lang"/></xsl:attribute>
		</xsl:if>
	</xsl:template>

	<xsl:template name="class">
		<xsl:attribute name="class"><xsl:value-of select="name(.)"/></xsl:attribute>
	</xsl:template>

	<xsl:template name="id">
		<!-- generiere immer eine id ... -->
		<xsl:attribute name="id"><xsl:value-of select="generate-id()"/></xsl:attribute>
		<!-- ... ausser wenn der Nutzer eine eigene id genannt hat, dann verwende diese -->
		<xsl:if test="@id">
			<xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
		</xsl:if>
	</xsl:template>

	<xsl:template name="gruppe"/>	<!-- Muss noch besprochen werden--> 
	
	<xsl:template name="standardattribute">
		<xsl:call-template name="class"/>
		<xsl:call-template name="id"/>
		<xsl:call-template name="lang"/>
	</xsl:template>

</xsl:stylesheet>
