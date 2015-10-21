<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<!-- ********************************************************************
		$Id: designinterface-html.xsl,v 1.4 2007/01/23 14:25:07 gebhard Exp $
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
	-->
<xsl:import href="../common/designinterface.xsl"/>

	<!-- ==================================================================== -->
	<!-- Environments-->
	<!-- ==================================================================== -->
	<xsl:template name="environment.selector">
		<xsl:param name="style"/>
		<xsl:param name="mode"/>
		<xsl:param name="Pfad"/>
		<xsl:choose>
			<xsl:when test="$style/envtype='labelbox' or $style/envtype='breakbox'">
				<xsl:call-template name="labelbox">
					<xsl:with-param name="style" select="$style"/>
					<xsl:with-param name="Pfad" select="$Pfad"/>
					<xsl:with-param name="mode" select="$mode"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$style/envtype='box'">
				<xsl:call-template name="box">
					<xsl:with-param name="style" select="$style"/>
					<xsl:with-param name="Pfad" select="$Pfad"/>
					<xsl:with-param name="mode" select="$mode"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$style/envtype='nobox'">
				<xsl:call-template name="nobox">
					<xsl:with-param name="style" select="$style"/>
					<xsl:with-param name="Pfad" select="$Pfad"/>
					<xsl:with-param name="mode" select="$mode"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$style/envtype='genericbox'">
				<xsl:call-template name="genericbox">
					<xsl:with-param name="style" select="$style"/>
					<xsl:with-param name="Pfad" select="$Pfad"/>
					<xsl:with-param name="mode" select="$mode"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$style/envtype='assignment'">
				<xsl:call-template name="assignment">
					<xsl:with-param name="style" select="$style"/>
					<xsl:with-param name="mode" select="$mode"/>
					<xsl:with-param name="Pfad" select="$Pfad"/>
				</xsl:call-template>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="structure">
		<xsl:param name="Pfad"/>
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/>
		<xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="mode" select="$mode"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>			
		</xsl:apply-templates>
	</xsl:template>

<!-- ==================================================================== -->
	<xsl:template name="labelbox">
		<xsl:param name="Pfad"/>
		<xsl:param name="style"/>
		<xsl:param name="mode"/>
		<xsl:param name="contextnode"/>
	<div class="{name(current())}">
		<xsl:call-template name="standardattribute"/>
		<xsl:apply-templates select="$style/label">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>
		<xsl:apply-templates select="node()[name()!='zwischentitel'][name()!='titel']">
			<xsl:with-param name="mode" select="$mode"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>
	</div>		
	</xsl:template>
	
	<xsl:template match="label">
		<xsl:param name="contextnode"/>
		<xsl:param name="Pfad"/>
		<xsl:param name="style"/>
		<xsl:param name="marg"/> <!-- feritg angelieferte Marginalie für Aufgaben-->
		<xsl:variable name="class">
			<xsl:choose>
				<xsl:when test="$contextnode/self::einsendeaufgabe">
					<xsl:text>eatitel</xsl:text>		
				</xsl:when>
				<xsl:when test="$contextnode/self::unteraufgabe">
					<xsl:text>uazwischentitel</xsl:text>		
				</xsl:when>				
				<xsl:otherwise>
					<xsl:value-of select="name($contextnode/(titel|zwischentitel))"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="string-length($class) &gt; 0">
				<p class="{$class}">
					<xsl:apply-templates select="node()">
						<xsl:with-param name="contextnode" select="$contextnode"/>
						<xsl:with-param name="Pfad" select="$Pfad"/>
					</xsl:apply-templates>
					<!-- Marginalienabfrage-->
					<xsl:choose>
						<xsl:when test="$contextnode/self::einsendeaufgabe">
							<span class="eamarginalie"><xsl:value-of select="$marg"/></span>									</xsl:when>
						<xsl:otherwise>
						<!-- Marginalienabfrage: Bei allen Einsendeaufgaben-->											<xsl:if test="string-length($marg) &gt; 0">
								<span><xsl:value-of select="$marg"/></span>
							</xsl:if>
						</xsl:otherwise>
					</xsl:choose>
				</p>
			</xsl:when>
			<xsl:otherwise>	<!-- Erzeugung für alle Elemente, ohne titel oder Zwischentitel -->
				<xsl:choose>
					<xsl:when test="name($contextnode)='selbsttestaufgabe' or name($contextnode)='uebungsaufgabe'">
						<p class="zwischentitel">	<!-- für die Erzeugung der richtigen Aufgabensyntax -->
							<xsl:apply-templates select="node()">
								<xsl:with-param name="contextnode" select="$contextnode"/>
								<xsl:with-param name="Pfad" select="$Pfad"/>
							</xsl:apply-templates>
						<!-- Marginalienabfrage: Bei allen Selbst-und Uebungsaufgaben-->
							<xsl:if test="string-length($marg) &gt; 0">
								<span><xsl:value-of select="$marg"/></span>
							</xsl:if>
						</p>
					</xsl:when>					
					<xsl:otherwise>
							<xsl:apply-templates select="node()">
								<xsl:with-param name="contextnode" select="$contextnode"/>
								<xsl:with-param name="Pfad" select="$Pfad"/>
							</xsl:apply-templates>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>	


	<!-- ==================================================================== -->
	<xsl:template name="genericbox">
		<xsl:param name="style"/>
		<xsl:param name="mode"/>
		<xsl:param name="Pfad"/>
			<xsl:apply-templates select="$style/marg">
				<xsl:with-param name="contextnode" select="current()"/>
				<xsl:with-param name="style" select="$style"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
			</xsl:apply-templates>
		<xsl:apply-templates select="$style/structure">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>

	<xsl:template name="box">
		<xsl:param name="style"/>
		<xsl:param name="mode"/>
		<xsl:param name="Pfad"/>
		<xsl:apply-templates select="$style/marg">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>
		<xsl:apply-templates select="node()[name()!='zwischentitel'][name()!='titel']">
			<xsl:with-param name="mode" select="$mode"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>
	</xsl:template>
	<!-- ==================================================================== -->
	<xsl:template name="nobox">
		<xsl:param name="style"/>
		<xsl:param name="Pfad"/>
		<xsl:param name="mode"/>
		<xsl:apply-templates>
			<xsl:with-param name="style" select="$style"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>
	<!-- ==================================================================== -->
	<xsl:template name="list">
		<xsl:param name="Pfad"/>
		<xsl:param name="style"/>
		<xsl:param name="mode"/>
		<xsl:apply-templates>
			<xsl:with-param name="style" select="$style"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>
	</xsl:template>
	<!-- ==================================================================== -->

	<xsl:template name="assignment">
		<xsl:param name="style"/>
		<xsl:param name="mode"/>
		<xsl:param name="Pfad"/>
		<xsl:variable name="marg">
		<xsl:apply-templates select="$style/marg">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
		</xsl:variable>
		<xsl:apply-templates select="$style/label">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="marg" select="$marg"/>
			<xsl:with-param name="mode" select="$mode"/>			
		</xsl:apply-templates>
		
		<xsl:apply-templates select="$style/structure">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="style" select="$style"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>

	</xsl:template>

	
	<!-- ==================================================================== -->
	
	
	<!-- ==================================================================== -->
	<!-- Elements of the Design Interface -->
	<!-- ==================================================================== -->
	

<!--	<xsl:template match="file">
		<xsl:param name="contextnode"/>
		<xsl:variable name="filename">
			<xsl:apply-templates select="filename">
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
		</xsl:variable>
		<xsl:message><xsl:value-of select="$filename"/></xsl:message>
		<xsl:result-document href="{$filename}.html">
			<html>
			<xsl:call-template name="htmlheader"/>
			<body>
				<xsl:apply-templates select="content">
					<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
			</body>
			</html>
		</xsl:result-document>
	</xsl:template> -->
	<!-- ==================================================================== -->

	<xsl:template match="marg">
		<xsl:param name="contextnode"/>
		<xsl:param name="style"/>
		<xsl:choose>
			<xsl:when test="$contextnode/ancestor-or-self::einsendeaufgabe">
				<span class="eamarginalie">
				<xsl:apply-templates select="node()">
					<xsl:with-param name="contextnode" select="$contextnode"/>
					<xsl:with-param name="style" select="$style"/>
				</xsl:apply-templates>
				</span>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="node()">
					<xsl:with-param name="contextnode" select="$contextnode"/>
					<xsl:with-param name="style" select="$style"/>
				</xsl:apply-templates>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- ==================================================================== -->

	<xsl:template name="newline">
		<xsl:param name="style"/>
	</xsl:template>
	<!-- ==================================================================== -->

<!--	<xsl:template match="newpage">
	</xsl:template> -->

	<!-- ==================================================================== -->
	
	<xsl:template match="box">
		<xsl:param name="contextnode"/>
		<xsl:value-of select="@width"/>
		<xsl:apply-templates>
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates>
	</xsl:template>

	<!-- ==================================================================== -->
	<!-- Processing instructions of the Design Interface -->	
	<!-- ==================================================================== -->
	
	<!-- =========== Course and Course Unit context ================== -->


	<!-- =========== Assignment context ================ -->
	
	<!-- Aufgabenstellung -->
	<xsl:template match="processing-instruction('task')">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/>
		<xsl:param name="Pfad"/>
		<xsl:choose>
			<xsl:when test="$contextnode/ancestor-or-self::einsendeaufgabe">
				<xsl:apply-templates select="$contextnode/aufgabenstellung" mode="normal">
					<xsl:with-param name="mode" select="$mode"/>
					<xsl:with-param name="Pfad" select="$Pfad"/>
					<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
			</xsl:when>
			<xsl:otherwise>
				<div class="aufgabenstellung">
				<xsl:apply-templates select="$contextnode/aufgabenstellung" mode="normal">
					<xsl:with-param name="mode" select="$mode"/>
					<xsl:with-param name="Pfad" select="$Pfad"/>
					<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
				</div>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- Loesungshinweise -->
	<xsl:template match="processing-instruction('hints')">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/>
		<xsl:param name="Pfad"/>
		<xsl:if test="$contextnode/loesungshinweis">
			<xsl:apply-templates select="$contextnode/loesungshinweis" mode="normal">
				<xsl:with-param name="mode" select="$mode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
		</xsl:if>
	</xsl:template>
	
	<!-- Aufgabenloesung -->
	<xsl:template match="processing-instruction('solution')">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/>
		<xsl:param name="Pfad"/>
		<xsl:if test="$contextnode/aufgabenloesung">
			<xsl:apply-templates select="$contextnode/aufgabenloesung" mode="normal">
				<xsl:with-param name="mode" select="$mode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
		</xsl:if>
	</xsl:template>
	
	
	<!-- Korrekturhinweise -->
	<xsl:template match="processing-instruction('corr-instructions')">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/>
		<xsl:param name="Pfad"/>
		<xsl:if test="$contextnode/korrekturhinweis">
			<xsl:apply-templates select="$contextnode/korrekturhinweis" mode="normal">
				<xsl:with-param name="mode" select="$mode"/>
				<xsl:with-param name="Pfad" select="$Pfad"/>
				<xsl:with-param name="contextnode" select="$contextnode"/>
			</xsl:apply-templates>
		</xsl:if>
	</xsl:template>
	
	<!-- Unteraufgaben -->
	<xsl:template match="processing-instruction('subtasks')">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/>
		<xsl:param name="Pfad"/>
		<xsl:if test="$contextnode/unteraufgabe">
			<xsl:choose>
				<xsl:when test="$contextnode/ancestor-or-self::einsendeaufgabe">
					<xsl:apply-templates select="$contextnode/unteraufgabe" mode="ea">
						<xsl:with-param name="mode" select="$mode"/>
						<xsl:with-param name="Pfad" select="$Pfad"/>
						<xsl:with-param name="contextnode" select="$contextnode"/>
					</xsl:apply-templates>
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates select="$contextnode/unteraufgabe" mode="normal">
						<xsl:with-param name="mode" select="$mode"/>
						<xsl:with-param name="Pfad" select="$Pfad"/>
						<xsl:with-param name="contextnode" select="$contextnode"/>
					</xsl:apply-templates>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
	
	<!-- =========== Titlepage context ================== -->
	<xsl:template match="processing-instruction('feulogo')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/feulogo"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('varlogo1')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/varlogo1"/>
	</xsl:template>

	<xsl:template match="processing-instruction('kurstitel')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/kurstitel"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('ke-bezeich')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ke-bezeich"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('ke-titel')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ke-titel"/>
	</xsl:template>

	<xsl:template match="processing-instruction('a-bezeich')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/a-bezeich"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('autoren')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/autoren"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('m-bezeich')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/m-bezeich"/>
	</xsl:template>

	<xsl:template match="processing-instruction('mitarbeiter')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/mitarbeiter"/>
	</xsl:template>

	<xsl:template match="processing-instruction('g-bezeich')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/g-bezeich"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('gestalter')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/gestalter"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('titelbild')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/titelbild"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('varlogo2')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/varlogo2"/>
	</xsl:template>

	<xsl:template match="processing-instruction('copyright')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/copyright"/>
	</xsl:template>

	<xsl:template match="processing-instruction('kursnummer')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/kursnummer"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('codierung')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/codierung"/>
	</xsl:template>

	<xsl:template match="processing-instruction('cunum')"><xsl:param name="contextnode"/>
		<xsl:value-of select="$contextnode/ancestor-or-self::kurseinheit/@number"/>
	</xsl:template>

	<xsl:template match="processing-instruction('picto')">
	</xsl:template>
	
	<xsl:template match="processing-instruction('level')">
		<xsl:param name="contextnode"/>
		<xsl:value-of select="normalize-space(($contextnode/@level)[1])"/>
	</xsl:template>

	<xsl:template match="processing-instruction('num')">
		<xsl:param name="contextnode"/>
		<xsl:param name="element-lenght"/>
		<xsl:choose>
			<xsl:when test="$element-lenght">
				<xsl:value-of select="substring(normalize-space(($contextnode/@number)[1]),0,number($element-lenght))"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="normalize-space(($contextnode/@number)[1])"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="processing-instruction('title')">
		<xsl:param name="contextnode"/>
		<xsl:param name="element-lenght"/>
		<xsl:param name="overlenght-symbol"/>
		<xsl:choose>
			<xsl:when test="$element-lenght and number($element-lenght) lt string-length($contextnode/titel/node()[not(self::untertitel)]|$contextnode/zwischentitel|$contextnode/objekttitel)">
				<xsl:value-of select="concat(substring(normalize-space($contextnode/titel/node()[not(self::untertitel)]|$contextnode/zwischentitel|$contextnode/objekttitel),0,number($element-lenght)),$overlenght-symbol)"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="$contextnode/titel/node()[not(self::untertitel)]|$contextnode/zwischentitel|$contextnode/objekttitel"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="get.htmlpath">
		<xsl:param name="contextnode"/>
		<xsl:param name="path">
			<xsl:call-template name="get.mediapath">
				<xsl:with-param name="mediaref" select="$contextnode/@sourcefile"/>
			</xsl:call-template>
		</xsl:param>
		<xsl:variable name="mediadir"><xsl:call-template name="get.mediadir"><xsl:with-param name="mediaref" select="$contextnode/@fileref"/></xsl:call-template></xsl:variable>
		<xsl:variable name="mediapath"><xsl:call-template name="get.mediapath"><xsl:with-param name="mediaref" select="$contextnode/@fileref"/></xsl:call-template></xsl:variable>
		<xsl:variable name="mediasuffix"><xsl:call-template name="get.mediasuffix"><xsl:with-param name="mediaref" select="$contextnode/@fileref"/></xsl:call-template></xsl:variable>
		<xsl:value-of select="$path"/>/<xsl:value-of select="$mediapath"/>
	</xsl:template>
	
	<!-- ==================================================================== -->
	<!--                                                      for compatibility betwen latexpdf and HTML                      -->	 
	<!-- ==================================================================== -->
	<xsl:template match="entrystructure|listdescription|mbox|fbox|makebox|parbox|minipage|raisebox|framebox|shabox|rule|breakbox|definecolor|numpart">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>
	</xsl:template>

</xsl:stylesheet>