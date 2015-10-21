<?xml version="1.0"?>
<!-- ********************************************************************
    $Id: header.xsl,v 1.5 2007/08/10 11:17:47 gebhard Exp $
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
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template name="latexheader">
<xsl:param name="contextnode"/>
<xsl:param name="pagelayout"/>
<xsl:variable name="docsettings" select="$pagelayout/../documentsettings"/>
<xsl:variable name="amssettings" select="$pagelayout/../amsmathsettings"/>

<xsl:variable name="font-size"><xsl:if test="$docsettings/font-size!=''"><xsl:value-of select="$docsettings/font-size"/>,</xsl:if></xsl:variable>
<xsl:variable name="twocolumn"><xsl:if test="$docsettings/twocolumn='true'">twocolumn,</xsl:if></xsl:variable>
<xsl:variable name="twoside"><xsl:if test="$docsettings/twoside!='true'">oneside,</xsl:if></xsl:variable>
<xsl:variable name="notitlepage"><xsl:if test="$docsettings/notitlepage='true'">notitlepage,</xsl:if></xsl:variable>
<xsl:variable name="draft"><xsl:if test="$docsettings/draft='true'">draft,</xsl:if></xsl:variable>
<xsl:variable name="leqno"><xsl:if test="$docsettings/leqno='true'">leqno,</xsl:if></xsl:variable>
<xsl:variable name="fleqn"><xsl:if test="$docsettings/fleqn='true'">fleqn</xsl:if></xsl:variable>
<xsl:variable name="openbib"><xsl:if test="$docsettings/openbib='true'">,openbib</xsl:if></xsl:variable>
<xsl:variable name="centertags"><xsl:if test="$amssettings/centertags='false'">tbtags,</xsl:if></xsl:variable>
<xsl:variable name="sumlimits"><xsl:if test="$amssettings/sumlimits='false'">nosumlimits,</xsl:if></xsl:variable>
<xsl:variable name="intlimits"><xsl:if test="$amssettings/intlimits='true'">intlimits,</xsl:if></xsl:variable>
<xsl:variable name="namelimits"><xsl:if test="$amssettings/namelimits='false'">nonamelimits</xsl:if></xsl:variable>

\documentclass[<xsl:value-of select="concat($font-size,$twocolumn,$twoside,$notitlepage,$draft,$leqno,$fleqn,$openbib)"/>]{book}
<xsl:if test="$docsettings/fontpacket!=''">\usepackage{<xsl:value-of select="$docsettings/fontpacket"/>}</xsl:if>
<xsl:if test="$docsettings/font-family!=''"><xsl:choose>
	<xsl:when test="$docsettings/font-family='sans-serif'">\renewcommand{\familydefault}{\sfdefault}</xsl:when>
	<xsl:when test="$docsettings/font-family='monospace'">\renewcommand{\familydefault}{\ttdefault}</xsl:when>
</xsl:choose></xsl:if>
%Font selection:
%\renewcommand{\familydefault}{\sfdefault}
%\DeclareFixedFont{\smallFont}{\encodingdefault}{\familydefault}{\seriesdefault}{\shapedefault}{12pt}
%\renewcommand{\tiny}{\smallFont}
%\renewcommand{\scriptsize}{\smallFont}
%\renewcommand{\footnotesize}{\smallFont}
%\renewcommand{\small}{\fontsize{14}{16}\selectfont}
%\renewcommand{\normalsize}{\fontsize{17}{18}\selectfont}
%\renewcommand{\large}{\fontsize{22}{24}\selectfont}
%\renewcommand{\Large}{\fontsize{30}{33}\selectfont}
%\renewcommand{\LARGE}{\fontsize{35}{37}\selectfont}

\usepackage[latin1]{inputenc}
\usepackage{ngerman}
<xsl:if test="$docsettings/language!=''">\selectlanguage{<xsl:value-of select="$docsettings/language"/>}</xsl:if>
\usepackage{a4}
\usepackage{t1enc} %Fuer Umlaute in Trennungsliste
%\usepackage[dvips]{hyperref} %Seite wird nach unten verschoben, Fehler im Index
\usepackage{fuxml} %colbreakbox, workarounds and hacks, first position with regard to other "color" packages is important
\usepackage{graphicx}

%AMS Packages
\usepackage[<xsl:value-of select="concat($centertags,$sumlimits,$intlimits,$namelimits)"/>]{amsmath}
\usepackage{amsfonts}
\usepackage{amssymb}


\usepackage{fancybox} % Merksatz, etc.
\usepackage{makeidx}
\usepackage{longtable}
\usepackage{alltt}
\usepackage{rotating}
%\usepackage{color} 		% Rasterflächen
\usepackage{psboxit}	% Rasterflächen
\usepackage{shadow} % shabox
\usepackage{textcomp}
\usepackage{marvosym}  % Euro, COLLIDES WITH \Rightarrow!!!!!
\mathchardef\Rightarrow="3229
\usepackage{wasysym}
\usepackage{stmaryrd} %binary operators
%\usepackage[T1]{fontenc}
\usepackage{verbatim} % liefert die comment Umgebung
\usepackage{fancyhdr} % Kolumnentitel
\usepackage{chappg} % for prefix in pagenumber
\usepackage{lscape} % provides landscape env.
\usepackage{multicol}
\usepackage{array}
%\usepackage{boites} % is loaded within package "fuxml"
\usepackage{mparhack} % fixes problems with marginpars occuring in inner marign
\usepackage{multirow} % provisdes table cells that span multiple rows
\usepackage{colortbl}
\usepackage[bottom]{footmisc} % footnotes at bottom of page
    \usepackage[dvips<xsl:if test="$pagelayout/sidebar/barpos ='outside'">,outerbars</xsl:if>]{changebar}%Revisionbars, collides with footnotemisc!
    <xsl:if test="$pagelayout/sidebar/barwidth !=''">\setlength{\changebarwidth}{<xsl:value-of select="$pagelayout/sidebar/barwidth"/>}</xsl:if>
    <xsl:if test="$pagelayout/sidebar/barsep !=''">\setlength{\changebarsep}{<xsl:value-of select="$pagelayout/sidebar/barsep"/>}</xsl:if>
    <xsl:if test="$pagelayout/sidebar/bargrey !=''">\setcounter{changebargrey}{<xsl:value-of select="$pagelayout/sidebar/bargrey"/>}</xsl:if>
    
    
%\usepackage[htt]{hyphenat} % hyphen typewriter text
\usepackage{hhline} % table lines
\usepackage{ifthen} 
\usepackage{wrapfig} %wraps text around graphics
\usepackage{psfrag}%Math in eps graphics

%The following command produces en error when it is executed after the calc package was loaded
\setlength{\tolerance}{2000}
\setlength{\emergencystretch}{20pt}

% Disable single lines at the start of a paragraph (Schusterjungen)
\clubpenalty = 10000
%
% Disable single lines at the end of a paragraph (Hurenkinder)
\widowpenalty = 10000 \displaywidowpenalty = 10000

\usepackage{calc} %For table column width

%% Select Font
<!--xsl:if test="$docsettings/font-family!=''">\renewcommand{\normalfont}{\encodingdefault<xsl:call-template name="get.font-family">
	<xsl:with-param name="style" select="$docsettings/font-family"/>
</xsl:call-template>\seriesdefault\shapedefault}</xsl:if-->

%%Page layout
<xsl:if test="$pagelayout/voffset!=''">\setlength{\voffset}{<xsl:apply-templates select="$pagelayout/voffset"/>}</xsl:if>
<xsl:if test="$pagelayout/hoffset!=''">\setlength{\hoffset}{<xsl:apply-templates select="$pagelayout/hoffset"/>}</xsl:if>
<xsl:if test="$pagelayout/paperheight!=''">\setlength{\paperheight}{<xsl:apply-templates select="$pagelayout/paperheight"/>}</xsl:if>
<xsl:if test="$pagelayout/paperwidth!=''">\setlength{\paperwidth}{<xsl:apply-templates select="$pagelayout/paperwidth"/>}</xsl:if>
<xsl:if test="$pagelayout/textheight!=''">\setlength{\textheight}{<xsl:apply-templates select="$pagelayout/textheight"/>}</xsl:if>
<xsl:if test="$pagelayout/textwidth!=''">\setlength{\textwidth}{<xsl:apply-templates select="$pagelayout/textwidth"/>}</xsl:if>
<xsl:if test="$pagelayout/textheight!=''">\setlength{\textheight}{<xsl:apply-templates select="$pagelayout/textheight"/>}</xsl:if>
<xsl:if test="$pagelayout/columnsep!=''">\setlength{\columnsep}{<xsl:apply-templates select="$pagelayout/columnsep"/>}</xsl:if>
<xsl:if test="$pagelayout/columnseprule!=''">\setlength{\columnseprule}{<xsl:apply-templates select="$pagelayout/columnseprule"/>}</xsl:if>
<xsl:if test="$pagelayout/columnwidth!=''">\setlength{\columnwidth}{<xsl:apply-templates select="$pagelayout/columnwidth"/>}</xsl:if>
<xsl:if test="$pagelayout/evensidemargin!=''">\setlength{\evensidemargin}{<xsl:apply-templates select="$pagelayout/evensidemargin"/>}</xsl:if>
<xsl:if test="$pagelayout/oddsidemargin!=''">\setlength{\oddsidemargin}{<xsl:apply-templates select="$pagelayout/oddsidemargin"/>}</xsl:if>
<xsl:if test="$pagelayout/footskip!=''">\setlength{\footskip}{<xsl:apply-templates select="$pagelayout/footskip"/>}</xsl:if>
<xsl:if test="$pagelayout/headheight!=''">\setlength{\headheight}{<xsl:apply-templates select="$pagelayout/headheight"/>}</xsl:if>
<xsl:if test="$pagelayout/headsep!=''">\setlength{\headsep}{<xsl:apply-templates select="$pagelayout/headsep"/>}</xsl:if>
<xsl:if test="$pagelayout/topmargin!=''">\setlength{\topmargin}{<xsl:apply-templates select="$pagelayout/topmargin"/>}</xsl:if>
<xsl:if test="$pagelayout/marginpush!=''">\setlength{\marginpush}{<xsl:apply-templates select="$pagelayout/marginpush"/>}</xsl:if>
<xsl:if test="$pagelayout/marginparsep!=''">\setlength{\marginparsep}{<xsl:apply-templates select="$pagelayout/marginparsep"/>}</xsl:if>
<xsl:if test="$pagelayout/marginparwidth!=''">\setlength{\marginparwidth}{<xsl:apply-templates select="$pagelayout/marginparwidth"/>}</xsl:if>

<xsl:if test="$pagelayout/baselinestretch!=''">\renewcommand{\baselinestretch}{<xsl:apply-templates select="$pagelayout/baselinestretch"/>}</xsl:if>
<xsl:if test="$pagelayout/parindent!=''">\setlength{\parindent}{<xsl:apply-templates select="$pagelayout/parindent"/>}</xsl:if>
<xsl:choose>
	<xsl:when test="$pagelayout/raggedbottom='true' or not($pagelayout/raggedbottom)">\raggedbottom</xsl:when>
	<xsl:otherwise>\flushbottom</xsl:otherwise>
</xsl:choose>


%%Page style
<xsl:apply-templates select="$pagelayout/pagestyle"><xsl:with-param name="contextnode" select="$contextnode" tunnel="yes"/></xsl:apply-templates>

\pagestyle{fancy}


%% Text for empty left pages
\makeatletter
\def\cleardoublepage{\clearpage\if@twoside \ifodd\c@page\else
	\hbox{}
	<xsl:apply-templates select="$pagelayout/emptypagestyle/node()"/>
	\newpage
	\if@twocolumn\hbox{}\newpage\fi\fi\fi}
\makeatother



%%Footnote Parameters
<xsl:if test="$pagelayout/footnotestyle/@footins!=''">\setlength{\skip\footins}{<xsl:apply-templates select="$pagelayout/footnotestyle/@footins"/>}</xsl:if>
<xsl:if test="$pagelayout/footnotestyle/@footnotesep!=''">\setlength{\footnotesep}{<xsl:apply-templates select="$pagelayout/footnotestyle/@footnotesep"/>}</xsl:if>


\makeatletter
\renewcommand{\@makefnmark}{\mbox{$^{\@thefnmark}$}}
\renewcommand{\@makefntext}[1]{<xsl:apply-templates select="$pagelayout/footnotestyle/footnoteline"><xsl:with-param name="contextnode" select="current()"/></xsl:apply-templates>}
\makeatother

\renewcommand{\footnoterule}{<xsl:apply-templates select="$pagelayout/footnotestyle/footnoterule"/>}
%%%%%%%%%%%%%%%%%%%



%%Index
	<xsl:if test="$config/config/index/indextitle">\renewcommand{\indexname}{<xsl:apply-templates select="$config/config/index[@context='courseunit' or not(@context)]/indextitle">
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates>}</xsl:if>

	\renewenvironment{theindex}{\newpage
	\pagestyle{fancy}\setlength{\parindent}{0pt}
	\renewcommand{\item}{\par\hangindent 40pt}
	%\markright{\indexname}{}
	\setlength{\columnsep}{<xsl:value-of select="$config/config/index[1]/@columnsep"/>}
	\begin{multicols}{<xsl:value-of select="$config/config/index[1]/@colnum"/>}[{{<xsl:call-template name="get.font-size"><xsl:with-param name="style" select="$config/config/index/indextitle/@font-size"/></xsl:call-template>
	\bfseries\indexname}}]\par\bigskip}
	{\end{multicols}}
\makeindex	

%Setzen schmaler Spalten in Tabellen: Der Latex Begleiter S.110
\newcommand{\PBS}[1]{\let\temp=\\#1\let\\=\temp}

%Switching off marginpars for breakbox environment
\let\savemarginpar=\marginpar
%\newenvironment{breakboxmp}{\def\marginpar[#1]#2{}\begin{breakbox}}{\end{breakbox}\renewcommand{\marginpar}{\savemarginpar}}

%Default definition for space occupied by wrapfigure
\newlength{\wrapfigwidth}

%Keine Silben mit weniger als x Buchstaben
<!--xsl:if test="$pagelayout/../hyphenation/lefthyphenmin!=''">\lefthyphenmin=<xsl:value-of select="$pagelayout/../hyphenation/lefthyphenmin"/></xsl:if>
<xsl:if test="$pagelayout/../hyphenation/lefthyphenmin!=''">\righthyphenmin=<xsl:value-of select="$pagelayout/../hyphenation/righthyphenmin"/></xsl:if-->

%mathindent
    <xsl:if test="$amssettings/mathindent!=''">\setlength{\mathindent}{<xsl:value-of select="$amssettings/mathindent"/>}</xsl:if>
    
%User commands, Hyphenation
\makeatletter
\@input{_config}
\makeatother

</xsl:template>


<xsl:template match="pagestyle[not(@id)]">
	<xsl:call-template name="pagestylesettings"/>
</xsl:template>

<xsl:template match="pagestyle[@id]">
	\fancypagestyle{<xsl:value-of select="@id"/>}{
	<xsl:call-template name="pagestylesettings"/>
	}
</xsl:template>
<xsl:template match="pagestyle[@id]" mode="thispagestyle">
<xsl:param name="contextnode" tunnel="yes"/>
    <xsl:call-template name="pagestylesettings">
        <xsl:with-param name="contextnode" select="$contextnode"/>
    </xsl:call-template>
</xsl:template>

<xsl:template name="pagestylesettings">
<xsl:param name="contextnode"/>
\lhead[<xsl:apply-templates select="lheadeven"/>]{<xsl:apply-templates select="lheadodd"/>}
\chead[<xsl:apply-templates select="cheadeven"/>]{<xsl:apply-templates select="cheadodd"/>}
\rhead[<xsl:apply-templates select="rheadeven"/>]{<xsl:apply-templates select="rheadodd"/>}
\lfoot[<xsl:apply-templates select="lfooteven"/>]{<xsl:apply-templates select="lfootodd"/>}
\cfoot[<xsl:apply-templates select="cfooteven"/>]{<xsl:apply-templates select="cfootodd"/>}
\rfoot[<xsl:apply-templates select="rfooteven"/>]{<xsl:apply-templates select="rfootodd"/>}
<xsl:if test="@headrulewidth!=''">\renewcommand{\headrulewidth}{<xsl:value-of select="@headrulewidth"/>}</xsl:if>
<xsl:if test="@footrulewidth!=''">\renewcommand{\footrulewidth}{<xsl:value-of select="@footrulewidth"/>}</xsl:if>
<xsl:if test="@headwidth!=''">\setlength{\headwidth}{<xsl:value-of select="@headwidth"/>}</xsl:if>   
</xsl:template>

</xsl:stylesheet>