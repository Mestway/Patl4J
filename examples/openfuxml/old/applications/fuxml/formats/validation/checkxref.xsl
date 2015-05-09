<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:saxon="http://saxon.sf.net/">
	<xsl:output method="xml" version="1.0" encoding="ISO-8859-1" indent="yes"/>
	<xsl:include href="errormessages.xsl"/>
	<xsl:include href="../common/mediainformation.xsl"/>
	<xsl:key name="id_key" match="@id" use="."/>
	<xsl:param name="srcdir">file:///i:/repository/fuxml/testprojekt_ks/</xsl:param>
	<xsl:param name="document">document</xsl:param>
	<xsl:param name="request">file:///I:/output/testprojekt_ks/validation/validationtest/request.xml</xsl:param>
	<xsl:variable name="recursive" select="
		if (document($request)//recursion[@active='true'] 
			or document($request)//option[@name='recursive' and @value='true']
			or document($request)//file[@filename='report_all.html']) 
		then true()
		else false()"/>

	
	<xsl:template match="/">
		<failures><!--xsl:apply-templates select="//*[@zielmarke]|//ke-lehrtext" mode="xref"/--><xsl:apply-templates/></failures>
		<xsl:if test="$recursive=true()"><xsl:message>Recursive</xsl:message></xsl:if>
	</xsl:template>
	
	<!-- To Do: check tables and their colwidth attributes-->
	
	
	
	<!--xsl:template match="abschnitt">
		<xsl:if test="preceding::abschnitt[@id=current()/@id]">
			<failure type="error">
					<xsl:attribute name="element"><xsl:value-of select="name(.)"/></xsl:attribute>
					<xsl:attribute name="xpath"><xsl:value-of select="saxon:path()"/></xsl:attribute>
					<xsl:attribute name="file"><xsl:value-of select="preceding::processing-instruction('file')[1]"/></xsl:attribute>
					<message>[<xsl:value-of select="@id"/>]: <xsl:value-of select="$DOUBLEID"/></message>
					<hint><xsl:value-of select="$DOUBLEIDHINT"/></hint>
			</failure>
		</xsl:if>
		<xsl:apply-templates/>
	</xsl:template-->
	
	
	<xsl:template match="*[@extern='ja']">
		<xsl:variable name="path">
				<xsl:call-template name="get.relativepath">
					<xsl:with-param name="baseuri" select="$srcdir"/>
					<xsl:with-param name="uri" select="base-uri(current())"/>
				</xsl:call-template>
		</xsl:variable>
		<xsl:if test="@quelle='' or not(@quelle)">
			<failure type="error">
					<xsl:attribute name="element"><xsl:value-of select="name(.)"/></xsl:attribute>
					<xsl:attribute name="xpath"><xsl:value-of select="saxon:path()"/></xsl:attribute>
					<xsl:attribute name="file">
						<xsl:call-template name="get.relativepath">
							<xsl:with-param name="baseuri" select="$srcdir"/>
							<xsl:with-param name="uri" select="base-uri(current())"/>
						</xsl:call-template>/<xsl:call-template name="get.filename">
							<xsl:with-param name="mediaref" select="base-uri(current())"/>
						</xsl:call-template>
					</xsl:attribute>
					<message>Externe Datei nicht spezifiziert</message>
					<hint></hint>
			</failure>
		</xsl:if>
		<xsl:if test="$recursive"><xsl:apply-templates select="document(concat($srcdir,$path,'/',@quelle))"/></xsl:if>
	</xsl:template>
	
	<xsl:template match="kurseinheit[@extern!='ja']">
		<xsl:if test="not(ke-lehrtext)">
			<failure type="warning">
					<xsl:attribute name="element"><xsl:value-of select="name(.)"/></xsl:attribute>
					<xsl:attribute name="xpath"><xsl:value-of select="saxon:path()"/></xsl:attribute>
					<xsl:attribute name="file">
						<xsl:call-template name="get.relativepath">
							<xsl:with-param name="baseuri" select="$srcdir"/>
							<xsl:with-param name="uri" select="base-uri(current())"/>
						</xsl:call-template>/<xsl:call-template name="get.filename">
							<xsl:with-param name="mediaref" select="base-uri(current())"/>
						</xsl:call-template>
					</xsl:attribute>
					<message><xsl:value-of select="$NOCUDEFINED"/></message>
					<hint><xsl:value-of select="$NOCUDEFINEDHINT"/></hint>
			</failure>
		</xsl:if>
	</xsl:template>
	
	<!-- check if cross references can be resolved -->
	<xsl:template match="glossarref|internref|margref|litref|querverweis">
		<xsl:variable name="path">
				<xsl:call-template name="get.relativepath">
					<xsl:with-param name="baseuri" select="$srcdir"/>
					<xsl:with-param name="uri" select="base-uri(current())"/>
				</xsl:call-template>
		</xsl:variable>

		<xsl:choose>
			<!-- Case 1: target ID in other document-->
			<xsl:when test="contains(@zielmarke,'#')">
				<xsl:variable name="refdoc" select="document(concat($srcdir,$path,'/',substring-before(@zielmarke,'#')))"/>
				<xsl:variable name="zielmarke" select="substring-after(@zielmarke,'#')"/>
				<xsl:if test="not($refdoc//@id[.=$zielmarke])">
					<xsl:call-template name="report.xref.failure"/>
				</xsl:if>
			</xsl:when>
			<!-- Case 2: target id in same document -->
			<xsl:otherwise>
				<xsl:if test="not(key('id_key',@zielmarke))">
					<xsl:call-template name="report.xref.failure"/>
				</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="report.xref.failure">
		<failure type="error">
			<xsl:attribute name="element"><xsl:value-of select="name(.)"/></xsl:attribute>
			<xsl:attribute name="xpath"><xsl:value-of select="saxon:path()"/></xsl:attribute>
			<xsl:attribute name="file">
				<xsl:call-template name="get.relativepath">
					<xsl:with-param name="baseuri" select="$srcdir"/>
					<xsl:with-param name="uri" select="base-uri(current())"/>
				</xsl:call-template>/<xsl:call-template name="get.filename">
					<xsl:with-param name="mediaref" select="base-uri(current())"/>
				</xsl:call-template>
			</xsl:attribute>
			<message>[<xsl:value-of select="."/>]: <xsl:value-of select="$XREFNOTFOUND"/></message>
			<hint><xsl:value-of select="$XREFHINT"/></hint>
		</failure>
	</xsl:template>
	
	
	<!-- check CU range information for validity --> 
	<xsl:template match="ke-lehrtext">
		<!--xsl:variable name="custart_refdoc" select="document(concat($srcdir,substring-before(@bereich-start,'#')))"/>
		<xsl:variable name="cuend_refdoc" select="document(concat($srcdir,substring-before(@bereich-ende,'#')))"/-->
		<xsl:variable name="path">
			<xsl:call-template name="get.relativepath">
				<xsl:with-param name="baseuri" select="$srcdir"/>
				<xsl:with-param name="uri" select="base-uri(current())"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:choose>
			<!-- range values exist? -->
			<xsl:when test="not(@bereich-start and @bereich-ende)">
				<failure type="error">
					<xsl:attribute name="element"><xsl:value-of select="name(.)"/></xsl:attribute>
					<xsl:attribute name="xpath"><xsl:value-of select="saxon:path()"/></xsl:attribute>
					<xsl:attribute name="file">
						<xsl:call-template name="get.relativepath">
							<xsl:with-param name="baseuri" select="$srcdir"/>
							<xsl:with-param name="uri" select="base-uri(current())"/>
						</xsl:call-template>/<xsl:call-template name="get.filename">
							<xsl:with-param name="mediaref" select="base-uri(current())"/>
						</xsl:call-template>
					</xsl:attribute>
					<message><xsl:value-of select="$CUNORANGE"/></message>
					<hint><xsl:value-of select="$CUNORANGEHINT"/></hint>
				</failure>
			</xsl:when>
			
			<xsl:otherwise>				
				<!-- Can referenced Elements be resolved?-->
				<xsl:choose>
					<!-- range start -->
					<xsl:when test="contains(@bereich-start,'#')">
						<xsl:variable name="refdoc" select="document(concat($srcdir,$path,'/',substring-before(@bereich-start,'#')))"/>
						<xsl:variable name="zielmarke" select="substring-after(@bereich-start,'#')"/>
						<xsl:if test="not($refdoc//@id[.=$zielmarke])">
							<xsl:call-template name="report.curange.failure">
								<xsl:with-param name="rangeatt" select="name(@bereich-start)"/>
							</xsl:call-template>
						</xsl:if>
					</xsl:when>
					<xsl:otherwise>
						<xsl:if test="not(key('id_key',@bereich-start))">
							<xsl:call-template name="report.curange.failure">
								<xsl:with-param name="rangeatt" select="name(@bereich-start)"/>
							</xsl:call-template>
						</xsl:if>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:choose>
					<!-- range end -->
					<xsl:when test="contains(@bereich-ende,'#')">
						<xsl:variable name="refdoc" select="document(concat($srcdir,$path,'/',substring-before(@bereich-ende,'#')))"/>
						<xsl:variable name="zielmarke" select="substring-after(@bereich-ende,'#')"/>
						<xsl:if test="not($refdoc//@id[.=$zielmarke])">
							<xsl:call-template name="report.curange.failure">
									<xsl:with-param name="rangeatt" select="name(@bereich-ende)"/>
							</xsl:call-template>
						</xsl:if>
					</xsl:when>
					<xsl:otherwise>
						<xsl:if test="not(key('id_key',@bereich-ende))">
							<xsl:call-template name="report.curange.failure">
								<xsl:with-param name="rangeatt" select="name(@bereich-ende)"/>
							</xsl:call-template>
						</xsl:if>
					</xsl:otherwise>
				</xsl:choose>
				
				
				<!-- Does CU start appear before CU end? -->
				<!-- XPath 2.0 expression "precedes" or "follows" required! -->
				<!--xsl:if test="key('id_key',@bereich-start) follows key('id_key',@bereich-ende)">
					<failure type="fatal">
						<xsl:attribute name="element"><xsl:value-of select="name(.)"/></xsl:attribute>
						<xsl:attribute name="xpath"><xsl:value-of select="saxon:path()"/></xsl:attribute>
						<message><xsl:value-of select="$CUENDBEFORESTART"/><xsl:text>&#x0a;</xsl:text>
						</message>
						<hint><xsl:value-of select="$CUENDBEFORESTARTHINT"/></hint>
					</failure>
				</xsl:if-->

			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="report.curange.failure">
		<xsl:param name="rangeatt"/>
		<failure type="error">
			<xsl:attribute name="element"><xsl:value-of select="$rangeatt"/></xsl:attribute>
			<xsl:attribute name="xpath"><xsl:value-of select="saxon:path()"/></xsl:attribute>
			<xsl:attribute name="file">
				<xsl:call-template name="get.relativepath">
					<xsl:with-param name="baseuri" select="$srcdir"/>
					<xsl:with-param name="uri" select="base-uri(current())"/>
				</xsl:call-template>/<xsl:call-template name="get.filename">
					<xsl:with-param name="mediaref" select="base-uri(current())"/>
				</xsl:call-template>
			</xsl:attribute>
			<message><xsl:value-of select="$CURANGENOTFOUND"/></message>
			<hint><xsl:value-of select="$CURANGENOTFOUNDHINT"/></hint>
		</failure>
	</xsl:template>

	<xsl:template match="formel|formel-imtext">
		<xsl:if test="contains(.,'$') and not(contains(.,'\$')) and not(contains(.,'\text'))">
		<failure type="warning">
			<xsl:attribute name="element"><xsl:value-of select="name(.)"/></xsl:attribute>
			<xsl:attribute name="xpath"><xsl:value-of select="saxon:path()"/></xsl:attribute>
			<xsl:attribute name="file">
				<xsl:call-template name="get.relativepath">
					<xsl:with-param name="baseuri" select="$srcdir"/>
					<xsl:with-param name="uri" select="base-uri(current())"/>
				</xsl:call-template>/<xsl:call-template name="get.filename">
					<xsl:with-param name="mediaref" select="base-uri(current())"/>
				</xsl:call-template>
			</xsl:attribute>
			<message><xsl:value-of select="$DOLLARINLATEXSYNTAX"/><xsl:text>&#x0a;</xsl:text>
			<xsl:value-of select="."/>
			</message>
			<hint><xsl:value-of select="$DOLLARINLATEXSYNTAXHINT"/></hint>
		</failure>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="textobjekt">
		<xsl:variable name="path">
			<xsl:call-template name="get.relativepath">
				<xsl:with-param name="baseuri" select="$srcdir"/>
				<xsl:with-param name="uri" select="base-uri(current())"/>
			</xsl:call-template>
		</xsl:variable>
		
		<xsl:variable name="suffix">
			<xsl:call-template name="get.mediasuffix">
				<xsl:with-param  name="mediaref" select="@fileref"/>
			</xsl:call-template>
		</xsl:variable>
		
		<xsl:if test="$suffix='xml'">
			<xsl:if test="$recursive"><xsl:apply-templates select="document(concat($srcdir,$path,'/',@fileref))"/></xsl:if>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="tgroup">
		<!-- colspec elements exist? -->
		<xsl:if test="not(colspec)">
			<failure type="error">
				<xsl:attribute name="element"><xsl:value-of select="name(.)"/></xsl:attribute>
				<xsl:attribute name="xpath"><xsl:value-of select="saxon:path()"/></xsl:attribute>
				<xsl:attribute name="file">
					<xsl:call-template name="get.relativepath">
						<xsl:with-param name="baseuri" select="$srcdir"/>
						<xsl:with-param name="uri" select="base-uri(current())"/>
					</xsl:call-template>/<xsl:call-template name="get.filename">
						<xsl:with-param name="mediaref" select="base-uri(current())"/>
					</xsl:call-template>
				</xsl:attribute>
				<message><xsl:value-of select="$COLSPECMISSINGERROR"/><xsl:text>&#x0a;</xsl:text>
				</message>
				<hint><xsl:value-of select="$COLSPECMISSINGERRORHINT"/></hint>
			</failure>
		</xsl:if>
		<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="colspec">
		<xsl:if test="not(@colnum)">
			<failure type="fatal">
				<xsl:attribute name="element"><xsl:value-of select="name(.)"/></xsl:attribute>
				<xsl:attribute name="xpath"><xsl:value-of select="saxon:path()"/></xsl:attribute>
				<xsl:attribute name="file">
					<xsl:call-template name="get.relativepath">
						<xsl:with-param name="baseuri" select="$srcdir"/>
						<xsl:with-param name="uri" select="base-uri(current())"/>
					</xsl:call-template>/<xsl:call-template name="get.filename">
						<xsl:with-param name="mediaref" select="base-uri(current())"/>
					</xsl:call-template>
				</xsl:attribute>
				<message><xsl:value-of select="$COLSPECERROR"/><xsl:text>&#x0a;</xsl:text>
				</message>
				<hint><xsl:value-of select="$COLSPECERRORHINT"/></hint>
			</failure>
		</xsl:if>
	</xsl:template>


	<xsl:template match="text()"/>
</xsl:stylesheet>
