<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
								xmlns:saxon="http://saxon.sf.net/" 
								xmlns:xs="http://www.w3.org/2001/XMLSchema" 
								xmlns:math="http://exslt.org/math"
         						extension-element-prefixes="math">
<!--
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
<xsl:include href="params.xsl"/>	
<xsl:output indent="no" />	
<xsl:strip-space elements="*"/>
	<!-- ignore headings for other then Print -->
	<xsl:template match="rightheading|leftheading|heading"/>
	
	<!-- ==================================================================== -->
	<!-- Format-independent Environments-->
	<!-- ==================================================================== -->
	
	<xsl:template name="container">
		<xsl:param name="Pfad"/>
		<xsl:param name="style"/>
		<xsl:param name="mode"/><!-- for assignments -->
		<xsl:apply-templates select="$style/structure">
			<xsl:with-param name="contextnode" select="current()"/>
			<xsl:with-param name="mode" select="$mode"/>
			<xsl:with-param name="Pfad" select="$Pfad"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<!-- ==================================================================== -->
	<!-- Format-independent Elements of the Design Interface -->
	<!-- ==================================================================== -->
	
	<xsl:template match="file">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="content">
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="filename|description|content">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/>
		<xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<!-- ==================================================================== -->
	<xsl:template match="structure">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/>
		<xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>
	<!-- ==================================================================== -->
	<xsl:template match="text-before">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/>
		<xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>
	<!-- ==================================================================== -->
	<xsl:template match="text-after">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/>
		<xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode"/>
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>
	<!-- ==================================================================== -->
	<xsl:template match="label"><xsl:param name="contextnode"/>
		<xsl:param name="style"/>
		<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates></xsl:template>	
	
	<!-- ==================================================================== -->
	<!-- Element numbering                                                                                                           -->
	<!-- ==================================================================== -->
	
	<!-- This template is called from numbering.xsl -->
	<xsl:template match="frontmatterformat|mainmatterformat">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates>
	</xsl:template>	

	
	<xsl:template match="numberformat|elementnumberformat">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates>
	</xsl:template>	
<!-- ==================================================================== -->
	<!--xsl:template match="num-sectionlevel">
		<xsl:param name="contextnode"/>
		<xsl:variable name="num-sectionlevel"><xsl:value-of select="."/></xsl:variable>
		<xsl:choose>
			<xsl:when test="$num-sectionlevel gt 0"><xsl:value-of select="$contextnode/preceding::abschnitt[@level le $num-sectionlevel][1]/@number"/>
		</xsl:when>
		<xsl:when test="$num-sectionlevel='cu'"/>
		</xsl:choose></xsl:template-->
	
	<xsl:template match="num-sectionpart"><xsl:param name="contextnode"/>
		<xsl:variable name="num-sectionlevel"><xsl:value-of select="(ancestor::frontmatterformat|ancestor::mainmatterformat)/@num-sectionlevel"/></xsl:variable>
		<xsl:choose>
			<xsl:when test="$num-sectionlevel='cu'"/>
			<xsl:when test="xs:integer($num-sectionlevel) gt 0"><xsl:value-of select="normalize-space($contextnode/preceding::abschnitt[@level le $num-sectionlevel][titel[@num='an']][1]/@number)"/>
		</xsl:when>
		</xsl:choose></xsl:template>

	<!-- ==================================================================== -->
	<xsl:template match="num-posstyle|num-pospart"><xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode" mode="numbering"/></xsl:template>
	<!-- ==================================================================== -->
	<xsl:template match="num-arraystyle|num-arraypart">	<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode" mode="numberingarray"/>	</xsl:template>
	


	<!-- ==================================================================== -->
	<!-- ==================================================================== -->
	<xsl:template match="xrefformat">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates>
	</xsl:template>
	<!-- ==================================================================== -->
	<xsl:template name="nbsp">&#x00a0;</xsl:template>
	<!-- ==================================================================== -->
	<xsl:template match="mainmatter|frontmatter|backmatter">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates>
	</xsl:template>
	<!-- ==================================================================== -->
	<!-- Format-independent Processing instructions of the Design Interface -->	
	<!-- ==================================================================== -->
	
	<!-- =========== Course and Course Unit context ================== -->

	
	
	<xsl:template match="processing-instruction('preface')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/fs-elemente/vorwort"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('preface-course')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor::kurs/fs-elemente/vorwort"/>
	</xsl:template>

	
	<xsl:template match="processing-instruction('prerequisites')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/fs-elemente/voraussetzungen"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('prerequisites-course')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor::kurs/fs-elemente/voraussetzungen"/>
	</xsl:template>

	
	<xsl:template match="processing-instruction('studyremarks')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/fs-elemente/studierhinweise"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('studyremarks-course')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor::kurs/fs-elemente/studierhinweise"/>
	</xsl:template>

	
	<xsl:template match="processing-instruction('authorintroduction')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/fs-elemente/autorenvorstellung"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('authorintroduction-course')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor::kurs/fs-elemente/autorenvorstellung"/>
	</xsl:template>

	
	<xsl:template match="processing-instruction('learningobjectives')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/fs-elemente/lehrziele"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('learningobjectives-course')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor::kurs/fs-elemente/lehrziele"/>
	</xsl:template>


	<xsl:template match="processing-instruction('bibliography')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/fs-elemente/(literaturverz|literaturliste)"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('bibliography-course')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor::kurs/fs-elemente/(literaturverz|literaturliste)"/>
	</xsl:template>

	
	<xsl:template match="processing-instruction('appendix')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/fs-elemente/anhang"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('appendix-course')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor::kurs/fs-elemente/anhang"/>
	</xsl:template>

	
	<xsl:template match="processing-instruction('glossary')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/fs-elemente/glossar"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('glossary-course')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor::kurs/fs-elemente/glossar"/>
	</xsl:template>

	
	<xsl:template match="processing-instruction('index')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/fs-elemente/index"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('index-course')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor::kurs/fs-elemente/index"/>
	</xsl:template>


	<xsl:template match="processing-instruction('titlepage')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/titelseite"><xsl:with-param name="contextnode" select="$contextnode" tunnel="yes"></xsl:with-param></xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="processing-instruction('cu')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/descendant-or-self::kurseinheit[@number=current()/.]"/>
	</xsl:template>

	<xsl:template match="processing-instruction('cu-content')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ke-lehrtext"/>
	</xsl:template>

	<!-- =========== Titlepage context ================== -->
	<xsl:template match="processing-instruction('feulogo')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/feulogo"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('varlogo1')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/varlogo1"/>
	</xsl:template>

	<xsl:template match="processing-instruction('varlogo2')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/varlogo2"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('varlogo3')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/varlogo3"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('varlogo4')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/varlogo4"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('varlogo5')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/varlogo5"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('varlogo6')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/varlogo6"/>
	</xsl:template>

	<xsl:template match="processing-instruction('kurstitel')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/kurstitel"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('ke-bezeich')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/ke-bezeich"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('ke-titel')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/ke-titel"/>
	</xsl:template>

	<xsl:template match="processing-instruction('a-bezeich')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/a-bezeich"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('autoren')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/autoren"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('m-bezeich')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/m-bezeich"/>
	</xsl:template>

	<xsl:template match="processing-instruction('mitarbeiter')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/mitarbeiter"/>
	</xsl:template>

	<xsl:template match="processing-instruction('g-bezeich')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/g-bezeich"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('gestalter')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/gestalter"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('titelbild')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/titelbild"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('varlogo2')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/varlogo2"/>
	</xsl:template>

	<xsl:template match="processing-instruction('copyright')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/copyright"/>
	</xsl:template>

	<xsl:template match="processing-instruction('kursnummer')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/kursnummer"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('codierung')">
		<xsl:param name="contextnode" tunnel="yes"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurseinheit/titelseite/codierung"/>
	</xsl:template>


	<!-- =========== Assignment context ================ -->
	
	
			
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

	<!-- Aufgabenstellung -->
	<xsl:template match="processing-instruction('task')"><xsl:param name="contextnode"/>
		<xsl:param name="mode"/><xsl:apply-templates select="$contextnode/aufgabenstellung">
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates></xsl:template>
	
	<!-- Loesungshinweise -->
	<xsl:template match="processing-instruction('hints')">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/>
		<xsl:if test="$contextnode/loesungshinweis">
			<xsl:apply-templates select="$contextnode/loesungshinweis">
				<xsl:with-param name="mode" select="$mode"/>
			</xsl:apply-templates>
		</xsl:if>
	</xsl:template>
	
	<!-- Aufgabenloesung -->
	<xsl:template match="processing-instruction('solution')">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/>
		<xsl:if test="$contextnode/aufgabenloesung">
			<xsl:apply-templates select="$contextnode/aufgabenloesung">
				<xsl:with-param name="mode" select="$mode"/>
			</xsl:apply-templates>
		</xsl:if>
	</xsl:template>
	
	
	<!-- Korrekturhinweise -->
	<xsl:template match="processing-instruction('corr-instructions')">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/>
		<xsl:if test="$contextnode/korrekturhinweis">
			<xsl:apply-templates select="$contextnode/korrekturhinweis">
				<xsl:with-param name="mode" select="$mode"/>
			</xsl:apply-templates>
		</xsl:if>
	</xsl:template>
	
	<!-- Unteraufgaben -->
	<xsl:template match="processing-instruction('subtasks')">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/>
		<xsl:if test="$contextnode/unteraufgabe">
			<xsl:apply-templates select="$contextnode/unteraufgabe">
				<xsl:with-param name="mode" select="$mode"/>
			</xsl:apply-templates>
		</xsl:if>
	</xsl:template>
	
	
	
	
	<!-- ================= List ================================-->
	<xsl:template match="entrystructure">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>
	</xsl:template>

	
	<xsl:template match="listlabel">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="listdescription">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="item">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="node()"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>
	</xsl:template>

	
	<!-- Returns text of liteintrag without litkennung -->
	<xsl:template match="processing-instruction('littext')">
				<xsl:param name="contextnode"/>
				<xsl:apply-templates select="$contextnode/node()[not(self::litkennung)]"></xsl:apply-templates>
	</xsl:template>
	
	<!-- Returns value of litkennung -->
	<xsl:template match="processing-instruction('litidentifier')">
			<xsl:param name="contextnode"/>
			<xsl:apply-templates select="$contextnode/litkennung"></xsl:apply-templates>
	</xsl:template>
	
	<xsl:template match="processing-instruction('litnum')">
			<xsl:param name="contextnode"/>
			<xsl:value-of select="$contextnode/self::liteintrag/@number"/>
	</xsl:template>

	
	<!-- Returns value of entry/label element-->
	<xsl:template match="processing-instruction('label')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="ancestor::entry/label">
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<!-- Returns value of entry/item element -->
	<xsl:template match="processing-instruction('itemref')[.!='']">
		<xsl:param name="contextnode"/>
		<xsl:choose>
			<xsl:when test="$contextnode/stichwort[@xml:lang=current()/.]">
				<xsl:apply-templates select="ancestor::entry/item[@xml:lang=current()/.]">
					<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="processing-instruction('itemref')[.='']">
		<xsl:param name="contextnode"/>
		<xsl:choose>
			<xsl:when test="$contextnode/stichwort[not(@xml:lang)]">
				<xsl:apply-templates select="ancestor::entry/item[not(@xml:lang)]">
					<xsl:with-param name="contextnode" select="$contextnode"/>
				</xsl:apply-templates>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	
	<!-- Returns the erlaeuterung element of the specified language -->
	<xsl:template match="processing-instruction('description')">
		<xsl:param name="contextnode"/>
		<xsl:choose>
			<xsl:when test=".='fr'"><xsl:apply-templates select="$contextnode/erlaeuterung[@xml:lang='fr']"/></xsl:when>
			<xsl:when test=".='en'"><xsl:apply-templates select="$contextnode/erlaeuterung[@xml:lang='en']"/></xsl:when>
			<xsl:otherwise><xsl:apply-templates select="$contextnode/erlaeuterung[1]"/></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- Returns the stichwort element of the specified language -->
	<xsl:template match="processing-instruction('keyword')">
		<xsl:param name="contextnode"/>
		<xsl:choose>
			<xsl:when test=".='fr'"><xsl:apply-templates select="$contextnode/stichwort[@xml:lang='fr']"/></xsl:when>
			<xsl:when test=".='en'"><xsl:apply-templates select="$contextnode/stichwort[@xml:lang='en']"/></xsl:when>
			<xsl:otherwise><xsl:apply-templates select="$contextnode/stichwort[1]"/></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	
	<!-- =================== General context ==========================-->
	
	<!--xsl:template match="processing-instruction('num')">
		<xsl:param name="contextnode"/><xsl:if test="$contextnode/titel/@num='an' or
					$contextnode/zwischentitel/@num='an' or
					$contextnode/objekttitel/@mediennum!='ohne' or
					$contextnode/@num='an' or
					name($contextnode)='bild' or
					name($contextnode)='grafik' or
					name($contextnode)='video' or
					name($contextnode)='simulation' or
					name($contextnode)='animation' or
					name($contextnode)='textobjekt' or
					name($contextnode)='unteraufgabe' or 
					name($contextnode)='einsendeaufgabe' or 
					name($contextnode)='selbsttestaufgabe' or 
					name($contextnode)='uebungsaufgabe' or
					name($contextnode)='liteintrag' or
					name($contextnode)='fnref' or
					name($contextnode)='eintrag'"
		>
		<xsl:value-of select="normalize-space($contextnode/@number)"/>
		</xsl:if>	</xsl:template-->
		
	<xsl:template match="processing-instruction('num')"><xsl:param name="contextnode"/><xsl:value-of select="normalize-space(($contextnode/@number)[1])"/>	</xsl:template>

	
	<xsl:template match="processing-instruction('numpart')"><xsl:param name="contextnode"/>
		<xsl:if test="$contextnode/titel/@num='an' or
					$contextnode/zwischentitel/@num='an' or
					$contextnode/objekttitel/@mediennum!='ohne' or
					$contextnode/@num='an'">
		<xsl:apply-templates select="ancestor::entry/numpart">
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates></xsl:if></xsl:template>
		
	<xsl:template match="numpart"><xsl:param name="contextnode"/><xsl:apply-templates select="node()">
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates></xsl:template>
	
	<xsl:template match="processing-instruction('title')"><xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/titel/node()[not(self::untertitel)]|$contextnode/zwischentitel|$contextnode/objekttitel"/></xsl:template>
	
	<xsl:template match="processing-instruction('subtitle')"><xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/titel/untertitel"/></xsl:template>
	
	<xsl:template match="processing-instruction('cunum')"><xsl:param name="contextnode" select="current()"/><xsl:value-of select="$contextnode/ancestor-or-self::kurseinheit/@number"/></xsl:template>
	
	<xsl:template match="processing-instruction('points')"><xsl:param name="contextnode"/>
		<xsl:if test="not($contextnode/@punkte)">? </xsl:if><xsl:value-of select="$contextnode/@punkte"/></xsl:template>

	<xsl:template match="processing-instruction('content')">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/><!-- for assignments -->
		<xsl:apply-templates select="$contextnode/node()">
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<!-- content excluding titel/zwischentitel-->
	<xsl:template match="processing-instruction('env-content')">
		<xsl:param name="contextnode"/>
		<xsl:param name="mode"/><!-- for assignments -->
		<xsl:apply-templates select="$contextnode/node()[not(self::titel)][not(self::zwischentitel)][not(self::objekttitel)]">
			<xsl:with-param name="mode" select="$mode"/>
		</xsl:apply-templates>
	</xsl:template>

	
	<xsl:template match="processing-instruction('documentname')"><xsl:value-of select="normalize-space($documentname)"/></xsl:template>
	<xsl:template match="processing-instruction('projectname')"><xsl:value-of select="normalize-space($projectname)"/></xsl:template>

	<xsl:template match="processing-instruction('coursenum')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor::kurs/metadata/kurs-nr"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('coursetitle')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/ancestor-or-self::kurs/metadata/kurs-titel"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('xref')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="ancestor::entry/xrefformat"><xsl:with-param name="contextnode" select="$contextnode"/></xsl:apply-templates>
	</xsl:template>

	
	<!-- ========================= media objects ======================= -->
	<xsl:template match="processing-instruction('mediatitle')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="ancestor::entry/mediatitle">
			<xsl:with-param name="contextnode" select="$contextnode"/>
		</xsl:apply-templates>
	</xsl:template>

	
	<xsl:template match="processing-instruction('picto')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode" mode="picto"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('animationpicto')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/animation" mode="picto"/>
	</xsl:template>

	
	<xsl:template match="processing-instruction('alttext')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode" mode="alttext"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('textobject')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/textobjekt" mode="printtext"/>
	</xsl:template>

	<xsl:template match="processing-instruction('subimages')">
		<!--
			Graphics are arranged in a (n,m) matrix
			m_max: maximum number of columns
			k: number of images in matrix
			Algorithm:
			
			if (k mod m_max = 0)
				m = m_max
			else
				m = min(floor(sqrt(k)), m_max)
			n = ceiling( k / m )
			for i=1 ... n
				for j= 1 ... m
					output image[ (i - 1) * m + j ]
				newline
		
		-->
		<xsl:param name="contextnode"/>
		<xsl:variable name="m_max" select="number(.)"/>
		<xsl:variable name="k"><xsl:value-of select="count($contextnode/(grafik|bild))"/></xsl:variable>
		<xsl:variable name="m">
			<xsl:choose>
				<xsl:when test="$k mod $m_max = 0"><xsl:value-of select="$m_max"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="xs:integer(min(($m_max,ceiling(math:sqrt($k)))))"/></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="n" select="xs:integer(ceiling($k div $m))"/>
		<xsl:message>Subfigure: k=<xsl:value-of select="$k"/>,m=<xsl:value-of select="$m"/>,n=<xsl:value-of select="$n"/></xsl:message>
		<xsl:variable name="newline"><xsl:element name="zeilenende"/></xsl:variable>
		<xsl:variable name="nodes" select="
			for $i in (1 to $n)
			return (for $j in (1 to $m) 
				     return $contextnode/(grafik|bild)[($i - 1) * $m + $j ],
				     $newline/zeilenende )
		"/>
		<xsl:apply-templates select="$nodes"/>
	</xsl:template>
	
	<xsl:template match="processing-instruction('fileref')">
		<xsl:param name="contextnode"/>
		<xsl:apply-templates select="$contextnode/@fileref"/>
	</xsl:template>

	<!-- ========================= url ======================= -->
	
	<xsl:template match="processing-instruction('urladdress')"><xsl:param name="contextnode"/>
		<xsl:value-of select="$contextnode/@adresse"/></xsl:template>
		
	
	<!-- ================= date and time ======================== -->
	<xsl:template match="processing-instruction('current-date')"><xsl:value-of select="current-date()"/></xsl:template>
	
	<xsl:template match="processing-instruction('current-time')"><xsl:value-of select="current-time()"/></xsl:template>
</xsl:stylesheet>
