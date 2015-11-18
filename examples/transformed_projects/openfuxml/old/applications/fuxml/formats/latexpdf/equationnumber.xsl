<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <!-- ********************************************************************
        $Id: equationnumber.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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

<xsl:template name="get.equation.number">
<xsl:call-template name="get.section.part"/><xsl:call-template name="get.equation.part"/><xsl:if test="parent::formelarray-num or parent::formelarray"><xsl:call-template name="get.arraypos.part"/></xsl:if>

<!--xsl:choose>
	<xsl:when test="parent::formelarray-num or parent::formelarray">
		<xsl:call-template name="get.section.part"/><xsl:call-template name="get.equation.part"/><xsl:call-template name="get.arraypos.part"/>
	</xsl:when>
	<xsl:otherwise>
			<xsl:value-of select="preceding::abschnitt[1]/@formel_num"/>-<xsl:call-template name="get.equation.part"/>
	</xsl:otherwise>
</xsl:choose-->
</xsl:template>

<!--xsl:template name="get.chapter.part"><xsl:if test="$formel_num_depth &gt; 0"><xsl:number level="any" count="abschnitt[@level=1]"/></xsl:if></xsl:template>

<xsl:template name="get.section.part"><xsl:if test="$formel_num_depth &gt; 1">.<xsl:number level="any" count="abschnitt[@level=2]"/></xsl:if></xsl:template>

<xsl:template name="get.subsection.part"><xsl:if test="$formel_num_depth &gt; 2">.<xsl:number level="any" count="abschnitt[@level=3]"/></xsl:if></xsl:template-->

<xsl:template name="get.section.part"><xsl:number level="multiple" count="abschnitt" from="/" format="{$formel_num_format_abschnitt}"/></xsl:template>

<xsl:template name="get.equation.part"><xsl:value-of select="$formel_abschnittstrennzeichen"/><xsl:number level="any" count="formelarray-num|formel-num[not(parent::formelarray-num) and not(parent::formelarray)]" from="abschnitt" format="{$formel_num_format_formel}"/></xsl:template>

<xsl:template name="get.arraypos.part"><xsl:value-of select="$formel_abschnittstrennzeichenarray"/><xsl:number level="any" count="formel-num" from="formelarray|formelarray-num" format="{$formel_num_format_formelarray}"/>
</xsl:template>

</xsl:stylesheet>