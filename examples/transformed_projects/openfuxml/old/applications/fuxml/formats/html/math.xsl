<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<!-- ********************************************************************
     $Id: math.xsl,v 1.5 2007/02/27 13:45:30 gebhard Exp $
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
     This file contains the functions for elements of formula.
     
     Use in Pass 4
	
	*******************************************************************
	Beschreibung:
	Diese Datei enthält die Designvorschriften für die Formelumgebungen
     ********************************************************************
-->

<!--####################################### Mathematische Elemente ############################################## -->

	<xsl:template match="formel|formelarray">
	<xsl:variable name="Pfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
		<div>
			<xsl:call-template name="standardattribute"/>
			<img src="{concat('math/',@hash,'.png')}" alt="{.}" class="{concat('m',@hash)}"/>
			<!-- Nummerierung wird nun in den Latexbildern angezeigt -->
			<!--	<span class="nummer"><xsl:value-of select="@number"/></span> -->
		</div>
	</xsl:template>

	
	<xsl:template match="formel-imtext">
	<xsl:variable name="Pfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
		<span>
			<xsl:call-template name="standardattribute"/>
			<img src="{concat('math/',@hash,'.png')}" alt="{.}" class="{concat('m',@hash)}"/>
		</span>
	</xsl:template>
	<xsl:template match="formel[parent::absatz]|formel[parent::absatz-klein]|formel[parent::absatz-mini]|formel[parent::absatz-ohne]|formelarray[parent::absatz]|formelarray[parent::absatz-klein]|formelarray[parent::absatz-mini]|formelarray[parent::absatz-ohne]">
	<xsl:variable name="Pfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
		<xsl:variable name="absatzhack1"><xsl:text><![CDATA[</p>]]></xsl:text></xsl:variable>
		<xsl:variable name="absatzhack2"><xsl:text><![CDATA[<p class="]]></xsl:text><xsl:value-of select="(name(parent::node()))"/><xsl:text><![CDATA[">]]></xsl:text></xsl:variable>
	<xsl:value-of select="$absatzhack1" disable-output-escaping="yes"/>
		<xsl:comment>closing para hack - math.xsl</xsl:comment>
		<div>
			<xsl:call-template name="standardattribute"/>
			<img src="{concat('math/',@hash,'.png')}" alt="{.}" class="{concat('m',@hash)}"/>
			<!-- Nummerierung wird nun in den Latexbildern angezeigt -->
			<!--	<span class="nummer"><xsl:value-of select="@number"/></span> -->
		</div>
	<xsl:value-of select="$absatzhack2" disable-output-escaping="yes"/>
 		<xsl:comment>starting para hack - math.xsl</xsl:comment>
	</xsl:template>
</xsl:stylesheet>