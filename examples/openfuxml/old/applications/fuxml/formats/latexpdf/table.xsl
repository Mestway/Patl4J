<?xml version='1.0'?>
<!-- ********************************************************************
	$Id: table.xsl,v 1.4 2007/01/11 13:37:18 gebhard Exp $
	********************************************************************
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
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fuxml="http://www.fernuni-hagen.de">
	<!--xsl:variable name="max.table.width">\textwidth</xsl:variable--> 
	<!-- Tabelle       -->
		
	<xsl:template match="tabelle|tabelle-alt">
		<xsl:param name="style" select="$styles/entry[@name=name(current())]"/>
		<xsl:param name="mode"/>
		<xsl:variable name="morethan1tgroup"><xsl:value-of select="tgroup/@cols"/></xsl:variable>
		<xsl:text xml:space="preserve">&#xD;&#xA;&#xD;&#xA;</xsl:text>
		<xsl:if test="$style/margin-top!='' and @gleiten!='ja'">\vspace{<xsl:value-of select="$style/margin-top"/>}</xsl:if>
		<xsl:text>% tgroup cols=</xsl:text><xsl:value-of select="$morethan1tgroup"/> <!-- Anzeige der Tabellenspaltenanzahl -->
		\pagebreak[2]
		\label{<xsl:value-of select="@id"/>}
		\def\thcol{<xsl:if test="$style/theadshading!=''">\rowcolor[gray]{<xsl:value-of select="$style/theadshading"/>}</xsl:if>}
		<xsl:choose>
			<xsl:when test="$morethan1tgroup = 1">
				\def\tbcol{<xsl:if test="$style/tbodyshading!=''">\cellcolor[gray]{<xsl:value-of select="$style/tbodyshading"/>}</xsl:if>}
			</xsl:when>
			<xsl:otherwise>
				\def\tbcol{<xsl:if test="$style/tbodyshading!=''">\rowcolor[gray]{<xsl:value-of select="$style/tbodyshading"/>}</xsl:if>}				
			</xsl:otherwise>
		</xsl:choose>
		\def\tbrulecol{<xsl:if test="$style/tbodyshading!=''">\arrayrulecolor[gray]{<xsl:value-of select="$style/tbodyshading"/>}</xsl:if>}
		\def\thrulecol{<xsl:if test="$style/theadshading!=''">\arrayrulecolor[gray]{<xsl:value-of select="$style/theadshading"/>}</xsl:if>}
		\def\tfrulecol{<xsl:if test="$style/tfootshading!=''">\arrayrulecolor[gray]{<xsl:value-of select="$style/tfootshading"/>}</xsl:if>}
		\def\tbarrayrulecol{<xsl:if test="$style/arrayruleshading!=''">\arrayrulecolor[gray]{<xsl:value-of select="$style/arrayruleshading"/>}</xsl:if>}
		\def\tfcol{<xsl:if test="$style/tfootshading!=''">\rowcolor[gray]{<xsl:value-of select="$style/tfootshading"/>}</xsl:if>}
		<xsl:call-template name="calc.table.parameters"><xsl:with-param name="style" select="$style"/><xsl:with-param name="colspecset" select="tgroup/colspec"/></xsl:call-template> 
		\setlength{\LTcapwidth}{\tablewidth} <!-- setting caption width -->
		<xsl:if test="descendant::margref and not($mode='inbox')"><xsl:apply-templates select="descendant::margref" mode="tablemargref"/></xsl:if>{<xsl:if test="$style/baselinestretch!=''">\renewcommand{\baselinestretch}{<xsl:apply-templates select="$style/baselinestretch"/>}</xsl:if>\normalsize
		<xsl:if test="$style/tabcolsep!=''">\setlength{\tabcolsep}{<xsl:apply-templates select="$style/tabcolsep"/>}</xsl:if>
		<xsl:if test="$style/arrayrulewidth!=''">\setlength{\arrayrulewidth}{<xsl:apply-templates select="$style/arrayrulewidth"/>}\setlength{\extrarowheight}{<xsl:apply-templates select="$style/arrayrulewidth"/>}</xsl:if>
		<xsl:if test="$style/arrayruleshading!=''">\arrayrulecolor[gray]{<xsl:value-of select="$style/arrayruleshading"/>}</xsl:if>
		<xsl:if test="$style/doublerulesep!=''">\setlength{\doublerulesep}{<xsl:apply-templates select="$style/doublerulesep"/>}</xsl:if>
		<xsl:if test="$style/doublerulesepshading!=''">\doublerulesepcolor[gray]{<xsl:value-of select="$style/doublerulesepshading"/>}</xsl:if>
		<xsl:if test="$style/arraystretch!=''">\renewcommand{\arraystretch}{<xsl:apply-templates select="$style/arraystretch"/>}</xsl:if>
		<xsl:choose>
			<xsl:when test="@mehrseitig='nein'">
				<xsl:if test="@gleiten='ja'">\begin{table}[htb]</xsl:if>
				<xsl:call-template name="get.table.align.begin"><xsl:with-param name="style" select="$style"/></xsl:call-template>
				<xsl:call-template name="get.font-family"><xsl:with-param name="style" select="$style/font-family"/></xsl:call-template>
				<xsl:call-template name="get.font-style"><xsl:with-param name="style" select="$style/font-style"/></xsl:call-template>
				<xsl:call-template name="get.font-variant"><xsl:with-param name="style" select="$style/font-variant"/></xsl:call-template>
				<xsl:call-template name="get.font-weight"><xsl:with-param name="style" select="$style/font-weight"/></xsl:call-template>
				<xsl:call-template name="get.font-size"><xsl:with-param name="style" select="$style/font-size"/></xsl:call-template>
				<xsl:call-template name="setcaptionoutside"><xsl:with-param name="style" select="$style"/></xsl:call-template>
				\begin{tabular}{<xsl:apply-templates select="tgroup/colspec"  mode="tabellenkopf"/>}
				<!--xsl:call-template name="setcaption"><xsl:with-param name="style" select="$style"/></xsl:call-template-->
				<xsl:apply-templates mode="short"><xsl:with-param name="style" select="$style"/></xsl:apply-templates>
				\end{tabular}
				<xsl:call-template name="get.table.align.end"><xsl:with-param name="style" select="$style"/></xsl:call-template>
				<xsl:if test="@gleiten='ja'">\end{table}</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="get.font-family"><xsl:with-param name="style" select="$style/font-family"/></xsl:call-template>
				<xsl:call-template name="get.font-style"><xsl:with-param name="style" select="$style/font-style"/></xsl:call-template>
				<xsl:call-template name="get.font-variant"><xsl:with-param name="style" select="$style/font-variant"/></xsl:call-template>
				<xsl:call-template name="get.font-weight"><xsl:with-param name="style" select="$style/font-weight"/></xsl:call-template>
				<xsl:call-template name="get.font-size"><xsl:with-param name="style" select="$style/font-size"/></xsl:call-template>
				\begin{longtable}<xsl:call-template name="get.longtable.align"><xsl:with-param name="style" select="$style"/></xsl:call-template>
					{<xsl:apply-templates select="tgroup/colspec"  mode="tabellenkopf"/>}
				<xsl:call-template name="setcaptionlongtable"><xsl:with-param name="style" select="$style"/></xsl:call-template>
				<xsl:apply-templates select="*[name()!='titel']"><xsl:with-param name="style" select="$style"/></xsl:apply-templates>
				\end{longtable}
			</xsl:otherwise>
		</xsl:choose>}<xsl:apply-templates select="$style/tocline"><xsl:with-param name="contextnode" select="current()"/></xsl:apply-templates><xsl:apply-templates select="descendant::fnref" mode="afterbox"/>
		<xsl:if test="$style/margin-bottom!='' and @gleiten!='ja'">\par\vspace{<xsl:value-of select="$style/margin-bottom"/>}</xsl:if>
	</xsl:template>

	
	
	<xsl:template name="setcaptionlongtable"><xsl:param name="style"/>
		<xsl:if test="titel">\caption*{
		<xsl:apply-templates select="$style/label">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
		</xsl:apply-templates>}\\<xsl:if test="$style/label/@spacing!=''">[<xsl:value-of select="$style/label/@spacing"/>]</xsl:if></xsl:if>
	</xsl:template>

	
	<xsl:template name="setcaptionoutside"><xsl:param name="style"/>
		<xsl:variable name="max.table.width"><xsl:choose>
			<xsl:when test="$style/maxtablewidth!=''"><xsl:value-of select="$style/maxtablewidth"/></xsl:when><xsl:otherwise>\textwidth</xsl:otherwise>
		</xsl:choose></xsl:variable>
		<xsl:if test="titel">\parbox[t]{\tablewidth<!--xsl:value-of select="$max.table.width"/-->-\tabcolsep-\arrayrulewidth}{
		<xsl:apply-templates select="$style/label">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
		</xsl:apply-templates>}\\*<xsl:if test="$style/label/@spacing!=''">[<xsl:value-of select="$style/label/@spacing"/>]</xsl:if></xsl:if>
	</xsl:template>
	
	<!-- deprecated -->	
	<xsl:template name="setcaptioninside"><xsl:param name="style"/>
		<xsl:variable name="max.table.width"><xsl:choose>
			<xsl:when test="$style/maxtablewidth!=''"><xsl:value-of select="$style/maxtablewidth"/></xsl:when><xsl:otherwise>\textwidth</xsl:otherwise>
		</xsl:choose></xsl:variable>
		<xsl:if test="titel">\multicolumn{<xsl:value-of select="descendant::tgroup/@cols"/>}
		{l}{\hspace*{-\tabcolsep}\parbox[t]{\tablewidth<!--xsl:value-of select="$max.table.width"/-->-\tabcolsep-\arrayrulewidth}{
		<xsl:apply-templates select="$style/label">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
		</xsl:apply-templates>}}\\<xsl:if test="$style/label/@spacing!=''">[<xsl:value-of select="$style/label/@spacing"/>]</xsl:if></xsl:if>
	</xsl:template>
	<!-- end depricated -->

	<xsl:template name="get.table.align.begin"><xsl:param name="style"/><xsl:choose>
		<xsl:when test="$style/align='center'">\begin{center}</xsl:when>
		<xsl:when test="$style/align='left'">\begin{flushleft}</xsl:when>
		<xsl:when test="$style/align='right'">\begin{flushright}</xsl:when>
		<xsl:otherwise></xsl:otherwise>	</xsl:choose>
	</xsl:template>
	<xsl:template name="get.table.align.end"><xsl:param name="style"/><xsl:choose>
		<xsl:when test="$style/align='center'">\end{center}</xsl:when>
		<xsl:when test="$style/align='left'">\end{flushleft}</xsl:when>
		<xsl:when test="$style/align='right'">\end{flushright}</xsl:when>
		<xsl:otherwise></xsl:otherwise>	</xsl:choose>
	</xsl:template>
	<xsl:template name="get.longtable.align"><xsl:param name="style"/><xsl:choose>
		<xsl:when test="$style/align='center'">[c]</xsl:when>
		<xsl:when test="$style/align='left'">[l]</xsl:when>
		<xsl:when test="$style/align='right'">[r]</xsl:when>
		<xsl:otherwise>[l]</xsl:otherwise></xsl:choose>
	</xsl:template>
	
	<xsl:template match="titel" mode="short"/>
	
	<xsl:template match="tgroup" mode="short">
		<xsl:call-template name="draw.top.frameborder"/>
		<xsl:apply-templates select="thead" mode="short"/>
		<xsl:apply-templates/>
		<xsl:apply-templates select="tfoot" mode="tabellenfuss"/>
		<!--xsl:if test="not(tfoot)"><xsl:call-template name="draw.bottom.frameborder"/></xsl:if-->
	</xsl:template>
	
	<xsl:template match="tgroup"><xsl:param name="style"/>
		<xsl:call-template name="draw.top.frameborder"/><xsl:apply-templates select="thead" mode="setfirsthead"/> \endfirsthead
		<xsl:if test="$style/contprevtext!=''">\hline \multicolumn{<xsl:value-of select="@cols"/>}{|l|}{<xsl:apply-templates select="$style/contprevtext"/>}\\\hline</xsl:if>
		<xsl:apply-templates select="thead" mode="setfirsthead"/>\endhead
		<xsl:if test="$style/contnexttext!=''">\hline \multicolumn{<xsl:value-of select="@cols"/>}{|r|}{<xsl:apply-templates select="$style/contnexttext"/>}\\\hline</xsl:if>
		\endfoot
		 \endlastfoot
		<xsl:apply-templates/>
		<xsl:apply-templates select="tfoot" mode="tabellenfuss"/>
		<!--xsl:call-template name="draw.bottom.frameborder"/-->
	</xsl:template>

	
	<xsl:template match="colspec" mode="tabellenkopf">
		<xsl:variable name="cols" select="../@cols"/>
		<xsl:if test="position()=1"><xsl:call-template name="draw.left.frameborder"/></xsl:if>>{\PBS<xsl:call-template name="get.hor.align"/><!--\hspace{0pt}-->}p{\colwidth<xsl:value-of select="fuxml:transnum(@colnum)"/>-2\tabcolsep<xsl:if test="ancestor-or-self::*/@colsep!='0' or not(ancestor-or-self::*/@colsep)">-\arrayrulewidth</xsl:if>}<xsl:if test="(not(ancestor-or-self::*/@colsep) or ancestor-or-self::*/@colsep!='0') and position()!=last()">|</xsl:if><xsl:if test="position()=last() and (@colsep!='0' or not(@colsep))"><xsl:call-template name="draw.right.frameborder"/></xsl:if>
	</xsl:template>
		
		
	<!--xsl:template match="colspec" mode="tabellenkopf">
		<xsl:variable name="cols" select="../@cols"/>
		>{\PBS<xsl:call-template name="get.hor.align"/>\hspace{0pt}}p{<xsl:call-template name="calc.col.width"/>-2\tabcolsep-\arrayrulewidth}|</xsl:template>
	
	<xsl:template match="colspec[position()=last()]" mode="tabellenkopf">
		<xsl:variable name="cols" select="../@cols"/>
		>{\PBS<xsl:call-template name="get.hor.align"/>\hspace{0pt}}p{<xsl:call-template name="calc.col.width"/>-2\tabcolsep-\arrayrulewidth}<xsl:call-template name="draw.right.frameborder"/></xsl:template>

	<xsl:template match="colspec[position()=1][position()=last()]" mode="tabellenkopf">
		<xsl:variable name="cols" select="../@cols"/>
		>{\PBS<xsl:call-template name="get.hor.align"/>\hspace{0pt}}p{<xsl:call-template name="calc.col.width"/>-2\tabcolsep-\arrayrulewidth}<xsl:call-template name="draw.right.frameborder"/></xsl:template-->
	

		
	<xsl:template match="thead" mode="short">
		<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="thead"/>
	
	<xsl:template match="thead" mode="setfirsthead">
		<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="tfoot" mode="tabellenfuss">
		<xsl:apply-templates/>
		<xsl:call-template name="draw.bottom.frameborder"/>
	</xsl:template>
	
	<xsl:template match="tfoot"/>
	
	<xsl:template match="tbody">
		<xsl:apply-templates/>
	</xsl:template>
	
	<!--xsl:template match="row[parent::thead][position()!=last()]" mode="old">\thcol <xsl:apply-templates/>\tabularnewline\hhline{<xsl:apply-templates mode="hhline"/>}</xsl:template-->
	<xsl:template match="row[parent::thead]">\thcol <xsl:apply-templates/>\tabularnewline<!--xsl:if test="position() != last()"-->\hhline{<xsl:apply-templates mode="hhline"/>}<!--/xsl:if--></xsl:template>
	
	<xsl:template match="row[parent::tfoot]">\tfcol <xsl:apply-templates/>\tabularnewline<xsl:if test="position() != last()">\hhline{<xsl:apply-templates mode="hhline"/>}</xsl:if></xsl:template>

	<xsl:template match="row[parent::tbody]">\tbcol <xsl:apply-templates/>\tabularnewline<!--xsl:if test="position() != last()"-->\hhline{<xsl:apply-templates mode="hhline"/>}<!--/xsl:if-->	
	</xsl:template>
	
	<!--xsl:template match="row[parent::thead][position()=last()]">\thcol <xsl:apply-templates/>\tabularnewline\hhline{<xsl:apply-templates mode="doubleline"/>}</xsl:template-->
	<!--xsl:template match="row[parent::tbody][position()=last()][not(ancestor::tgroup/tfoot)]">\tbcol <xsl:apply-templates/>\tabularnewline\hhline{<xsl:apply-templates mode="hhline"/>}</xsl:template>
	<xsl:template match="row[parent::tbody][position()=last()][ancestor::tgroup/tfoot]">\tbcol <xsl:apply-templates/>\tabularnewline\hhline{<xsl:apply-templates mode="doubleline"/>}</xsl:template-->


	
	
	<xsl:template match="entry" mode="hhline">
		<xsl:variable name="colspecset" select="ancestor::tgroup/colspec"/>
		<xsl:variable name="num"><xsl:value-of select="$colspecset[@colname=current()/@colname]/@colnum"/></xsl:variable>
		<xsl:variable name="num1"><xsl:value-of select="$colspecset[@colname=current()/@nameend]/@colnum"/></xsl:variable>
		<xsl:variable name="num2"><xsl:value-of select="$colspecset[@colname=current()/@namest]/@colnum"/></xsl:variable>
		<xsl:variable name="dif"><xsl:value-of select="number($num1) - number($num2) + 1"/></xsl:variable>
		<xsl:variable name="sequence" select="(1 to $dif)"/>
		<xsl:choose>
			<xsl:when test="@morerows!=''"></xsl:when>
			<xsl:when test="@namest!=''"><xsl:choose><!-- Multicell -->
				<xsl:when test="ancestor-or-self::*/@rowsep!='0' or not(ancestor-or-self::*/@rowsep)"><xsl:for-each select="$sequence">-</xsl:for-each></xsl:when>
				<xsl:otherwise><xsl:call-template name="draw.left.gap"/><xsl:call-template name="get.rule.color"/><xsl:for-each select="$sequence">-</xsl:for-each>&gt;{\tbarrayrulecol}<xsl:call-template name="draw.right.gap"/></xsl:otherwise>
			</xsl:choose></xsl:when><!--xsl:value-of select="for$i in(1to$dif),$j in('-')return($j)"/-->
			<xsl:otherwise><xsl:choose><!-- normal cell -->
				<xsl:when test="ancestor-or-self::*/@rowsep!='0' or not(ancestor-or-self::*/@rowsep)"><xsl:call-template name="draw.left.gap"/>-</xsl:when>
				<xsl:otherwise><xsl:call-template name="draw.left.gap"/><xsl:call-template name="get.rule.color"/>-&gt;{\tbarrayrulecol}<xsl:call-template name="draw.right.gap"/></xsl:otherwise>
			</xsl:choose></xsl:otherwise></xsl:choose></xsl:template>
	
	<xsl:template match="entry" mode="doubleline">
		<xsl:variable name="colspecset" select="ancestor::tgroup/colspec"/>
		<xsl:variable name="num"><xsl:value-of select="$colspecset[@colname=current()/@colname]/@colnum"/></xsl:variable>
		<xsl:variable name="num1"><xsl:value-of select="$colspecset[@colname=current()/@nameend]/@colnum"/></xsl:variable>
		<xsl:variable name="num2"><xsl:value-of select="$colspecset[@colname=current()/@namest]/@colnum"/></xsl:variable>
		<xsl:variable name="dif"><xsl:value-of select="$num1 - $num2 + 1"/></xsl:variable>
		<xsl:variable name="sequence" select="(1 to $dif)"/>
		<xsl:choose>
			<xsl:when test="@morerows!=''"></xsl:when>
			<xsl:when test="@namest!=''"><xsl:choose><!-- Multicell -->
				<xsl:when test="ancestor-or-self::*/@rowsep='0'"><xsl:for-each select="$sequence">~</xsl:for-each></xsl:when>
				<xsl:otherwise><xsl:for-each select="$sequence">=</xsl:for-each></xsl:otherwise>
			</xsl:choose></xsl:when><!--xsl:value-of select="for$i in(1to$dif),$j in('-')return($j)"/-->
			<xsl:otherwise><xsl:choose><!-- normal cell -->
				<xsl:when test="@rowsep='0'">~</xsl:when>
				<xsl:otherwise>=</xsl:otherwise>
			</xsl:choose></xsl:otherwise></xsl:choose></xsl:template>

	
	<xsl:template name="get.rule.color">&gt;{<xsl:choose>
		<xsl:when test="ancestor::row[parent::thead]">\thrulecol</xsl:when>
		<xsl:when test="ancestor::row[parent::tbody]">\tbrulecol</xsl:when>
		<xsl:when test="ancestor::row[parent::tfoot]">\tfrulecol</xsl:when>
	</xsl:choose>}</xsl:template>

	<!-- cline wird von \cellcolor überdeckt, deshalb hhline --> 
	<!--xsl:template match="entry" mode="cline">
		<xsl:variable name="colspecset" select="ancestor::tgroup/colspec"/>
		<xsl:variable name="num"><xsl:value-of select="$colspecset[@colname=current()/@colname]/@colnum"/></xsl:variable>
		<xsl:variable name="num1"><xsl:value-of select="$colspecset[@colname=current()/@nameend]/@colnum"/></xsl:variable>
		<xsl:variable name="num2"><xsl:value-of select="$colspecset[@colname=current()/@namest]/@colnum"/></xsl:variable>
		<xsl:if test="@rowsep!='0' or not(@rowsep)"><xsl:choose>
			<xsl:when test="@morerows!=''"></xsl:when>
			<xsl:when test="@namest!=''">\cline{<xsl:value-of select="$num2"/>-<xsl:value-of select="$num1"/>}</xsl:when>
			<xsl:otherwise>\cline{<xsl:value-of select="$num"/>-<xsl:value-of select="$num"/>}</xsl:otherwise>
		</xsl:choose></xsl:if>
		
	</xsl:template-->

	
	<xsl:template match="entry[position()=1]"><xsl:call-template name="process.entry"/></xsl:template>
	
	<xsl:template match="entry[position()!=1]">&amp;<xsl:call-template name="process.entry"/></xsl:template>
	
	<xsl:template name="process.entry"><!--xsl:call-template name="draw.cell.borders"/-->
		
		<xsl:if test="not(@namest)"><!-- multicolums moegen das nicht --><xsl:if test="absatz|absatz-ohne|absatz-mini|absatz-klein">\hspace{0cm}</xsl:if></xsl:if><xsl:choose>
			<xsl:when test="@namest">	<xsl:variable name="colspecset" select="ancestor::tgroup/colspec"/>
				<xsl:variable name="num1"><xsl:value-of select="$colspecset[@colname=current()/@nameend]/@colnum"/></xsl:variable>
				<xsl:variable name="num2"><xsl:value-of select="$colspecset[@colname=current()/@namest]/@colnum"/></xsl:variable>
				<xsl:variable name="num"><xsl:value-of select="$num1 - $num2 + 1"/></xsl:variable>\multicolumn{<xsl:value-of select="$num"/>}{<xsl:call-template name="draw.left.multicol.line"/>>{\PBS<xsl:call-template name="get.hor.align"/>\hspace{0pt}}p{<xsl:call-template name="calc.multi.width"/>-2\tabcolsep<xsl:if test="ancestor::tgroup/colspec[@colname=current()/@nameend]/@colsep!='0' or not(ancestor::tgroup/colspec[@colname=current()/@nameend]/@colsep)">-\arrayrulewidth</xsl:if>}<xsl:call-template name="draw.right.multicol.line"/>}{<xsl:call-template name="get.hor.align"/><xsl:value-of select="fuxml:getcellcolor(current())"/>{}<xsl:if test="ancestor::tfoot"><xsl:call-template name="set.tfoot.style"/></xsl:if><xsl:apply-templates select="node()[name()!='indexfix']"/>}<xsl:apply-templates select="indexfix"/>
			</xsl:when>
			<xsl:otherwise><xsl:if test="@rotate">\begin{rotate}{<xsl:value-of select="@rotate"/>}</xsl:if><xsl:call-template name="get.hor.align"/>{<xsl:if test="ancestor::tfoot"><xsl:call-template name="set.tfoot.style"/></xsl:if><xsl:apply-templates/>}<xsl:if test="@rotate">\end{rotate}</xsl:if></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="set.tfoot.style">
		<xsl:variable name="style" select="$styles/entry[@name=name(current()/ancestor::tabelle|current()/ancestor::tabelle-alt)]"/>
		<xsl:call-template name="get.font-family"><xsl:with-param name="style" select="$style/tf-font-family"/></xsl:call-template>
		<xsl:call-template name="get.font-style"><xsl:with-param name="style" select="$style/tf-font-style"/></xsl:call-template>
		<xsl:call-template name="get.font-variant"><xsl:with-param name="style" select="$style/tf-font-variant"/></xsl:call-template>
		<xsl:call-template name="get.font-weight"><xsl:with-param name="style" select="$style/tf-font-weight"/></xsl:call-template>
		<xsl:call-template name="get.font-size"><xsl:with-param name="style" select="$style/tf-font-size"/></xsl:call-template>
	</xsl:template>
	
	<xsl:function name="fuxml:getcellcolor">
		<xsl:param name="contextnode"/>
		<xsl:variable name="style" select="$styles/entry[@name=name($contextnode/(ancestor::tabelle|ancestor::tabelle-alt))]"/>
		<xsl:choose>
			<xsl:when test="$contextnode/ancestor::thead"><xsl:if test="$style/theadshading!=''">\cellcolor[gray]{<xsl:value-of select="$style/theadshading"/>}</xsl:if></xsl:when>
			<xsl:when test="$contextnode/ancestor::tfoot"><xsl:if test="$style/tfootshading!=''">\cellcolor[gray]{<xsl:value-of select="$style/tfootshading"/>}</xsl:if></xsl:when>
			<xsl:otherwise><xsl:if test="$style/tbodyshading!=''">\cellcolor[gray]{<xsl:value-of select="$style/tbodyshading"/>}</xsl:if></xsl:otherwise>
		</xsl:choose>
	</xsl:function>
	
	<xsl:function name="fuxml:transnum">
		<xsl:param name="number"/>
		<xsl:value-of select="translate(string($number),'1234567890','abcdefghij')"/>
	</xsl:function>

	
	<xsl:template name="get.hor.align"><xsl:choose>
		<xsl:when test="@align='right'">\raggedleft</xsl:when>
		<xsl:when test="@align='left'">\raggedright</xsl:when>
		<xsl:when test="@align='center'">\centering</xsl:when>
		<xsl:when test="@align='justify'"></xsl:when>
		<xsl:otherwise>\raggedright</xsl:otherwise></xsl:choose>{}</xsl:template>
	
	

	<!-- Berechnet Tabellenbreite, als Parameter werden alle Colspec-Elemente übergeben -->

	<xsl:template name="calc.table.parameters"><xsl:param name="style"/><xsl:param name="colspecset"/><xsl:variable name="max.table.width"><xsl:choose>
			<xsl:when test="$style/maxtablewidth!=''"><xsl:value-of select="$style/maxtablewidth"/></xsl:when><xsl:otherwise><xsl:value-of select="$style/ancestor::config/pagelayout/textwidth"/></xsl:otherwise>
		</xsl:choose></xsl:variable>
		<xsl:variable name="colspecsetfilter">
		<xsl:for-each select="$colspecset[contains(self::*/@colwidth,'*') or not(@colwidth)]">
			<xsl:element name="colspec">
				<xsl:attribute name="colwidth">
					<xsl:choose>
						<xsl:when test="substring-before(@colwidth,'*')!=''">
							<xsl:value-of select="number(substring-before(@colwidth,'*'))"/>
						</xsl:when>
						<xsl:otherwise>1</xsl:otherwise>
					</xsl:choose>
				</xsl:attribute>
			</xsl:element>
		</xsl:for-each>
	</xsl:variable><xsl:for-each select="$colspecset">\def\colwidth<xsl:value-of select="fuxml:transnum(@colnum)"/>{<xsl:choose>
					<xsl:when test="starts-with(@colwidth,'*')  or not(@colwidth)">
					<!-- Fall: alle Tabellenspalten gleich breit, colwidth enthält nur '*'-->
						<xsl:value-of select="$max.table.width"/>/<xsl:value-of select="count($colspecset)"/>
					</xsl:when>
					<xsl:when test="contains(self::*/@colwidth,'*')"><xsl:value-of select="$max.table.width"/>*\real{<xsl:choose>
							<xsl:when test="substring-before(@colwidth,'*')!=''">
								<xsl:value-of select="number(substring-before(@colwidth,'*'))"/>
							</xsl:when>
							<xsl:otherwise>1</xsl:otherwise>
						</xsl:choose>}/\real{<xsl:value-of select="sum($colspecsetfilter//@colwidth)"/>}</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="@colwidth"/>
					</xsl:otherwise></xsl:choose>}</xsl:for-each>
				\def\tablewidth{<xsl:for-each select="$colspecset">\colwidth<xsl:value-of select="fuxml:transnum(@colnum)"/><xsl:if test="position()!=last()">+</xsl:if></xsl:for-each>}
	</xsl:template>
	
		
	
	


	<xsl:template name="calc.multi.width">
	<!-- Berechnet die Spaltenbreite für Multispalten -->
		<xsl:variable name="colst" select="ancestor::tgroup/colspec[@colname=current()/@namest]"/>
		<xsl:variable name="colend" select="ancestor::tgroup/colspec[@colname=current()/@nameend]"/>
		<xsl:for-each select="ancestor::tgroup/colspec[number(@colnum) ge number($colst/@colnum) 
							and number(@colnum) le number($colend/@colnum)]">\colwidth<xsl:value-of select="fuxml:transnum(@colnum)"/><xsl:if test="@colname!=$colend/@colname">+</xsl:if></xsl:for-each>		
	</xsl:template>

		
	

	
	<xsl:template name="draw.left.frameborder"><xsl:if test="
			ancestor::tabelle/@frame='all' or
			ancestor::tabelle-alt/@frame='all' or
			ancestor::tabelle/@frame='sides' or
			ancestor::tabelle-alt/@frame='sides' or
			ancestor::tabelle[not(@frame)] or
			ancestor::tabelle-alt[not(@frame)]
			">|</xsl:if>
	</xsl:template>
	
	<xsl:template name="draw.right.frameborder"><xsl:if test="
			ancestor::tabelle/@frame='all' or
			ancestor::tabelle-alt/@frame='all' or
			ancestor::tabelle/@frame='sides' or
			ancestor::tabelle-alt/@frame='sides' or
			ancestor::tabelle[not(@frame)] or
			ancestor::tabelle-alt[not(@frame)]
			">|</xsl:if>
	</xsl:template>

	<xsl:template name="draw.top.frameborder"><xsl:if test="
			ancestor::tabelle/@frame='all' or
			ancestor::tabelle-alt/@frame='all' or
			ancestor::tabelle/@frame='topbot' or
			ancestor::tabelle-alt/@frame='topbot'or
			ancestor::tabelle/@frame='top' or
			ancestor::tabelle-alt/@frame='top' or
			ancestor::tabelle[not(@frame)] or
			ancestor::tabelle-alt[not(@frame)]
			">\hline </xsl:if>
	</xsl:template>
	
	<xsl:template name="draw.bottom.frameborder"><xsl:if test="
			ancestor::tabelle/@frame='all' or
			ancestor::tabelle-alt/@frame='all' or
			ancestor::tabelle/@frame='topbot' or
			ancestor::tabelle-alt/@frame='topbot'or
			ancestor::tabelle/@frame='bottom' or
			ancestor::tabelle-alt/@frame='bottom' or
			ancestor::tabelle[not(@frame)] or
			ancestor::tabelle-alt[not(@frame)]
			">\hline </xsl:if>
	</xsl:template>

	<xsl:template name="draw.right.multicol.line"><xsl:if test="ancestor::tgroup/colspec[@colname=current()/@nameend]/@colsep!='0' or not(ancestor::tgroup/colspec[@colname=current()/@nameend]/@colsep)"><xsl:call-template name="draw.right.frameborder"/></xsl:if></xsl:template>
	
	<!-- If multicol is first cell left border has to be set -->
	<xsl:template name="draw.left.multicol.line"><xsl:if test="position()=1"><xsl:call-template name="draw.left.frameborder"/></xsl:if></xsl:template>

	<xsl:template name="draw.left.gap"><xsl:choose>
		<xsl:when test="position()=1">
			<xsl:call-template name="draw.left.frameborder"/>
		</xsl:when>
		<xsl:otherwise>
			<!--xsl:if test="ancestor::tgroup/colspec[@colname=current()/preceding-sibling::entry/@colname]/@colsep!='0' or not(ancestor::tgroup/colspec[@colname=preceding-sibling::entry/@nameend]/@colsep)">|</xsl:if-->
			<!--xsl:if test="ancestor-or-self::*/@colsep!='0' or not(ancestor-or-self::*/@colsep)">|</xsl:if-->
		</xsl:otherwise>
	</xsl:choose></xsl:template>
	
	
	<xsl:template name="draw.right.gap"><xsl:choose>
		<xsl:when test="position()=last()"><!-- letzte Zelle? ja-> frame auswerten -->
			<xsl:choose>
				<xsl:when test="@nameend"><!-- Multitzelle -->
					<xsl:if test="ancestor::tgroup/colspec[@colname=current()/@nameend]/@colsep!='0' or not(ancestor::tgroup/colspec[@colname=current()/@nameend]/@colsep)"><xsl:call-template name="draw.right.frameborder"/></xsl:if>
				</xsl:when>
				<xsl:otherwise><!-- keine Multitzelle -->
					<xsl:if test="ancestor::tgroup/colspec[@colname=current()/@colname]/@colsep!='0' or not(ancestor::tgroup/colspec[@colname=current()/@colname]/@colsep)"><xsl:call-template name="draw.right.frameborder"/></xsl:if>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		<xsl:otherwise><xsl:if test="ancestor-or-self::*/@colsep!='0' or not(ancestor-or-self::*/@colsep)">|</xsl:if></xsl:otherwise></xsl:choose></xsl:template>

	
	
	
	
	
	
	
	
	
	<xsl:template name="draw.right.gap.old"><xsl:choose>
		<xsl:when test="position()=last()"><!-- letzte Zelle? ja-> frame auswerten -->
			<xsl:choose>
				<xsl:when test="@nameend"><!-- Multitzelle -->
					<xsl:if test="ancestor::tgroup/colspec[@colname=current()/@nameend]/@colsep!='0' or not(ancestor::tgroup/colspec[@colname=current()/@nameend]/@colsep)"><xsl:call-template name="draw.right.frameborder"/></xsl:if>
				</xsl:when>
				<xsl:otherwise><!-- keine Multitzelle -->
					<xsl:if test="ancestor::tgroup/colspec[@colname=current()/@colname]/@colsep!='0' or not(ancestor::tgroup/colspec[@colname=current()/@colname]/@colsep)"><xsl:call-template name="draw.right.frameborder"/></xsl:if>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		<xsl:otherwise>
			<xsl:choose>
				<xsl:when test="@nameend">
					<xsl:if test="ancestor::tgroup/colspec[@colname=current()/@nameend]/@colsep!='0' or not(ancestor::tgroup/colspec[@colname=current()/@nameend]/@colsep)">|</xsl:if>
				</xsl:when>
				<xsl:otherwise>
					<xsl:if test="ancestor::tgroup/colspec[@colname=current()/@colname]/@colsep!='0' or not(ancestor::tgroup/colspec[@colname=current()/@colname]/@colsep)">|</xsl:if>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:otherwise>
	</xsl:choose></xsl:template>


</xsl:stylesheet>
