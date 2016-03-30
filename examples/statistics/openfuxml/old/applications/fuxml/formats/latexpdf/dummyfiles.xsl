<?xml version="1.0" encoding="UTF-8"?>
<!-- ********************************************************************
	$Id: dummyfiles.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
	<xsl:output method="xml"/>
	<!--xsl:param name="workdir">file://I:/output/testprojekt_ks/latexpdf/zfetest</xsl:param-->

	<xsl:template match="/">
		<project name="Create Dummy Files" default="default">
			<target name="default">
				<antcall target="indextoc"/>
				<antcall target="lastpage"/>
			</target>
			<!-- +++++++++++++++++++++++  -->
			<target name="indextoc">
				<xsl:apply-templates select="//document" mode="indextoccondition"/>
			</target>
			<!-- +++++++++++++++++++++++  -->
			<xsl:apply-templates select="//document" mode="indextoc"/>
			<!-- +++++++++++++++++++++++  -->
			<target name="lastpage">
				<xsl:apply-templates select="//document" mode="lastpagecondition"/>
			</target>
			<!-- +++++++++++++++++++++++  -->
			<xsl:apply-templates select="//document" mode="lastpage"/>
		</project>
	</xsl:template>
	
	<xsl:template match="document" mode="indextoccondition">
		<!-- write dummy index toc and lastpage files since latex expects the file to exist -->
		<available>
			<xsl:attribute name="property">
				<xsl:value-of select="concat(normalize-space(filename),'_indextocexists')"/>
			</xsl:attribute>
			<xsl:attribute name="file"><!-- If this file exists the other two types should also exist -->
				<xsl:value-of select="concat(normalize-space(filename),'.toc')"/>
			</xsl:attribute>
		</available>
		<antcall>
			<xsl:attribute name="target">
				<xsl:value-of select="concat('create_',normalize-space(filename),'_indextocfiles')"/>
			</xsl:attribute>
		</antcall>
	</xsl:template>
	
	<xsl:template match="document[@precedingfile]" mode="lastpagecondition">
		<!-- write dummy lastpage files since latex expects the file to exist -->
		<available>
			<xsl:attribute name="property">
				<xsl:value-of select="concat(normalize-space(filename),'_lastpageexists')"/>
			</xsl:attribute>
			<xsl:attribute name="file"><!-- If this file exists the other two types should also exist -->
				<xsl:value-of select="concat(normalize-space(filename),'.lastpage.tex')"/>
			</xsl:attribute>
		</available>
		<antcall>
			<xsl:attribute name="target">
				<xsl:value-of select="concat('create_',normalize-space(filename),'_lastpagefiles')"/>
			</xsl:attribute>
		</antcall>
	</xsl:template>

	
	<xsl:template match="document" mode="indextoc">
		<target>
			<xsl:attribute name="name">
				<xsl:value-of select="concat('create_',normalize-space(filename),'_indextocfiles')"/>
			</xsl:attribute>
			<xsl:attribute name="unless">
				<xsl:value-of select="concat(normalize-space(filename),'_indextocexists')"/>
			</xsl:attribute>
			<echo>Create dummy index and toc files for <xsl:value-of select="normalize-space(filename)"/></echo>
			<echo>
				<xsl:attribute name="file"><xsl:value-of select="normalize-space(filename)"/>.idx</xsl:attribute>
			</echo>
			<echo>
				<xsl:attribute name="file"><xsl:value-of select="normalize-space(filename)"/>.toc</xsl:attribute>
			</echo>
		</target>
	</xsl:template>
	
	<xsl:template match="document[@precedingfile]" mode="lastpage">
		<target>
			<xsl:attribute name="name">
				<xsl:value-of select="concat('create_',normalize-space(filename),'_lastpagefiles')"/>
			</xsl:attribute>
			<xsl:attribute name="unless">
				<xsl:value-of select="concat(normalize-space(filename),'_lastpageexists')"/>
			</xsl:attribute>
			<echo>Create dummy lastpage file for <xsl:value-of select="normalize-space(filename)"/></echo>
			<echo>
				<xsl:attribute name="file"><xsl:value-of select="normalize-space(@precedingfile)"/>.lastpage.tex</xsl:attribute>
			</echo>
		</target>
	</xsl:template>
	
	<xsl:template match="*" mode="lastpage"/>
	<xsl:template match="*" mode="lastpagecondition"/>

</xsl:stylesheet>
