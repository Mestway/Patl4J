<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<!-- ********************************************************************
     $Id: aufgaben.xsl,v 1.3 2007/01/11 13:38:26 gebhard Exp $
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
     This file contain the design-role for assignment-element einsendeaufgabe
     
     Use in Pass1 to make einsendeaufgaben work

     ********************************************************************
     Beschreibung:
     Dieses Stylesheet dient der Verarbeitung der Einsendeaufgabe, die im ersten Durchgang bereits auf eigene Seiten 	abgespalten und später mit dem aufgaben2.xsl Stylesheet in ihrem Aussehen beschrieben wird.
	Diese Zweiteilung ist leider notwendig, um die geforderten Verzeichnisssysteme und Trennung vom restlichen 	Material zu ermöglichen.
	Hier wird entschieden, welche Elemente später noch verarbeitet werden. Bei Task wird erst keine Lösung 		mitgegeben, bei Korrection ebend alles (Je nach Konfiguration von Korrection in der Config.xml-Datei).
	styles/einsendeaufgabe/structure/<?task?><?hints?><?corr-instructions?><?solution?>
	********************************************************************
-->

<!-- ==================================================================== -->
	
	<xsl:template match="einsendeaufgaben" mode="task">
		<xsl:attribute name="Modus">task</xsl:attribute>		
		<xsl:comment>Einsendeaufgaben, Modus Task - Comment</xsl:comment>
		<xsl:apply-templates>
			<xsl:with-param name="mode">task</xsl:with-param>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="einsendeaufgaben" mode="solution">
		<xsl:attribute name="Modus">solution</xsl:attribute>
		<xsl:comment>Einsendeaufgaben, Modus Solution - Comment</xsl:comment>
		<xsl:apply-templates>
			<xsl:with-param name="mode">solution</xsl:with-param>
		</xsl:apply-templates>
	</xsl:template>

	<xsl:template match="einsendeaufgaben" mode="correction">
		<xsl:attribute name="Modus">correction</xsl:attribute>
		<xsl:comment>Einsendeaufgaben, Modus Correction - Comment</xsl:comment>
		<xsl:apply-templates>
			<xsl:with-param name="mode">correction</xsl:with-param>
		</xsl:apply-templates>
	</xsl:template>

	<!-- ==================================================================== -->
	<!-- Environments-->
	<!-- ==================================================================== -->

	<xsl:template name="environment.selector">
		<xsl:param name="style"/>
		<xsl:param name="mode"/>
		<xsl:choose>
			<xsl:when test="$style/envtype='labelbox' or $style/envtype='breakbox'">
				<xsl:call-template name="labelbox"><xsl:with-param name="style" select="$style"/></xsl:call-template>
			</xsl:when>
			<xsl:when test="$style/envtype='genericbox'">
				<xsl:call-template name="genericbox"><xsl:with-param name="style" select="$style"/></xsl:call-template>
			</xsl:when>
			<xsl:when test="$style/envtype='nobox'">
				<xsl:call-template name="nobox"><xsl:with-param name="style" select="$style"/></xsl:call-template>
			</xsl:when>
			<xsl:when test="$style/envtype='breakbox'">
				<xsl:call-template name="breakbox"><xsl:with-param name="style" select="$style"/></xsl:call-template>
			</xsl:when>
			<xsl:when test="$style/envtype='assignment'">
				<xsl:call-template name="assignment">
					<xsl:with-param name="style" select="$style"/>
					<xsl:with-param name="mode" select="$mode"/>
				</xsl:call-template>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="labelbox">
		<xsl:param name="style"/>
		<xsl:param name="mode"/>
		<xsl:param name="contextnode"/>

		<xsl:apply-templates select="$style/label">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
		</xsl:apply-templates>
		<xsl:apply-templates select="node()[name()!='zwischentitel'][name()!='titel']">
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template name="breakbox">
		<xsl:param name="style"/>
		<xsl:param name="mode"/>
		<xsl:param name="contextnode"/>

		<xsl:apply-templates select="$style/label">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
		</xsl:apply-templates>
		<xsl:apply-templates select="node()[name()!='zwischentitel'][name()!='titel']">
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="label">
		<xsl:param name="contextnode"/>
		<xsl:param name="style"/>
			<xsl:apply-templates select="node()">
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
	</xsl:template>	
	
	<!-- ==================================================================== -->
	<xsl:template name="genericbox">
		<xsl:param name="style"/>
		<xsl:param name="mode"/>
			<xsl:apply-templates select="$style/marg">
				<xsl:with-param name="contextnode" select="current()"/>
				<xsl:with-param name="style" select="$style"/>
			</xsl:apply-templates>
		<xsl:apply-templates select="$style/structure">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>

	<xsl:template name="box">
		<xsl:param name="style"/>
		<xsl:param name="mode"/>
		<xsl:apply-templates select="$style/marg">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
		</xsl:apply-templates>
		<xsl:apply-templates select="node()[name()!='zwischentitel'][name()!='titel']">
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>
	<!-- ==================================================================== -->
	<xsl:template name="nobox">
		<xsl:param name="style"/>
		<xsl:param name="mode"/>
		<xsl:apply-templates>
			<xsl:with-param name="style" select="$style"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>
	<!-- ==================================================================== -->
	<xsl:template name="list">
		<xsl:param name="style"/>
		<xsl:param name="mode"/>
		<xsl:apply-templates>
			<xsl:with-param name="style" select="$style"/>
		</xsl:apply-templates>
	</xsl:template>
	<!-- ==================================================================== -->

	<xsl:template name="assignment">
		<xsl:param name="style"/>
		<xsl:param name="mode"/>
		<xsl:param name="Pfad"/>
		
		<xsl:apply-templates select="$style/label">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>
		
		<xsl:apply-templates select="$style/marg">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>
		
		<xsl:apply-templates select="$style/structure">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>

	</xsl:template>
	<!-- ==================================================================== -->
</xsl:stylesheet>