<?xml version='1.0'?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version='2.0'>

<xsl:variable name="korrekturhinweise">an</xsl:variable>
<xsl:variable name="loesungshinweise">an</xsl:variable>
<xsl:variable name="loesungen">an</xsl:variable>

<xsl:template match="einsendeaufgabe">																																								<!-- EINSENDEAUFGABE -->
<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
<xsl:variable name="CSSPfadname"><xsl:value-of select="$CSSPfad"/><xsl:value-of select="$cssdateiname"/></xsl:variable>
<xsl:variable name="Pfad"><xsl:call-template name="bauePfadName"/></xsl:variable>
<xsl:message><xsl:value-of select="$CSSPfad"/></xsl:message>
<xsl:if test="$Pfad">
<xsl:result-document href="{$Pfad}">
	<html><head><title>Einsendeaufgabe</title>
	<link rel="stylesheet" type="text/css"><xsl:attribute name="href"><xsl:value-of select="$CSSPfadname"/></xsl:attribute></link></head>
	<body>
	<div id="kopf">
	<img class="left" src="{concat($CSSPfad,concat($medienpfad,'design/fuhlogo.jpg'))}" width="185" height="36" />
	<p class="kopftitel"><img src="{concat($CSSPfad,concat($medienpfad,'design/kopftitel.jpg'))}" width="502" height="36" /></p>
	<p class="ktitel"><xsl:call-template name="Kapitelname"/></p>
	</div>
	<div id="lehrtext">
	<div>
	<xsl:call-template name="standardattribute"/>
		<p>
		<xsl:call-template name="standardattribute"/>
		<xsl:value-of select="$einsendeaufgabe_bezeichner"/>
		<xsl:value-of select="@number"/>
			<xsl:if test="child::zwischentitel">
				<xsl:text> (</xsl:text><xsl:value-of select="child::zwischentitel"/><xsl:text>)</xsl:text>
			</xsl:if>
			<xsl:if test="@punkte">
			<span  class="marginalie"><xsl:text>(</xsl:text><xsl:value-of select="@punkte"/> Punkte<xsl:text>)</xsl:text></span>
			</xsl:if>
		</p>
		<xsl:apply-templates select="aufgabenstellung" mode="main"/>
	</div>
	</div>
	<div id="navi">
	<img class="left" vspace="20px" src="{concat($CSSPfad,concat($medienpfad,'design/logo-lg.jpg'))}" alt="zum Lehrgebiet"/>
		<xsl:call-template name="navigation"/>
	</div>
	</body>
	</html>
</xsl:result-document>
</xsl:if>
</xsl:template>

<xsl:template match="selbsttestaufgabe">																																							<!-- SELBSTTESTAUFGABE-->
<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
<div>
	<xsl:call-template name="standardattribute"/>
		<p>
		<xsl:call-template name="standardattribute"/>
		<xsl:call-template name="standardattribute"/>
		<xsl:value-of select="$selbsttestaufgabe_bezeichner"/>
		<xsl:value-of select="@number"/>
			<xsl:if test="child::zwischentitel">
				<xsl:text> (</xsl:text><xsl:value-of select="child::zwischentitel"/><xsl:text>)</xsl:text>
			</xsl:if>
		</p>
		<xsl:apply-templates select="aufgabenstellung" mode="main"/>
		<xsl:call-template name="navAufgabe"/>
</div>
</xsl:template>

<xsl:template match="uebungsaufgabe">																																								<!-- ÜBUNGSAUFGABE -->
<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
<div>
	<xsl:call-template name="standardattribute"/>
		<p>
		<xsl:call-template name="standardattribute"/>
		<xsl:call-template name="standardattribute"/>
		<xsl:value-of select="$uebungsaufgabe_bezeichner"/>
		<xsl:value-of select="@number"/>
			<xsl:if test="child::zwischentitel">
				<xsl:text> (</xsl:text><xsl:value-of select="child::zwischentitel"/><xsl:text>)</xsl:text>
			</xsl:if>
		</p>
		<xsl:apply-templates select="aufgabenstellung" mode="main"/>
		<xsl:call-template name="navAufgabe"/>
</div>
</xsl:template>

																						<!-- Zulieferdienste: Erzeugen der Nummern-->

<xsl:template name="snummer">
		<xsl:number format="1" from="abschnitt" count="selbsttestaufgabe" level="any"/>
</xsl:template>

<xsl:template name="unummer">
		<xsl:number format="1" from="abschnitt" count="uebungsaufgabe" level="any"/>		
</xsl:template>

<xsl:template name="enummer">
		<xsl:number format="1" from="abschnitt" count="einsendeaufgabe" level="any"/>
</xsl:template>

<xsl:template match="*" mode="getaufgabenpfade">
<xsl:param  name="Pfad"/>
	<xsl:call-template name="bauePfadName">
			<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
	</xsl:call-template>
</xsl:template>

<xsl:template name="bauePfadName">
<xsl:param  name="Pfad"/>
<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
<xsl:value-of select="$Pfad"/>
	<!-- grobe Einteilung des Pfads-->
	<xsl:choose>
<!--		<xsl:when test="ancestor-or-self::einsendeaufgabe">
				<xsl:value-of select="ancestor::kurseinheit/@number"/>
		</xsl:when>-->
		<xsl:when test="ancestor-or-self::selbsttestaufgabe">
			<xsl:call-template name="Pfad"/>
			<xsl:value-of select="normalize-space($selbsttestaufgabe_bezeichner)"/>
		</xsl:when>
		<xsl:when test="ancestor-or-self::uebungsaufgabe">
			<xsl:call-template name="Pfad"/>
			<xsl:value-of select="normalize-space($uebungsaufgabe_bezeichner)"/>
		</xsl:when>
	</xsl:choose>
	<!-- Um welches Unterverzeichnis handelt es sich? -->
	<xsl:choose>
		<xsl:when test="self::einsendeaufgabe/aufgabenstellung">
			<xsl:text>ea/ea</xsl:text><xsl:value-of select="ancestor::kurseinheit/@number"/><xsl:text>/</xsl:text>
		</xsl:when>
		<xsl:when test="self::einsendeaufgaben/aufgabenloesung">
			<xsl:text>ml/ml</xsl:text><xsl:value-of select="ancestor::kurseinheit/@number"/><xsl:text>/</xsl:text>
		</xsl:when>
		<xsl:when test="self::aufgabenloesung">
			<xsl:text>/Loesungen/</xsl:text>				
		</xsl:when>
		<xsl:when test="self::korrekturhinweis">
			<xsl:text>/Korrekturhinweise/</xsl:text>			
		</xsl:when>
		<xsl:when test="self::loesungshinweis">
			<xsl:text>/Loesungshinweise/</xsl:text>			
		</xsl:when>
	</xsl:choose>
	<!-- Dateinamen erzeugen -->
	<xsl:choose>
		<xsl:when test="self::aufgabenstellung">
		<xsl:variable name="myPfad"><xsl:value-of select="$CSSPfad"/><xsl:apply-templates select="ancestor-or-self::selbsttestaufgabe[1]|ancestor-or-self::uebungsaufgabe[1]" mode="getpage"/></xsl:variable>
		<xsl:value-of select="$myPfad"/><xsl:text>#</xsl:text><xsl:value-of select="ancestor-or-self::einsendeaufgabe/@id | ancestor-or-self::selbsttestaufgabe/@id | ancestor-or-self::uebungsaufgabe/@id"/>
		</xsl:when>
		<xsl:when test="self::einsendeaufgabe/aufgabenstellung">
			<xsl:text>aufgabe</xsl:text><xsl:number count="aufgabenstellung" level="any"/><xsl:text>.html</xsl:text>
		</xsl:when>
		<xsl:when test="self::aufgabenloesung">
			<xsl:text>loesung</xsl:text><xsl:number count="aufgabenloesung" level="any"/><xsl:text>.html</xsl:text>
		</xsl:when>
		<xsl:when test="self::korrekturhinweis">
			<xsl:text>khinweis</xsl:text><xsl:number count="korrekturhinweis" level="any"/><xsl:text>.html</xsl:text>
		</xsl:when>
		<xsl:when test="self::loesungshinweis">
			<xsl:text>lhinweis</xsl:text><xsl:number count="loesungshinweis" level="any"/><xsl:text>.html</xsl:text>
		</xsl:when>
	</xsl:choose>
</xsl:template>

<xsl:template match="aufgabenstellung">
	<xsl:apply-templates/>
</xsl:template>

<xsl:template match="aufgabenloesung">
	<xsl:apply-templates/>
</xsl:template>

<!--xsl:template match="loesungshinweis">
	<xsl:apply-templates/>
</xsl:template -->

<xsl:template match="korrekturhinweis">
	<xsl:apply-templates/>
</xsl:template>


<xsl:template match="aufgabenstellung" mode="main">
<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
<xsl:variable name="CSSPfadname"><xsl:value-of select="$CSSPfad"/><xsl:value-of select="$cssdateiname"/></xsl:variable>
<xsl:variable name="Pfad"><xsl:call-template name="bauePfadName"/></xsl:variable>
	<div>
		<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates select="preceding-sibling::zwischentitel|following-sibling::zwischentitel"/>
			<span class="marginalie" >
				<xsl:if test="parent::*/@punkte">
					<xsl:text>(</xsl:text><xsl:value-of select="parent::*/@punkte"/> Punkte<xsl:text>)</xsl:text>
				</xsl:if>
			</span>
			<xsl:apply-templates/>
		<table>
		<xsl:apply-templates select="preceding-sibling::*[not(self::zwischentitel)]|following-sibling::*[not(self::zwischentitel)]" mode="main"/>
		</table>
	</div>
</xsl:template>

<xsl:template match="aufgabenloesung" mode="main">
<xsl:if test="contains($loesungen,'an')">		<!-- Nur Lösungen erzeugen, wenn dies gewünscht wird -->
<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
<xsl:variable name="CSSPfadname"><xsl:value-of select="$CSSPfad"/><xsl:value-of select="$cssdateiname"/></xsl:variable>
<xsl:variable name="Pfad"><xsl:call-template name="bauePfadName"/></xsl:variable>
<xsl:result-document href="{$Pfad}">
	<html><head><title>Aufgabenlösung</title>
	<link rel="stylesheet" type="text/css"><xsl:attribute name="href"><xsl:value-of select="$CSSPfadname"/></xsl:attribute></link></head>
	<body>
	<div id="kopf">
	<img class="left" src="{concat($CSSPfad,concat($medienpfad,'design/fuhlogo.jpg'))}" width="185" height="36" />
	<p class="kopftitel"><img src="{concat($CSSPfad,concat($medienpfad,'design/kopftitel.jpg'))}" width="502" height="36" /></p>
	<p class="ktitel"><xsl:call-template name="Kapitelname"/></p>
	</div>
	<div id="lehrtext">
		<xsl:call-template name="standardattribute"/>
		<xsl:apply-templates mode="main"/>
	</div>
	<xsl:call-template name="navLoesung">
			<xsl:with-param name="Pfad"><xsl:value-of select="$CSSPfad"/></xsl:with-param>
	</xsl:call-template>
	<div id="navi">
	<img class="left" vspace="20px" src="{concat($CSSPfad,concat($medienpfad,'design/logo-lg.jpg'))}" alt="zum Lehrgebiet"/>
		<xsl:call-template name="navigation"/>
	</div>
	</body>
	</html>
</xsl:result-document>	
</xsl:if>
</xsl:template>

<xsl:template match="loesungshinweis" mode="main">
<xsl:if test="contains($loesungshinweise,'an')">		<!-- Nur Lösungshinweise erzeugen, wenn dies gewünscht wird -->
<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
<xsl:variable name="CSSPfadname"><xsl:value-of select="$CSSPfad"/><xsl:value-of select="$cssdateiname"/></xsl:variable>
<xsl:variable name="Pfad"><xsl:call-template name="bauePfadName"/></xsl:variable>
<xsl:result-document href="{$Pfad}">
	<html><head><title>Lösungshinweis</title>
	<link rel="stylesheet" type="text/css"><xsl:attribute name="href"><xsl:value-of select="$CSSPfadname"/></xsl:attribute></link></head>
	<body>
	<div id="kopf">
	<img class="left" src="{concat($CSSPfad,concat($medienpfad,'design/fuhlogo.jpg'))}" width="185" height="36" />
	<p class="kopftitel"><img src="{concat($CSSPfad,concat($medienpfad,'design/kopftitel.jpg'))}" width="502" height="36" /></p>
	<p class="ktitel"><xsl:call-template name="Kapitelname"/></p>
	</div>
	<div id="lehrtext">
		<xsl:call-template name="standardattribute"/>
		<xsl:apply-templates/>
	</div>
	<xsl:call-template name="navLh">
			<xsl:with-param name="Pfad"><xsl:value-of select="$CSSPfad"/></xsl:with-param>
	</xsl:call-template>
	<div id="navi">
	<img class="left" vspace="20px" src="{concat($CSSPfad,concat($medienpfad,'design/logo-lg.jpg'))}" alt="zum Lehrgebiet"/>
		<xsl:call-template name="navigation"/>
	</div>
	</body>
	</html>
</xsl:result-document>	
</xsl:if>
</xsl:template>

<xsl:template match="korrekturhinweis" mode="main">
<xsl:if test="contains($korrekturhinweise,'an')">		<!-- Nur Korrekturhinweise erzeugen, wenn dies gewünscht wird -->
<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
<xsl:variable name="CSSPfadname"><xsl:value-of select="$CSSPfad"/><xsl:value-of select="$cssdateiname"/></xsl:variable>
<xsl:variable name="Pfad"><xsl:call-template name="bauePfadName"/></xsl:variable>
<xsl:result-document href="{$Pfad}">
	<html><head><title>Korrekturhinweis</title>
	<link rel="stylesheet" type="text/css"><xsl:attribute name="href"><xsl:value-of select="$CSSPfadname"/></xsl:attribute></link></head>
	<body>
	<div id="kopf">
	<img class="left" src="{concat($CSSPfad,concat($medienpfad,'design/fuhlogo.jpg'))}" width="185" height="36" />
	<p class="kopftitel"><img src="{concat($CSSPfad,concat($medienpfad,'design/kopftitel.jpg'))}" width="502" height="36" /></p>
	<p class="ktitel"><xsl:call-template name="Kapitelname"/></p>
	</div>
	<div id="lehrtext">
		<xsl:call-template name="standardattribute"/>
		<xsl:apply-templates/>
	</div>
	<xsl:call-template name="navKh">
		<xsl:with-param name="Pfad"><xsl:value-of select="$CSSPfad"/></xsl:with-param>
	</xsl:call-template>
	<div id="navi">
	<img class="left" vspace="20px" src="{concat($CSSPfad,concat($medienpfad,'design/logo-lg.jpg'))}" alt="zum Lehrgebiet"/>
		<xsl:call-template name="navigation"/>
	</div>
	</body>
	</html>
</xsl:result-document>
</xsl:if>
</xsl:template>

<xsl:template match="unteraufgabe" mode="main">
<tr>
<div>
	<xsl:call-template name="standardattribute"/>
		<xsl:apply-templates mode="uaufgabe"/>
</div>
</tr>
</xsl:template>

<xsl:template match="zwischentitel" mode="main">
	<xsl:apply-templates/>
</xsl:template>

<xsl:template match="aufgabenstellung" mode="uaufgabe">
<td valign="top">
	<p><xsl:apply-templates select="parent::*/@number"/></p>
</td>
<td width="10"></td>
<td valign="top">
<div>
	<xsl:call-template name="standardattribute"/>
	<xsl:apply-templates/>
</div>
</td>
<td valign="bottom">
	<p><xsl:call-template name="zUL"/></p>
</td>
</xsl:template>

<xsl:template match="aufgabenloesung" mode="uaufgabe">
<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
<xsl:variable name="CSSPfadname"><xsl:value-of select="$CSSPfad"/><xsl:value-of select="$cssdateiname"/></xsl:variable>
<xsl:variable name="Pfad"><xsl:call-template name="bauePfadName"/></xsl:variable>
<xsl:result-document href="{$Pfad}">
	<html><head><title>Aufgabenlösung</title>
	<link rel="stylesheet" type="text/css"><xsl:attribute name="href"><xsl:value-of select="$CSSPfadname"/></xsl:attribute></link></head>
	<body>
	<div id="kopf">
	<img class="left" src="{concat($CSSPfad,concat($medienpfad,'design/fuhlogo.jpg'))}" width="185" height="36" />
	<p class="kopftitel"><img src="{concat($CSSPfad,concat($medienpfad,'design/kopftitel.jpg'))}" width="502" height="36" /></p>
	<p class="ktitel"><xsl:call-template name="Kapitelname"/></p>
	</div>
	<div id="lehrtext">
		<div>
		<xsl:call-template name="standardattribute"/>
		<xsl:apply-templates/>
		</div>
	</div>
	<xsl:call-template name="navLoesung">
			<xsl:with-param name="Pfad"><xsl:value-of select="$CSSPfad"/></xsl:with-param>
	</xsl:call-template>
	<div id="navi">
	<img class="left" vspace="20px" src="{concat($CSSPfad,concat($medienpfad,'design/logo-lg.jpg'))}" alt="zum Lehrgebiet"/>
		<xsl:call-template name="navigation"/>
	</div>
	</body>
	</html>
</xsl:result-document>	
</xsl:template>

<xsl:template match="loesungshinweis" mode="uaufgabe">
<xsl:if test="contains($loesungshinweise,'an')">		<!-- Nur Lösungshinweise erzeugen, wenn dies gewünscht wird -->
<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
<xsl:variable name="CSSPfadname"><xsl:value-of select="$CSSPfad"/><xsl:value-of select="$cssdateiname"/></xsl:variable>
<xsl:variable name="Pfad"><xsl:call-template name="bauePfadName"/></xsl:variable>
<xsl:result-document href="{$Pfad}">
	<html><head><title>Lösungshinweis</title>
	<link rel="stylesheet" type="text/css"><xsl:attribute name="href"><xsl:value-of select="$CSSPfadname"/></xsl:attribute></link></head>
	<body>
	<div id="kopf">
	<img class="left" src="{concat($CSSPfad,concat($medienpfad,'design/fuhlogo.jpg'))}" width="185" height="36" />
	<p class="kopftitel"><img src="{concat($CSSPfad,concat($medienpfad,'design/kopftitel.jpg'))}" width="502" height="36" /></p>
	<p class="ktitel"><xsl:call-template name="Kapitelname"/></p>
	</div>
	<div id="lehrtext">
		<div>
		<xsl:call-template name="standardattribute"/>
		<xsl:apply-templates/>
		</div>
	</div>
	<xsl:call-template name="navLh">
		<xsl:with-param name="Pfad"><xsl:value-of select="$CSSPfad"/></xsl:with-param>
	</xsl:call-template>
	<div id="navi">
	<img class="left" vspace="20px" src="{concat($CSSPfad,concat($medienpfad,'design/logo-lg.jpg'))}" alt="zum Lehrgebiet"/>
		<xsl:call-template name="navigation"/>
	</div>
	</body>
	</html>
</xsl:result-document>
</xsl:if>
</xsl:template>

<xsl:template match="korrekturhinweis" mode="uaufgabe">
<xsl:if test="contains($korrekturhinweise,'an')">		<!-- Nur Korrekturhinweise erzeugen, wenn dies gewünscht wird -->
<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
<xsl:variable name="CSSPfadname"><xsl:value-of select="$CSSPfad"/><xsl:value-of select="$cssdateiname"/></xsl:variable>
<xsl:variable name="Pfad"><xsl:call-template name="bauePfadName"/></xsl:variable>
<xsl:result-document href="{$Pfad}">
	<html><head><title>Korrekturhinweis</title>
	<link rel="stylesheet" type="text/css"><xsl:attribute name="href"><xsl:value-of select="$CSSPfadname"/></xsl:attribute></link></head>
	<body>
 	<div id="kopf">
 	<img class="left" src="{concat($CSSPfad,concat($medienpfad,'design/fuhlogo.jpg'))}" width="185" height="36" />
	<p class="kopftitel"><img src="{concat($CSSPfad,concat($medienpfad,'design/kopftitel.jpg'))}" width="502" height="36" /></p>
	<p class="ktitel"><xsl:call-template name="Kapitelname"/></p>
	</div>
	<div id="lehrtext">
		<div>
		<xsl:call-template name="standardattribute"/>
		<xsl:apply-templates/>
		</div>
	</div>
	<xsl:call-template name="navKh">
		<xsl:with-param name="Pfad"><xsl:value-of select="$CSSPfad"/></xsl:with-param>
	</xsl:call-template>
	<div id="navi">
	<img class="left" vspace="20px" src="{concat($CSSPfad,concat($medienpfad,'design/logo-lg.jpg'))}" alt="zum Lehrgebiet"/>
		<xsl:call-template name="navigation"/>
	</div>
	</body>
	</html>
</xsl:result-document>
</xsl:if>
</xsl:template>

<xsl:template match="zwischentitel" mode="uaufgabe">
	<xsl:apply-templates/>
</xsl:template>


<xsl:template name="navAufgabe">
<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
		<xsl:if test="contains($loesungshinweise,'an')"> 			<!-- Nur Verlinkungen erzeugen, wenn dies gewünscht wird -->
			<xsl:if test="following-sibling::loesungshinweis|preceding-sibling::loesungshinweis"><xsl:call-template name="zLH"/></xsl:if>
		</xsl:if>
			<a>
			<xsl:attribute name="target"><xsl:value-of select="$loesungentarget"/></xsl:attribute>
			<xsl:attribute name="href">
			<xsl:apply-templates select="aufgabenloesung" mode="getaufgabenpfade">
				<xsl:with-param name="Pfad"><xsl:value-of select="$CSSPfad"/></xsl:with-param>
			</xsl:apply-templates>
			</xsl:attribute>
			<p style="color: #50AA50">Zur Lösung</p>
			</a>
</xsl:template>

<xsl:template name="navLoesung">
<xsl:param  name="Pfad"/>
		<xsl:if test="contains($korrekturhinweise,'an')"> 			<!-- Nur Verlinkungen erzeugen, wenn dies gewünscht wird -->
			<xsl:if test="following-sibling::korrekturhinweis|preceding-sibling::korrekturhinweis"><xsl:call-template name="zKH"/></xsl:if>
		</xsl:if>
		<xsl:call-template name="zzK"/>
</xsl:template>

<xsl:template name="navLh">
<xsl:param  name="Pfad"/>
	<xsl:call-template name="zzK"/>
</xsl:template>

<xsl:template name="navKh">
<xsl:param  name="Pfad"/>
	<xsl:call-template name="zAL"/>
	<xsl:call-template name="zzK"/>
</xsl:template>

<xsl:template name="zzK">	<!-- zurück zum Kurs-->
<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
<xsl:variable name="myPfad"><xsl:value-of select="$CSSPfad"/><xsl:apply-templates select="ancestor-or-self::einsendeaufgabe[1]|ancestor-or-self::selbsttestaufgabe[1]|ancestor-or-self::uebungsaufgabe[1]" mode="getpage"/></xsl:variable>
	<a>
	<xsl:attribute name="target"><xsl:value-of select="$haupttarget"/></xsl:attribute>
	<xsl:attribute name="href"><xsl:value-of select="$myPfad"/><xsl:text>#</xsl:text><xsl:value-of select="ancestor-or-self::einsendeaufgabe/@id | ancestor-or-self::selbsttestaufgabe/@id | ancestor-or-self::uebungsaufgabe/@id"/>
	</xsl:attribute>
	zurück zum Kurs
	</a>
</xsl:template>
<xsl:template name="zLH"> <!-- zLH : zum Lösungshinweis-->
<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
<xsl:variable name="Pfad"><xsl:value-of select="$CSSPfad"/><xsl:apply-templates select="following-sibling::loesungshinweis" mode="getaufgabenpfade"/></xsl:variable>
	<a>
	<xsl:attribute name="target"><xsl:value-of select="$haupttarget"/></xsl:attribute>
	<xsl:attribute name="href"><xsl:value-of select="$Pfad"/>
	</xsl:attribute>
		Lösungshinweis
	</a>
</xsl:template>
<xsl:template name="zAL"> <!-- zAL : zur Aufgabenlösung-->
<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
<xsl:variable name="Pfad"><xsl:value-of select="$CSSPfad"/><xsl:apply-templates select="following-sibling::aufgabenloesung" mode="getaufgabenpfade"/></xsl:variable>
	<a>
	<xsl:attribute name="target"><xsl:value-of select="$haupttarget"/></xsl:attribute>
	<xsl:attribute name="href"><xsl:value-of select="$Pfad"/>
	</xsl:attribute>
		Lösung
	</a>
</xsl:template>
<xsl:template name="zAS"> <!-- zAS : zur Aufgabenstellung-->
<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
<xsl:variable name="Pfad"><xsl:value-of select="$CSSPfad"/><xsl:apply-templates select="ancestor::einsendeaufgabe/aufgabenstellung|ancestor::selbsttestaufgabe/aufgabenstellung|ancestor::uebungsaufgabe/aufgabenstellung" mode="getaufgabenpfade"/></xsl:variable>
	<a>
	<xsl:attribute name="target"><xsl:value-of select="$haupttarget"/></xsl:attribute>
	<xsl:attribute name="href"><xsl:value-of select="$Pfad"/>	</xsl:attribute>
		Zur Aufgabenstellung
	</a>
</xsl:template>
<xsl:template name="zKH"> <!-- zAL : zum Korrekturhinweis-->
<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
<xsl:variable name="Pfad"><xsl:value-of select="$CSSPfad"/><xsl:apply-templates select="following-sibling::korrekturhinweis|preceding-sibling::korrekturhinweis" mode="getaufgabenpfade"/></xsl:variable>
	<a>
	<xsl:attribute name="target"><xsl:value-of select="$haupttarget"/></xsl:attribute>
	<xsl:attribute name="href"><xsl:value-of select="$Pfad"/>
	</xsl:attribute>
		Korrekturhinweis
	</a>
</xsl:template>

<xsl:template name="zUL"> <!-- zUL : zur UnteraufgabenLösung-->
<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
<xsl:variable name="Pfad"><xsl:value-of select="$CSSPfad"/><xsl:apply-templates select="following-sibling::aufgabenloesung|preceding-sibling::aufgabenloesung"  mode="getaufgabenpfade"/>
</xsl:variable>
	<a>
	<xsl:attribute name="target"><xsl:value-of select="$haupttarget"/></xsl:attribute>
	<xsl:attribute name="href"><xsl:value-of select="$Pfad"/>
	</xsl:attribute>
		Lösung
	</a></xsl:template>

<xsl:template name="zurloesung">
<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
	<xsl:call-template name="navAufgabe"/>
</xsl:template>

</xsl:stylesheet>
