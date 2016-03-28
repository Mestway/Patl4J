<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<!-- ********************************************************************
     $Id: demux.xsl,v 1.4 2007/01/23 14:25:07 gebhard Exp $
     ********************************************************************

     This file is part of the FuXML_HTML Stylesheet distribution.
     
     Description:
     This file is the headerfile of pass1 in processing.
     It linera the FILE-elements under a document-element
     
     Use in Pass1: making NEUE-BILDSCHIRMSEITE work
     
     ********************************************************************
     Beschreibung:
     Dieses Stylesheet ist das Herz der ersten Prozessstufe, in der der Kontent, je nach dem ob eine 	Kurs/Kurseinheitenumgebung existiert oder nicht, unter Berücksichtigung der 
	Processing-Instruction("NEUBILDSCHIRMSEITE"), den FILE-Elemente zugewiesen wird. Die dabei entstandene 	Baumstruktur wird im Pass2 aufgelöst und linearisiert.
	Alle Style-Elemente werden in dieser Stufe so umgeformt,	dass sie den vorgenommenen Einstellungen der 	Config.xml entsprechen. Die erzeugte Zwischen-Datei *.demux1.xml ist nicht mehr DTD-Konform, wird aber von der 	weitern Produktion so erwartet.
	
	Der gesamte Kurs wird zur Sicherheit nocheinmal komplett unberührt mitgeführt.

     ********************************************************************
-->

	<xsl:import href="designinterface.xsl"/>
	<xsl:import href="../html/aufgaben.xsl"/>
	<xsl:import href="../html/index.xsl"/>

	<xsl:param name="format">html</xsl:param>
	<xsl:variable name="folder" select="$config/config/specialtables"/>
	<xsl:variable name="page" select="$config/config/screenconfig"/>
	<xsl:key name="abschnitt_key" match="//kurs/kurseinheiten/kurseinheit/ke-lehrtext/abschnitt" use="@id"/>
   	
<xsl:strip-space elements="*"/><!-- Überflüssige Spaces (Leerstellen) werden hier entfernt -->

	<xsl:template match="/">
	<!-- auf alles eingehen und im Fall einer Kursumgebung alles (doppelt) mitführen -->
	<document>
		<filecontent>
		<xsl:apply-templates select="node()"/>
		</filecontent>
		<xsl:if test="//kurs">
			<kurs>
				<xsl:copy-of select="//kurs/node()|@*"/>
			</kurs>
		</xsl:if>
	</document>
	</xsl:template>

<!-- Hauptroutine -->	
	<xsl:template match="*">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/>
		<xsl:variable name="style" select="$styles/entry[@name=name(current())]"/>
		
		<xsl:choose>
			<!-- Wenn eine Kursumgebung existiert, führe alles wie folgt aus: -->
			<xsl:when test="//kurs">
				<xsl:choose>
					<!-- ... prüfe, ob ein Containerelement vorliegt, wenn ja, führe es aus! -->
					<xsl:when test="$style/envtype='container'">
						<xsl:if test="not(self::einsendeaufgaben) or not($styles/entry[@name='einsendeaufgaben']/@number)">
							<xsl:call-template name="container">
								<xsl:with-param name="style" select="$style"/>
								<xsl:with-param name="mode" select="$mode"/>
							</xsl:call-template>
						</xsl:if>
						<xsl:if test="self::einsendeaufgaben and $styles/entry[@name='einsendeaufgaben']/@number">
						<xsl:variable name="eke"><xsl:value-of select="self::einsendeaufgaben/ancestor::kurseinheit/@number"/></xsl:variable>
							<xsl:if test="$style[@number=$eke]">
								<xsl:variable name="style" select="$style[@number=$eke]"/>
								<xsl:call-template name="container">
									<xsl:with-param name="style" select="$style"/>
									<xsl:with-param name="mode" select="$mode"/>
								</xsl:call-template>
							</xsl:if>
						</xsl:if>
					</xsl:when>
					<xsl:otherwise>
					<!-- ... wenn nein, dann prüfe, ob im ke-lehrtext operiert wird -->
					<xsl:choose>	
						<xsl:when test="self::ke-lehrtext">
							<!-- ... wenn ja, dann spalte FILE-Elemente nach PI ab -->
							<xsl:for-each-group select="node()" group-ending-with="processing-instruction('NEUE-BILDSCHIRMSEITE')">
								<xsl:if test="current-group()[not(self::processing-instruction('NEUE-BILDSCHIRMSEITE'))]">
									<xsl:variable name="pagenumber">
										<xsl:number count="processing-instruction('NEUE-BILDSCHIRMSEITE')" from="/" level="any"/>
									</xsl:variable>
									<xsl:variable name="file">
										<xsl:text>FuX_</xsl:text>								
										<xsl:value-of select="concat(following::*[1]/@id,'.html')"/>
									</xsl:variable>
									<xsl:variable name="style" select="$styles/entry[@name='kurseinheit'][@number=ancestor::kurseinheit[1]/@number]/structure[@format='html']/file/directory"/>
									<xsl:variable name="dir">
										<xsl:apply-templates select="$style">
											<xsl:with-param name="contextnode" select="ancestor::kurseinheit[1]"/>
										</xsl:apply-templates>
									</xsl:variable>
									<xsl:variable name="contentnumber">
										<xsl:choose>
											<xsl:when test="self::abschnitt">
												<xsl:value-of select="self::abschnitt/@level"/>
											</xsl:when>
											<xsl:otherwise>
												<xsl:value-of select="following::abschnitt[1]/@level"/>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:variable>
									<xsl:variable name="id">
										<xsl:choose>
											<xsl:when test="./@id">
												<xsl:value-of select="./@id"/>
											</xsl:when>
											<xsl:otherwise>
												<xsl:value-of select="following::abschnitt[1]/@id"/>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:variable>
									<file>
										<!-- $contextnode/abschnitt/@id=$id or ($contextnode/preceding::abschnitt[@level=1][1]/@id=$id and number($contextlevel) ge 2) -->
										<xsl:if test="$page/sectiontitle/@activ='1'">
											<!-- Einstellungen fuer den Kolumnentitel in der HTML-Ausgabe-->
											<xsl:attribute name="htmlsectiontitle">
												<xsl:if test="number($contentnumber) ge 2">
													<xsl:apply-templates select="$page/sectiontitle/level[@number=$contentnumber]/node()">
														<xsl:with-param name="contextnode" select="key('abschnitt_key',./@id)/preceding::abschnitt[@level=1][1]"/>
													</xsl:apply-templates>
												</xsl:if>
												<xsl:if test="number($contentnumber) ge 3">
													<xsl:apply-templates select="$page/sectiontitle/level[@number=$contentnumber]/node()">
														<xsl:with-param name="contextnode" select="key('abschnitt_key',./@id)/preceding::abschnitt[@level=2][1]"/>
													</xsl:apply-templates>
												</xsl:if>
												<xsl:if test="number($contentnumber) ge 4">
													<xsl:apply-templates select="$page/sectiontitle/level[@number=$contentnumber]/node()">
														<xsl:with-param name="contextnode" select="key('abschnitt_key',./@id)/preceding::abschnitt[@level=3][1]"/>
													</xsl:apply-templates>
												</xsl:if>
												<xsl:if test="number($contentnumber) ge 5">
													<xsl:apply-templates select="$page/sectiontitle/level[@number=$contentnumber]/node()">
														<xsl:with-param name="contextnode" select="key('abschnitt_key',./@id)/preceding::abschnitt[@level=4][1]"/>
													</xsl:apply-templates>
												</xsl:if>
												<xsl:if test="number($contentnumber) ge 6">
													<xsl:apply-templates select="$page/sectiontitle/level[@number=$contentnumber]/node()">
														<xsl:with-param name="contextnode" select="key('abschnitt_key',./@id)/preceding::abschnitt[@level=5][1]"/>
													</xsl:apply-templates>
												</xsl:if>
												<!-- Spezialfall Erstes-und letztes- Element, sowie letztes Element -->
												<xsl:choose>
													<xsl:when test="number($contentnumber) = 1">
														<xsl:apply-templates select="$page/sectiontitle/level[@number=99]/node()">
															<xsl:with-param name="contextnode" select="following::abschnitt[1]"/>
														</xsl:apply-templates>													
													</xsl:when>
													<xsl:otherwise>
														<xsl:choose>
															<xsl:when test="self::abschnitt">
																<xsl:apply-templates select="$page/sectiontitle/level[@number=99]/node()">
																	<xsl:with-param name="contextnode" select="self::abschnitt"/>
																</xsl:apply-templates>
															</xsl:when>
															<xsl:otherwise>
																<xsl:apply-templates select="$page/sectiontitle/level[@number=99]/node()">
																	<xsl:with-param name="contextnode" select="following::abschnitt[1]"/>
																</xsl:apply-templates>
															</xsl:otherwise>
														</xsl:choose>
													</xsl:otherwise>
												</xsl:choose>
											</xsl:attribute>
											<xsl:attribute name="html_akt_id">
												<xsl:value-of select="key('abschnitt_key',./@id)/preceding::abschnitt[@level=1][1]/@id"/>
											</xsl:attribute>
											<xsl:attribute name="debug">
												<xsl:text>CNR</xsl:text><xsl:value-of select="number($contentnumber)"/>
												<xsl:text>LV</xsl:text><xsl:value-of select="@level"/>
											</xsl:attribute>
										</xsl:if>
										<xsl:attribute name="filename"><xsl:value-of select="normalize-space($file)"/></xsl:attribute>
										<xsl:attribute name="directory"><xsl:value-of select="normalize-space($dir)"/></xsl:attribute>
										<xsl:attribute name="design"><xsl:value-of select="ancestor::file[1]/@design"/></xsl:attribute>
										<xsl:attribute name="integrate">yes</xsl:attribute>
										<xsl:copy-of select="current-group()[not(self::processing-instruction('NEUE-BILDSCHIRMSEITE'))]"/>
									</file>
								</xsl:if>
							</xsl:for-each-group>
							<!-- ********************** -->
						</xsl:when>
						<xsl:otherwise>
							<!-- ...wenn nein, dann kopiere nur die Inhalte (Hier ist ein Problem, da alle Elemente, die kein 									Container sind nur copiert werden, können sie an dieser Stelle auch nicht applyed/									ausgeführt werden) -->
							<xsl:copy>
								<xsl:copy-of  select="node()|@*"/>
							</xsl:copy>
						</xsl:otherwise>
					</xsl:choose>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
			<!-- Wenn keine Kursumgebung gegeben ist, produziere einfach was da ist, beachte aber 						NeueBildschirmseite !-->
			 <xsl:for-each-group select="node()" group-starting-with="processing-instruction('NEUE-BILDSCHIRMSEITE')">
	        	<xsl:if test="current-group()[self::*]"> <!-- Nur Gruppen produzieren, die auch Elemente enthalten -->
				<xsl:variable name="file">
					<xsl:text>FuX_</xsl:text>
					<xsl:value-of select="concat(following::*[1]/@id,'.html')"/>
				</xsl:variable>
				<xsl:variable name="style" select="$styles/entry[@name='document']/structure[@format='html']"/>
				<xsl:variable name="dir">
					<xsl:apply-templates select="$style/file/directory">
				</xsl:apply-templates>
				</xsl:variable>
				<xsl:variable name="design">
					<xsl:apply-templates select="$style/file/designtemplate">
				</xsl:apply-templates>
				</xsl:variable>
				<file>
					<xsl:attribute name="filename"><xsl:value-of select="normalize-space($file)"/></xsl:attribute>
					<xsl:attribute name="directory"><xsl:value-of select="normalize-space($dir)"/></xsl:attribute>
					<xsl:attribute name="design"><xsl:value-of select="normalize-space($design)"/></xsl:attribute>
					<xsl:attribute name="integrate">yes</xsl:attribute>
					<xsl:copy-of select="current-group()[not(self::processing-instruction('NEUE-BILDSCHIRMSEITE'))]"/>
				</file>
				</xsl:if>
			</xsl:for-each-group>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
<!-- Ende der Hauptroutine -->

<!-- Diese Routine erzeugt das eigentliche FILE-Element mit seinen Attributen und dem Kontent.
	Einsendeaufgaben werden direkt vorverarbeitet (siehe aufgaben.xsl) -->
	<xsl:template match="processing-instruction('NEUE-BILDSCHIRMSEITE')[not(ancestor-or-self::einsendeaufgaben)]">
	<xsl:param name="contextnode"/>
	<xsl:variable name="pagenumber">
		<xsl:number count="processing-instruction('NEUE-BILDSCHIRMSEITE')" from="/" level="any"/>
	</xsl:variable>
	<xsl:variable name="file">
		<xsl:text>FuX_</xsl:text>	
		<xsl:value-of select="concat(following::*[1]/@id,'.html')"/>
	</xsl:variable>
	<xsl:variable name="style" select="$styles/entry[@name='kurseinheit'][@number=ancestor::kurseinheit[1]/@number]/structure[@format='html']/file/directory"/>
	<xsl:variable name="dir">
		<xsl:apply-templates select="$style">
		<xsl:with-param name="contextnode" select="ancestor::kurseinheit[1]"/>
		</xsl:apply-templates>
	</xsl:variable>
	<file>
			<xsl:attribute name="filename"><xsl:value-of select="normalize-space($file)"/></xsl:attribute>
			<xsl:attribute name="directory"><xsl:value-of select="normalize-space($dir)"/></xsl:attribute>
			<xsl:attribute name="design"><xsl:value-of select="ancestor::file[1]/@design"/></xsl:attribute>
			<xsl:attribute name="integrate">yes</xsl:attribute>
			<xsl:copy-of select="following-sibling::node()[count(preceding::processing-instruction('NEUE-BILDSCHIRMSEITE'))=$pagenumber]"/>
	</file>
	</xsl:template>

	<!-- Der Kurs-Container wird verarbeitet, anschliessend noch Fussnotenabschnitte und Metadaten -->
	<xsl:template match="kurs">
	<xsl:param name="contextnode"/>
	<xsl:variable name="style" select="$styles/entry[@name=name(current())]"/>
	<kurs>
		<xsl:call-template name="container">
			<xsl:with-param name="style" select="$style"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:call-template>
		<xsl:apply-templates select="fnabschnitt|metadata"/>
	</kurs>
	</xsl:template>
	
	<xsl:template match="kurseinheit">
	<xsl:param name="contextnode"/>
<!--	<xsl:variable name="style" select="$styles/entry[@name='kurseinheit'][@number=current()/@number]"/> -->
	<xsl:variable name="style" select="if ($styles/entry[@name=name(current())][@number=current()/@number]) 
											then ($styles/entry[@name=name(current())][@number=current()/@number]) 
										  	else ($styles/entry[@name=name(current())][not(@number)]) 
										"/>
	<kurseinheit>
		<xsl:attribute name="number">
			<xsl:value-of select="current()/@number"/>		
		</xsl:attribute>
		<xsl:call-template name="container">
			<xsl:with-param name="style" select="$style"/>
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:call-template>
		<xsl:apply-templates select="ancestor::kurs/metadata | titelseite">
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates>
<!--		<xsl:apply-templates select="descendant::aufgabenloesung[not(ancestor-or-self::einsendeaufgabe)]">
			<xsl:with-param name="style" select="$style"/>
		</xsl:apply-templates> -->
	</kurseinheit>
	</xsl:template>

	<!-- Verarbeitung der in der Config.xml geforderten Einstellungen für jeder Element -->
	<xsl:template name="container">
		<xsl:param name="style"/>
		<xsl:param name="mode"/>
		<xsl:param name="contextnode"/>
 		<xsl:apply-templates select="$style/structure[@format=$format]">
			<xsl:with-param name="contextnode" select="current()"/>
		</xsl:apply-templates>
	</xsl:template>

	<!-- Auswertung des Structure-Elements und damit Aufruf der configurierten und unterstützten ;-) Processing-			Instructions -->
	<xsl:template match="structure">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/>
		<xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<!-- Auswertung des FILE-Elements der Config.xml
		wichtig für die Verarbeitung der in der Config.xml vorgegebenen Files (Startfile, Verzeichnisfiles, etc. -->
	<xsl:template match="file">
		<xsl:param name="contextnode"/>
		<xsl:variable name="file">
			<xsl:apply-templates select="filename"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>
		</xsl:variable>
		<xsl:variable name="dir">
			<xsl:apply-templates select="directory"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>
		</xsl:variable>
		<xsl:variable name="design">
			<xsl:apply-templates select="designtemplate"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>
		</xsl:variable>
		<xsl:variable name="design2">
			<xsl:apply-templates select="ancestor::file/designtemplate"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="@integrate">
				<xsl:value-of select="@integrate"/>
			</xsl:when>
			<xsl:when test="descendant::multimedia">
				<xsl:value-of select="$folder/multimedia/@integrate"/>
			</xsl:when>
			<xsl:when test="descendant::beispielverzeichnis">
				<xsl:value-of select="$folder/example/@integrate"/>
			</xsl:when>
		</xsl:choose>
		<xsl:copy>
			<xsl:attribute name="filename"><xsl:value-of select="normalize-space($file)"/></xsl:attribute>
			<xsl:attribute name="directory"><xsl:value-of select="normalize-space($dir)"/></xsl:attribute>
			<xsl:attribute name="integrate"><xsl:value-of select="@integrate"/></xsl:attribute>
			<xsl:attribute name="design">
			<xsl:choose>
				<xsl:when test="string-length($design) &gt; 0"><xsl:value-of select="normalize-space($design)"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="normalize-space($design2)"/></xsl:otherwise>
			</xsl:choose>
			</xsl:attribute>
			<xsl:apply-templates select="content|filecopy">
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="filecopy">
		<xsl:variable name="from">
			<xsl:apply-templates select="from"/>
		</xsl:variable>
		<xsl:variable name="todir">
			<xsl:apply-templates select="todir"/>
		</xsl:variable>	
		<copy file="{$from}" todir="{$todir}/" recursive="{@recursive}"/>
	</xsl:template>

	<!-- Auswertung div. Elemente der Config.xml -->
	<xsl:template match="filename|directory|designtemplate|startpagetemplate|impressumtemplate">
	<xsl:param name="contextnode"/>
		<xsl:apply-templates>
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="description">
		<xsl:param name="contextnode"/>
		<xsl:copy>
			<xsl:apply-templates>
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
		</xsl:copy>
	</xsl:template>
	
	<!-- Auswertung div. Elemente der Config.xml -->
	<xsl:template match="content">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/><!-- for assignments -->
		<xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>

	<!-- Auswertung div. Elemente der Config.xml -->
	<xsl:template match="metadata">
	<metadata>
		<xsl:copy-of select="node()|@*"/>	
	</metadata>
	</xsl:template>
	
	<!-- Auswertung div. Elemente der Config.xml -->
	<xsl:template match="titelseite">
	<titelseite>
		<xsl:copy-of select="node()|@*"/>	
	</titelseite>
	</xsl:template>
	
	<!-- Auswertung div. Elemente der Config.xml -->
	<!-- Wie soll mit dem Fussnotenabschnitt umgegangen werden? -->
	<!-- Z.Z.: Wenn eine Kursumgebung existiert, dann füge Fussnotenabschitte in FILE-Elemente in die Rootebene ein 		und nummeriere sie aufsteigend (Designdatei nutze je nach Elternelement (Kurseinheit)) -->
 	<xsl:template match="fnabschnitt[parent::kurs]">
 		<xsl:variable name="dir">
 		</xsl:variable>
		<xsl:variable name="designdatei">
			<xsl:apply-templates select="$styles/entry[@name='kurs']/structure[@format='html']/file/designtemplate">
				<xsl:with-param name="contextnode" select="current()"/>
			</xsl:apply-templates>
		</xsl:variable>
		<xsl:variable name="Pfad">
		<xsl:text>fussnoten</xsl:text>	<xsl:number count="fnabschnitt[parent::kurs]"/><xsl:text>.html</xsl:text>
		</xsl:variable>
 		<file filename="{$Pfad}">
 			<xsl:attribute name="directory"><xsl:value-of select="normalize-space($dir)"/></xsl:attribute>
 			<xsl:attribute name="design"><xsl:value-of select="normalize-space($designdatei)"/></xsl:attribute>
 			<xsl:copy>
 				<xsl:copy-of select="@*|node()"/>
 			</xsl:copy>		
 		</file>
	</xsl:template>

	<!-- Erzeuge vorbereitend die div. Verzeichnisse, die in der Config.xml gefordert werden ... -->
	<!-- Es werden nur die Abschnittstitel und IDs der Multimediaobjekte mitgeliefert -->
	<!-- ... hier das Multimediaverzeichnis -->
	<xsl:template match="multimedia">
	<xsl:param name="contextnode"/>
		<xsl:variable name="context">
		<xsl:choose>
			<xsl:when test="$contextnode/ancestor-or-self::kurseinheit">courseunit</xsl:when>
			<xsl:when test="$contextnode/ancestor-or-self::kurs">course</xsl:when>
		</xsl:choose>
		</xsl:variable>
		<xsl:if test="	$contextnode/descendant::medienobjekt/objekttitel/@mediennum='animation' or
						$contextnode/descendant::medienobjekt/objekttitel/@mediennum='audio' or
						$contextnode/descendant::medienobjekt/objekttitel/@mediennum='simulation' or
						$contextnode/descendant::medienobjekt/objekttitel/@mediennum='textobjekt' or
						$contextnode/descendant::medienobjekt/objekttitel/@mediennum='video'">
			<abschnitt>
				<xsl:attribute name="level">
					<xsl:call-template name="level">
						<xsl:with-param name="element" select="$folder[@context=$context]/multimedia"/>
					</xsl:call-template>
				</xsl:attribute>
				<titel>
				<xsl:attribute name="id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
				<xsl:apply-templates select="$folder[@context=$context]/multimedia/label/node()">
						<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
				</titel>
			</abschnitt>
			<multimedia>
				<xsl:attribute name="id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
				<xsl:apply-templates>
					<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
			</multimedia>
		</xsl:if>
	</xsl:template>

	<!-- PI(TOC) wird hier verarbeitet -->
	<xsl:template match="processing-instruction('toc')">
		<xsl:param name="contextnode"/>
	</xsl:template>

	<!-- PI(content) wird hier verarbeitet -->
	<xsl:template match="processing-instruction('content')">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/>
			<xsl:apply-templates select="$contextnode/node()">
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="mode" select="$mode"/>
			</xsl:apply-templates>
	</xsl:template>
	
	<!-- PI(index) wird hier verarbeitet -->
	<!-- Im Gegensatz zu allen anderen Verzeichnissen, wird das Indexsystem hier vollständig ausgeführt.
		Die index.xsl ist dafür verantwortlich -->
	<xsl:template match="processing-instruction('index')">
		<xsl:param name="contextnode"/>
		<xsl:variable name="context">
		<xsl:choose>
			<xsl:when test="$contextnode/ancestor-or-self::kurseinheit">courseunit</xsl:when>
			<xsl:when test="$contextnode/ancestor-or-self::kurs">course</xsl:when>
		</xsl:choose>
		</xsl:variable>
			<!-- Der Abschnittstitel wird hier vergeben -->
			<abschnitt>
				<xsl:attribute name="level">
					<xsl:call-template name="level">
						<xsl:with-param name="element" select="$config/config/index[@context=$context]"/>
					</xsl:call-template>
				</xsl:attribute>
				<titel>
				<xsl:attribute name="id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
				<xsl:apply-templates select="$config/config/index[@context=$context]/indextitle/node()">
						<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
				</titel>
			</abschnitt>
			<!-- hier wird der eigentliche Indexmechanismus gestartet -->
			<xsl:call-template name="generateIndex">
				<xsl:with-param name="contextnode">
					<xsl:apply-templates select="$contextnode//indexfix"/>				
				</xsl:with-param>
			</xsl:call-template>
	</xsl:template>

	<!-- Die beiden PIs aus der Config.xml werden an dieser Stelle ausgewertet
		und für die Produktion nutzbar gemacht -->	
	<xsl:template match="processing-instruction('startpage')|processing-instruction('titlepage')">
		<xsl:param name="contextnode"/>
		<xsl:variable name="design">
			<xsl:apply-templates select="ancestor-or-self::file[1]/startpagetemplate">
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
		</xsl:variable>
		<startpage>
			<xsl:attribute name="id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
			<xsl:attribute name="design">
				<xsl:value-of select="normalize-space($design)"/>
			</xsl:attribute>
		</startpage>
	</xsl:template>

	<!-- Die PI aus der Config.xml werden an dieser Stelle ausgewertet
		und für die Produktion nutzbar gemacht -->	
		<xsl:template match="processing-instruction('impressum')">
		<xsl:param name="contextnode"/>
		<xsl:variable name="design">
			<xsl:apply-templates select="ancestor-or-self::file[1]/impressumtemplate">
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
		</xsl:variable>
		<impressum>
				<xsl:attribute name="id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
			<xsl:attribute name="design">
				<xsl:value-of select="normalize-space($design)"/>
			</xsl:attribute>
		</impressum>
	</xsl:template>
	
	<!-- Die PI aus der Config.xml werden an dieser Stelle ausgewertet
		und für die Produktion nutzbar gemacht -->	
	<xsl:template match="processing-instruction('footnote')">
		<xsl:param name="contextnode"/>
		<xsl:variable name="design">
			<xsl:apply-templates select="ancestor-or-self::file[1]/startpagetemplate">
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
		</xsl:variable>
			<xsl:copy-of select="$contextnode/ancestor-or-self::document/kurs/fnabschnitt"/>
	</xsl:template>
	
	<xsl:template match="*" mode="getID">
		<id><xsl:value-of select="@id"/></id>
	</xsl:template>

	<!-- Die PI aus der Config.xml werden an dieser Stelle ausgewertet
		und für die Produktion nutzbar gemacht -->
	<!-- Das Inhaltsverzeichnis, ohne Sonderverzeichnisse kann hier gebaut werden (z.B. im Text) -->
	<xsl:template match="processing-instruction('toc')" priority="1">
		<xsl:param name="contextnode"/>
		<xsl:variable name="context">
		<xsl:choose>
			<xsl:when test="$contextnode/ancestor-or-self::kurseinheit">courseunit</xsl:when>
			<xsl:when test="$contextnode/ancestor-or-self::kurs">course</xsl:when>
		</xsl:choose>
		</xsl:variable>
			<abschnitt>
				<xsl:attribute name="level">
					<xsl:call-template name="level">
						<xsl:with-param name="element" select="$config/config/toc[@context=$context]/toctitle"/>
					</xsl:call-template>
				</xsl:attribute>
				<titel>
				<xsl:attribute name="id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
				<xsl:apply-templates select="$config/config/toc[@context=$context]/toctitle/node()">
						<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
				</titel>
			</abschnitt>
			<inhaltsverzeichnis>
			<xsl:apply-templates select="$contextnode/descendant::abschnitt[not(ancestor::textobjekt)]/titel" mode="getID">
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
			</inhaltsverzeichnis>
	</xsl:template>

	<!-- Erzeuge vorbereitend die div. Verzeichnisse, die in der Config.xml gefordert werden ... -->
	<!-- Es werden nur die Abschnittstitel und IDs der Multimediaobjekte mitgeliefert -->
	<!-- ... hier das Abbildungsverzeichnis -->
	<xsl:template match="processing-instruction('tofig')">
		<xsl:param name="contextnode"/>
		<xsl:variable name="context">
		<xsl:choose>
			<xsl:when test="$contextnode/ancestor-or-self::kurseinheit">courseunit</xsl:when>
			<xsl:when test="$contextnode/ancestor-or-self::kurs">course</xsl:when>
		</xsl:choose>
		</xsl:variable>
		<xsl:if test="$contextnode/descendant::medienobjekt/objekttitel/@mediennum='bild-grafik'">
			<abschnitt>
				<xsl:attribute name="level">
					<xsl:call-template name="level">
						<xsl:with-param name="element" select="$folder[@context=$context]/figure"/>
					</xsl:call-template>
				</xsl:attribute>
				<titel>
				<xsl:attribute name="id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
				<xsl:apply-templates select="$folder[@context=$context]/figure/label/node()">
						<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
				</titel>
			</abschnitt>
			<abbildungsverzeichnis>
			<xsl:apply-templates select="$contextnode/descendant::medienobjekt/objekttitel[@mediennum='bild-grafik']" mode="getID">
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
			</abbildungsverzeichnis>
		</xsl:if>
	</xsl:template>

	<!-- Erzeuge vorbereitend die div. Verzeichnisse, die in der Config.xml gefordert werden ... -->
	<!-- Es werden nur die Abschnittstitel und IDs der Multimediaobjekte mitgeliefert -->
	<!-- ... hier das Animationsverzeichnis -->	
	<xsl:template match="processing-instruction('toanima')">
		<xsl:param name="contextnode"/>
		<xsl:variable name="context">
		<xsl:choose>
			<xsl:when test="$contextnode/ancestor-or-self::kurseinheit">courseunit</xsl:when>
			<xsl:when test="$contextnode/ancestor-or-self::kurs">course</xsl:when>
		</xsl:choose>
		</xsl:variable>
		<xsl:if test="$contextnode/descendant::medienobjekt/objekttitel/@mediennum='animation'">
			<abschnitt>
				<xsl:attribute name="level">
					<xsl:call-template name="level">
						<xsl:with-param name="element" select="$folder[@context=$context]/multimedia/animation"/>
					</xsl:call-template>
				</xsl:attribute>
				<titel>
				<xsl:attribute name="id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
				<xsl:apply-templates select="$folder[@context=$context]/multimedia/animation/label/node()">
						<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
				</titel>
			</abschnitt>
				<animationsverzeichnis>
					<xsl:apply-templates select="$contextnode/descendant::medienobjekt/objekttitel[@mediennum='animation']" mode="getID">
						<xsl:with-param name="contextnode" select="$contextnode"/>
					</xsl:apply-templates>
				</animationsverzeichnis>
		</xsl:if>
	</xsl:template>

	<!-- Erzeuge vorbereitend die div. Verzeichnisse, die in der Config.xml gefordert werden ... -->
	<!-- Es werden nur die Abschnittstitel und IDs der Multimediaobjekte mitgeliefert -->
	<!-- ... hier das Audioverzeichnis -->
	<xsl:template match="processing-instruction('toaudio')">
		<xsl:param name="contextnode"/>
		<xsl:variable name="context">
		<xsl:choose>
			<xsl:when test="$contextnode/ancestor-or-self::kurseinheit">courseunit</xsl:when>
			<xsl:when test="$contextnode/ancestor-or-self::kurs">course</xsl:when>
		</xsl:choose>
		</xsl:variable>
		<xsl:if test="$contextnode/descendant::medienobjekt/objekttitel/@mediennum='audio'">
			<abschnitt>
				<xsl:attribute name="level">
					<xsl:call-template name="level">
						<xsl:with-param name="element" select="$folder[@context=$context]/multimedia/audio"/>
					</xsl:call-template>
				</xsl:attribute>
				<titel>
				<xsl:attribute name="id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
				<xsl:apply-templates select="$folder[@context=$context]/multimedia/audio/label/node()">
						<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
				</titel>
			</abschnitt>
				<audioverzeichnis>
					<xsl:apply-templates select="$contextnode/descendant::medienobjekt/objekttitel[@mediennum='audio']" mode="getID">
						<xsl:with-param name="contextnode" select="$contextnode"/>
					</xsl:apply-templates>
				</audioverzeichnis>
		</xsl:if>
	</xsl:template>

	<!-- Erzeuge vorbereitend die div. Verzeichnisse, die in der Config.xml gefordert werden ... -->
	<!-- Es werden nur die Abschnittstitel und IDs der Multimediaobjekte mitgeliefert -->
	<!-- ... hier das Simulationsverzeichnis -->
	<xsl:template match="processing-instruction('tosimu')">
		<xsl:param name="contextnode"/>
		<xsl:variable name="context">
		<xsl:choose>
			<xsl:when test="$contextnode/ancestor-or-self::kurseinheit">courseunit</xsl:when>
			<xsl:when test="$contextnode/ancestor-or-self::kurs">course</xsl:when>
		</xsl:choose>
		</xsl:variable>
		<xsl:if test="$contextnode/descendant::medienobjekt/objekttitel/@mediennum='simulation'">
			<abschnitt>
				<xsl:attribute name="level">
					<xsl:call-template name="level">
						<xsl:with-param name="element" select="$folder[@context=$context]/multimedia/simulation"/>
					</xsl:call-template>
				</xsl:attribute>
				<titel>
				<xsl:attribute name="id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
				<xsl:apply-templates select="$folder[@context=$context]/multimedia/simulation/label/node()">
						<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
				</titel>
			</abschnitt>
				<simulationsverzeichnis>
					<xsl:apply-templates select="$contextnode/descendant::medienobjekt/objekttitel[@mediennum='simulation']" mode="getID">
						<xsl:with-param name="contextnode" select="$contextnode"/>
					</xsl:apply-templates>
				</simulationsverzeichnis>
		</xsl:if>
	</xsl:template>

	<!-- Erzeuge vorbereitend die div. Verzeichnisse, die in der Config.xml gefordert werden ... -->
	<!-- Es werden nur die Abschnittstitel und IDs der Multimediaobjekte mitgeliefert -->
	<!-- ... hier das Textobjektverzeichnis -->
	<xsl:template match="processing-instruction('totext')">
		<xsl:param name="contextnode"/>
		<xsl:variable name="context">
		<xsl:choose>
			<xsl:when test="$contextnode/ancestor-or-self::kurseinheit">courseunit</xsl:when>
			<xsl:when test="$contextnode/ancestor-or-self::kurs">course</xsl:when>
		</xsl:choose>
		</xsl:variable>
		<xsl:if test="$contextnode/descendant::medienobjekt/objekttitel/@mediennum='textobjekt'">
			<abschnitt>
				<xsl:attribute name="level">
					<xsl:call-template name="level">
						<xsl:with-param name="element" select="$folder[@context=$context]/multimedia/textobjekt"/>
					</xsl:call-template>
				</xsl:attribute>
				<titel>
				<xsl:attribute name="id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
				<xsl:apply-templates select="$folder[@context=$context]/multimedia/textobjekt/label/node()">
						<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
				</titel>
			</abschnitt>
				<textobjektverzeichnis>
					<xsl:apply-templates select="$contextnode/descendant::medienobjekt/objekttitel[@mediennum='textobjekt']" mode="getID">
						<xsl:with-param name="contextnode" select="$contextnode"/>
					</xsl:apply-templates>
				</textobjektverzeichnis>
		</xsl:if>
	</xsl:template>

	<!-- Erzeuge vorbereitend die div. Verzeichnisse, die in der Config.xml gefordert werden ... -->
	<!-- Es werden nur die Abschnittstitel und IDs der Multimediaobjekte mitgeliefert -->
	<!-- ... hier das Videoverzeichnis -->
	<xsl:template match="processing-instruction('tovideo')">
		<xsl:param name="contextnode"/>
		<xsl:variable name="context">
		<xsl:choose>
			<xsl:when test="$contextnode/ancestor-or-self::kurseinheit">courseunit</xsl:when>
			<xsl:when test="$contextnode/ancestor-or-self::kurs">course</xsl:when>
		</xsl:choose>
		</xsl:variable>
		<xsl:if test="$contextnode/descendant::medienobjekt/objekttitel/@mediennum='video'">
			<abschnitt>
				<xsl:attribute name="level">
					<xsl:call-template name="level">
						<xsl:with-param name="element" select="$folder[@context=$context]/multimedia/video"/>
					</xsl:call-template>
				</xsl:attribute>
				<titel>
				<xsl:attribute name="id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
				<xsl:apply-templates select="$folder[@context=$context]/multimedia/video/label/node()">
						<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
				</titel>
			</abschnitt>
				<videoverzeichnis>
					<xsl:apply-templates select="$contextnode/descendant::medienobjekt/objekttitel[@mediennum='video']" mode="getID">
						<xsl:with-param name="contextnode" select="$contextnode"/>
					</xsl:apply-templates>
				</videoverzeichnis>
		</xsl:if>
	</xsl:template>

	<!-- Erzeuge vorbereitend die div. Verzeichnisse, die in der Config.xml gefordert werden ... -->
	<!-- Es werden nur die Abschnittstitel und IDs der Multimediaobjekte mitgeliefert -->
	<!-- ... hier das Tabellenverzeichnis -->
	<xsl:template match="processing-instruction('totable')">
		<xsl:param name="contextnode"/>
		<xsl:variable name="context">
		<xsl:choose>
			<xsl:when test="$contextnode/ancestor-or-self::kurseinheit">courseunit</xsl:when>
			<xsl:when test="$contextnode/ancestor-or-self::kurs">course</xsl:when>
		</xsl:choose>
		</xsl:variable>
		<xsl:if test="$contextnode/descendant::tabelle|$contextnode/descendant::tabelle-alt">
			<abschnitt>
				<xsl:attribute name="level">
					<xsl:call-template name="level">
						<xsl:with-param name="element" select="$folder[@context=$context]/table"/>
					</xsl:call-template>
				</xsl:attribute>
				<titel>
				<xsl:attribute name="id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
				<xsl:apply-templates select="$folder[@context=$context]/table/label/node()">
						<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
				</titel>
			</abschnitt>
				<tabellenverzeichnis>
					<xsl:apply-templates select="$contextnode/descendant::tabelle|$contextnode/descendant::tabelle-alt" mode="getID">
						<xsl:with-param name="contextnode" select="$contextnode"/>
					</xsl:apply-templates>
				</tabellenverzeichnis>
		</xsl:if>
	</xsl:template>

	<!-- Erzeuge vorbereitend die div. Verzeichnisse, die in der Config.xml gefordert werden ... -->
	<!-- Es werden nur die Abschnittstitel und IDs der Multimediaobjekte mitgeliefert -->
	<!-- ... hier das Aufgabenverzeichnis (Nur Selbstest-und Uebungsaufgaben) -->	
	<xsl:template match="processing-instruction('toass')">
		<xsl:param name="contextnode"/>
		<xsl:variable name="context">
		<xsl:choose>
			<xsl:when test="$contextnode/ancestor-or-self::kurseinheit">courseunit</xsl:when>
			<xsl:when test="$contextnode/ancestor-or-self::kurs">course</xsl:when>
		</xsl:choose>
		</xsl:variable>
		<xsl:if test="$contextnode/descendant::selbsttestaufgabe|$contextnode/descendant::uebungsaufgabe">
			<abschnitt>
				<xsl:attribute name="level">
					<xsl:call-template name="level">
						<xsl:with-param name="element" select="$folder[@context=$context]/assignment"/>
					</xsl:call-template>
				</xsl:attribute>
				<titel>
				<xsl:attribute name="id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
				<xsl:apply-templates select="$folder[@context=$context]/assignment/label/node()">
						<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
				</titel>
			</abschnitt>
				<aufgabenverzeichnis>
					<xsl:apply-templates select="$contextnode/descendant::selbsttestaufgabe|$contextnode/descendant::uebungsaufgabe" mode="getID">
						<xsl:with-param name="contextnode" select="$contextnode"/>
					</xsl:apply-templates>
				</aufgabenverzeichnis>
		</xsl:if>
	</xsl:template>

	<!-- Erzeuge vorbereitend die div. Verzeichnisse, die in der Config.xml gefordert werden ... -->
	<!-- Es werden nur die Abschnittstitel und IDs der Multimediaobjekte mitgeliefert -->
	<!-- ... hier das Beispielverzeichnis -->	
	<xsl:template match="processing-instruction('toex')">
		<xsl:param name="contextnode"/>
		<xsl:variable name="context">
		<xsl:choose>
			<xsl:when test="$contextnode/ancestor-or-self::kurseinheit">courseunit</xsl:when>
			<xsl:when test="$contextnode/ancestor-or-self::kurs">course</xsl:when>
		</xsl:choose>
		</xsl:variable>
		<xsl:if test="$contextnode/descendant::beispiel">
			<abschnitt>
				<xsl:attribute name="level">
					<xsl:call-template name="level">
						<xsl:with-param name="element" select="$folder[@context=$context]/example"/>
					</xsl:call-template>
				</xsl:attribute>
				<titel>
				<xsl:attribute name="id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
				<xsl:apply-templates select="$folder[@context=$context]/example/label/node()">
					<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
				</titel>
			</abschnitt>
				<beispielverzeichnis>
					<xsl:apply-templates select="$contextnode/descendant::beispiel" mode="getID">
						<xsl:with-param name="contextnode" select="$contextnode"/>
					</xsl:apply-templates>
				</beispielverzeichnis>
		</xsl:if>
	</xsl:template>
	
	<xsl:template name="coursecontext">
		<xsl:param name="contextnode"/>
	<!-- This function finds out, which context the node is -->
		<xsl:choose>
			<xsl:when test="$contextnode/ancestor::kurseinheit">courseunit</xsl:when>
			<xsl:when test="$contextnode/ancestor::kurs">course</xsl:when>
		</xsl:choose>
	</xsl:template>

<xsl:template match="aufgabenloesung">
		<xsl:param name="style"/>
		<xsl:variable name="dir">
		<xsl:apply-templates select="$style/structure[@format='html']/file/directory">
			<xsl:with-param name="contextnode" select="ancestor-or-self::kurseinheit[1]"/>
		</xsl:apply-templates>
	</xsl:variable>
		<xsl:variable name="designdatei">
			<xsl:apply-templates select="$style/structure[@format='html']/file/designtemplate">
				<xsl:with-param name="contextnode" select="current()"/>
			</xsl:apply-templates>
		</xsl:variable>
	<xsl:message>Spalte Aufgabenloesung ab <xsl:value-of select="$dir"/><xsl:value-of select="concat(@id,'.html')"/></xsl:message>
 		<file filename="{concat('sol_',@id,'.html')}">
 			<xsl:attribute name="directory"><xsl:value-of select="normalize-space($dir)"/></xsl:attribute>
 			<xsl:attribute name="design"><xsl:value-of select="normalize-space($designdatei)"/></xsl:attribute>
 			<xsl:attribute name="integrate">no</xsl:attribute> 			
 			<xsl:copy>
 				<xsl:copy-of select="@*|node()"/>
 			</xsl:copy>		
 		</file>
</xsl:template>

<xsl:template name="level">
<xsl:param name="element"/>
<xsl:variable name="prelevel">
	<xsl:apply-templates select="$element/@level"/>
</xsl:variable>
		<xsl:choose>
			<xsl:when test="$prelevel=''">1</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$prelevel"/>
			</xsl:otherwise>
		</xsl:choose>	
</xsl:template>
</xsl:stylesheet>
