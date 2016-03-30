<xsl:stylesheet version="1.0"
	xmlns:ofx="http://www.openfuxml.org"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:template match="ofx:reference">
		<xsl:element name="querverweis">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="ofx:reference/@target">
		<xsl:attribute name="zielmarke">
			<xsl:value-of select="."/>
		</xsl:attribute>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:template>

</xsl:stylesheet>