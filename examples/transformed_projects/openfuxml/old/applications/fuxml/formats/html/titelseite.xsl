<?xml version="1.0"?>
<!-- edited with XML Spy v3.5 NT (http://www.xmlspy.com) by Gerd Steinkamp (FernUniversität Hagen) -->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<!-- ********************************************************************
     $Id: titelseite.xsl,v 1.3 2007/01/11 13:38:26 gebhard Exp $
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
     
     Description:
     This file contains the design for only one element: titelseite
     
     Use in Pass 4:
	
	*******************************************************************
	Beschreibung:
	Diese Datei enthält die Designvorschriften für die Titelseite
     ********************************************************************
-->

<xsl:template match="titelseite|processing-instruction('titlepage')">
	<xsl:param name="Pfad"/>
	<div class="titelseite">
		<table width="100%">
			<xsl:apply-templates select="descendant::feulogo"  mode="Titelseite">
				<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
			</xsl:apply-templates>
			<xsl:apply-templates select="descendant::varlogo1" mode="Titelseite">
				<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
			</xsl:apply-templates>
			<xsl:apply-templates select="descendant::varlogo2" mode="Titelseite">
				<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
			</xsl:apply-templates>
		</table>
		<table width="100%">
			<tr>	<td>	<xsl:apply-templates select="descendant::titelbild" mode="Titelseite">
								<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
							</xsl:apply-templates></td></tr>
			<tr>	<td>	<xsl:apply-templates select="descendant::kurstitel" mode="Titelseite">
								<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
							</xsl:apply-templates></td></tr>
			<tr>	<td><xsl:apply-templates select="descendant::ke-bezeich" mode="Titelseite"/>
					<xsl:apply-templates select="descendant::ke-titel" mode="Titelseite"/></td></tr>
			<tr>	<td><xsl:apply-templates select="descendant::a-bezeich" mode="Titelseite"/>
					<xsl:apply-templates select="descendant::autoren" mode="Titelseite"/></td></tr>
			<tr>	<td><xsl:apply-templates select="descendant::m-bezeich" mode="Titelseite"/>
					<xsl:apply-templates select="descendant::mitarbeiter" mode="Titelseite"/></td></tr>
			<tr>	<td><xsl:apply-templates select="descendant::g-bezeich" mode="Titelseite"/>
					<xsl:apply-templates select="descendant::gestalter" mode="Titelseite"/></td></tr>
			<tr>	<td><xsl:apply-templates select="descendant::kursnummer" mode="Titelseite"/>
					<xsl:apply-templates select="descendant::codierung" mode="Titelseite"/>
					<xsl:apply-templates select="descendant::copyright" mode="Titelseite"/></td></tr>
		</table>
	</div>
</xsl:template>

<xsl:template match="bild" mode="Titelseite">
<xsl:param name="Pfad"/>
	<img src="{concat($Pfad,concat($medienpfad,concat(substring-before(@fileref,'web/'),substring-after(@fileref,'web/'))))}" alt="{@alt}" align="{@align}" width="{@width}" height="{@depth}"/>
</xsl:template>

<xsl:template match="feulogo" mode="Titelseite">
<xsl:param name="Pfad"/>
	<td align="left" width="100%">
	<xsl:apply-templates mode="Titelseite">
		<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
	</xsl:apply-templates>
	</td>
</xsl:template>

<xsl:template match="kurstitel" mode="Titelseite">
	<p>
	<xsl:call-template name="standardattribute"/>
	<xsl:apply-templates/>
	</p>
</xsl:template>

<xsl:template match="ke-bezeich" mode="Titelseite">
	<p>
	<xsl:call-template name="standardattribute"/>
	<xsl:apply-templates/>
	</p>
</xsl:template>

<xsl:template match="ke-titel" mode="Titelseite">
	<p>
	<xsl:call-template name="standardattribute"/>
	<xsl:apply-templates/>
	</p>
</xsl:template>

<xsl:template match="autoren" mode="Titelseite">
	<p>
	<xsl:call-template name="standardattribute"/>
	<xsl:apply-templates/>
	</p>
</xsl:template>

<xsl:template match="a-bezeich" mode="Titelseite">
	<p>
	<xsl:call-template name="standardattribute"/>
	<xsl:apply-templates/>
	</p>
</xsl:template>

<xsl:template match="m-bezeich" mode="Titelseite">
	<p>
	<xsl:call-template name="standardattribute"/>
	<xsl:apply-templates/>
	</p>
</xsl:template>

<xsl:template match="mitarbeiter" mode="Titelseite">
	<p>
	<xsl:call-template name="standardattribute"/>
	<xsl:apply-templates/>
	</p>
</xsl:template>

<xsl:template match="g-bezeich" mode="Titelseite">
	<p>
	<xsl:call-template name="standardattribute"/>
	<xsl:apply-templates/>
	</p>
</xsl:template>

<xsl:template match="gestalter" mode="Titelseite">
	<p>
	<xsl:call-template name="standardattribute"/>
	<xsl:apply-templates/>
	</p>
</xsl:template>

<xsl:template match="titelbild" mode="Titelseite">
<xsl:param name="Pfad"/>
	<tr>
		<td align="center">
		<xsl:apply-templates mode="Titelseite">
			<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
		</xsl:apply-templates>
		</td>
	</tr>
</xsl:template>

<xsl:template match="varlogo1" mode="Titelseite">
<xsl:param name="Pfad"/>
	<td>
	<xsl:apply-templates mode="Titelseite">
		<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
	</xsl:apply-templates>
	</td>
</xsl:template>

<xsl:template match="varlogo2" mode="Titelseite">
<xsl:param name="Pfad"/>
	<td>
	<xsl:apply-templates mode="Titelseite">
		<xsl:with-param name="Pfad"><xsl:value-of select="$Pfad"/></xsl:with-param>
	</xsl:apply-templates>
	</td>
</xsl:template>

<xsl:template match="kursnummer" mode="Titelseite">
	<p align="right">
	<xsl:call-template name="standardattribute"/>
	<xsl:apply-templates mode="Titelseite" />
	</p>
</xsl:template>

<xsl:template match="codierung" mode="Titelseite">
	<p align="left">
	<xsl:call-template name="standardattribute"/>
	<xsl:apply-templates mode="Titelseite" />
	</p>
</xsl:template>

<xsl:template match="copyright" mode="Titelseite">
	<p>
	<xsl:call-template name="standardattribute"/>
	<xsl:apply-templates mode="Titelseite"/>
	</p>
</xsl:template>

</xsl:stylesheet>
