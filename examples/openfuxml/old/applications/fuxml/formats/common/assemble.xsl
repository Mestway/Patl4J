<?xml version="1.0" encoding="UTF-8"?>
<!--
	*******************************************************************************
	| openFuXML open source                                                       |
	*******************************************************************************
	| Copyright (c) 2002-2006 openFuXML open source, University of Hagen          |
	|                                                                             |
	| This program is free software; you can redistribute it and/or               |
	| modify it under the terms of the GNU General Public License                 |
	| as published by the Free Software Foundation; either version 2              |
	| of the License, or (at your option) any later version.                      |
	|                                                                             |
	| This program is distributed in the hope that it will be useful,             |
	| but WITHOUT ANY WARRANTY; without even the implied warranty of              |
	| MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the               |
	| GNU General Public License for more details.                                |
	|                                                                             |
	| You should have received a copy of the GNU General Public License           |
	| along with this program; if not, write to the Free Software                 |
	| Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. |
	*******************************************************************************
		
	- Assembly of distributed documents
	- globalisation of local IDs (footnote, marginals, indexfix, litentry, etc.)
	- generation of IDs for elements without ID
-->

<xsl:stylesheet 	version="2.0" 
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:xs="http://www.w3.org/2001/XMLSchema"
		xmlns:xlink="http://www.w3.org/TR/xlink/" 
		xmlns:saxon="http://saxon.sf.net/"
		xmlns:fuxml="http://www.fernuni-hagen.de/fuxml/">

	<xsl:param name="root-dir">file:/I:/repository-lokal/testprojekt_ks</xsl:param>
	<xsl:param name="linkwatch-file">file:///I:/repository-lokal/testprojekt_ks/config/linkwatch.xml</xsl:param>
	<xsl:param name="request-file">file:///I:/output/testprojekt_ks/common/strukturtest/request.xml</xsl:param>
	<xsl:param name="documentname">strukturtest</xsl:param>
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="no"/>

<xsl:strip-space elements="*"/>
	
<xsl:key name="marginalie_key" match="marginalie" use="@id"/>
<xsl:key name="margref_key" match="margref" use="@id"/>
<xsl:key name="fussnote_key" match="fussnote" use="@id"/>
<xsl:key name="fnref_key" match="fnref" use="@id"/>
<xsl:key name="liteintrag_key" match="liteintrag" use="@id"/>


<xsl:include href="preservespace.xsl"/>
<xsl:include href="mediainformation.xsl"/>

<xsl:variable name="linkwatch" select="document($linkwatch-file)"/>
<xsl:variable name="request" select="document($request-file)"/>
<xsl:variable name="linkredirect" select="
	if ($request//linkwatch[@active='true'] 
		or $request//option[@name='linkredirect' and @value='true'])
	then true()
	else false()"/>

<!-- default rule for nodes: copy and if id does not exist, append a generated id -->
<!-- XMetaL provides numbered IDs idx1, idx2, etc. which lead to collisions between elements in different documents.
       These IDs are not required and are overwritten -->

<xsl:template match="node()">
	<!--xsl:message>Assemble: <xsl:value-of select="@id"/></xsl:message-->
	<xsl:copy>
		<xsl:if test="not(@id)">
			<xsl:attribute name="id"><xsl:value-of select="generate-id()"/></xsl:attribute>
		</xsl:if>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:copy>
</xsl:template>

<!--xsl:template match="@id">
	<xsl:attribute name="id" select="my:normalise-id()"/>
</xsl:template-->

<xsl:template match="indexfix">
	<xsl:copy>
		<xsl:attribute name="id"><xsl:value-of select="generate-id()"/></xsl:attribute>
		<xsl:copy-of select="@*[name()!='id']"/>
		<xsl:apply-templates select="node()"/>
	</xsl:copy>
</xsl:template>


<!-- The following elements are provided with information about their source file -->
<xsl:template match="abschnitt">
	<!--xsl:message>Assemble: <xsl:value-of select="titel|objekttitel"/></xsl:message-->
	<xsl:copy>
		<xsl:if test="not(@id)">
			<xsl:attribute name="id"><xsl:value-of select="generate-id()"/></xsl:attribute>
		</xsl:if>
		<xsl:attribute name="sourcefile">
			<xsl:value-of select="substring-after(xs:string(base-uri(current())),$root-dir)"/>
		</xsl:attribute>
		<xsl:apply-templates select="@*|node()"/>
		
	</xsl:copy>
</xsl:template>
	
<xsl:template match="grafik|bild|animation|simulation|video|audio">
	<!--xsl:message>Assemble: <xsl:value-of select="titel|objekttitel"/></xsl:message-->
	<xsl:copy>
		<xsl:if test="not(@id)">
			<xsl:attribute name="id"><xsl:value-of select="generate-id()"/></xsl:attribute>
		</xsl:if>
		<xsl:attribute name="sourcefile">
			<xsl:value-of select="substring-after(xs:string(base-uri(current())),$root-dir)"/>
		</xsl:attribute>
		<!--xsl:attribute name="absolutefileref">
			<xsl:value-of select="substring-after(resolve-uri(@fileref,base-uri(current())),$root-dir)"/>
		</xsl:attribute-->
		<xsl:apply-templates select="@*|node()"/>		
	</xsl:copy>
</xsl:template>

<!-- Linkwatch implementation -->
<xsl:template match="url">
	<xsl:variable name="sourcefile"><xsl:value-of select="substring-after(xs:string(base-uri(current())),$root-dir)"/>
<!--xsl:value-of select="base-uri(current())"/--></xsl:variable>
	<xsl:variable name="datei">
		<xsl:call-template name="get.filename">
			<xsl:with-param name="mediaref" select="xs:string(base-uri(current()))"/>
		</xsl:call-template>
	</xsl:variable>
	<xsl:variable name="position"><xsl:value-of select="count(preceding::url) + 1"/></xsl:variable>
	<xsl:copy>
		<xsl:if test="not(@id)">
			<xsl:attribute name="id"><xsl:value-of select="generate-id()"/></xsl:attribute>
		</xsl:if>
		<!-- replace URL with redirect address from linkwatch file -->
		<xsl:attribute name="adresse">
			<xsl:choose>
				<xsl:when test="$linkredirect">
					<xsl:choose>
						<xsl:when test="$linkwatch/redirmap/datei[@name=$datei]/url[position=$position]">
							<xsl:value-of select="$linkwatch/redirmap/datei[@name=$datei]/url[position=$position]/redir"/>
							<xsl:message>Redirecting URL: <xsl:value-of select="@adresse"/> to <xsl:value-of select="$linkwatch/redirmap/datei[@name=$datei]/url[position=$position]/redir"/></xsl:message>
						</xsl:when>
						<xsl:otherwise><xsl:value-of select="@adresse"/></xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="@adresse"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		<!-- Set position and save original URL -->
		<xsl:if test="$linkredirect and $linkwatch/redirmap/datei[@name=$datei]/url[position=$position]">
			<xsl:attribute name="position">
				<xsl:value-of select="$linkwatch/redirmap/datei[@name=$datei]/url[position=$position]/position"/>
			</xsl:attribute>
			<xsl:attribute name="originallink"><xsl:value-of select="@adresse"/></xsl:attribute>
		</xsl:if>
		<xsl:copy-of select="@bezeichner"/>
		<xsl:attribute name="sourcefile"><xsl:value-of select="$sourcefile"/></xsl:attribute>
		<xsl:apply-templates select="node()"/>
	</xsl:copy>
</xsl:template>

<xsl:template match="@*"><xsl:copy-of select="."/></xsl:template>


<!-- Assembly of external files --> 
<xsl:template match="*[@extern='ja']">
	<xsl:variable name="path">
		<xsl:call-template name="get.relativepath">
			<xsl:with-param name="baseuri" select="$root-dir"/>
			<xsl:with-param name="uri" select="xs:string(base-uri(current()))"/>
		</xsl:call-template>
	</xsl:variable>
	
	<xsl:variable name="filename">
		<xsl:choose>
			<xsl:when test="contains(@quelle,'#')"><xsl:value-of select="substring-before(@quelle,'#')"/></xsl:when>
			<xsl:otherwise><xsl:value-of select="@quelle"/></xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:variable name="exid"><xsl:value-of select="substring-after(@quelle,'#')"/></xsl:variable>
	<xsl:variable name="elname">
		<xsl:choose>
			<xsl:when test="$exid != '' "><xsl:value-of select="name(document(concat($root-dir,$path,'/',$filename))//*[@id=$exid])"/></xsl:when>
			<xsl:otherwise><xsl:value-of select="name(document(concat($root-dir,$path,'/',$filename))/*[1])"/></xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:processing-instruction name="file"><xsl:value-of select="$filename"/></xsl:processing-instruction>
		<xsl:message>Loading: Element <xsl:value-of select="$elname"/> in <xsl:value-of select="$filename"/> at position <xsl:value-of select="saxon:path()"/></xsl:message>
		<xsl:choose>
			<xsl:when test="$exid != '' ">
				<xsl:apply-templates select="document(concat($root-dir,$path,'/',$filename))//node()[@id=$exid]"/>
			</xsl:when>
			<xsl:otherwise>
	       		<xsl:apply-templates select="document(concat($root-dir,$path,'/',$filename))"/>
			</xsl:otherwise>
		</xsl:choose>
</xsl:template>

<!-- File information, which is the part befor "#",  is not needed in links, since all files are assembled here -->
<xsl:template match="@bereich-start[contains(.,'#')]"><xsl:attribute name="bereich-start"><xsl:value-of select="substring-after(.,'#')"/></xsl:attribute></xsl:template>

<xsl:template match="@bereich-ende[contains(.,'#')]"><xsl:attribute name="bereich-ende"><xsl:value-of select="substring-after(.,'#')"/></xsl:attribute></xsl:template>

<xsl:template match="@zielmarke[contains(.,'#')]"><xsl:attribute name="zielmarke"><xsl:value-of select="substring-after(.,'#')"/></xsl:attribute></xsl:template>


<!-- globalisation of local IDs, IDs are augmented with generated IDs -->
<xsl:template match="fnref">
	<xsl:copy>
		<xsl:attribute name="zielmarke"><xsl:apply-templates select="key('fussnote_key',@zielmarke)" mode="generateid"/></xsl:attribute>
		<xsl:attribute name="id"><xsl:value-of select="@id"/><xsl:value-of select="generate-id()"/></xsl:attribute>
	</xsl:copy>
</xsl:template>

<xsl:template match="fnref" mode="generateid"><xsl:value-of select="generate-id()"/></xsl:template>

<xsl:template match="fussnote">
	<xsl:copy>
		<xsl:attribute name="zielmarke"><xsl:value-of select="@zielmarke"/><xsl:apply-templates select="key('fnref_key',@zielmarke)" mode="generateid"/></xsl:attribute>
		<xsl:attribute name="id"><xsl:value-of select="@id"/><xsl:value-of select="generate-id()"/></xsl:attribute>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="fussnote" mode="generateid"><xsl:value-of select="@id"/><xsl:value-of select="generate-id()"/></xsl:template>

<xsl:template match="querverweis[key('fussnote_key',@zielmarke)]">
	<xsl:copy>
		<xsl:attribute name="zielmarke"><xsl:apply-templates select="key('fussnote_key',@zielmarke)" mode="generateid"/></xsl:attribute>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>
	
<xsl:template match="querverweis[key('marginalie_key',@zielmarke)]">
	<xsl:copy>
		<xsl:attribute name="zielmarke"><xsl:value-of select="@zielmarke"/><xsl:apply-templates select="key('marginalie_key',@zielmarke)" mode="generateid"/></xsl:attribute>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="margref">
	<xsl:copy>
		<xsl:attribute name="zielmarke"><xsl:value-of select="@zielmarke"/><xsl:apply-templates select="key('marginalie_key',@zielmarke)" mode="generateid"/></xsl:attribute>
		<xsl:attribute name="id"><xsl:value-of select="@id"/><xsl:value-of select="generate-id()"/></xsl:attribute>
	</xsl:copy>
</xsl:template>

<xsl:template match="margref" mode="generateid"><xsl:value-of select="generate-id()"/></xsl:template>

<xsl:template match="marginalie">
	<xsl:copy>
		<xsl:attribute name="zielmarke"><xsl:value-of select="@zielmarke"/><xsl:apply-templates select="key('margref_key',@zielmarke)" mode="generateid"/></xsl:attribute>
		<xsl:attribute name="id"><xsl:value-of select="@id"/><xsl:value-of select="generate-id()"/></xsl:attribute>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="marginalie" mode="generateid"><xsl:value-of select="generate-id()"/></xsl:template>

<!-- liteintrag are globalised according to the following scheme: <path-within-project><document><id> -->
<xsl:template match="litref">
	<xsl:copy>
		<xsl:attribute name="zielmarke">
			<xsl:choose>
				<xsl:when test="contains(@zielmarke,'#')">
					<xsl:variable name="exid">
						<xsl:value-of select="substring-after(@zielmarke,'#')"/>
					</xsl:variable>
					<xsl:variable name="newid">
						<xsl:apply-templates select="document(substring-before(xs:string(resolve-uri(escape-uri(@zielmarke,false()),base-uri(current()))),'#'))//liteintrag[@id=$exid]" mode="generateid"/>
					</xsl:variable>
					<!--xsl:message>uri <xsl:value-of select="resolve-uri(escape-uri(@zielmarke,false()),base-uri(current()))"/></xsl:message-->
					<xsl:message>Replacing litref <xsl:value-of select="@zielmarke"/> with <xsl:value-of select="$newid"/></xsl:message>
					<xsl:value-of select="$newid"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates select="key('liteintrag_key',@zielmarke)" mode="generateid"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
	</xsl:copy>
</xsl:template>

<xsl:template match="liteintrag">
	<xsl:copy>
		<xsl:attribute name="id"><xsl:apply-templates select="current()" mode="generateid"/></xsl:attribute>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="liteintrag" mode="generateid">
	<xsl:variable name="path">
		<xsl:call-template name="get.relativepath">
			<xsl:with-param name="baseuri" select="escape-uri($root-dir,false())"/>
			<xsl:with-param name="uri" select="xs:string(base-uri(current()))"/>
		</xsl:call-template>
	</xsl:variable>
	<xsl:variable name="medianame">
		<xsl:call-template name="get.medianame">
			<xsl:with-param name="mediaref" select="xs:string(base-uri(current()))"/>
		</xsl:call-template>
	</xsl:variable>
	<xsl:value-of select="concat(replace($path,'/',''),'',$medianame,'',@id)"/>
</xsl:template>
	
<!--  -->
<xsl:template match="textobjekt">
	<xsl:variable name="path">
		<xsl:call-template name="get.relativepath">
			<xsl:with-param name="baseuri" select="$root-dir"/>
			<xsl:with-param name="uri" select="xs:string(base-uri(current()))"/>
		</xsl:call-template>
	</xsl:variable>
	<xsl:variable name="suffix">
		<xsl:call-template name="get.mediasuffix">
			<xsl:with-param name="mediaref" select="@fileref"/>
		</xsl:call-template>
	</xsl:variable>
	<xsl:copy>
		<xsl:apply-templates select="@*|node()"/>
		<xsl:if test="$suffix='xml'">
			<xsl:message>Loading Textobject: <xsl:value-of select="@fileref"/> at position  <xsl:value-of select="saxon:path()"/></xsl:message>
			<xsl:processing-instruction name="file"><xsl:value-of select="@fileref"/></xsl:processing-instruction>
			<xsl:apply-templates select="document(concat($root-dir,$path,'/',@fileref))"/>
		</xsl:if>
	</xsl:copy>
</xsl:template>



</xsl:stylesheet>
