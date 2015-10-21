<?xml version="1.0" encoding="UTF-8"?>
<!-- ********************************************************************
	$Id: resultxml.xsl,v 1.3 2007/01/11 13:37:18 gebhard Exp $
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
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ssi="http://www.fernuni-hagen.de/fuxml/ssi">
	<xsl:import href="../common/params.xsl"/>
	<xsl:variable name="config" select="document('../common/config.xml')"/>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="no"/>
	<xsl:strip-space elements="*"/>

	<xsl:template match="document">
		<documents>
		<xsl:variable name="style" select="$styles/entry[@name=name(current())]"/>
		<xsl:apply-templates select="$style/structure"><xsl:with-param name="contextnode" select="current()"/></xsl:apply-templates>
		</documents>
	</xsl:template>
	
	<xsl:template match="*"/>
	
	<xsl:template match="kurs">
		<xsl:variable name="style" select="$styles/entry[@name=name(current())]"/>
		<xsl:apply-templates select="$style/structure"><xsl:with-param name="contextnode" select="current()"/></xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="kurseinheit">
		<xsl:variable name="style" select="if ($styles/entry[@name=name(current())][@number=current()/@number]) 
											then ($styles/entry[@name=name(current())][@number=current()/@number]) 
										  	else ($styles/entry[@name=name(current())][not(@number)]) 
										"/>
		<xsl:apply-templates select="$style/structure"><xsl:with-param name="contextnode" select="current()"/></xsl:apply-templates>
	</xsl:template>
	
	<!--xsl:template match="einsendeaufgaben">
		<xsl:variable name="style" select="$styles/entry[@name=name(current())]"/>
		<xsl:apply-templates select="$style/structure"><xsl:with-param name="contextnode" select="current()"/></xsl:apply-templates>
	</xsl:template-->
	


	<xsl:template match="einsendeaufgaben">
		<xsl:variable name="style" select="if ($styles/entry[@name=name(current())][@number=current()/ancestor::kurseinheit/@number]) 
											then ($styles/entry[@name=name(current())][@number=current()/ancestor::kurseinheit/@number]) 
										  	else ($styles/entry[@name=name(current())][not(@number)]) 
"/>
		<xsl:apply-templates select="$style/structure"><xsl:with-param name="contextnode" select="current()"/></xsl:apply-templates>
	</xsl:template>



	
	<xsl:template match="einsendeaufgaben" mode="task">
		<xsl:variable name="style" select="$styles/entry[@name=name(current())][@variant='task']"/>
		<xsl:apply-templates select="$style/structure"><xsl:with-param name="contextnode" select="current()"/></xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="einsendeaufgaben" mode="solution">
		<xsl:variable name="style" select="$styles/entry[@name=name(current())][@variant='solution']"/>
		<xsl:apply-templates select="$style/structure"><xsl:with-param name="contextnode" select="current()"/></xsl:apply-templates>
	</xsl:template>

	<xsl:template match="einsendeaufgaben" mode="correction">
		<xsl:variable name="style" select="$styles/entry[@name=name(current())][@variant='correction']"/>
		<xsl:apply-templates select="$style/structure"><xsl:with-param name="contextnode" select="current()"/></xsl:apply-templates>
	</xsl:template>

	<xsl:template match="einsendeaufgabe">
		<xsl:param name="mode"/>
		<xsl:variable name="style" select="$styles/entry[@name=name(current())][@variant=$mode]"/>
		<xsl:apply-templates select="$style/structure"><xsl:with-param name="contextnode" select="current()"/></xsl:apply-templates>
	</xsl:template>
	
	


	<xsl:template match="file">
		<xsl:param name="contextnode"/>
		<document>
			<xsl:copy-of select="@archive"/>
			<xsl:if test="ancestor::entry/indexstructure">
				<xsl:attribute name="indexfiles">
					<xsl:apply-templates select="ancestor::entry/indexstructure">
						<xsl:with-param name="filename">
							<xsl:apply-templates select="filename">
								<xsl:with-param name="contextnode" select="$contextnode"/>
							</xsl:apply-templates>
						</xsl:with-param>
					</xsl:apply-templates>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="ancestor::entry/tocstructure">
				<xsl:attribute name="tocfiles">
					<xsl:apply-templates select="ancestor::entry/tocstructure">
						<xsl:with-param name="filename">
							<xsl:apply-templates select="filename">
								<xsl:with-param name="contextnode" select="$contextnode"/>
							</xsl:apply-templates>
						</xsl:with-param>
					</xsl:apply-templates>
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="@precedingfile">
				<xsl:copy-of select="@precedingfile"/>
			</xsl:if>
			<xsl:apply-templates select="*">
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
		</document>
	</xsl:template>
	
	<xsl:template match="filename"> 
		<xsl:param name="contextnode"/>
		<filename><xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates></filename>
	</xsl:template>
	
	<xsl:template match="directory"> 
		<xsl:param name="contextnode"/>
		<directory><xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates></directory>
	</xsl:template>

	
	<xsl:template match="description">
		<xsl:param name="contextnode"/>
		<description><xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates></description>
	</xsl:template>
	
	<xsl:template match="frontmatter|mainmatter">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="content">
		<xsl:param name="contextnode"/>
		<content><xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates></content>
	</xsl:template>
	
	<xsl:template match="structure[@format='latexpdf']">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="structure[@format='html']"/>

	<!-- Index -->
	<xsl:template match="indexstructure">
		<xsl:param name="filename"/>
		<xsl:apply-templates><xsl:with-param name="filename" select="$filename"/></xsl:apply-templates>
	</xsl:template>
	<xsl:template match="currentindex">
		<xsl:param name="filename"/>
		<xsl:value-of select="concat(normalize-space($filename),'.idx')"/><xsl:if test="position()!=last()">,</xsl:if>		
	</xsl:template>
	
	<xsl:template match="includeindex">
		<xsl:value-of select="concat(.,'.idx')"/><xsl:if test="position()!=last()">,</xsl:if>
	</xsl:template>
	
	<!-- toc -->
	<xsl:template match="tocstructure">
		<xsl:param name="filename"/>
		<xsl:apply-templates><xsl:with-param name="filename" select="$filename"/></xsl:apply-templates>
	</xsl:template>
	<xsl:template match="currenttoc">
		<xsl:param name="filename"/>
		<xsl:value-of select="concat(normalize-space($filename),'.toc')"/><xsl:if test="following-sibling::tocstructure">,</xsl:if>		
	</xsl:template>
	
	<xsl:template match="includetoc">
		<xsl:value-of select="concat(.,'.toc')"/><xsl:if test="position()!=last()">,</xsl:if>
	</xsl:template>

	<!-- -->
	
	<xsl:template match="processing-instruction('cu')">
		<xsl:param name="contextnode"/>
			<xsl:apply-templates select="$contextnode/descendant-or-self::kurseinheit[@number=current()/.]"/>
	</xsl:template>

	<xsl:template match="processing-instruction('cu-content')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ke-lehrtext"/>
	</xsl:template>

	<xsl:template match="processing-instruction('content')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/node()"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('cunum')">
		<xsl:param name="contextnode"/>
		<xsl:value-of select="$contextnode/ancestor-or-self::kurseinheit/@number"/>
	</xsl:template>

	<xsl:template match="processing-instruction('documentname')">
		<xsl:value-of select="$documentname"/>
	</xsl:template>

	<xsl:template match="processing-instruction('coursenum')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor::kurs/metadata/kurs-nr"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('assignments')">
			<xsl:param name="contextnode"/>
			<xsl:apply-templates select="$contextnode/descendant-or-self::einsendeaufgaben"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('assignments-course')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurs/fs-elemente/einsendeaufgaben"/>
	</xsl:template>

	
	<xsl:template match="processing-instruction('assignment')">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/><!-- for assignments -->
		<xsl:apply-templates select="$contextnode/einsendeaufgabe">
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="processing-instruction('assignment-tasks')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/descendant-or-self::einsendeaufgaben" mode="task"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('assignment-solutions')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/descendant-or-self::einsendeaufgaben" mode="solution"/>
	</xsl:template>

	<xsl:template match="processing-instruction('assignment-corrections')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/descendant-or-self::einsendeaufgaben" mode="correction"/>
	</xsl:template>

	
	<!--xsl:template match="text()|@*"></xsl:template>
	<xsl:template match="text()|@*" mode="call"><xsl:value-of select="."/></xsl:template-->


</xsl:stylesheet>
