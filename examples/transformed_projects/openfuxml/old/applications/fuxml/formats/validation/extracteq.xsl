<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:saxon="http://saxon.sf.net/">
<xsl:param name="documentname">document</xsl:param>
<xsl:output method="text"/>
<xsl:variable name="config" select="document('../common/config.xml')"/>
<xsl:variable name="styles" select="$config/config/styles"/>
<xsl:include href="../common/projectsettings.xsl"/>

<xsl:param name="srcdir">file:///I:/repository/fuxml/lgks_krypto/</xsl:param>
<xsl:include href="../common/mediainformation.xsl"/>
<xsl:param name="request">file:///I:/output/testprojekt_ks/validation/validationtest/request.xml</xsl:param>
<xsl:variable name="recursive" select="
	if (document($request)//recursion[@active='true'] 
			or document($request)//option[@name='recursive' and @value='true']
		or document($request)//file[@filename='report_all.html']) 
	then true()
	else false()"/>

<!--xsl:include href="../latexpdf/abschnitt.xsl"/-->
<!--xsl:include href="../latexpdf/designinterface-latexpdf.xsl"/-->



<xsl:template match="/">
\documentclass[12pt,twoside,fleqn]{book}
\usepackage{german}
\usepackage{a4}
\usepackage{t1enc} %Fuer Umlaute in Trennungsliste
\usepackage{graphicx}
\usepackage{amsfonts}
\usepackage{amsmath}
\usepackage{amssymb}
\usepackage{psfrag}%Math in eps graphics
\usepackage{syntonly}
\syntaxonly

%User commands, Hyphenation
\makeatletter
\@input{_config}
\makeatother

		\begin{document}
				<xsl:apply-templates/>
		\end{document}		
	</xsl:template>
	
		<!--=============================================================-->
	<xsl:template match="formel[@num='an'][not(ancestor::formelarray)]">\begin{equation}<xsl:value-of select="normalize-space(.)"/>\tag{<xsl:value-of select="@number"/>}\end{equation}<xsl:call-template name="append_id"/><xsl:call-template name="crlf"/></xsl:template>
	<!--=============================================================-->
	<xsl:template match="formel">\begin{equation*}<xsl:value-of select="normalize-space(.)"/>\end{equation*}<xsl:call-template name="append_id"/><xsl:call-template name="crlf"/></xsl:template>
	<!--=============================================================-->
	<xsl:template match="formel[@num='an'][ancestor::formelarray[@num='an']]"><xsl:value-of select="normalize-space(.)"/><xsl:call-template name="append_label"/>\tag{<xsl:value-of select="@number"/>}<xsl:if test="position()!=last()">\\</xsl:if><xsl:call-template name="append_id"/><xsl:if test="position()!=last()"><xsl:call-template name="crlf"/></xsl:if></xsl:template>
	<!--=============================================================-->
	<xsl:template match="formel[@num='aus'][ancestor::formelarray[@num='an']]"><xsl:value-of select="normalize-space(.)"/>\notag<xsl:if test="position()!=last()">\\ </xsl:if><xsl:call-template name="append_id"/><xsl:if test="position()!=last()"><xsl:call-template name="crlf"/></xsl:if></xsl:template>
	<!--=============================================================-->
	<xsl:template match="formel[ancestor::formelarray[@num='aus']]"><xsl:value-of select="normalize-space(.)"/>\notag<xsl:if test="position()!=last()">\\ </xsl:if><xsl:call-template name="append_id"/><xsl:if test="position()!=last()"><xsl:call-template name="crlf"/></xsl:if></xsl:template>
	<!--=============================================================-->
	<xsl:template match="formelarray[@num='an']">\begin{align}
	<xsl:apply-templates select="descendant::formel"/>
	\end{align}<!--xsl:call-template name="append_id"/><xsl:call-template name="crlf"/-->
	</xsl:template>
	<!--=============================================================-->
	<xsl:template match="formelarray[@num='aus' or not(@num)]">\begin{align*}
		<xsl:apply-templates select="descendant::formel"/>
		\end{align*}<xsl:call-template name="append_id"/><xsl:call-template name="crlf"/>
	</xsl:template>
	<!--=============================================================-->
	<!-- Space in the next line is not correctly displayed by Latex. It must be copied to the current line before the commen starts! -->
	<xsl:template match="formel-imtext"><xsl:if test=". != ''">$<xsl:value-of select="normalize-space(.)"/>$<xsl:call-template name="append_id"/><xsl:call-template name="crlf"/></xsl:if></xsl:template>
	<!--=============================================================-->
	<xsl:template match="formel-imtext[ancestor::buchstaeblich]">\(<xsl:value-of select="normalize-space(.)"/>\)</xsl:template>
	<!--=============================================================-->
	<xsl:template name="append_id">%id:<xsl:call-template name="get.relativepath">
				<xsl:with-param name="baseuri" select="$srcdir"/>
				<xsl:with-param name="uri" select="base-uri(current())"/>
			</xsl:call-template>/<xsl:call-template name="get.filename">
				<xsl:with-param name="mediaref" select="base-uri(current())"/>
			</xsl:call-template>#<xsl:value-of select="saxon:path()"/></xsl:template>
	<!--=============================================================-->
	<xsl:template name="append_label"> \label{<xsl:value-of select="@id"/>} </xsl:template>
	<!--=============================================================-->
	<xsl:template name="crlf"><xsl:text xml:space="preserve">&#xA;</xsl:text></xsl:template>



	<xsl:template match="*[@extern='ja']">
		<xsl:variable name="path">
			<xsl:call-template name="get.relativepath">
				<xsl:with-param name="baseuri" select="$srcdir"/>
				<xsl:with-param name="uri" select="base-uri(current())"/>
			</xsl:call-template>
		</xsl:variable>
		
		<xsl:if test="$recursive"><xsl:apply-templates select="document(concat($srcdir,$path,'/',@quelle))//(formel[not(parent::formelarray)]|formel-imtext|formelarray)"/></xsl:if>
	</xsl:template>
	
	<xsl:template match="textobjekt">
		<xsl:variable name="suffix">
			<xsl:call-template name="get.mediasuffix">
				<xsl:with-param  name="mediaref" select="@fileref"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="$suffix='xml'">
				<xsl:variable name="path">
					<xsl:call-template name="get.relativepath">
						<xsl:with-param name="baseuri" select="$srcdir"/>
						<xsl:with-param name="uri" select="base-uri(current())"/>
					</xsl:call-template>
				</xsl:variable>
				
				<xsl:if test="$recursive"><xsl:apply-templates select="document(concat($srcdir,$path,'/',@fileref))//formel"/></xsl:if>
			</xsl:when>
			<xsl:otherwise>
			
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>


<xsl:template match="text()|@*"/>

</xsl:stylesheet>

