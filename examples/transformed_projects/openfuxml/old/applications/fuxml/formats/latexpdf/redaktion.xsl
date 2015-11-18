<?xml version="1.0"?>
<!-- ********************************************************************
	$Id: redaktion.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" />
	
	
	
	<xsl:template match="aenderungsdatum"/>	
	<xsl:template match="sans"><xsl:param name="contextnode"/>{\textsf{<xsl:apply-templates  select="node()[name()!='margref']"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}}<xsl:apply-templates select="margref"/></xsl:template>
		
	<xsl:template match="fett"><xsl:param name="contextnode"/>{\bfseries{}<xsl:apply-templates  select="node()[name()!='margref']"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}<xsl:apply-templates select="margref"/></xsl:template>
	<!--xsl:template match="fett"><xsl:choose>
		<xsl:when test="$styles/entry[@name='fett']"><xsl:call-template name="inline">
			<xsl:with-param name="style" select="$styles/entry[@name='fett']"/></xsl:call-template></xsl:when>
			<xsl:otherwise>{\bfseries{}<xsl:apply-templates  select="node()[name()!='margref']"></xsl:apply-templates>}<xsl:apply-templates select="margref"/></xsl:otherwise>
	</xsl:choose></xsl:template-->
			
	<xsl:template match="hochgestellt"><xsl:param name="contextnode"/>\raisebox{1ex}{<xsl:apply-templates><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}</xsl:template>
	
	<xsl:template match="hochgestellt[ancestor::buchstaeblich]">\(\sp{<xsl:apply-templates/>}\)</xsl:template>
	
	<xsl:template match="kapitaelchen"><xsl:param name="contextnode"/>{\scshape{}<xsl:apply-templates><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}</xsl:template>
	
	<xsl:template match="kursiv"><xsl:param name="contextnode"/>{\itshape{}<xsl:apply-templates><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}</xsl:template>
	
	<xsl:template match="aufhebung"><xsl:param name="contextnode"/>{\normalfont{}<xsl:apply-templates><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}</xsl:template>
		
	<xsl:template match="motto"><xsl:if test="name(preceding-sibling::*[1])='abschnitt'"><xsl:call-template name="nobox">
		<xsl:with-param name="style" select="$styles/entry[@name='motto']"/>
	</xsl:call-template>\nopagebreak{}</xsl:if></xsl:template>
	
	
	
	<xsl:template match="schreibmaschine"><xsl:param name="contextnode"/>{\ttfamily{}<xsl:apply-templates><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}</xsl:template>
		
	<xsl:template match="tiefgestellt"><xsl:param name="contextnode"/>\raisebox{-1ex}{<xsl:apply-templates><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}</xsl:template>
	
	<xsl:template match="tiefgestellt[ancestor::buchstaeblich]">\(\sb{<xsl:apply-templates/>}\)</xsl:template>

	<xsl:template match="unterstrichen"><xsl:param name="contextnode"/>\underline{<xsl:apply-templates><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}</xsl:template>
	
	<xsl:template match="url"><xsl:call-template name="nobox"><xsl:with-param name="style" select="$styles/entry[@name='url']"/></xsl:call-template></xsl:template><!--\footnote{<xsl:value-of select="@adresse"/>}-->
	
	<xsl:template match="zeilenende">\\</xsl:template>
	
	<!--xsl:template match="zeilenende[parent::schreibmaschine or parent::kursiv or parent::fett or parent::unterstrichen][not(ancestor::entry)][not(ancestor::marginalie)]">\\</xsl:template-->
	
	<!--xsl:template match="zeilenende[ancestor::entry]">\newline{}</xsl:template-->
	<!-- In a marginpar "\\" does not work, except in a list in a marginpar -->
	<xsl:template match="zeilenende[ancestor::marginalie][not(ancestor::eintrag)]">\par </xsl:template>
		
	<xsl:template match="zitat"><xsl:param name="mode"/><xsl:call-template name="labelbox">
		<xsl:with-param name="style" select="$styles/entry[@name='zitat']"/>
		<xsl:with-param name="mode" select="$mode"/>
	</xsl:call-template>
</xsl:template>
	
	
</xsl:stylesheet>