<xsl:stylesheet version="1.0"
	xmlns:ofx="http://www.openfuxml.org/ofx"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:template match="schreibmaschine">
		<xsl:element name="emphasis">
			<xsl:attribute name="style">typewriter</xsl:attribute>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="kursiv">
		<xsl:element name="emphasis">
			<xsl:attribute name="style">normal</xsl:attribute>
			<xsl:attribute name="italic">true</xsl:attribute>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="fett">
		<xsl:element name="emphasis">
			<xsl:attribute name="style">normal</xsl:attribute>
			<xsl:attribute name="bold">true</xsl:attribute>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="unterstrichen">
		<xsl:element name="emphasis">
			<xsl:attribute name="style">normal</xsl:attribute>
			<xsl:attribute name="underline">true</xsl:attribute>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	
</xsl:stylesheet>