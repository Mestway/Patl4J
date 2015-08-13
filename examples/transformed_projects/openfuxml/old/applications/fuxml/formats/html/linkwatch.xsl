<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<!-- ********************************************************************
     $Id: linkwatch.xsl,v 1.1 2006/09/05 09:39:09 gebhard Exp $
     ********************************************************************

     This file is part of the FuXML_HTML Stylesheet distribution.
     
     Description:
     This file creates linkwatch.xml, which used by datamanagement component (DMC) to manage external urls.

     Use in Pass 5 (optional; only if activated by DMC)
	
	*******************************************************************
	Beschreibung:
	Diese Datei erzeugt die Austauschdatei linkwatch.xml, die alle externen URLs enthält. Die Datenmanagement Komponente
	speichert diese URLs in der Datenbank und liefert stattdessen Redirect-Adressen aus, die bei der nächsten Produktion anstatt
	der Originallinks eingesetzt werden.
     ********************************************************************
-->

<xsl:include href="common.xsl"/>
<xsl:include href="../common/settings.xsl"/>
<xsl:include href="../common/mediainformation.xsl"/>
<xsl:include href="designinterface-html.xsl"/>
<xsl:include href="templates.xsl"/>

	<xsl:variable name="styles" select="$config/config/styles"/>
	<xsl:variable name="folder" select="$config/config/specialfolder/html"/>

<xsl:output method="xml" indent="yes"/>

	<xsl:key name="id_key" match="*" use="@id"/>
	<xsl:key name="source_key" match="url" use="@sourcefile"/>

 
	<xsl:template match="document">
	<xsl:variable name="docnames" select="//url/@sourcefile[not(.=following::url/@sourcefile)]"/>
	<document>
	<xsl:for-each select="$docnames">
	<datei>
		<xsl:attribute name="name">
<!--			<xsl:call-template name="get.mediadir">
				<xsl:with-param name="mediaref" select="."/>
			</xsl:call-template>
			<xsl:text>/</xsl:text> -->
			<xsl:call-template name="get.filename">
				<xsl:with-param name="mediaref" select="."/>
			</xsl:call-template>
		</xsl:attribute>
		<xsl:apply-templates select="key('source_key',.)" mode="linkwatch"/>
	</datei>
	</xsl:for-each>
	</document>
	</xsl:template>

	<xsl:template name="linkwatch">
	<xsl:message>Linkwatch aktiv</xsl:message>
	<xsl:result-document href="linkwatch.xml">
	<document>
		<xsl:apply-templates select="//url" mode="linkwatch"/>
	</document>
	</xsl:result-document>
	</xsl:template>

	<xsl:template match="url" mode="linkwatch">
	<url>
		<text>
			<xsl:value-of select="."/>
		</text>
		<xsl:choose>
			<xsl:when test="@originallink">
				<original>
					<xsl:value-of select="@originallink"/>
				</original>
				<redirect>
					<xsl:value-of select="@adresse"/>
				</redirect>
			</xsl:when>
			<xsl:otherwise>
				<original>
					<xsl:value-of select="@adresse"/>
				</original>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:if test="@position">
			<position>
				<xsl:value-of select="@position"/>
			</position>
		</xsl:if>
		<location>
			<xsl:apply-templates select="key('id_key',@id)[1]" mode="getpage">
				<xsl:with-param name="contextnode" select="current()"/>
			</xsl:apply-templates>#<xsl:value-of select="@id"/>
		</location>
	</url>
	</xsl:template>

</xsl:stylesheet>