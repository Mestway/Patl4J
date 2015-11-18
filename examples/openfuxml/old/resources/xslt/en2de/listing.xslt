<xsl:stylesheet version="1.0"
	xmlns:ofx="http://www.openfuxml.org"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:template match="ofx:listing">
		<xsl:element name="proglist">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="ofx:listing/ofx:title">
		<xsl:element name="zwischentitel">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="ofx:listing/ofx:raw">
		<xsl:element name="buchstaeblich"><xsl:element name="schreibmaschine">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element></xsl:element>
	</xsl:template>
	

</xsl:stylesheet>