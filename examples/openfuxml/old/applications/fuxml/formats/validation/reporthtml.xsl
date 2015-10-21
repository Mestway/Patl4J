<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xhtml" indent="yes" encoding="ISO-8859-1"/>
	
	
	<xsl:template match="report">
		<html>
		   <head>
			<link rel="stylesheet" type="text/css" href="html.css" />
			<link rel="stylesheet" type="text/css" href="report.css" />
		      <title>Validation Report</title>
		   </head>
		   <body>
			<!--div id="leiste3">
				<div id="navi1">
					<p class="navi1">
						<a class="navi1" href="../../../inhalt.html">Inhaltsübersicht</a>
						<a class="navi1" href="../../../ke/ke1/html/khindex.html">Index</a>
					</p>
					<p class="lernumgebung">
						<a class="navi1" href="http://www.fernuni-hagen.de/ZFE">Lernumgebung</a>
					</p>
					<p class="schalt">
						<a href="../../../ke/ke1/html/d0e1168.html"><img src="../../../images/back.gif" alt="zum vorherigen Abschnitt" width="20px" height="15px" /></a>
						<a href="../../../ke/ke1/html/d0e1185.html"><img src="../../../images/top.gif" alt="zum Seitenanfang" width="15px" height="20px" /></a>
						<a href="../../../ke/ke1/html/d0e1203.html"><img src="../../../images/forward.gif" alt="zum nächsten Abschnitt" width="20px" height="15px" /></a>
					</p>
				</div>
			</div-->
			<!--div id="lehrinhaltbox"-->
			<div id="lehrinhalt">
			<div class="abschnitt"><h2 class="titel-ohne">Validierungsbericht</h2></div>
			<p>Dokument: <xsl:value-of select="@document"/></p>
			<p>Wurzelelement: <xsl:value-of select="@rootelement"/></p>
			<p><xsl:if test="@recursive='true'">Externe Dokumente werden mit überprüft.</xsl:if></p>
			
			<xsl:apply-templates select="failures"/>
			<xsl:apply-templates select="referenceddocuments"/>
			<xsl:apply-templates select="referencedresources"/>
		
			</div><!--/div-->
		   </body>
		</html>

	</xsl:template>
	
	
	<xsl:template match="referenceddocuments[referenceddocument]">
		<div class="abschnitt"><h3 class="titel-ohne">Externe Dokumente</h3></div>
		<div class="tabelle-alt">
		<table>
			<thead>
				<tr>
					<th>Referenzierte Datei</th>
					<th>Kontext-Element</th>
					<th>XPath</th>
					<th>Ausgangsdatei</th>
					<th>Datei existiert?</th>
				</tr>
			</thead>
			<tbody>
				<xsl:apply-templates/>	
			</tbody>
		</table>
		</div>
	</xsl:template>
	
	<xsl:template match="referencedresources[referencedresource]">
		<div class="abschnitt"><h3 class="titel-ohne">Referenzierte Medien</h3></div>
		<div class="tabelle-alt">
		<table>
			<thead>
				<tr>
					<th>Referenzierte Datei</th>
					<th>Kontext-Element</th>
					<th>XPath</th>
					<th>Ausgangsdatei</th>
					<th>Datei existiert?</th>
				</tr>
			</thead>
			<tbody>
				<xsl:apply-templates/>	
			</tbody>
		</table>
		</div>
	</xsl:template>
	
	<xsl:template match="failures[failure]">
		<div class="abschnitt"><h3 class="titel-ohne">Probleme</h3></div>
		<div class="tabelle-alt">
		<table>
			<thead>
				<tr>
					<th>Fehlertyp</th>
					<th>Kontext-Element</th>
					<th>Fehlerstelle</th>
					<th>Ausgangsdatei</th>
					<th>Fehlermeldung</th>
					<th>Hinweis</th>
				</tr>
			</thead>
			<tbody>
				<xsl:apply-templates/>	
			</tbody>
		</table>
		</div>
	</xsl:template>

	

	
	<xsl:template match="referenceddocument|referencedresource|failure">
		<tr><xsl:apply-templates select="@*|*"/></tr>
	</xsl:template>
	
	
	<xsl:template match="referenceddocument[@available[.='false']]|referencedresource[@available[.='false']]">
		<tr style="color:red"><xsl:apply-templates select="@*|*"/></tr>
	</xsl:template>

	
	<xsl:template match="failure[@linenumber]">
		<tr>
			<xsl:apply-templates select="@type"/>
			<td />
			<td>Zeile: <xsl:value-of select="@linenumber"/>, Spalte: <xsl:value-of select="@columnnumber"/></td>
			<xsl:apply-templates select="@file"/>
			<xsl:apply-templates select="message"/>
			<xsl:apply-templates select="hint"/>
		</tr>
	</xsl:template>

		
	<xsl:template match="@fileref|@element|@xpath|@file|@available|@type|message|hint">
		<td><xsl:value-of select="."/></td>
	</xsl:template>
	
	<xsl:template match="@available[.='false']">
		<td><xsl:value-of select="."/></td>
	</xsl:template>

	
	<xsl:template match="message[parent::failure/@element[.='formel/formel-imtext']]">
		<td><pre><xsl:value-of select="."/></pre></td>
	</xsl:template>

	<xsl:template match="@id"/>
</xsl:stylesheet>
