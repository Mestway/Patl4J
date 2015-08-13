<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:saxon="http://saxon.sf.net/">
	<xsl:output method="xml" version="1.0" encoding="ISO-8859-1" indent="yes"/>
	<xsl:include href="errormessages.xsl"/>
	<xsl:param name="workdir">file:///i:/output/testprojekt_ks/validation/validationtest/</xsl:param>
	<xsl:param name="document">validationtest</xsl:param>
	<xsl:param name="srcfile"></xsl:param>
	<xsl:variable name="parserfailures" select="document(concat($workdir,'parserfailures.xml'))"/>
	<xsl:variable name="xreffailures" select="document(concat($workdir,'xreffailures.xml'))"/>
	<xsl:variable name="latexfailures" select="document(concat($workdir,'latexfailures.xml'))"/>
	<xsl:variable name="idfailures" select="document(concat($workdir,'idfailures.xml'))"/>
	<!--xsl:variable name="referenceddocuments" select="document(concat($workdir,'referenceddocuments.xml'))"/-->
	<xsl:variable name="referencedresources" select="document(concat($workdir,'referencedresources.xml'))"/>
	<xsl:param name="request">file:///I:/output/testprojekt_ks/validation/validationtest/request.xml</xsl:param>
	<xsl:variable name="recursive" select="
		if (document($request)//recursion[@active='true'] 
			or document($request)//option[@name='recursive' and @value='true']
			or document($request)//file[@filename='report_all.html']) 
		then true()
		else false()"/>

	
	
	<xsl:template match="/">
		<report>
			<xsl:attribute name="document"><xsl:value-of select="$document"/></xsl:attribute>
			<xsl:attribute name="rootelement"><xsl:value-of select="name(document($srcfile)/*[1])"/></xsl:attribute>
			<xsl:attribute name="recursive"><xsl:value-of select="$recursive"/></xsl:attribute>
			<referenceddocuments>
				<xsl:apply-templates select="$referencedresources//referenceddocument"/>
			</referenceddocuments>
			<referencedresources>
				<xsl:apply-templates select="$referencedresources//referencedresource"/>
			</referencedresources>
			<failures>
				<xsl:apply-templates select="$parserfailures//failure"/>
				<xsl:apply-templates select="$xreffailures//failure"/>
				<xsl:apply-templates select="$idfailures//failure"/>
				<xsl:apply-templates select="$latexfailures//failure" mode="latex"/>
			</failures>
		</report>
	</xsl:template>
	
	<xsl:template match="failure|referenceddocument|referencedresource"><xsl:copy-of select="."/></xsl:template>
	
	<xsl:template match="failures|referenceddocuments|referencedresources"><xsl:apply-templates/></xsl:template>
	
	<xsl:template match="failure" mode="latex">
		<failure type="error">
			<xsl:attribute name="element">formel/formel-imtext</xsl:attribute>
			<xsl:attribute name="xpath"><xsl:value-of select="tokenize(@xpath,'#')[last()]"/></xsl:attribute>
			<xsl:attribute name="file"><xsl:value-of select="tokenize(@xpath,'#')[1]"/></xsl:attribute>
			<xsl:copy-of select="message"/>
			<hint><xsl:value-of select="$ERRORINLATEXSYNTAXHINT"/></hint>
		</failure>
	</xsl:template>
	
</xsl:stylesheet>
