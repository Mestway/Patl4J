<xsl:stylesheet version="1.0"
	xmlns:ofx="http://www.openfuxml.org/ofx"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:template match="tabelle">
		<xsl:element name="table">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>

</xsl:stylesheet>