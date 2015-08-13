<?xml version="1.0" encoding="UTF-8"?>
<!-- ********************************************************************
	$Id: marginalie.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
	
	<xsl:key name="marginalie_key" match="marginalie" use="@id"/>
	
	<xsl:template match="margref"><xsl:param name="mode"></xsl:param><xsl:if test="not($mode='inbox')"><xsl:value-of select="$mode"/>\mbox{}\marginpar[{\parbox[t]{\marginparwidth}{\footnotesize\raggedleft\hspace{0pt}<xsl:apply-templates select="key('marginalie_key',@zielmarke)[1]" mode="call"/>}}]{\parbox[t]{\marginparwidth}{\footnotesize\raggedright\hspace{0pt}<xsl:apply-templates select="key('marginalie_key',@zielmarke)[1]" mode="call"/>}}</xsl:if><xsl:apply-templates select="key('marginalie_key',@zielmarke)[1]/descendant::indexfix" mode="call"/></xsl:template>
	
	<xsl:template match="margref" mode="call">\mbox{}\marginpar[\footnotesize\raggedleft\hspace{0pt}<xsl:apply-templates select="key('marginalie_key',@zielmarke)[1]" mode="call"/>]{\footnotesize\raggedright\hspace{0pt}<xsl:apply-templates select="key('marginalie_key',@zielmarke)[1]" mode="call"/>}<xsl:apply-templates select="key('marginalie_key',@zielmarke)[1]/descendant::indexfix" mode="call"/></xsl:template>

	<xsl:template match="margref" mode="breakbox">\marginpar[\footnotesize\raggedleft\vspace{3ex}<xsl:apply-templates select="key('marginalie_key',@zielmarke)[1]" mode="call"/>]{\footnotesize\raggedright\vspace{3ex}<xsl:apply-templates select="key('marginalie_key',@zielmarke)[1]" mode="call"/>}\nopagebreak<xsl:apply-templates select="key('marginalie_key',@zielmarke)[1]/descendant::indexfix" mode="call"/></xsl:template>
	
	<xsl:template match="margref" mode="tablemargref">\mbox{}\hspace{-1.5mm}\marginpar[\footnotesize\raggedleft\hspace{0pt}<xsl:apply-templates select="key('marginalie_key',@zielmarke)[1]" mode="call"/>]{\footnotesize\raggedright\hspace{0pt}<xsl:apply-templates select="key('marginalie_key',@zielmarke)[1]" mode="call"/>}\nopagebreak<xsl:apply-templates select="key('marginalie_key',@zielmarke)[1]/descendant::indexfix" mode="call"/></xsl:template>
	

	
	<!-- Marginpars in tables are problematic -->
	<xsl:template match="margref[ancestor::tabelle or ancestor::tabelle-alt]"/>
	
	<!-- Marginpars in section titles are problematic -->
	<xsl:template match="margref[ancestor::titel[parent::abschnitt]]"/>

	
	<!--xsl:template match="marginalie" mode="call"><xsl:apply-templates/></xsl:template-->
	
	<xsl:template match="marginalie" mode="call">
		<xsl:call-template name="environment.selector">
			<xsl:with-param name="style" select="$styles/entry[@name='marginalie']"/>
		</xsl:call-template>
	</xsl:template>

	
	<xsl:template match="marginalie"/>
		

</xsl:stylesheet>
