<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:template match="@extern">
		<xsl:if test=".='ja'"><xsl:attribute name="external">true</xsl:attribute></xsl:if>
		<xsl:if test=".='nein'"><xsl:attribute name="external">false</xsl:attribute></xsl:if>
	</xsl:template>
	<xsl:template match="@quelle">
		<xsl:attribute name="source">
			<xsl:value-of select="."/>
		</xsl:attribute>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:template>

</xsl:stylesheet>