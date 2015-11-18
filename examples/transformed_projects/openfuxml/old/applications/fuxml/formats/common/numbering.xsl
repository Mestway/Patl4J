<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema">
<xsl:include href="designinterface.xsl"/>	
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

		This style sheet controls the numbering of the elements
	The number is generated in a @number attribute
	A number has the format:
	<sectionpart><positionpart><arraypart>
	At the moment, arraypart is only required for equationarrays
	Example.: Equation 5.1.3-1/2
-->
<xsl:include href="preservespace.xsl"/>

<xsl:template match="@*|processing-instruction()|comment()">
	<xsl:copy-of select="."/>
</xsl:template>

<!-- Elements which are not numbered -->
<xsl:template match="document|kurs|abschnitt|lehrtext|fs-elemente|*[parent::fs-elemente]|metadata|*[parent::metadata]|*[parent::titelseite]" priority="1">
	<xsl:copy>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:copy>
</xsl:template>

<xsl:template match="litkennung|absatz|absatz-klein|absatz-ohne|absatz-mini|fett|kursiv|unterstrichen|schreibmaschine|kapitaelchen|motto|hochgestellt|tiefgestellt
					|querverweis|internref|glossarref|url|zeilenende|buchstaeblich|tgroup|tbody|row|entry|colspec|trow|tbody|theader|margref|aufzaehlungsliste|nummerierteliste
					|universalliste|eintrag[(parent::aufzaehlungsliste|parent::universalliste)]|universaleintrag|stichwort|erlaeuterung|fussnote|indexfix|primaer|sekundaer|tertiaer">
	<xsl:copy>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:copy>
</xsl:template>

<xsl:template match="titel|zwischentitel">
	<xsl:copy>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:copy>
</xsl:template>

<!-- Elements for which numbering is switched off are ignored -->
<xsl:template match="*[(titel|zwischentitel)/@num='aus'][not(self::einsendeaufgabe)][not(self::unteraufgabe)]">
	<xsl:copy>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:copy>
</xsl:template>

<!-- elements for which num=off are not numbered, except formelarray that contains formel with num=on -->
<xsl:template match="*[@num='aus'][not(self::formelarray[formel[@num='an']])]">
	<xsl:copy>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:copy>
</xsl:template>



<!-- Default numbering -->
<xsl:template match="*">
	<xsl:variable name="name" select="name(current())"/>
	<xsl:variable name="scheme"><xsl:value-of select="$styles/entry[@name=$name][1]/@numscheme"/></xsl:variable>
	<xsl:variable name="numformat"><!-- is there a numscheme defined for this element ? -->
		<xsl:choose>
			<xsl:when test="$scheme!=''"><!-- no numscheme defined: default scheme  -->
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=$scheme]"/>
			</xsl:when>
			<xsl:otherwise><!-- numscheme is defined -->
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=parent::elementnumbering/@activescheme]"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:copy>
		<xsl:attribute name="number"><!-- element inside mainmatter or frontmatter?-->
			<xsl:choose>
				<xsl:when test="ancestor::ke-lehrtext or not(/document/kurs)"><!-- mainmatter format -->
					<xsl:apply-templates select="$numformat/elementnumberformat/mainmatterformat"><!-- see "designinterface.xsl" -->
						<xsl:with-param name="contextnode" select="current()"/>
					</xsl:apply-templates>			
				</xsl:when>
				<xsl:otherwise><!-- frontmatter format -->
					<xsl:apply-templates select="$numformat/elementnumberformat/frontmatterformat"><!-- see "designinterface.xsl" -->
						<xsl:with-param name="contextnode" select="current()"/>
					</xsl:apply-templates>			
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:copy>
</xsl:template>

<!-- This template is called by "designinterface.xsl" and calulates the pospart-->
<xsl:template match="*" mode="numbering">
	<xsl:variable name="name" select="name(current())"/>
	<xsl:variable name="scheme"><xsl:value-of select="$styles/entry[@name=$name]/@numscheme"/></xsl:variable>
	<xsl:variable name="numformat">
		<xsl:choose>
			<xsl:when test="$scheme!=''">
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=$scheme]"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=parent::elementnumbering/@activescheme]"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

	<xsl:variable name="num-sectionlevel">
		<xsl:choose>
			<xsl:when test="ancestor::ke-lehrtext or not(/document/kurs)">
				<xsl:value-of select="$numformat/elementnumberformat/mainmatterformat/@num-sectionlevel"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$numformat/elementnumberformat/frontmatterformat/@num-sectionlevel"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable> 
	<xsl:variable name="num-posstyle">
		<xsl:choose>
			<xsl:when test="ancestor::ke-lehrtext or not(/document/kurs)">
				<xsl:value-of select="$numformat/elementnumberformat/mainmatterformat/@num-posstyle"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$numformat/elementnumberformat/frontmatterformat/@num-posstyle"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:choose>
		<xsl:when test="number($num-sectionlevel) gt 0">
			<xsl:number level="any" count="*[name(self::*)=$name and (titel/@num!='aus' or zwischentitel/@num!='aus' or not(zwischentitel or titel))]" from="abschnitt[titel[@num='an']][@level le $num-sectionlevel]" format="{$num-posstyle}"/>
		</xsl:when>
		<xsl:when test="$num-sectionlevel='cu'">
			<xsl:number level="any" count="*[name(self::*)=$name and (titel/@num!='aus' or zwischentitel/@num!='aus' or not(zwischentitel or titel))]" from="ke-lehrtext" format="{$num-posstyle}"/>
		</xsl:when>
		<xsl:when test="$num-sectionlevel=0">
			<xsl:number level="any" count="*[name(self::*)=$name and (titel/@num!='aus' or zwischentitel/@num!='aus' or not(zwischentitel or titel))]" from="kurseinheiten" format="{$num-posstyle}"/>
		</xsl:when>
	</xsl:choose>
</xsl:template>




<!-- Multimedia -->
<xsl:template match="medienobjekt">
	<xsl:variable name="name" select="name(current())"/>
	<xsl:variable name="variant">
		<xsl:choose>
			<xsl:when test="bild|grafik">figure</xsl:when>
			<xsl:when test="(animation|video|audio|simulation|textobjekt) and not(bild|grafik)"><xsl:value-of select="name(current()/(animation|video|audio|simulation|textobjekt)[1])"/></xsl:when>
			<xsl:otherwise>mixed</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:variable name="scheme"><xsl:value-of select="$styles/entry[@name=$name][@variant=$variant]/@numscheme"/></xsl:variable>
	<xsl:variable name="numformat">
		<xsl:choose>
			<xsl:when test="$scheme!=''">
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=$scheme]"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=parent::elementnumbering/@activescheme]"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:copy>
		<xsl:attribute name="number">
			<xsl:choose>
				<xsl:when test="ancestor::ke-lehrtext or not(/document/kurs)">
					<xsl:apply-templates select="$numformat/elementnumberformat/mainmatterformat">
						<xsl:with-param name="contextnode" select="current()"/>
					</xsl:apply-templates>			
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates select="$numformat/elementnumberformat/frontmatterformat">
						<xsl:with-param name="contextnode" select="current()"/>
					</xsl:apply-templates>			
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:copy>
</xsl:template>

<xsl:template match="medienobjekt[objekttitel[@mediennum!='ohne']]" mode="numbering">
	<xsl:variable name="name" select="name(current())"/>
	<xsl:variable name="variant">
		<xsl:choose>
			<xsl:when test="bild|grafik">figure</xsl:when>
			<xsl:when test="(animation|video|audio|simulation|textobjekt) and not(bild|grafik)"><xsl:value-of select="name(current()/(animation|video|audio|simulation|textobjekt)[1])"/></xsl:when>
			<xsl:otherwise>mixed</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:variable name="scheme"><xsl:value-of select="$styles/entry[@name=$name][@variant=$variant]/@numscheme"/></xsl:variable>
	<xsl:variable name="numformat">
		<xsl:choose>
			<xsl:when test="$scheme!=''">
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=$scheme]"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=parent::elementnumbering/@activescheme]"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:variable name="num-sectionlevel">
		<xsl:choose>
			<xsl:when test="ancestor::ke-lehrtext or not(/document/kurs)">
				<xsl:value-of select="$numformat/elementnumberformat/mainmatterformat/@num-sectionlevel"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$numformat/elementnumberformat/frontmatterformat/@num-sectionlevel"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable> 
	<xsl:variable name="num-posstyle">
		<xsl:choose>
			<xsl:when test="ancestor::ke-lehrtext or not(/document/kurs)">
				<xsl:value-of select="$numformat/elementnumberformat/mainmatterformat/@num-posstyle"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$numformat/elementnumberformat/frontmatterformat/@num-posstyle"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:variable name="mediennum"><xsl:value-of select="objekttitel/@mediennum"/></xsl:variable>
	<xsl:choose>
		<xsl:when test="$num-sectionlevel='cu'">
			<xsl:number level="any" count="medienobjekt[objekttitel/@mediennum=$mediennum]" from="ke-lehrtext|fs-elemente" format="{$num-posstyle}"/>
		</xsl:when>
		<xsl:when test="number($num-sectionlevel) gt 0">
			<xsl:number level="any" count="medienobjekt[objekttitel/@mediennum=$mediennum]" from="abschnitt[titel[@num='an']][@level le $num-sectionlevel]" format="{$num-posstyle}"/>
		</xsl:when>
				<xsl:when test="$num-sectionlevel=0">
			<xsl:number level="any" count="medienobjekt[objekttitel/@mediennum=$mediennum]" from="kurseinheiten" format="{$num-posstyle}"/>
		</xsl:when>
	</xsl:choose>
</xsl:template>



<!-- Equation -->
<!-- Formelarrays for which numbering is off are counted if they contain a numbered equation! -->
	<xsl:template match="formel[not(parent::formelarray)][@num='an']|
		formelarray[@num='an']|
		formelarray[@num='aus'][formel[@num='an']]">
	<xsl:variable name="num-posstyle" select="$styles/entry[@name='formel']/numberformat[not(@variant)]/num-posstyle"/>
	<xsl:variable name="name" select="xs:string('formel')"/>
	<xsl:variable name="scheme"><xsl:value-of select="$styles/entry[@name=$name][not(@variant)]/@numscheme"/></xsl:variable>
	<xsl:variable name="numformat"><!-- is there a numscheme defined for this element ? -->
		<xsl:choose>
			<xsl:when test="$scheme!=''">
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=$scheme]"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=parent::elementnumbering/@activescheme]"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:copy>
		<xsl:attribute name="number"><!-- element inside mainmatter or frontmatter?-->
			<xsl:choose>
				<xsl:when test="ancestor::ke-lehrtext or not(/document/kurs)"><!-- mainmatter format -->
					<xsl:apply-templates select="$numformat/elementnumberformat/mainmatterformat"><!-- see "designinterface.xsl" -->
						<xsl:with-param name="contextnode" select="current()"/>
					</xsl:apply-templates>			
				</xsl:when>
				<xsl:otherwise><!-- frontmatter format -->
					<xsl:apply-templates select="$numformat/elementnumberformat/frontmatterformat"><!-- see "designinterface.xsl" -->
						<xsl:with-param name="contextnode" select="current()"/>
					</xsl:apply-templates>			
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:copy>
</xsl:template>
	
<!-- Formelarrays for which numbering is off are counted if they contain a numbered equation! -->
<xsl:template match="formel[not(parent::formelarray)][@num='an']|
	formelarray[@num='an']|
	formelarray[@num='aus'][formel[@num='an']]"
	mode="numbering">
	<xsl:variable name="name">formel</xsl:variable>
	<xsl:variable name="numformat">
		<xsl:choose>
			<xsl:when test="$styles/entry[@name=$name][not(@variant)]/@numscheme!=''">
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=$styles/entry[@name=$name][not(@variant)]/@numscheme]"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=parent::elementnumbering/@activescheme]"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:variable name="num-sectionlevel">
		<xsl:choose>
			<xsl:when test="ancestor::ke-lehrtext or not(/document/kurs)">
				<xsl:value-of select="$numformat/elementnumberformat/mainmatterformat/@num-sectionlevel"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$numformat/elementnumberformat/frontmatterformat/@num-sectionlevel"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:variable name="num-posstyle">
		<xsl:choose>
			<xsl:when test="ancestor::ke-lehrtext or not(/document/kurs)">
				<xsl:value-of select="$numformat/elementnumberformat/mainmatterformat/@num-posstyle"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$numformat/elementnumberformat/frontmatterformat/@num-posstyle"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:choose>
		<xsl:when test="number($num-sectionlevel) gt 0">
			<xsl:number 
				level="any"
				count="formelarray[@num='an']|
					formel[@num='an'][not(parent::formelarray)]|
					formelarray[@num='aus'][formel[@num='an']]" 
				from="abschnitt[titel[@num='an']][@level le $num-sectionlevel]" 
				format="{$num-posstyle}"/>
		</xsl:when>
		<xsl:when test="$num-sectionlevel='cu'">
			<xsl:number level="any" 
				count="formelarray[@num='an']|
					formel[@num='an'][not(parent::formelarray)]|
					formelarray[@num='aus'][formel[@num='an']]"
				from="ke-lehrtext" 
				format="{$num-posstyle}"/>
		</xsl:when>
		<xsl:when test="number($num-sectionlevel)=0">
			<xsl:number 
				level="any" 
				count="formelarray[@num='an']|
					formel[@num='an'][not(parent::formelarray)]|
					formelarray[@num='aus'][formel[@num='an']]"
				from="kurseinheiten"
				format="{$num-posstyle}"/>
		</xsl:when>
	</xsl:choose>

</xsl:template>

<xsl:template match="formel[parent::formelarray][@num='an']">
	<xsl:variable name="num-posstyle" select="$styles/entry[@name='formel']/numberformat[@variant='insidearray']/num-posstyle"/>
	<xsl:variable name="name" select="name(current())"/>
	<xsl:variable name="scheme"><xsl:value-of select="$styles/entry[@name=$name][@variant='insidearray']/@numscheme"/></xsl:variable>
	<xsl:variable name="numformat"><!-- is there a numscheme defined for this element ? -->
		<xsl:choose>
			<xsl:when test="$scheme!=''">
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=$scheme]"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=parent::elementnumbering/@activescheme]"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:copy>
		<xsl:attribute name="number"><!-- element inside mainmatter or frontmatter?-->
			<xsl:choose>
				<xsl:when test="ancestor::ke-lehrtext or not(/document/kurs)"><!-- mainmatter format -->
					<xsl:apply-templates select="$numformat/elementnumberformat/mainmatterformat"><!-- see "designinterface.xsl" -->
						<xsl:with-param name="contextnode" select="current()"/>
					</xsl:apply-templates>			
				</xsl:when>
				<xsl:otherwise><!-- frontmatter format -->
					<xsl:apply-templates select="$numformat/elementnumberformat/frontmatterformat"><!-- see "designinterface.xsl" -->
						<xsl:with-param name="contextnode" select="current()"/>
					</xsl:apply-templates>			
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		<xsl:attribute name="posnumber">
			<xsl:number level="any" count="formel[@num='an']" from="formelarray" format="{$num-posstyle}"/>
		</xsl:attribute>

		<xsl:apply-templates select="@*|node()"/>
	</xsl:copy>
</xsl:template>
	
<!-- Formelarrays for which numbering is off are counted if they contain a numbered equation! -->
<xsl:template match="formel[parent::formelarray]" mode="numbering">
	<xsl:variable name="name">formel</xsl:variable>
	<xsl:variable name="numformat">
		<xsl:choose>
			<xsl:when test="$styles/entry[@name=$name][@variant='insidearray']/@numscheme!=''">
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=$styles/entry[@name=$name][@variant='insidearray']/@numscheme]"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme='default']"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:variable name="num-sectionlevel">
		<xsl:choose>
			<xsl:when test="ancestor::ke-lehrtext or not(/document/kurs)">
				<xsl:value-of select="$numformat/elementnumberformat/mainmatterformat/@num-sectionlevel"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$numformat/elementnumberformat/frontmatterformat/@num-sectionlevel"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable> 
	<xsl:variable name="num-posstyle">
		<xsl:choose>
			<xsl:when test="ancestor::ke-lehrtext or not(/document/kurs)">
				<xsl:value-of select="$numformat/elementnumberformat/mainmatterformat/@num-posstyle"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$numformat/elementnumberformat/frontmatterformat/@num-posstyle"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:choose>
		<xsl:when test="number($num-sectionlevel) gt 0">
			<xsl:number 
				level="any" 
				count="formelarray[@num='an']|
					formelarray[@num='aus'][formel[@num='an']]|
					formel[@num='an'][not(parent::formelarray)]" 
				from="abschnitt[titel[@num='an']][@level le $num-sectionlevel]" 
				format="{$num-posstyle}"/>
		</xsl:when>
		<xsl:when test="$num-sectionlevel='cu'">
			<xsl:number 
				level="any"
				count="formelarray[@num='an']|
					formelarray[@num='aus'][formel[@num='an']]|
				formel[@num='an'][not(parent::formelarray)]"
				from="ke-lehrtext"
				format="{$num-posstyle}"/>
		</xsl:when>
		<xsl:when test="$num-sectionlevel=0">
			<xsl:number 
				level="any" 
				count="formelarray[@num='an']|
					formelarray[@num='aus'][formel[@num='an']]|
					formel[@num='an'][not(parent::formelarray)]"
				from="kurseinheiten"
				format="{$num-posstyle}"/>
		</xsl:when>
	</xsl:choose>
</xsl:template>

<xsl:template match="formel[parent::formelarray]" mode="numberingarray">
	<xsl:variable name="name">formel</xsl:variable>
	<xsl:variable name="numformat">
		<xsl:choose>
			<xsl:when test="$styles/entry[@name=$name][@variant='insidearray']/@numscheme!=''">
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=$styles/entry[@name=$name][@variant='insidearray']/@numscheme]"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme='default']"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:number level="any" count="formel[@num='an']" from="formelarray" format="{$numformat/@num-arraystyle}"/>
</xsl:template>

<!-- if arraypart is also defined in numformat for normal equation, this prevents latex code appearing in number-->
<xsl:template match="formel[not(parent::formelarray)]" mode="numberingarray"/>



<!-- Nummbered List -->
<xsl:template match="eintrag[parent::nummerierteliste]">
	<xsl:variable name="level" select="count(ancestor::nummerierteliste)"/>
	<xsl:variable name="numformat"><xsl:call-template name="get.num.format"/></xsl:variable>
	<xsl:copy>
		<xsl:attribute name="number">
			<xsl:number level="any" count="eintrag[parent::nummerierteliste][count(ancestor::nummerierteliste)=$level]" from="nummerierteliste[count(ancestor::nummerierteliste)=$level - 1]" format="{$numformat}"/>
		</xsl:attribute>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:copy>
</xsl:template>

<xsl:template match="eintrag[parent::nummerierteliste[@fortsetzen='ja']]" priority="1">
	<xsl:variable name="level" select="count(ancestor::nummerierteliste)"/>
	<xsl:variable name="numformat"><xsl:call-template name="get.num.format"/></xsl:variable>
	<xsl:copy>
		<xsl:attribute name="number">
			<xsl:number level="any" count="eintrag[parent::nummerierteliste][count(ancestor::nummerierteliste)=$level]" from="nummerierteliste[@fortsetzen='nein'][count(ancestor::nummerierteliste)=$level - 1]" format="{$numformat}"/>
		</xsl:attribute>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:copy>
</xsl:template>


<xsl:template name="get.num.format">
	<xsl:param name="style" select="$styles/entry[@name = 'nummerierteliste']"/>
	<xsl:variable name="level" select="count(ancestor::nummerierteliste)"/>
	<xsl:variable name="enum">
		<xsl:choose>
			<xsl:when test="parent::nummerierteliste/@stiltyp='standard'">
				<xsl:choose>
					<xsl:when test="$level=1"><xsl:value-of select="$style/enumi"/></xsl:when>
					<xsl:when test="$level=2"><xsl:value-of select="$style/enumii"/></xsl:when>
					<xsl:when test="$level=3"><xsl:value-of select="$style/enumiii"/></xsl:when>
					<xsl:when test="$level=4"><xsl:value-of select="$style/enumiv"/></xsl:when>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise><xsl:value-of select="parent::nummerierteliste/@stiltyp"/></xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:call-template name="map.enum">
		<xsl:with-param name="enum" select="$enum"/>
	</xsl:call-template>
</xsl:template>

<xsl:template name="map.enum">
	<xsl:param name="enum"/>
	<xsl:choose>
		<xsl:when test="$enum='dezimal'">1</xsl:when>
		<xsl:when test="$enum='g-roemisch'">I</xsl:when>
		<xsl:when test="$enum='k-roemisch'">i</xsl:when>
		<xsl:when test="$enum='g-alpha'">A</xsl:when>
		<xsl:when test="$enum='k-alpha'">a</xsl:when>
		<xsl:when test="$enum='griechisch'">&#x03B1;</xsl:when>
		<xsl:otherwise>1</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- Literature List -->
<xsl:template match="liteintrag">
	<xsl:variable name="numformat"><xsl:value-of select="$styles/entry[@name = 'literaturliste']/enum"/></xsl:variable>
	<xsl:copy>
		<xsl:attribute name="number">
			<xsl:number level="any" count="liteintrag" from="/" format="{$numformat}"/>
		</xsl:attribute>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:copy>
</xsl:template>



<!-- Exercises -->

<!-- Einsendeaufgaben werden unabhaengig von der Einstellung in zwischentitel/@num durchnummeriert -->
<xsl:template match="einsendeaufgabe" mode="numbering">
	<xsl:variable name="name" select="name(current())"/>
	<!--xsl:variable name="numformat">
		<xsl:choose>
			<xsl:when test="$styles/entry[@name=$name]/@numscheme!=''">
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=$styles/entry[@name=$name]/@numscheme]"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme='default']"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable-->
	
	<xsl:variable name="scheme"><xsl:value-of select="$styles/entry[@name=$name]/@numscheme"/></xsl:variable>
	<xsl:variable name="numformat">
		<xsl:choose>
			<xsl:when test="$scheme!=''">
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=$scheme]"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=parent::elementnumbering/@activescheme]"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

	
	<xsl:variable name="num-sectionlevel">
		<xsl:choose>
			<xsl:when test="ancestor::ke-lehrtext or not(/document/kurs)">
				<xsl:value-of select="$numformat/elementnumberformat/mainmatterformat/@num-sectionlevel"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$numformat/elementnumberformat/frontmatterformat/@num-sectionlevel"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable> 
	<xsl:variable name="num-posstyle">
		<xsl:choose>
			<xsl:when test="ancestor::ke-lehrtext or not(/document/kurs)">
				<xsl:value-of select="$numformat/elementnumberformat/mainmatterformat/@num-posstyle"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$numformat/elementnumberformat/frontmatterformat/@num-posstyle"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:choose>
		<xsl:when test="number($num-sectionlevel) gt 0">
			<xsl:number level="any" count="*[name(self::*)=$name]" from="abschnitt[titel[@num='an']][@level le $num-sectionlevel]" format="{$num-posstyle}"/>
		</xsl:when>
		<xsl:when test="$num-sectionlevel='cu'">
			<xsl:number level="any" count="*[name(self::*)=$name]" from="ke-lehrtext" format="{$num-posstyle}"/>
		</xsl:when>
		<xsl:when test="$num-sectionlevel=0">
			<xsl:number level="any" count="*[name(self::*)=$name]" from="kurseinheiten" format="{$num-posstyle}"/>
		</xsl:when>
	</xsl:choose>
</xsl:template>



<xsl:template match="unteraufgabe" mode="numbering">
	<xsl:variable name="num-posstyle" as="xs:string">
		<xsl:choose>
			<xsl:when test="$styles/entry[@name=name(current())][1]/numberformat">
				<xsl:value-of select="$styles/entry[@name=name(current())]/numberformat/num-posstyle"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test="@numtyp='dezimal'">1</xsl:when>
					<xsl:otherwise>a</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:variable>
	<xsl:number level="any" count="unteraufgabe" from="einsendeaufgabe|uebungsaufgabe|selbsttestaufgabe" format="{$num-posstyle}"/>
</xsl:template>

<!-- Tables -->

<xsl:template match="tabelle[titel[@num='an']]|tabelle-alt[titel[@num='an']]" mode="numbering">
	<xsl:variable name="name" select="name(current())"/>
	<!--xsl:variable name="numformat">
		<xsl:choose>
			<xsl:when test="$styles/entry[@name=$name]/@numscheme!=''">
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=$styles/entry[@name=$name]/@numscheme]"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme='default']"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable-->
	
	<xsl:variable name="scheme"><xsl:value-of select="$styles/entry[@name=$name]/@numscheme"/></xsl:variable>
	<xsl:variable name="numformat">
		<xsl:choose>
			<xsl:when test="$scheme!=''">
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=$scheme]"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy-of select="$config/config/elementnumbering/elementnumberformat[@scheme=parent::elementnumbering/@activescheme]"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

	
	<xsl:variable name="num-sectionlevel">
		<xsl:choose>
			<xsl:when test="ancestor::ke-lehrtext or not(/document/kurs)">
				<xsl:value-of select="$numformat/elementnumberformat/mainmatterformat/@num-sectionlevel"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$numformat/elementnumberformat/frontmatterformat/@num-sectionlevel"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable> 
	<xsl:variable name="num-posstyle">
		<xsl:choose>
			<xsl:when test="ancestor::ke-lehrtext or not(/document/kurs)">
				<xsl:value-of select="$numformat/elementnumberformat/mainmatterformat/@num-posstyle"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$numformat/elementnumberformat/frontmatterformat/@num-posstyle"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:choose>
		<xsl:when test="number($num-sectionlevel) gt 0">
			<xsl:number level="any" count="tabelle[titel/@num='an']|tabelle-alt[titel/@num='an']" from="abschnitt[titel[@num='an']][@level le $num-sectionlevel]" format="{$num-posstyle}"/>
		</xsl:when>
		<xsl:when test="$num-sectionlevel='cu'">
			<xsl:number level="any" count="tabelle[titel/@num='an']|tabelle-alt[titel/@num='an']" from="ke-lehrtext" format="{$num-posstyle}"/>
		</xsl:when>
		<xsl:when test="$num-sectionlevel=0">
			<xsl:number level="any" count="tabelle[titel/@num='an']|tabelle-alt[titel/@num='an']" from="kurseinheiten" format="{$num-posstyle}"/>
		</xsl:when>
	</xsl:choose>
</xsl:template>

<xsl:template match="tabelle[not(titel)]|tabelle-alt[not(titel)]">
	<xsl:copy>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:copy>
</xsl:template>

<!-- redundant -->
<!--xsl:template match="tabelle[titel/@num='aus']|tabelle-alt[titel/@num='aus']">
	<xsl:copy>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:copy>
</xsl:template-->



<!--xsl:template match="tabelle[titel[@num='an']]|tabelle-alt[titel[@num='an']]">
	<xsl:copy>
		<xsl:attribute name="number">
			<xsl:apply-templates select="$styles/entry[@name=name(current())]/numberformat">
				<xsl:with-param name="contextnode" select="current()"/>
			</xsl:apply-templates>
		</xsl:attribute>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:copy>
</xsl:template>



<xsl:template match="tabelle[titel[@num='an']]|tabelle-alt[titel[@num='an']]" mode="numbering">
	<xsl:variable name="num-sectionlevel" select="$styles/entry[@name=name(current())]/numberformat/num-sectionlevel"/>
	<xsl:variable name="num-posstyle" select="$styles/entry[@name=name(current())]/numberformat/num-posstyle"/>
	<xsl:number level="any" count="tabelle[titel[@num='an']]|tabelle-alt[titel[@num='an']]" from="abschnitt[@level le $num-sectionlevel]" format="{$num-posstyle}"/>
</xsl:template-->

<!-- Footnotes -->
<xsl:template match="fnref">
	<xsl:variable name="fnreset" select="$config/config/pagelayout/footnotestyle/@reset"/>
	<xsl:copy>
		<xsl:attribute name="number">
			<xsl:choose>
				<xsl:when test="xs:integer($fnreset) lt 0"><xsl:number level="any" count="fnref" from="/" format="1"/>AA</xsl:when>
				<xsl:when test="xs:integer($fnreset) = 0"><xsl:number level="any" count="fnref" from="kurseinheit" format="1"/></xsl:when>
				<xsl:when test="xs:integer($fnreset) gt 0"><xsl:number level="any" count="fnref" from="abschnitt[@level le $fnreset]" format="1"/></xsl:when>
				<xsl:otherwise><xsl:message>fnerror</xsl:message><xsl:number level="any" count="fnref" from="kurseinheit" format="1"/></xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		<xsl:apply-templates select="@*|node()"/>
	</xsl:copy>
</xsl:template>






</xsl:stylesheet>