<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:saxon="http://saxon.sf.net/">
	<xsl:param name="srcdir">file:///I:/repository/fuxml/k20022/</xsl:param>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	
	<xsl:include href="../common/mediainformation.xsl"/>
	<xsl:param name="request">file:///I:/output/testprojekt_ks/validation/validationtest/request.xml</xsl:param>
	<!-- Recursive validation requested? Remark: There are two deprecated ways to specify this option (first and last)-->
	<xsl:variable name="recursive" select="
		if (document($request)//recursion[@active='true'] 
			or document($request)//option[@name='recursive' and @value='true']
			or document($request)//file[@filename='report_all.html']) 
		then true()
		else false()"/>

	<xsl:template match="/">
		<project default="validatereferenceddocuments">
			<target name="validatereferenceddocuments">
				<echo><xsl:attribute name="file">${validation.dir}/${document.plain}/refdoc.parserfailures.xml</xsl:attribute>&lt;failures/&gt;</echo>
				<xsl:if test="$recursive">
					<xsl:apply-templates select="//referenceddocument"/>
					<condition property="refdoc.parser.error">
						<or>
							<xsl:apply-templates select="//referenceddocument" mode="condition"/>
						</or>
					</condition> 
				</xsl:if>
				<echo><xsl:attribute name="file">${validation.dir}/${document.plain}/refdoc.parser.error</xsl:attribute>${refdoc.parser.error}</echo>
			</target>
		</project>
	</xsl:template>
	
	<xsl:template match="referenceddocument[@available='true']">
		<xsl:comment>+++ <xsl:value-of select="@fileref"/> +++</xsl:comment>
		<echo><xsl:value-of select="@fileref"/></echo>
		<xsl:variable name="path">
			<xsl:call-template name="get.relativepath">
				<xsl:with-param name="baseuri" select="$srcdir"/>
				<xsl:with-param name="uri" select="base-uri(current())"/>
			</xsl:call-template>
		</xsl:variable>
		<echo><xsl:attribute name="file">${validation.dir}/${document.plain}/<xsl:value-of select="@id"/>.failures.xml</xsl:attribute>&lt;failures/&gt;</echo>
		<java classname="org.openfuxml.producer.utilities.Parser" fork="true" resultproperty="{@id}.parser.result">
			<arg>
				<xsl:attribute name="value"><xsl:value-of select="concat($srcdir,$path,@fileref)"/></xsl:attribute>
			</arg>
			<arg>
				<xsl:attribute name="value">${validation.dir}/${document.plain}/<xsl:value-of select="@id"/>.failures.xml</xsl:attribute>
			</arg>
		</java>
		<condition property="{@id}.parser.error">
			<equals>
				<xsl:attribute name="arg1">${<xsl:value-of select="@id"/>.parser.result}</xsl:attribute>
				<xsl:attribute name="arg2">1</xsl:attribute>
			</equals> 
		</condition>
		<echo>${<xsl:value-of select="@id"/>.parser.error}</echo>

	</xsl:template>		
	
	<xsl:template match="referenceddocument[@available='true']" mode="condition">
		<isset property="{@id}.parser.error"/>
	</xsl:template>
		
	<xsl:template match="text()"/>
	
	

</xsl:stylesheet>
