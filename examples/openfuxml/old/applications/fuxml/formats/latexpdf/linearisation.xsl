<?xml version="1.0" encoding="UTF-8"?>
<!-- ********************************************************************
    $Id: linearisation.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="no"/>
<xsl:include href="../html/variablen.xsl"/>
<xsl:include href="numbering.xsl"/>

<!--     Provide documents without dtd and proper encoding for Java programms                   -->

<xsl:template match="@*|node()"><xsl:copy><xsl:apply-templates select="@*|node()"/></xsl:copy></xsl:template>

<xsl:template match="abschnitt"><xsl:copy><xsl:attribute name="level"><xsl:value-of select="count(ancestor-or-self::abschnitt|ancestor-or-self::abschnitt-ohne-num)"/></xsl:attribute><xsl:attribute name="number"><xsl:call-template name="get.section.number"/></xsl:attribute><xsl:apply-templates select="@*|titel"/></xsl:copy><xsl:apply-templates select="node()[name() != 'titel']"/></xsl:template>

<xsl:template match="abschnitt-ohne-num"><xsl:copy><xsl:attribute name="level"><xsl:value-of select="count(ancestor-or-self::abschnitt-ohne-num)"/></xsl:attribute><xsl:apply-templates select="@*|titel"/></xsl:copy><xsl:apply-templates select="node()[name() != 'titel']"/></xsl:template>


<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<xsl:template match="formel-num"><xsl:copy><xsl:attribute name="number"><xsl:call-template name="get.equation.number"/></xsl:attribute><xsl:apply-templates select="@*|node()"/></xsl:copy>

</xsl:template>
</xsl:stylesheet>
