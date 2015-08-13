<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<!-- ********************************************************************
		$Id: aufgaben.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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

	<xsl:template match="einsendeaufgaben">
		<xsl:variable name="style" select="$styles/entry[@name=name(current())]"/>
		<xsl:call-template name="container"><xsl:with-param name="style" select="$style"/></xsl:call-template>
	</xsl:template>
	
	<xsl:template match="aufgabenstellung|loesungshinweis|korrekturhinweis|aufgabenloesung|unteraufgabe">
		<xsl:param name="mode"/>
		<xsl:variable name="entry">
			<xsl:choose>
				<xsl:when test="$styles/entry[@name = name(current())][@variant]">
					<xsl:copy-of select="$styles/entry[@name = name(current()) and @variant=$mode]"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:copy-of select="$styles/entry[@name = name(current())]"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<!--xsl:message>Entry: <xsl:value-of select="concat($entry/entry/@name)"/></xsl:message-->
		<xsl:call-template name="environment.selector">
			<xsl:with-param name="style" select="$entry/entry"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="einsendeaufgaben" mode="task">
		<xsl:variable name="style" select="$styles/entry[@name=name(current())][@variant='task']"/>
		<xsl:variable name="kenumber" select="ancestor::kurseinheit/@number"/>
		<xsl:apply-templates select="$style/text-before"><xsl:with-param name="contextnode" select="current()"/><xsl:with-param name="style" select="$style"/></xsl:apply-templates>
		<xsl:apply-templates><xsl:with-param name="mode">task</xsl:with-param></xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="einsendeaufgaben" mode="solution">
		<xsl:variable name="style" select="$styles/entry[@name=name(current())][@variant='solution']"/>
		<xsl:variable name="kenumber" select="ancestor::kurseinheit/@number"/>
		<xsl:apply-templates select="$style/text-before"><xsl:with-param name="contextnode" select="current()"/><xsl:with-param name="style" select="$style"/></xsl:apply-templates>
		<xsl:apply-templates><xsl:with-param name="mode">solution</xsl:with-param></xsl:apply-templates>
	</xsl:template>

	<xsl:template match="einsendeaufgaben" mode="correction">
		<xsl:variable name="style" select="$styles/entry[@name=name(current())][@variant='correction']"/>
		<xsl:variable name="kenumber" select="ancestor::kurseinheit/@number"/>
		<xsl:apply-templates select="$style/text-before"><xsl:with-param name="contextnode" select="current()"/><xsl:with-param name="style" select="$style"/></xsl:apply-templates>
		<xsl:apply-templates><xsl:with-param name="mode">correction</xsl:with-param></xsl:apply-templates>
	</xsl:template>

	
	<xsl:template match="einsendeaufgabe">
		<xsl:param name="mode"/>
		<xsl:call-template name="assignment">
			<xsl:with-param name="style" select="$styles/entry[@name=name(current())][@variant=$mode]"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:call-template>
	</xsl:template>
	
	
	<xsl:template match="selbsttestaufgabe|uebungsaufgabe">
		<xsl:variable name="mode">task</xsl:variable>
		<xsl:call-template name="assignment">
			<xsl:with-param name="style" select="$styles/entry[@name=name(current())][@variant=$mode]"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="selbsttestaufgabe|uebungsaufgabe" mode="loesung">
		<xsl:variable name="mode">solution</xsl:variable>
		<xsl:call-template name="assignment">
			<xsl:with-param name="style" select="$styles/entry[@name=name(current())][@variant=$mode]"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="zwischentitel[parent::einsendeaufgabe]|zwischentitel[parent::uebungsaufgabe]|zwischentitel[parent::selbsttestaufgabe]">
		<xsl:apply-templates/>
	</xsl:template> 
	
</xsl:stylesheet>
