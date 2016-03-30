<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text"/>
<xsl:include href="../latexpdf/header.xsl"/>
<xsl:include href="../latexpdf/numbering.xsl"/>

<xsl:template match="kurs">
<xsl:apply-templates/>
</xsl:template>

<xsl:template name="header">
<xsl:call-template name="latexheader"/>
\usepackage{tex4ht}
\begin{document}
</xsl:template>

<xsl:template name="footer">
\end{document}
</xsl:template>

<xsl:template match="formel-num">
<xsl:result-document href="{@bezeichner}.tex">
<xsl:call-template name="header"/>
\Picture+[formel]{<xsl:value-of select="@bezeichner"/>.gif}
\begin{equation*}
<xsl:value-of select="."/>\tag{<xsl:call-template name="get.equation.number"/>}
\end{equation*}
\EndPicture
<xsl:call-template name="footer"/>
</xsl:result-document>
</xsl:template>

<xsl:template match="formel">
<xsl:result-document href="{@bezeichner}.tex">
<xsl:call-template name="header"/>
\Picture+[formel]{<xsl:value-of select="@bezeichner"/>.gif}
\begin{equation*}
<xsl:value-of select="."/>
\end{equation*}
\EndPicture
<xsl:call-template name="footer"/>
</xsl:result-document>
</xsl:template>

<xsl:template match="formel[ancestor::formelarray]">
<xsl:value-of select="."/><xsl:if test="position()!=last()"><xsl:text xml:space="preserve">\\&#xD;&#xA;</xsl:text></xsl:if>
</xsl:template>

<xsl:template match="formel[ancestor::formelarray-num]">
<xsl:value-of select="."/><xsl:if test="position()!=last()"><xsl:text xml:space="preserve">\nonumber\\&#xD;&#xA;</xsl:text></xsl:if>
</xsl:template>

<xsl:template match="formel-num[ancestor::formelarray]">
<xsl:value-of select="."/>\tag{<xsl:call-template name="get.equation.number"/>}<xsl:if test="position()!=last()"><xsl:text xml:space="preserve">\\&#xD;&#xA;</xsl:text></xsl:if>
</xsl:template>

<xsl:template match="formel-num[ancestor::formelarray-num]">
<xsl:value-of select="."/>\tag{<xsl:call-template name="get.equation.number"/>}<xsl:if test="position()!=last()"><xsl:text xml:space="preserve">\\&#xD;&#xA;</xsl:text></xsl:if>
</xsl:template>


<xsl:template match="formelarray">
<xsl:result-document href="{@bezeichner}.tex">
<xsl:call-template name="header"/>
\Picture+[formel]{<xsl:value-of select="@bezeichner"/>.gif}
\begin{align*}
<xsl:apply-templates select="descendant::formel"/>
\end{align*}
\EndPicture
<xsl:call-template name="footer"/>
</xsl:result-document>
</xsl:template>

<xsl:template match="formelarray-num">
<xsl:result-document href="{@bezeichner}.tex">
<xsl:call-template name="header"/>
\Picture+[formel]{<xsl:value-of select="@bezeichner"/>.gif}
\begin{align}
<xsl:apply-templates select="descendant::formel|descendant::formel-num"/>
\end{align}
\EndPicture
<xsl:call-template name="footer"/>
</xsl:result-document>
</xsl:template>



<xsl:template match="formel-imtext">
<xsl:result-document href="{@bezeichner}.tex">
<xsl:call-template name="header"/>
\Picture+[formel]{<xsl:value-of select="@bezeichner"/>.gif}
\begin{equation*}
<xsl:value-of select="."/>
\end{equation*}
\EndPicture
<xsl:call-template name="footer"/>
</xsl:result-document>
</xsl:template>



<xsl:template match="text()|@*"/>




</xsl:stylesheet>

