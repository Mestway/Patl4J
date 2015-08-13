<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<!-- ********************************************************************
     $Id: common.xsl,v 1.4 2007/01/11 13:38:26 gebhard Exp $
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
     This file contain the design-role for most basic elements
     
     Use in Pass4: XML- to HTML

     ********************************************************************
     Beschreibung:
     Dieses Stylesheet dient der Verarbeitung der meisten Grundelemente,
     zentrale Pfad- und weiterer Funktionen in FuXML.
     
     ********************************************************************
-->

	<!--	Verarbeitung der div. Absatzformate. Sollte ein Absatz als NPR-Teil deklariert sein, so sollten alle 				Unterelemente ebenfalls in einen DIV eingeschlossen werden -->
	<xsl:template match="absatz|absatz-klein|absatz-mini|absatz-ohne">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
	<xsl:choose>
		<xsl:when test="preceding-sibling::abschnitt/@npr-teil !='ja' and @npr-teil='ja'">
		<div class="npr-teil">
			<p>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
			</p>
		</div>
		</xsl:when>
		<xsl:otherwise>
			<p>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
			</p>
		</xsl:otherwise>
	</xsl:choose>
	</xsl:template>
	
	<!-- Spezieller Absatz: Was tun, wenn das Elternelement ein Objekttitel ist ? ... -->
	<xsl:template match="absatz[parent::objekttitel]|absatz-klein[parent::objekttitel]|absatz-mini[parent::objekttitel]|absatz-ohne[parent::objekttitel]">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
	<!-- .. alles in einen span-Element einsetzen -->
		<span>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
		</span>
	</xsl:template>

	<!-- Spezieller Absatz: Was tun, wenn das Element eine Marginalie ist ? ... -->
	<xsl:template match="absatz|absatz-klein|absatz-mini|absatz-ohne" mode="marginalie">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
	<!-- .. alles in einen span-Element einsetzen -->	
		<span>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
		</span>
	</xsl:template>

	<!-- Das Änderungsdatum wird z.Z. nicht ausgewertet! -->
	<xsl:template match="aenderungsdatum">
	</xsl:template>
	
	<!-- Buchstäblich kommt in eine HTML-PRE-Umgebung -->
	<xsl:template match="buchstaeblich">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
	<pre><xsl:call-template name="standardattribute"/><xsl:call-template name="gruppe"/><xsl:apply-templates><xsl:with-param name="contextnode" select="$contextnode"/><xsl:with-param name="Pfad" select="$Pfad"/>	</xsl:apply-templates></pre>
	</xsl:template>

	<!-- Ausdruck wird mittels CSS-formatiert -->
	<xsl:template match="computerausdruck">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
		<div>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
		</div>
	</xsl:template>
	
	<!-- Ausdruck wird "Hart"-formatiert als HTML <b>BOLD</b>-Element -->
	<xsl:template match="fett">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
			<b>
				<xsl:apply-templates select="node()">
					<xsl:with-param name="contextnode" select="$contextnode"/>
					<xsl:with-param name="Pfad" select="$Pfad"/>
				</xsl:apply-templates>
			</b>
	</xsl:template>

	<!-- Der Fussnotenabschnitt wird mittels CSS-formatiert -->
	<xsl:template match="fnabschnitt">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
		<div>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
		</div>
	</xsl:template>
	
	<!-- Hier wird der Link auf eine Fussnote erzeugt -->
	<xsl:template match="fnref">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>	<!-- kann von aussen in die Funktion mitgegeben werden ... -->
	<xsl:variable name="nPfad">	<!-- wenn nicht, dann baue einen eigenen Pfad zusammen -->
	<xsl:choose>
		<xsl:when test="string-length($Pfad) &lt; 1">
			<xsl:call-template name="CSSPfad"/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$Pfad"/>
		</xsl:otherwise>
	</xsl:choose>
	</xsl:variable>
	<a>
		<xsl:attribute name="name">
			<xsl:value-of select="@id"/>
		</xsl:attribute>
		<xsl:attribute name="href">
			<xsl:apply-templates select="key('id_key',@zielmarke)[1]" mode="getpage">
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad"><xsl:value-of select="$nPfad"/></xsl:with-param>
			</xsl:apply-templates>#<xsl:value-of select="@zielmarke"/>
		</xsl:attribute>
		<xsl:attribute name="class">
			<xsl:value-of select="name()"/>
		</xsl:attribute>
		<!-- Hartcodiertes Hochstellen der Fussnotennummer -->
		<sup>(<xsl:value-of select="@number"/>)</sup>
	</a>
	</xsl:template>

	<!-- Ausdruck wird mittels CSS-formatiert -->	
	<xsl:template match="fussnote">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
	<xsl:variable name="nPfad">
	<xsl:choose>
		<xsl:when test="string-length($Pfad) &lt; 1">
			<xsl:call-template name="CSSPfad"/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$Pfad"/>
		</xsl:otherwise>
	</xsl:choose>
	</xsl:variable>
		<div>
			<xsl:call-template name="standardattribute"/>
			<p class="fnnummer">
				<a>
					<xsl:attribute name="href">
					<xsl:apply-templates select="key('id_key',@zielmarke)[1]" mode="getpage">
						<xsl:with-param name="contextnode" select="$contextnode"/>
						<xsl:with-param name="Pfad"><xsl:value-of select="$nPfad"/></xsl:with-param>
					</xsl:apply-templates>#<xsl:value-of select="@zielmarke"/></xsl:attribute>
					<xsl:attribute name="class">
						<xsl:value-of select="name()"/>
					</xsl:attribute>
				<xsl:text>(</xsl:text>
					<xsl:value-of select="key('id_key',@zielmarke)/@number"/>
				<xsl:text>)</xsl:text>
				</a>
			</p>
		<xsl:apply-templates/>
		</div>
	</xsl:template>

	<!-- Ausdruck wird mittels CSS-formatiert (siehe fnref) -->
	<xsl:template match="glossarref">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
	<xsl:variable name="nPfad">
	<xsl:choose>
		<xsl:when test="string-length($Pfad) &lt; 1">
			<xsl:call-template name="CSSPfad"/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$Pfad"/>
		</xsl:otherwise>
	</xsl:choose>
	</xsl:variable>
		<a>
		<xsl:attribute name="href">
			<xsl:apply-templates select="key('id_key',@zielmarke)[1]" mode="getpage">
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad"><xsl:value-of select="$nPfad"/></xsl:with-param>
			</xsl:apply-templates><xsl:value-of select="concat('#',@zielmarke)"/>
		</xsl:attribute>
		<xsl:attribute name="class">
			<xsl:value-of select="name()"/>
		</xsl:attribute>
			<xsl:apply-templates/>
		</a>
	</xsl:template>

	<!-- Ausdruck wird "Hart"-formatiert als HTML <sup>SUPERIOR</sup>-Element -->
	<xsl:template match="hochgestellt">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
		<sup>
			<xsl:apply-templates select="node()">
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
		</sup>
	</xsl:template>

	<!-- Ausdruck wird mittels CSS-formatiert (siehe fnref) -->
	<xsl:template match="internref">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
	<xsl:variable name="nPfad">
	<xsl:choose>
		<xsl:when test="string-length($Pfad) &lt; 1">
			<xsl:call-template name="CSSPfad"/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$Pfad"/>
		</xsl:otherwise>
	</xsl:choose>
	</xsl:variable>
		<a>
		<xsl:attribute name="href">
			<xsl:apply-templates select="key('id_key',@zielmarke)[1]" mode="getpage">
					<xsl:with-param name="contextnode" select="$contextnode"/>
					<xsl:with-param name="Pfad"><xsl:value-of select="$nPfad"/></xsl:with-param>
			</xsl:apply-templates><xsl:value-of select="concat('#',@zielmarke)"/>
		</xsl:attribute>
		<xsl:attribute name="class">
			<xsl:value-of select="name()"/>
		</xsl:attribute>
		<xsl:apply-templates/>
		</a>
	</xsl:template>

	<!-- Ausdrücke werden mittels CSS-formatiert -->
	<xsl:template match="kapitaelchen|aufhebung">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
		<span>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates select="node()">
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
		</span>
	</xsl:template>

	<!-- Ausdruck wird "Hart"-formatiert als HTML <i>ITALIC</i>-Element -->
	<xsl:template match="kursiv">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
		<i>
			<xsl:apply-templates select="node()">
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
		</i>
	</xsl:template>
	
	<!-- Marginalie im normalen Fließtext ausblenden (nicht verarbeiten) -->
	<xsl:template match="marginalie"/>
	
	<!-- Ausdruck wird mittels CSS-formatiert -->
	<xsl:template match="marginalie" mode="margref">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
	<span>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates mode="marginalie">
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
	</span>
	</xsl:template>
<!--	
    <xsl:template match="*" mode="marginalie">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
	<span>
	   <xsl:attribute name="class">
	       <xsl:value-of select="name(.)"/>
	   </xsl:attribute>
			<xsl:apply-templates mode="marginalie">
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
	</span>
	</xsl:template>
-->
	<!-- Alles wird unter Marginale (im <span/>-Element (s.o.) eingesetzt) -->
	<xsl:template match="*" mode="margref">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
		<xsl:apply-templates mode="margref">
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>
	</xsl:template>

	<!-- ??? -->
	<xsl:template match="margref">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
			<xsl:apply-templates select="//*[@id=current()/@zielmarke]" mode="margref">
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
	</xsl:template>

	<!-- Ausdruck wird mittels CSS-formatiert -->
	<xsl:template match="motto">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
		<div>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
		</div>
	</xsl:template>

	<!-- Nur alles Ausführen und sonst durchreichen -->
	<xsl:template match="objekttitel">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
		<xsl:apply-templates>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>
	</xsl:template>

	<!-- Ausdruck wird mittels CSS-formatiert -->
	<xsl:template match="proglist">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
		<div>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates select="$styles/entry[@name=name(current())]/label">
				<xsl:with-param name="contextnode" select="current()"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
			<xsl:apply-templates select="*[name(.)!='zwischentitel']">
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
		</div>
	</xsl:template>

	<!-- Ausdruck wird "Hart"-formatiert als HTML <tt>TRUETYPE</tt>-Element -->
	<xsl:template match="schreibmaschine">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
<tt><xsl:apply-templates><xsl:with-param name="contextnode" select="$contextnode"/><xsl:with-param name="Pfad" select="$Pfad"/></xsl:apply-templates></tt>
	</xsl:template>

	<!-- Ausdruck wird "Hart"-formatiert als HTML <p>PARAGRAPH</p>-Element, später mittels CSS-formatiert-->
	<xsl:template match="stichwort">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
		<p>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
		</p>
	</xsl:template>

	<!-- "Hart"-formatierte DIVS, später mittels CSS-formatiert-->
	<!-- eigentliche Verarbeitung der Tabelle geschieht unter mitwirkung der tabelle.xsl und commontabelle.xsl -->
	<xsl:template match="tabelle|tabelle-alt">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
		<div><!-- Label wird ausgewertet -->
			<xsl:call-template name="standardattribute"/>
			<xsl:if test="zwischentitel/node()|titel/node()">
			<div class="tabtitel">
				<xsl:attribute name="id">
					<xsl:value-of select="titel/@id"/>
				</xsl:attribute>
				<xsl:choose>
					<xsl:when test="contains(titel/@num,'an')">
						<p class="tab-num">
							<xsl:apply-templates select="$styles/entry[@name=name(current())]/numpart">
								<xsl:with-param name="contextnode" select="current()"/>
								<xsl:with-param name="Pfad" select="$Pfad"/>
							</xsl:apply-templates>
							<span class="titel">
								<xsl:apply-templates select="$styles/entry[@name=name(current())]/label//processing-instruction('title')">
									<xsl:with-param name="contextnode" select="current()"/>
									<xsl:with-param name="Pfad" select="$Pfad"/>
								</xsl:apply-templates>
							</span>
						</p>
					</xsl:when>
					<xsl:when test="contains(titel/@num,'aus')">
						<xsl:apply-templates select="$styles/entry[@name=name(current())]/label">
							<xsl:with-param name="contextnode" select="current()"/>
							<xsl:with-param name="Pfad" select="$Pfad"/>
						</xsl:apply-templates>
					</xsl:when>
				</xsl:choose>
			</div>
			</xsl:if>
			<!-- Die eigentliche Tabelle wird von einem anderen Stylesheet eingebaut -->
			<xsl:apply-templates select="tgroup"/>
		</div>
	</xsl:template>

	<!-- Hier wird ein Ankerpunkt im HTML-Dokument gesetzt -->
	<xsl:template match="textmarke">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
		<a>
			<xsl:variable name="name"><xsl:value-of select="@id"/></xsl:variable>
			<xsl:call-template name="standardattribute"/>
		</a>
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
	</xsl:template>

	<!-- Ausdruck wird "Hart"-formatiert als HTML <sub>SUB</sub>-Element -->
	<xsl:template match="tiefgestellt">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
		<sub>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
		</sub>
	</xsl:template>

	<!-- Ausdruck wird "Hart"-formatiert als HTML <span/>-Element, später mittels CSS-formatiert-->
	<xsl:template match="titel|untertitel">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
		<span>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
		</span>
	</xsl:template>

<!-- Titelsonderfaelle -->
	<!-- Die großen Abschnittstitel werden hier verarbeitet, mit - oder ohne Nummer, etc.-->
	<xsl:template match="titel[parent::abschnitt]">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
	<xsl:choose>
		<xsl:when test="contains(@num,'an')">
 			<xsl:element name="{concat('h',parent::*/@level)}">
				<xsl:attribute name="class">
					<xsl:text>kap-num</xsl:text>
				</xsl:attribute>
				<xsl:value-of select="parent::abschnitt/@number"/>
			</xsl:element>
			<!-- Die Abschnitts-Ebene wird hier in ein h1 bis hx-Element umgesetzt -->
 			<xsl:element name="{concat('h',parent::*/@level)}">
				<xsl:call-template name="standardattribute"/>
			<xsl:attribute name="class">
				<xsl:text>kap-titel</xsl:text>
			</xsl:attribute>
				<xsl:apply-templates/>
			</xsl:element>
		</xsl:when>
		<xsl:when test="contains(@num,'aus')">
 			<xsl:element name="{concat('h',parent::*/@level)}">
				<xsl:call-template name="standardattribute"/>
			<xsl:attribute name="class">
				<xsl:text>titel-ohne</xsl:text>
			</xsl:attribute>
				<xsl:apply-templates>
					<xsl:with-param name="contextnode" select="$contextnode"/>
					<xsl:with-param name="Pfad" select="$Pfad"/>
				</xsl:apply-templates>
			</xsl:element>
		</xsl:when>
	</xsl:choose>
	</xsl:template>

<xsl:template match="titel[parent::abschnitt]" mode="titel.markup"><!-- Indexdarstellung-->
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
	<xsl:if test="contains(@num,'an')">
		<xsl:value-of select="parent::abschnitt/@number"/>
		<xsl:text> </xsl:text>
		<xsl:apply-templates>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>
	</xsl:if>
	<xsl:if test="contains(@num,'aus')">
		<xsl:apply-templates>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>
	</xsl:if>
</xsl:template>

<!--<xsl:template match="titel[parent::tabelle|parent::tabelle-alt]">
<xsl:param name="contextnode"/>
<xsl:param name="Pfad"/>
	<xsl:apply-templates>
		<xsl:with-param name="contextnode" select="$contextnode"/>
		<xsl:with-param name="Pfad" select="$Pfad"/>
	</xsl:apply-templates>
</xsl:template> -->
<!-- Titelsonderfaelle - Ende -->

	<!-- Ausdruck wird "Hart"-formatiert als HTML <div/>-Element, später mittels CSS-formatiert-->
	<xsl:template match="universaleintrag">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
	<div>
		<xsl:call-template name="standardattribute"/>
		<xsl:apply-templates>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>
	</div>
	</xsl:template>

	<!-- Ausdruck wird "Hart"-formatiert als HTML <u>UNDERLINE</u>-Element -->
	<xsl:template match="unterstrichen">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
		<u>
			<xsl:apply-templates select="node()">
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
		</u>
	</xsl:template>

	<!-- Ausdruck wird "Hart"-formatiert als HTML <a/>-Element, später mittels CSS-formatiert-->
	<xsl:template match="url">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
		<a href="{@adresse}">
			<xsl:call-template name="id"/>
			<xsl:attribute name="class">
				<xsl:value-of select="name()"/>
			</xsl:attribute>
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
		</a>
	</xsl:template>
	
	<xsl:template match="vorwort|voraussetzungen|autorenvorstellung|anhang|fnabschnitt|glossar|lehrziele|literaturliste|literaturverz|studierhinweise|einsendeaufgaben">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
			<xsl:apply-templates select="node()">
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
	</xsl:template>

	<!-- Funktion für die Verarbeitung des Linkcheckers -->
	<xsl:template match="url" mode="Pfad">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
		<xsl:variable name="Pfadname"><xsl:call-template name="SeitenPfad"/></xsl:variable>
		<xsl:variable name="file" select="preceding::processing-instruction('file')[last()]"/>
		<url>
			<quelle>
				<position><xsl:number count="url" format="1" level="multiple" from="processing-instruction('file')"/></position>
				<datei><xsl:value-of select="$file" /></datei>
			</quelle>
		<htmlposition><xsl:value-of select="$Pfadname"/><xsl:text>#</xsl:text><xsl:value-of select="@id"/></htmlposition>
		<original><xsl:value-of select="@adresse"/></original>
		<redir></redir>
		</url>
	</xsl:template>

	<!-- Ausdruck wird "Hart"-formatiert als HTML <br/>-Element -->
	<xsl:template match="zeilenende">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
		<br/>
	</xsl:template>
	
	<!-- Ausdruck wird "Hart"-formatiert als HTML <div/>-Element, später mittels CSS-formatiert
	<xsl:template match="zusammenfassung">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
	<div>
		<xsl:call-template name="standardattribute"/>
		<xsl:apply-templates>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>
	</div>
	</xsl:template>-->

<!-- Pfadsystem -->

	<!-- Zentrale Pfaderzeugung. Mit Hilfe dieser Funtion werden zahlreiche Templates (am Ende dieses Stylesheets)
		bemüht, um den 	richtigen Pfad auf das gewünschte Element zu bekommen -->
	<xsl:template match="*" mode="getpage">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
		<xsl:call-template name="SeitenPfad">
			<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:call-template>
	</xsl:template>

<xsl:template name="rootPfad">
<!-- 	Dieses Template baut Pfadangaben zum root-Verzeichnis
		Nummerischer Eingabewert: Ebene	(z.b.: 3)
		String Ausgabe: (z.B.: "../../../")
		Getestet: 20.10.03	SG		-->
	<xsl:param name="Ebene"/>
	<xsl:param name="String"/>
	<xsl:param name="contextnode"/>
	<xsl:choose>
		<xsl:when test="$Ebene>0">
		<xsl:call-template name="rootPfad">
			<xsl:with-param name="Ebene" select="$Ebene - 1"/>
			<xsl:with-param name="String" select="concat($String,'../')"/>
		</xsl:call-template>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$String"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template name="SeitenPfad">
<!-- Dieses Template erzeugt den Pfad zu der Seite, auf dem sich das anfragende Objekt befindet -->
<xsl:param name="contextnode"/>
<xsl:param name="Pfad"/>
<xsl:value-of select="$Pfad"/>
<xsl:call-template name="Pfad"/>
	<xsl:apply-templates select="ancestor::file[1]/@filename">
		<xsl:with-param name="contextnode" select="$contextnode"/>
	</xsl:apply-templates>
</xsl:template>

<!-- Hole ienfach nur die Pfadangabe aus dem File-Element -->
<xsl:template name="Pfad">
<xsl:param name="contextnode"/>
	<xsl:apply-templates select="ancestor::file[1]/@directory">
		<xsl:with-param name="contextnode" select="current()"/>
	</xsl:apply-templates>
</xsl:template>

<xsl:template name="CSSPfad">
<xsl:param name="contextnode"/>
<!-- Die folgende Funktion berechnet aus dem Directorypfad die nummerische Variable Ebene (für das Template "rootPfad" -->
<xsl:variable name="NumPfadEbene"><xsl:value-of select="string-length(ancestor::file[1]/@directory) - string-length(translate(ancestor::file[1]/@directory, '/', ''))"/></xsl:variable>
	<xsl:call-template name="rootPfad">
		<xsl:with-param name="Ebene" select="$NumPfadEbene"/>
	</xsl:call-template>
</xsl:template>
	
</xsl:stylesheet>