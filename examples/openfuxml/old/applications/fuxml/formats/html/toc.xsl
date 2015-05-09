<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:saxon="http://saxon.sf.net/" xmlns:xs="http://www.w3.org/2001/XMLSchema">
	<!-- ********************************************************************
		$Id: toc.xsl,v 1.6 2007/03/27 14:50:51 gebhard Exp $
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
		This file contains the functions for tocs
		
		Use in Pass one and 4:
		*******************************************************************
		Beschreibung:
		Die IDs wurden bereits in der Vorphase "demux1" erzeugt und in der "demux2" - Phase in <file> Elementen verpackt.		
		********************************************************************
	-->
	<xsl:key name="kurseinheit" match="//file[not(@integrate='no')]" use="@kurseinheit"/>
	<xsl:key name="inhaltsvz" match="//abschnitt/titel | //literaturliste/zwischentitel" use="not(@integrate='no')"/>
	<xsl:template match="inhaltsverzeichnis">
		<xsl:param name="contextnode"/>
		<xsl:variable name="Pfad">
			<xsl:call-template name="CSSPfad"/>
		</xsl:variable>
		<xsl:variable name="kurseinheit">
			<xsl:value-of select="parent::*/@kurseinheit"/>
		</xsl:variable>
		<xsl:variable name="titel-id">
			<xsl:value-of select="preceding::abschnitt[1]/titel/@id"/>
		</xsl:variable>
		<xsl:variable name="kurs" select="//abschnitt/titel[not(ancestor::file/@integrate='no')][not(@id=$titel-id)][ancestor::file] | //literaturliste[not(ancestor::file/@integrate='no')][ancestor::file]/zwischentitel"/>
		<xsl:variable name="kursmitdiv">
			<xsl:for-each select="$kurs">
				<xsl:apply-templates select="$kurs[ancestor::file/@kurseinheit[not(.=preceding::file/@kurseinheit)]]"/>
			</xsl:for-each>
		</xsl:variable>
		<div class="inhaltsverz">
			<p class="zwischentitel">
				<xsl:value-of select="key('id_key',$titel-id)[1]"/>
			</p>
			<!-- Das Inhaltsverzeichnis wird nun im letzten Schritt zusammen gebaut,
	damit die Sonderverzeichnisse, wie Tabellenverzeichnis, etc., mit enthalten sind -->
			<xsl:choose>
				<xsl:when test="$kurseinheit='kein'">
					<!-- XSLT2.0 Funktion! Sie gruppiert das Inhaltsverzeichnis nach Kurs und Kurseinheiten -->
					<xsl:for-each-group select="$kurs" group-adjacent="ancestor::file/@kurseinheit">
						<div>
							<xsl:attribute name="class"><xsl:text>inhaltke</xsl:text><xsl:value-of select="ancestor::file/@kurseinheit"/></xsl:attribute>
							<xsl:for-each select="current-group()">
								<xsl:apply-templates select="." mode="TOC">
									<xsl:with-param name="Pfad" select="$Pfad"/>
								</xsl:apply-templates>
							</xsl:for-each>
						</div>
					</xsl:for-each-group>
					<xsl:value-of select="$kursmitdiv"/>
				</xsl:when>
				<xsl:when test="xs:integer($kurseinheit) gt 0">
					<xsl:apply-templates select="key('kurseinheit',$kurseinheit)/descendant::abschnitt/titel[not(@id=$titel-id)]|key('kurseinheit',$kurseinheit)/descendant::literaturliste[not(ancestor::file/@integrate='no')]/zwischentitel" mode="TOC">
						<xsl:with-param name="Pfad" select="$Pfad"/>
					</xsl:apply-templates>
				</xsl:when>
			</xsl:choose>
			<!--			<xsl:apply-templates mode="inhvz">
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates> -->
		</div>
	</xsl:template>
	<xsl:template name="kursdiv">
		<xsl:param name="content"/>
		<xsl:param name="Pfad"/>
		<xsl:choose>
			<xsl:when test="$content/ancestor::file/@kurseinheit[.=preceding::file[1]/@kurseinheit]">
				<xsl:apply-templates select="." mode="TOC">
					<xsl:with-param name="Pfad" select="$Pfad"/>
				</xsl:apply-templates>
				<xsl:call-template name="kursdiv">
					<xsl:with-param name="content" select="$content"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
	</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="id" mode="inhvz">
		<xsl:param name="Pfad"/>
		<xsl:variable name="node" select="key('id_key',.)[1]"/>
		<xsl:apply-templates select="$node" mode="TOC">
			<xsl:with-param name="contextnode" select="$node"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template name="TOC">
		<xsl:param name="contextnode"/>
		<xsl:param name="Pfad"/>
		<xsl:apply-templates select="$config/config/toc/toctitle">
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>
		<xsl:apply-templates select="descendant::abschnitt/titel" mode="TOC">
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="abbildungsverzeichnis">
		<xsl:param name="contextnode"/>
		<div class="abbildungsverz">
			<p class="zwischentitel2">
				<xsl:value-of select="preceding::abschnitt[1]/titel"/>
			</p>
			<xsl:apply-templates mode="abbvz"/>
		</div>
	</xsl:template>
	<xsl:template match="id" mode="abbvz">
		<xsl:variable name="Pfad">
			<xsl:apply-templates select="key('id_key',.)" mode="getpage">
				<xsl:with-param name="Pfad">
					<xsl:call-template name="CSSPfad"/>
				</xsl:with-param>
			</xsl:apply-templates>
			<xsl:text>#</xsl:text>
			<xsl:value-of select="key('id_key',.)/parent::*/@id"/>
		</xsl:variable>
		<xsl:variable name="link">
			<xsl:variable name="CSSPfad">
				<xsl:call-template name="CSSPfad"/>
			</xsl:variable>
			<xsl:apply-templates select="key('id_key',.)/ancestor::file[1]/@directory"/>
			<xsl:call-template name="get.medialink">
				<xsl:with-param name="contextnode" select="key('id_key',.)"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="objekt">
			<xsl:choose>
				<xsl:when test="key('id_key',.)/preceding-sibling::bild or key('id_key',.)/preceding-sibling::grafik or key('id_key',.)/following-sibling::bild or key('id_key',.)/following-sibling::grafik">
					<xsl:apply-templates select="$styles/entry[@name='medienobjekt'][@variant='figure']/tocline/line">
						<xsl:with-param name="contextnode" select="key('id_key',.)/parent::node()"/>
					</xsl:apply-templates>
				</xsl:when>
				<xsl:when test="key('id_key',.)/preceding-sibling::animation and key('id_key',.)/preceding-sibling::textobjekt or key('id_key',.)/following-sibling::animation and key('id_key',.)/following-sibling::textobjekt">
					<xsl:apply-templates select="$styles/entry[@name='medienobjekt'][@variant='animation-textobjekt']/tocline/line">
						<xsl:with-param name="contextnode" select="key('id_key',.)/parent::node()"/>
					</xsl:apply-templates>
				</xsl:when>
				<xsl:when test="key('id_key',.)/preceding-sibling::textobjekt or key('id_key',.)/following-sibling::textobjekt">
					<xsl:apply-templates select="$styles/entry[@name='medienobjekt'][@variant='xml-textobjekt']/tocline/line">
						<xsl:with-param name="contextnode" select="key('id_key',.)/parent::node()"/>
					</xsl:apply-templates>
				</xsl:when>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="nummer">
			<xsl:choose>
				<xsl:when test="key('id_key',.)/preceding-sibling::bild or key('id_key',.)/preceding-sibling::grafik or key('id_key',.)/following-sibling::bild or key('id_key',.)/following-sibling::grafik">
					<xsl:apply-templates select="$styles/entry[@name='medienobjekt'][@variant='figure']/tocline/number">
						<xsl:with-param name="contextnode" select="key('id_key',.)/parent::node()"/>
					</xsl:apply-templates>
				</xsl:when>
				<xsl:when test="key('id_key',.)/preceding-sibling::animation and key('id_key',.)/preceding-sibling::textobjekt or key('id_key',.)/following-sibling::animation and key('id_key',.)/following-sibling::textobjekt">
					<xsl:apply-templates select="$styles/entry[@name='medienobjekt'][@variant='animation-textobjekt']/tocline/number">
						<xsl:with-param name="contextnode" select="key('id_key',.)/parent::node()"/>
					</xsl:apply-templates>
				</xsl:when>
				<xsl:when test="key('id_key',.)/preceding-sibling::textobjekt or key('id_key',.)/following-sibling::textobjekt">
					<xsl:apply-templates select="$styles/entry[@name='medienobjekt'][@variant='xml-textobjekt']/tocline/number">
						<xsl:with-param name="contextnode" select="key('id_key',.)/parent::node()"/>
					</xsl:apply-templates>
				</xsl:when>
			</xsl:choose>
		</xsl:variable>
		<xsl:if test="string-length(key('id_key',.)[1]/parent::*[1]/@number) &gt; 0">
			<!-- -->
			<a href="{$Pfad}" class="objektnum">
				<xsl:value-of select="$nummer"/>
			</a>
		</xsl:if>
		<xsl:if test="string-length($objekt) &gt; 1">
			<a href="{$Pfad}" class="objekt">
				<xsl:value-of select="$objekt"/>
			</a>
		</xsl:if>
	</xsl:template>
	<xsl:template match="tabellenverzeichnis">
		<xsl:param name="contextnode"/>
		<div class="tabellenverz">
			<p class="zwischentitel2">
				<xsl:value-of select="preceding::abschnitt[1]/titel"/>
			</p>
			<xsl:apply-templates mode="tabvz"/>
		</div>
	</xsl:template>
	<xsl:template match="id" mode="tabvz">
		<xsl:variable name="Pfad">
			<xsl:apply-templates select="key('id_key',.)" mode="getpage">
				<xsl:with-param name="Pfad">
					<xsl:call-template name="CSSPfad"/>
				</xsl:with-param>
			</xsl:apply-templates>
			<xsl:text>#</xsl:text>
			<xsl:value-of select="."/>
		</xsl:variable>
		<xsl:variable name="link">
			<xsl:variable name="CSSPfad">
				<xsl:call-template name="CSSPfad"/>
			</xsl:variable>
			<xsl:apply-templates select="key('id_key',.)/ancestor::file[1]/@directory"/>
			<xsl:call-template name="get.medialink">
				<xsl:with-param name="contextnode" select="key('id_key',.)"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="mode">
			<xsl:value-of select="name(key('id_key',.)[1])"/>
		</xsl:variable>
		<xsl:variable name="objekt">
			<xsl:apply-templates select="$styles/entry[@name = $mode]/tocline/line">
				<xsl:with-param name="contextnode" select="key('id_key',.)"/>
			</xsl:apply-templates>
		</xsl:variable>
		<xsl:variable name="nummer">
			<xsl:apply-templates select="$styles/entry[@name = $mode]/tocline/number">
				<xsl:with-param name="contextnode" select="key('id_key',.)"/>
			</xsl:apply-templates>
		</xsl:variable>
		<xsl:if test="string-length(key('id_key',.)[1]/@number) &gt; 0">
			<a href="{$Pfad}" class="objektnum">
				<xsl:value-of select="$nummer"/>
			</a>
		</xsl:if>
		<xsl:if test="string-length($objekt) &gt; 1">
			<a href="{$Pfad}" class="objekt">
				<xsl:value-of select="$objekt"/>
			</a>
		</xsl:if>
	</xsl:template>
	<xsl:template match="beispielverzeichnis">
		<xsl:param name="contextnode"/>
		<div class="beispielverz">
			<p class="zwischentitel2">
				<xsl:value-of select="preceding::abschnitt[1]/titel"/>
			</p>
			<xsl:apply-templates mode="bspvz"/>
		</div>
	</xsl:template>
	<xsl:template match="id" mode="bspvz">
		<xsl:variable name="Pfad">
			<xsl:apply-templates select="key('id_key',.)[1]" mode="getpage">
				<xsl:with-param name="Pfad">
					<xsl:call-template name="CSSPfad"/>
				</xsl:with-param>
			</xsl:apply-templates>
			<xsl:text>#</xsl:text>
			<xsl:value-of select="."/>
		</xsl:variable>
		<xsl:variable name="link">
			<xsl:variable name="CSSPfad">
				<xsl:call-template name="CSSPfad"/>
			</xsl:variable>
			<xsl:apply-templates select="key('id_key',.)/ancestor::file[1]/@directory"/>
			<xsl:call-template name="get.medialink">
				<xsl:with-param name="contextnode" select="key('id_key',.)"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="mode">
			<xsl:value-of select="name(key('id_key',.)[1])"/>
		</xsl:variable>
		<xsl:variable name="objekt">
			<xsl:apply-templates select="$styles/entry[@name = $mode]/tocline/line">
				<xsl:with-param name="contextnode" select="key('id_key',.)"/>
			</xsl:apply-templates>
		</xsl:variable>
		<xsl:variable name="nummer">
			<xsl:apply-templates select="$styles/entry[@name = $mode]/tocline/number">
				<xsl:with-param name="contextnode" select="key('id_key',.)"/>
			</xsl:apply-templates>
		</xsl:variable>
		<xsl:if test="string-length(key('id_key',.)[1]/@number) &gt; 0">
			<a href="{$Pfad}" class="objektnum">
				<xsl:value-of select="$nummer"/>
			</a>
		</xsl:if>
		<xsl:if test="string-length($nummer) &gt; 1">
			<xsl:choose>	<!-- &nbsp; bei Beispielen ohne Titel / SG 05.12.06 -->
				<xsl:when test="string-length($objekt) &gt; 0">
					<a href="{$Pfad}" class="objekt">
						<xsl:value-of select="$objekt"/>
						<xsl:comment>Titel ist >null</xsl:comment>
					</a>				
				</xsl:when>
				<xsl:otherwise>
					<a href="{$Pfad}" class="objekt"><xsl:text>&#x00a0;</xsl:text></a>
						<xsl:comment>Titel ist null</xsl:comment>					
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
	<xsl:template match="aufgabenverzeichnis">
		<xsl:param name="contextnode"/>
		<div class="aufgabenverz">
			<p class="zwischentitel2">
				<xsl:value-of select="preceding::abschnitt[1]/titel"/>
			</p>
			<xsl:apply-templates mode="aufgvz"/>
		</div>
	</xsl:template>
	<xsl:template match="id" mode="aufgvz">
		<xsl:variable name="Pfad">
			<xsl:apply-templates select="key('id_key',.)[1]" mode="getpage">
				<xsl:with-param name="Pfad">
					<xsl:call-template name="CSSPfad"/>
				</xsl:with-param>
			</xsl:apply-templates>
			<xsl:text>#</xsl:text>
			<xsl:value-of select="."/>
		</xsl:variable>
		<xsl:variable name="link">
			<xsl:variable name="CSSPfad">
				<xsl:call-template name="CSSPfad"/>
			</xsl:variable>
			<xsl:apply-templates select="key('id_key',.)[1]/ancestor::file[1]/@directory"/>
			<xsl:call-template name="get.medialink">
				<xsl:with-param name="contextnode" select="key('id_key',.)[1]"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="mode">
			<xsl:value-of select="name(key('id_key',.)[1])"/>
		</xsl:variable>
		<xsl:variable name="objekt">
			<xsl:apply-templates select="$styles/entry[@name = $mode and @variant='task']/tocline/line">
				<xsl:with-param name="contextnode" select="key('id_key',.)[1]"/>
			</xsl:apply-templates>
		</xsl:variable>
		<xsl:variable name="nummer">
			<xsl:apply-templates select="$styles/entry[@name = $mode and @variant='task']/tocline/number">
				<xsl:with-param name="contextnode" select="key('id_key',.)[1]"/>
			</xsl:apply-templates>
		</xsl:variable>
		<xsl:if test="string-length(key('id_key',.)[1]/@number) &gt; 0">
			<a href="{$Pfad}" class="objektnum">
				<xsl:value-of select="$nummer"/>
			</a>
		</xsl:if>
		<xsl:if test="(string-length($nummer) &gt; 1)">
			<a href="{$Pfad}" class="objekt">
				<xsl:value-of select="$objekt"/>
			</a>
		</xsl:if>
	</xsl:template>
	<xsl:template match="multimedia">
		<xsl:param name="contextnode"/>
		<div class="multimediaverz">
			<p class="zwischentitel">
				<xsl:value-of select="preceding::abschnitt[1]/titel"/>
			</p>
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
		</div>
	</xsl:template>
	<xsl:template match="multimedia">
		<xsl:param name="contextnode"/>
		<div class="multimediaverz">
			<p class="zwischentitel">
				<xsl:value-of select="preceding::abschnitt[1]/titel"/>
			</p>
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
		</div>
	</xsl:template>
	<xsl:template match="animationsverzeichnis">
		<xsl:param name="contextnode"/>
		<p class="zwischentitel2">
			<xsl:value-of select="preceding::abschnitt[1]/titel"/>
		</p>
		<xsl:apply-templates mode="medvz">
			<xsl:with-param name="modus">animation</xsl:with-param>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="audioverzeichnis">
		<xsl:param name="contextnode"/>
		<p class="zwischentitel2">
			<xsl:value-of select="preceding::abschnitt[1]/titel"/>
		</p>
		<xsl:apply-templates mode="medvz">
			<xsl:with-param name="modus">audio</xsl:with-param>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="simulationsverzeichnis">
		<xsl:param name="contextnode"/>
		<p class="zwischentitel2">
			<xsl:value-of select="preceding::abschnitt[1]/titel"/>
		</p>
		<xsl:apply-templates mode="medvz">
			<xsl:with-param name="modus">simulation</xsl:with-param>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="textobjektverzeichnis">
		<xsl:param name="contextnode"/>
		<p class="zwischentitel2">
			<xsl:value-of select="preceding::abschnitt[1]/titel"/>
		</p>
		<xsl:apply-templates mode="medvz">
			<xsl:with-param name="modus">textobjekt</xsl:with-param>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="videoverzeichnis">
		<xsl:param name="contextnode"/>
		<p class="zwischentitel2">
			<xsl:value-of select="preceding::abschnitt[1]/titel"/>
		</p>
		<xsl:apply-templates mode="medvz">
			<xsl:with-param name="modus">video</xsl:with-param>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="id" mode="medvz">
		<xsl:param name="modus"/>
		<xsl:variable name="mediaelement" select="key('id_key',.)/parent::*/(animation|simulation|audio|video|textobjekt)[1]"/>
		<xsl:variable name="mediapath">
			<xsl:call-template name="get.mediapath">
				<xsl:with-param name="mediaref" select="$mediaelement/@fileref"/>
			</xsl:call-template>
			<xsl:text>/</xsl:text>
		</xsl:variable>
		<xsl:variable name="Pfad">
			<xsl:apply-templates select="key('id_key',.)" mode="getpage">
				<xsl:with-param name="Pfad">
					<xsl:call-template name="CSSPfad"/>
				</xsl:with-param>
			</xsl:apply-templates>
			<xsl:text>#</xsl:text>
			<xsl:value-of select="key('id_key',.)/parent::*/@id"/>
		</xsl:variable>
		<xsl:variable name="nmodus">
			<xsl:choose>
				<xsl:when test="key('id_key',.)/parent::*/animation and key('id_key',.)/parent::*/textobjekt">
					<xsl:text>animation-textobjekt</xsl:text>
				</xsl:when>
				<xsl:when test="contains(key('id_key',.)[1]/parent::*/textobjekt/@fileref,'.xml')">
					<xsl:text>xml-textobjekt</xsl:text>
				</xsl:when>
				<xsl:when test="key('id_key',.)/parent::*/textobjekt">
					<xsl:text>textobjekt</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$modus"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="link">
			<xsl:choose>
				<xsl:when test="$nmodus='xml-textobjekt'">
					<xsl:value-of select="$Pfad"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:choose>
						<xsl:when test="contains($mediaelement/@fileref,'.htm') or contains($mediaelement/@fileref,'.swf')">
							<xsl:choose>
								<xsl:when test="name($mediaelement)='textobjekt'">
									<xsl:value-of select="concat($mediapath,key('id_key',.)/parent::*/@id,'.html')"/>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="concat($mediapath,$mediaelement/@id,'.html')"/>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:when>
						<xsl:otherwise>
							<xsl:call-template name="get.medialink">
								<xsl:with-param name="contextnode" select="key('id_key',.)/parent::*/(audio|animation|simulation|video|textobjekt)[1]"/>
							</xsl:call-template>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="objekt">
			<xsl:apply-templates select="$styles/entry[@name='medienobjekt'][@variant=$nmodus]/tocline/line">
				<xsl:with-param name="contextnode" select="key('id_key',.)/parent::node()"/>
			</xsl:apply-templates>
		</xsl:variable>
		<xsl:variable name="nummer">
			<xsl:apply-templates select="$styles/entry[@name='medienobjekt'][@variant=$nmodus]/tocline/number">
				<xsl:with-param name="contextnode" select="key('id_key',.)/parent::node()"/>
			</xsl:apply-templates>
		</xsl:variable>
		<xsl:if test="string-length(key('id_key',.)[1]/parent::*/@number) &gt; 0">
			<a class="medziel" href="{$link}"/>
			<a href="{$Pfad}" class="mediennum">
				<xsl:value-of select="$nummer"/>
				<span class="medium">
					<xsl:value-of select="$objekt"/>
				</span>
			</a>
		</xsl:if>
	</xsl:template>
	<xsl:template match="mediatitle|xrefformat|colorbox|erlaeuterung|indextitle">
		<xsl:param name="contextnode"/>
		<xsl:param name="Pfad"/>
		<xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="glossar | literaturverz">
		<xsl:param name="contextnode"/>
		<xsl:param name="Pfad"/>
		<div>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates select="node()">
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
		</div>
	</xsl:template>
	<xsl:template match="processing-instruction('font-size')">
</xsl:template>
	<xsl:template match="titel|zwischentitel" mode="TOC">


		<xsl:param name="Pfad"/>
		<xsl:param name="contextnode"/>


		<xsl:variable name="href">
			<xsl:choose>
				<xsl:when test="contains(parent::abschnitt/parent::file/@directory,'ml/ml')">
					<xsl:apply-templates select="key('id_key',@id)[2]" mode="getpage">
						<xsl:with-param name="contextnode" select="$contextnode"/>
						<xsl:with-param name="Pfad" select="$Pfad"/>
					</xsl:apply-templates>
				</xsl:when>
				<xsl:when test="contains(parent::abschnitt/parent::file/@directory,'kor/kor')">
					<xsl:apply-templates select="key('id_key',@id)[3]" mode="getpage">
						<xsl:with-param name="contextnode" select="$contextnode"/>
						<xsl:with-param name="Pfad" select="$Pfad"/>
					</xsl:apply-templates>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates select="key('id_key',@id)[1]" mode="getpage">
						<xsl:with-param name="contextnode" select="$contextnode"/>
						<xsl:with-param name="Pfad" select="$Pfad"/>
				</xsl:apply-templates>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:value-of select="concat('#',@id)"/>
		</xsl:variable>
		<xsl:variable name="level">
			<xsl:choose>
				<xsl:when test="parent::abschnitt/@level">
					<xsl:value-of select="parent::abschnitt/@level"/>
				</xsl:when>
				<xsl:when test="ancestor::literaturverz">
					<xsl:value-of select="(number(ancestor::literaturverz/abschnitt[1]/@level)+1)"/>
				</xsl:when>
			</xsl:choose>
		</xsl:variable>
		<xsl:if test="string-length(.) &gt;= 1">
			<a>
				<xsl:attribute name="href"><xsl:value-of select="$href"/></xsl:attribute>
				<xsl:attribute name="class"><xsl:text>inhalt</xsl:text><xsl:if test="parent::abschnitt/@number"><xsl:value-of select="$level"/><xsl:text>num</xsl:text></xsl:if></xsl:attribute>
				<xsl:value-of select="parent::abschnitt/@number"/>
			</a>
			<a>
				<xsl:attribute name="href"><xsl:value-of select="$href"/></xsl:attribute>
				<xsl:attribute name="class">
					<xsl:choose>
						<xsl:when test="parent::abschnitt/@number"><xsl:text>inhalt</xsl:text><xsl:value-of select="$level"/></xsl:when>
						<xsl:otherwise><xsl:text>inhalt</xsl:text><xsl:value-of select="$level"/><xsl:text>onum</xsl:text></xsl:otherwise>
					</xsl:choose>
				</xsl:attribute>

			<xsl:choose>
				<xsl:when test="contains(parent::abschnitt/parent::file/@directory,'ml/ml')">
					<xsl:text>Musterlösungen von </xsl:text>
				</xsl:when>
				<xsl:when test="contains(parent::abschnitt/parent::file/@directory,'kor/kor')">
					<xsl:text>Korrektorversion von </xsl:text>
				</xsl:when>
			</xsl:choose>


				<xsl:apply-templates select="node()[not(self::indexfix)]" mode="TOC"/>

			</a>
			<span class="strich">|</span>
			<!-- als Unterstützung von CSS-problematischer Browser -->
		</xsl:if>
	</xsl:template>
	<xsl:template match="node()" mode="TOC">
		<xsl:value-of select="."/>
	</xsl:template>
	<xsl:template match="formel-imtext" mode="TOC">
		<xsl:variable name="Pfad">
			<xsl:call-template name="CSSPfad"/>
		</xsl:variable>
		<span>
			<xsl:call-template name="standardattribute"/>
			<img src="{concat('math/',@hash,'.gif')}" alt="{.}" class="{concat('m',@hash)}"/>
		</span>
	</xsl:template>
	<xsl:template match="formel-imtext" mode="TOC">
		<xsl:variable name="Pfad">
			<xsl:call-template name="CSSPfad"/>
		</xsl:variable>
		<span>
			<xsl:call-template name="standardattribute"/>
			<img src="{concat('math/',@hash,'.gif')}" alt="{.}" class="{concat('m',@hash)}"/>
		</span>
	</xsl:template>
	<xsl:template match="untertitel" mode="TOC">
		<span class="inhaltuntertitel">
			<xsl:value-of select="."/>
		</span>
	</xsl:template>
</xsl:stylesheet>
