<?xml version="1.0" encoding="UTF-8"?>
<!-- ********************************************************************
	$Id: resulthtml.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
	<!---xsl:include href="../common/designinterface.xsl"/-->
	<xsl:param name="logfile">internet_kurs.xml_log.xml</xsl:param>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"/>
	
	<xsl:template match="documents">
		<html xmlns="http://www.w3.org/1999/xhtml">
		<head><title>Ergebnis der Produktion</title></head>
		<body>
			<p><a href="{$logfile}">Logfile</a></p>
			<table>
				<thead>
				 	<tr><th>Dokument</th><th>Beschreibung</th></tr>
				</thead>
				<tbody>
					<xsl:apply-templates select="document"/>
				</tbody>
			</table>
			</body>
		</html>
	</xsl:template>
	
	<xsl:template match="document">
		<tr><td><a>
		<xsl:attribute name="href"><xsl:value-of select="normalize-space(filename)"/>.pdf</xsl:attribute>
		<xsl:value-of select="normalize-space(filename)"/>.pdf</a></td><td><xsl:value-of select="description"/></td></tr>
		<xsl:apply-templates select="content"/>
	</xsl:template>
	
	<xsl:template match="content"><xsl:apply-templates select="document"/></xsl:template>
		
</xsl:stylesheet>



