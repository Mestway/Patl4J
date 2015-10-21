<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<!-- ********************************************************************
     $Id: mathenv.xsl,v 1.3 2007/01/11 13:38:26 gebhard Exp $
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
     This file contains the functions for elements of all labelbox-elements (math and more).
     
     Use in Pass 4:
	
	*******************************************************************
	Beschreibung:
	Diese Datei enthält die Designvorschriften für die LabelBox-Elemente wie z.B. bathematische Elemente
     ********************************************************************
-->

	<xsl:template match="	beispiel |
								merksatz |
								vertiefung |
								exkurs |
								kommentar |
								norm |
								rechtsprechung |
								zusammenfassung |
								axiom |
								definition |
								corollar |
								folgerung |
								fall |
								problem |
								loesung |
								hauptsatz |
								hilfssatz |
								lemma |
								proposition |
								regel |
								satz |
								theorem |
								satz |
								annahme |
								bemerkung |
								beweis |
								x-umgebung |
								zitat
							">
		<xsl:call-template name="labelbox">
			<xsl:with-param name="style" select="$styles/entry[@name=name(current())]"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="zwischentitel">
		<xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="zwischentitel[parent::literaturliste|parent::fnabschnitt]">
		<p>
			<xsl:call-template name="standardattribute"/>		
			<xsl:apply-templates/>
		</p>
	</xsl:template>


</xsl:stylesheet>
