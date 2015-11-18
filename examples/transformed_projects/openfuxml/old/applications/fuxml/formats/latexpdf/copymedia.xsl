<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fn="http://www.w3.org/1999/XSL/Transform">
	<!-- ********************************************************************
		$Id: copymedia.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
	<xsl:param name="srcdir">i:/repository/fuxml/k20022</xsl:param>
	<xsl:param name="workdir">i:/output/k20022/latexpdf/zfetest</xsl:param>
	<xsl:param name="imagepath">i:/output/k20022/latexpdf/eps</xsl:param>
	<xsl:variable name="config" select="document('../common/config.xml')"/>
	<xsl:variable name="printgraphicdir">
		<xsl:choose>
			<xsl:when test="$config/config/printgraphicdir!=''"><xsl:value-of select="$config/config/printgraphicdir"/></xsl:when>
			<xsl:otherwise>druck</xsl:otherwise>
		</xsl:choose>
    </xsl:variable>
	<xsl:include href="../common/mediainformation.xsl"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	
	<xsl:template match="/">
		<project name="copymedia" default="copymedia">
			<target name="copymedia">
				<xsl:apply-templates select="//*[@fileref]"/>
			</target>
		</project>
	</xsl:template>
	
	<xsl:template match="grafik|bild">
		<xsl:variable name="filename"><xsl:call-template name="get.filename"><xsl:with-param name="mediaref" select="@fileref"/></xsl:call-template></xsl:variable>
		<xsl:variable name="medianame"><xsl:call-template name="get.medianame"><xsl:with-param name="mediaref" select="@fileref"/></xsl:call-template></xsl:variable>
		<xsl:variable name="mediadir"><xsl:call-template name="get.mediadir"><xsl:with-param name="mediaref" select="@fileref"/></xsl:call-template></xsl:variable>
		<xsl:variable name="mediapath"><xsl:call-template name="get.mediapath"><xsl:with-param name="mediaref" select="@fileref"/></xsl:call-template></xsl:variable>
		<xsl:variable name="relativepath">
		    <xsl:call-template name="get.mediapath"><xsl:with-param name="mediaref" select="@sourcefile"/></xsl:call-template>
		</xsl:variable>
		<!--copy file="{$srcdir}/{@fileref}" todir="{$imagepath}" failonerror="false"/-->
		<!-- copy  todir="{$imagepath}" failonerror="false" flatten="true">
		    <fileset dir="{concat($srcdir,$relativepath,'/',$mediapath)}/../{$printgraphicdir}" includes="{$medianame}.eps"/>
	    </copy-->
	    <!-- eps2eps -->
	    <apply executable="eps2eps" dest="{$imagepath}" failonerror="false" vmlauncher="false">
			<srcfile/>
			<targetfile/>
			<fileset dir="{concat($srcdir,$relativepath,'/',$mediapath)}/../{$printgraphicdir}" includes="{$medianame}.eps"/>
			<mapper type="merge"  to="{$medianame}.eps"/>
		</apply>
		<!-- if eps does not exist autocreate it -->
		<!--apply executable="convert" dest="{$imagepath}" parallel="false">
			<arg value="-scale"/>
			<arg value="70%"/>
			<arg value="-quality"/>
			<arg value="100"/>
			<srcfile/>
			<targetfile/>
			<fileset dir="{$imagepath}" includes="{$filename}"/>
			<mapper type="merge"  to="{$medianame}.eps"/>
		</apply-->
	</xsl:template>
	
	<xsl:template match="animation|simulation|audio|video|textobjekt">
		<xsl:variable name="filename"><xsl:call-template name="get.filename"><xsl:with-param name="mediaref" select="@pikto"/></xsl:call-template></xsl:variable>
		<xsl:variable name="medianame"><xsl:call-template name="get.medianame"><xsl:with-param name="mediaref" select="@pikto"/></xsl:call-template></xsl:variable>
		<xsl:variable name="mediadir"><xsl:call-template name="get.mediadir"><xsl:with-param name="mediaref" select="@pikto"/></xsl:call-template></xsl:variable>
		<xsl:variable name="mediapath"><xsl:call-template name="get.mediapath"><xsl:with-param name="mediaref" select="@pikto"/></xsl:call-template></xsl:variable>
		<xsl:variable name="relativepath">
		    <xsl:call-template name="get.mediapath"><xsl:with-param name="mediaref" select="@sourcefile"/></xsl:call-template>
		</xsl:variable>
		<!--copy file="{$srcdir}/{@fileref}" todir="{$imagepath}" failonerror="false"/-->
		<copy  todir="{$imagepath}" failonerror="false" flatten="true">
		    <fileset dir="{concat($srcdir,$relativepath,'/',$mediapath)}/.." includes="**/{$medianame}.eps"/>
	              </copy>
		<!-- if eps does not exist autocreate it -->
		<!--apply executable="convert" dest="{$imagepath}" parallel="false">
			<arg value="-scale"/>
			<arg value="70%"/>
			<arg value="-quality"/>
			<arg value="100"/>
			<srcfile/>
			<targetfile/>
			<fileset dir="{$imagepath}" includes="{$filename}"/>
			<mapper type="merge"  to="{$medianame}.eps"/>
		</apply-->
	</xsl:template>

	
	<!--xsl:template match="animation|video|simulation|audio|textobjekt">
		<xsl:variable name="filename"><xsl:call-template name="get.filename"><xsl:with-param name="mediaref" select="@pikto"/></xsl:call-template></xsl:variable>
		<xsl:variable name="medianame"><xsl:call-template name="get.medianame"><xsl:with-param name="mediaref" select="@pikto"/></xsl:call-template></xsl:variable>
		<xsl:variable name="mediadir"><xsl:call-template name="get.mediadir"><xsl:with-param name="mediaref" select="@pikto"/></xsl:call-template></xsl:variable>
		<xsl:variable name="mediapath"><xsl:call-template name="get.mediapath"><xsl:with-param name="mediaref" select="@fileref"/></xsl:call-template></xsl:variable>
		<copy file="{$srcdir}/{@pikto}" todir="{$imagepath}" failonerror="false"/>
		<copy  todir="{$imagepath}" failonerror="false" flatten="true"><fileset dir="{$mediapath}/.." includes="**/{$medianame}.eps"/></copy>
		<apply executable="convert" dest="{$imagepath}" parallel="false">
			<arg value="-scale"/>
			<arg value="70%"/>
			<arg value="-quality"/>
			<arg value="100"/>
			<srcfile/>
			<targetfile/>
			<fileset dir="{$imagepath}" includes="{$filename}"/>
			<mapper type="merge"  to="{$medianame}.eps"/>
		</apply>

	</xsl:template-->

	
	
	

	
	<xsl:template match="text()"/>
</xsl:stylesheet>
