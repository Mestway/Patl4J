<?xml version="1.0" encoding="UTF-8"?>
<!-- ********************************************************************
	$Id: index.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
<xsl:strip-space elements="indexfix"/>


<xsl:template match="processing-instruction('index')">
	<xsl:param name="contextnode"/>
	<!-- Einstellungen zum Index befinden sich in "header.xsl" 
		Bei Einstellungen ausserhalb der Präambel kommt es zu Problemen, z.B. mit dem Paket "calc"
	 -->
	\printindex
</xsl:template>

<xsl:template match="indexfix">\index{<xsl:apply-templates/><xsl:if test="@relevanz='hoch'">|textbf</xsl:if>}</xsl:template>

<xsl:template match="indexfix" mode="call">\index{<xsl:apply-templates/><xsl:if test="@relevanz='hoch'">|textbf</xsl:if>}</xsl:template>


<xsl:template match="indexfix[ancestor::marginalie]"/>

<xsl:template match="primaer"><xsl:apply-templates/></xsl:template>

<xsl:template match="sekundaer">!<xsl:apply-templates/></xsl:template>

<xsl:template match="tertiaer">!<xsl:apply-templates/></xsl:template>

<xsl:template match="primaer[@sortals]"><xsl:apply-templates select="@sortals"/>@<xsl:apply-templates/></xsl:template>

<xsl:template match="sekundaer[@sortals]">!<xsl:apply-templates select="@sortals"/>@<xsl:apply-templates/>"/></xsl:template>

<xsl:template match="tertiaer[@sortals]">!<xsl:apply-templates select="@sortals"/>@<xsl:apply-templates/>"/></xsl:template>


<xsl:template match="siehe">|see{<xsl:apply-templates/>}</xsl:template>

<xsl:template match="sieheauch">{ (siehe auch { \it <xsl:apply-templates/>}}</xsl:template>

<xsl:template match="text()[ancestor::indexfix]">
		<!-- XSLT 2.0 ! replace -->
		<!-- normally escape character is " -> has been replaced by > in fuxml.ist-->
		<!--xsl:variable name="text" select="."/>
		<xsl:variable name="text2" select="replace($text,'!','&gt;!')"/>
		<xsl:variable name="text3" select="replace($text2,'@','&gt;@')"/>
		<xsl:variable name="text4" select="replace($text3,'|','&gt;|')"/>
		<xsl:variable name="text5" select="replace($text4,'\{\\glqq\}','')"/>
		<xsl:variable name="text6" select="replace($text5,'\{\\textquotedblleft\}','')"/>
		<xsl:value-of select="$text6"/-->
	
	<xsl:choose>
		<xsl:when test="contains(.,'!')"><xsl:value-of select="replace(.,'!','&gt;!')"/></xsl:when>
		<xsl:when test="contains(.,'@')"><xsl:value-of select="replace(.,'@','&gt;@')"/></xsl:when>
		<xsl:when test="contains(.,'|')">
			<xsl:if test="not(contains(.,'&#x0022;|'))"><!-- "| : no ligature -->
				<xsl:message><xsl:value-of select="."/></xsl:message>
				<xsl:value-of select="replace(.,'\\\|','&gt;\\\|')"/>
			</xsl:if>
		</xsl:when>
		<!--xsl:when test="contains(.,'\glqq')"><xsl:value-of select="replace(.,'\\glqq','')"/></xsl:when>
		<xsl:when test="contains(.,'\textquotedblleft')"><xsl:value-of select="replace(.,'\\textquotedblleft','')"/></xsl:when-->
		<xsl:otherwise><xsl:value-of select="."/></xsl:otherwise>
	</xsl:choose>
</xsl:template>
</xsl:stylesheet>
