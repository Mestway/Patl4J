<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	
		<!-- Check if Latexsyntax contains fishy '$' character -->	
	<xsl:template match="formel|formel-imtext">
		<xsl:if test="contains(.,'$') and not(contains(.,'\$'))">
		<failure type="warning">
			<xsl:attribute name="element"><xsl:value-of select="name(.)"/></xsl:attribute>
			<xsl:attribute name="xpath"><xsl:value-of select="saxon:path()"/></xsl:attribute>
			<message><xsl:value-of select="$DOLLARINLATEXSYNTAX"/><xsl:text>&#x0a;</xsl:text>
			<xsl:value-of select="."/>
			</message>
			<hint><xsl:value-of select="$DOLLARINLATEXSYNTAXHINT"/></hint>
		</failure>
		</xsl:if>
	</xsl:template>

	<xsl:template match="*"><xsl:apply-templates/></xsl:template>	
	<xsl:template match="text()"/>
	
</xsl:stylesheet>
