<?xml version="1.0"?>
<!-- ********************************************************************
	$Id: result.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
<xsl:output method="html"/>
<xsl:include href="../common/projectsettings.xsl"/>

<xsl:param name="doc_name">internet_kurs</xsl:param>
<xsl:param name="logfile">internet_kurs.xml_log.xml</xsl:param>
	
	<xsl:template match="/">
		<html>
			<head><title>Ergebnis der Produktion</title></head>
			<body>
			<p><a href="{$logfile}">Logfile</a></p>
			<table>
				<thead>
				 	<th>Dokument</th><th>Beschreibung</th>
				</thead>
				<tbody>
					<tr><td><a href="{$doc_name}.pdf"><xsl:value-of select="$doc_name"/>.pdf</a></td><td>Hauptdokument</td></tr>
					<xsl:apply-templates mode="result"/>
				</tbody>
			</table>
			</body>
		</html>
	</xsl:template>
	
	<xsl:template match="kurseinheit" mode="result">
		<xsl:variable name="kenumber"><xsl:number count="kurseinheit" from="kurseinheiten" level="any"/></xsl:variable>
		<tr><td><a href="{$kurseinheit_filename}{$kenumber}.pdf"><xsl:value-of select="concat($kurseinheit_filename,$kenumber)"/>.pdf</a></td><td>Kurseinheit <xsl:value-of select="$kenumber"/></td></tr>
		<xsl:apply-templates mode="result"/>
	</xsl:template>

	<xsl:template match="einsendeaufgaben[ancestor::kurseinheit]" mode="result">
		<xsl:variable name="kenumber"><xsl:number count="kurseinheit" from="kurseinheiten" level="any"/></xsl:variable>
		<xsl:if test="*"><tr><td><a href="{$einsendeaufgabe_filename}{$kenumber}.pdf"><xsl:value-of select="concat($einsendeaufgabe_filename,$kenumber)"/>.pdf</a></td><td>Einsendeaufgaben zu KE <xsl:value-of select="$kenumber"/></td></tr></xsl:if>
		<xsl:if test="einsendeaufgabe/aufgabenloesung"><tr><td><a href="{$musterloesung_filename}{$kenumber}.pdf"><xsl:value-of select="concat($musterloesung_filename,$kenumber)"/>.pdf</a></td><td>Musterlösungen zu KE <xsl:value-of select="$kenumber"/></td></tr></xsl:if>
	</xsl:template>
	
	<xsl:template match="node()|@*" mode="result"><xsl:apply-templates mode="result"/></xsl:template>
	
	
	
</xsl:stylesheet>

