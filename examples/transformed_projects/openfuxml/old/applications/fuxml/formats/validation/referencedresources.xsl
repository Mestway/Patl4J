<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:saxon="http://saxon.sf.net/">
	<xsl:param name="srcdir">file:///I:/repository-lokal/testprojekt_ks/</xsl:param>
	<xsl:param name="request">file:///I:/output/testprojekt_ks/validation/validationtest/request.xml</xsl:param>
	<xsl:variable name="recursive" select="
		if (document($request)//recursion[@active='true'] 
			or document($request)//option[@name='recursive' and @value='true']
			or document($request)//file[@filename='report_all.html']) 
		then true()
		else false()"/>
	
	<xsl:include href="../common/mediainformation.xsl"/>
	
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	
		
	<xsl:template match="/">
			<xsl:if test="$recursive=true()"><xsl:message>Recursive</xsl:message></xsl:if>
			<referencedresources><xsl:apply-templates/></referencedresources>
	</xsl:template>

	<xsl:template match="*[@fileref][not(self::textobjekt)]">
		<xsl:variable name="path">
			<xsl:call-template name="get.relativepath">
				<xsl:with-param name="baseuri" select="$srcdir"/>
				<xsl:with-param name="uri" select="base-uri(current())"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="absolutefileref" select="substring-after(resolve-uri(escape-uri(@fileref,false()),base-uri(current())),$srcdir)"/>
		<referencedresource>
			<xsl:attribute name="fileref"><xsl:value-of select="$absolutefileref"/></xsl:attribute>
			<!--xsl:attribute name="fileref"><xsl:value-of select="concat($path,'/',@fileref)"/></xsl:attribute-->
			<xsl:attribute name="element"><xsl:value-of select="name(.)"/></xsl:attribute>
			<xsl:attribute name="xpath"><xsl:value-of select="saxon:path()"/></xsl:attribute>
			<xsl:attribute name="file">
				<xsl:call-template name="get.relativepath">
					<xsl:with-param name="baseuri" select="$srcdir"/>
					<xsl:with-param name="uri" select="base-uri(current())"/>
				</xsl:call-template>/<xsl:call-template name="get.filename">
					<xsl:with-param name="mediaref" select="base-uri(current())"/>
				</xsl:call-template>
			</xsl:attribute>
		</referencedresource>
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
				<xsl:call-template name="get.relativepath">
					<xsl:with-param name="baseuri" select="$srcdir"/>
					<xsl:with-param name="uri" select="base-uri(current())"/>
				</xsl:call-template>/<xsl:call-template name="get.filename">
					<xsl:with-param name="mediaref" select="base-uri(current())"/>
				</xsl:call-template>
			</xsl:attribute>
			<xsl:attribute name="id" select="generate-id()"/>
		</referenceddocument>

		<xsl:if test="$recursive"><xsl:apply-templates select="document(concat($srcdir,$path,'/',@quelle))"/></xsl:if>
	</xsl:template>
	
	<xsl:template match="textobjekt">
		<xsl:variable name="suffix">
			<xsl:call-template name="get.mediasuffix">
				<xsl:with-param  name="mediaref" select="@fileref"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="$suffix='xml'">
				<xsl:variable name="path">
					<xsl:call-template name="get.relativepath">
						<xsl:with-param name="baseuri" select="$srcdir"/>
						<xsl:with-param name="uri" select="base-uri(current())"/>
					</xsl:call-template>
				</xsl:variable>
				<referenceddocument>
					<xsl:attribute name="fileref"><xsl:value-of select="concat($path,'/',@fileref)"/></xsl:attribute>
					<xsl:attribute name="element"><xsl:value-of select="name(.)"/></xsl:attribute>
					<xsl:attribute name="xpath"><xsl:value-of select="saxon:path()"/></xsl:attribute>
					<xsl:attribute name="file">
						<xsl:call-template name="get.relativepath">
							<xsl:with-param name="baseuri" select="$srcdir"/>
							<xsl:with-param name="uri" select="base-uri(current())"/>
						</xsl:call-template>/<xsl:call-template name="get.filename">
							<xsl:with-param name="mediaref" select="base-uri(current())"/>
						</xsl:call-template>
					</xsl:attribute>
				</referenceddocument>
				<xsl:if test="$recursive"><xsl:apply-templates select="document(concat($srcdir,$path,'/',@fileref))"/></xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<referencedresource>
					<xsl:copy-of select="@fileref"/>
					<xsl:attribute name="element"><xsl:value-of select="name(.)"/></xsl:attribute>
					<xsl:attribute name="file">
						<xsl:call-template name="get.relativepath">
							<xsl:with-param name="baseuri" select="$srcdir"/>
							<xsl:with-param name="uri" select="base-uri(current())"/>
						</xsl:call-template>/<xsl:call-template name="get.filename">
							<xsl:with-param name="mediaref" select="base-uri(current())"/>
						</xsl:call-template>
					</xsl:attribute>
					<xsl:attribute name="xpath"><xsl:value-of select="saxon:path()"/></xsl:attribute>
				</referencedresource>
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>

	
	<xsl:template match="text()"/>

</xsl:stylesheet>
