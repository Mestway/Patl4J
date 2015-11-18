<?xml version="1.0" encoding="UTF-8"?>
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
		
 	- Linearisation of section structure. The recursive structure of the sections is flattened. 
	- Course units are numbered. Other elements are numbered in a later stage.
	- Processing-instructions for course units are set which are processed in a later stage
	- Automatic Creation of processing-instructions for screen pages
	
-->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:saxon="http://saxon.sf.net/" xmlns:xs="http://www.w3.org/2001/XMLSchema">
<xsl:include href="designinterface.xsl"/>
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="no"/>
<!--xsl:strip-space elements="*"/--><!-- <fett>...</fett> <kursiv>...</kursiv> ... -->
<!--xsl:include href="projectsettings.xsl"/-->
<xsl:key name="be_key" match="ke-lehrtext/@bereich-ende" use="."/>
<xsl:key name="ba_key" match="ke-lehrtext/@bereich-start" use="."/>
<xsl:include href="preservespace.xsl"/>

<!-- only the root of the assembled document shall be embraced by a document structure. If this statement was included in assemble.xsl
       every subdocument would be embraced by a document element-->
<xsl:template match="/">
	<document>
		<xsl:processing-instruction name="file">
			<xsl:value-of select="$documentname"/>
		</xsl:processing-instruction>
		<xsl:apply-templates select="node()"/>
	</document>
</xsl:template>

<xsl:template match="@*|node()">
	<xsl:call-template name="check.custart"/>
	<xsl:if test="@npr-teil='ja' and not(ancestor::*[@npr-teil='ja'])"><xsl:processing-instruction name="npr-teil-start"/></xsl:if>
	<xsl:copy><xsl:apply-templates select="node()|@*"/></xsl:copy>
	<xsl:if test="@npr-teil='ja' and not(ancestor::*[@npr-teil='ja'])"><xsl:processing-instruction name="npr-teil-end"/></xsl:if>
	<xsl:call-template name="check.cuend"/>
</xsl:template>

<xsl:template match="titel[parent::abschnitt and ancestor::textobjekt]">
	<xsl:copy>
		<xsl:attribute name="num">aus</xsl:attribute>
		<xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="abschnitt">
	<xsl:variable name="level"><xsl:value-of select="count(ancestor-or-self::abschnitt)"/></xsl:variable>
	<xsl:call-template name="check.custart"/>
	<xsl:processing-instruction name="SECTIONSTART"><xsl:value-of select="@id"/></xsl:processing-instruction>
	<!-- Start new screen when section level < cutlevel with the following exceptions:
	     	1. the section defines the start of a new course unit 
		2. the section is the first child of another section
	-->
	<xsl:variable name="screencutlevel">
		<xsl:choose>
			<xsl:when test="$config/config/screenconfig">
				<xsl:value-of select="$config/config/screenconfig/page-cutlevel"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$config/config/screenpage-cutlevel"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:variable name="elementcount">
		<xsl:value-of select="count(preceding-sibling::abschnitt[1]/*[not(self::titel|self::abschnitt)])"/>
	</xsl:variable>
	<xsl:variable name="style" select="$config/config/styles/entry[@name='abschnitt'][@level=$level]"/>
	<xsl:if test="not(key('ba_key', @id))">
		<xsl:choose>
			<xsl:when test="$level='1' and xs:integer($screencutlevel) gt 0">
				<xsl:processing-instruction name="NEUE-BILDSCHIRMSEITE"><xsl:value-of select="$level"/></xsl:processing-instruction>
				<xsl:message>PI-NEUE-BILDSCHIRMSEITE set; first level capter (<xsl:value-of select="titel/@id"/>)</xsl:message>
			</xsl:when>
			<xsl:when test="xs:integer($level) le xs:integer($screencutlevel) and name(preceding-sibling::*[1])!='titel'">
				<xsl:if test="$config/config/screenconfig/page-cutlevel/@min_element_quantity
					and xs:integer($elementcount) ge xs:integer($config/config/screenconfig/page-cutlevel/@min_element_quantity)">
					<xsl:message>PI-NEUE-BILDSCHIRMSEITE set; Elementcount (<xsl:value-of select="$elementcount"/>) greater then @min_element_quantity(<xsl:value-of select="$config/config/screenconfig/page-cutlevel/@min_element_quantity"/>) on abschnitt (<xsl:value-of select="titel/@id"/>)</xsl:message>
					<xsl:processing-instruction name="NEUE-BILDSCHIRMSEITE">MEQ<xsl:value-of select="$elementcount"/>ge<xsl:value-of select="$config/config/screenconfig/page-cutlevel/@min_element_quantity"/></xsl:processing-instruction>
				</xsl:if>
				<xsl:if test="$config/config/screenconfig/page-cutlevel/@min_element_quantity
					and xs:integer($elementcount) lt xs:integer($config/config/screenconfig/page-cutlevel/@min_element_quantity)">
					<xsl:message>parentElementCount (<xsl:value-of select="$elementcount"/>) is smaller then @min_element_quantity(<xsl:value-of select="$config/config/screenconfig/page-cutlevel/@min_element_quantity"/>) on abschnitt (<xsl:value-of select="titel/@id"/>)</xsl:message>
				</xsl:if>
				<xsl:if test="not($config/config/screenconfig/page-cutlevel/@min_element_quantity)">
					<xsl:processing-instruction name="NEUE-BILDSCHIRMSEITE"><xsl:value-of select="$level"/></xsl:processing-instruction>
				</xsl:if>
			</xsl:when>
		</xsl:choose>
	</xsl:if>
	<xsl:if test="$style/@newpage='true'
		and not(ancestor::einsendeaufgaben)
		and not(preceding-sibling::*[1]/self::titel)
		and not(preceding-sibling::*[1]/self::motto) 
		">
		<xsl:processing-instruction name="newpage"/>
	</xsl:if>
	<xsl:if test="@npr-teil='ja'and not(ancestor::*[@npr-teil='ja'])"><xsl:processing-instruction name="npr-teil-start"/></xsl:if>
	<xsl:copy>
		<xsl:attribute name="level"><xsl:value-of select="$level"/></xsl:attribute>
		<xsl:apply-templates select="@*|titel"/>
	</xsl:copy>
	<xsl:apply-templates select="node()[name() != 'titel']"/>
	<xsl:if test="@npr-teil='ja' and not(ancestor::*[@npr-teil='ja'])"><xsl:processing-instruction name="npr-teil-end"/></xsl:if>
	<xsl:processing-instruction name="SECTIONEND"><xsl:value-of select="@id"/></xsl:processing-instruction>
	<xsl:call-template name="check.cuend"/>
</xsl:template>



<xsl:template match="kurseinheit">
	<xsl:copy><xsl:attribute name="number"><xsl:number count="kurseinheit" from="/" level="any"/></xsl:attribute><xsl:apply-templates  select="@*|node()"/></xsl:copy></xsl:template>

<xsl:template match="kurseinheit" mode="position"><xsl:number count="kurseinheit" from="/" level="any"/></xsl:template>

<xsl:template match="ke-lehrtext" mode="position"><xsl:number count="ke-lehrtext" from="kurseinheit" level="any"/></xsl:template>


<!-- +++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<xsl:template name="check.custart">
	<xsl:if test="key('ba_key', @id)">
		<xsl:if test="count(key('ba_key', @id)) gt 1">
			<xsl:message>!!!!!!!!!!! OVERLAPPING COURSEUNITS !!!!!!!!!!!!</xsl:message>
		</xsl:if>
		<xsl:processing-instruction name="CUSTART">
				<xsl:apply-templates select="key('ba_key', @id)/ancestor::kurseinheit" mode="position"/><xsl:text>_</xsl:text>
				<xsl:apply-templates select="key('ba_key', @id)/parent::ke-lehrtext" mode="position"/>
		</xsl:processing-instruction>
		<xsl:processing-instruction name="NEUE-BILDSCHIRMSEITE"/>
	</xsl:if>
</xsl:template>

<xsl:template name="check.cuend">
<xsl:if test="key('be_key', @id)">
	<xsl:processing-instruction name="CUEND">
		<xsl:apply-templates select="key('be_key', @id)/ancestor::kurseinheit" mode="position"/><xsl:text>_</xsl:text>
		<xsl:apply-templates select="key('be_key', @id)/parent::ke-lehrtext" mode="position"/>
	</xsl:processing-instruction>
</xsl:if>
</xsl:template>

<!-- +++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<!-- This is already done in assembly.xsl -> @sourcefile -->
<!--xsl:template match="grafik|bild|animation|simulation|video|textobjekt">
	<xsl:call-template name="check.custart"/>
	<xsl:copy>
		<xsl:attribute name="infile">
			<xsl:choose>
				<xsl:when test="starts-with(@fileref,'..')">
					<xsl:value-of select="preceding::processing-instruction('file')[not(ancestor::textobjekt)][1]"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$documentname"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		<xsl:apply-templates select="node()|@*"/>
	</xsl:copy>
	<xsl:call-template name="check.cuend"/>
</xsl:template-->

<!-- +++++++++++++++++++++++++++++++++++++++++++++++++++++ -->


</xsl:stylesheet>
