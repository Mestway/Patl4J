<?xml version="1.0"?>
<!-- ********************************************************************
	$Id: math.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
	********************************************************************
	*******************************************************************************
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
	******************************************************************** -->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:saxon="http://saxon.sf.net/">
<xsl:include href="../common/math.xsl"/>
	<!--=============================================================-->
	<!-- single equation -->
	<xsl:template match="formel[not(ancestor::formelarray)]">\begin{equation*}<xsl:value-of select="."/><xsl:if test="@num='an'">\tag*{<xsl:value-of select="normalize-space(@number)"/>}</xsl:if>\end{equation*}<xsl:call-template name="crlf"/></xsl:template>

	<!--=============================================================-->
	<!-- different types of equationarrays                                                                                   -->
	<!--=============================================================-->	
	<!-- align equationarray -->
	<xsl:template match="formelarray">\begin{align*}<xsl:call-template name="num.array"/>
		<xsl:apply-templates select="descendant::formel"/>
		\end{align*}
	</xsl:template>
	<!--=============================================================-->
	<!-- gather equationarray -->
	<xsl:template match="formelarray[@typ='gather']">\begin{gather*}<xsl:call-template name="num.array"/>
	<xsl:apply-templates select="descendant::formel"/>
	\end{gather*}
	</xsl:template>
	<!--=============================================================-->
	<!-- alignat equationarray -->
	<xsl:template match="formelarray[@typ='alignat']">\begin{alignat*}{<xsl:call-template name="get.columns"/>}<xsl:call-template name="num.array"/>
	<xsl:apply-templates select="descendant::formel"/>
	\end{alignat*}
	</xsl:template>
	<!--=============================================================-->
	<!-- xalignat equationarray -->
	<xsl:template match="formelarray[@typ='xalignat']">\begin{xalignat*}{<xsl:call-template name="get.columns"/>}<xsl:call-template name="num.array"/>
	<xsl:apply-templates select="descendant::formel"/>
	\end{xalignat*}
	</xsl:template>
	<!--=============================================================-->
	<!-- xxalignat equationarray -->
	<xsl:template match="formelarray[@typ='xxalignat']">\begin{xxalignat}{<xsl:call-template name="get.columns"/>}
	<xsl:apply-templates select="descendant::formel"/>
	\end{xxalignat}
	</xsl:template>
	<!--=============================================================-->
	<!-- multiline equationarray -->
	<xsl:template match="formelarray[@typ='multiline']">\begin{multline*}<xsl:call-template name="num.array"/>
	<xsl:apply-templates select="descendant::formel"/>
	\end{multline*}
	</xsl:template>
	
	<!--=============================================================-->
	<!-- inline math -->
	<!--=============================================================-->
	<xsl:template match="formel-imtext"><xsl:if test=".!=''">$<xsl:value-of select="."/>$</xsl:if></xsl:template>
	<!--=============================================================-->
	<!-- inline math in verbose context -->
	<xsl:template match="formel-imtext[ancestor::buchstaeblich]">\(<xsl:value-of select="."/>\)</xsl:template>
	
	<!--=============================================================-->

	
</xsl:stylesheet>