<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"  
			         xmlns:xs="http://www.w3.org/2001/XMLSchema">
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
	<!-- Example: "bilder/web/vlogo1.jpg" 
		filename: 	"vlogo1.jpg"
		medianame:	"vlogo1"
		mediapath:	"bilder/web"
		mediadir:	"web"
		mediasuffix:	"jpg"

		relativepath (baseuri="file://bilder/"): "web"
	-->
	<xsl:template name="get.filename">
		<xsl:param name="mediaref"/>
		<xsl:value-of select="tokenize($mediaref[1],'/')[last()]"/>
	</xsl:template>
	
	<xsl:template name="get.medianame">
		<xsl:param name="mediaref"/>
		<xsl:variable name="filename" select="tokenize($mediaref[1],'/')[last()]"/>
		<xsl:value-of select="substring-before($filename,'.')"/>
	</xsl:template>
	
	<xsl:template name="get.mediapath">
		<xsl:param name="mediaref"/>
		<xsl:variable name="filename" select="tokenize($mediaref[1],'/')[last()]"/>
		<xsl:value-of select="substring-before($mediaref[1],concat('/',$filename))"/>
	</xsl:template>

	<xsl:template name="get.mediadir">
		<xsl:param name="mediaref"/>
		<xsl:variable name="filename" select="tokenize($mediaref[1],'/')[last()]"/>
		<xsl:variable name="mediapath" select="substring-before($mediaref[1],concat('/',$filename))"/>
		<xsl:value-of select="tokenize($mediapath,'/')[last()]"/>			
	</xsl:template>
	
	<xsl:template name="get.mediasuffix">
		<xsl:param name="mediaref"/>
		<xsl:variable name="filename" select="tokenize($mediaref[1],'/')[last()]"/>
		<xsl:value-of select="substring-after($filename,'.')"/>
	</xsl:template>
	
	<xsl:template name="get.outputdir">
		<xsl:param name="contextnode"/>
		<xsl:choose>
			<xsl:when test="$contextnode/self::bild|$contextnode/self::grafik">bilder</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="name($contextnode[1])"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="get.medialink">
		<xsl:param name="contextnode"/>
		<xsl:variable name="filename">
			<xsl:call-template name="get.filename">
				<xsl:with-param name="mediaref" select="$contextnode/@fileref"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="mediadir">
			<xsl:call-template name="get.mediadir">
				<xsl:with-param name="mediaref" select="$contextnode/@fileref"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="mediasuffix">
			<xsl:call-template name="get.mediasuffix">
				<xsl:with-param name="mediaref" select="$contextnode/@fileref"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="outputdir">
			<xsl:call-template name="get.outputdir">
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="$mediasuffix='html'">
				<xsl:value-of select="concat($outputdir,'/',$mediadir,'/',$filename)"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="concat($outputdir,'/',$filename)"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="get.relativepath">
		<xsl:param name="baseuri"/>
		<xsl:param name="uri"/>
		<xsl:call-template name="get.mediapath">
			<xsl:with-param name="mediaref" select="substring-after($uri,$baseuri)"/>
		</xsl:call-template>
	</xsl:template>

</xsl:stylesheet>
