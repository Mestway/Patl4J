<?xml version="1.0" encoding="UTF-8"?>
<!-- ********************************************************************
	$Id: lists.xsl,v 1.4 2007/03/14 14:16:54 gebhard Exp $
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
	

	<xsl:template match="aufzaehlungsliste|nummerierteliste">
		<xsl:param name="mode"/>
		<xsl:call-template name="list">
			<xsl:with-param name="style" select="$styles/entry[@name = name(current())][not(@variant)]"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:call-template>
	</xsl:template>
	
	<!-- Liste in Tabelle -->
	<!--xsl:template match="aufzaehlungsliste[parent::entry]|nummerierteliste[parent::entry]">
		<xsl:param name="mode"/>
		<xsl:call-template name="list">
			<xsl:with-param name="style" select="$styles/entry[@name = name(current())]"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:call-template>
	</xsl:template-->
	
	<!-- Liste in Marginalie wird separat konfiguriert -->
	<xsl:template match="aufzaehlungsliste[ancestor::marginalie]|nummerierteliste[ancestor::marginalie]">
		<xsl:call-template name="list">
			<xsl:with-param name="style" select="$styles/entry[@name = name(current())][@variant='marginalie']"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="eintrag[parent::aufzaehlungsliste]">
		<xsl:param name="mode"/>
		<xsl:variable name="label">
			<xsl:choose>
				<xsl:when test="ancestor::marginalie">
					<xsl:apply-templates select="$styles/entry[@name = 'aufzaehlungsliste'][@variant='marginalie']/label">
						<xsl:with-param name="contextnode" select="current()"/>
						<xsl:with-param name="mode" select="$mode"/>
					</xsl:apply-templates>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates select="$styles/entry[@name = 'aufzaehlungsliste'][not(@variant)]/label">
						<xsl:with-param name="contextnode" select="current()"/>
						<xsl:with-param name="mode" select="$mode"/>
					</xsl:apply-templates>
				</xsl:otherwise>
			</xsl:choose>		
		</xsl:variable>
		\item[{<xsl:value-of select="$label"/>}]<xsl:apply-templates><xsl:with-param name="mode" select="$mode"/></xsl:apply-templates>
	</xsl:template>
			
	<xsl:template match="processing-instruction('labelitem')">
		<xsl:param name="contextnode"/>
		<xsl:variable name="level" select="count($contextnode/ancestor::aufzaehlungsliste)"/>
		<xsl:variable name="style" select="ancestor::entry"/>
		<xsl:variable name="labelitem">
			<xsl:choose>
				<xsl:when test="$contextnode/parent::aufzaehlungsliste/@stiltyp='standard'">
					<xsl:choose>
						<xsl:when test="$level=1"><xsl:value-of select="$style/labelitemi"/></xsl:when>
						<xsl:when test="$level=2"><xsl:value-of select="$style/labelitemii"/></xsl:when>
						<xsl:when test="$level=3"><xsl:value-of select="$style/labelitemiii"/></xsl:when>
						<xsl:when test="$level=4"><xsl:value-of select="$style/labelitemiv"/></xsl:when>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise><xsl:value-of select="$contextnode/parent::aufzaehlungsliste/@stiltyp"/></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:call-template name="map.labelitem">
			<xsl:with-param name="item" select="$labelitem"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template name="map.labelitem">
		<xsl:param name="item"/>
		<xsl:choose>
			<xsl:when test="$item='punkt'">$\bullet$</xsl:when>
			<xsl:when test="$item='kreis'">$\circ$</xsl:when>
			<xsl:when test="$item='quadrat'">$\square$</xsl:when>
			<xsl:when test="$item='spiegelstrich'">{\bfseries --}</xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	
	<xsl:template match="eintrag[parent::nummerierteliste]">
		<xsl:param name="mode"/>
		<xsl:variable name="label">
			<xsl:choose>
				<xsl:when test="$styles/entry[@name = 'nummerierteliste'][not(@variant)]/label[@type=current()/parent::nummerierteliste/@stiltyp]">
					<xsl:apply-templates select="$styles/entry[@name = 'nummerierteliste'][not(@variant)]/label[@type=current()/parent::nummerierteliste/@stiltyp]">
						<xsl:with-param name="contextnode" select="current()"/>
						<xsl:with-param name="mode" select="$mode"/>
					</xsl:apply-templates>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates select="$styles/entry[@name = 'nummerierteliste'][not(@variant)]/label[not(@type)][1]">
						<xsl:with-param name="contextnode" select="current()"/>
						<xsl:with-param name="mode" select="$mode"/>
					</xsl:apply-templates>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		\item[{<xsl:value-of select="$label"/>}]<xsl:apply-templates><xsl:with-param name="mode" select="$mode"/></xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="eintrag[parent::nummerierteliste and ancestor::marginalie]" priority="1">
		<xsl:param name="mode"/>
		<xsl:variable name="label">
			<xsl:choose>
				<xsl:when test="$styles/entry[@name = 'nummerierteliste'][@variant='marginalie']/label[@type=current()/parent::nummerierteliste/@stiltyp]">
					<xsl:apply-templates select="$styles/entry[@name = 'nummerierteliste'][@variant='marginalie']/label[@type=current()/parent::nummerierteliste/@stiltyp]">
						<xsl:with-param name="contextnode" select="current()"/>
						<xsl:with-param name="mode" select="$mode"/>
					</xsl:apply-templates>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates select="$styles/entry[@name = 'nummerierteliste'][@variant='marginalie']/label[not(@type)][1]">
						<xsl:with-param name="contextnode" select="current()"/>
						<xsl:with-param name="mode" select="$mode"/>
					</xsl:apply-templates>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		\item[{<xsl:value-of select="$label"/>}]<xsl:apply-templates><xsl:with-param name="mode" select="$mode"/></xsl:apply-templates>
	</xsl:template>
	<!-- ==================================================================== -->
	
	<xsl:template match="universalliste[not(ancestor::glossar) and @typ='begriffsverz']">
		<xsl:param name="mode"/>
		<xsl:call-template name="genericlist">
			<xsl:with-param name="style" select="$styles/entry[@name = name(current())][@variant='default' or @variant='begriffsverz'][1]"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="universaleintrag[not(ancestor::glossar) and ancestor::universalliste[@typ='begriffsverz']]">
		<xsl:param name="mode"/>
		<xsl:apply-templates select="$styles/entry[@name = 'universalliste'][@variant='default' or @variant='begriffsverz'][1]/entrystructure[@format='latexpdf']">
				<xsl:with-param name="contextnode" select="current()"/>
				<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>

	<!-- ==================================================================== -->

	<xsl:template match="universalliste[@typ='abkuerzungsverz']">
		<xsl:param name="mode"/>
		<xsl:call-template name="genericlist">
			<xsl:with-param name="style" select="$styles/entry[@name = name(current())][@variant='abkuerzungsverz']"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="universaleintrag[ancestor::universalliste[@typ='abkuerzungsverz']]">
		<xsl:param name="mode"/>
		<xsl:apply-templates select="$styles/entry[@name = 'universalliste'][@variant='abkuerzungsverz']/entrystructure[@format='latexpdf']">
				<xsl:with-param name="contextnode" select="current()"/>
				<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>

	<!-- ==================================================================== -->

	<xsl:template match="universalliste[@typ='symbolverz']">
		<xsl:param name="mode"/>
		<xsl:call-template name="genericlist">
			<xsl:with-param name="style" select="$styles/entry[@name = name(current())][@variant='symbolverz']"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="universaleintrag[ancestor::universalliste[@typ='symbolverz']]">
		<xsl:param name="mode"/>
		<xsl:apply-templates select="$styles/entry[@name = 'universalliste'][@variant='symbolverz']/entrystructure[@format='latexpdf']">
				<xsl:with-param name="contextnode" select="current()"/>
				<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>

	
	<!-- ==================================================================== -->

	<xsl:template match="universalliste[ancestor::glossar and @typ='begriffsverz']">
		<xsl:param name="mode"/>
		<xsl:call-template name="genericlist">
			<xsl:with-param name="style" select="$styles/entry[@name = name(current())][@variant='glossary']"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="universaleintrag[ancestor::glossar and parent::universalliste/@typ='begriffsverz']">
		<xsl:param name="mode"/>
		<xsl:apply-templates select="$styles/entry[@name = 'universalliste'][@variant='glossary']/entrystructure[@format='latexpdf']">
				<xsl:with-param name="contextnode" select="current()"/>
				<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>
	<!-- ==================================================================== -->

	<xsl:template match="listlabel">
		<xsl:param name="mode"/>
		<xsl:param name="contextnode"/>
		\item[{<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/><xsl:with-param name="mode" select="$mode"/></xsl:apply-templates>}]<xsl:if test="@newline='true'">\mbox{}\\*<xsl:if test="@spacing!=''">[<xsl:value-of select="@spacing"/>]</xsl:if></xsl:if>
	</xsl:template>
	
	
	<xsl:template match="erlaeuterung"><xsl:param name="mode"/><xsl:apply-templates><xsl:with-param name="mode" select="$mode"/>
</xsl:apply-templates></xsl:template>
	
	<xsl:template match="stichwort"><xsl:param name="mode"/><xsl:apply-templates><xsl:with-param name="mode" select="$mode"/>
</xsl:apply-templates></xsl:template>
	
	
	<!-- ==================================================================== -->
	<!--                                      Literature                                                                                     -->
	<!-- ==================================================================== -->
	
	<!--xsl:template match="literaturliste">
		<xsl:call-template name="genericlist">
			<xsl:with-param name="style" select="$styles/entry[@name = name(current())][@variant='endofdocument']"/>
		</xsl:call-template>
	</xsl:template-->
	
	<xsl:template match="literaturliste[zwischentitel!='']">
		<xsl:param name="mode"/>
		<xsl:choose>
			<xsl:when test="$config/config/bibtexml/@active='true'">
				\bibliographystyle{<xsl:value-of select="$config/config/bibtexml/@bibliographystyle"/>}
				\bibliography{<xsl:for-each select="$config/config/bibtexml/bibfile"><xsl:value-of select="normalize-space(@fileref)"/><xsl:if test="position()!=last()">,</xsl:if></xsl:for-each>}
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="genericlist">
					<xsl:with-param name="style" select="$styles/entry[@name = name(current())][@variant='insidedocument']"/>
					<xsl:with-param name="mode" select="$mode"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="literaturliste[zwischentitel='' or not(zwischentitel)]">
		<xsl:param name="mode"/>
		<xsl:call-template name="genericlist">
			<xsl:with-param name="style" select="$styles/entry[@name = name(current())][@variant='insidedocumentnotitle']"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="lit-imtext">
		<xsl:param name="mode"/>
		<xsl:call-template name="genericbox">
			<xsl:with-param name="style" select="$styles/entry[@name = name(current())]"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:call-template>
	</xsl:template>
	
	
	<!--xsl:template match="liteintrag[parent::literaturliste]">
		<xsl:apply-templates select="$styles/entry[@name = 'literaturliste'][@variant='endofdocument']/entrystructure[@format='latexpdf']">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates>
	</xsl:template-->

	<xsl:template match="liteintrag[parent::literaturliste[zwischentitel!='']]">
		<xsl:param name="mode"/>
		<xsl:apply-templates select="$styles/entry[@name = 'literaturliste'][@variant='insidedocument']/entrystructure[@format='latexpdf']">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>

	
	<xsl:template match="liteintrag[parent::literaturliste[zwischentitel='' or not(zwischentitel)]]">
		<xsl:param name="mode"/>
		<xsl:apply-templates select="$styles/entry[@name = 'literaturliste'][@variant='insidedocumentnotitle']/entrystructure[@format='latexpdf']">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>

	
	<xsl:template match="litkennung"><xsl:param name="mode"/><xsl:apply-templates><xsl:with-param name="mode" select="$mode"/>
</xsl:apply-templates></xsl:template>
	
	
	
	<!-- ==================================================================== -->
	<xsl:template name="list"><xsl:param name="style"/><xsl:param name="mode"/><xsl:if test="$style/margin-top!=''">\vspace{<xsl:value-of select="$style/margin-top"/>}</xsl:if>
		\begin{list}{}{<xsl:call-template name="get.font-family"><xsl:with-param name="style" select="$style/font-family"/></xsl:call-template>
		<xsl:call-template name="get.font-style"><xsl:with-param name="style" select="$style/font-style"/></xsl:call-template>
		<xsl:call-template name="get.font-variant"><xsl:with-param name="style" select="$style/font-variant"/></xsl:call-template>
		<xsl:call-template name="get.font-weight"><xsl:with-param name="style" select="$style/font-weight"/></xsl:call-template>
		<xsl:call-template name="get.font-size"><xsl:with-param name="style" select="$style/font-size"/></xsl:call-template>
		<xsl:if test="$style/margin-left!=''">\setlength{\leftmargin}{<xsl:value-of select="$style/margin-left"/>}</xsl:if>
		<xsl:if test="$style/margin-right!=''">\setlength{\rightmargin}{<xsl:value-of select="$style/margin-right"/>}</xsl:if>
		<xsl:if test="$style/labelwidth!=''">\setlength{\labelwidth}{<xsl:value-of select="$style/labelwidth"/>}</xsl:if>
		<xsl:if test="$style/labelsep!=''">\setlength{\labelsep}{<xsl:value-of select="$style/labelsep"/>}</xsl:if>
		<xsl:if test="$style/itemindent!=''">\setlength{\itemindent}{<xsl:value-of select="$style/itemindent"/>}</xsl:if>
		<xsl:if test="$style/itemsep!=''">\setlength{\itemsep}{<xsl:value-of select="$style/itemsep"/>}</xsl:if>
		<xsl:if test="$style/parsep!=''">\setlength{\parsep}{<xsl:value-of select="$style/parsep"/>}</xsl:if><!-- \parsep does not seem to work, therefore its added manually (absatz.xsl) -->
		<xsl:if test="$style/topsep!=''">\setlength{\topsep}{<xsl:value-of select="$style/topsep"/>}</xsl:if>
		\setlength{\partopsep}{0ex}
		<xsl:if test="position()=1">\setlength{\topsep}{0pt}\vspace{-\baselineskip}</xsl:if><!-- Lists without preceding elements should not start with an empty line -->
		<xsl:if test="$style/listparindent!=''">\setlength{\listparindent}{<xsl:value-of select="$style/listparindent"/>}</xsl:if>}<xsl:choose>
			<xsl:when test="$style/text-align='left'">\raggedright</xsl:when>
			<xsl:when test="$style/text-align='right'">\raggedleft</xsl:when>
			<xsl:when test="$style/text-align='center'">\centering</xsl:when>
		</xsl:choose>
		<xsl:apply-templates><xsl:with-param name="mode" select="$mode"/></xsl:apply-templates>
		\vspace{\parskip}\end{list}\vspace{-\parskip}<xsl:if test="$style/margin-bottom!=''">\vspace{<xsl:value-of select="$style/margin-bottom"/>}</xsl:if><!--xsl:choose>
			< Insert extra space at end of list if last paragraph is 'absatz' and preceding paragraph is not absatz>
			<xsl:when test="name(descendant::*[self::absatz or self::absatz-klein or self::absatz-mini or self::absatz-ohne][position()=last()]) = 'absatz' and name(preceding-sibling::*[not(self::marginalie)][1])!='absatz'">\vspace{<xsl:value-of select="$style/partopsep"/>}</xsl:when>
			< Delete space at end of list if last paragraph is not 'absatz' and preceding paragraph is absatz >
			<xsl:when test="name(descendant::*[self::absatz or self::absatz-klein or self::absatz-mini or self::absatz-ohne][position()=last()]) != 'absatz' and name(preceding-sibling::*[not(self::marginalie)][1])='absatz'">\vspace{-<xsl:value-of select="$style/partopsep"/>}</xsl:when>
		</xsl:choose-->
	</xsl:template>
	
	<!-- ==================================================================== -->

	<xsl:template name="genericlist"><xsl:param name="style"/><xsl:param name="mode"/><xsl:if test="$style/margin-top!=''">\vspace{<xsl:value-of select="$style/margin-top"/>}</xsl:if>
		<xsl:if test="$style/text-before/node()"><xsl:apply-templates select="$style/text-before"><xsl:with-param name="contextnode" select="current()"/>
</xsl:apply-templates></xsl:if>
		\begin{list}{}{<xsl:call-template name="get.font-family"><xsl:with-param name="style" select="$style/font-family"/></xsl:call-template>
		<xsl:call-template name="get.font-style"><xsl:with-param name="style" select="$style/font-style"/></xsl:call-template>
		<xsl:call-template name="get.font-variant"><xsl:with-param name="style" select="$style/font-variant"/></xsl:call-template>
		<xsl:call-template name="get.font-weight"><xsl:with-param name="style" select="$style/font-weight"/></xsl:call-template>
		<xsl:call-template name="get.font-size"><xsl:with-param name="style" select="$style/font-size"/></xsl:call-template>
		<xsl:if test="$style/margin-left!=''">\setlength{\leftmargin}{<xsl:value-of select="$style/margin-left"/>}</xsl:if>
		<xsl:if test="$style/margin-right!=''">\setlength{\rightmargin}{<xsl:value-of select="$style/margin-right"/>}</xsl:if>
		<xsl:if test="$style/labelwidth!=''">\setlength{\labelwidth}{<xsl:value-of select="$style/labelwidth"/>}</xsl:if>
		<xsl:if test="$style/labelsep!=''">\setlength{\labelsep}{<xsl:value-of select="$style/labelsep"/>}</xsl:if>
		<xsl:if test="$style/itemindent!=''">\setlength{\itemindent}{<xsl:value-of select="$style/itemindent"/>}</xsl:if>
		<xsl:if test="$style/itemsep!=''">\setlength{\itemsep}{<xsl:value-of select="$style/itemsep"/>}</xsl:if>
		<xsl:if test="$style/parsep!=''">\setlength{\parsep}{<xsl:value-of select="$style/parsep"/>}</xsl:if><!-- \parsep does not seem to work, therefore its added manually (absatz.xsl) -->
		<xsl:if test="$style/topsep!=''">\setlength{\topsep}{<xsl:value-of select="$style/topsep"/>}</xsl:if>
		<xsl:if test="position()=1">\setlength{\topsep}{0pt}\vspace{-\baselineskip}</xsl:if><!-- Lists without preceding elements should not start with an empty line -->
		<xsl:if test="$style/listparindent!=''">\setlength{\listparindent}{<xsl:value-of select="$style/listparindent"/>}</xsl:if>}<xsl:choose>
			<xsl:when test="$style/text-align='left'">\raggedright</xsl:when>
			<xsl:when test="$style/text-align='right'">\raggedleft</xsl:when>
			<xsl:when test="$style/text-align='center'">\centering</xsl:when>
		</xsl:choose>
		<xsl:apply-templates select="*[not(self::zwischentitel)]"><xsl:with-param name="mode" select="$mode"/></xsl:apply-templates>
		\vspace{\parskip}\end{list}\vspace{-\parskip}<xsl:if test="$style/margin-bottom!=''">\vspace{<xsl:value-of select="$style/margin-bottom"/>}</xsl:if><!--xsl:choose>
			< Insert extra space at end of list if last paragraph is 'absatz' and preceding paragraph is not absatz >
			<xsl:when test="name(descendant::*[self::absatz or self::absatz-klein or self::absatz-mini or self::absatz-ohne][position()=last()]) = 'absatz' and name(preceding-sibling::*[not(self::marginalie)][1])!='absatz'">\vspace{<xsl:value-of select="$style/partopsep"/>}</xsl:when>
			< Delete space at end of list if last paragraph is not 'absatz' and preceding paragraph is absatz >
			<xsl:when test="name(descendant::*[self::absatz or self::absatz-klein or self::absatz-mini or self::absatz-ohne][position()=last()]) != 'absatz' and name(preceding-sibling::*[not(self::marginalie)][1])='absatz'">\vspace{-<xsl:value-of select="$style/partopsep"/>}</xsl:when>
		</xsl:choose><xsl:if test="$style/margin-bottom!=''">\vspace{<xsl:value-of select="$style/margin-bottom"/>}</xsl:if-->
	</xsl:template>
	
</xsl:stylesheet> 
 