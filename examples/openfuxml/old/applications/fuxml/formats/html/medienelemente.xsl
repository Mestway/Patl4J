<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
version='2.0'>

<!-- ********************************************************************
     $Id: medienelemente.xsl,v 1.3 2007/01/11 13:38:26 gebhard Exp $
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
     This file contains the functions for elements of multimedia
     
     Use in Pass 4
	
	*******************************************************************
	Beschreibung:
	Diese Datei enthält die Designvorschriften für die multimedialen Inhalte.
	Sowohl inline als auch Medienobjekte.
     ********************************************************************
-->

	<!-- Inline image -->
	<xsl:template match="bild|grafik">
		<span>
			<xsl:call-template name="standardattribute"/>
			<img alt="{@alt}" width="{@width}" height="{@depth}">
				 <xsl:attribute name="src">
					<xsl:call-template name="get.medialink">
						<xsl:with-param name="contextnode" select="current()"/>
					</xsl:call-template>
				</xsl:attribute>
				<xsl:call-template name="fliessen">
					<xsl:with-param name="node" select="."/>
				</xsl:call-template>
			</img>
		</span>	
	</xsl:template>

	<!-- Inline Objekte -->
	<xsl:template match="animation|simulation|audio|video">
		<!-- globale Variablen -->
		<xsl:variable name="Pfad" select="ancestor-or-self::file/@directory"/>

		<!-- Funktionen für das Piktogramm -->
			<xsl:variable name="piktofilename">
				<xsl:call-template name="get.filename">
					<xsl:with-param name="mediaref" select="@pikto"/>
				</xsl:call-template>
			</xsl:variable>

		<!-- Welche Designdatei ist zu nehmen, und wo finde ich sie? -->
			<xsl:variable name="designdatei">
				<xsl:value-of select="ancestor-or-self::file/@design"/>
			</xsl:variable>
			<xsl:variable name="design" select="document(concat($designpath,$designdatei))"/>
	
		<!-- Mediadir bezeichnet den kompletten Pfad zur Quelldatei -->
		<xsl:variable name="mediadir">
			<xsl:call-template name="get.mediapath">
				<xsl:with-param name="mediaref" select="@sourcefile"/>
			</xsl:call-template>
				<xsl:text>/</xsl:text>
				<xsl:value-of select="@fileref"/>
		</xsl:variable>

		<!-- fullmediapath enthält den kompletten Pfad auf das Medienelement für die Webseite-->
		<xsl:variable name="fullmediapath">
			<xsl:call-template name="completePath">
				<xsl:with-param name="sourcefile" select="@sourcefile"/>			
				<xsl:with-param name="mediaref" select="@fileref"/>
				<xsl:with-param name="medianame" select="name(current())"/>
			</xsl:call-template>
		</xsl:variable>

	<!-- CSS-Pfadberechnungen, wie weit ist es bis zur Rootebene? -->
		<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
		<xsl:variable name="eCSSPfad">
			<xsl:value-of select="$CSSPfad"/>
			<xsl:call-template name="rootPfad">
				<xsl:with-param name="Ebene">
					<xsl:value-of select="string-length($fullmediapath) - string-length(translate($fullmediapath, '/', ''))"/>
				</xsl:with-param>
			</xsl:call-template>
		</xsl:variable>

	<!-- Status-Nachricht in das Logbuch abgeben -->
		<xsl:message>create inline-element <xsl:value-of select="concat($Pfad,$fullmediapath,@id,'.html')"/> from <xsl:value-of select="@fileref"/></xsl:message>
		<xsl:choose>
		<xsl:when test="contains(@fileref,'.htm')">
				<!-- Wenn das Multimediaelement in einer Webseite steckt,
					dann erzeuge eine neue Seite mit dem aktuellen Design und 
					bette die Webseite des filerefs als content ein -->
				<xsl:result-document href="{concat($Pfad,$fullmediapath,@id,'.html')}">
					<xsl:apply-templates select="$design/html">
						<xsl:with-param name="Pfad" select="$eCSSPfad"/>
						<xsl:with-param name="contextnode" select="."/>
						<xsl:with-param name="filenode" select="ancestor::file" tunnel="yes"/>
						<xsl:with-param name="filename" select="$mediadir" tunnel="yes"/>
						<xsl:with-param name="encoding" tunnel="yes">iso-8859-1</xsl:with-param>
						<xsl:with-param name="backlinkid" tunnel="yes" select="@id"/>
					</xsl:apply-templates>
				</xsl:result-document>
		<span>
			<xsl:call-template name="standardattribute"/>
			<span class="pikto">
			<a href="{concat($fullmediapath,@id,'.html')}">
					<img src="{concat('bilder/',$piktofilename)}" alt="{@alt}" width="{@width}" height="{@depth}">
						<xsl:call-template name="fliessen">
							<xsl:with-param name="node" select="."/>
						</xsl:call-template>
				</img>
			</a>
			</span>
		</span>
		</xsl:when>
		<xsl:when test="contains(@fileref,'.swf') or contains(@fileref,'.dcr')">
				<!-- Wenn das Multimediaelement eine Shockwavedatei, oder Directordatei ist,
					dann erzeuge eine neue Seite mit dem aktuellen Design und 
					bette das Shockwave des filerefs als <object> ein.
					
					Hier wird nur die Seite abgespalten, 
					das <object>-Tag wird erst in der xhtml.xsl verarbeitet -->
					<xsl:result-document href="{concat($Pfad,$fullmediapath,@id,'.html')}">
					<xsl:apply-templates select="$design/html">
						<xsl:with-param name="Pfad" select="$eCSSPfad"/>
						<xsl:with-param name="contextnode" select="."/>
						<xsl:with-param name="filenode" select="ancestor::file" tunnel="yes"/>
						<xsl:with-param name="filename" select="$mediadir" tunnel="yes"/>
						<xsl:with-param name="med-width" tunnel="yes">670px</xsl:with-param>
						<xsl:with-param name="med-height" tunnel="yes">500px</xsl:with-param>
						<xsl:with-param name="mediaelement" select="current()" tunnel="yes"/>
						<xsl:with-param name="encoding" tunnel="yes">iso-8859-1</xsl:with-param>
						<xsl:with-param name="backlinkid" tunnel="yes" select="@id"/>
					</xsl:apply-templates>
				</xsl:result-document>
		<span>
			<xsl:call-template name="standardattribute"/>
			<span class="pikto">
			<a href="{concat($fullmediapath,@id,'.html')}">
					<img src="{concat('bilder/',$piktofilename)}" alt="{@alt}" width="{@width}" height="{@depth}">
						<xsl:call-template name="fliessen">
							<xsl:with-param name="node" select="."/>
						</xsl:call-template>
				</img>
			</a>
			</span>
		</span>
		</xsl:when>
	<xsl:otherwise>		
		<!-- Standartmäßig wird nur ein Link auf die Mediadatei erzeugt -->
		<span>
			<xsl:call-template name="standardattribute"/>
			<span class="pikto">
			<a>
				<xsl:attribute name="href">
					<xsl:call-template name="get.medialink">
						<xsl:with-param name="contextnode" select="current()"/>
					</xsl:call-template>
				</xsl:attribute>
				<img src="{concat('bilder/',$piktofilename)}" alt="{@alt}" width="{@width}" height="{@depth}">
				<xsl:call-template name="fliessen">
					<xsl:with-param name="node" select="."/>
				</xsl:call-template>
				</img>
			</a>
			</span>
		</span>
	</xsl:otherwise>
	</xsl:choose>
</xsl:template>

	<!-- Inline textobjekt -->
	<xsl:template match="textobjekt">
		<xsl:variable name="filename">
			<xsl:call-template name="get.filename">
				<xsl:with-param name="mediaref" select="textobjekt/@pikto"/>
			</xsl:call-template>
		</xsl:variable>
		<span>
			<a  href="{@fileref}">
				<xsl:call-template name="standardattribute"/>
				<img src="{concat('bilder/',$filename)}" alt="{@alt}" width="{@width}" height="{@depth}" >
				<xsl:call-template name="fliessen">
					<xsl:with-param name="node" select="."/>
				</xsl:call-template>
				</img>
			</a>
		</span>
	</xsl:template>
	
	<!-- Sonderfall: Ein Absatz ohne Texte, aber mit einem Inlineelement-->
	<xsl:template match="	absatz[not(text())][bild|grafik|animation|simulation|textobjekt|video|audio]|
								absatz-klein[not(text())][bild|grafik|animation|simulation|textobjekt|video|audio]|
								absatz-mini[not(text())][bild|grafik|animation|simulation|textobjekt|video|audio]|
								absatz-ohne[not(text())][bild|grafik|animation|simulation|textobjekt|video|audio]">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
		<p>
			<xsl:call-template name="standardattribute"/>
			<div>
			<xsl:attribute name="style">
				<xsl:text>text-align:</xsl:text><xsl:value-of select="(bild|grafik|animation|audio|simulation|textobjekt|video)[1]/@align"/>
			</xsl:attribute>
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
			</div>
		</p>
	</xsl:template>


	<!-- image in marginpar -->
	<xsl:template match="bild" mode="marginalie">
		<span>
			<xsl:call-template name="standardattribute"/>
			<img alt="{@alt}" width="{@width}" height="{@depth}">
				 <xsl:attribute name="src">
					<xsl:call-template name="get.medialink">
						<xsl:with-param name="contextnode" select="current()"/>
					</xsl:call-template>
				</xsl:attribute>
			</img>
		</span>	
	</xsl:template>
	

	<!-- Image in mediaobject -->
	<xsl:template match="medienobjekt[bild|grafik]">
		<xsl:variable name="mediaelement" select="(bild|grafik)[1]"/>
		<xsl:variable name="style" select="$styles/entry[@name='medienobjekt'][@variant='figure']"/>
		<div>
			<xsl:call-template name="standardattribute"/>
			<xsl:attribute name="style">
				<xsl:text>text-align:</xsl:text><xsl:value-of select="(bild|grafik)/@align"/>
			</xsl:attribute>

			<span class="{name($mediaelement)}" id="{$mediaelement/@id}">
				<img alt="{$mediaelement/@alt}" width="{$mediaelement/@width}" height="{$mediaelement/@depth}">
					<xsl:attribute name="src">
						<xsl:call-template name="get.medialink">
							<xsl:with-param name="contextnode" select="$mediaelement"/>
						</xsl:call-template>
					</xsl:attribute>
					<xsl:call-template name="fliessen">
						<xsl:with-param name="node" select="$mediaelement"/>
					</xsl:call-template>
				</img>
			</span>
			<div class="objekttitel">
				<p class="bildgrafnum">
					<xsl:apply-templates select="$styles/entry[@name='medienobjekt'][@variant='figure']/mediatitle">
						<xsl:with-param name="contextnode" select="current()"/>
					</xsl:apply-templates>
				</p>
			</div>
<!--		<xsl:apply-templates select="parent::*/objekttitel/*"/> -->
		</div>
	</xsl:template>
	
	<!-- Multiple subfigures -->
	<xsl:template match="medienobjekt[count(grafik|bild) gt 1]" priority="1">
		<xsl:variable name="style" select="$styles/entry[@name='medienobjekt'][@variant='subfigure']"/>
		<xsl:variable name="mediaelement" select="(bild|grafik)[1]"/>
		<div>
			<xsl:call-template name="standardattribute"/>
			<xsl:attribute name="style">
				<xsl:text>text-align:</xsl:text><xsl:value-of select="(bild|grafik)[1]/@align"/>
			</xsl:attribute>
			<span class="{name($mediaelement)}" id="{$mediaelement/@id}">
				<xsl:apply-templates select="$style/structure/descendant::processing-instruction('subimages')">
					<xsl:with-param name="contextnode" select="current()"/>
				</xsl:apply-templates>
			</span>
			<div class="objekttitel">
				<p class="bildgrafnum">
					<xsl:apply-templates select="$styles/entry[@name='medienobjekt'][@variant='subfigure']/mediatitle">
						<xsl:with-param name="contextnode" select="current()"/>
					</xsl:apply-templates>
				</p>
			</div>
		</div>
	</xsl:template>

	
	<!-- Animation and Textobject -->
	<xsl:template match="medienobjekt[animation and textobjekt]">
		<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
		<xsl:variable name="designdatei">
			<xsl:value-of select="ancestor-or-self::file/@design"/>
		</xsl:variable>
		<xsl:variable name="design" select="document(concat($designpath,$designdatei))"/>
		<xsl:variable name="Pfad" select="ancestor-or-self::file/@directory"/>
		<xsl:variable name="piktofilename">
			<xsl:call-template name="get.filename">
				<xsl:with-param name="mediaref" select="animation/@pikto"/>
			</xsl:call-template>
		</xsl:variable>
		<div>
			<xsl:call-template name="standardattribute"/>
			<xsl:attribute name="style">
				<xsl:text>text-align:</xsl:text><xsl:value-of select="animation/@align"/>
			</xsl:attribute>
			<span class="animation" id="{animation/@id}">
				<span class="pikto">
				<a >
					 <xsl:attribute name="href">
						<xsl:call-template name="get.medialink">
							<xsl:with-param name="contextnode" select="current()/animation"/>
						</xsl:call-template>
					</xsl:attribute>
					<img src="{concat('bilder/',$piktofilename)}" alt="{animation/@alt}" width="{animation/@width}" height="{animation/@depth}">
					<xsl:call-template name="fliessen">
						<xsl:with-param name="node" select="current()/animation"/>
					</xsl:call-template>
					</img>
				</a>
				</span>
				</span>
				<div class="objekttitel">
				<p class="medienobjektnum">
				<xsl:apply-templates select="$styles/entry[@name='medienobjekt'][@variant='animation-textobjekt']/mediatitle">
					<xsl:with-param name="contextnode" select="current()"/>
				</xsl:apply-templates>
				</p>
				</div>
				<xsl:result-document href="{concat($Pfad,concat(@id,'.html'))}">
					<xsl:apply-templates select="$design/html">
						<xsl:with-param name="Pfad" select="$CSSPfad"/>
						<xsl:with-param name="filenode" select="ancestor::file" tunnel="yes"/>
						<xsl:with-param name="contextnode">
							<div>
								<xsl:call-template name="standardattribute"/>
								<xsl:apply-templates select="textobjekt"/>
							</div>
							<a class="backpikto">
								<xsl:attribute name="href">
									<xsl:apply-templates select="key('id_key',@id)" mode="getpage">
									</xsl:apply-templates>#<xsl:value-of select="@id"/>
								</xsl:attribute>
							</a>
						</xsl:with-param>
					</xsl:apply-templates>
				</xsl:result-document>
				<a class="textopikto" href="{concat(@id,'.html')}"/>
		</div>
	</xsl:template>

	
	<xsl:template match="medienobjekt[animation|simulation|audio|video][not(textobjekt)][not(bild|grafik)]">
		<!-- globale Variablen -->
		<xsl:variable name="mediaelement" select="(animation|simulation|audio|video)[1]"/>
		<xsl:variable name="Pfad" select="ancestor-or-self::file/@directory"/>
		<!-- Funktionen für das Piktogramm -->
		<xsl:variable name="piktofilename">
			<xsl:call-template name="get.filename">
				<xsl:with-param name="mediaref" select="$mediaelement/@pikto"/>
			</xsl:call-template>
		</xsl:variable>

		<!-- Welche Designdatei ist zu nehmen, und wo finde ich sie? -->
		<xsl:variable name="designdatei">
			<xsl:value-of select="ancestor-or-self::file/@design"/>
		</xsl:variable>
		<xsl:variable name="design" select="document(concat($designpath,$designdatei))"/>

		<!-- Mediadir bezeichnet den kompletten Pfad zur Quelldatei -->		
		<xsl:variable name="mediadir">
			<xsl:call-template name="get.mediapath">
				<xsl:with-param name="mediaref" select="$mediaelement/@sourcefile"/>
			</xsl:call-template>
				<xsl:text>/</xsl:text>
				<xsl:value-of select="$mediaelement/@fileref"/>
		</xsl:variable>

		<!-- fullmediapath enthält den kompletten Pfad auf das Medienelement für die Webseite-->
		<xsl:variable name="fullmediapath">
			<xsl:call-template name="completePath">
				<xsl:with-param name="sourcefile" select="$mediaelement/@sourcefile"/>			
				<xsl:with-param name="mediaref" select="$mediaelement/@fileref"/>
				<xsl:with-param name="medianame" select="name($mediaelement)"/>
			</xsl:call-template>
		</xsl:variable>

	<!-- CSS-Pfadberechnungen, wie weit ist es bis zur Rootebene? -->
		<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
		<xsl:variable name="eCSSPfad">
			<xsl:value-of select="$CSSPfad"/>
			<xsl:call-template name="rootPfad">
				<xsl:with-param name="Ebene">
					<xsl:value-of select="string-length($fullmediapath) - string-length(translate($fullmediapath, '/', ''))"/>
				</xsl:with-param>
			</xsl:call-template>
		</xsl:variable>

	<!-- Status-Nachricht in das Logbuch abgeben -->
	<xsl:message>create <xsl:value-of select="concat($Pfad,$fullmediapath,$mediaelement/@id,'.html')"/> from <xsl:value-of select="$mediaelement/@fileref"/></xsl:message>

		<xsl:choose>
		<xsl:when test="contains($mediaelement/@fileref,'.htm')">
				<!-- Wenn das Multimediaelement in einer Webseite steckt,
					dann erzeuge eine neue Seite mit dem aktuellen Design und 
					bette die Webseite des filerefs als content ein -->
				<xsl:result-document href="{concat($Pfad,$fullmediapath,$mediaelement/@id,'.html')}">
					<xsl:apply-templates select="$design/html">
						<xsl:with-param name="Pfad" select="$eCSSPfad"/>
						<xsl:with-param name="contextnode" select="."/>
						<xsl:with-param name="filenode" select="ancestor::file" tunnel="yes"/>
						<xsl:with-param name="filename" select="$mediadir" tunnel="yes"/>
						<xsl:with-param name="encoding" tunnel="yes">iso-8859-1</xsl:with-param>
						<xsl:with-param name="backlinkid" tunnel="yes" select="@id"/>
					</xsl:apply-templates>
				</xsl:result-document>
		<div>
			<xsl:call-template name="standardattribute"/>
			<xsl:attribute name="style">
				<xsl:text>text-align:</xsl:text><xsl:value-of select="$mediaelement/@align"/>
			</xsl:attribute>
			<span class="{name($mediaelement)}" id="{$mediaelement/@id}">
				<span class="pikto">
				<a href="{concat($fullmediapath,$mediaelement/@id,'.html')}">
					<img src="{concat('bilder/',$piktofilename)}" alt="{$mediaelement/@alt}" width="{$mediaelement/@width}" height="{$mediaelement/@depth}">
					<xsl:call-template name="fliessen">
						<xsl:with-param name="node" select="$mediaelement"/>
					</xsl:call-template>
					</img>
				</a>
				</span>
			</span>
			<div class="objekttitel">
				<p class="medienobjektnum">
				<xsl:apply-templates select="$styles/entry[@name='medienobjekt'][@variant=name($mediaelement)]/mediatitle">
					<xsl:with-param name="contextnode" select="current()"/>
				</xsl:apply-templates>
				</p>
			</div>
		</div>
		</xsl:when>
		<xsl:when test="contains($mediaelement/@fileref,'.swf') or contains($mediaelement/@fileref,'.dcr')">
				<!-- Wenn das Multimediaelement eine Shockwavedatei, oder Directordatei ist,
					dann erzeuge eine neue Seite mit dem aktuellen Design und 
					bette das Shockwave des filerefs als <object> ein.
					
					Hier wird nur die Seite abgespalten, 
					das <object>-Tag wird erst in der xhtml.xsl verarbeitet -->
				<xsl:result-document href="{concat($Pfad,$fullmediapath,$mediaelement/@id,'.html')}">
					<xsl:apply-templates select="$design/html">
						<xsl:with-param name="Pfad" select="$eCSSPfad"/>
						<xsl:with-param name="contextnode" select="."/>
						<xsl:with-param name="filenode" select="ancestor::file" tunnel="yes"/>
						<xsl:with-param name="filename" select="$mediadir" tunnel="yes"/>
						<xsl:with-param name="med-width" tunnel="yes"><xsl:value-of select="$styles/entry[@name='medienobjekt'][@variant=name($mediaelement)]/canvas-width"/></xsl:with-param>
						<xsl:with-param name="med-height" tunnel="yes"><xsl:value-of select="$styles/entry[@name='medienobjekt'][@variant=name($mediaelement)]/canvas-height"/></xsl:with-param>			
						<xsl:with-param name="mediaelement" select="$mediaelement" tunnel="yes"/>
						<xsl:with-param name="encoding" tunnel="yes">iso-8859-1</xsl:with-param>
						<xsl:with-param name="backlinkid" tunnel="yes" select="@id"/>
					</xsl:apply-templates>
				</xsl:result-document>
		<div>
			<xsl:call-template name="standardattribute"/>
			<xsl:attribute name="style">
				<xsl:text>text-align:</xsl:text><xsl:value-of select="$mediaelement/@align"/>
			</xsl:attribute>
			<span class="{name($mediaelement)}" id="{$mediaelement/@id}">
				<span class="pikto">
				<a href="{concat($fullmediapath,$mediaelement/@id,'.html')}">
					<img src="{concat('bilder/',$piktofilename)}" alt="{$mediaelement/@alt}" width="{$mediaelement/@width}" height="{$mediaelement/@depth}">
					<xsl:call-template name="fliessen">
						<xsl:with-param name="node" select="$mediaelement"/>
					</xsl:call-template>
					</img>
				</a>
				</span>
			</span>
			<div class="objekttitel">
				<p class="medienobjektnum">
				<xsl:apply-templates select="$styles/entry[@name='medienobjekt'][@variant=name($mediaelement)]/mediatitle">
					<xsl:with-param name="contextnode" select="current()"/>
				</xsl:apply-templates>
				</p>
			</div>
		</div>
		</xsl:when>
		<xsl:otherwise>
		<!-- Standartmäßig wird nur ein Link auf die Mediadatei erzeugt -->
		<div>
			<xsl:call-template name="standardattribute"/>
			<xsl:attribute name="style">
				<xsl:text>text-align:</xsl:text><xsl:value-of select="$mediaelement/@align"/>
			</xsl:attribute>
			<span class="{name($mediaelement)}" id="{$mediaelement/@id}">
				<span class="pikto">
				<a >
					<xsl:attribute name="href">
						<xsl:call-template name="get.medialink">
							<xsl:with-param name="contextnode" select="$mediaelement"/>
						</xsl:call-template>
					</xsl:attribute>
					<img src="{concat('bilder/',$piktofilename)}" id="{$mediaelement/@id}" alt="{$mediaelement/@alt}" width="{$mediaelement/@width}" height="{$mediaelement/@depth}">
					<xsl:call-template name="fliessen">
						<xsl:with-param name="node" select="$mediaelement"/>
					</xsl:call-template>
					</img>
				</a>
				</span>
			</span>
			<div class="objekttitel">
				<p class="medienobjektnum">
				<xsl:apply-templates select="$styles/entry[@name='medienobjekt'][@variant=name($mediaelement)]/mediatitle">
					<xsl:with-param name="contextnode" select="current()"/>
				</xsl:apply-templates>
				</p>
			</div>
		</div>
	</xsl:otherwise>
	</xsl:choose>
	</xsl:template>

<!-- Medienobjekt/Textopbjekt -->	
	<xsl:template match="medienobjekt[textobjekt][not(animation|video|audio|simulation)][not(bild|grafik)]">
		<xsl:variable name="mediaelement" select="textobjekt[1]"/>
		<xsl:variable name="relativepath">
			<xsl:call-template name="get.relativepath">
				<xsl:with-param name="baseuri" select="@sourcefile"/>
				<xsl:with-param name="uri" select="$mediaelement/@fileref"/>
			</xsl:call-template><xsl:text>/</xsl:text>
		</xsl:variable>
		<xsl:variable name="piktofilename">
			<xsl:call-template name="get.filename">
				<xsl:with-param name="mediaref" select="textobjekt/@pikto"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="textofilename">
			<xsl:call-template name="get.medialink">
				<xsl:with-param name="contextnode" select="textobjekt"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
		<xsl:variable name="designdatei">
			<xsl:value-of select="ancestor-or-self::file/@design"/>
		</xsl:variable>
		<xsl:variable name="design" select="document(concat($designpath,$designdatei))"/>
		<xsl:variable name="Pfad" select="ancestor-or-self::file/@directory"/>
		<xsl:variable name="filename">
			<xsl:call-template name="get.filename">
				<xsl:with-param name="mediaref" select="$mediaelement/@fileref"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="medialink">
		<xsl:call-template name="get.htmlpath">
			<xsl:with-param name="contextnode" select="$mediaelement"/>
		</xsl:call-template>/<xsl:value-of select="$filename"/>
		</xsl:variable>
		<xsl:variable name="mediapath">
			<xsl:call-template name="get.mediapath">
					<xsl:with-param name="mediaref" select="$mediaelement/@fileref"/>
			</xsl:call-template>		
		</xsl:variable>
		<xsl:variable name="aktPfad">
			<xsl:value-of select="$CSSPfad"/>
			<xsl:value-of select="$Pfad"/>
		</xsl:variable>
		<xsl:variable name="eCSSPfad">
			<xsl:value-of select="$CSSPfad"/>
			<xsl:call-template name="rootPfad">
				<xsl:with-param name="Ebene">
					<xsl:value-of select="string-length($relativepath) - string-length(translate($relativepath, '/', ''))"/>
				</xsl:with-param>
			</xsl:call-template>
		</xsl:variable>
		<xsl:message>Erstelle aus <xsl:value-of select="$medialink"/> ein neues Dokument.(<xsl:value-of select="concat($aktPfad,$relativepath,@id,'.html')"/>)
							Relativer Pfad <xsl:value-of select="$relativepath"/>
							CSSPfad <xsl:value-of select="$eCSSPfad"/>

			</xsl:message>
		<xsl:choose>
		<xsl:when test="contains($mediaelement/@fileref,'.htm')">
				<!-- Wenn das Multimediaelement in einer Webseite steckt, dann bette diese Webseite in das Designtemplate ein -->
				<xsl:result-document href="{concat($Pfad,$relativepath,@id,'.html')}">
					<xsl:apply-templates select="$design/html">
						<xsl:with-param name="Pfad" select="$eCSSPfad"/>
						<xsl:with-param name="contextnode" select="."/>
						<xsl:with-param name="filenode" select="ancestor::file" tunnel="yes"/>
						<xsl:with-param name="filename" select="$medialink" tunnel="yes"/>
						<xsl:with-param name="encoding" tunnel="yes">iso-8859-1</xsl:with-param>
						<xsl:with-param name="backlinkid" tunnel="yes" select="@id"/>
					</xsl:apply-templates>
				</xsl:result-document>
		<div>
			<xsl:call-template name="standardattribute"/>
			<xsl:attribute name="style">
				<xsl:text>text-align:</xsl:text><xsl:value-of select="$mediaelement/@align"/>
			</xsl:attribute>
			<span class="{name($mediaelement)}" id="{@id}">
				<span class="pikto">
				<a href="{concat($relativepath,@id,'.html')}"> <!-- {concat($aktPfad,concat($mediapath,concat('/',concat(@id,'.html'))))}"> -->
					<img src="{concat('bilder/',$piktofilename)}" alt="{$mediaelement/@alt}" width="{$mediaelement/@width}" height="{$mediaelement/@depth}">
					<xsl:call-template name="fliessen">
						<xsl:with-param name="node" select="$mediaelement"/>
					</xsl:call-template>
					</img>
				</a>
				</span>
			</span>
			<div class="objekttitel">
				<p class="medienobjektnum">
				<xsl:apply-templates select="$styles/entry[@name='medienobjekt'][@variant=name($mediaelement)]/mediatitle">
					<xsl:with-param name="contextnode" select="current()"/>
				</xsl:apply-templates>
				</p>
			</div>
		</div>
		</xsl:when>
	<!-- textobject ist keine html-Datei -->
	<xsl:otherwise>		
		<div>
			<xsl:call-template name="standardattribute"/>
			<xsl:attribute name="style">
				<xsl:text>text-align:</xsl:text><xsl:value-of select="$mediaelement/@align"/>
			</xsl:attribute>
			<xsl:choose>
			<!-- Textobjekt ist eine XML-Datei -->
				<xsl:when test="contains($textofilename,'.xml')">
					<span class="{name($mediaelement)}" id="{$mediaelement/@id}">
						<xsl:apply-templates select="textobjekt" mode="xml-textobjekt"/>
						<xsl:if test="string-length($piktofilename) >0">
						<span class="pikto">
							<img src="{concat('bilder/',$piktofilename)}" id="{$mediaelement/@id}" alt="{$mediaelement/@alt}" width="{$mediaelement/@width}" height="{$mediaelement/@depth}">
							<xsl:call-template name="fliessen">
								<xsl:with-param name="node" select="$mediaelement"/>
							</xsl:call-template>
							</img>
						</span>
						</xsl:if>
					</span>
					<div class="objekttitel">
						<p class="medienobjektnum">
							<xsl:apply-templates select="$styles/entry[@name='medienobjekt'][@variant='xml-textobjekt']/mediatitle">
								<xsl:with-param name="contextnode" select="current()"/>
							</xsl:apply-templates>
						</p>
					</div>
				</xsl:when>
			<!-- Textobjekt ist keine XML-Datei -->
				<xsl:when test="not(contains($textofilename,'.xml'))">
					<span class="{name($mediaelement)}" id="{$mediaelement/@id}">
						<xsl:if test="string-length($piktofilename) >0">
						<span class="pikto">
						<a  href="{$textofilename}">
							<img src="{concat('bilder/',$piktofilename)}" id="{$mediaelement/@id}" alt="{$mediaelement/@alt}" width="{$mediaelement/@width}" height="{$mediaelement/@depth}">
							<xsl:call-template name="fliessen">
								<xsl:with-param name="node" select="$mediaelement"/>
							</xsl:call-template>
							</img>
						</a>
						</span>
						</xsl:if>
					</span>

					<div class="objekttitel">
						<p class="medienobjektnum">
						<xsl:apply-templates select="$styles/entry[@name='medienobjekt'][@variant='xml-textobjekt']/mediatitle">
							<xsl:with-param name="contextnode" select="current()"/>
						</xsl:apply-templates>
						</p>
					</div>
				</xsl:when>
			</xsl:choose>
		</div>
	</xsl:otherwise>
	</xsl:choose>
<!-- ##  -->
</xsl:template>

	
	<xsl:template match="textobjekt" mode="xml-textobjekt">
		<xsl:apply-templates/>
	</xsl:template>

<xsl:template name="fliessen">
<xsl:param name="node"/>

<xsl:if test="$node/@fliessen">
	 <xsl:attribute name="class">
	 	<xsl:choose>
			<xsl:when test="$node/@fliessen='nicht'">
				<xsl:text>none</xsl:text>				
			</xsl:when>
			<xsl:when test="$node/@fliessen='links'">
				<xsl:text>left</xsl:text>				
			</xsl:when>
			<xsl:when test="$node/@fliessen='rechts'">
				<xsl:text>right</xsl:text>				
			</xsl:when>
			<xsl:when test="$node/@fliessen='innen'">
				<xsl:text>innercol</xsl:text>				
			</xsl:when>
			<xsl:when test="$node/@fliessen='aussen'">
				<xsl:text>outercol</xsl:text>				
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$node/@fliessen"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:attribute>
</xsl:if>
</xsl:template>

<xsl:template name="completePath">
	<xsl:param name="sourcefile"/>
	<xsl:param name="mediaref"/>
	<xsl:param name="medianame"/>
<!--
	<xsl:variable name="path">
			<xsl:call-template name="get.mediapath">
				<xsl:with-param name="mediaref" select="$sourcefile"/>
			</xsl:call-template>
	</xsl:variable>
-->
	<xsl:variable name="mediadir"><xsl:call-template name="get.mediadir"><xsl:with-param name="mediaref" select="$mediaref"/></xsl:call-template></xsl:variable>
	<xsl:variable name="mediapath"><xsl:call-template name="get.mediapath"><xsl:with-param name="mediaref" select="$mediaref"/></xsl:call-template></xsl:variable>
	<xsl:variable name="mediasuffix"><xsl:call-template name="get.mediasuffix"><xsl:with-param name="mediaref" select="$mediaref"/></xsl:call-template></xsl:variable>
		
	<xsl:choose>
		<xsl:when test="$mediasuffix='html'">
			<xsl:if test="$mediaref!=''">
				<xsl:value-of select="concat($medianame,'/',$mediadir)"/><xsl:text>/</xsl:text>
			</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<xsl:if test="$mediaref!=''">
					<xsl:value-of select="$mediadir"/><xsl:text>/</xsl:text>
				</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
</xsl:template>

</xsl:stylesheet>
