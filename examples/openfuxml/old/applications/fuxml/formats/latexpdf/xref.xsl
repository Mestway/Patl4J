<?xml version="1.0" encoding="UTF-8"?>
<!-- ********************************************************************
	$Id: xref.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:key name="fnref_key" match="fnref" use="@zielmarke"/>

	<xsl:template match="querverweis|litref"><xsl:choose>
			<xsl:when test="$config/config/bibtexml/@active='true' and self::litref">\cite{<xsl:value-of select="@zielmarke"/>}</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="referenz" select="key('id_key',@zielmarke)[1]"/><xsl:choose>
					<xsl:when test="not(key('id_key',@zielmarke))">{\bf???}</xsl:when>
					<xsl:otherwise><xsl:apply-templates select="$referenz" mode="xref"/></xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise></xsl:choose>
		</xsl:template>
	
	<xsl:template match="glossarref"><xsl:choose>
			<xsl:when test="$styles/entry[@name='universalliste'][@variant='glossary']/xrefformat"><xsl:apply-templates select="$styles/entry[@name='universalliste'][@variant='glossary']/xrefformat">
						<xsl:with-param name="contextnode" select="current()"/>
				</xsl:apply-templates></xsl:when>
			<xsl:otherwise><xsl:apply-templates/></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="liteintrag" mode="xref">
		<xsl:apply-templates select="$styles/entry[@name='literaturliste'][1]/xrefformat">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="litkennung" mode="xref">
		<xsl:apply-templates select="$styles/entry[@name='literaturliste'][1]/xrefformat">
			<xsl:with-param name="contextnode" select="current()/parent::liteintrag"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="beispiel|exkurs|fall|kommentar|norm|rechtsprechung|zusammenfassung|axiom|corollar|definition|folgerung|hauptsatz|hilfssatz|lemma|proposition|regel|satz|theorem|annahme|bemerkung|beweis|loesung|problem|merksatz|vertiefung|zitat|proglist|tabelle|tabelle-alt|marginalie" mode="xref">
		<xsl:apply-templates select="$styles/entry[@name=name(current())]/xrefformat">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="formel[parent::formelarray]" mode="xref">
		<xsl:apply-templates select="$styles/entry[@name=name(current())][@variant='insidearray']/xrefformat">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="formel[not(parent::formelarray)]|formelarray" mode="xref">
		<xsl:apply-templates select="$styles/entry[@name='formel'][not(@variant)]/xrefformat">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates>
	</xsl:template>



	
	<xsl:template match="einsendeaufgabe|selbsttestaufgabe|uebungsaufgabe|unteraufgabe" mode="xref">
		<xsl:apply-templates select="($styles/entry[@name=name(current())]/xrefformat)[1]">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates>
	</xsl:template>

	
	<xsl:template match="abschnitt" mode="xref"><xsl:apply-templates select="$styles/entry[@name='abschnitt' and @level=current()/@level]/xrefformat">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates></xsl:template>
	
	<xsl:template match="abschnitt[ancestor::anhang]" mode="xref"><xsl:apply-templates select="$styles/entry[@name='abschnitt' and @level=concat('a',current()/@level)]/xrefformat">
		<xsl:with-param name="contextnode" select="current()"/>
	</xsl:apply-templates></xsl:template>

		
	<xsl:template match="grafik|bild" mode="xref" priority="1"><xsl:apply-templates select="$styles/entry[@name='medienobjekt' and @variant='figure']/xrefformat">
			<xsl:with-param name="contextnode" select="current()/parent::medienobjekt"/>
		</xsl:apply-templates></xsl:template>
	
			
	<xsl:template match="medienobjekt[grafik|bild]" mode="xref" priority="1"><xsl:apply-templates select="$styles/entry[@name='medienobjekt' and @variant='figure']/xrefformat">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates></xsl:template>

	
	<xsl:template match="animation|video|audio|simulation|textobjekt" mode="xref"><xsl:apply-templates select="$styles/entry[@name='medienobjekt' and @variant=name(current())]/xrefformat">
			<xsl:with-param name="contextnode" select="current()/parent::medienobjekt"/>
		</xsl:apply-templates></xsl:template>
		
	<xsl:template match="textobjekt[preceding-sibling::animation or following-sibling::animation]" mode="xref" priority="1"><xsl:apply-templates select="$styles/entry[@name='medienobjekt' and @variant='animation_textobjekt']/xrefformat">
			<xsl:with-param name="contextnode" select="current()/parent::medienobjekt"/>
		</xsl:apply-templates></xsl:template>

		
	<xsl:template match="medienobjekt[animation|video|audio|simulation|textobjekt]" mode="xref"><xsl:apply-templates select="$styles/entry[@name='medienobjekt' and @variant=name(current()/(animation|video|audio|simulation|textobjekt))]/xrefformat">
			<xsl:with-param name="contextnode" select="current()"/>
	</xsl:apply-templates></xsl:template>
	
	<xsl:template match="medienobjekt[animation and textobjekt]" mode="xref" priority="1"><xsl:apply-templates select="$styles/entry[@name='medienobjekt' and @variant='animation-textobjekt']/xrefformat">
			<xsl:with-param name="contextnode" select="current()"/>
	</xsl:apply-templates></xsl:template>

	
	<xsl:template match="fussnote" mode="xref"><xsl:apply-templates select="key('fnref_key',current()/@id)" mode="xref"/></xsl:template>
	
	<xsl:template match="fnref" mode="xref"><xsl:apply-templates select="$styles/entry[@name='fussnote']/xrefformat[@format='latexpdf']">
			<xsl:with-param name="contextnode" select="current()"/>
	</xsl:apply-templates></xsl:template>
	
	<xsl:template match="textmarke" mode="xref"><xsl:apply-templates select="$styles/entry[@name='textmarke']/xrefformat[@format='latexpdf']">
			<xsl:with-param name="contextnode" select="current()"/>
	</xsl:apply-templates></xsl:template>
	
	
	<xsl:template match="titel|zwischentitel|objekttitel" mode="xref"><xsl:apply-templates select="parent::*" mode="xref"/></xsl:template>
	
		
	<xsl:template match="*" mode="xref">[INVALID XREF!]</xsl:template>
</xsl:stylesheet>
