<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:saxon="http://saxon.sf.net/">
	<xsl:param name="srcdir">file:///I:/repository/fuxml/k20022/</xsl:param>
	<xsl:variable name="recursive" select="true()"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	
	<xsl:include href="../common/mediainformation.xsl"/>
	
	<xsl:template match="/">
			<referenceddocuments><xsl:apply-templates/><!--xsl:apply-templates select="//*[@extern='ja']|//textobjekt" mode="referenceddocument"/--></referenceddocuments>
	</xsl:template>

	<xsl:template match="*[@extern='ja']">
		<xsl:variable name="path">
			<xsl:call-template name="get.relativepath">
				<xsl:with-param name="baseuri" select="$srcdir"/>
				<xsl:with-param name="uri" select="base-uri(current())"/>
			</xsl:call-template>
		</xsl:variable>
		
		<referenceddocument>
			<xsl:variable name="quelle">
				<xsl:choose>
					<xsl:when test="contains(@quelle,'#')"><xsl:value-of select="substring-before(@quelle,'#')"/></xsl:when>
					<xsl:otherwise><xsl:value-of select="@quelle"/></xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:attribute name="fileref"><xsl:value-of select="concat($path,'/',$quelle)"/></xsl:attribute>
			<xsl:attribute name="element"><xsl:value-of select="name(.)"/></xsl:attribute>
			<xsl:attribute name="xpath"><xsl:value-of select="saxon:path()"/></xsl:attribute>
			<xsl:attribute name="file">
				<xsl:call-template name="get.filename">
					<xsl:with-param name="mediaref" select="base-uri(current())"/>
				</xsl:call-template>
			</xsl:attribute>
		</referenceddocument>
				
		<xsl:if test="$recursive"><xsl:apply-templates select="document(concat($srcdir,$path,'/',@quelle))"/></xsl:if>
	</xsl:template>
	
	<xsl:template match="textobjekt">
		<xsl:variable name="path">
			<xsl:call-template name="get.relativepath">
				<xsl:with-param name="baseuri" select="$srcdir"/>
				<xsl:with-param name="uri" select="base-uri(current())"/>
			</xsl:call-template>
		</xsl:variable>
		
		<xsl:variable name="suffix">
			<xsl:call-template name="get.mediasuffix">
				<xsl:with-param  name="mediaref" select="@fileref"/>
			</xsl:call-template>
		</xsl:variable>
		
		<xsl:if test="$suffix='xml'">
			<referenceddocument>
				<xsl:attribute name="fileref"><xsl:value-of select="concat($path,'/',@fileref)"/></xsl:attribute>
				<xsl:attribute name="element"><xsl:value-of select="name(.)"/></xsl:attribute>
				<xsl:attribute name="xpath"><xsl:value-of select="saxon:path()"/></xsl:attribute>
			</referenceddocument>
			
		<xsl:if test="$recursive"><xsl:apply-templates select="document(concat($srcdir,$path,'/',@fileref))"/></xsl:if>
		</xsl:if>
	</xsl:template>
		
	<xsl:template match="text()"/>
	
	<xsl:template match="*"><xsl:apply-templates/></xsl:template>

</xsl:stylesheet>
