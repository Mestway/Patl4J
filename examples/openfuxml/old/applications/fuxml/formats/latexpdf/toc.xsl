<?xml version="1.0" encoding="UTF-8"?>
<!-- ********************************************************************
	$Id: toc.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
	
	<xsl:template match="toctitle">
		<xsl:param name="contextnode"/>
		%\markboth{<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}{<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}
		{<xsl:call-template name="get.font-family"><xsl:with-param name="style" select="@font-family"/></xsl:call-template>
		<xsl:call-template name="get.font-style"><xsl:with-param name="style" select="@font-style"/></xsl:call-template>
		<xsl:call-template name="get.font-variant"><xsl:with-param name="style" select="@font-variant"/></xsl:call-template>
		<xsl:call-template name="get.font-weight"><xsl:with-param name="style" select="@font-weight"/></xsl:call-template>
		<xsl:call-template name="get.font-size"><xsl:with-param name="style" select="@font-size"/></xsl:call-template>
		<xsl:if test="@space-before!=''">\vspace{<xsl:value-of select="@space-before"/>}</xsl:if>
		<xsl:if test="@indent">\hspace*{<xsl:value-of select="@indent"/>}</xsl:if>
		<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>
		<xsl:if test="@space-after!=''">\\*[<xsl:value-of select="@space-after"/>]</xsl:if>}
	</xsl:template>

	
	<xsl:template match="kurs" mode="toc">
		<xsl:variable name="tocsettings" select="$config/config/toc[@variant='course']"/>
		<xsl:apply-templates select="$tocsettings/toctitle"/>
		<xsl:apply-templates select="descendant::abschnitt" mode="tocline"><xsl:with-param name="tocsettings" select="$tocsettings"/></xsl:apply-templates>
	</xsl:template>


	<xsl:template match="kurseinheit" mode="toc">
		<xsl:param name="tocstructure"/>
		<xsl:variable name="tocsettings" select="$config/config/toc[@context='courseunit']"/>
		<xsl:apply-templates select="$tocsettings/toctitle"><xsl:with-param name="contextnode" select="current()"/></xsl:apply-templates>
		<xsl:if test="$tocsettings/@tocdepth!=''">\setcounter{tocdepth}{<xsl:value-of select="$tocsettings/@tocdepth"/>}</xsl:if>
		<xsl:if test="$tocsettings/@pnumwidth!=''">\makeatletter\renewcommand{\@pnumwidth}{<xsl:value-of select="$tocsettings/@pnumwidth"/>}\makeatother</xsl:if>
		<xsl:if test="$tocsettings/@tocrmarg!=''">\makeatletter\renewcommand{\@tocrmarg}{<xsl:value-of select="$tocsettings/@tocrmarg"/>}\makeatother</xsl:if>
		<xsl:if test="$tocsettings/@dotsep!=''">\makeatletter\renewcommand{\@dotsep}{<xsl:value-of select="$tocsettings/@dotsep"/>}\makeatother</xsl:if>
		<xsl:apply-templates select="$tocstructure/self::tocstructure">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="tocsettings" select="$tocsettings"/>
		</xsl:apply-templates>
		<!-- \makeatletter<xsl:if test="$tocsettings/@style='continuous'">\input{<xsl:value-of select="$file/@precedingfile"/>.toc}</xsl:if>\@starttoc{toc}\makeatother
		<xsl:apply-templates select="ke-lehrtext/abschnitt" mode="tocline"><xsl:with-param name="tocsettings" select="$tocsettings"/></xsl:apply-templates>-->
	</xsl:template>
	
	<xsl:template match="kurseinheit" mode="tocline">
		<xsl:param name="tocsettings"/>
		<xsl:variable name="tocline" select="$tocsettings/tocline[@level='cu']"/>
		\addtocontents{toc}{{<xsl:apply-templates select="$tocline/line"><xsl:with-param name="contextnode" select="current()"/></xsl:apply-templates>}}
	</xsl:template>

	
	
	
	<!-- ==================================================================== -->
	<!--                                                      TOC                                                                             -->	
	<!-- ==================================================================== -->

	<xsl:template match="processing-instruction('toc')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode" mode="toc">
			<xsl:with-param name="tocstructure" select="ancestor::entry/tocstructure"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="processing-instruction('toc-course')">
		<xsl:param name="contextnode"/>
		<xsl:variable name="tocsettings" select="$config/config/toc[@context='course']"/>
		{<xsl:apply-templates select="$tocsettings/toctitle"/>}
		<xsl:if test="$tocsettings/@tocdepth!=''">\setcounter{tocdepth}{<xsl:value-of select="$tocsettings/@tocdepth"/>}</xsl:if>
		<xsl:if test="$tocsettings/@pnumwidth!=''">\makeatletter\renewcommand{\@pnumwidth}{<xsl:value-of select="$tocsettings/@pnumwidth"/>}\makeatother</xsl:if>
		<xsl:if test="$tocsettings/@tocrmarg!=''">\makeatletter\renewcommand{\@tocrmarg}{<xsl:value-of select="$tocsettings/@tocrmarg"/>}\makeatother</xsl:if>
		<xsl:if test="$tocsettings/@dotsep!=''">\makeatletter\renewcommand{\@dotsep}{<xsl:value-of select="$tocsettings/@dotsep"/>}\makeatother</xsl:if>
		\setlength{\parskip}{0pt}\makeatletter\InputIfFileExists{toc-course.toc}{}{}\makeatother
	</xsl:template>


	
	

	<xsl:template match="tocstructure">
		<xsl:param name="contextnode"/>
		<xsl:param name="tocsettings"/>
		<xsl:apply-templates>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="tocsettings" select="$tocsettings"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="includetoc">
		 \makeatletter\input{<xsl:value-of select="."/>.toc}\makeatother
	</xsl:template>
	
	<xsl:template match="currenttoc">
		<xsl:param name="contextnode"/>
		<xsl:param name="tocsettings"/>
		\setlength{\parskip}{0pt}\makeatletter\@starttoc{toc}\makeatother
		<!--xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="tocsettings" select="$tocsettings"/>
		</xsl:apply-templates-->
	</xsl:template>
	
	<!-- ==================================================================== -->
	<!--                                                     Other TOC structures                                                  -->	
	<!-- ==================================================================== -->

	<xsl:template match="processing-instruction('tofig')">
		<xsl:param name="contextnode"/>
		<xsl:if test="$contextnode/descendant::medienobjekt[objekttitel[@mediennum='bild-grafik']]"><xsl:variable name="tocsettings" select="$config/config/specialtables[@context!='course'][1]"/>
		{<xsl:apply-templates select="$tocsettings/figure/label"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}
		<xsl:if test="$tocsettings/@tocdepth!=''">\setcounter{tocdepth}{<xsl:value-of select="$tocsettings/@tocdepth"/>}</xsl:if>
		<xsl:if test="$tocsettings/@pnumwidth!=''">\makeatletter\renewcommand{\@pnumwidth}{<xsl:value-of select="$tocsettings/@pnumwidth"/>}\makeatother</xsl:if>
		<xsl:if test="$tocsettings/@tocrmarg!=''">\makeatletter\renewcommand{\@tocrmarg}{<xsl:value-of select="$tocsettings/@tocrmarg"/>}\makeatother</xsl:if>
		<xsl:if test="$tocsettings/@dotsep!=''">\makeatletter\renewcommand{\@dotsep}{<xsl:value-of select="$tocsettings/@dotsep"/>}\makeatother</xsl:if>
		\setlength{\parskip}{0pt}\makeatletter\@starttoc{tofig}\makeatother</xsl:if>
	</xsl:template>
	
	<xsl:template match="processing-instruction('totable')">
		<xsl:param name="contextnode"/>
		<xsl:if test="$contextnode/descendant::tabelle or $contextnode/descendant::tabelle-alt"><xsl:variable name="tocsettings" select="$config/config/specialtables[@context!='course'][1]"/>
		{<xsl:apply-templates select="$tocsettings/table/label"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}
		<xsl:if test="$tocsettings/@tocdepth!=''">\setcounter{tocdepth}{<xsl:value-of select="$tocsettings/@tocdepth"/>}</xsl:if>
		<xsl:if test="$tocsettings/@pnumwidth!=''">\makeatletter\renewcommand{\@pnumwidth}{<xsl:value-of select="$tocsettings/@pnumwidth"/>}\makeatother</xsl:if>
		<xsl:if test="$tocsettings/@tocrmarg!=''">\makeatletter\renewcommand{\@tocrmarg}{<xsl:value-of select="$tocsettings/@tocrmarg"/>}\makeatother</xsl:if>
		<xsl:if test="$tocsettings/@dotsep!=''">\makeatletter\renewcommand{\@dotsep}{<xsl:value-of select="$tocsettings/@dotsep"/>}\makeatother</xsl:if>
		\setlength{\parskip}{0pt}\makeatletter\@starttoc{totable}\makeatother</xsl:if>
	</xsl:template>
	
	
	<xsl:template match="processing-instruction('tofig-course')">
		<xsl:param name="contextnode"/>
		<xsl:variable name="tocsettings" select="$config/config/specialtables[@context='course']"/>
		{<xsl:apply-templates select="$tocsettings/figure/label"/>}
		<xsl:if test="$tocsettings/@tocdepth!=''">\setcounter{tocdepth}{<xsl:value-of select="$tocsettings/@tocdepth"/>}</xsl:if>
		<xsl:if test="$tocsettings/@pnumwidth!=''">\makeatletter\renewcommand{\@pnumwidth}{<xsl:value-of select="$tocsettings/@pnumwidth"/>}\makeatother</xsl:if>
		<xsl:if test="$tocsettings/@tocrmarg!=''">\makeatletter\renewcommand{\@tocrmarg}{<xsl:value-of select="$tocsettings/@tocrmarg"/>}\makeatother</xsl:if>
		<xsl:if test="$tocsettings/@dotsep!=''">\makeatletter\renewcommand{\@dotsep}{<xsl:value-of select="$tocsettings/@dotsep"/>}\makeatother</xsl:if>
		\setlength{\parskip}{0pt}\makeatletter\InputIfFileExists{tofig-course.tofig}{}{}\makeatother
	</xsl:template>
	
	<xsl:template match="processing-instruction('totable-course')">
		<xsl:param name="contextnode"/>
		<xsl:variable name="tocsettings" select="$config/config/specialtables[@context='course']"/>
		{<xsl:apply-templates select="$tocsettings/table/label"/>}
		<xsl:if test="$tocsettings/@tocdepth!=''">\setcounter{tocdepth}{<xsl:value-of select="$tocsettings/@tocdepth"/>}</xsl:if>
		<xsl:if test="$tocsettings/@pnumwidth!=''">\makeatletter\renewcommand{\@pnumwidth}{<xsl:value-of select="$tocsettings/@pnumwidth"/>}\makeatother</xsl:if>
		<xsl:if test="$tocsettings/@tocrmarg!=''">\makeatletter\renewcommand{\@tocrmarg}{<xsl:value-of select="$tocsettings/@tocrmarg"/>}\makeatother</xsl:if>
		<xsl:if test="$tocsettings/@dotsep!=''">\makeatletter\renewcommand{\@dotsep}{<xsl:value-of select="$tocsettings/@dotsep"/>}\makeatother</xsl:if>
		\setlength{\parskip}{0pt}\makeatletter\InputIfFileExists{totable-course.totable}{}{}\makeatother
	</xsl:template>


	
	<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->

	<xsl:template match="tocline"><xsl:param name="contextnode"/><xsl:if test="$contextnode/objekttitel/@mediennum != 'ohne' or $contextnode/titel/@num ='an'">\addtocontents{<xsl:call-template name="select.toctype"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:call-template>}{
		<xsl:if test="@space-before!=''">\vspace{<xsl:value-of select="@space-before"/>}</xsl:if>\protect\@dottedtocline{1}
		{<xsl:value-of select="@indent"/>}{<xsl:value-of select="@numwidth"/>}
		{\numberline{<xsl:apply-templates select="number"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}<xsl:apply-templates select="line"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}
		{<xsl:apply-templates select="page"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}
		<xsl:if test="@space-after!=''">\vspace{<xsl:value-of select="@space-after"/>}</xsl:if>}</xsl:if></xsl:template>	

	
	
	<xsl:template name="select.toctype">
		<xsl:param name="contextnode"/>
		<xsl:choose>
			<xsl:when test="$contextnode/self::medienobjekt">
				<xsl:choose>
					<xsl:when test="$contextnode/objekttitel/@mediennum='bild-grafik'">tofig</xsl:when>
				</xsl:choose>
			</xsl:when>
			<xsl:when test="$contextnode/self::tabelle or $contextnode/self::tabelle-alt">totable</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="label[ancestor::specialtables]">
		<xsl:param name="contextnode"/>
		%\markboth{<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}{\markboth{<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}
		{<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}
	</xsl:template>
	
	<xsl:template match="number|page"><xsl:param name="contextnode"/>
		<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="line"><xsl:param name="contextnode"/>
		<xsl:apply-templates select="node()" mode="toc"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>
	</xsl:template>
	
	

	
<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
	<!-- These elements can be used in config.xml to add entries for toc -->
	
	<xsl:template match="addcontentsline[@level!=0]"><xsl:param name="contextnode"/>\addtocontents{toc}{
		<xsl:if test="@space-before!=''">\vspace{<xsl:value-of select="@space-before"/>}</xsl:if>\protect\dottedtocline{<xsl:value-of select="@level"/>}
		{<xsl:value-of select="@indent"/>}{<xsl:value-of select="@numwidth"/>}
		{\numberline{}<xsl:apply-templates select="line/node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}
		{<xsl:apply-templates select="page/node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}<xsl:if test="@space-after!=''">\vspace{<xsl:value-of select="@space-after"/>}</xsl:if>
		}</xsl:template>
	
	<xsl:template match="addcontentsline[@level=0]"><xsl:param name="contextnode"/>\addtocontents{toc}{
		<xsl:if test="@space-before!=''">\vspace{<xsl:value-of select="@space-before"/>}</xsl:if>{<xsl:apply-templates select="line/node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}<xsl:if test="@space-after!=''">\vspace{<xsl:value-of select="@space-after"/>}</xsl:if>}
</xsl:template>
	<!-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
	<!-- Elements have to be treated differently in toc, e.g. no footnotes  -->
	
	<xsl:template match="processing-instruction('title')" mode="toc">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/titel/node()[not(self::untertitel)]|$contextnode/zwischentitel|$contextnode/objekttitel" mode="toc"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('subtitle')" mode="toc">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/titel/untertitel" mode="toc"/>
	</xsl:template>

	
	<xsl:template match="titel|zwischentitel|objekttitel" mode="toc"><xsl:apply-templates mode="toc"/></xsl:template>
	
	<xsl:template match="zeilenende" mode="toc">\\</xsl:template>
	
	<!--xsl:template match="absatz|absatz-klein|absatz-mini|absatz-ohne" mode="toc"><xsl:apply-templates mode="toc"/>\\</xsl:template-->
	
	<xsl:template match="*" mode="toc"><xsl:value-of select="text()"/></xsl:template>
	
	<xsl:template match="untertitel" mode="toc"><xsl:value-of select="text()"/></xsl:template>
	
	<xsl:template match="indexfix|primaer|sekundaer|tertiaer|siehe|sieheauch" mode="toc"/>
	<xsl:template match="fussnote" mode="toc"/>
	
	<xsl:template match="formel-imtext" mode="toc">$<xsl:apply-templates/>$</xsl:template>
	
</xsl:stylesheet>
