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

- Packaging of course units
- numbering of sections 
-->

<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="no"/>
<xsl:include href="preservespace.xsl"/>
<xsl:strip-space elements="abschnitt"/><!-- get rid of whitespace in numbers-->
<xsl:include href="designinterface.xsl"/>
<xsl:variable name="numscheme" select="$config/config/sectionnumbering/@activescheme"/>

<xsl:template match="@*|node()">
<xsl:copy><xsl:apply-templates select="@*|node()"/></xsl:copy>
</xsl:template>

<xsl:template match="lehrtext[parent::kurs]"><xsl:apply-templates select="fnabschnitt"/></xsl:template>

<!--xsl:template match="kurseinheiten"><xsl:apply-templates/></xsl:template>

<xsl:template match="kurseinheit"><kurseinheit><xsl:apply-templates/></kurseinheit></xsl:template-->

<xsl:template match="ke-lehrtext">
<xsl:variable name="part"><xsl:number count="ke-lehrtext" from="kurseinheit"/></xsl:variable>
<xsl:copy>
	<xsl:apply-templates select="//lehrtext/node()
		[preceding::processing-instruction('CUSTART') = concat(current()/ancestor::kurseinheit/@number,'_',$part)]
		[following::processing-instruction('CUEND') = concat(current()/ancestor::kurseinheit/@number,'_',$part)]"/>
</xsl:copy>
</xsl:template>

<xsl:template match="abschnitt">
	<xsl:variable name="level"><xsl:value-of select="current()/@level"/></xsl:variable>
	<xsl:copy>
		<xsl:if test="titel/@num='an' and not(ancestor::textobjekt)">
			<xsl:attribute name="number">
				<xsl:choose>
					<xsl:when test="ancestor::anhang">
						<xsl:apply-templates select="$styles/entry[@name='abschnitt' and @level=concat('a',$level)]/numberformat[@scheme=$numscheme]">
							<xsl:with-param name="contextnode" select="current()"/>
						</xsl:apply-templates>
					</xsl:when>
					<xsl:otherwise>
						<xsl:apply-templates select="$styles/entry[@name='abschnitt' and @level=$level]/numberformat[@scheme=$numscheme]">
							<xsl:with-param name="contextnode" select="current()"/>
						</xsl:apply-templates>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
		</xsl:if>
		<xsl:attribute name="level"><xsl:value-of select="$level"/></xsl:attribute>
		<xsl:apply-templates select="@*|titel"/>
	</xsl:copy>
</xsl:template>

<!-- =========== Start Section Numbering ================ --> 
	<!-- A number part is processed -->
	<xsl:template match="processing-instruction('numberpart')">
		<xsl:param name="contextnode"/>
		<xsl:if test="$contextnode/titel/@num='an'">
			<xsl:choose>
				<xsl:when test="$contextnode/ancestor::anhang">
					<xsl:apply-templates select="$config/config/sectionnumbering/sectionnumberformat[@scheme=$numscheme]/numberpart[@level=concat('a',current()/.)]">
						<xsl:with-param name="contextnode" select="$contextnode"/>
					</xsl:apply-templates>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates select="$config/config/sectionnumbering/sectionnumberformat[@scheme=$numscheme]/numberpart[@level=current()/.]">
						<xsl:with-param name="contextnode" select="$contextnode"/>
					</xsl:apply-templates>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
	
	<!-- This template selects the numberpart settings an calls back on the section to do the actual numbering.
			The numbering cannot be carried out within numberpart element because it does not belong
			to the document (section) context. -->
	<xsl:template match="numberpart">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode" mode="count">
			<xsl:with-param name="numberpart" select="current()"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<!-- Finally, the numbering is carried out within the section context --> 
	<xsl:template match="abschnitt" mode="count">
		<xsl:param name="numberpart"/>
		<xsl:choose>
			<xsl:when test="ancestor::anhang">
				<xsl:variable name="level" select="number(substring-after($numberpart/@level,'a'))"/>
				<xsl:choose>
					<xsl:when test="$numberpart/@resetlevel != 'cu'">
						<xsl:number count="abschnitt[titel/@num='an' and @level=$level and ancestor::anhang]" from="abschnitt[@level=$numberpart/@resetlevel and ancestor::anhang]" format="{$numberpart/@style}" level="any"/>
					</xsl:when>
					<xsl:when test="$numberpart/@resetlevel='cu'">
						<xsl:number count="abschnitt[titel/@num='an' and @level=$level and ancestor::anhang]" from="anhang" format="{$numberpart/@style}" level="any"/>
					</xsl:when>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
			<xsl:choose>
				<xsl:when test="$numberpart/@resetlevel != 'cu'">
					<xsl:number count="abschnitt[titel/@num='an' and @level=$numberpart/@level and not(ancestor::textobjekt) and not(ancestor::anhang)]" from="abschnitt[@level=$numberpart/@resetlevel]" format="{$numberpart/@style}" level="any"/>
				</xsl:when>
				<xsl:when test="$numberpart/@resetlevel='cu'">
					<xsl:number count="abschnitt[titel/@num='an' and @level=$numberpart/@level and not(ancestor::textobjekt) and not(ancestor::anhang)]" from="processing-instruction('CUSTART')[substring-after(.,'_') = '1']" format="{$numberpart/@style}" level="any"/>
				</xsl:when>
			</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	
	
	
<!-- =========== End Section Numbering ================ --> 


</xsl:stylesheet>
