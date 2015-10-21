<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<!-- ********************************************************************
     $Id: xref.xsl,v 1.3 2007/01/11 13:38:26 gebhard Exp $
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
     This file contains the functions for the xref-system
     
     Use in Pass 4:
	
	*******************************************************************
	Beschreibung:
	Diese Datei enthält die Funktionen für die Querverweistechniken.
	In der Regel werden alle Querverweisersetzungen aus der Config.XML geholt und eingesetzt.
     ********************************************************************
-->

	<xsl:key name="fnref_key" match="fnref[not(ancestor::kurs)]" use="@zielmarke"/>
	
	<xsl:template match="querverweis|litref">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
	<xsl:variable name="nPfad">
	<xsl:choose>
		<xsl:when test="string-length($Pfad) &lt; 1">
			<xsl:call-template name="CSSPfad"/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$Pfad"/>
		</xsl:otherwise>
	</xsl:choose>
	</xsl:variable>
		<xsl:variable name="referenz" select="key('id_key',@zielmarke)[1]"/>
		<xsl:variable name="link">
			<xsl:apply-templates select="key('id_key',@zielmarke)[1]" mode="getpage">
				<xsl:with-param name="Pfad"  select="$nPfad"/>
				<xsl:with-param name="contextnode"  select="$contextnode"/>
			</xsl:apply-templates>
			<xsl:text>#</xsl:text>
			<xsl:value-of select="@zielmarke"/>
		</xsl:variable>
		<a href="{$link}">
		<xsl:attribute name="class">
			<xsl:value-of select="name()"/>
		</xsl:attribute>
			<xsl:apply-templates select="$referenz" mode="xref"/>
		</a>
	</xsl:template>
	<xsl:template match="liteintrag" mode="xref">
		<xsl:apply-templates select="$styles/entry[@name='literaturliste'][1]/xrefformat">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates>
	</xsl:template>

	<xsl:template match="beispiel|exkurs|fall|kommentar|norm|rechtsprechung|zusammenfassung|axiom|corollar|definition|folgerung|hauptsatz|hilfssatz|lemma|proposition|regel|satz|theorem|annahme|bemerkung|beweis|loesung|problem|merksatz|vertiefung|zitat|proglist|formelarray|tabelle|tabelle-alt|marginalie" mode="xref">
		<xsl:apply-templates select="$styles/entry[@name=name(current())]/xrefformat">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="einsendeaufgabe|selbsttestaufgabe|uebungsaufgabe|unteraufgabe" mode="xref">
		<xsl:apply-templates select="($styles/entry[@name=name(current())]/xrefformat)[1]">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="abschnitt" mode="xref">
		<xsl:apply-templates select="$styles/entry[@name='abschnitt' and @level=current()/@level]/xrefformat">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="abschnitt[ancestor::anhang]" mode="xref">
		<xsl:apply-templates select="$styles/entry[@name='abschnitt' and @level=concat('a',current()/@level)]/xrefformat">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="grafik|bild" mode="xref" priority="1">
		<xsl:apply-templates select="$styles/entry[@name='medienobjekt' and @variant='figure']/xrefformat">
			<xsl:with-param name="contextnode" select="current()/parent::medienobjekt"/>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="medienobjekt[grafik|bild]" mode="xref" priority="1">
		<xsl:apply-templates select="$styles/entry[@name='medienobjekt' and @variant='figure']/xrefformat">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="animation|video|audio|simulation|textobjekt" mode="xref">
		<xsl:apply-templates select="$styles/entry[@name='medienobjekt' and @variant=name(current())]/xrefformat">
			<xsl:with-param name="contextnode" select="current()/parent::medienobjekt"/>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="textobjekt[preceding-sibling::animation or following-sibling::animation]" mode="xref" priority="1">
		<xsl:apply-templates select="$styles/entry[@name='medienobjekt' and @variant='animation_textobjekt']/xrefformat">
			<xsl:with-param name="contextnode" select="current()/parent::medienobjekt"/>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="medienobjekt[animation|video|audio|simulation|textobjekt]" mode="xref">
		<xsl:apply-templates select="$styles/entry[@name='medienobjekt' and @variant=name(current()/(animation|video|audio|simulation|textobjekt))]/xrefformat">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="medienobjekt[animation and textobjekt]" mode="xref" priority="1">
		<xsl:apply-templates select="$styles/entry[@name='medienobjekt' and @variant='animation-textobjekt']/xrefformat">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates>
	</xsl:template>

	<xsl:template match="formel[parent::formelarray]" mode="xref">
		<xsl:apply-templates select="$styles/entry[@name=name(current())][@variant='insidearray']/xrefformat">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="formel[not(parent::formelarray)]" mode="xref">
		<xsl:apply-templates select="$styles/entry[@name=name(current())][not(@variant='insidearray')]/xrefformat">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates>
	</xsl:template>

	<xsl:template match="fussnote" mode="xref"><xsl:apply-templates select="key('fnref_key',current()/@id)" mode="xref"/></xsl:template>	
	<xsl:template match="fnref" mode="xref"><xsl:apply-templates select="$styles/entry[@name='fussnote']/xrefformat[@format='html']">
			<xsl:with-param name="contextnode" select="current()"/>
	</xsl:apply-templates></xsl:template>
	
	<xsl:template match="textmarke" mode="xref">
		<xsl:apply-templates select="$styles/entry[@name='textmarke']/xrefformat[@format='html']">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="titel|zwischentitel|objekttitel" mode="xref">
		<xsl:apply-templates select="parent::*" mode="xref"/>
	</xsl:template>
	<!-- Sollte für ein Element keine Querverweisauflösung bestehen, dann zeige einen Invaliden Querverweis an! -->
	<xsl:template match="*" mode="xref">[INVALID XREF!]</xsl:template>
</xsl:stylesheet>
