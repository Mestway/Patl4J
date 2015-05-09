<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">

<!-- ********************************************************************
     $Id: abschnitt.xsl,v 1.3 2007/01/11 13:38:26 gebhard Exp $
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
     This file contain the design-roles for section-Element
     ******************************************************************** -->

<!-- ==================================================================== -->
<xsl:template match="abschnitt">
	<xsl:variable name="id" select="@id"/>
		<div>
			<xsl:call-template name="standardattribute"/>
			<xsl:apply-templates select="titel"/>
		</div>
</xsl:template>

</xsl:stylesheet>
