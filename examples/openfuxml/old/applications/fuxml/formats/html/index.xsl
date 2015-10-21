<?xml version="1.0"?>

<!-- ********************************************************************
     $Id: index.xsl,v 1.3 2007/01/11 13:38:26 gebhard Exp $
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
     This file contains the functions for index system. Its used twice, in pass 1 and pass 4.
     First all index entrys are grouped together (main task), second the html page is created.
     
     Use in Pass1 and 4
	
	*******************************************************************
	Beschreibung:
	Diese Datei erzeugt in der ersten Phase die Indexe für Kurs oder Kurseinheiten. In der Phase vier werden diese 
	Indexe lediglich über das konfigurierte Designtemplate in HTML umgewandelt.
	Der zu Grunde liegende Code kam ursprünglich vom DocBook-Projekt und wurde an FuXML angepasst.
     ********************************************************************
-->

<!DOCTYPE xsl:stylesheet [

<!-- In einer späteren Translation werden alle Buchstaben des Alphabets in Grossbuchstaben umgesetzt -->
<!ENTITY lowercase "'abcdefghijklmnopqrstuvwxyz'">
<!ENTITY uppercase "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'">

<!-- Hier werden Schlüssel (Teilbäume) für die verschiedenen Indexlevel angelegt -->
<!ENTITY primary   'normalize-space(concat(primaer/@sortals, primaer[not(@sortals)]))'>
<!ENTITY secondary 'normalize-space(concat(sekundaer/@sortals, sekundaer[not(@sortals)]))'>
<!ENTITY tertiary  'normalize-space(concat(tertiaer/@sortals, tertiaer[not(@sortals)]))'>

<!-- Mit Hilfe der section werden die nächsthöheren Elemente definiert, welche angesprungen werden können -->
<!ENTITY section   '( preceding::abschnitt-klein
						|preceding::abschnitt-mini
						|preceding::abschnitt-ohne
		                    |preceding::abschnitt
		                    |preceding::absatz
		                    |preceding::annahme
		                    |preceding::aufgabenstellung
		                    |preceding::autoren
		                    |preceding::axiom
		                    |preceding::beispiel
		                    |preceding::bemerkung
		                    |preceding::beweis
		                    |preceding::corollar
		                    |preceding::definition
		                    |preceding::eintrag
		                    |preceding::erlaeuterung
		                    |preceding::exkurs
		                    |preceding::fall
		                    |preceding::folgerung
		                    |preceding::formel
		                    |preceding::formelarray
		                    |preceding::fussnote
		                    |preceding::hauptsatz
		                    |preceding::hilfssatz
		                    |preceding::indexfix
		                    |preceding::kommentar
		                    |preceding::lehrziele
		                    |preceding::lemma
		                    |preceding::liteintrag
		                    |preceding::marginalie
		                    |preceding::medienobjekt
		                    |preceding::merksatz
		                    |preceding::motto
		                    |preceding::norm
		                    |preceding::objekttitel
		                    |preceding::problem
		                    |preceding::proglist
		                    |preceding::proposition
		                    |preceding::querverweis
		                    |preceding::rechtsprechung
		                    |preceding::regel
		                    |preceding::satz
		                    |preceding::selbsttestaufgabe
		                    |preceding::simulation
		                    |preceding::stichwort
		                    |preceding::tabelle
		                    |preceding::tabelle-alt
		                    |preceding::textmarke
		                    |preceding::textobjekt
		                    |preceding::theorem
		                    |preceding::titel
		                    |preceding::uebungsaufgabe
		                    |preceding::universalliste
		                    |preceding::url
		                    |preceding::vertiefung
		                    |preceding::x-umgebung
		                    |preceding::zusammenfassung
		                    |preceding::zitat)[last()]'>

<!ENTITY section.id 'generate-id(&section;)'>
<!ENTITY sep '" "'>
]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="2.0">

<!-- ==================================================================== -->

<!-- Anpassung der Keys an das FuXML-Projekt -->
<!-- Einige Elemente heißen in FuXML anders als in DocBook, so dass hier eine Anpassung erfolgte -->
<xsl:key name="letter"
         match="indexfix"
         use="translate(substring(&primary;, 1, 1),&lowercase;,&uppercase;)"/>

<xsl:key name="primaer"
         match="indexfix"
         use="&primary;"/>

<xsl:key name="sekundaer"
         match="indexfix"
         use="concat(&primary;, &sep;, &secondary;)"/>

<xsl:key name="tertiaer"
         match="indexfix"
         use="concat(&primary;, &sep;, &secondary;, &sep;, &tertiary;)"/>

<xsl:key name="primary-section"
         match="indexfix[not(sekundaer) and not(siehe)]"
         use="concat(&primary;, &sep;, &section.id;)"/>

<xsl:key name="secondary-section"
         match="indexfix[not(tertiaer) and not(siehe)]"
         use="concat(&primary;, &sep;, &secondary;, &sep;, &section.id;)"/>

<xsl:key name="tertiary-section"
         match="indexfix[not(siehe)]"
         use="concat(&primary;, &sep;, &secondary;, &sep;, &tertiary;, &sep;, &section.id;)"/>

<xsl:key name="sieheauch"
         match="indexfix[sieheauch]"
         use="concat(&primary;, &sep;, &secondary;, &sep;, &tertiary;, &sep;, seealso)"/>

<xsl:key name="siehe"
         match="indexfix[siehe]"
         use="concat(&primary;, &sep;, &secondary;, &sep;, &tertiary;, &sep;, see)"/>

<xsl:key name="sections" match="*[@id]" use="@id"/>

<!-- Diese Funktion wird in der ersten Phase aufgerufen und erzeugt mit Hilfe aller weiteren Funktionen
	dieser Datei den Kontextbezogenen Index -->
<xsl:template name="generateIndex">
<xsl:param name="contextnode"/>
<xsl:param name="Pfad"/>
  <xsl:variable name="terms"
                select="$contextnode/node()[count(.|key('letter',
                                                translate(substring(&primary;, 1, 1),
                                                          &lowercase;,
                                                          &uppercase;))[1]) = 1
                                    and not(@class = 'endofrange')]"/>

  <xsl:variable name="alphabetical"
                select="$terms[contains(concat(&lowercase;, &uppercase;),
                                        substring(&primary;, 1, 1))]"/>
  <xsl:variable name="others" select="$terms[not(contains(concat(&lowercase;,
                                                 &uppercase;),
                                             substring(&primary;, 1, 1)))]"/>
  <index>
    <xsl:if test="$others">
	<gruppe>
     	<bezeichner>
          <xsl:call-template name="gentext">
            <xsl:with-param name="key">Symbole</xsl:with-param>	<!-- Alle Sonderzeichen auserhalb des Alphabets-->
          </xsl:call-template>
		</bezeichner>
		<eintraege>
          <xsl:apply-templates select="$others[count(.|key('primaer',
                                       &primary;)[1]) = 1]"
                               mode="index-symbol-div">
            <xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
            <xsl:sort select="&primary;"/>
          </xsl:apply-templates>
		</eintraege>
	</gruppe>
    </xsl:if>
    <xsl:apply-templates select="$alphabetical[count(.|key('letter',
                                 translate(substring(&primary;, 1, 1),&lowercase;,&uppercase;))[1]) = 1]"
                         mode="index-div">
      <xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
      <xsl:sort select="&primary;"/>
    </xsl:apply-templates>
</index>
</xsl:template>

<xsl:template match="indexfix">
	<a id="{@id}"/>	<!-- Keine Ausgabe im normalen Text, aber einen Anker zum Anspringen erzeugen -->
</xsl:template>

<xsl:template match="indexfix" mode="index-div">
<xsl:param name="Pfad"/>
  <xsl:variable name="key" select="translate(substring(&primary;, 1, 1),&lowercase;,&uppercase;)"/>
	<gruppe>
		<bezeichner>
	      <xsl:value-of select="translate($key, &lowercase;, &uppercase;)"/>
		</bezeichner>
		<eintraege>
			<xsl:apply-templates select="key('letter', $key)[count(.|key('primaer', &primary;)[1]) = 1]" mode="index-primary">
				<xsl:sort select="&primary;"/>
				<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
			</xsl:apply-templates>
		</eintraege>
</gruppe>
</xsl:template>

<xsl:template match="indexfix" mode="index-symbol-div">
<xsl:param name="Pfad"/>
  <xsl:variable name="key" select="translate(substring(&primary;, 1, 1),&lowercase;,&uppercase;)"/>
  <xsl:apply-templates select="key('letter', $key)[count(.|key('primaer', &primary;)[1]) = 1]"
                       mode="index-primary">
    <xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
    <xsl:sort select="&primary;"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="indexfix" mode="index-primary">
<xsl:param name="Pfad"/>
  <xsl:variable name="key" select="&primary;"/>
  <xsl:variable name="refs" select="key('primaer', $key)"/>
<subeintrag>
	<xsl:attribute name="gruppe">primaereintrag</xsl:attribute>
    <xsl:value-of select="primaer"/>
    <xsl:for-each select="$refs[generate-id() = generate-id(key('primary-section', concat($key, &sep;, &section.id;))[1])]">
      <xsl:apply-templates select="." mode="reference"/>
    </xsl:for-each>

    <xsl:if test="$refs[not(sekundaer)]/*[self::siehe]">
      <xsl:apply-templates select="$refs[generate-id() = generate-id(key('siehe', concat(&primary;, &sep;, &sep;, &sep;, see))[1])]"
                           mode="index-see">
        <xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
        <xsl:sort select="siehe"/>
      </xsl:apply-templates>
    </xsl:if>
  <xsl:if test="$refs/sekundaer or $refs[not(sekundaer)]/*[self::sieheauch]">
        <xsl:apply-templates select="$refs[generate-id() = generate-id(key('sieheauch', concat(&primary;, &sep;, &sep;, &sep;, seealso))[1])]"
                             mode="index-seealso">
          <xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
          <xsl:sort select="sieheauch"/>
        </xsl:apply-templates>
        <xsl:apply-templates select="$refs[sekundaer and count(.|key('sekundaer', concat($key, &sep;, &secondary;))[1]) = 1]" 
                             mode="index-secondary">
          <xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
          <xsl:sort select="&secondary;"/>
        </xsl:apply-templates>
  </xsl:if>
</subeintrag>
</xsl:template>

<xsl:template match="indexfix" mode="index-secondary">
<xsl:param name="Pfad"/>
  <xsl:variable name="key" select="concat(&primary;, &sep;, &secondary;)"/>
  <xsl:variable name="refs" select="key('sekundaer', $key)"/>
<subeintrag>
	<xsl:attribute name="gruppe">sekundaereintrag</xsl:attribute>
    <xsl:value-of select="sekundaer"/>
    <xsl:for-each select="$refs[generate-id() = generate-id(key('secondary-section', concat($key, &sep;, &section.id;))[1])]">
      <xsl:apply-templates select="." mode="reference"/>
    </xsl:for-each>

    <xsl:if test="$refs[not(tertiaer)]/*[self::siehe]">
      <xsl:apply-templates select="$refs[generate-id() = generate-id(key('siehe', concat(&primary;, &sep;, &secondary;, &sep;, &sep;, see))[1])]"
                           mode="index-see">
        <xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
        <xsl:sort select="siehe"/>
      </xsl:apply-templates>
    </xsl:if>
  <xsl:if test="$refs/tertiaer or $refs[not(tertiaer)]/*[self::sieheauch]">
        <xsl:apply-templates select="$refs[generate-id() = generate-id(key('sieheauch', concat(&primary;, &sep;, &secondary;, &sep;, &sep;, seealso))[1])]"
                             mode="index-seealso">
          <xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
          <xsl:sort select="sieheauch"/>
        </xsl:apply-templates>
        <xsl:apply-templates select="$refs[tertiaer and count(.|key('tertiaer', concat($key, &sep;, &tertiary;))[1]) = 1]" 
                             mode="index-tertiary">
          <xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
          <xsl:sort select="&tertiary;"/>
        </xsl:apply-templates>
  </xsl:if>
</subeintrag>
</xsl:template>

<xsl:template match="indexfix" mode="index-tertiary">
<xsl:param name="Pfad"/>
  <xsl:variable name="key" select="concat(&primary;, &sep;, &secondary;, &sep;, &tertiary;)"/>
  <xsl:variable name="refs" select="key('tertiaer', $key)"/>
<subeintrag>
	<xsl:attribute name="gruppe">teriaereintrag</xsl:attribute>
    <xsl:value-of select="tertiaer"/>
    <xsl:for-each select="$refs[generate-id() = generate-id(key('tertiary-section', concat($key, &sep;, &section.id;))[1])]">
      <xsl:apply-templates select="." mode="reference"/>
    </xsl:for-each>

    <xsl:if test="$refs/siehe">
      <xsl:apply-templates select="$refs[generate-id() = generate-id(key('siehe', concat(&primary;, &sep;, &secondary;, &sep;, &tertiary;, &sep;, see))[1])]"
                           mode="index-see">
        <xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
        <xsl:sort select="siehe"/>
      </xsl:apply-templates>
    </xsl:if>
  <xsl:if test="$refs/sieheauch">
        <xsl:apply-templates select="$refs[generate-id() = generate-id(key('siehe', concat(&primary;, &sep;, &secondary;, &sep;, &tertiary;, &sep;, see))[1])]"
                             mode="index-see">
          <xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
          <xsl:sort select="siehe"/>
        </xsl:apply-templates>
        <xsl:apply-templates select="$refs[generate-id() = generate-id(key('sieheauch', concat(&primary;, &sep;, &secondary;, &sep;, &tertiary;, &sep;, seealso))[1])]"
                             mode="index-seealso">
          <xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
          <xsl:sort select="sieheauch"/>
        </xsl:apply-templates>
  </xsl:if>
</subeintrag>
</xsl:template>

<xsl:template match="indexfix" mode="reference">
<xsl:param name="Pfad"/>
  <xsl:text>, </xsl:text>
  <xsl:choose>
    <xsl:when test="@zone and string(@zone)">
      <xsl:call-template name="reference">
        <xsl:with-param name="zones" select="normalize-space(@zone)"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
    	<!-- Hier wird der Link erzeugt, der in der vierten Phase zum eigentlichen Sprung, und in HTML, umgesetzt wird -->
      <internref>
      	<xsl:attribute name="zielmarke"><xsl:value-of select="@id"/></xsl:attribute>
        <xsl:variable name="title">
          <xsl:apply-templates select="&section;" mode="titel.markup"/>
        </xsl:variable>
        	<relevanz typ="{@relevanz}">
			<xsl:value-of select="$title"/>		<!-- text only -->
		</relevanz>
	</internref>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="reference">
<xsl:param name="Pfad"/>
  <xsl:param name="zones"/>
  <xsl:choose>
    <xsl:when test="contains($zones, ' ')">
      <xsl:variable name="zone" select="substring-before($zones, ' ')"/>
      <xsl:variable name="target" select="key('sections', $zone)"/>

      <a>
        <xsl:attribute name="href">
          <xsl:call-template name="href.target">
            <xsl:with-param name="object" select="$target[1]"/>
            <xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
          </xsl:call-template>
        </xsl:attribute>
        <xsl:apply-templates select="$target[1]" mode="index-title-content"/>
      </a>
      <xsl:text>, </xsl:text>
      <xsl:call-template name="reference">
        <xsl:with-param name="zones" select="substring-after($zones, ' ')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:variable name="zone" select="$zones"/>
      <xsl:variable name="target" select="key('sections', $zone)"/>

      <a>
        <xsl:attribute name="href">
          <xsl:call-template name="href.target">
            <xsl:with-param name="object" select="$target[1]"/>
            <xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
          </xsl:call-template>
        </xsl:attribute>
        <xsl:apply-templates select="$target[1]" mode="index-title-content"/>
      </a>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="indexfix" mode="index-see">
<xsl:param name="Pfad"/>
  <xsl:text> (</xsl:text>
  <xsl:call-template name="gentext">
    <xsl:with-param name="key" select="'siehe'"/>
  </xsl:call-template>
  <xsl:text> '</xsl:text>
  <xsl:value-of select="siehe"/>
  <xsl:text>')</xsl:text>
</xsl:template>

<xsl:template match="indexfix" mode="index-seealso">
<xsl:param name="Pfad"/>
  <xsl:text>(</xsl:text>
  <xsl:call-template name="gentext">
    <xsl:with-param name="key" select="'siehe auch'"/>
  </xsl:call-template>
  <xsl:text> '</xsl:text>
  <xsl:value-of select="sieheauch"/>
  <xsl:text>')</xsl:text>
</xsl:template>

<xsl:template match="*" mode="index-title-content">
<xsl:param name="Pfad"/>
  <xsl:variable name="title">
    <xsl:apply-templates select="&section;"/> <!-- mode="title.markup"/> -->
  </xsl:variable>

  <xsl:value-of select="$title"/>
</xsl:template>

<xsl:template name="href.target">
<xsl:param name="Pfad"/>
  <xsl:param name="context" select="."/>
  <xsl:param name="object" select="."/>
  <!-- <xsl:apply-templates select="$object" mode="getpage">
		<xsl:with-param name="Pfad"><xsl:value-of select="Pfad"/></xsl:with-param>
	</xsl:apply-templates>
  <xsl:text>#</xsl:text> -->
  <xsl:call-template name="object.id">
    <xsl:with-param name="object" select="$object"/>
  </xsl:call-template>
</xsl:template>

<xsl:template name="object.id">
<xsl:param name="Pfad"/>
  <xsl:param name="object" select="."/>
  <xsl:choose>
    <xsl:when test="$object/@id">
      <xsl:value-of select="$object/@id"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="generate-id($object)"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="gentext">
  <xsl:param name="key" select="local-name(.)"/>
      <xsl:value-of select="normalize-space($key)"/>			
</xsl:template>

<xsl:template match="indextitle">
<xsl:param name="contextnode"/>
	<xsl:apply-templates>
		<xsl:with-param name="contextnode" select="$contextnode"/>
	</xsl:apply-templates>
</xsl:template>

<!-- *************************************************** XML->HTML / Phase 4 Funktionen ********************************* -->
<xsl:template match="index">
<xsl:variable name="Bezeichner">
	<xsl:apply-templates select="gruppe/bezeichner" mode="sprungzeile"/>
</xsl:variable>

<div class="index">
<div class="sprungzeile">
<p class="zwischentitel">
	<xsl:value-of select="preceding::abschnitt[1]/titel"/>
</p>
	<a name="oben"/>	<!-- Anker -->
	<xsl:call-template name="Alphabet">
		<xsl:with-param name="str"><xsl:value-of select="&uppercase;"/></xsl:with-param>
		<xsl:with-param name="vgl"><xsl:value-of select="$Bezeichner"/></xsl:with-param>
	</xsl:call-template>
<!--
<xsl:choose>
	<xsl:when test="contains(.,$Bezeichner)">
		<a name="oben"/><a class="{concat('#',.)}"><xsl:value-of select="."/></a>
	</xsl:when>
	<xsl:otherwise>
		<span class="sprungzeile"><xsl:value-of select="."/></span>
	</xsl:otherwise>
</xsl:choose> 
-->
</div>
	<xsl:apply-templates/> <!-- Eigentliche Inhalte Ausgeben -->
</div>
</xsl:template>

<xsl:template name="Alphabet">
	<xsl:param name="str"/>
	<xsl:param name="vgl"/>
	<xsl:choose>
		<xsl:when test="contains($vgl,substring($str,1,1))">
			<a href="{concat('#',substring($str,1,1))}" class="sprungziele"><xsl:value-of select="substring($str,1,1)"/></a>
		</xsl:when>
		<xsl:otherwise>
			<span class="sprungziele"><xsl:value-of select="substring($str,1,1)"/></span>
		</xsl:otherwise>
	</xsl:choose>

	<xsl:if test="string-length($str) &gt; 0">
		<xsl:call-template name="Alphabet">
			<xsl:with-param name="str"><xsl:value-of select="substring($str,2)"/></xsl:with-param>
			<xsl:with-param name="vgl"><xsl:value-of select="$vgl"/></xsl:with-param>
		</xsl:call-template>
	</xsl:if>
	
</xsl:template>

<xsl:template match="bezeichner" mode="sprungzeile">
	<xsl:apply-templates/>
</xsl:template>

<xsl:template match="gruppe">
<div class="indexdiv">
	<xsl:apply-templates/>
</div>
</xsl:template>

<xsl:template match="bezeichner">
<p class="absatz-ohne">
	<xsl:apply-templates/>
</p>
<xsl:if test="string-length(.) &gt; 0">
	<a id="{.}"/>
	<p class="nachobenpikto"><a href="#oben"/></p>
</xsl:if>
</xsl:template>

<xsl:template match="eintraege">
	<xsl:apply-templates/>
</xsl:template>

<xsl:template match="subeintrag">
<xsl:choose>
	<xsl:when test="@gruppe='primaereintrag'">
		<div class="indexeintrag">
			<p class="{@gruppe}">
			<xsl:apply-templates/>
			</p>
		</div>
	</xsl:when>
	<xsl:otherwise>
			<p class="{@gruppe}">
				<xsl:apply-templates/>
			</p>
	</xsl:otherwise>
</xsl:choose>
</xsl:template>

<xsl:template match="relevanz">
	<span class="{@typ}">
<!--		<xsl:apply-templates/> -->
	<xsl:number count="internref"  from="subeintrag" level="single"/>
	</span>
</xsl:template>

</xsl:stylesheet>
