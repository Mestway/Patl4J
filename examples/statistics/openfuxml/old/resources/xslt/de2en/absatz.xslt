<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:template match="absatz">
		<xsl:element name="paragraph" >
			<xsl:attribute name="top">normal</xsl:attribute>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="absatz-klein">
		<xsl:element name="paragraph" >
			<xsl:attribute name="top">small</xsl:attribute>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="absatz-mini">
		<xsl:element name="paragraph" >
			<xsl:attribute name="top">mini</xsl:attribute>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="absatz-ohne">
		<xsl:element name="paragraph" >
			<xsl:attribute name="top">zero</xsl:attribute>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>

</xsl:stylesheet>