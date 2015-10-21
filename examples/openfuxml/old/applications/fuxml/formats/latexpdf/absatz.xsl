<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<!-- ********************************************************************
		$Id: absatz.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
	
	
	<xsl:variable name="parasettings" select="$config/config/paragraphsettings"/>

	
	<xsl:template match="absatz|absatz-klein|absatz-mini|absatz-ohne"><xsl:param name="mode"/><xsl:apply-templates><xsl:with-param name="mode" select="$mode"/></xsl:apply-templates><xsl:if test="not(parent::marginalie) and not(parent::eintrag) and not(parent::entry)">\pagebreak[1]</xsl:if><xsl:call-template name="get.spacing"/></xsl:template>

	
	<!--xsl:template match="absatz"><xsl:apply-templates/><xsl:if test="not(parent::marginalie)">\pagebreak[1]</xsl:if>\setlength{\parskip}{<xsl:value-of select="$parasettings/paranormal"/>}<xsl:text xml:space="preserve">&#xD;&#xA;&#xD;&#xA;</xsl:text></xsl:template>
	
	<xsl:template match="absatz-ohne"><xsl:apply-templates/><xsl:if test="not(parent::marginalie)">\pagebreak[1]</xsl:if>\setlength{\parskip}{<xsl:value-of select="$parasettings/parawithout"/>}<xsl:text xml:space="preserve">&#xD;&#xA;&#xD;&#xA;</xsl:text></xsl:template>

	<xsl:template match="absatz-klein"><xsl:apply-templates/><xsl:if test="not(parent::marginalie)">\pagebreak[1]</xsl:if>\setlength{\parskip}{<xsl:value-of select="$parasettings/parasmall"/>}<xsl:text xml:space="preserve">&#xD;&#xA;&#xD;&#xA;</xsl:text></xsl:template>
	
	<xsl:template match="absatz-mini"><xsl:apply-templates/><xsl:if test="not(parent::marginalie)">\pagebreak[1]</xsl:if>\setlength{\parskip}{<xsl:value-of select="$parasettings/paramini"/>}<xsl:text xml:space="preserve">&#xD;&#xA;&#xD;&#xA;</xsl:text></xsl:template-->

	

	<xsl:template match="absatz[parent::entry[@namest] and following-sibling::*]"><xsl:apply-templates/>\newline{}</xsl:template>
	
	<xsl:template match="absatz-klein[parent::entry[@namest] and following-sibling::*]"><xsl:apply-templates/>\newline{}</xsl:template>

	<xsl:template match="absatz-mini[parent::entry[@namest] and following-sibling::*]"><xsl:apply-templates/>\newline{}</xsl:template>

	<xsl:template match="absatz-ohne[parent::entry[@namest] and following-sibling::*]"><xsl:apply-templates/>\newline{}</xsl:template>

	
	<!--xsl:template match="[parent::eintrag]"><xsl:apply-templates/>\setlength{\parskip}{<xsl:value-of select="$parasettings/parawithout"/>}<xsl:text xml:space="preserve">&#xD;&#xA;&#xD;&#xA;</xsl:text></xsl:template>

	<xsl:template match="absatz-klein[parent::eintrag]"><xsl:apply-templates/>\setlength{\parskip}{<xsl:value-of select="$parasettings/parasmall"/>}<xsl:text xml:space="preserve">&#xD;&#xA;&#xD;&#xA;</xsl:text></xsl:template>
	
	<xsl:template match="absatz-mini[parent::eintrag]"><xsl:apply-templates/>\setlength{\parskip}{<xsl:value-of select="$parasettings/paramini"/>}<xsl:text xml:space="preserve">&#xD;&#xA;&#xD;&#xA;</xsl:text></xsl:template-->

	
	
	<xsl:template  match="absatz[parent::objekttitel and following-sibling::*]" ><xsl:apply-templates/>\\</xsl:template>
	
	<xsl:template match="absatz-klein[parent::objekttitel and following-sibling::*]"><xsl:apply-templates/>\\</xsl:template>

	<xsl:template match="absatz-mini[parent::objekttitel and following-sibling::*]"><xsl:apply-templates/>\\</xsl:template>

	<xsl:template match="absatz-ohne[parent::objekttitel and following-sibling::*]"><xsl:apply-templates/>\\</xsl:template>



	
	
	<xsl:template  match="absatz[(parent::entry or parent::fussnote or parent::marginalie or parent::objekttitel) and not(following-sibling::*)]"><xsl:apply-templates/></xsl:template>
	
	<xsl:template match="absatz-klein[(parent::entry or parent::fussnote or parent::marginalie or parent::objekttitel) and not(following-sibling::*)]"><xsl:apply-templates/></xsl:template>

	<xsl:template match="absatz-mini[(parent::entry or parent::fussnote or parent::marginalie or parent::objekttitel) and not(following-sibling::*)]"><xsl:apply-templates/></xsl:template>

	<xsl:template match="absatz-ohne[(parent::entry or parent::fussnote or parent::marginalie or parent::objekttitel) and  not(following-sibling::*)]"><xsl:apply-templates/></xsl:template>
	

	<xsl:template  match="absatz[parent::objekttitel and following-sibling::*]" mode="toc"><xsl:apply-templates mode="toc"/>\\</xsl:template>
	
	<xsl:template match="absatz-klein[parent::objekttitel and following-sibling::*]" mode="toc"><xsl:apply-templates mode="toc"/>\\</xsl:template>

	<xsl:template match="absatz-mini[parent::objekttitel and following-sibling::*]" mode="toc"><xsl:apply-templates mode="toc"/>\\</xsl:template>

	<xsl:template match="absatz-ohne[parent::objekttitel and following-sibling::*]" mode="toc"><xsl:apply-templates mode="toc"/>\\</xsl:template>



	
	
	<xsl:template  match="absatz[(parent::entry or parent::fussnote or parent::marginalie or parent::objekttitel) and not(following-sibling::*)]" mode="toc"><xsl:apply-templates mode="toc"/></xsl:template>
	
	<xsl:template match="absatz-klein[(parent::entry or parent::fussnote or parent::marginalie or parent::objekttitel) and not(following-sibling::*)]" mode="toc"><xsl:apply-templates mode="toc"/></xsl:template>

	<xsl:template match="absatz-mini[(parent::entry or parent::fussnote or parent::marginalie or parent::objekttitel) and not(following-sibling::*)]" mode="toc"><xsl:apply-templates mode="toc"/></xsl:template>

	<xsl:template match="absatz-ohne[(parent::entry or parent::fussnote or parent::marginalie or parent::objekttitel) and  not(following-sibling::*)]" mode="toc"><xsl:apply-templates mode="toc"/></xsl:template>



	<!-- When following element is a list do not create empty line -->
	<!--xsl:template match="absatz-klein[name(following-sibling::*[not(self::marginalie)][1])='nummerierteliste' or name(following-sibling::*[not(self::marginalie)][1])='aufzaehlungsliste' or name(following-sibling::*[not(self::marginalie)][1])='universalliste']" priority="1"><xsl:apply-templates/>\setlength{\parskip}{<xsl:value-of select="$parasettings/parasmall"/>}</xsl:template>
	<xsl:template match="absatz-mini[name(following-sibling::*[not(self::marginalie)][1])='nummerierteliste' or name(following-sibling::*[not(self::marginalie)][1])='aufzaehlungsliste' or name(following-sibling::*[not(self::marginalie)][1])='universalliste']" priority="1"><xsl:apply-templates/>\setlength{\parskip}{<xsl:value-of select="$parasettings/paramini"/>}</xsl:template>
	<xsl:template match="absatz-ohne[name(following-sibling::*[not(self::marginalie)][1])='nummerierteliste' or name(following-sibling::*[not(self::marginalie)][1])='aufzaehlungsliste' or name(following-sibling::*[not(self::marginalie)][1])='universalliste']" priority="1"><xsl:apply-templates/>\setlength{\parskip}{<xsl:value-of select="$parasettings/without"/>}</xsl:template-->

<xsl:template name="get.spacing">\setlength{\parskip}{<xsl:choose>
	<xsl:when test="self::absatz"><xsl:value-of select="$parasettings/paranormal"/></xsl:when>
	<xsl:when test="self::absatz-klein"><xsl:value-of select="$parasettings/parasmall"/></xsl:when>
	<xsl:when test="self::absatz-mini"><xsl:value-of select="$parasettings/paramini"/></xsl:when>
	<xsl:when test="self::absatz-ohne"><xsl:value-of select="$parasettings/parawithout"/></xsl:when></xsl:choose><!--xsl:if test="ancestor::eintrag|ancestor::universaleintrag">+\parsep</xsl:if-->}<xsl:text xml:space="preserve">&#xD;&#xA;&#xD;&#xA;</xsl:text></xsl:template>
	<!-- \parsep does not seem to work, therefore its added manually -->

	
</xsl:stylesheet>
