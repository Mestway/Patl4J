<?xml version="1.0"?>
<!-- edited with XML Spy v4.2 U (http://www.xmlspy.com) by ttdown.com (ttdown.com) -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">

<!-- ********************************************************************
     $Id: xhtml.xsl,v 1.7 2007/04/17 12:58:55 gebhard Exp $
     ********************************************************************

     This file is part of the FuXML_HTML Stylesheet distribution.
     
     Description:
     This file contains the functions to parse the designtemplate and replace processing-instructions through the context
     
     Use in Pass 4:
	
	*******************************************************************
	Beschreibung:
	Diese Datei enthält die Funktionen zum Einparsen der Designtemplates und Ersetzen der darin enthaltenen PIs durch deren Inhalt.
     ********************************************************************
-->
	<!-- Hier werden Metainformationen der Designtemplates kopiert, oder beim Auffinden von PIs durch deren Inhalt ersetzt.-->
	<xsl:template match="meta">
		<xsl:param name="contextnode"/>
		<xsl:param name="filenode" tunnel="yes"/>
		<xsl:variable name="content">
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
		</xsl:variable>
		<meta>
			<xsl:copy-of select="@*"/>
			<xsl:if test="$content!=''">
				<xsl:attribute name="name"><xsl:value-of select="@name"/></xsl:attribute>
				<xsl:attribute name="content"><xsl:value-of select="$content"/></xsl:attribute>
			</xsl:if>
		</meta>
	</xsl:template>

	<!-- Die Titelinformation wird übergeben -->
	<xsl:template match="title">
		<xsl:param name="contextnode"/>
		<xsl:param name="filenode" tunnel="yes"/>
		<title>
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="filenode" select="$filenode"/>
			</xsl:apply-templates>
		</title>
	</xsl:template>

	<xsl:template match="link">
		<xsl:param name="contextnode"/>
		<xsl:param name="filenode" tunnel="yes"/>
		<xsl:param name="Pfad"/>
		<link>
			<xsl:copy-of select="@*"/>
			<xsl:attribute name="href"><xsl:value-of select="concat($Pfad,@href)"/></xsl:attribute>
		</link>
	</xsl:template>
	<!-- Sämtliche Pfade müssen im Template angepasst werden, da z.B. die Verzeichnistiefe variiert. Dazu werden die 
		einzelnen Pfade in diesem Schritt angepasst -->
	<xsl:template match="img">
		<xsl:param name="contextnode"/>
		<xsl:param name="filenode" tunnel="yes"/>
		<xsl:param name="Pfad"/>
		<img>
			<xsl:copy-of select="@*"/>
			<xsl:attribute name="src"><xsl:value-of select="concat($Pfad,@src)"/></xsl:attribute>
		</img>
	</xsl:template>
	
	<xsl:template match="a">
		<xsl:param name="contextnode"/>
		<xsl:param name="filenode" tunnel="yes"/>
		<xsl:param name="content"/>
		<xsl:param name="Pfad"/>
		<a>
			<xsl:copy-of select="@*"/>
			<xsl:if test="@href">
			<xsl:attribute name="href">
				<xsl:choose>
				<xsl:when test="contains(@href,'://')">
					<xsl:value-of select="@href"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="concat($Pfad,@href)"/>	
				</xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
			</xsl:if>
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="filenode" select="$filenode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
				<xsl:with-param name="content" select="$content"/>
			</xsl:apply-templates>
		</a>
	</xsl:template>
	
	<!-- Alle Elemente, für die es keine explizierte Anweisung gibt, werden hier weitergereicht (kopiert) -->
	<xsl:template match="*">
		<xsl:param name="contextnode"/>
		<xsl:param name="filenode" tunnel="yes"/>
		<xsl:param name="mode"/>
		<xsl:param name="Pfad"/>
		<xsl:if test="debug=1">
			<xsl:comment>ohne Anweisung: <xsl:value-of select="name(.)"></xsl:value-of></xsl:comment>
		</xsl:if>			
		<xsl:element name="{name(.)}">
			<xsl:copy-of select="@*"/>
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="filenode" select="$filenode"/>				
				<xsl:with-param name="Pfad" select="$Pfad"/>
				<xsl:with-param name="mode" select="$mode"/>
			</xsl:apply-templates>
		</xsl:element>
	</xsl:template>

    <xsl:template match="comment()">
		<xsl:param name="contextnode"/>
		<xsl:param name="Pfad"/>    
        <xsl:variable name="content"><xsl:value-of select="."/></xsl:variable>
        <xsl:comment>
            <xsl:call-template name="modify">
                <xsl:with-param name="string" select="$content"/>
                <xsl:with-param name="modify">href</xsl:with-param>
                <xsl:with-param name="insert-on-start" select="$Pfad"/>
            </xsl:call-template>
        </xsl:comment>            
    </xsl:template>

	<!-- Immer wenn vor einem Bildchen eine PI steht führe diese Funktions aus !!! -->
	<xsl:template match='img[preceding-sibling::processing-instruction()]'>
		<!-- nichts -->
	</xsl:template>

	<xsl:template match='img[preceding-sibling::processing-instruction("KELogo")]'>
		<xsl:param name="contextnode"/>
		<xsl:param name="Pfad"/>
		<xsl:variable name="src">
			<xsl:apply-templates select="$styles/entry[@name='kurseinheit'][@number=$contextnode/ancestor-or-self::file/@kurseinheit]/structure[@format='html']/file/designtemplate/@KELogo"/>
		</xsl:variable>
		<xsl:variable name="alt">
			<xsl:apply-templates select="$styles/entry[@name='kurseinheit'][@number=$contextnode/ancestor-or-self::file/@kurseinheit]/structure[@format='html']/file/designtemplate/@KELogo_Alttext"/>				</xsl:variable>		
		<xsl:element name="{name(.)}">
			<xsl:copy-of select="@*"/>
			<xsl:choose>
				<xsl:when test="string-length($src) gt 0">
					<xsl:attribute name="src"><xsl:value-of select="concat($Pfad,$src)"/></xsl:attribute>				
					<xsl:attribute name="alt"><xsl:value-of select="$alt"/></xsl:attribute>					
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="src"><xsl:value-of select="concat($Pfad,@src)"/></xsl:attribute>				
				</xsl:otherwise>				
			</xsl:choose>
		</xsl:element>
	</xsl:template>
	
		<xsl:template match='img[preceding-sibling::processing-instruction("KLogo")]'>
		<xsl:param name="contextnode"/>
		<xsl:param name="Pfad"/>
		<xsl:variable name="src">
			<xsl:apply-templates select="$styles/entry[@name='kurs']/structure[@format='html']/file/designtemplate/@KLogo"/>
		</xsl:variable>
		<xsl:variable name="alt">
			<xsl:apply-templates select="$styles/entry[@name='kurs']/structure[@format='html']/file/designtemplate/@KLogo_Alttext"/>				</xsl:variable>		
		<xsl:element name="{name(.)}">
			<xsl:copy-of select="@*"/>
			<xsl:choose>
				<xsl:when test="string-length($src) gt 0">
					<xsl:attribute name="src"><xsl:value-of select="concat($Pfad,$src)"/></xsl:attribute>				
					<xsl:attribute name="alt"><xsl:value-of select="$alt"/></xsl:attribute>					
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="src"><xsl:value-of select="concat($Pfad,@src)"/></xsl:attribute>				
				</xsl:otherwise>				
			</xsl:choose>
		</xsl:element>
	</xsl:template>
	
	<!-- Immer wenn innerhalb eines Bildchen eine PI steht führe diese Funktions aus !!! -->
	<xsl:template match='a[child::processing-instruction()]'>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
	<xsl:param name="Pfad"/>
	<xsl:apply-templates select="processing-instruction()">
		<xsl:with-param name="contextnode" select="$contextnode"/>
		<xsl:with-param name="filenode" select="$filenode"/>
		<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<!-- Baue den Abschnittstitel ein -->
	<xsl:template match="processing-instruction('sectiontitle')">
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
	<xsl:choose>
		<!-- Wenn ein sectiontitle mitgebracht wird, verwende ihn -->
		<xsl:when test="$contextnode/@htmlsectiontitle">
			<xsl:value-of select="$contextnode/@htmlsectiontitle"/>	
		</xsl:when>
		<!-- Wenn das nächste Element ein (Haupt-)Abschnitt/Titel ist, und die Nummerierung auf "an" steht -->
		<xsl:when test="$contextnode/descendant::abschnitt[1]/titel">
			<xsl:if test="contains($contextnode/descendant::abschnitt[1]/titel/@num,'an')">
				<xsl:value-of select="$contextnode/descendant::abschnitt[1]/@number"/>
				<xsl:text> </xsl:text><!-- Ein Hardcodiertes Leerzeichen zwischen der Nummerierung und dem Titel -->
			</xsl:if>
				<!--xsl:value-of select="$contextnode/descendant::abschnitt[1]/titel"/-->
				<xsl:apply-templates select="$contextnode/descendant::abschnitt[1]/titel" mode="ktitel"/>
		</xsl:when>

		<!-- Wenn das nächste Element eine Literaturliste oder ein Fussnotenabschnitt ist, füge den Zwischentitel ein -->
		<xsl:when test="$contextnode/descendant::literaturliste[1]/zwischentitel | $contextnode/descendant::fnabschnitt[1]/zwischentitel">
			<xsl:value-of select="$contextnode/descendant::literaturliste[1]/zwischentitel | $contextnode/descendant::fnabschnitt[1]/zwischentitel"/>
		</xsl:when>
		<!-- Wenn das nächste Element eine Startseite, Impressum oder eine Titelseite ist, füge nichts ein -->
		<xsl:when test="$contextnode/descendant::startpage | $contextnode/descendant::titlepage| $contextnode/descendant::impressum">
			<xsl:text> </xsl:text>
		</xsl:when>
		<xsl:otherwise>
		<!-- Wenn keines der oben aufgeführten Elemente da ist, füge den Titel des letzen bekannten Hauptabschnitts ein -->
			<xsl:if test="contains($contextnode/preceding::abschnitt[1]/titel/@num,'an')">
				<xsl:value-of select="$contextnode/preceding::abschnitt[1]/@number"/>
				<xsl:text> </xsl:text><!-- Ein Hardcodiertes Leerzeichen zwischen der Nummerierung und dem Titel -->
			</xsl:if>
				<!--xsl:value-of select="	$contextnode/preceding::abschnitt[1]/titel"/-->
				<xsl:apply-templates select="$contextnode/preceding::abschnitt[1]/titel" mode="ktitel"/>
		</xsl:otherwise>		
	</xsl:choose>
	</xsl:template>
	
	<xsl:template match="*" mode="ktitel"/>
	<xsl:template match="titel" mode="ktitel">
		<xsl:apply-templates mode="ktitel"/><!-- Verarbeitung des SektionsTitels -->
	</xsl:template>
	<xsl:template match="fett|kursiv|unterstrichen|hochgestellt|tiefgestellt" mode="ktitel">
		<xsl:apply-templates/>
	</xsl:template>
<!--	<xsl:template match="untertitel" mode="ktitel"><span class="kuntertitel"><xsl:value-of select="."/></span> 	</xsl:template> -->
	
	<xsl:template match="processing-instruction('coursetitle')">
		<xsl:param name="contextnode"/>
		<xsl:param name="filenode" tunnel="yes"/>
		<xsl:choose>
			<xsl:when test="$contextnode/ancestor-or-self::document/kurs">
				<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurs/metadata/kurs-titel"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurseinheit[1]/metadata/kurs-titel"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="processing-instruction('cunum')" priority="1">
		<xsl:param name="contextnode"/>
		<xsl:param name="filenode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::file/@kurseinheit"/>
	</xsl:template>

	<xsl:template match="processing-instruction('coursesystemlink')" priority="1">
		<xsl:param name="contextnode"/>
		<xsl:variable name="coursesystemlink">
			<xsl:apply-templates select="$styles/entry[@name='kurs']/structure[@format='html']/file/designtemplate/@coursesystemlink"/>
		</xsl:variable>
		<xsl:if test="$coursesystemlink">
			<xsl:element name="{name(parent::node())}">
			<xsl:copy-of select="parent::*/@*"/>
			<xsl:attribute name="href">
				<xsl:value-of select="$coursesystemlink"/>
			</xsl:attribute>
				<xsl:value-of select="normalize-space(string(parent::*))"/>
			</xsl:element>
		</xsl:if>
	</xsl:template>	
	
	<xsl:template match="processing-instruction('titlepicture')" priority="1">
		<xsl:param name="contextnode"/>
		<xsl:variable name="gettitlepicture">
			<xsl:apply-templates select="$styles/entry[@name='kurs']/structure[@format='html']/file/startpagetemplate/@titlepicture"/>
		</xsl:variable>
		<xsl:variable name="getalttext">
			<xsl:apply-templates select="$styles/entry[@name='kurs']/structure[@format='html']/file/startpagetemplate/@alttext"/>
		</xsl:variable>
			<xsl:element name="{name(parent::node())}">
			<xsl:copy-of select="parent::*/@*"/>
			<xsl:attribute name="href">
				<xsl:value-of select="$gettitlepicture"/>
			</xsl:attribute>
			<xsl:attribute name="alt">
				<xsl:value-of select="$getalttext"/>
			</xsl:attribute>
				<xsl:value-of select="normalize-space(string(parent::*))"/>
			</xsl:element>
	</xsl:template>

	<xsl:template match="processing-instruction('pdfpath')"><!-- Link-PI auf eine PDF-Datei mit Namen keX.pdf, wobei X für die Kurseinheitsnummer steht im Verzeichnis, dass durch den href der Linkanweisung vorgegeben wird -->
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:variable name="cu"><xsl:apply-templates select="$contextnode/ancestor-or-self::file[1]/@kurseinheit"/></xsl:variable>
<!--	<xsl:variable name="kename">
		<xsl:apply-templates select="$styles/entry[@name='kurseinheit']structure[@format='latexpdf']/file/filename"/>
	</xsl:variable> -->
	<xsl:variable name="pdfpfad">
		<xsl:value-of select="$Pfad"/>
		<xsl:value-of select="parent::*/@href"/>
		<xsl:value-of select="concat('ke',normalize-space(string($cu)),'.pdf')"/>
	</xsl:variable>
		<xsl:element name="{name(parent::node())}">
			<xsl:copy-of select="parent::*/@*"/>
		<xsl:attribute name="href">
			<xsl:value-of select="$pdfpfad"/>
		</xsl:attribute>
			<xsl:value-of select="normalize-space(string(parent::*))"/>
		</xsl:element>
	</xsl:template>

	<!-- Hauptfunktion dieses Stylesheets, das einfügen des eigentlichen Inhalts in das Designtemplate -->
	<xsl:template match="processing-instruction('content')">
		<xsl:param name="contextnode"/>
		<xsl:param name="filenode" tunnel="yes"/>
		<xsl:param name="styles"/>
		<xsl:param name="content" tunnel="yes"/>
		<xsl:param name="Pfad"/>
		<xsl:param name="med-width" tunnel="yes"/>
		<xsl:param name="med-height" tunnel="yes"/>
		<xsl:param name="mode"/><!-- for assignments -->
		<xsl:param name="filename" tunnel="yes"/>
		<xsl:param name="mediaelement" tunnel="yes"/>		
		<xsl:param name="encoding" tunnel="yes"/>
		<xsl:param name="backlinkid" tunnel="yes"/>
		<xsl:variable name="this">
			<xsl:apply-templates select="$contextnode/ancestor-or-self::file[1]/@directory"/>
			<xsl:apply-templates select="$contextnode/ancestor-or-self::file[1]/@filename"/>
		</xsl:variable>
		<xsl:variable name="Objektfilename">
			<xsl:call-template name="get.filename">
				<xsl:with-param name="mediaref" select="$filename"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="width">
			<xsl:choose>
				<xsl:when test="$med-width !=''">
					<xsl:value-of select="$med-width"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>760px</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="height">
			<xsl:choose>
				<xsl:when test="$med-height !=''">
					<xsl:value-of select="$med-height"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>570px</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<!-- Spezial-Anpassungen für Sockwave und Flash -->
		<xsl:variable name="objekt-swf">
					<![CDATA[
			<!--[if IE]>
				<style type="text/css">
					.iehidden { display:none; }
				</style>
			<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="]]><xsl:value-of select="$width"/><![CDATA[" height="]]><xsl:value-of select="$height"/><![CDATA[" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0">
				<param name="src" value="]]><xsl:value-of select="$Objektfilename"/><![CDATA[">
			</object>
			<![endif]-->
				]]>
		</xsl:variable>
		<xsl:variable name="objekt-dcr">
			<![CDATA[
			<!--[if IE]>
				<style type="text/css">
					.iehidden { display:none; }
				</style>
			<object classid="clsid:166B1BCA-3F9C-11CF-8075-444553540000" width="]]><xsl:value-of select="$width"/><![CDATA[" height="]]><xsl:value-of select="$height"/><![CDATA[" codebase="http://download.macromedia.com/pub/shockwave/cabs/director/sw.cab#version=8,5,1,0">
				<param name="src" value="]]><xsl:value-of select="$Objektfilename"/><![CDATA[">
			</object>
			<![endif]-->
				]]>
		</xsl:variable>		
		<xsl:choose>
			<!-- Wenn ein filename mitgegeben wird, dann importiere den Inhalt der, mit dem filenamen verknüpften Datei,
				 anstelle von Inhalten aus der QuellXMLdatei. Wird vor allem durch Impressum, Startpage, etc. genutzt.
				 Aber auch die Multimedialen Inhalt wie Shockwave und Flashanimationen, oder ganze HTML oder XML-Dateien
				 können so als "Inhalt" importiert werden -->
			<xsl:when test="string-length($filename) gt 0">
			<xsl:choose> 
				<xsl:when test="contains($filename,'.swf')">
					<object id="{$mediaelement/@id}" type="application/x-shockwave-flash" width="{$width}" height="{$height}" class="iehidden" style="-moz-user-focus:ignore">
					<xsl:attribute name="data"><xsl:value-of select="$Objektfilename"/></xsl:attribute>
					</object>
				<xsl:value-of select="$objekt-swf" disable-output-escaping="yes"/>
				<xsl:element name="a">
					<xsl:attribute name="class">backpikto</xsl:attribute>
					<xsl:attribute name="href">
						<xsl:value-of select="$Pfad"/>
						<xsl:apply-templates select="$this"/>#<xsl:value-of select="$backlinkid"/>
					</xsl:attribute>
				</xsl:element>
				</xsl:when>
				<xsl:when test="contains($filename,'.dcr')">
					<object id="{$mediaelement/@id}" type="application/x-director" width="{$width}" height="{$height}" class="iehidden" style="-moz-user-focus:ignore">
					<xsl:attribute name="data"><xsl:value-of select="$Objektfilename"/></xsl:attribute>
					</object>
				<xsl:value-of select="$objekt-dcr" disable-output-escaping="yes"/>
				<xsl:element name="a">
					<xsl:attribute name="class">backpikto</xsl:attribute>
					<xsl:attribute name="href">
						<xsl:value-of select="$Pfad"/>
						<xsl:apply-templates select="$this"/>#<xsl:value-of select="$backlinkid"/>
					</xsl:attribute>
				</xsl:element>
				</xsl:when>
				<xsl:otherwise>
				<!-- An dieser Stelle wird ein SubHTML oder sonstige Datei als Textdatei in das Designtemplate "einkopiert" .
					Die dadurch entstandene Webseite im Kurs ist dann in der Regel nicht mehr W3C-Konform, funktioniert
					aber praktischer Weise trotzdem ;-) -->
				<xsl:value-of select="unparsed-text(concat('file:///',(concat($Quellpfad,$filename))), $encoding)" disable-output-escaping="yes"/>
				<xsl:element name="a">
					<xsl:attribute name="class">backpikto</xsl:attribute>
					<xsl:attribute name="href">
						<xsl:value-of select="$Pfad"/>
						<xsl:apply-templates select="$this"/>#<xsl:value-of select="$backlinkid"/>
					</xsl:attribute>
				</xsl:element>
				</xsl:otherwise>
			</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<!-- Wenn der folgende Abschnitt "npr-teil" ist, dann wird an dieser Stelle die "Div-Klammer" gesetzt -->
					<xsl:when test="$contextnode/abschnitt/@npr-teil='ja'">
					<div class="npr-teil">
						<xsl:choose>
							<xsl:when test="$content">
								<xsl:apply-templates select="$content">
									<xsl:with-param name="mode" select="$mode"/>
									<xsl:with-param name="styles" select="$styles"/>
									<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
									<xsl:with-param name="contextnode" select="$content"/>
									<xsl:with-param name="filenode" select="$filenode"/>
								</xsl:apply-templates>
							</xsl:when>
							<xsl:otherwise>
								<xsl:apply-templates select="$contextnode/node()">
									<xsl:with-param name="mode" select="$mode"/>
									<xsl:with-param name="styles" select="$styles"/>
									<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
									<xsl:with-param name="contextnode" select="$contextnode"/>
									<xsl:with-param name="filenode" select="$filenode"/>
								</xsl:apply-templates>
							</xsl:otherwise>
						</xsl:choose>
					</div>
					</xsl:when>
					<xsl:otherwise>
					<!-- Wenn keine Sonderregeln anzuwenden sind, dann ersetze, an dieser Stelle, die PI durch 
						den HTML-mäßig aufbereiteten Inhalt der QuellXML-Datei -->
						<xsl:choose>
							<xsl:when test="$content">
								<xsl:apply-templates select="$content">
									<xsl:with-param name="mode" select="$mode"/>
									<xsl:with-param name="styles" select="$styles"/>
									<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
									<xsl:with-param name="contextnode" select="$content"/>
									<xsl:with-param name="filenode" select="$filenode"/>
								</xsl:apply-templates>
							</xsl:when>
							<xsl:otherwise>
								<xsl:apply-templates select="$contextnode/node()">
									<xsl:with-param name="mode" select="$mode"/>
									<xsl:with-param name="styles" select="$styles"/>
									<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
									<xsl:with-param name="contextnode" select="$contextnode"/>
									<xsl:with-param name="filenode" select="$filenode"/>
								</xsl:apply-templates>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- Die für die Navigation unerläßlichen Navigationsbuttons werden hier verarbeitet.
		Es wird vorrausgesetzt, dass ein Bild direkt nach der PI('link-back') enthalten ist, dass als NavIcon zu verwenden ist -->	
	<xsl:template match="processing-instruction('link-back')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
	<xsl:param name="backlinkid" tunnel="yes"/>
	<xsl:variable name="this">
		<xsl:apply-templates select="$contextnode/ancestor-or-self::file[1]/@directory"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::file[1]/@filename"/>
	</xsl:variable>
	<xsl:variable name="back"><!-- Die vorhergehende Seite wird gesucht -->
		<xsl:apply-templates select="$contextnode/preceding-sibling::file[not(@integrate='no')][1]/@directory"/>
		<xsl:apply-templates select="$contextnode/preceding-sibling::file[not(@integrate='no')][1]/@filename"/>
	</xsl:variable>
	<xsl:choose>
	<xsl:when test="string-length($backlinkid) gt 0">
		<xsl:element name="a">
		<xsl:attribute name="href">
			<xsl:value-of select="$Pfad"/>
			<xsl:apply-templates select="$this"/>#<xsl:value-of select="$backlinkid"/>
		</xsl:attribute>
		<img>
		<xsl:copy-of select="following::img[1]/@*"/>
		<xsl:attribute name="src"><xsl:value-of select="concat($Pfad,following::img[1]/@src)"/></xsl:attribute>
		</img>
		</xsl:element>
	</xsl:when>
	<xsl:otherwise>
	<xsl:if test="string-length($back) &gt; 0">
		<xsl:element name="a">
		<xsl:attribute name="href">
			<xsl:value-of select="$Pfad"/>
			<xsl:apply-templates select="$back"/>
		</xsl:attribute>
		<img>
		<xsl:copy-of select="following::img[1]/@*"/>
		<xsl:attribute name="src"><xsl:value-of select="concat($Pfad,following::img[1]/@src)"/></xsl:attribute>
		</img>
		</xsl:element>
	</xsl:if>
	</xsl:otherwise>
	</xsl:choose>
	</xsl:template>
	
	<!-- Siehe PI('link-back') -->
	<xsl:template match="processing-instruction('link-forward')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
	<xsl:variable name="forward">
		<xsl:apply-templates select="$contextnode/following-sibling::file[not(@integrate='no')][1]/@directory"/>
		<xsl:apply-templates select="$contextnode/following-sibling::file[not(@integrate='no')][1]/@filename"/>
	</xsl:variable>
	<xsl:if test="string-length($forward) &gt; 0">
		<xsl:element name="a">
		<xsl:attribute name="href">
			<xsl:value-of select="$Pfad"/>
			<xsl:apply-templates select="$forward"/>
		</xsl:attribute>
		<img>
		<xsl:copy-of select="following::img[1]/@*"/>
		<xsl:attribute name="src"><xsl:value-of select="concat($Pfad,following::img[1]/@src)"/></xsl:attribute>
		</img>
		</xsl:element>
	</xsl:if>
	</xsl:template>
	
	<!-- Siehe PI('link-back') -->	
	<xsl:template match="processing-instruction('link-top')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
	<xsl:param name="backlinkid" tunnel="yes"/>
	
	<xsl:variable name="this">
		<xsl:apply-templates select="$contextnode/ancestor-or-self::file[1]/@directory"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::file[1]/@filename"/>
	</xsl:variable>
	<xsl:if test="not($backlinkid)">
	<xsl:element name="a">
	<xsl:attribute name="href">
		<xsl:value-of select="$Pfad"/>
		<xsl:value-of select="$this"/>
	</xsl:attribute>
	<img>
	<xsl:copy-of select="following::img[1]/@*"/>
	<xsl:attribute name="src"><xsl:value-of select="concat($Pfad,following::img[1]/@src)"/></xsl:attribute>
	</img>
	</xsl:element>
	</xsl:if>
	</xsl:template>
	
	<!-- Die folgenden PIs erzeugen einen Link auf ihre Elemente, z.B. Vorwort, Voraussetzungen, etc.
		Die PIs ersetzen die href-Anweisung der vorausgesetzten Link-Umgebung <a href="xxx"></a> durch korrekte Werte -->
	<xsl:template match="processing-instruction('link-preface')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">vorwort</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('link-prerequisites')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">voraussetzungen</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('link-bibliography')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>	
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">literaturverz</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
		
	<xsl:template match="processing-instruction('link-learningobjectives')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">lehrziele</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('link-authorintroduction')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">autorenvorstellung</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('link-studyremarks')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">studierhinweise</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('link-appendix')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">anhang</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('link-index')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">index</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	
	<xsl:template match="processing-instruction('link-glossary')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">glossar</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="processing-instruction('link-toc')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">inhaltsverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="processing-instruction('link-tofig')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">abbildungsverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('link-tomedia')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">multimedia</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="processing-instruction('link-toanima')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">animationsverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('link-toaudio')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>	
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">audioverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="processing-instruction('link-tosimu')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">simulationsverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('link-totext')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">textobjektverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="processing-instruction('link-tovideo')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">videoverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="processing-instruction('link-totable')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>	
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">tabellenverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('link-toass')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">aufgabenverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('link-toex')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">beispielverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('link-custartpage')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">startpage</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('link-impressum')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">impressum</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="0"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
<!-- Links zu den Kurs (Globalen Dateien) -->
	<xsl:template match="processing-instruction('glink-preface')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">vorwort</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('glink-prerequisites')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">voraussetzungen</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('glink-bibliography')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>	
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">literaturverz</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
		
	<xsl:template match="processing-instruction('glink-learningobjectives')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">lehrziele</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('glink-authorintroduction')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">autorenvorstellung</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('glink-studyremarks')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">studierhinweise</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('glink-appendix')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">anhang</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('glink-index')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">index</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	
	<xsl:template match="processing-instruction('glink-glossary')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">glossar</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="processing-instruction('glink-toc')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">inhaltsverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="processing-instruction('glink-tofig')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">abbildungsverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('glink-tomedia')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">multimedia</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="processing-instruction('glink-toanima')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">animationsverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('glink-toaudio')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>	
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">audioverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="processing-instruction('glink-tosimu')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">simulationsverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('glink-totext')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">textobjektverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="processing-instruction('glink-tovideo')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">videoverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>

	<xsl:template match="processing-instruction('glink-totable')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>	
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">tabellenverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('glink-toass')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">aufgabenverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('glink-toex')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">beispielverzeichnis</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('glink-impressum')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">impressum</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('glink-cu')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
	<xsl:variable name="cu"><xsl:value-of select="normalize-space(substring-after(., 'cu='))"/></xsl:variable>			<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">cu</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="cu" select="$cu"/>
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="processing-instruction('glink-cuIn')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
	<xsl:variable name="cu"><xsl:value-of select="normalize-space(substring-after(., 'cu='))"/></xsl:variable>
		<xsl:call-template name="specialtables">
			
			<xsl:with-param name="mode">cuIn</xsl:with-param>
			
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="global" select="1"/>
			<xsl:with-param name="cu" select="$cu"/>			
			<xsl:with-param name="filenode" select="$filenode"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
<!-- Ende, mit den Globalen Links -->
	
	
	<xsl:template match="processing-instruction('feulogo')" priority="1">
	<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurs/kurseinheiten/kurseinheit[@number=$contextnode/ancestor-or-self::file/@kurseinheit]/titelseite/feulogo"/>
	</xsl:template>
	<xsl:template match="processing-instruction('varlogo1')" priority="1">
	<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurs/kurseinheiten/kurseinheit[@number=$contextnode/ancestor-or-self::file/@kurseinheit]/titelseite/varlogo1"/>
	</xsl:template>
	<xsl:template match="processing-instruction('varlogo2')" priority="1">
	<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurs/kurseinheiten/kurseinheit[@number=$contextnode/ancestor-or-self::file/@kurseinheit]/titelseite/varlogo2"/>
	</xsl:template>
	<xsl:template match="processing-instruction('kurstitel')" priority="1">
	<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurs/kurseinheiten/kurseinheit[@number=$contextnode/ancestor-or-self::file/@kurseinheit]/titelseite/kurstitel"/>
	</xsl:template>
	<xsl:template match="processing-instruction('ke-bezeich')" priority="1">
	<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurs/kurseinheiten/kurseinheit[@number=$contextnode/ancestor-or-self::file/@kurseinheit]/titelseite/ke-bezeich"/>
	</xsl:template>
	<xsl:template match="processing-instruction('ke-titel')" priority="1">
	<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurs/kurseinheiten/kurseinheit[@number=$contextnode/ancestor-or-self::file/@kurseinheit]/titelseite/ke-titel"/>
	</xsl:template>
	<xsl:template match="processing-instruction('m-bezeich')" priority="1">
	<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurs/kurseinheiten/kurseinheit[@number=$contextnode/ancestor-or-self::file/@kurseinheit]/titelseite/m-bezeich"/>
	</xsl:template>
	<xsl:template match="processing-instruction('a-bezeich')" priority="1">
	<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurs/kurseinheiten/kurseinheit[@number=$contextnode/ancestor-or-self::file/@kurseinheit]/titelseite/a-bezeich"/>
	</xsl:template>
	<xsl:template match="processing-instruction('g-bezeich')" priority="1">
	<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurs/kurseinheiten/kurseinheit[@number=$contextnode/ancestor-or-self::file/@kurseinheit]/titelseite/g-bezeich"/>
	</xsl:template>
	<xsl:template match="processing-instruction('autoren')" priority="1">
	<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurs/kurseinheiten/kurseinheit[@number=$contextnode/ancestor-or-self::file/@kurseinheit]/titelseite/autoren"/>
	</xsl:template>
	<xsl:template match="processing-instruction('mitarbeiter')" priority="1">
	<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurs/kurseinheiten/kurseinheit[@number=$contextnode/ancestor-or-self::file/@kurseinheit]/titelseite/mitarbeiter"/>
	</xsl:template>
	<xsl:template match="processing-instruction('kursnummer')" priority="1">
	<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurs/kurseinheiten/kurseinheit[@number=$contextnode/ancestor-or-self::file/@kurseinheit]/titelseite/kursnummer"/>
	</xsl:template>
	<xsl:template match="processing-instruction('titelbild')" priority="1">
	<xsl:param name="contextnode"/>
	<xsl:param name="Pfad"/>
			<img class="titelbild" alt="titelbild">
			<xsl:attribute name="src"><xsl:value-of select="$Pfad"/>bilder/<xsl:call-template name="get.filename">
					<xsl:with-param name="mediaref">
						<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurs/kurseinheiten/kurseinheit[@number=$contextnode/ancestor-or-self::file/@kurseinheit]/titelseite/titelbild/*[1]/@fileref"/>
					</xsl:with-param>
				</xsl:call-template>
			</xsl:attribute>
		</img>
	</xsl:template>
	<xsl:template match="processing-instruction('copyright')" priority="1">
	<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurs/kurseinheiten/kurseinheit[@number=$contextnode/ancestor-or-self::file/@kurseinheit]/titelseite/copyright"/>
	</xsl:template>
	<xsl:template match="processing-instruction('kursnummer')" priority="1">
	<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurs/kurseinheiten/kurseinheit[@number=$contextnode/ancestor-or-self::file/@kurseinheit]/titelseite/kursnummer"/>
	</xsl:template>
	<xsl:template match="processing-instruction('codierung')" priority="1">
	<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurs/kurseinheiten/kurseinheit[@number=$contextnode/ancestor-or-self::file/@kurseinheit]/titelseite/codierung"/>
	</xsl:template>
	<xsl:template match="processing-instruction('kurs-nr')" priority="1">
	<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurs/metadata/kurs-nr"/>
	</xsl:template>
	<xsl:template match="processing-instruction('kurs-titel')" priority="1">
	<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::document/kurs/metadata/kurs-titel"/>
	</xsl:template>
	<xsl:template match="processing-instruction('kurs-autor')" priority="1">
	<xsl:param name="contextnode"/>
	<xsl:for-each select="$contextnode/ancestor-or-self::document/kurs/metadata/kurs-autor">
		<p>
			<xsl:apply-templates select="."/>
		</p>
	</xsl:for-each>
	</xsl:template>

	<xsl:template match="autoren|feulogo|varlogo1|varlogo2|kurstitel|ke-bezeich|a-bezeich|g-bezeich|m-bezeich|gestalter|mitarbeiter|codierung|copyright|kursnummer|titelbild">
		<xsl:apply-templates/>
	</xsl:template>

	<xsl:template name="specialtables">
	<xsl:param name="Pfad"/>
	<xsl:param name="filenode" tunnel="yes"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="parentnode"/>
	<xsl:param name="global"/>	<!-- wenn Globale Dateien gefragt werden -->
	<xsl:param name="cu"/>		<!-- Kurseinheitennummer -->
	<xsl:param name="mode"/>
	<xsl:variable name="thispath">
		<xsl:value-of select="$Pfad"/>
		<xsl:apply-templates select="$contextnode/@directory"/>
		<xsl:apply-templates select="$contextnode/@filename"/>
	</xsl:variable>
	<xsl:variable name="content">
	<xsl:choose>
	<xsl:when test="$filenode">
		<xsl:apply-templates select="$filenode/preceding-sibling::file[@directory=$filenode/ancestor-or-self::file/@directory]/*[name()=$mode] | $filenode/following-sibling::file[@directory=$filenode/ancestor-or-self::file[$mode]/@directory]/*[name()=$mode]" mode="getpage">
				<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
		</xsl:apply-templates>
	</xsl:when>
	<xsl:when test="$mode='cuIn'">
		<xsl:apply-templates select="$contextnode/preceding-sibling::file[@kurseinheit=string($cu)]/inhaltsverzeichnis | $contextnode/following-sibling::file[@kurseinheit=string($cu)]/inhaltsverzeichnis" mode="getpage">
		<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
</xsl:apply-templates>
	</xsl:when>
	<xsl:when test="$cu">
		<xsl:apply-templates select="$contextnode/preceding-sibling::file[@kurseinheit=string($cu)]/startpage | $contextnode/following-sibling::file[@kurseinheit=string($cu)]/startpage" mode="getpage">
		<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
</xsl:apply-templates>
	</xsl:when>

	<xsl:when test="$global">
		<xsl:apply-templates select="$contextnode/preceding-sibling::file[@kurseinheit='kein']/*[name()=$mode] | $contextnode/following-sibling::file[@kurseinheit='kein']/*[name()=$mode]" mode="getpage">
				<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
		</xsl:apply-templates>
	</xsl:when>
<!--
	<xsl:when test="$mode='impressum'">
		<xsl:apply-templates select="$contextnode/preceding-sibling::file[@directory=$filenode/ancestor-or-self::file/@directory]/impressum | $contextnode/following-sibling::file[@directory=$filenode/ancestor-or-self::file/@directory]/impressum" mode="getpage">
				<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
	</xsl:apply-templates>
	</xsl:when>
-->
	<xsl:otherwise>
		<xsl:apply-templates select="$contextnode/preceding-sibling::file[@directory=$contextnode/ancestor-or-self::file/@directory]/*[name()=$mode] | $contextnode/following-sibling::file[@directory=$contextnode/ancestor-or-self::file[$mode]/@directory]/*[name()=$mode]" mode="getpage">
				<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
		</xsl:apply-templates>
	</xsl:otherwise>
	</xsl:choose>
	</xsl:variable>
	<xsl:variable name="this">
	<xsl:choose>
	<xsl:when test="$mode='cuIn' and $cu=$contextnode/@kurseinheit">
		<xsl:apply-templates select="$contextnode/inhaltsverzeichnis"/>
	</xsl:when>	
	<xsl:when test="$mode='cu' and $cu=$contextnode/@kurseinheit">
		<xsl:apply-templates select="$contextnode/startpage"/>
	</xsl:when>
	<xsl:when test="$filenode">
		<xsl:value-of select="$filenode/descendant::*[name()=$mode]"/>	
	</xsl:when>
	<xsl:otherwise>
<!--		<xsl:value-of select="$contextnode/descendant::*[name()=$mode]"/>-->
		<xsl:apply-templates select="$contextnode/descendant::*[name()=$mode]"/>

	</xsl:otherwise>
	</xsl:choose>
	</xsl:variable>
	<xsl:if test="string-length($content) &gt; 0 or string-length($this) &gt; 0">
		<xsl:element name="{$parentnode}">
		<xsl:copy-of select="parent::*/@*"/>
		<xsl:if test="string-length($content) &gt; 0">
		<xsl:attribute name="href">
			<xsl:value-of select="$content"/>
		</xsl:attribute>
		</xsl:if>
		<xsl:if test="string-length($this) &gt; 0">
			<xsl:attribute name="class">
				<xsl:value-of select="concat(parent::*/@class,'_akt')"/>
			</xsl:attribute>
		</xsl:if>
		<xsl:value-of select="normalize-space(string(parent::*))"/>
		</xsl:element>
	</xsl:if>
	</xsl:template>
	
	<xsl:template match="processing-instruction('link-id')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
	<xsl:variable name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:variable>
	<xsl:variable name="id"><xsl:value-of select="normalize-space(substring-after(., 'id='))"/></xsl:variable>
	<xsl:if test="$contextnode/key('id_key',$id)">
	<xsl:element name="{$parentnode}">
	<xsl:copy-of select="parent::*/@*"/>
	<xsl:attribute name="href">
		<xsl:apply-templates select="$contextnode/key('id_key',$id)" mode="getpage">
				<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
		</xsl:apply-templates>
		<xsl:text>#</xsl:text>
		<xsl:value-of select="normalize-space(substring-after(., 'id='))"/>
	</xsl:attribute>
	<xsl:if test="$contextnode/*[@id=$id]">
		<xsl:attribute name="class">
			<xsl:value-of select="concat(parent::*/@class,'_akt')"/>
		</xsl:attribute>
	</xsl:if>
	<xsl:value-of select="normalize-space(string(parent::*))"/>
	</xsl:element>
	</xsl:if>
	</xsl:template>
	
	<!-- Hinzugefuegt am 11.01.2009 SGE -->
	<xsl:template match="processing-instruction('link-auto')">
		<xsl:param name="Pfad"/>
		<xsl:param name="contextnode"/>
		<xsl:param name="filenode" tunnel="yes"/>
		<xsl:variable name="konfig" select="$config/config/screenconfig/link-auto"/>
		<xsl:variable name="contextlevel">
			<xsl:value-of select="$contextnode/abschnitt[1]/@level"/>
		</xsl:variable>
		<xsl:variable name="parentnode">
			<xsl:value-of select="name(parent::node())"/>
		</xsl:variable>
		<xsl:variable name="id">
			<xsl:value-of select="normalize-space(substring-after(., 'id='))"/>
		</xsl:variable>
		<xsl:variable name="lolevel" select="$contextnode/key('id_key',$id)/@level"/>
		<xsl:variable name="cu" select="$contextnode/@kurseinheit"/>
		<xsl:if test="$contextnode/key('id_key',$id)">
			<xsl:element name="{$parentnode}">
				<xsl:copy-of select="parent::*/@*"/>
				<xsl:attribute name="href">
					<xsl:apply-templates select="$contextnode/key('id_key',$id)" mode="getpage">
						<xsl:with-param name="Pfad">
							<xsl:value-of select="$Pfad"/>
						</xsl:with-param>
					</xsl:apply-templates>
					<xsl:text>#</xsl:text>
					<xsl:value-of select="normalize-space(substring-after(., 'id='))"/>
				</xsl:attribute>
				<xsl:if test="$contextnode/abschnitt/@id=$id or ($contextnode/preceding::abschnitt[@level=1][1]/@id=$id and number($contextlevel) ge 2) or $contextnode/*[@id=$id]">
					<xsl:attribute name="class">
						<xsl:value-of select="concat(parent::*/@class,'_akt')"/>
					</xsl:attribute>
				</xsl:if>
				<xsl:choose>
					<!-- gibt es den Konfigzweig screenconfig, wenn ja ist der Titel länger als die erlaubte element-laenge ?-->
					<xsl:when test="exists($konfig/@element-lenght) and string-length(normalize-space(string(parent::*))) gt number($konfig/@element-lenght)">
						<xsl:value-of select="concat(substring(normalize-space(string(parent::*)),0,number($konfig/@element-lenght)),$konfig/@overlenght-symbol)"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="normalize-space(string(parent::*))"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:element>
		</xsl:if>
		<!-- Hole Dir alle Abschnitte in linearer Abfolge dieser CU in die Variable $subs -->
		<xsl:variable name="subs"
			select="$contextnode/ancestor::document/kurs/kurseinheiten/kurseinheit[@number=$cu]/ke-lehrtext/abschnitt"/>
		<!-- Gruppiere die Abschnitte nach Top-Levelelementen (level=1). Jede Gruppe besteht aus einem Toplevel und vielen Sublevelelementen -->
		<xsl:for-each-group select="$subs" group-starting-with="abschnitt[@level=1]">
			<xsl:choose>
				<!-- wenn level=1, die GruppenID gleich der ElementID und der Kontextknoten zum Elementknoten level 1 gehoert -->
				<xsl:when
					test="@level=1
					and @id=$id 
					and ($contextnode/abschnitt/@id=$id or ($contextnode/preceding::abschnitt[@level=1][1]/@id=$id and number($contextlevel) ge 2))">
					<div>
						<xsl:attribute name="id">
							<xsl:text>navi</xsl:text>
							<xsl:value-of select="./@level"/>
							<xsl:text>_layer</xsl:text>
						</xsl:attribute>
						<p>
							<xsl:attribute name="class">
								<xsl:text>navi</xsl:text>
								<xsl:value-of select="./@level"/>
								<xsl:text>_layer</xsl:text>
							</xsl:attribute>
							<xsl:for-each select="current-group()">
								<xsl:variable name="lokalid" select="./@id"/>
								<xsl:if test="@level=$konfig/sublevel/@level">
									<a>
										<xsl:choose>
											<xsl:when test="./@id=$contextnode/abschnitt[1]/@id">
												
												<xsl:attribute name="class">
													<xsl:choose>
														<xsl:when test="exists($konfig/sublevel/class)">
															<xsl:apply-templates select="$konfig/sublevel/class/node()">
																<xsl:with-param name="contextnode" select="."/>
															</xsl:apply-templates><xsl:text>_akt</xsl:text>
														</xsl:when>
														<xsl:otherwise>
															<xsl:text>navi1</xsl:text>
															<xsl:text>_layer_akt</xsl:text>
														</xsl:otherwise>
													</xsl:choose>
												</xsl:attribute>
											</xsl:when>
											<xsl:otherwise>
												<xsl:attribute name="class">
													<xsl:choose>
														<xsl:when test="exists($konfig/sublevel/class)">
															<xsl:apply-templates select="$konfig/sublevel/class/node()">
																<xsl:with-param name="contextnode" select="."/>
															</xsl:apply-templates>
														</xsl:when>
														<xsl:otherwise>
															<xsl:text>navi1</xsl:text>
															<xsl:text>_layer</xsl:text>
														</xsl:otherwise>
													</xsl:choose>
												</xsl:attribute>
											</xsl:otherwise>
										</xsl:choose>
										<xsl:attribute name="href">
											<xsl:apply-templates
												select="$contextnode/key('id_key',$lokalid)"
												mode="getpage">
												<xsl:with-param name="Pfad">
													<xsl:value-of select="$Pfad"/>
												</xsl:with-param>
											</xsl:apply-templates>
											<xsl:text>#</xsl:text>
											<xsl:value-of select="$lokalid"/>
										</xsl:attribute>
										<!-- zusammenstellung des titels laut konfigzweig/content -->
										<xsl:apply-templates select="$konfig/sublevel/content/node()">
											<xsl:with-param name="contextnode" select="."/>
											<xsl:with-param name="element-lenght" select="$konfig/sublevel/@element-lenght"/>
											<xsl:with-param name="overlenght-symbol" select="$konfig/sublevel/@overlenght-symbol"/>
										</xsl:apply-templates>
									</a>
								</xsl:if>
							</xsl:for-each>
						</p>
					</div>
				</xsl:when>
			</xsl:choose>
		</xsl:for-each-group>
	</xsl:template>
	
	<xsl:template match="processing-instruction('glink-abbreviations')">
	<xsl:param name="Pfad"/>
	<xsl:param name="contextnode"/>
	<xsl:param name="filenode" tunnel="yes"/>
	<xsl:variable name="parentnode"><xsl:value-of select="name(parent::node())"/></xsl:variable>
	<xsl:variable name="id">abkzv_kurs</xsl:variable>
	<xsl:if test="$contextnode/key('id_key',$id)">
	<xsl:element name="{$parentnode}">
	<xsl:copy-of select="parent::*/@*"/>
	<xsl:attribute name="href">
		<xsl:apply-templates select="$contextnode/key('id_key',$id)" mode="getpage">
				<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
		</xsl:apply-templates>
	</xsl:attribute>
	<xsl:if test="$contextnode/*[@id=$id]">
		<xsl:attribute name="class">
			<xsl:value-of select="concat(parent::*/@class,'_akt')"/>
		</xsl:attribute>
	</xsl:if>
	<xsl:value-of select="normalize-space(string(parent::*))"/>
	</xsl:element>
	</xsl:if>
	</xsl:template>
	
			
	 <xsl:template name="modify">
        <!-- This script modify an parameter in a string-->
        <xsl:param name="string"/> <!-- ... to modify-->
        <xsl:param name="insert-on-start"/> <!-- ... insert this on start of the parameter-->
        <xsl:param name="modify"/> <!-- ... modify what element -->

        <xsl:variable name="hochkomma">"</xsl:variable>
        <xsl:variable name="tokstring" select="tokenize($string,' ')"/>
        <xsl:variable name="sum"/>
        
        <xsl:for-each select="$tokstring">
            <xsl:choose>
                <xsl:when test="contains(.,$modify)">
                    <xsl:variable name="mod"><xsl:value-of select="substring-after(.,$hochkomma)"/></xsl:variable>
                    <xsl:value-of select="$modify"/><xsl:text>="</xsl:text><xsl:value-of select="concat($insert-on-start,$mod)"/><xsl:text> </xsl:text>                                     
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="if (position()=last()) then . else concat(.,' ')"/>                    
                </xsl:otherwise>
            </xsl:choose>
        </xsl:for-each>
        
          <xsl:variable name="first"><xsl:value-of select="substring-before($string,'href')"/></xsl:variable>
          <xsl:variable name="aftertemp"><xsl:value-of select=" substring-after($string,'href')"/></xsl:variable>

        <xsl:value-of select="$sum"/>
    </xsl:template>

</xsl:stylesheet>
