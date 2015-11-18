<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <!-- ********************************************************************
        $Id: counter.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
<xsl:output method="text"  indent="no" />

<xsl:template name="counter">
<xsl:variable name="chapter">0<xsl:number count="abschnitt[@level=1 and titel/@num='an']" from="kurseinheiten"  level="any"/></xsl:variable>
<xsl:variable name="section">0<xsl:number count="abschnitt[@level=2 and titel/@num='an']" from="abschnitt[@level=1]"  level="any"/></xsl:variable>
<xsl:variable name="subsection">0<xsl:number count="abschnitt[@level=3 and titel/@num='an']" from="abschnitt[@level=2]"  level="any"/></xsl:variable>
<xsl:variable name="subsubsection">0<xsl:number count="abschnitt[@level=4 and titel/@num='an']" from="abschnitt[@level=3]"  level="any"/></xsl:variable>

\setcounter{chapter}{<xsl:value-of select="number($chapter)"/>}
\setcounter{section}{<xsl:value-of select="number($section)"/>}
\setcounter{subsection}{<xsl:value-of select="number($subsection)"/>}
\setcounter{subsubsection}{<xsl:value-of select="number($subsubsection)"/>}
</xsl:template>

<!--xsl:choose>
		<xsl:when test="preceding::kurseinheit/descendant::abschnitt[@level=2]">0<xsl:number count="abschnitt[@level=2]" from="abschnitt[@level=1]"  level="any"/></xsl:when>
		<xsl:otherwise>0</xsl:otherwise>
	</xsl:choose-->
</xsl:stylesheet>
