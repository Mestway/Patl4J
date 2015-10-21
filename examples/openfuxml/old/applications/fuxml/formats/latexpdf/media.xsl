<?xml version="1.0"?>
<!-- ********************************************************************
	$Id: media.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
<!DOCTYPE xsl:stylesheet [
<!ENTITY absatz 'parent::absatz|parent::absatz-klein|parent::absatz-mini|parent::absatz-ohne'>
<!ENTITY wrapfighorspace '<xsl:value-of select="$config/config/latexparams/wrapfighorspace"/>'>
<!ENTITY wrapfigverspace '<xsl:value-of select="$config/config/latexparams/wrapfigverspace"/>'>
]>

<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:saxon="http://saxon.sf.net/">
<xsl:include href="../common/mediainformation.xsl"/>

	<xsl:template match="medienobjekt">
		<xsl:apply-templates/>
	</xsl:template>
	
	<xsl:template match="medienobjekt" mode="afterbox"><!-- addtocontents funktioniert in breakbox nicht -->
	</xsl:template>

	<xsl:template match="medienobjekt[grafik|bild][not(animation)]" mode="afterbox"><!-- addtocontents funktioniert in breakbox nicht -->
		<xsl:variable name="style" select="$styles/entry[@name='medienobjekt'][@variant='figure']"/>
		<xsl:apply-templates select="$style/tocline"><xsl:with-param name="contextnode" select="current()"/></xsl:apply-templates></xsl:template>

	<xsl:template match="medienobjekt[grafik|bild][not(animation)]">
			<xsl:param name="mode"/>
			<xsl:call-template name="environment.selector">
				<xsl:with-param name="style" select="$styles/entry[@name='medienobjekt'][@variant='figure']"/>
				<xsl:with-param name="medianode" select="bild|grafik"/>
				<xsl:with-param name="mode" select="$mode"/>
			</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="medienobjekt[grafik|bild][animation]">
			<xsl:param name="mode"/>
			<xsl:call-template name="environment.selector">
				<xsl:with-param name="style" select="$styles/entry[@name='medienobjekt'][@variant='figure']"/>
				<xsl:with-param name="medianode" select="bild|grafik"/>
				<xsl:with-param name="mode" select="$mode"/>
			</xsl:call-template>
	</xsl:template>


	<xsl:template match="medienobjekt[(animation|audio|video|simulation)][not(textobjekt)][not(bild|grafik)]">
			<xsl:param name="mode"/>
			<xsl:call-template name="environment.selector">
				<xsl:with-param name="style" select="$styles/entry[@name='medienobjekt'][@variant=name(current()/(animation|audio|video|textobjekt|simulation)[1])]"/>
				<xsl:with-param name="medianode" select="(animation|audio|video|textobjekt|simulation)[1]"/>
				<xsl:with-param name="mode" select="$mode"/>
			</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="medienobjekt[textobjekt][not(animation|video|audio|simulation)][not(bild|grafik)]">
			<xsl:param name="mode"/>
			<xsl:variable name="filetype">
				<xsl:call-template name="get.mediasuffix">
					<xsl:with-param name="mediaref" select="textobjekt/@fileref"/>
				</xsl:call-template>
			</xsl:variable>
			<xsl:choose>
				<xsl:when test="$filetype='xml'">
					<xsl:call-template name="genericmediaobject">
						<xsl:with-param name="style" select="$styles/entry[@name='medienobjekt'][@variant='xml-textobjekt']"/>
						<xsl:with-param name="medianode" select="(animation|audio|video|textobjekt|simulation)[1]"/>
						<xsl:with-param name="mode" select="$mode"/>
					</xsl:call-template>
				</xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="environment.selector">
						<xsl:with-param name="style" select="$styles/entry[@name='medienobjekt'][@variant='textobjekt']"/>
						<xsl:with-param name="medianode" select="textobjekt"/>
						<xsl:with-param name="mode" select="$mode"/>
					</xsl:call-template>
				</xsl:otherwise>
			</xsl:choose>
	</xsl:template>

	
	<xsl:template match="medienobjekt[animation and textobjekt]">
			<xsl:param name="mode"/>
			<xsl:call-template name="environment.selector">
				<xsl:with-param name="style" select="$styles/entry[@name='medienobjekt'][@variant='animation-textobjekt']"/>
				<xsl:with-param name="medianode" select="animation"/>
				<xsl:with-param name="mode" select="$mode"/>
			</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="medienobjekt[count(grafik|bild) gt 1]" priority="1">
			<xsl:param name="mode"/>
			<xsl:call-template name="environment.selector">
				<xsl:with-param name="style" select="$styles/entry[@name='medienobjekt'][@variant='subfigure']"/>
				<xsl:with-param name="medianode" select="animation"/>
				<xsl:with-param name="mode" select="$mode"/>
			</xsl:call-template>
	</xsl:template>



	
	
	
	
	<xsl:template match="mediatitle"><xsl:param name="contextnode"/>
		<xsl:param name="style"/>
		{<xsl:call-template name="get.font-family"><xsl:with-param name="style" select="@font-family"/></xsl:call-template>
		<xsl:call-template name="get.font-style"><xsl:with-param name="style" select="@font-style"/></xsl:call-template>
		<xsl:call-template name="get.font-variant"><xsl:with-param name="style" select="@font-variant"/></xsl:call-template>
		<xsl:call-template name="get.font-weight"><xsl:with-param name="style" select="@font-weight"/></xsl:call-template>
		<xsl:call-template name="get.font-size"><xsl:with-param name="style" select="@font-size"/></xsl:call-template>
		<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}</xsl:template>	
	
	
	


	
	<xsl:template match="objekttitel"><xsl:apply-templates/></xsl:template>
	
	

	<xsl:template match="medienobjekt[grafik and parent::entry]" priority="1">
			<xsl:apply-templates/>
			<!--xsl:apply-templates select="objekttitel" mode="call"/-->
	</xsl:template>

	<xsl:template match="medienobjekt[bild][parent::entry]" priority="1">
			<xsl:apply-templates/>
			<xsl:apply-templates select="objekttitel" mode="call"/>
	</xsl:template>
	


	<xsl:template match="medienobjekt[animation|audio|video|simulation|textobjekt]" mode="picto">\includegraphics<xsl:if test="(animation|audio|video|simulation|textobjekt)/@scale">[scale=<xsl:value-of select="(animation|audio|video|simulation|textobjekt)[1]/@scale"/>]</xsl:if>{<xsl:value-of select="$image_path"/>/<xsl:call-template name="get.medianame"><xsl:with-param name="mediaref" select="(animation|audio|video|simulation|textobjekt)/@pikto"/></xsl:call-template>.<xsl:value-of select="$printgraphic"/>}</xsl:template>

	<xsl:template match="animation" mode="picto">\includegraphics<xsl:if test="@scale">[scale=<xsl:value-of select="@scale"/>]</xsl:if>{<xsl:value-of select="$image_path"/>/<xsl:call-template name="get.medianame"><xsl:with-param name="mediaref" select="@pikto"/></xsl:call-template>.<xsl:value-of select="$printgraphic"/>}</xsl:template>


<xsl:template match="medienobjekt[animation|audio|video|simulation|textobjekt]" mode="alttext"><xsl:value-of select="(animation|audio|video|simulation|textobjekt)[1]/@alt"/></xsl:template>

<!--xsl:template match="grafik|bild">\includegraphics[<xsl:if test="@scale">scale=<xsl:value-of select="@scale"/></xsl:if>]{<xsl:value-of select="$image_path"/>/<xsl:call-template name="get.medianame"><xsl:with-param name="mediaref" select="@fileref"/></xsl:call-template>.<xsl:value-of select="$printgraphic"/>}</xsl:template-->

<xsl:template match="grafik[ancestor::marginalie]|bild[ancestor::marginalie]">{\includegraphics[<xsl:if test="@scale">scale=<xsl:value-of select="@scale"/></xsl:if>]{<xsl:value-of select="$image_path"/>/<xsl:call-template name="get.medianame"><xsl:with-param name="mediaref" select="@fileref"/></xsl:call-template>.<xsl:value-of select="$printgraphic"/>}}</xsl:template>

<xsl:template match="grafik|bild">
	<xsl:param name="mode"/>
	<xsl:choose>
		<xsl:when test="$styles/entry[@name=name(current())] and not(parent::medienobjekt)">
			<xsl:call-template name="environment.selector">
				<xsl:with-param name="style" select="$styles/entry[@name=name(current())]"/>
				<xsl:with-param name="medianode" select="current()"/>
				<xsl:with-param name="mode" select="$mode"/>
			</xsl:call-template>
		</xsl:when>
		<xsl:otherwise>
			<xsl:if test="@fliessen!='' and @fliessen!='nicht'">
				<xsl:if test="$config/config/latexparams/wrapfigverspace!=''">\setlength{\intextsep}{&wrapfigverspace;}</xsl:if>
				<xsl:if test="$config/config/latexparams/wrapfighorspace!=''">\setlength{\columnsep}{&wrapfighorspace;}</xsl:if>
				\begin{wrapfigure}{<xsl:call-template name="mediaobject.fliessen"><!-- s. designinterface-latex.xsl -->
						<xsl:with-param name="medianode" select="current()"/>
				</xsl:call-template>}{<xsl:value-of select="@width"/>in/
				<xsl:choose>
					<xsl:when test="@resolution"><xsl:value-of select="@resolution"/></xsl:when>
					<xsl:when test="$config/config/latexparams/resolution"><xsl:value-of select="$config/config/latexparams/resolution"/></xsl:when>
					<xsl:otherwise>96</xsl:otherwise>
				</xsl:choose>}
			</xsl:if>
			<xsl:if test="position()=1">\vspace{2\parskip}</xsl:if>
			<xsl:if test="&absatz; and not(parent::*[text()])">
				<xsl:call-template name="map.align.line"><xsl:with-param name="align" select="@align"/></xsl:call-template>
			</xsl:if>
			\includegraphics[<xsl:if test="@scale">scale=<xsl:value-of select="@scale"/></xsl:if>]{<xsl:value-of select="$image_path"/>/<xsl:call-template name="get.medianame">
					<xsl:with-param name="mediaref" select="@fileref"/>
			</xsl:call-template>.<xsl:value-of select="$printgraphic"/>}
			<xsl:if test="&absatz; and not(parent::*[text()])">}</xsl:if>
			<xsl:if test="@fliessen!='' and @fliessen!='nicht'">\end{wrapfigure}\mbox{}</xsl:if>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

	
	

<xsl:template match="simulation|audio|animation|textobjekt|video">\includegraphics*{<xsl:value-of select="$image_path"/>/<xsl:call-template name="get.medianame"><xsl:with-param name="mediaref" select="@pikto"/></xsl:call-template>.<xsl:value-of select="$printgraphic"/>}</xsl:template>

<xsl:template match="textobjekt" mode="printtext"><xsl:apply-templates/></xsl:template>

</xsl:stylesheet>