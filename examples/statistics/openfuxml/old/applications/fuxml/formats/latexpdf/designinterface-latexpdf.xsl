<?xml version="1.0" encoding="UTF-8"?>
<!-- ********************************************************************
	$Id: designinterface-latexpdf.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
<xsl:stylesheet version="2.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:saxon="http://saxon.sf.net/">
	<xsl:import href="../common/designinterface.xsl"/>
	<xsl:variable name="request" select="document(concat($workdir,'/request.xml'))"/>
	<xsl:variable name="config" select="document(concat($workdir,'/config.tex.xml'))"/>
	

	<!-- ==================================================================== -->
	<!-- Environments-->
	<!-- ==================================================================== -->
	<xsl:template name="environment.selector">
		<xsl:param name="style"/>
		<xsl:param name="mode"/>
		<xsl:param name="medianode"/>
		<xsl:choose>
			<xsl:when test="$style/envtype='labelbox'">
				<xsl:call-template name="labelbox"><xsl:with-param name="style" select="$style"/></xsl:call-template>
			</xsl:when>
			<xsl:when test="$style/envtype='genericbox'">
				<xsl:call-template name="genericbox"><xsl:with-param name="style" select="$style"/></xsl:call-template>
			</xsl:when>
			<xsl:when test="$style/envtype='nobox'">
				<xsl:call-template name="nobox"><xsl:with-param name="style" select="$style"/></xsl:call-template>
			</xsl:when>
			<xsl:when test="$style/envtype='breakbox'">
				<xsl:call-template name="breakbox"><xsl:with-param name="style" select="$style"/></xsl:call-template>
			</xsl:when>
			<xsl:when test="$style/envtype='assignment'">
				<xsl:call-template name="assignment">
					<xsl:with-param name="style" select="$style"/>
					<xsl:with-param name="mode" select="$mode"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$style/envtype='mediaobject'">
				<xsl:call-template name="mediaobject">
					<xsl:with-param name="style" select="$style"/>
					<xsl:with-param name="mode" select="$mode"/>
					<xsl:with-param name="medianode" select="$medianode"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$style/envtype='genericmediaobject'">
				<xsl:call-template name="genericmediaobject">
					<xsl:with-param name="style" select="$style"/>
					<xsl:with-param name="mode" select="$mode"/>
					<xsl:with-param name="medianode" select="$medianode"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$style/envtype='direct'">
				<xsl:call-template name="direct"><xsl:with-param name="style" select="$style"/></xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:message>Info: no configuration entry for element <xsl:value-of select="saxon:path()"/> in config.xml</xsl:message>
				<xsl:apply-templates/>
			</xsl:otherwise>
		</xsl:choose>
		<!--xsl:message>XXX<xsl:value-of select="concat($style/@name, $style/envtype)"/></xsl:message-->
	</xsl:template>
	<!-- ==================================================================== -->
	<xsl:template name="labelbox"><xsl:param name="style"/><xsl:param name="mode"/><xsl:if test="$style/margin-top!=''">\vspace{<xsl:value-of select="$style/margin-top"/>}</xsl:if>
		<xsl:apply-templates select="$style/text-before"><xsl:with-param name="contextnode" select="current()"/><xsl:with-param name="style" select="$style"/>
</xsl:apply-templates>\pagebreak[2]\begin{list}{}{<xsl:call-template name="get.font-family"><xsl:with-param name="style" select="$style/font-family"/></xsl:call-template>
		<xsl:call-template name="get.font-style"><xsl:with-param name="style" select="$style/font-style"/></xsl:call-template>
		<xsl:call-template name="get.font-variant"><xsl:with-param name="style" select="$style/font-variant"/></xsl:call-template>
		<xsl:call-template name="get.font-weight"><xsl:with-param name="style" select="$style/font-weight"/></xsl:call-template>
		<xsl:call-template name="get.font-size"><xsl:with-param name="style" select="$style/font-size"/></xsl:call-template>
		<xsl:if test="$style/margin-left!=''">\setlength{\leftmargin}{<xsl:value-of select="$style/margin-left"/>}</xsl:if>
		<xsl:if test="$style/margin-right!=''">\setlength{\rightmargin}{<xsl:value-of select="$style/margin-right"/>}</xsl:if>
		<xsl:if test="$style/label/@labelwidth!=''">\setlength{\labelwidth}{<xsl:value-of select="$style/label/@labelwidth"/>}</xsl:if>
		<xsl:if test="$style/label/@labelsep!=''">\setlength{\labelsep}{<xsl:value-of select="$style/label/@labelsep"/>}</xsl:if>
		<xsl:if test="$style/label/@itemindent!=''">\setlength{\itemindent}{<xsl:value-of select="$style/label/@itemindent"/>}</xsl:if>\setlength{\topsep}{0pt}\setlength{\partopsep}{0pt}\setlength{\itemsep}{0pt}\setlength{\parsep}{0pt}}<xsl:choose>
			<xsl:when test="$style/text-align='left'">\raggedright</xsl:when>
			<xsl:when test="$style/text-align='right'">\raggedleft</xsl:when>
			<xsl:when test="$style/text-align='center'">\centering</xsl:when>
		</xsl:choose>
			\item[{<xsl:if test="titel|zwischentitel"><xsl:apply-templates select="$style/label">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates></xsl:if>}]<xsl:apply-templates select="$style/marg"><xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates><xsl:if test="((zwischentitel!='' or titel!='') and $style/label/processing-instruction('newline')) or $style/label/@newline='true' or zwischentitel!='' or titel!=''"><xsl:call-template name="newline"><xsl:with-param name="style" select="$style"/></xsl:call-template></xsl:if>
		<xsl:apply-templates select="$style/line-height"/><xsl:apply-templates select="node()[name()!='zwischentitel'][name()!='titel']"><xsl:with-param name="mode" select="$mode"/></xsl:apply-templates>\vspace{\parskip}\end{list}\vspace{-\parskip}<xsl:apply-templates select="$style/text-after">
			<xsl:with-param name="contextnode" select="current()"/><xsl:with-param name="style" select="$style"/>
		</xsl:apply-templates><xsl:if test="$style/margin-bottom!=''">\vspace{<xsl:value-of select="$style/margin-bottom"/>}</xsl:if></xsl:template>
	<!-- ==================================================================== -->
	
	
	<xsl:template name="genericbox"><xsl:param name="style"/><xsl:param name="mode"/><xsl:if test="$style/margin-top!=''">\vspace{<xsl:value-of select="$style/margin-top"/>}</xsl:if>\pagebreak[2]\begin{list}{}{<xsl:call-template name="get.font-family"><xsl:with-param name="style" select="$style/font-family"/></xsl:call-template>
		<xsl:if test="$style/margin-left!=''">\setlength{\leftmargin}{<xsl:value-of select="$style/margin-left"/>}</xsl:if>
		<xsl:if test="$style/margin-right!=''">\setlength{\rightmargin}{<xsl:value-of select="$style/margin-right"/>}</xsl:if>
		<xsl:if test="$style/label/@labelwidth!=''">\setlength{\labelwidth}{<xsl:value-of select="$style/label/@labelwidth"/>}</xsl:if>
		<xsl:if test="$style/label/@labelsep!=''">\setlength{\labelsep}{<xsl:value-of select="$style/label/@labelsep"/>}</xsl:if>
		<xsl:if test="$style/listparindent!=''">\setlength{\listparindent}{<xsl:value-of select="$style/listparindent"/>}</xsl:if>
		<xsl:if test="$style/itemindent!=''">\setlength{\itemindent}{<xsl:value-of select="$style/itemindent"/>}</xsl:if>\setlength{\topsep}{0pt}\setlength{\partopsep}{0pt}\setlength{\itemsep}{0pt}\setlength{\parsep}{0pt}}
		\item[]<xsl:apply-templates select="$style/marg">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates><xsl:apply-templates select="$style/structure"><xsl:with-param name="contextnode" select="current()"/><xsl:with-param name="mode" select="$mode"/></xsl:apply-templates>
		\vspace{\parskip}\end{list}\vspace{-\parskip}<xsl:if test="$style/margin-bottom!=''">\vspace{<xsl:value-of select="$style/margin-bottom"/>}</xsl:if></xsl:template>
	<!-- ==================================================================== -->

	<xsl:template name="nobox"><xsl:param name="style"/><xsl:param name="mode"/><xsl:if test="$style/margin-top!=''">\vspace{<xsl:value-of select="$style/margin-top"/>}</xsl:if>{<xsl:apply-templates select="$style/structure"><xsl:with-param name="contextnode" select="current()"/><xsl:with-param name="mode" select="$mode"/>
</xsl:apply-templates>}<xsl:if test="$style/margin-bottom!=''">\vspace{<xsl:value-of select="$style/margin-bottom"/>}</xsl:if></xsl:template>

	<!-- ==================================================================== -->
	<xsl:template name="inline"><xsl:param name="style"/><xsl:param name="mode"/>{<xsl:apply-templates select="$style/structure"><xsl:with-param name="contextnode" select="current()"/><xsl:with-param name="mode" select="$mode"/>
</xsl:apply-templates>}</xsl:template>

	<!-- ==================================================================== -->
	
	<xsl:template name="direct"><xsl:param name="style"/><xsl:param name="mode"/><xsl:apply-templates select="$style/structure"><xsl:with-param name="contextnode" select="current()"/><xsl:with-param name="mode" select="$mode"/>
	</xsl:apply-templates></xsl:template>
	<!-- ==================================================================== -->
	<xsl:template name="breakbox"><xsl:param name="style"/><xsl:if test="$style/margin-top!=''">\vspace{<xsl:value-of select="$style/margin-top"/>}</xsl:if>
		\pagebreak[2]<xsl:if test="$style/fboxrule!=''">\setlength{\fboxrule}{<xsl:value-of select="$style/fboxrule"/>}</xsl:if><xsl:if test="$style/fboxsep!=''">\setlength{\fboxsep}{<xsl:value-of select="$style/fboxsep"/>}</xsl:if><xsl:if test="descendant::margref"><xsl:apply-templates select="descendant::margref" mode="breakbox"/></xsl:if>\begin{colbreakbox}{<xsl:choose>
			<xsl:when test="$style/shading"><xsl:value-of select="$style/shading"/></xsl:when><xsl:otherwise>1</xsl:otherwise>
		</xsl:choose>}
		{\setlength{\parindent}{0ex}<xsl:apply-templates select="$style/structure"><xsl:with-param name="contextnode" select="current()"/><xsl:with-param name="mode">inbox</xsl:with-param></xsl:apply-templates>}
		\end{colbreakbox}<xsl:apply-templates select="descendant::fnref|descendant::medienobjekt" mode="afterbox"/>
		<xsl:if test="$style/margin-bottom!=''">\vspace{<xsl:value-of select="$style/margin-bottom"/>}</xsl:if></xsl:template>

	<!-- ==================================================================== -->

	<xsl:template name="assignment"><xsl:param name="style"/><xsl:param name="mode"/>
		<xsl:if test="$style/margin-top!=''">\vspace{<xsl:value-of select="$style/margin-top"/>}</xsl:if>\pagebreak[3]\begin{list}{}{
		<xsl:if test="$style/margin-left!=''">\setlength{\leftmargin}{<xsl:value-of select="$style/margin-left"/>}</xsl:if>
		<xsl:if test="$style/margin-right!=''">\setlength{\rightmargin}{<xsl:value-of select="$style/margin-right"/>}</xsl:if>
		<xsl:if test="$style/label/@labelwidth!=''">\setlength{\labelwidth}{<xsl:value-of select="$style/label/@labelwidth"/>}</xsl:if>
		<xsl:if test="$style/label/@labelsep!=''">\setlength{\labelsep}{<xsl:value-of select="$style/label/@labelsep"/>}</xsl:if>
		<xsl:if test="$style/label/@itemindent!=''">\setlength{\itemindent}{<xsl:value-of select="$style/label/@itemindent"/>}</xsl:if>
		\setlength{\topsep}{0pt}\setlength{\partopsep}{0pt}\setlength{\itemsep}{0pt}\setlength{\parsep}{0pt}}<xsl:choose>
			<xsl:when test="$style/text-align='left'">\raggedright</xsl:when>
			<xsl:when test="$style/text-align='right'">\raggedleft</xsl:when>
			<xsl:when test="$style/text-align='center'">\centering</xsl:when>
		</xsl:choose>\item[{<xsl:apply-templates select="$style/label">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
		</xsl:apply-templates>}]<xsl:apply-templates select="$style/marg"><xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates><xsl:if test="$style/label/@newline='true' or zwischentitel!=''">\mbox{}\\*<xsl:if test="$style/label/@spacing!=''">[<xsl:value-of select="$style/label/@spacing"/>]</xsl:if></xsl:if><xsl:apply-templates select="$style/structure">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
		\end{list}
		<!-- Unterer Rand -->
		<xsl:if test="$style/margin-bottom!=''">\vspace{<xsl:value-of select="$style/margin-bottom"/>}</xsl:if>
	</xsl:template>
	<!-- ==================================================================== -->

	<xsl:template name="mediaobject">
		<xsl:param name="style"/>
		<xsl:param name="medianode"/>
		<xsl:param name="mode"/>
		<xsl:variable name="width"><xsl:value-of select="$medianode/@width"/>pt/<xsl:choose><xsl:when test="$medianode/@resolution"><xsl:value-of select="$medianode/@resolution"/></xsl:when><xsl:when test="$config/config/latexparams/resolution"><xsl:value-of select="$config/config/latexparams/resolution"/></xsl:when><xsl:otherwise>96</xsl:otherwise></xsl:choose>*\real{72.27}</xsl:variable>
		<xsl:variable name="height"><xsl:value-of select="$medianode/@depth"/>pt/<xsl:choose><xsl:when test="$medianode/@resolution"><xsl:value-of select="$medianode/@resolution"/></xsl:when><xsl:when test="$config/config/latexparams/resolution"><xsl:value-of select="$config/config/latexparams/resolution"/></xsl:when><xsl:otherwise>96</xsl:otherwise></xsl:choose>*\real{72.27}</xsl:variable>
		<xsl:variable name="wrapfighorspace" select="$config/config/latexparams/wrapfighorspace"/>
		<xsl:variable name="align">
			<xsl:call-template name="map.align">
				<xsl:with-param name="align">
					<xsl:choose>
						<xsl:when test="$medianode/@align!=''"><xsl:value-of select="$medianode/@align"/></xsl:when>
						<xsl:otherwise><xsl:value-of select="$style/align"/></xsl:otherwise>
					</xsl:choose>
				</xsl:with-param>
			</xsl:call-template>
		</xsl:variable><xsl:choose><xsl:when test="$medianode/@fliessen!='' or $medianode/@fliessen!='nicht'">\setlength{\wrapfigwidth}{<xsl:value-of select="$wrapfighorspace"/>+<xsl:value-of select="$width"/>}</xsl:when>
			<xsl:otherwise>\setlength{\wrapfigwidth}{0pt}</xsl:otherwise>
		</xsl:choose><xsl:if test="$style/margin-top!='' and @gleiten!='ja'">\vspace{<xsl:value-of select="$style/margin-top"/>}</xsl:if>
		<xsl:if test="@gleiten='ja'">\begin{figure}[htbp]</xsl:if>
		{<xsl:apply-templates select="processing-instruction('psfrag')"/><xsl:value-of select="$align"/><xsl:choose>
			<xsl:when test="$medianode/@fliessen='links'">
				\makebox[<xsl:value-of select="$width"/>][t]{\raisebox{-<xsl:value-of select="$height"/>+1ex}{<xsl:call-template name="mediaobject.objectpart">
					<xsl:with-param name="medianode" select="$medianode"/>
					<xsl:with-param name="style" select="$style"/>
				</xsl:call-template>}}<xsl:if test="objekttitel">\hspace{<xsl:value-of select="$wrapfighorspace"/>}\parbox[t]{\columnwidth-\wrapfigwidth<!--xsl:value-of select="$medianode/@width"/>in/<xsl:choose><xsl:when test="$medianode/@resolution"><xsl:value-of select="$medianode/@resolution"/></xsl:when><xsl:otherwise>96</xsl:otherwise></xsl:choose>-<xsl:value-of select="$wrapfighorspace"/-->}{<xsl:call-template name="mediaobject.titlepart">
					<xsl:with-param name="medianode" select="$medianode"/>
					<xsl:with-param name="style" select="$style"/>
				</xsl:call-template>}</xsl:if>
			</xsl:when>
			<xsl:when test="$medianode/@fliessen='rechts'">
				<xsl:if test="objekttitel">\parbox[t]{\columnwidth-\wrapfigwidth<!--xsl:value-of select="$medianode/@width"/>in/<xsl:choose><xsl:when test="$medianode/@resolution"><xsl:value-of select="$medianode/@resolution"/></xsl:when><xsl:otherwise>96</xsl:otherwise></xsl:choose>-<xsl:value-of select="$wrapfighorspace"/-->}{<xsl:call-template name="mediaobject.titlepart">
					<xsl:with-param name="medianode" select="$medianode"/>
					<xsl:with-param name="style" select="$style"/>
				</xsl:call-template>}</xsl:if>\hspace{<xsl:value-of select="$wrapfighorspace"/>}\makebox[<xsl:value-of select="$width"/>][t]{\raisebox{-<xsl:value-of select="$height"/>+1ex}{<xsl:call-template name="mediaobject.objectpart">
					<xsl:with-param name="medianode" select="$medianode"/>
					<xsl:with-param name="style" select="$style"/>
				</xsl:call-template>}}
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="mediaobject.objectpart">
					<xsl:with-param name="medianode" select="$medianode"/>
					<xsl:with-param name="style" select="$style"/>
				</xsl:call-template><xsl:if test="objekttitel">\\*[<xsl:value-of select="$style/mediatitle/@spacing"/>]
				<xsl:call-template name="mediaobject.titlepart">
					<xsl:with-param name="medianode" select="$medianode"/>
					<xsl:with-param name="style" select="$style"/>
				</xsl:call-template></xsl:if>
			</xsl:otherwise>
		</xsl:choose>
		\label{<xsl:value-of select="@id"/>}}
		<xsl:if test="@gleiten='ja'">\end{figure}</xsl:if><xsl:if test="$mode!='inbox'"><xsl:apply-templates select="$style/tocline"><xsl:with-param name="contextnode" select="current()"/></xsl:apply-templates></xsl:if><xsl:if test="$style/margin-bottom!='' and @gleiten!='ja'">\vspace{<xsl:value-of select="$style/margin-bottom"/>}</xsl:if>	
		<xsl:text xml:space="preserve">&#xD;&#xA;&#xD;&#xA;</xsl:text>
	</xsl:template>
		
	<xsl:template name="mediaobject.objectpart"><xsl:param name="style"/><xsl:param name="medianode"/><xsl:if test="$style/border">
			\setlength{\fboxrule}{<xsl:value-of select="$style/border/@fboxrule"/>}\setlength{\fboxsep}{<xsl:value-of select="$style/border/@fboxsep"/>}
			\framebox{</xsl:if>\includegraphics[<xsl:if test="$medianode/@scale">scale=<xsl:value-of select="$medianode/@scale"/></xsl:if>]{<xsl:value-of select="$image_path"/>/<xsl:call-template name="get.medianame"><xsl:with-param name="mediaref" select="$medianode/@fileref"/></xsl:call-template>.<xsl:value-of select="$printgraphic"/>}<xsl:if test="$style/border">}</xsl:if></xsl:template>
		
	<xsl:template name="mediaobject.titlepart"><xsl:param name="style"/><xsl:param name="medianode"/><xsl:apply-templates select="$style/mediatitle">
					<xsl:with-param name="contextnode" select="current()"/>
					<xsl:with-param name="style" select="$style"/>
					</xsl:apply-templates></xsl:template>
					
	<xsl:template name="mediaobject.fliessen"><xsl:param name="medianode"/><xsl:choose>
			<xsl:when test="@fliessen='links'">l</xsl:when>
			<xsl:when test="@fliessen='rechts'">r</xsl:when>
			<xsl:when test="@fliessen='innen'">i</xsl:when>
			<xsl:when test="@fliessen='aussen'">o</xsl:when>
			<xsl:otherwise>r</xsl:otherwise>
		</xsl:choose></xsl:template>
		<!-- ==================================================================== -->

	<xsl:template name="genericmediaobject">
		<xsl:param name="style"/>
		<xsl:param name="medianode"/>
		<xsl:param name="mode"/>
		<xsl:variable name="align">
			<xsl:call-template name="map.align">
				<xsl:with-param name="align">
					<xsl:choose>
						<xsl:when test="$medianode/@align!=''"><xsl:value-of select="$medianode/@align"/></xsl:when>
						<xsl:otherwise><xsl:value-of select="$style/align"/></xsl:otherwise>
					</xsl:choose>
				</xsl:with-param>
			</xsl:call-template>
		</xsl:variable><xsl:if test="$style/margin-top!=''">\vspace{<xsl:value-of select="$style/margin-top"/>}</xsl:if>
		<xsl:if test="@gleiten='ja'">\begin{figure}[htbp]</xsl:if>
		{\label{<xsl:value-of select="@id"/>}<xsl:value-of select="$align"/>
			<xsl:apply-templates select="$style/structure">
					<xsl:with-param name="contextnode" select="current()"/>
					<xsl:with-param name="style" select="$style"/>
			</xsl:apply-templates>}
			<xsl:text xml:space="preserve">&#xD;&#xA;&#xD;&#xA;</xsl:text>
		<xsl:if test="@gleiten='ja'">\end{figure}</xsl:if><xsl:if test="$mode!='inbox'"><xsl:apply-templates select="$style/tocline"><xsl:with-param name="contextnode" select="current()"/></xsl:apply-templates></xsl:if><xsl:if test="$style/margin-bottom!=''">\vspace{<xsl:value-of select="$style/margin-bottom"/>}</xsl:if>
	</xsl:template>
		
	<!-- ==================================================================== -->
	<xsl:template match="label[parent::entry[envtype='genericbox' or envtype='breakbox']]"><xsl:param name="contextnode"/>
		<xsl:param name="style"/>
		{<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}</xsl:template>
	
	<xsl:template name="map.align">
		<xsl:param name="align"/><xsl:choose>
			<xsl:when test="$align='center'">\centering{}</xsl:when>
			<xsl:when test="$align='left'">\raggedright{}</xsl:when>
			<xsl:when test="$align='right'">\raggedleft{}</xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="map.align.line">
		<xsl:param name="align"/><xsl:choose>
			<xsl:when test="$align='center'">\centerline{</xsl:when>
			<xsl:when test="$align='left'">\leftline{</xsl:when>
			<xsl:when test="$align='right'">\rightline{</xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>


	
	<!-- ==================================================================== -->
	<xsl:template match="marg">	<xsl:param name="contextnode"/><xsl:param name="style"/><xsl:if test="node()">\mbox{}\marginpar[\raggedleft\hspace{0pt}<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>]{\raggedright\hspace{0pt}<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}</xsl:if></xsl:template>
	<!-- ==================================================================== -->
	<xsl:template name="newline">
	<xsl:param name="style"/>
	\mbox{}\\*<xsl:if test="$style/label/@spacing!=''">[<xsl:value-of select="$style/label/@spacing"/>]</xsl:if>
	</xsl:template>
	<!-- ==================================================================== -->

	<xsl:template match="line-height"><xsl:if test=".!=''">\setlength{\baselineskip}{<xsl:value-of select="."/>}</xsl:if></xsl:template>
	
	<!-- ==================================================================== -->
	<!-- Elements of the Design Interface -->
	<!-- ==================================================================== -->

	
	<xsl:template match="text-after"><xsl:param name="style"/><xsl:param name="contextnode"></xsl:param><xsl:apply-templates select="node()">
		<xsl:with-param name="contextnode" select="$contextnode"/>
		<xsl:with-param name="style" select="$style"/></xsl:apply-templates>\par{}</xsl:template>
	
	<xsl:template match="structure[@format='latexpdf']">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/>
		<xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="structure[@format='html']"/>
		
	<xsl:template match="file">
		<xsl:param name="contextnode"/>
		<xsl:variable name="filename">
			<xsl:apply-templates select="filename">
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
		</xsl:variable>
		<xsl:variable name="directory">
			<xsl:apply-templates select="directory">
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
		</xsl:variable>
		<xsl:message><xsl:value-of select="$filename"/></xsl:message>
		<!--xsl:if test="$request/sessionpreferences/productionentities/file[@filename=concat(normalize-space($filename),'.pdf')]"-->
		<xsl:result-document href="{$filename}.tex">
			<xsl:call-template name="latexheader"><xsl:with-param name="pagelayout" select="$config/config/pagelayout"/><xsl:with-param name="contextnode" select="$contextnode"/></xsl:call-template>
			\begin{document}
				<!--xsl:variable name="pagestyle" select="$config/config/pagelayout/pagestyle"/>
				<xsl:if test="$pagestyle/@headrulewidth!=''">\renewcommand{\headrulewidth}{<xsl:apply-templates select="$pagestyle/@headrulewidth"/>}</xsl:if>
				<xsl:if test="$pagestyle/@footrulewidth!=''">\renewcommand{\footrulewidth}{<xsl:apply-templates select="$pagestyle/@footrulewidth"/>}</xsl:if>
				<xsl:if test="$pagestyle/@headwidth!=''">\setlength{\headwidth}{<xsl:apply-templates select="$pagestyle/@headwidth"/>}</xsl:if-->
				<xsl:apply-templates select="content">
					<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
			\end{document}
		</xsl:result-document>
		<!--/xsl:if-->
	</xsl:template>
	
	
	<!-- ==================================================================== -->
	<xsl:template match="frontmatter">
		<xsl:param name="contextnode"/>
		\frontmatter
		<xsl:apply-templates select="$config/config/pagenumbering/pagenumberformat[@variant='frontmatter']">
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates>
		<xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates>
	</xsl:template>
	<!-- ==================================================================== -->
	<xsl:template match="mainmatter">
		<xsl:param name="contextnode"/>
		\mainmatter
		<xsl:apply-templates select="$config/config/pagenumbering/pagenumberformat[@variant='mainmatter']">
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates>
		<xsl:if test="$config/config/pagenumbering/pagenumberformat[@variant='mainmatter']/@style='continuous'">
			<xsl:apply-templates select="ancestor::file/@precedingfile" mode="page">
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
		</xsl:if>
		<xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates>
		<xsl:if test="not(following-sibling::backmatter)">\cleardoublepage\label{lastpage}\typeout{@lastpage:@\thepage}</xsl:if>
	</xsl:template>
	
	<!-- ==================================================================== -->
	<xsl:template match="pagenumberformat">
		<xsl:param name="contextnode"/>
		<xsl:choose>
			<xsl:when test="@style='prefix'">
				<xsl:apply-templates select="prefix">
					<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
			</xsl:when>
			<xsl:when test="@style='continuous'">\pagenumbering{arabic}
			</xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!-- ==================================================================== -->
	<xsl:template match="@precedingfile" mode="page">\input{<xsl:value-of select="."/>.lastpage}</xsl:template>
	<!-- ==================================================================== -->
	
	<xsl:template match="prefix">
		<xsl:param name="contextnode"/>
		\pagenumbering[<xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates>]{bychapter}
	</xsl:template>	

	
	<!-- ==================================================================== -->

	<xsl:template match="newpage">\newpage{}</xsl:template>
	
	
	
	<!-- ==================================================================== -->
	<!--                                                      Boxes                                                                          -->	
	<!-- ==================================================================== -->

	<xsl:template match="mbox"><xsl:param name="contextnode"/>\mbox{<xsl:apply-templates><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}</xsl:template>
	
	<xsl:template match="fbox"><xsl:param name="contextnode"/><xsl:if test="@fboxrule!=''">\setlength{\fboxrule}{<xsl:value-of select="@fboxrule"/>}</xsl:if><xsl:if test="@fboxsep!=''">\setlength{\fboxsep}{<xsl:value-of select="@fboxsep"/>}</xsl:if>\fbox{<xsl:apply-templates><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}</xsl:template>

	<xsl:template match="makebox"><xsl:param name="contextnode"/>\makebox
		[<xsl:value-of select="@boxwidth"/>]
		<xsl:if test="@pos!=''">[<xsl:value-of select="@pos"/>]</xsl:if>{<xsl:apply-templates><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}</xsl:template>

	<xsl:template match="parbox"><xsl:param name="contextnode"/>\parbox<xsl:if test="@pos!=''">[<xsl:value-of select="@pos"/>]</xsl:if>
				<xsl:if test="@boxheight!=''">[<xsl:apply-templates select="@boxheight"/>]</xsl:if>
				<xsl:if test="@ipos!=''">[<xsl:value-of select="@ipos"/>]</xsl:if>
				<xsl:if test="@boxwidth!=''">{<xsl:apply-templates select="@boxwidth"/>}</xsl:if>{<xsl:apply-templates select="node()">
						<xsl:with-param name="contextnode" select="$contextnode"/>
						<xsl:with-param name="mode">inbox</xsl:with-param>
					</xsl:apply-templates>}<xsl:if test="not($contextnode/self::kurseinheit or $contextnode/self::kurs)"><xsl:apply-templates select="$contextnode/descendant::fnref" mode="fninbox"/></xsl:if></xsl:template>
	
	<xsl:template match="minipage">
		<xsl:param name="contextnode"/>
		\begin{minipage}<xsl:if test="@pos!=''">[<xsl:value-of select="@pos"/>]</xsl:if>
				<xsl:if test="@boxheight!=''">[<xsl:apply-templates select="@boxheight"/>]</xsl:if>
				<xsl:if test="@ipos!=''">[<xsl:value-of select="@ipos"/>]</xsl:if>
				<xsl:if test="@boxwidth!=''">{<xsl:apply-templates select="@boxwidth"/>}</xsl:if>
					<xsl:apply-templates select="node()">
						<xsl:with-param name="contextnode" select="$contextnode"/>
						<xsl:with-param name="mode">inbox</xsl:with-param>
					</xsl:apply-templates>\end{minipage}<xsl:if test="not($contextnode/self::kurseinheit or $contextnode/self::kurs)"><xsl:apply-templates select="$contextnode/descendant::fnref" mode="fninbox"/></xsl:if></xsl:template>

	<xsl:template match="@boxwidth|@boxheight"><xsl:choose>
		<xsl:when test=".='textwidth'">\textwidth</xsl:when>
		<xsl:when test=".='height'">\height</xsl:when>
		<xsl:when test=".='width'">\width</xsl:when>
		<xsl:otherwise><xsl:value-of select="."/></xsl:otherwise>
	</xsl:choose></xsl:template>
	
	<xsl:template match="raisebox"><xsl:param name="contextnode"/>\raisebox{<xsl:value-of select="@lift"/>}<xsl:if test="@width!=''">[<xsl:apply-templates select="@width"/>]</xsl:if>
				   <xsl:if test="@height!=''">[<xsl:value-of select="@height"/>]</xsl:if>
					{<xsl:apply-templates select="node()">
						<xsl:with-param name="contextnode" select="$contextnode"/>
						<xsl:with-param name="mode">inbox</xsl:with-param>
					</xsl:apply-templates>}<xsl:if test="not($contextnode/self::kurseinheit or $contextnode/self::kurs)"><xsl:apply-templates select="$contextnode/descendant::fnref" mode="fninbox"/></xsl:if>	</xsl:template>
	
	<xsl:template match="framebox">
		<xsl:param name="contextnode"/>
		<xsl:if test="@fboxrule!=''">\setlength{\fboxrule}{<xsl:value-of select="@fboxrule"/>}</xsl:if><xsl:if test="@fboxsep!=''">\setlength{\fboxsep}{<xsl:value-of select="@fboxsep"/>}</xsl:if>\framebox<xsl:if test="@boxwidth!=''">[<xsl:apply-templates select="@boxwidth"/>]</xsl:if>
				   <xsl:if test="@pos!=''">[<xsl:value-of select="@pos"/>]</xsl:if>
					{<xsl:apply-templates select="node()">
						<xsl:with-param name="contextnode" select="$contextnode"/>
						<xsl:with-param name="mode">inbox</xsl:with-param>
					</xsl:apply-templates>}<xsl:apply-templates select="$contextnode/descendant::fnref" mode="fninbox"/>
	</xsl:template>
	
	<xsl:template match="shabox">
		<xsl:param name="contextnode"/>
		<xsl:if test="@sboxrule!=''">\setlength{\sboxrule}{<xsl:value-of select="@sboxrule"/>}</xsl:if><xsl:if test="@sboxsep!=''">\setlength{\sboxsep}{<xsl:value-of select="@sboxsep"/>}</xsl:if><xsl:if test="@sdim!=''">\setlength{\sdim}{<xsl:value-of select="@sdim"/>}</xsl:if>\shabox
				  	{<xsl:apply-templates select="node()">
						<xsl:with-param name="contextnode" select="$contextnode"/>
						<xsl:with-param name="mode">inbox</xsl:with-param>
					</xsl:apply-templates>}<xsl:apply-templates select="$contextnode/descendant::fnref" mode="fninbox"/>
	</xsl:template>


	
	<xsl:template match="rule">\rule<xsl:if test="@lift!=''">[<xsl:value-of select="@lift"/>]</xsl:if>{<xsl:value-of select="@width"/>}{<xsl:value-of select="@height"/>}</xsl:template>

	<xsl:template match="colorbox">
		<xsl:param name="contextnode"/><xsl:if test="@fboxsep!=''">\setlength{\fboxsep}{<xsl:value-of select="@fboxsep"/>}</xsl:if>\definecolor{<xsl:value-of select="@color"/>}{<xsl:value-of select="@model"/>}{<xsl:value-of select="@value"/>}\colorbox{<xsl:value-of select="@color"/>}{<xsl:apply-templates select="node()">
						<xsl:with-param name="contextnode" select="$contextnode"/>
						<xsl:with-param name="mode">inbox</xsl:with-param></xsl:apply-templates>}</xsl:template>
	
	<xsl:template match="definecolor"><xsl:param name="contextnode"/>\definecolor{<xsl:value-of select="@color"/>}{<xsl:value-of select="@model"/>}{<xsl:value-of select="@value"/>}</xsl:template>
	
	<xsl:template match="breakbox"><xsl:param name="contextnode"/>
	<xsl:if test="@fboxrule!=''">\setlength{\fboxrule}{<xsl:value-of select="@fboxrule"/>}</xsl:if><xsl:if test="@fboxsep!=''">\setlength{\fboxsep}{<xsl:value-of select="@fboxsep"/>}</xsl:if>
	<xsl:if test="descendant::margref"><xsl:apply-templates select="descendant::margref" mode="breakbox"/></xsl:if>\begin{colbreakbox}{<xsl:choose>
			<xsl:when test="@shading"><xsl:value-of select="@shading"/></xsl:when><xsl:otherwise>1</xsl:otherwise>
		</xsl:choose>}\setlength{\parindent}{0ex}<xsl:apply-templates select="node()">
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>\end{colbreakbox}
	</xsl:template>
	
	
	<xsl:template match="center">
		<xsl:param name="contextnode"/>
		\begin{center}
			<xsl:apply-templates select="node()">
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
		\end{center}
	</xsl:template>
	
	<xsl:template match="flushleft">
		<xsl:param name="contextnode"/>
		\begin{flushleft}
			<xsl:apply-templates select="node()">
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
		\end{flushleft}
	</xsl:template>
	
	<xsl:template match="flushright">
		<xsl:param name="contextnode"/>
		\begin{flushright}
			<xsl:apply-templates select="node()">
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
		\end{flushright}
	</xsl:template>

	<xsl:template match="alltt">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/>
		\begin{alltt}
			<xsl:apply-templates select="node()[name()!='margref']">
				<xsl:with-param name="contextnode" select="$contextnode"/>
				<xsl:with-param name="mode" select="$mode"/>
			</xsl:apply-templates>
		\end{alltt}
	</xsl:template>
	
	<!-- Tabbing -->
	<xsl:template match="tabbing">
		<xsl:param name="contextnode"/>
		\begin{tabbing}
		<xsl:if test="not(tabpositions)">\hspace*{2cm}\=\hspace{2cm}\=\hspace{2cm}\=\hspace{2cm}\=\hspace{2cm}\=\hspace{2cm}\=\hspace{2cm}\=\hspace{2cm}\=\hspace{2cm}\=\hspace{2cm}\kill </xsl:if>
			<xsl:apply-templates select="node()">
				<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates>
		\end{tabbing}
	</xsl:template>
	
	<xsl:template match="tabpositions"><xsl:param name="contextnode"/>
	<xsl:apply-templates select="node()">
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>\kill
	</xsl:template>
	
	<xsl:template match="image"><xsl:param name="contextnode"/>\includegraphics[<xsl:if test="$contextnode/@scale">scale=<xsl:value-of select="$contextnode/@scale"/></xsl:if>]{<xsl:value-of select="$image_path"/>/<xsl:call-template name="get.medianame">
			<xsl:with-param name="mediaref">
				<xsl:apply-templates select="filename">
					<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
			</xsl:with-param>
		</xsl:call-template>.<xsl:value-of select="$printgraphic"/>}</xsl:template>
			
	<xsl:template match="processing-instruction('tab')">\&gt;</xsl:template>
	
	<xsl:template match="processing-instruction('tab')[parent::tabpositions]">\=</xsl:template>

	<!-- Footnotes -->
	<xsl:template match="footnoteline|footnoterule">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>
	 </xsl:template>
	
	<xsl:template match="footnote"><xsl:param name="contextnode"/>\footnote{<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}</xsl:template>
	
	<!-- pagestyle -->
	<xsl:template match="lheadeven|lheadodd|cheadeven|cheadodd|lfooteven|lfootodd|cfooteven|cfootodd|rfooteven|rfootodd">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode" tunnel="yes"/>
		</xsl:apply-templates>
	 </xsl:template>

	
<xsl:template match="rightheading"><xsl:param name="contextnode"/>\markright{<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}</xsl:template>

<xsl:template match="leftheading"><xsl:param name="contextnode"/>\markboth{<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}{}</xsl:template>

<xsl:template match="heading"><xsl:param name="contextnode"/>\markboth{<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}{<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>}</xsl:template>


	
	
	 
	<!-- ==================================================================== -->
	<!-- Processing instructions of the Design Interface -->	
	<!-- ==================================================================== -->
	
	<!-- =========== Course and Course Unit context ================== -->
	
	<!-- =========== Assignment context ================ -->
	<xsl:template match="processing-instruction('selfexercises-solutions')">
		<xsl:param name="contextnode" select="current()"/>
		<xsl:if test="$contextnode/ancestor-or-self::kurseinheit/descendant::selbsttestaufgabe">
			<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/descendant::selbsttestaufgabe" mode="loesung"/>
		</xsl:if>	
	</xsl:template>
	
	<xsl:template match="processing-instruction('exercises-solutions')">
		<xsl:param name="contextnode" select="current()"/>
		<xsl:if test="$contextnode/descendant::uebungsaufgabe">
			<xsl:apply-templates select="$contextnode/descendant::uebungsaufgabe" mode="loesung"/>
		</xsl:if>	
	</xsl:template>

	

	
	<!-- Page layout -->

	<xsl:template match="processing-instruction('marginparwidth')"><xsl:if test=".!=''">\setlength{\marginparwidth}{<xsl:value-of select="."/>}</xsl:if></xsl:template>

	<xsl:template match="processing-instruction('evensidemargin')"><xsl:if test=".!=''">\setlength{\evensidemargin}{<xsl:value-of select="."/>}</xsl:if></xsl:template>
	
	<xsl:template match="processing-instruction('oddsidemargin')"><xsl:if test=".!=''">\setlength{\oddsidemargin}{<xsl:value-of select="."/>}</xsl:if></xsl:template>

	<xsl:template match="processing-instruction('textwidth')"><xsl:if test=".!=''">\setlength{\textwidth}{<xsl:value-of select="."/>}</xsl:if></xsl:template>

	<xsl:template match="processing-instruction('textheight')"><xsl:if test=".!=''">\setlength{\textheight}{<xsl:value-of select="."/>}</xsl:if></xsl:template>

	<xsl:template match="processing-instruction('headheight')"><xsl:if test=".!=''">\setlength{\headheight}{<xsl:value-of select="."/>}</xsl:if></xsl:template>
	
	<xsl:template match="processing-instruction('headwidth')"><xsl:if test=".!=''">\setlength{\headwidth}{<xsl:value-of select="."/>}</xsl:if></xsl:template>

	<xsl:template match="processing-instruction('headsep')"><xsl:if test=".!=''">\setlength{\headsep}{<xsl:value-of select="."/>}</xsl:if></xsl:template>

	<xsl:template match="processing-instruction('footskip')"><xsl:if test=".!=''">\setlength{\footskip}{<xsl:value-of select="."/>}</xsl:if></xsl:template>

	<xsl:template match="processing-instruction('enlargethispage')" mode="#all"><xsl:if test=".!=''">\enlargethispage{<xsl:value-of select="."/>}</xsl:if></xsl:template>
	
	<xsl:template match="processing-instruction('enlargethispage-')" mode="#all"><xsl:if test=".!=''">\enlargethispage*{<xsl:value-of select="."/>}</xsl:if></xsl:template>

	<!-- General context -->
	<xsl:template match="processing-instruction('pageref')" mode="#all"><xsl:param name="contextnode"/>\pageref{<xsl:value-of select="$contextnode/@id"/>}</xsl:template>
	
	<xsl:template match="processing-instruction('hrule')" mode="#all">\hrule </xsl:template>

	<!--xsl:template match="processing-instruction('newline')[ancestor::entry[@name='abschnitt' or @name='abschnitt-nonum']]">\mbox{}\\*<xsl:if test="parent::title/@space-after!=''">[<xsl:value-of select="parent::title/@space-after"/>]</xsl:if></xsl:template-->
	
	<xsl:template match="processing-instruction('newline')" mode="#all">\mbox{}\\*<xsl:if test=".!=''">[<xsl:value-of select="."/>]</xsl:if></xsl:template>
	<!-- führt zu zu großem Abstand wenn eine Liste folgt -->

	<xsl:template match="processing-instruction('par')" mode="#all"><xsl:if test=".!=''">\setlength{\parskip}{<xsl:value-of select="."/>}</xsl:if>\par{}</xsl:template>

	<xsl:template match="processing-instruction('hspace')" mode="#all">\hspace{<xsl:value-of select="."/>}</xsl:template>
	
	<xsl:template match="processing-instruction('hspace-')" mode="#all">\hspace*{<xsl:value-of select="."/>}</xsl:template>
	
	<xsl:template match="processing-instruction('hspaceeven')" mode="#all">\ifthenelse{\isodd{\thepage}}{}{\hspace{<xsl:value-of select="."/>}}</xsl:template>
	<xsl:template match="processing-instruction('hspaceeven-')" mode="#all">\ifthenelse{\isodd{\thepage}}{}{\hspace*{<xsl:value-of select="."/>}}</xsl:template>
	<xsl:template match="processing-instruction('hspaceodd')" mode="#all">\ifthenelse{\isodd{\thepage}}{\hspace{<xsl:value-of select="."/>}}{}</xsl:template>
	<xsl:template match="processing-instruction('hspaceodd-')" mode="#all">\ifthenelse{\isodd{\thepage}}{\hspace*{<xsl:value-of select="."/>}}{}</xsl:template>

	
	<xsl:template match="processing-instruction('vspace')" mode="#all">\vspace{<xsl:value-of select="."/>}</xsl:template>

	<xsl:template match="processing-instruction('vspace-')" mode="#all">\vspace*{<xsl:value-of select="."/>}</xsl:template>

	<xsl:template match="processing-instruction('dotfill')" mode="#all">\dotfill{}</xsl:template>
	
	<xsl:template match="processing-instruction('hfill')" mode="#all">\hfill{}</xsl:template>
	
	<xsl:template match="processing-instruction('hfill-')" mode="#all">\hspace*{\fill}</xsl:template>

	
	<xsl:template match="processing-instruction('hrulefill')" mode="#all">\hrulefill{}</xsl:template>
	
	<xsl:template match="processing-instruction('vfill-')" mode="#all">\vspace*{\fill}</xsl:template>
	
	<xsl:template match="processing-instruction('vfill')" mode="#all">\vfill{}</xsl:template>
	
	<xsl:template match="processing-instruction('qquad')" mode="#all">\qquad{}</xsl:template>
	
	<xsl:template match="processing-instruction('pagebreak')" mode="#all">\pagebreak<xsl:if test=".!=''">[<xsl:value-of select="."/>]</xsl:if>{}</xsl:template>

	
	<xsl:template match="processing-instruction('newpage')" mode="#all">\newpage{}</xsl:template>
	
	<xsl:template match="processing-instruction('clearpage')" mode="#all">\clearpage{}</xsl:template>
	
	<xsl:template match="processing-instruction('cleardoublepage')" mode="#all">\cleardoublepage{}</xsl:template>
	
	<xsl:template match="nbsp">~</xsl:template>

	<xsl:template match="processing-instruction('portrait')" mode="#all">\end{landscape}</xsl:template>
	
	<xsl:template match="processing-instruction('landscape')" mode="#all">\begin{landscape}</xsl:template>
	
	<xsl:template match="processing-instruction('onecolumn')" mode="#all">\onecolumn{}</xsl:template>
	
	<xsl:template match="processing-instruction('twocolumn')" mode="#all">\twocolumn{}</xsl:template>

	<xsl:template match="processing-instruction('centering')" mode="#all">\centering{}</xsl:template>
	
	<xsl:template match="processing-instruction('raggedright')" mode="#all">\raggedright{}</xsl:template>
	
	<xsl:template match="processing-instruction('raggedleft')" mode="#all">\raggedleft{}</xsl:template>
	
	<xsl:template match="processing-instruction('baselinestretch')" mode="#all"><xsl:if test=".!=''">\renewcommand{\baselinestretch}{<xsl:value-of select="."/>}</xsl:if></xsl:template>
	
	<xsl:template match="processing-instruction('font-family')" mode="#all"><xsl:if test=".!=''"><xsl:call-template name="get.font-family"><xsl:with-param name="style" select="."/></xsl:call-template></xsl:if></xsl:template>
	
	<xsl:template match="processing-instruction('font-style')" mode="#all"><xsl:if test=".!=''"><xsl:call-template name="get.font-style"><xsl:with-param name="style" select="."/></xsl:call-template></xsl:if></xsl:template>
	
	<xsl:template match="processing-instruction('font-weight')" mode="#all"><xsl:if test=".!=''"><xsl:call-template name="get.font-weight"><xsl:with-param name="style" select="."/></xsl:call-template></xsl:if></xsl:template>

	<xsl:template match="processing-instruction('font-variant')" mode="#all"><xsl:if test=".!=''"><xsl:call-template name="get.font-variant"><xsl:with-param name="style" select="."/></xsl:call-template></xsl:if></xsl:template>
	
	<xsl:template match="processing-instruction('font-size')" mode="#all"><xsl:if test=".!=''"><xsl:call-template name="get.font-size"><xsl:with-param name="style" select="."/></xsl:call-template></xsl:if></xsl:template>

	<xsl:template match="processing-instruction('line-height')" mode="#all"><xsl:if test=".!=''">\setlength{\baselineskip}{<xsl:value-of select="."/>}</xsl:if></xsl:template>
	
	<xsl:template match="processing-instruction('noindent')" mode="#all">\noindent{}</xsl:template>

	<xsl:template match="processing-instruction('makefnmark')">\@makefnmark{}</xsl:template>
	
	<xsl:template match="processing-instruction('thefnmark')">\@thefnmark{}</xsl:template>
	
	<xsl:template match="processing-instruction('fntext')">#1</xsl:template>
	
	<xsl:template match="processing-instruction('thepage')">\thepage{}</xsl:template>
	
	<xsl:template match="processing-instruction('rightmark')">\rightmark{}</xsl:template>
	
	<xsl:template match="processing-instruction('leftmark')">\leftmark{}</xsl:template>
	
	<xsl:template match="processing-instruction('color')" mode="#all"><xsl:if test=".!=''">\color{<xsl:value-of select="."/>}</xsl:if></xsl:template>
	
	<xsl:template match="processing-instruction('pagecolor')" mode="#all"><xsl:if test=".!=''">\pagecolor{<xsl:value-of select="."/>}</xsl:if></xsl:template>

	<xsl:template match="processing-instruction('pagestyle')"><xsl:if test=".!=''">\pagestyle{<xsl:value-of select="."/>}</xsl:if></xsl:template>
		
	<xsl:template match="processing-instruction('pagenumbering')"><xsl:if test=".!=''">\pagenumbering{<xsl:value-of select="."/>}</xsl:if></xsl:template>
		
	<xsl:template match="processing-instruction('thispagestyle')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<!-- umschaltbares pagelayout im latex /SGE 14.03.2009 -->
		<xsl:if test=".!=''">
			<xsl:variable name="id"><xsl:value-of select="."/></xsl:variable>
			<xsl:choose>
				<xsl:when test="$id='fancy'">
					\thispagestyle{fancy}
				</xsl:when>
				<xsl:when test="$id='emty'">
					\thispagestyle{emty}		
				</xsl:when>
				<xsl:when test="$id='mlea'">
					\thispagestyle{mlea}		
				</xsl:when>
				<xsl:otherwise>
			 		<xsl:apply-templates select="$config/config/pagelayout/pagestyle[@id=$id]" mode="thispagestyle">
			 			<xsl:with-param name="contextnode" select="current()" tunnel="yes"/>
			    	</xsl:apply-templates>			
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>

	<xsl:template match="processing-instruction('nopagebreak')">\nopagebreak{}</xsl:template>
	
	<xsl:template match="processing-instruction('psfrag')">\psfrag<xsl:value-of select="."/></xsl:template>

	<xsl:template match="processing-instruction('npr-teil-start')">\cbstart{}</xsl:template>
	
	<xsl:template match="processing-instruction('npr-teil-end')">\cbend{}</xsl:template>
	

	<!-- We are using {} to create seperation because ~ had the negative side effect of producing unwanted vertical space -->	
	<xsl:template name="get.font-family"><xsl:param name="style"/><xsl:choose>
			<xsl:when test="$style='serif'">\rmfamily{}</xsl:when>
			<xsl:when test="$style='sans-serif'">\sffamily{}</xsl:when>
			<xsl:when test="$style='monospace'">\ttfamily{}</xsl:when></xsl:choose></xsl:template>
	
	<xsl:template name="get.font-style"><xsl:param name="style"/><xsl:choose>
			<xsl:when test="$style='italic'">\itshape{}</xsl:when>
			<xsl:when test="$style='slanted'">\slshape{}</xsl:when>
			<xsl:when test="$style='normal'">\upshape{}</xsl:when></xsl:choose></xsl:template>
	
	<xsl:template name="get.font-variant"><xsl:param name="style"/><xsl:choose>
			<xsl:when test="$style='small-caps'">\scshape{}</xsl:when></xsl:choose></xsl:template>
	
	<xsl:template name="get.font-weight"><xsl:param name="style"/><xsl:choose>
			<xsl:when test="$style='normal'">\mdseries{}</xsl:when>
			<xsl:when test="$style='bold'">\bfseries{}</xsl:when></xsl:choose></xsl:template>
	
	<xsl:template name="get.font-size"><xsl:param name="style"/><xsl:choose>
			<xsl:when test="$style='xxx-small'">\tiny{}</xsl:when>
			<xsl:when test="$style='xx-small'">\scriptsize{}</xsl:when>
			<xsl:when test="$style='x-small'">\footnotesize{}</xsl:when>
			<xsl:when test="$style='small'">\small{}</xsl:when>
			<xsl:when test="$style='medium'">\normalsize{}</xsl:when>
			<xsl:when test="$style='large'">\large{}</xsl:when>
			<xsl:when test="$style='x-large'">\Large{}</xsl:when>
			<xsl:when test="$style='xx-large'">\LARGE{}</xsl:when>
			<xsl:when test="$style='xxx-large'">\huge{}</xsl:when>
			<xsl:when test="$style='xxxx-large'">\Huge{}</xsl:when></xsl:choose></xsl:template>



















<!--xsl:template name="box"><xsl:param name="style"/><xsl:param name="mode"/><xsl:if test="$style/margin-top!=''">\vspace{<xsl:value-of select="$style/margin-top"/>}</xsl:if>\begin{list}{}{	<xsl:call-template name="get.font-family"><xsl:with-param name="style" select="$style/font-family"/></xsl:call-template>
		<xsl:call-template name="get.font-style"><xsl:with-param name="style" select="$style/font-style"/></xsl:call-template>
		<xsl:call-template name="get.font-variant"><xsl:with-param name="style" select="$style/font-variant"/></xsl:call-template>
		<xsl:call-template name="get.font-weight"><xsl:with-param name="style" select="$style/font-weight"/></xsl:call-template>
		<xsl:call-template name="get.font-size"><xsl:with-param name="style" select="$style/font-size"/></xsl:call-template>
		<xsl:if test="$style/margin-left!=''">\setlength{\leftmargin}{<xsl:value-of select="$style/margin-left"/>}</xsl:if>
		<xsl:if test="$style/margin-right!=''">\setlength{\rightmargin}{<xsl:value-of select="$style/margin-right"/>}</xsl:if>
		<xsl:if test="$style/label/@labelwidth!=''">\setlength{\labelwidth}{<xsl:value-of select="$style/label/@labelwidth"/>}</xsl:if>
		<xsl:if test="$style/label/@labelsep!=''">\setlength{\labelsep}{<xsl:value-of select="$style/label/@labelsep"/>}</xsl:if>
		<xsl:if test="$style/label/@itemindent!=''">\setlength{\itemindent}{<xsl:value-of select="$style/label/@itemindent"/>}</xsl:if>\setlength{\topsep}{0pt}\setlength{\partopsep}{0pt}\setlength{\itemsep}{0pt}\setlength{\parsep}{0pt}}<xsl:choose>
			<xsl:when test="$style/text-align='left'">\raggedright</xsl:when>
			<xsl:when test="$style/text-align='right'">\raggedleft</xsl:when>
			<xsl:when test="$style/text-align='center'">\centering</xsl:when>
		</xsl:choose>
			\item[]<xsl:apply-templates select="$style/marg">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
		</xsl:apply-templates><xsl:apply-templates select="node()[name()!='zwischentitel'][name()!='titel']"><xsl:with-param name="mode" select="$mode"/></xsl:apply-templates>
		\end{list}<xsl:if test="$style/margin-bottom!=''">\vspace{<xsl:value-of select="$style/margin-bottom"/>}</xsl:if>
	</xsl:template>
	-->
</xsl:stylesheet>
