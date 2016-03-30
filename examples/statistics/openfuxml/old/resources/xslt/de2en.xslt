<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:include href="de2en/kurs.xslt"/>
	<xsl:include href="de2en/absatz.xslt"/>
	<xsl:include href="de2en/attribute.xslt"/>
	<xsl:include href="de2en/emphasis.xslt"/>
	<xsl:include href="de2en/tabelle.xslt"/>
	
	<xsl:output method="xml" version="1.0" encoding="UTF-8"/>
	
	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="abschnitt">
		<xsl:element name="section">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="titel">
		<xsl:element name="title">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>

</xsl:stylesheet>