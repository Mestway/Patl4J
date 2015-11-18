<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:saxon="http://saxon.sf.net/">
	<xsl:output method="xml" version="1.0" encoding="ISO-8859-1" indent="yes"/>
	<xsl:include href="errormessages.xsl"/>
	<xsl:param name="workdir">file:///i:/output/testprojekt_ks/validation/validationtest/</xsl:param>
	<xsl:param name="document">validationtest.xml</xsl:param>
	<xsl:param name="request">file:///I:/output/testprojekt_ks/validation/validationtest/request.xml</xsl:param>
	<xsl:variable name="recursive" select="
		if (document($request)//recursion[@active='true'] 
			or document($request)//option[@name='recursive' and @value='true']
			or document($request)//file[@filename='report_all.html']) 
		then true()
		else false()"/>
	<xsl:include href="../common/mediainformation.xsl"/>

	
	<xsl:template match="/">
		<failures>
			<xsl:apply-templates select="document(concat($workdir,'/','parserfailures.xml'))//failure">
				<xsl:with-param name="file" select="$document"/>
				</xsl:apply-templates>
			<xsl:if test="$recursive"><xsl:apply-templates select="//referenceddocument"/></xsl:if>
		</failures>
	</xsl:template>
	
	<xsl:template match="referenceddocument[@available='true']">
		<xsl:apply-templates select="document(concat($workdir,'/',@id,'.failures.xml'))//failure">
			<xsl:with-param name="file"><xsl:value-of select="@fileref"/></xsl:with-param>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="failure">
		<xsl:param name="file"/>
		<failure>
		<xsl:copy-of select="@*"/>
		<xsl:attribute name="file">
			<xsl:value-of select="$file"/>
		</xsl:attribute>
		<xsl:copy-of select="*"/>
		</failure>
	</xsl:template>
	
</xsl:stylesheet>
