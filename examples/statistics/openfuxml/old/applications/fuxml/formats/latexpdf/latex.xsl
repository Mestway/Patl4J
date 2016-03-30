<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!-- ********************************************************************
	$Id: latex.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:log="http://www.fernuni-hagen.de/FuXML" xmlns:doc="http://www.fernuni-hagen.de/fuxml/designinterface">
	<xsl:output method="text"  encoding="UTF-8"/><!--ISO-8859-1 -->
	<!-- output= Latin 1 führt zu Problemen bei Zeichen außerhalb von Latin 1, z.B. Latin Extended-A
	Saxon: "Output character not available in this encoding"
	 -->
	<xsl:strip-space elements="*"/>
	
	<xsl:param name="image_path">../eps</xsl:param>
	<!-- workdir and documentname are declared in params.xsl -->
	<xsl:key name="id_key" match="*" use="@id"/>
	
	<xsl:variable name="printgraphic">eps</xsl:variable> <!-- Endung bei Grafiken fuer Druck -->	

	<!--xsl:include href="../common/settings.xsl"/-->
	<xsl:include href="kurs.xsl"/>
	<xsl:include href="header.xsl"/>
	<xsl:include href="abschnitt.xsl"/>
	<xsl:include href="absatz.xsl"/>
	<xsl:include href="redaktion.xsl"/>
	<xsl:include href="table.xsl"/>
	<xsl:include  href="technik.xsl"/>
	<xsl:include href="math.xsl"/>
	<xsl:include href="mathenv.xsl"/>
	<xsl:include href="titelseite.xsl"/>
	<xsl:include href="index.xsl"/>
	<xsl:include href="fussnoten.xsl"/>
	<xsl:include href="media.xsl"/>
	<xsl:include href="xref.xsl"/>
	<xsl:include href="counter.xsl"/>
	<xsl:include href="../common/assignments.xsl"/>
	<xsl:include href="lists.xsl"/>
	<xsl:include href="jura.xsl"/>
	<xsl:include href="didaktik.xsl"/>
	<xsl:include href="designinterface-latexpdf.xsl"/>
	<xsl:include href="toc.xsl"/>
	<xsl:include href="marginalie.xsl"/>
	<!--xsl:include href="../common/preservespace.xsl"/-->
	<!-- ==================================================================== -->
	<xsl:template match="document">
		<xsl:variable name="style" select="$styles/entry[@name='document']"/>
		<xsl:call-template name="container"><xsl:with-param name="style" select="$style"/></xsl:call-template>
	</xsl:template>
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
	
	
	<!--xsl:template name="environment">
		<xsl:param name="type"/>
		<xsl:param name="name"/>
		<xsl:param name="marg"/>
		
		\vspace{<xsl:value-of select="$env_vspace"/>}
		<xsl:choose>
			<xsl:when test="$type='plain'">
				<xsl:if test="$name!=''">{\bf <xsl:value-of select="concat($name, @number, $env_separator)"/>}</xsl:if>
				<xsl:if test="zwischentitel">{\bf <xsl:apply-templates select="zwischentitel"/>}\newline </xsl:if>
				<xsl:apply-templates select="node()[name()!='zwischentitel']"/>
			</xsl:when>
			
			<xsl:when test="$type='italic'">
				<xsl:if test="$name!=''">{\bf <xsl:value-of select="concat($name, @number, $env_separator)"/>}</xsl:if>
				<xsl:if test="zwischentitel">{\bf <xsl:apply-templates select="zwischentitel"/>}\newline </xsl:if>
				{\slshape <xsl:apply-templates select="node()[name()!='zwischentitel']"/>}
			</xsl:when>
			
			<xsl:when test="$type='box'">
				<xsl:if test="$name!=''">{\bf <xsl:value-of select="concat($name, @number, $env_separator)"/>}</xsl:if>
				<xsl:if test="zwischentitel"><xsl:apply-templates select="zwischentitel"/>\newline </xsl:if>
				<xsl:apply-templates select="node()[name()!='zwischentitel']"/>
			</xsl:when>
		</xsl:choose>
		\vspace{<xsl:value-of select="$env_vspace"/>}
	</xsl:template-->

</xsl:stylesheet>