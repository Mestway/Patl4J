<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
<!-- 
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
      -->    
    <!--=============================================================-->
    <!-- equations in equationarrays                                                                                          -->
    <!--=============================================================-->
    <!--numbered equation in numbered equationarray (1/1)-->
    <xsl:template match="formel[@num='an'][ancestor::formelarray[@num='an']]"><xsl:value-of select="normalize-space(.)"/>\tag*{<xsl:value-of select="normalize-space(@number)"/>}<xsl:if test="position()!=last()">\\<xsl:call-template name="crlf"/></xsl:if></xsl:template>
    <!--=============================================================-->
    <!-- unnumbered equation in numbered equationarray (1/0)-->
    <xsl:template match="formel[@num='aus'][ancestor::formelarray[@num='an']]"><xsl:value-of select="normalize-space(.)"/><xsl:if test="position()!=last()">\\<xsl:call-template name="crlf"/></xsl:if></xsl:template>
    <!--=============================================================-->
    <!-- numbered equation in unnumbered equationarray )(0/1)-->
    <xsl:template match="formel[@num='an'][ancestor::formelarray[@num='aus']]"><xsl:value-of select="normalize-space(.)"/>\tag*{<xsl:value-of select="normalize-space(@number)"/>}<xsl:if test="position()!=last()">\\<xsl:call-template name="crlf"/></xsl:if></xsl:template>
    <!--=============================================================-->
    <!-- unnumbered equation in unnumbered equationarray (0/0) -->
    <xsl:template match="formel[@num='aus'][ancestor::formelarray[@num='aus']]"><xsl:value-of select="normalize-space(.)"/><xsl:if test="position()!=last()">\\<xsl:call-template name="crlf"/></xsl:if></xsl:template>
    <!--=============================================================-->
    <!-- equation in multiline equationarray, single lines are not numbered  -->
    <xsl:template match="formel[ancestor::formelarray[@typ='multiline']]" priority="1"><xsl:value-of select="normalize-space(.)"/><xsl:if test="position()!=last()">\\<xsl:call-template name="crlf"/></xsl:if></xsl:template>

    <xsl:template name="crlf"><!--xsl:if test="not(starts-with(following::text()[1],'&#xA;')) or not(parent::absatz)"><xsl:text xml:space="preserve">&#xD;&#xA;</xsl:text></xsl:if--><xsl:text xml:space="preserve">&#xA;</xsl:text></xsl:template>
    
    <!--=============================================================-->
    <!-- numbering of equationarray -->
    <xsl:template name="num.array"><xsl:if test="@num='an' and not(formel/@num='an')">\tag*{<xsl:value-of select="normalize-space(@number)"/>}</xsl:if></xsl:template>
    <!--=============================================================-->
    <xsl:template name="get.columns"><xsl:variable name="columns" select="tokenize(formel[1],'&amp;')"/>
        <xsl:message><xsl:value-of select="ceiling((count($columns) div 2)+1)"/></xsl:message><xsl:value-of select="ceiling((count($columns) div 2)+1)"/></xsl:template>	
    <!--=============================================================-->

</xsl:stylesheet>
