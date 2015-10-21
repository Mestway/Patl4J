<xsl:stylesheet version="1.0"
	xmlns:ofx="http://www.openfuxml.org"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:include href="en2de/ofxdoc.xslt"/>
	<xsl:include href="en2de/title.xslt"/>
	<xsl:include href="en2de/paragraph.xslt"/>
	<xsl:include href="en2de/attributes.xslt"/>
	<xsl:include href="en2de/table.xslt"/>
	<xsl:include href="en2de/emphasis.xslt"/>
	<xsl:include href="en2de/listing.xslt"/>
	<xsl:include href="en2de/reference.xslt"/>
		
	<xsl:output method="xml" version="1.0" encoding="UTF-8"/>
	
	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="ofx:section">
		<xsl:element name="abschnitt">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="ofx:raw">
		<xsl:element name="buchstaeblich">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>

</xsl:stylesheet>