<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<!-- ********************************************************************
     $Id: listen.xsl,v 1.3 2007/01/11 13:38:26 gebhard Exp $
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
     
     Description:
     This file contains all functions for lists
    
     Use in Pass 4
	
	*******************************************************************
	Beschreibung:
	Diese Datei enthält sämtliche Designvorschriften für Elemente der Listenumgebungen.
     ********************************************************************
-->

<!-- ################################### Listen und ihre Subelemente ########################################### -->

	<xsl:template match="aufzaehlungsliste">
	<div class="listenbox">
		<ul>
			<xsl:attribute name="class">
				<xsl:value-of select="@stiltyp"/>
			</xsl:attribute>
			<xsl:apply-templates/>
		</ul>
	</div>
	</xsl:template>

	<xsl:template match="aufzaehlungsliste" mode="marginalie">
	<span>
		<xsl:attribute name="class">
			<xsl:value-of select="@stiltyp"/>
		</xsl:attribute>
		<span>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates mode="marginalie"/>
		</span>
	</span>
	</xsl:template>
	
		
	<xsl:template match="begriffsliste">
		<dl>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates/>
		</dl>
	</xsl:template>
	
	<xsl:template match="literaturliste">
		<div>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates/>
		</div>
	</xsl:template>

	<xsl:template match="nummerierteliste" mode="marginalie">
	<span>
		<xsl:attribute name="class">
			<xsl:value-of select="@stiltyp"/>
		</xsl:attribute>
		<span>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates mode="magnuliste"/>
		</span>
	</span>
	</xsl:template>
	
	<xsl:template match="eintrag" mode="magnuliste">
		<span>
			<xsl:call-template name="standardattribute"/>
			<xsl:attribute name="class">
				<xsl:text>listeintrag</xsl:text>
			</xsl:attribute>
			<span class="zaehler">
				<xsl:choose>
					<xsl:when test="$styles/entry[@name = 'nummerierteliste'][@variant='marginalie']/label[@type=current()/parent::nummerierteliste/@stiltyp]">
						<xsl:apply-templates select="$styles/entry[@name = 'nummerierteliste'][@variant='marginalie']/label[@type=current()/parent::nummerierteliste/@stiltyp]">
							<xsl:with-param name="contextnode" select="current()"/>
						</xsl:apply-templates>
					</xsl:when>
					<xsl:otherwise>
						<xsl:apply-templates select="$styles/entry[@name = 'nummerierteliste'][@variant='marginalie']/label[not(@type)][1]">
							<xsl:with-param name="contextnode" select="current()"/>
						</xsl:apply-templates>
					</xsl:otherwise>
				</xsl:choose>
				<!--xsl:apply-templates select="$styles/entry[@name='nummerierteliste']/label">
					<xsl:with-param name="contextnode" select="current()"/>
				</xsl:apply-templates-->
			</span>
				<xsl:apply-templates mode="marginalie"/>
		</span>
	</xsl:template>

	
	<xsl:template match="nummerierteliste">
	<div>
		<xsl:call-template name="standardattribute"/>
		<xsl:apply-templates mode="nuliste"/>
	</div>
	</xsl:template>


	<xsl:template match="eintrag" mode="nuliste">
		<div>
			<xsl:call-template name="standardattribute"/>
			<xsl:attribute name="class">
				<xsl:text>listeintrag</xsl:text>
			</xsl:attribute>
			<p class="zaehler">
				<xsl:choose>
					<xsl:when test="$styles/entry[@name = 'nummerierteliste'][not(@variant)]/label[@type=current()/parent::nummerierteliste/@stiltyp]">
						<xsl:apply-templates select="$styles/entry[@name = 'nummerierteliste'][not(@variant)]/label[@type=current()/parent::nummerierteliste/@stiltyp]">
							<xsl:with-param name="contextnode" select="current()"/>
						</xsl:apply-templates>
					</xsl:when>
					<xsl:otherwise>
						<xsl:apply-templates select="$styles/entry[@name = 'nummerierteliste'][not(@variant)]/label[not(@type)][1]">
							<xsl:with-param name="contextnode" select="current()"/>
						</xsl:apply-templates>
					</xsl:otherwise>
				</xsl:choose>
				<!--xsl:apply-templates select="$styles/entry[@name='nummerierteliste']/label">
					<xsl:with-param name="contextnode" select="current()"/>
				</xsl:apply-templates-->
			</p>
				<xsl:apply-templates/>
		</div>
	</xsl:template>
	
	<xsl:template match="universalliste">
	<div>
		<xsl:call-template name="standardattribute"/>
		<xsl:attribute name="class">
			<xsl:value-of select="@typ"/>
		</xsl:attribute>
		<xsl:apply-templates/>
	</div>
	</xsl:template>

<!-- ## -->

	<xsl:template match="begriffseintrag">
		<div>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates/>
		</div>
	</xsl:template>
	
	<xsl:template match="eintrag">
		<li>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates/>
		</li>
	</xsl:template>
	
	<xsl:template match="eintrag" mode="marginalie">
		<span>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates mode="marginalie"/>
		</span>
	</xsl:template>
	
	<xsl:template match="*" mode="marginalie">
	<span>
	   <xsl:attribute name="class">
	       <xsl:value-of select="name(.)"/>
	   </xsl:attribute>
	   <xsl:call-template name="standardattribute"/>
	   <xsl:apply-templates mode="marginalie"/>
	</span>
	</xsl:template>

<!-- Alt
	<xsl:template match="liteintrag">
	<div>
		<xsl:call-template name="standardattribute"/>
			<p class="litnummer"><xsl:value-of select="@number"/></p>
			<p class="litkennung"><xsl:apply-templates select="litkennung"/></p>
			<xsl:apply-templates select="*[not(self::litkennung)]"/>
	</div>
	</xsl:template>
	
	<xsl:template match="lit-imtext">
	<div>
		<xsl:call-template name="standardattribute"/>
		<xsl:apply-templates/>
	</div>
	</xsl:template> -->

<!--	<xsl:template match="litkennung">
		<xsl:apply-templates/>
	</xsl:template> -->
	
<!-- NEU -->

	<!-- ==================================================================== -->
	<!--                                      Literature                                                                                     -->
	<!-- ==================================================================== -->
	
	<xsl:template match="literaturliste[zwischentitel!='']">
		<xsl:param name="mode"/>
		<xsl:apply-templates select="zwischentitel"/>
		<xsl:call-template name="genericlist">
			<xsl:with-param name="style" select="$styles/entry[@name = name(current())][@variant='insidedocument']"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:call-template>
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
		<div>
			<xsl:call-template name="standardattribute"/>
			<xsl:call-template name="genericbox">
				<xsl:with-param name="style" select="$styles/entry[@name = name(current())]"/>
				<xsl:with-param name="mode" select="$mode"/>
			</xsl:call-template>
		</div>
	</xsl:template>
	
	<xsl:template match="liteintrag[parent::literaturliste[zwischentitel!='']]">
		<xsl:param name="mode"/>
	<div>
		<xsl:call-template name="standardattribute"/>
		<xsl:apply-templates select="$styles/entry[@name = 'literaturliste'][@variant='insidedocument']/entrystructure">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</div>
	</xsl:template>

	
	<xsl:template match="liteintrag[parent::literaturliste[zwischentitel='' or not(zwischentitel)]]">
		<xsl:param name="mode"/>
	<div>
		<xsl:call-template name="standardattribute"/>
		<xsl:apply-templates select="$styles/entry[@name = 'literaturliste'][@variant='insidedocumentnotitle']/entrystructure">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</div>
	</xsl:template>

	
	<xsl:template match="litkennung">
	<xsl:param name="mode"/>
		<xsl:apply-templates><xsl:with-param name="mode" select="$mode"/></xsl:apply-templates>
	</xsl:template>

	<xsl:template name="genericlist">
		<xsl:param name="style"/>
		<xsl:param name="mode"/>
		<xsl:apply-templates select="*[not(self::zwischentitel)]">
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="listlabel">
		<xsl:param name="contextnode"/>
		<p class="litkennung">
		<xsl:apply-templates><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>
		</p>
	</xsl:template>

</xsl:stylesheet>
