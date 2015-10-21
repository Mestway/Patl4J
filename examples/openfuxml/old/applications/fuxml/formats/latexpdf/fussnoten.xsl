<?xml version="1.0" encoding="UTF-8"?>
<!-- ********************************************************************
    $Id: fussnoten.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
<xsl:key name="fussnote_key" match="fussnote" use="@id"/>

<xsl:template match="fnref">
	<xsl:param name="mode"/>
	<xsl:choose>
		<xsl:when test="$mode='inbox' or ancestor::tabelle or ancestor::tabelle-alt">\footnotemark[<xsl:value-of select="@number"/>]</xsl:when>
		<xsl:otherwise>\protect\footnote[<xsl:value-of select="@number"/>]{<xsl:apply-templates select="key('fussnote_key',@zielmarke)[1]" mode="call"/>}\label{<xsl:value-of select="@id"/>}</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template match="fnref" mode="afterbox">\footnotetext[<xsl:value-of select="@number"/>]{<xsl:apply-templates select="key('fussnote_key',@zielmarke)[1]" mode="call"/>}</xsl:template>

	
<xsl:template match="fnabschnitt"></xsl:template>

<xsl:template match="fussnote" mode="call">
		<xsl:apply-templates/>
</xsl:template>
	
<xsl:template match="fussnote"/>


</xsl:stylesheet>