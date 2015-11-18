<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<!-- ********************************************************************
     $Id: demux-html.xsl,v 1.6 2007/02/01 15:14:10 gebhard Exp $
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
     This file is the headerfile of pass2 in processing. It linera the FILE-elements under a document-element
     
     Use in Pass2: linearisation of files

     ********************************************************************
     Beschreibung:
     Dieses Stylesheet ist das Herz der zweiten Prozessstufe, in der die FILE-Elemente, die über Inhalt(Kindelemente) 	verfügen, aus der ersten Stufe 	(../common/demux.xsl) linearisiert unter ein DOCUMENT-Element gesetzt werden.
	Zusätzlich werden fehlende DESIGN und DIRECTORY-Attribute ergänzt.
     
     ********************************************************************
-->
	<xsl:import href="../common/designinterface.xsl"/>
	<xsl:include href="../common/mediainformation.xsl"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:key name="kc" match="*[not(ancestor::filecontent)]" use="@id"/>

<xsl:template match="/">
<document>
	<xsl:apply-templates/>
</document>
</xsl:template>

<xsl:template match="filecontent|kurs[ancestor::filecontent]">
	<xsl:apply-templates/>
</xsl:template>

<xsl:template match="file">
	<xsl:variable name="id"><xsl:value-of select="descendant::*[1]/@id"/></xsl:variable>
	<xsl:variable name="knr" select="key('kc',$id)/ancestor::kurseinheit/@number"/>        
	<xsl:variable name="style" select="$styles/entry[@name='kurseinheit'][@number=$knr]"/>
	<xsl:variable name="designdatei">
		<xsl:apply-templates select="$style/structure[@format='html']/file/designtemplate">
			<xsl:with-param name="contextnode" select="key('kc',$id)"/>
		</xsl:apply-templates>
	</xsl:variable>
	<xsl:variable name="dir">
		<xsl:apply-templates select="$style/structure[@format='html']/file/directory">
			<xsl:with-param name="contextnode" select="key('kc',$id)"/>
		</xsl:apply-templates>
	</xsl:variable>        
	<!-- if (string-length($design) lt 1) then $design else $designdatei -->
	<!-- if (string-length($directory) lt 1) then $directory else $dir -->
	<xsl:message>Verarbeite File: <xsl:value-of select="@filename"/></xsl:message>
	<xsl:variable name="design-temp" select="if (string-length(@design) gt 1) then @design else parent::*/@design"/>
	<xsl:variable name="directory-temp" select="if (string-length(@directory) gt 1) then @directory else parent::*/@directory"/>
	<xsl:variable name="design" select="if (string-length($design-temp) gt 1) then $design-temp else $designdatei"/>
	<xsl:variable name="directory" select="if (string-length($directory-temp) gt 1) then $directory-temp else $dir"/>
	<xsl:variable name="Modus" select="if (string-length(@Modus) gt 1) then @Modus else 'solution'"/>		
	<xsl:if test="descendant::*">	<!-- Erzeuge nur dann eine Datei, wenn auch Inhalt vorhanden ist -->
		<xsl:copy>
			<xsl:copy-of select="@*"/>
			<xsl:attribute name="design" select="normalize-space($design)"/>
			<xsl:attribute name="directory" select="normalize-space($directory)"/>			
			<xsl:attribute name="kurseinheit" select="if (ancestor::kurseinheit/@number) then ancestor::kurseinheit/@number else 'kein'"/>
			<xsl:attribute name="Modus"  select="normalize-space($Modus)"/>									<xsl:copy-of select="*[not(name()='file')][not(name()='kurs')][not(name()='kurseinheit')]"/>
		</xsl:copy>
		</xsl:if>
	<xsl:apply-templates select="file|kurs|kurseinheit|selbsttestaufgabe|uebungsaufgabe"/>
</xsl:template>

<xsl:template match="metadata|titelseite"/>
	
	<xsl:template match="selbsttestaufgabe|uebungsaufgabe">
		<xsl:apply-templates select="file"/>
	</xsl:template>

<xsl:template match="kurs[parent::document]">
	<kurs>
		<xsl:copy-of select="node()|@*"/>
	</kurs>
</xsl:template>

<xsl:template match="copy">
	<copy from="{@from}" todir="{@todir}/" recursive="{@recursive}"/>
</xsl:template>

</xsl:stylesheet>