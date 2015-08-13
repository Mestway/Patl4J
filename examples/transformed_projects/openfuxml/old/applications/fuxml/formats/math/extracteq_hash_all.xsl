<?xml version="1.0"?>
<!-- Creation of bitmap images for math elements.
	The name of the images is the hash value of the corresponding Latex expression
	There are no multiple images for the same Latex expression
 -->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:saxon="http://saxon.sf.net/">
 <xsl:output method="text"/>
<xsl:variable name="config" select="document('../common/config.xml')"/>
<xsl:variable name="docsettings" select="$config/config/documentsettings"/>
 <xsl:variable name="amssettings" select="$config/config/amsmathsettings"/>
<xsl:variable name="leqno"><xsl:if test="$docsettings/leqno='true'">leqno,</xsl:if></xsl:variable>
<xsl:variable name="fleqn"><xsl:if test="$docsettings/fleqn='true'">fleqn</xsl:if></xsl:variable>
 <xsl:variable name="centertags"><xsl:if test="$amssettings/centertags='false'">tbtags,</xsl:if></xsl:variable>
 <xsl:variable name="sumlimits"><xsl:if test="$amssettings/sumlimits='false'">nosumlimits,</xsl:if></xsl:variable>
 <xsl:variable name="intlimits"><xsl:if test="$amssettings/intlimits='true'">intlimits,</xsl:if></xsl:variable>
 <xsl:variable name="namelimits"><xsl:if test="$amssettings/namelimits='false'">nonamelimits</xsl:if></xsl:variable>
 <xsl:include href="../common/math.xsl"/>
 
<xsl:template match="/">
\documentclass[<xsl:value-of select="concat($leqno,$fleqn)"/>]{article}
 \usepackage[latin1]{inputenc}
 \usepackage{ngerman}
 \usepackage{times}
 \usepackage{a4}
 \usepackage{t1enc} %Fuer Umlaute in Trennungsliste
\usepackage{tex4ht}
\usepackage{amsfonts}
 \usepackage[<xsl:value-of select="concat($centertags,$sumlimits,$intlimits,$namelimits)"/>]{amsmath}
\usepackage{amssymb}
 %mathindent
 <xsl:if test="$amssettings/mathindent!=''">\setlength{\mathindent}{<xsl:value-of select="$amssettings/mathindent"/>}</xsl:if>
%User commands, Hyphenation
\makeatletter
\@input{_config}
\makeatother

\begin{document}

<xsl:apply-templates/>
\end{document}
</xsl:template>
 <!--=============================================================-->
<!-- single equation -->
 <xsl:template match="formel[not(ancestor::formelarray)]">
<!-- Only process equation if it did not occur before -->
<xsl:if test="not(preceding::*[@hash=current()/@hash])">
\Picture+[formel]{<xsl:value-of select="@hash"/>.png}
 \begin{equation*}
 <xsl:value-of select="normalize-space(.)"/><xsl:if test="@num='an'">\tag*{<xsl:value-of select="normalize-space(@number)"/>}</xsl:if>
 \end{equation*}
\EndPicture
</xsl:if>
</xsl:template>
 

 <!--=============================================================-->
 <!-- numbered equation in numbered equationarray -->
 <!--xsl:template match="formel[@num='an'][ancestor::formelarray[@num='an']]">
  <xsl:value-of select="normalize-space(.)"/>\tag{<xsl:value-of select="@posnumber"/>}<xsl:if test="position()!=last()"><xsl:text xml:space="preserve">\\&#xD;&#xA;</xsl:text></xsl:if>
 </xsl:template-->
 <!--=============================================================-->
<!-- equation in equationarray without number -->
<!--xsl:template match="formel[@num='aus'][ancestor::formelarray]">
<xsl:value-of select="normalize-space(.)"/><xsl:if test="position()!=last()"><xsl:text xml:space="preserve">\nonumber\\&#xD;&#xA;</xsl:text></xsl:if>
</xsl:template-->
 
 
 <!--=============================================================-->
 <!-- equationarray align -->
<xsl:template match="formelarray">
<xsl:if test="not(preceding::*[@hash=current()/@hash])">
\Picture+[formel]{<xsl:value-of select="@hash"/>.png}
\begin{align*}<xsl:call-template name="num.array"/>
 <xsl:apply-templates select="descendant::formel"/>
 \end{align*}
\EndPicture
</xsl:if>
</xsl:template>
<!--=============================================================-->
<xsl:template match="formelarray[@typ='gather']">
<xsl:if test="not(preceding::*[@hash=current()/@hash])">
\Picture+[formel]{<xsl:value-of select="@hash"/>.png}
\begin{gather*}<xsl:call-template name="num.array"/>
 <xsl:apply-templates select="descendant::formel"/>
 \end{gather*}
\EndPicture
</xsl:if>
</xsl:template>
<!--=============================================================-->
<xsl:template match="formelarray[@typ='alignat']">
<xsl:if test="not(preceding::*[@hash=current()/@hash])">
\Picture+[formel]{<xsl:value-of select="@hash"/>.png}
\begin{alignat*}{<xsl:call-template name="get.columns"/>}<xsl:call-template name="num.array"/>
 <xsl:apply-templates select="descendant::formel"/>
 \end{alignat*}
\EndPicture
</xsl:if>
</xsl:template>
<!--=============================================================-->
<xsl:template match="formelarray[@typ='xalignat']">
<xsl:if test="not(preceding::*[@hash=current()/@hash])">
\Picture+[formel]{<xsl:value-of select="@hash"/>.png}
\begin{xalignat*}{<xsl:call-template name="get.columns"/>}<xsl:call-template name="num.array"/>
 <xsl:apply-templates select="descendant::formel"/>
 \end{xalignat*}
\EndPicture
</xsl:if>
</xsl:template>
<!--=============================================================-->
 <!-- xxalignat must not be numbered !! -->
<xsl:template match="formelarray[@typ='xxalignat']">
<xsl:if test="not(preceding::*[@hash=current()/@hash])">
\Picture+[formel]{<xsl:value-of select="@hash"/>.png}
\begin{xxalignat}{<xsl:call-template name="get.columns"/>}<!--xsl:call-template name="num.array"/-->
 <xsl:apply-templates select="descendant::formel"/>
 \end{xxalignat}
\EndPicture
</xsl:if>
</xsl:template>
<!--=============================================================-->
<xsl:template match="formelarray[@typ='multiline']">
<xsl:if test="not(preceding::*[@hash=current()/@hash])">
\Picture+[formel]{<xsl:value-of select="@hash"/>.png}
\begin{multline*}<xsl:call-template name="num.array"/>
 <xsl:apply-templates select="descendant::formel"/>
 \end{multline*}
\EndPicture
</xsl:if>
</xsl:template>
<!--=============================================================-->

<xsl:template match="formel-imtext">
<xsl:if test="not(preceding::*[@hash=current()/@hash])">
\Picture+[formel]{<xsl:value-of select="@hash"/>.png}
\begin{equation*}<xsl:value-of select="normalize-space(.)"/>\end{equation*}
\EndPicture
</xsl:if>
</xsl:template>


<xsl:template match="text()|@*"/>


</xsl:stylesheet>

