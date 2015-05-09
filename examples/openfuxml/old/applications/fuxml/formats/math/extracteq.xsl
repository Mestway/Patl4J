<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text"/>
<xsl:template match="kurs">
\documentclass{article}
\usepackage[latin1]{inputenc}
\usepackage{ngerman}
\usepackage{a4}
\usepackage{t1enc} %Fuer Umlaute in Trennungsliste
    
\usepackage{tex4ht}
\usepackage{amsmath}
\usepackage{amsfonts}
\usepackage{amssymb}

\begin{document}
<xsl:apply-templates/>
\end{document}
</xsl:template>

<xsl:template match="formel|formel_num">
\Picture+[formel]{<xsl:value-of select="generate-id()"/>.gif}
\begin{equation*}
<xsl:value-of select="."/>
\end{equation*}
\EndPicture
</xsl:template>

<xsl:template match="formel[ancestor::formelarray]|formel[ancestor::formelarray_num]|formel_num[ancestor::formelarray]|formel_num[ancestor::formelarray_num]">
<xsl:value-of select="."/><xsl:if test="position()!=last()"><xsl:text xml:space="preserve">\\&#xD;&#xA;</xsl:text></xsl:if>
</xsl:template>

<xsl:template match="formelarray|formelarray_num">
\Picture+[formel]{<xsl:value-of select="generate-id()"/>.gif}
\begin{eqnarray*}
<xsl:apply-templates select="descendant::formel|descendant::formel_num"/>
\end{eqnarray*}
\EndPicture
</xsl:template>

<xsl:template match="formel_imtext">
\Picture+[formel]{<xsl:value-of select="generate-id()"/>.gif}
\begin{equation*}
<xsl:value-of select="."/>
\end{equation*}
\EndPicture
</xsl:template>

<xsl:template match="text()|@*"/>

</xsl:stylesheet>

