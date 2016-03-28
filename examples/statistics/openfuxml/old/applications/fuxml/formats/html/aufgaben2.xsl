<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<!-- ********************************************************************
		$Id: aufgaben2.xsl,v 1.6 2007/03/27 14:54:12 gebhard Exp $
		********************************************************************
		
		This file is part of the FuXML_HTML Stylesheet distribution.
		
		Description:
		This file contain the design-roles for assignment-elements as
		einsendeaufgabe, selbsttestaufgabe and uebungsaufgabe.
		
		Use in Pass4: XML to HTML conversation
		
		*******************************************************************
		Beschreibung:
		Einsendeaufgaben wurden bereits in der Pass1 auf eigene Seiten abgespalten. Selbsttest-/Übungsaufgaben
		werden erst bei der "Endproduktion" abgespalten und auf eigene Seiten verteilt.
		Trotzdem werden auch die Einsendeaufgaben erst hier wirklich, je nach Modus (der bereits im Pass1
		bestimmt wurde), ausgewertet.
		********************************************************************
	-->
	
	<!-- ==================================================================== -->
	
	<!-- Bearbeite die Einsendeaufgabe. Konfiguration der Aufgabe erfolgt in der Config.xml unter
		Styles/Einsendeaufgabe -> Variante task, solution, korrection
		
		Die Aufgerufenen Funktionen assignment befinden sich in der designinterface-html.xsl -->
	
	<xsl:key name="aufgaben" match="	//selbsttestaufgabe[aufgabenstellung][ancestor::file]|
		//uebungsaufgabe[aufgabenstellung][ancestor::file]" use="@id"/>
	
	<xsl:template match="einsendeaufgabe">
		<xsl:variable name="mode" select="ancestor::file/@Modus"/>
		<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
		<xsl:variable name="loesungspfad">
			<xsl:choose>
				<xsl:when test="aufgabenloesung/@id">
					<xsl:value-of select="aufgabenloesung/@id"/>
				</xsl:when>
				<xsl:when test="unteraufgabe/aufgabenloesung/@id">
					<xsl:value-of select="unteraufgabe[1]/aufgabenloesung/@id"/>
				</xsl:when>
			</xsl:choose>
		</xsl:variable>
		<xsl:if test="debug=1">
			<xsl:comment>aufgabe2.xsl -> Einsendeaufgabe - Modus:<xsl:value-of select="$mode"/></xsl:comment>
		</xsl:if>		
		<div>
			<xsl:attribute name="class"><xsl:value-of select="name(.)"/></xsl:attribute>
			<xsl:call-template name="assignment">
				<xsl:with-param name="style" select="$styles/entry[@name=name(current())][@variant=$mode]"/>
				<xsl:with-param name="mode" select="$mode"/>
			</xsl:call-template>
			<p class="eaverweise">
				<xsl:choose>
					<xsl:when test="$mode = 'task'">
						<a>
							<xsl:attribute name="href">
								<xsl:apply-templates select="key('id_key',$loesungspfad)[2]" mode="getpage">
									<xsl:with-param name="Pfad"><xsl:value-of select="$CSSPfad"/></xsl:with-param>
								</xsl:apply-templates>
								<xsl:text>#</xsl:text><xsl:value-of select="$loesungspfad"/>
							</xsl:attribute>
							<img src="../../images/ml.gif" alt="zur Musterlösung" />
						</a>				
					</xsl:when>
					<xsl:when test="$mode = 'solution'">
						<a>
							<xsl:attribute name="href">
								<xsl:apply-templates select="key('id_key',aufgabenstellung/@id)[1]" mode="getpage">
									<xsl:with-param name="Pfad"><xsl:value-of select="$CSSPfad"/></xsl:with-param>
								</xsl:apply-templates>
								<xsl:text>#</xsl:text><xsl:value-of select="aufgabenstellung/@id[1]"/>
							</xsl:attribute>
							<img src="../../images/ea.gif" alt="zur Einsendeaufgabe" />
						</a>
					</xsl:when>
				</xsl:choose>
			</p>
		</div>
	</xsl:template>
	
	<xsl:template match="unteraufgabe" mode="ea">
		<xsl:variable name="mode" select="ancestor::file/@Modus"/>
		<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
		<xsl:if test="debug=1">
			<xsl:comment>aufgabe2.xsl -> Unteraufgabe - mode:ea</xsl:comment>
		</xsl:if>		
		<div>
			<xsl:attribute name="class"><xsl:value-of select="name(.)"/></xsl:attribute>	
			<xsl:call-template name="assignment">
				<xsl:with-param name="style" select="$styles/entry[@name=name(current())][@variant=$mode]"/>
				<xsl:with-param name="mode" select="$mode"/>
			</xsl:call-template>
		</div>
	</xsl:template>
	
	<xsl:template match="aufgabenstellung|loesungshinweis|korrekturhinweis|aufgabenloesung|unteraufgabe" mode="normal">
		<xsl:param name="mode"/>
		<xsl:variable name="workmode" select="if (string-length($mode) gt 0) then $mode else ancestor::file/@Modus"/>
		<!--xsl:variable name="entry"><xsl:choose>
			<xsl:when test="$styles/entry[@name = name(current())][@variant]">
			<xsl:copy-of select="$styles/entry[@name = name(current()) and @variant=$workmode]"/>
			</xsl:when>
			<xsl:otherwise>
			<xsl:copy-of select="$styles/entry[@name = name(current())]"/>
			</xsl:otherwise>
			</xsl:choose>
			</xsl:variable-->
		<xsl:if test="debug=1">
			<xsl:comment>aufgabe2.xsl -> a/l/k/a/u - var:mode:<xsl:value-of select="$workmode"/> - normal</xsl:comment>
		</xsl:if>
		<a><!-- Sprungpunkt --><xsl:attribute name="name"><xsl:value-of select="@id"/></xsl:attribute></a>
		<xsl:choose>
			<xsl:when test="aufgabenstellung">
				<xsl:call-template name="assignment">
					<xsl:with-param name="style" select="$styles/entry[@name=name(current())][@variant=$workmode]"/>
					<xsl:with-param name="mode" select="$workmode"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<div>
					<xsl:attribute name="class"><xsl:value-of select="name(.)"/></xsl:attribute>				
					<xsl:call-template name="assignment">
						<xsl:with-param name="style" select="$styles/entry[@name=name(current())][@variant=$workmode]"/>
						<xsl:with-param name="mode" select="$workmode"/>
					</xsl:call-template>
				</div>				
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>	
	
	<xsl:template match="aufgabenstellung|loesungshinweis|korrekturhinweis|aufgabenloesung|unteraufgabe">
		<xsl:param name="mode"/>
		<xsl:variable name="workmode" select="if (string-length($mode) gt 0) then $mode else ancestor::file/@Modus"/>
		<!--xsl:variable name="entry"><xsl:choose>
			<xsl:when test="$styles/entry[@name = name(current())][@variant]">
			<xsl:copy-of select="$styles/entry[@name = name(current()) and @variant=$workmode]"/>
			</xsl:when>
			<xsl:otherwise>
			<xsl:copy-of select="$styles/entry[@name = name(current())]"/>
			</xsl:otherwise>
			</xsl:choose>
			</xsl:variable-->
		<xsl:comment>aufgabe2.xsl -> a/l/k/a/u - var:mode:<xsl:value-of select="$workmode"/></xsl:comment>
		<xsl:comment>styles:<xsl:value-of select="$styles/entry[@name = name(.) and @variant=$workmode]"/></xsl:comment>		
		<a>		<!-- Sprungpunkt -->
			<xsl:attribute name="name"><xsl:value-of select="@id"/></xsl:attribute></a>
		<div>
			<xsl:attribute name="class"><xsl:value-of select="name(.)"/></xsl:attribute>
			<xsl:call-template name="assignment">
				<xsl:with-param name="style" select="$styles/entry[@name=name(current())][@variant=$workmode]"/>
				<xsl:with-param name="mode" select="$workmode"/>
			</xsl:call-template>
		</div>
	</xsl:template>	
	
	<xsl:template match="selbsttestaufgabe|uebungsaufgabe">
		<xsl:variable name="CSSPfad"><xsl:call-template name="CSSPfad"></xsl:call-template></xsl:variable>
		<xsl:variable name="designdatei">
			<xsl:value-of select="ancestor-or-self::file[1]/@design"/>
		</xsl:variable>
		<!--xsl:variable name="design" select="document(concat($designpath,$designdatei))"/-->
		<xsl:variable name="Dir" select="ancestor-or-self::file/@directory"/>
		<xsl:variable name="afilename" select="key('aufgaben',@id)/ancestor::file/@filename"/>
		<div>
			<xsl:call-template name="standardattribute"/>
			<xsl:choose>
				<xsl:when test="aufgabenstellung">
					<xsl:call-template name="assignment">
						<xsl:with-param name="style" select="$styles/entry[@name=name(current())][@variant='task']"/>
						<xsl:with-param name="mode">task</xsl:with-param>
						<xsl:with-param name="Pfad"></xsl:with-param>
					</xsl:call-template>
					<p class="loesungspikto">
						<a href="{concat('sol_',aufgabenloesung/@id,'.html')}"></a>
					</p>						
				</xsl:when>
				<xsl:when test="aufgabenloesung">
					<xsl:call-template name="assignment">
						<xsl:with-param name="style" select="$styles/entry[@name=name(current())][@variant='solution']"/>
						<xsl:with-param name="mode">solution</xsl:with-param>
						<xsl:with-param name="Pfad"></xsl:with-param>
					</xsl:call-template>
					<p class="aufgabenpikto">
						<a>
							<xsl:attribute name="href"><xsl:value-of select="$afilename"/>#<xsl:value-of select="@id"/></xsl:attribute>
						</a>
					</p>
				</xsl:when>
			</xsl:choose>
		</div>
	</xsl:template>
	
	<!-- Wie soll der Zwischentitel der unterschiedlichen Aufgabentypen ausgegeben werden? -->
	<xsl:template match="zwischentitel[parent::einsendeaufgabe]|zwischentitel[parent::uebungsaufgabe]|zwischentitel[parent::selbsttestaufgabe]">
		<xsl:if test="debug=1">
			<xsl:comment>aufgabe2.xsl -> zwischentitel ganz unten</xsl:comment>		
		</xsl:if>	
		<xsl:choose>
			<xsl:when test="parent::einsendeaufgabe">
				<p class="eazwischentitel">
					<xsl:apply-templates/>
				</p>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template> 
	
</xsl:stylesheet>