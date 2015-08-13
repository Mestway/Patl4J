<xsl:stylesheet version="1.0"
	xmlns:ofx="http://www.openfuxml.org"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:template match="ofx:title">
		<xsl:element name="titel">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>

</xsl:stylesheet>