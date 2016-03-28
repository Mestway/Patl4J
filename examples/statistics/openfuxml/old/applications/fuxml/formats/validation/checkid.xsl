<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:saxon="http://saxon.sf.net/">
	<xsl:output method="xml" version="1.0" encoding="ISO-8859-1" indent="yes"/>
	<xsl:include href="errormessages.xsl"/>
	<xsl:param name="srcdir">file:///v:/fuxml/testprojekt_ks/</xsl:param>
	

	
	<xsl:template match="/">
		<failures><!--xsl:apply-templates select="//*[@zielmarke]|//ke-lehrtext" mode="xref"/--><xsl:apply-templates/></failures>
	</xsl:template>
	
	<xsl:template match="abschnitt">
		<xsl:if test="preceding::abschnitt[@id=current()/@id]">
			<failure type="error">
					<xsl:attribute name="element"><xsl:value-of select="name(.)"/></xsl:attribute>
					<!--xsl:attribute name="xpath"><xsl:value-of select="saxon:path()"/></xsl:attribute-->
					<!-- XPath of "abschnitt" in external file is determined -->
			                            <xsl:attribute name="xpath">
					    <xsl:apply-templates select="document(concat($srcdir,@sourcefile))/descendant::*[@id=current()/@id]" mode="get.xpath"></xsl:apply-templates>
					</xsl:attribute>
			                              <xsl:attribute name="file"><xsl:value-of select="preceding::processing-instruction('file')[1]"/></xsl:attribute>
					<message>[<xsl:value-of select="@id"/>]: <xsl:value-of select="$DOUBLEID"/></message>
					<hint><xsl:value-of select="$DOUBLEIDHINT"/></hint>
			</failure>
		</xsl:if>
		<xsl:apply-templates/>
	</xsl:template>
	

	<xsl:template match="text()"/>
    
                <xsl:template match="*" mode="get.xpath"><xsl:value-of select="saxon:path()"/></xsl:template>
</xsl:stylesheet>
