<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
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
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
		<project name="check_files" default="check">
			<target name="check">
				<xsl:if test="//processing-instruction('file')">
				<dependset>
					<srcfilelist>
						<xsl:attribute name="dir">${src.dir}</xsl:attribute>
						<xsl:attribute name="files"><xsl:apply-templates select="//processing-instruction('file')"/></xsl:attribute>
					</srcfilelist>
					<!--srcfilelist>
						<xsl:attribute name="dir">${src}</xsl:attribute>
						<xsl:attribute name="files"><xsl:apply-templates select="//grafik"/></xsl:attribute>
					</srcfilelist-->
					<targetfileset>
						<xsl:attribute name="dir">${common.dir}/${masterfile.plain}</xsl:attribute>
						<xsl:attribute name="includes">*.assembled.xml,*.assembled.lin.xml,*.assembled.lin.cu.xml,*.assembled.lin.cu.num.xml</xsl:attribute>
					</targetfileset>
				</dependset>
				</xsl:if>
			</target>
		</project>
	</xsl:template>
	<xsl:template match="processing-instruction('file')">
		<xsl:value-of select="normalize-space(.)"/>,</xsl:template>
	
	<xsl:template match="processing-instruction('file')[not(following::processing-instruction('file'))]">
		<xsl:value-of select="normalize-space(.)"/>
	</xsl:template>
	
	<!--xsl:template match="grafik">
		<xsl:value-of select="normalize-space(@fileref)"/>,</xsl:template>
	<xsl:template match="grafik[not(following::grafik)]">
		<xsl:value-of select="normalize-space(@fileref)"/>
	</xsl:template-->

	
	<xsl:template match="node()">
		<xsl:apply-templates select="node()"/>
	</xsl:template>
</xsl:stylesheet>
