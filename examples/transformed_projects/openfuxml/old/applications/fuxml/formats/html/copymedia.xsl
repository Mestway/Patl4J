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
	 -->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:param name="srcdir"/>
	<xsl:param name="workdir"/>
	<xsl:param name="mathdir"/>
	<xsl:param name="convertfile"/>
	<xsl:param name="convertfilename"/>
	<xsl:include href="../common/mediainformation.xsl"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

	<xsl:template match="/">
		<project name="copymedia" default="copymedia">
			<target if="medianotavailable" name="convert">
				<apply executable="convert" parallel="false">
					<srcfile/>
					<fileset dir="{$srcdir}/bilder/druck/" includes="{$convertfile}.eps"/>
					<arg value="{$srcdir}/bilder/web/{$convertfilename}"/>
				</apply>
			</target>

			<target name="copymedia">
				<xsl:apply-templates
					select="//*[@fileref]|//processing-instruction('file')|//formel-imtext|//formel[not(parent::formelarray)]|//formelarray"
				/>
			</target>
		</project>
	</xsl:template>

	<xsl:template match="grafik|bild">
		<xsl:variable name="filename">
			<xsl:call-template name="get.filename">
				<xsl:with-param name="mediaref" select="@fileref"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="medianame">
			<xsl:call-template name="get.medianame">
				<xsl:with-param name="mediaref" select="@fileref"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="mediadir">
			<xsl:call-template name="get.mediadir">
				<xsl:with-param name="mediaref" select="@fileref"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="mediapath">
			<xsl:call-template name="get.mediapath">
				<xsl:with-param name="mediaref" select="@fileref"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="relativepath">
			<xsl:call-template name="get.mediapath">
				<xsl:with-param name="mediaref" select="@sourcefile"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:if test="@fileref!=''">
			<property name="convertfile" value="{$medianame}"> </property>
			<property name="convertfilename" value="{$filename}"> </property>
			<condition property="medianotavailable">
				<not>
					<available file="{$srcdir}{$relativepath}/{@fileref}"/>
				</not>
			</condition>
			<!--antcall target="convert"> </antcall-->
			<!--xsl:comment>srcdir: <xsl:value-of select="$srcdir"/></xsl:comment>
			<xsl:comment>relativpath: <xsl:value-of select="$relativepath"/></xsl:comment>
			<xsl:comment>mediapath: <xsl:value-of select="$mediapath"/></xsl:comment>
			<xsl:comment>mediadir: <xsl:value-of select="$mediadir"/></xsl:comment-->
			<copy file="{$srcdir}{$relativepath}/{@fileref}" todir="{$workdir}/{ancestor::file/@directory}/bilder"
				failonerror="false"/>
		</xsl:if>
	</xsl:template>

	<xsl:template match="simulation|animation|video|audio|textobjekt">
		<xsl:param name="path">
			<xsl:call-template name="get.mediapath">
				<xsl:with-param name="mediaref" select="@sourcefile"/>
			</xsl:call-template>
		</xsl:param>
		<xsl:variable name="mediadir">
			<xsl:call-template name="get.mediadir">
				<xsl:with-param name="mediaref" select="@fileref"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="mediapath">
			<xsl:call-template name="get.mediapath">
				<xsl:with-param name="mediaref" select="@fileref"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="mediasuffix">
			<xsl:call-template name="get.mediasuffix">
				<xsl:with-param name="mediaref" select="@fileref"/>
			</xsl:call-template>
		</xsl:variable>

		<xsl:choose>
			<xsl:when test="$mediasuffix='html'">
				<xsl:if test="@pikto!=''">
					<copy file="{$srcdir}/{$path}/{@pikto}"
						todir="{$workdir}/{ancestor::file/@directory}/bilder" failonerror="false"/>
				</xsl:if>
				<xsl:if test="@fileref!=''">
					<copy
						todir="{$workdir}/{ancestor::file/@directory}/{name(current())}/{$mediadir}"
						failonerror="false">
						<fileset dir="{$srcdir}/{$path}/{$mediapath}"/>
					</copy>
				</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<xsl:if test="@pikto!=''">
					<copy file="{$srcdir}/{$path}/{@pikto}"
						todir="{$workdir}/{ancestor::file/@directory}/bilder" failonerror="false"/>
				</xsl:if>
				<xsl:if test="@fileref!=''">
					<copy file="{$srcdir}/{$path}/{@fileref}"
						todir="{$workdir}/{ancestor::file/@directory}/{$mediadir}"
						failonerror="false"/>
				</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!--xsl:template match="simulation">
		<xsl:param name="path">
			<xsl:call-template name="get.mediapath">
				<xsl:with-param name="mediaref" select="@sourcefile"/>
			</xsl:call-template>
		</xsl:param>
		<xsl:variable name="mediadir"><xsl:call-template name="get.mediadir"><xsl:with-param name="mediaref" select="@fileref"/></xsl:call-template></xsl:variable>
		<xsl:variable name="mediapath"><xsl:call-template name="get.mediapath"><xsl:with-param name="mediaref" select="@fileref"/></xsl:call-template></xsl:variable>
		<xsl:if test="@pikto!=''"><copy file="{$srcdir}/{$path}/{@pikto}" todir="{$workdir}/{ancestor::file/@directory}/bilder" failonerror="false"/></xsl:if>
		<xsl:if test="@fileref!=''"><copy  todir="{$workdir}/{ancestor::file/@directory}/simulation/{$mediadir}" failonerror="false">
			<fileset dir="{$srcdir}/{$path}/{$mediapath}"/>
		</copy></xsl:if>
	</xsl:template-->

	<xsl:template match="formel-imtext|formel|formelarray">
		<copy file="{$mathdir}/{@hash}.png" todir="{$workdir}/{ancestor::file/@directory}/math"
			failonerror="false"/>
	</xsl:template>
	<xsl:template match="text()"/>

</xsl:stylesheet>
