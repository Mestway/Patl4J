<xsl:stylesheet version="1.0"
	xmlns:ofx="http://www.openfuxml.org"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:template match="@numbering">
		<xsl:if test=".='true'"><xsl:attribute name="num">an</xsl:attribute></xsl:if>
		<xsl:if test=".='false'"><xsl:attribute name="num">aus</xsl:attribute></xsl:if>
	</xsl:template>
	
	<xsl:template match="@external">
		<xsl:if test=".='true'"><xsl:attribute name="extern">ja</xsl:attribute></xsl:if>
		<xsl:if test=".='false'"><xsl:attribute name="extern">nein</xsl:attribute></xsl:if>
	</xsl:template>
	
	<xsl:template match="@source">
		<xsl:attribute name="quelle">
			<xsl:value-of select="."/>
		</xsl:attribute>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:template>

</xsl:stylesheet>