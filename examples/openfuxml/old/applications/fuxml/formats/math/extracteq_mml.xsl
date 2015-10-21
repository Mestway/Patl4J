<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
<xsl:output method="text" encoding="ISO-8859-1"/>
<xsl:strip-space elements="*"/>


<xsl:template match="kurs">
\documentclass{article}
\usepackage{tex4ht}
\usepackage{amsmath}
\usepackage{amsfonts}
\usepackage{amssymb}

\begin{document}
<xsl:apply-templates/>
\end{document}
</xsl:template>

<xsl:template match="formel|formel_x">
\begin{equation*}<xsl:value-of select="."/>\end{equation*}
</xsl:template>

<xsl:template match="formel[parent::formelarray]|formel[parent::formelarray_x]">
<xsl:value-of select="."/><xsl:if test="position()!=last()"><xsl:text xml:space="preserve">\\&#xD;&#xA;</xsl:text></xsl:if>
</xsl:template>

<xsl:template match="formelarray|formelarray_x">
\begin{eqnarray*}<xsl:apply-templates/>\end{eqnarray*}
</xsl:template>

<xsl:template match="mathe">
\begin{equation*}<xsl:value-of select="."/>\end{equation*}
</xsl:template>



<!-- xsl:template match="formel_x">
	<xsl:variable name="filename"><xsl:value-of select="generate-id()"/>.tex</xsl:variable>
	<xsl:result-document href="{$filename}">
		<xsl:call-template name="pre"/>
		\begin{equation*}<xsl:value-of select="."/>\end{equation*}
		<xsl:call-template name="post"/>
	</xsl:result-document>
	<xsl:text>dbmlatex </xsl:text><xsl:value-of select="$filename"/><text xml:space="preserve">&#xD;&#xA;</text>
</xsl:template-->

<!--xsl:template match="formel[parent::formelarray]|formel[parent::formelarray_x]">
<xsl:value-of select="."/><xsl:if test="position()!=last()"><xsl:text xml:space="preserve">\\&#xD;&#xA;</xsl:text></xsl:if>
</xsl:template-->

<!--xsl:template match="EquationNoNum">
\begin{equation*}<xsl:value-of select="."/>\end{equation*}
</xsl:template>

<xsl:template match="EquationArrayNoNum">
<xsl:value-of select="generate-id()"/>
\begin{eqnarray*}<xsl:value-of select="."/>\end{eqnarray*}
</xsl:template>

<xsl:template match="InlineMath">
<xsl:value-of select="generate-id()"/>
\begin{equation*}<xsl:value-of select="."/>\end{equation*}
</xsl:template-->

<xsl:template match="text()|@*"/>

<xsl:template name="pre">
\documentclass{article}
\usepackage{tex4ht}
\usepackage{amsmath}
\usepackage{amsfonts}
\usepackage{amssymb}

\begin{document}
</xsl:template>

<xsl:template name="post">
\end{document}
</xsl:template>
</xsl:stylesheet>

