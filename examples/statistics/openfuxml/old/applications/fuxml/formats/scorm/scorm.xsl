<?xml version="1.0"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY elementtyp '(		startpage|
								autorenvorstellung|
								index|
								inhaltsverzeichnis|
								impressum|
								lehrziele|
								literaturverz|
								studierhinweise|
								voraussetzungen|
								vorwort)'>
								
<!ENTITY deselementtyp '(		descendant::autorenvorstellung|
								descendant::index|
								descendant::inhaltsverzeichnis|
								descendant::impressum|
								descendant::lehrziele|
								descendant::literaturverz|
								descendant::studierhinweise|
								descendant::voraussetzungen|
								descendant::vorwort)'>
]>

<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" indent="no" version="1.3" standalone="no"/>

<xsl:template match="document">
<xsl:variable name="kurseinheit" select="//file/@kurseinheit[not(.=preceding::file/@kurseinheit)]"/>

<manifest version="1.3"
			xmlns=				"http://www.imsglobal.org/xsd/imscp_v1p1"
			xmlns:adlcp=			"http://www.adlnet.org/xsd/adlcp_v1p3"
			xmlns:imsss=		"http://www.imsglobal.org/xsd/imsss" 
			xmlns:xsi=			"http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="	http://www.imsglobal.org/xsd/imscp_v1p1 imscp_v1p1.xsd
                     						http://www.adlnet.org/xsd/adlcp_v1p3 adlcp_v1p3.xsd
										http://www.imsglobal.org/xsd/imsss imsss_v1p0.xsd">
  <xsl:attribute name="identifier"><xsl:apply-templates select="kurs/metadata/kurs-titel"/></xsl:attribute>
                     
   <metadata>
      <schema>ADL SCORM</schema>
      <schemaversion>CAM 1.3</schemaversion>
   </metadata>
   
      <organizations default="kurs">
      <xsl:for-each-group select="file" group-by="@kurseinheit">
      		<xsl:choose>
			<xsl:when test="@kurseinheit='kein'">
			      <organization>
				  <xsl:attribute name="identifier"><xsl:text>kursganzoben</xsl:text></xsl:attribute>
					<xsl:apply-templates select="current-group()/&elementtyp;" mode="elemente"/>
			      </organization>
			</xsl:when>
			<xsl:otherwise>
			      <organization>
				  <xsl:attribute name="identifier"><xsl:text>ke</xsl:text><xsl:value-of select="@kurseinheit"/></xsl:attribute>
					<xsl:apply-templates select="current-group()/&elementtyp;" mode="elemente"/>
			      </organization>
			</xsl:otherwise>
		</xsl:choose>
      </xsl:for-each-group>
   </organizations>

   <resources>
	<xsl:apply-templates select="file"/>
   </resources>
</manifest>
</xsl:template>
	
<xsl:template match="*"/>

<xsl:template match="*" mode="elemente">
	<item>
		<xsl:attribute name="identifier">
			<xsl:call-template name="convertname">
				<xsl:with-param name="element" select="."/>
			</xsl:call-template>
		</xsl:attribute>
		<xsl:attribute name="identifierref">
			<xsl:value-of select="@id"/>
		</xsl:attribute>
		<title>
			<xsl:choose>
				<xsl:when test="name(.)='abschnitt'">
					<xsl:apply-templates select="titel"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates select="abschnitt[1]/titel"/>				
				</xsl:otherwise>
			</xsl:choose>
		</title>
		<xsl:apply-templates select="abschnitt" mode="elemente"/>
    	</item>
</xsl:template>

<xsl:template name="convertname">
<xsl:param name="element"/>
		<xsl:choose>
			<xsl:when test="name($element)='startpage'">
					<xsl:text>EINSPRUNGPUNKT</xsl:text>
			</xsl:when>
			<xsl:when test="name($element)='vorwort'">
					<xsl:text>VORWORT</xsl:text>
			</xsl:when>
			<xsl:when test="name($element)='autorenvorstellung'">
					<xsl:text>AUTOREN</xsl:text>
			</xsl:when>
			<xsl:when test="name($element)='index'">
					<xsl:text>INDEX</xsl:text>
			</xsl:when>
			<xsl:when test="name($element)='inhaltsverzeichnis'">
					<xsl:text>INHALTSVERZEICHNIS</xsl:text>
			</xsl:when>
			<xsl:when test="name($element)='impressum'">
					<xsl:text>IMPRESSUM</xsl:text>
			</xsl:when>
			<xsl:when test="name($element)='lehrziele'">
					<xsl:text>LEHRZIELE</xsl:text>
			</xsl:when>
			<xsl:when test="name($element)='literaturverz'">
					<xsl:text>LITERATURVERZEICHNIS</xsl:text>
			</xsl:when>
			<xsl:when test="name($element)='studierhinweise'">
					<xsl:text>STUDIERHINWEISE</xsl:text>
			</xsl:when>
			<xsl:when test="name($element)='voraussetzungen'">
					<xsl:text>VORAUSSETZUNGEN</xsl:text>
			</xsl:when>
			<xsl:when test="name($element)='abschnitt'">
					<xsl:text>ABSCHNITT</xsl:text>
			</xsl:when>			
		</xsl:choose>
</xsl:template>


<xsl:template match="file">
<xsl:variable name="kurseinheit">
	<xsl:value-of select="@kurseinheit"/>
</xsl:variable>
   	<resource>
<!--		<xsl:attribute name="identifier">
			<xsl:choose>
				<xsl:when test="&deselementtyp;">
					<xsl:text>RS_</xsl:text>
					<xsl:call-template name="convertname">
						<xsl:with-param name="element" select="&deselementtyp;"/>
					</xsl:call-template>
					<xsl:if test="string-length($kurseinheit) > 0">
						<xsl:text>_KE</xsl:text><xsl:value-of select="$kurseinheit"/>
					</xsl:if>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>RS_</xsl:text>
					<xsl:value-of select="@filename"/>
					<xsl:if test="string-length($kurseinheit) > 0">
						<xsl:text>_KE</xsl:text><xsl:value-of select="$kurseinheit"/>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute> -->
		<xsl:attribute name="type">
			<xsl:text>webcontent</xsl:text>
		</xsl:attribute>
		<xsl:attribute name="href">	
			<xsl:value-of select="concat(@directory,@filename)"/>
		</xsl:attribute>

<!--		<xsl:for-each-group select="$media" group-by="@fileref">
		    <xsl:for-each select="current-group()">
	               <xsl:apply-templates select="."/>
		    </xsl:for-each>
		</xsl:for-each-group> -->
			<xsl:apply-templates select="	descendant::bild|
												descendant::grafik|
												descendant::formel|
												descendant::animation|
												descendant::audio|
												descendant::video|
												descendant::silumation|
												descendant::textobjekt"/>

   	</resource>
</xsl:template>

<xsl:template match="bild|grafik">
<resource identifier="{@id}" type="image">
	<title><xsl:apply-templates select="parent::*/objekttitel"/></title>
	<file href="{@fileref}"/>
</resource>
</xsl:template>
<xsl:template match="animation">
<resource identifier="{@id}" type="animation">
	<title><xsl:apply-templates select="parent::*/objekttitel"/></title>
	<file href="{@fileref}"/>
</resource>
</xsl:template>
<xsl:template match="simulation">
<resource identifier="{@id}" type="simulation">
	<title><xsl:apply-templates select="parent::*/objekttitel"/></title>
	<file href="{@fileref}"/>
</resource>
</xsl:template>
<xsl:template match="audio">
<resource identifier="{@id}" type="sound">
	<title><xsl:apply-templates select="parent::*/objekttitel"/></title>
	<file href="{@fileref}"/>
</resource>
</xsl:template>
<xsl:template match="textobjekt">
<resource identifier="{@id}" type="object">
	<title><xsl:apply-templates select="parent::*/objekttitel"/></title>
	<file href="{@fileref}"/>
</resource>
</xsl:template>

<xsl:template match="titel">
<xsl:apply-templates/>
</xsl:template>

</xsl:stylesheet>
