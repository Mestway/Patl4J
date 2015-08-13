<xsl:stylesheet version="1.0"
	xmlns:ofx="http://www.openfuxml.org/ofx"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:template match="kurs">
		<xsl:element name="ofxdoc">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="lehrtext">
		<xsl:element name="content">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	
</xsl:stylesheet>