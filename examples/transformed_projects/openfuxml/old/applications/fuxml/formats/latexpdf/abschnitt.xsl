<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<!-- ********************************************************************
		$Id: abschnitt.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
	<xsl:strip-space elements="title"/>
	
	<xsl:template match="abschnitt"><xsl:call-template name="section"/></xsl:template>
	
	<xsl:template match="abschnitt[titel/@num='aus']"><xsl:call-template name="section-nonum"/></xsl:template>
	
	<xsl:template match="abschnitt[ancestor::anhang][titel/@num='an']"><xsl:call-template name="section-appendix"/></xsl:template>
	
	
	<xsl:template name="section">
		<xsl:param name="style" select="$styles/entry[@name='abschnitt'][@level=current()/@level]"/>
		<!-- no newpage if 
			preceding sibling is 'abschnitt' or
			preceding sibling is 'motto'
		--><!-- sections in assignments do not implement the pagebreaking mechanism -->
		<!--xsl:if test="$style/@newpage='true'
			and not(ancestor::einsendeaufgaben)
			and not(preceding-sibling::*[1]/self::abschnitt)
			and not(preceding-sibling::*[1]/self::motto) 
			">\newpage</xsl:if-->
		<xsl:apply-templates select="$style/title">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$styles/entry[@name='abschnitt'][@level=current()/@level]"/>
		</xsl:apply-templates><xsl:call-template name="write.section.tocline"><xsl:with-param name="style" select="$style"/></xsl:call-template>
	</xsl:template> 
	
	<xsl:template name="section-appendix">
		<!-- appendix section is identified by starting with 'a' -->
		<xsl:param name="style" select="$styles/entry[@name='abschnitt'][@level=concat('a',current()/@level)]"/>
		<!--xsl:if test="$style/@newpage='true'
			and not(preceding-sibling::*[1]/self::abschnitt)
			and not(preceding-sibling::*[1]/self::motto) 
			">\newpage</xsl:if-->
		<xsl:apply-templates select="$style/title">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$styles/entry[@name='abschnitt'][concat('a',@level=current()/@level)]"/>
		</xsl:apply-templates><xsl:call-template name="write.section.tocline"><xsl:with-param name="style" select="$style"/></xsl:call-template>
	</xsl:template> 

	
	<xsl:template name="section-nonum">
		<xsl:param name="style" select="$styles/entry[@name='abschnitt-nonum'][@level=current()/@level]"/>
		<!--xsl:if test="$style/@newpage='true'
			and not(ancestor::einsendeaufgaben)
			and not(preceding-sibling::*[1]/self::abschnitt)
			and not(preceding-sibling::*[1]/self::motto) 
			">\newpage</xsl:if-->
		<xsl:apply-templates select="$style/title">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$styles/entry[@name='abschnitt-nonum'][@level=current()/@level]"/>
		</xsl:apply-templates><xsl:call-template name="write.section.tocline"><xsl:with-param name="style" select="$style"/></xsl:call-template>
	</xsl:template>
	
	<xsl:template name="section-assignments">
		
		<xsl:param name="style" select="$styles/entry[@name='abschnitt'][@level=current()/@level]"/>
		<xsl:apply-templates select="$style/title">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$styles/entry[@name='abschnitt'][@level=current()/@level]"/>
		</xsl:apply-templates>
	</xsl:template> 

	
	<xsl:template match="title">
		<xsl:param name="contextnode"/>
		<xsl:param name="style"/>
		<xsl:if test="@space-before!='' and name($contextnode/preceding-sibling::*[1])!='abschnitt' ">
		\vspace{<xsl:value-of select="@space-before"/>}</xsl:if>\setlength{\parskip}{<xsl:value-of select="@space-after"/>}
		<xsl:if test="name($contextnode/preceding-sibling::*[1])!='abschnitt' and name($contextnode/preceding-sibling::*[1])!='motto' ">\pagebreak[3]</xsl:if><!--\noindent{}-->
		<xsl:apply-templates select="$contextnode/titel/margref" mode="call"/>\makeatletter\@ssect{0cm}{0cm}{<xsl:value-of select="@space-after"/>}{}{<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>\makeatother\label{<xsl:value-of select="$contextnode/@id"/>}}
		</xsl:template>

	

	
	<xsl:template name="write.section.tocline"><xsl:param name="style"/><xsl:if test="titel!=''"><xsl:if test="$style/tocline">
		<xsl:if test="$style/tocline/@space-before!=''">\addtocontents{toc}{\vspace{<xsl:value-of select="$style/tocline/@space-before"/>}}</xsl:if>
		<xsl:if test="@level='1'">\addtocontents{toc}{\protect\pagebreak[3]}</xsl:if>
		\addtocontents{toc}{
		\protect\dottedtocline{<xsl:value-of select="@level"/>}
		{<xsl:value-of select="$style/tocline/@indent"/>}{<xsl:value-of select="$style/tocline/@numwidth"/>}
		{\numberline{<xsl:choose>
			<xsl:when test="$style/tocline/number"><xsl:apply-templates select="$style/tocline/number"><xsl:with-param name="contextnode" select="current()"/></xsl:apply-templates></xsl:when>
			<xsl:otherwise><xsl:value-of select="@number"/></xsl:otherwise>
		</xsl:choose>}<xsl:apply-templates select="$style/tocline/line"><xsl:with-param name="contextnode" select="current()"/></xsl:apply-templates>}
		{<xsl:apply-templates select="$style/tocline/page"><xsl:with-param name="contextnode" select="current()"/></xsl:apply-templates>}
		<xsl:if test="$style/tocline/@space-after!=''">\vspace{<xsl:value-of select="$style/tocline/@space-after"/>}</xsl:if>}</xsl:if></xsl:if></xsl:template>	
	
	
	<!--xsl:template match="title">
		<xsl:param name="contextnode"/>
		<xsl:param name="style"/>
		<xsl:if test="@space-before!='' and name($contextnode/preceding-sibling::*[1])!='abschnitt' ">
		\vspace{<xsl:value-of select="@space-before"/>}</xsl:if>
		<xsl:if test="name($contextnode/preceding-sibling::*[1])!='abschnitt' ">\pagebreak[3]</xsl:if><xsl:choose><xsl:when test="$contextnode/@level=1">\markboth{<xsl:value-of select="concat($contextnode/@number,' ',$contextnode/titel)"/>}{}</xsl:when>
		<xsl:when test="$contextnode/@level=2">\markright{<xsl:value-of select="concat($contextnode/@number,' ',$contextnode/titel)"/>}</xsl:when></xsl:choose>\noindent{}{<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}<xsl:if test="@space-after!=''">\\*[<xsl:value-of select="@space-after"/>]</xsl:if>
		</xsl:template-->


	
	<xsl:template match="titel[parent::abschnitt]"><xsl:apply-templates select="node()[not(self::untertitel)]"/></xsl:template>
	
	<xsl:template match="untertitel[parent::abschnitt]"><xsl:apply-templates/></xsl:template>
	
	
	
	
	
	
	
	
	
	
	
	
	<xsl:template match="abschnitt" mode="old"><xsl:apply-templates/></xsl:template>

<xsl:template match="titel[parent::abschnitt]" mode="old">
		<xsl:text xml:space="preserve">&#xD;&#xA;&#xD;&#xA;</xsl:text>
		<xsl:choose>
			<xsl:when test="parent::abschnitt/@level = 0">\chapter<xsl:if test="@num='aus'">*</xsl:if>{</xsl:when>
			<xsl:when test="parent::abschnitt/@level = 1">\chapter<xsl:if test="@num='aus'">*</xsl:if>{</xsl:when>
			<xsl:when test="parent::abschnitt/@level = 2">\section<xsl:if test="@num='aus'">*</xsl:if>{</xsl:when>
			<xsl:when test="parent::abschnitt/@level  = 3">\subsection<xsl:if test="@num='aus'">*</xsl:if>{</xsl:when>
			<xsl:when test="parent::abschnitt/@level  = 4">\subsubsection<xsl:if test="@num='aus'">*</xsl:if>{</xsl:when>
			<xsl:when test="parent::abschnitt/@level  = 5">\paragraph<xsl:if test="@num='aus'">*</xsl:if>{</xsl:when>
			<xsl:when test="parent::abschnitt/@level  = 6">\subparagraph<xsl:if test="@num='aus'">*</xsl:if>{</xsl:when>
			<xsl:when test="parent::abschnitt/@level  = 7">\subsubparagraph<xsl:if test="@num='aus'">*</xsl:if>{</xsl:when>
			<xsl:when test="parent::abschnitt/@level  = 8">\subsubsubparagraph<xsl:if test="@num='aus'">*</xsl:if>{</xsl:when>
			<xsl:when test="parent::abschnitt/@level  = 9">\subsubsubsubparagraph<xsl:if test="@num='aus'">*</xsl:if>{</xsl:when>
		</xsl:choose><xsl:apply-templates select="node()[name()!='margref']"/>}\nopagebreak
				<xsl:apply-templates select="margref"/>
	</xsl:template>

<xsl:template match="textmarke">
	\label{<xsl:value-of select="@id"/>}
</xsl:template>

</xsl:stylesheet>