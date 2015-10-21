<xsl:stylesheet version="1.0"
	xmlns:ofx="http://www.openfuxml.org"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:template match="ofx:table">
		<xsl:element name="tabelle">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="ofx:tgroup">
		<xsl:element name="tgroup">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="ofx:colspec">
		<xsl:element name="colspec">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="ofx:thead">
		<xsl:element name="thead">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="ofx:tbody">
		<xsl:element name="tbody">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>
	
	<xsl:template match="ofx:row">
		<xsl:element name="row">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>

	<xsl:template match="ofx:entry">
		<xsl:element name="entry">
			<xsl:apply-templates select="@*|node()"/>
		</xsl:element>
	</xsl:template>

</xsl:stylesheet>