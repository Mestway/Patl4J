<?xml version="1.0" encoding="UTF-8"?>
<!-- ********************************************************************
	$Id: querverweis.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
	*******************************************************************************-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="querverweis"><xsl:variable name="referenz" select="key('id_key',@zielmarke)"/>\mbox{<xsl:choose>
		<xsl:when test="name($referenz)='bild'"><xsl:value-of select="$bild_bezeichner"/></xsl:when>
		<xsl:when test="name($referenz)='grafik'"><xsl:value-of select="$bild_bezeichner"/></xsl:when>
		<xsl:when test="name($referenz)='formel'"><xsl:value-of select="$qformel_bezeichner1"/></xsl:when>
		<xsl:when test="name($referenz)='tabelle' or name($referenz)='tabelle-alt'"><xsl:value-of select="$tab_bezeichner"/></xsl:when>
		<xsl:when test="name($referenz)='abschnitt'"><xsl:value-of select="$qabschnitt_bezeichner1"/></xsl:when>
	</xsl:choose><xsl:value-of select="$referenz/@number"/>}</xsl:template>
</xsl:stylesheet>