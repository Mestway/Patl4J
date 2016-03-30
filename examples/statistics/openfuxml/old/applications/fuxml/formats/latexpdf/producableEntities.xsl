<?xml version="1.0" encoding="UTF-8"?>
<!-- ********************************************************************
	$Id: producableEntities.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
	-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
	<xsl:include href="producibleEntities.xsl"/>
	<!--xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:param name="logfile"/>
	<xsl:param name="basedir"/>
	<xsl:param name="document"/>
	
	<xsl:template match="documents">
		<producableentities logfile="{$basedir}/{$logfile}">
			<xsl:apply-templates/>
		</producableentities>
	</xsl:template>
	
	<xsl:template match="document">
		<file>
			<xsl:attribute name="filename">
				<xsl:value-of select="normalize-space(filename)"/><xsl:choose>
					<xsl:when test="@archive='true'">.zip</xsl:when>
					<xsl:otherwise>.pdf</xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
			
			<xsl:attribute name="directory"><xsl:value-of select="$basedir"/>/<xsl:if test="directory and not(@archive='true')"><xsl:value-of select="normalize-space(directory)"/>/</xsl:if></xsl:attribute>
			<xsl:apply-templates select="description"/>
		</file>
		<xsl:apply-templates select="content"/>
	</xsl:template>
	
	<xsl:template match="description">
		<description><xsl:value-of select="normalize-space(.)"/></description>
	</xsl:template>
	
	<xsl:template match="content"><xsl:apply-templates/></xsl:template>
	
	<xsl:template match="text()"/-->
</xsl:stylesheet>
