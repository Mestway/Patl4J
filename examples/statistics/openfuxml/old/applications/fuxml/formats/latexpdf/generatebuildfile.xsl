<?xml version="1.0" encoding="UTF-8"?>
<!-- ********************************************************************
	$Id: generatebuildfile.xsl,v 1.7 2007/08/06 12:51:43 kisner Exp $
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
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:param name="document">document</xsl:param>
	<xsl:param name="workdir">file:///I:/output/k20022/latexpdf/oop_kurs</xsl:param>
	<xsl:variable name="request" select="document(concat($workdir,'/request.xml'))"/>
	<xsl:variable name="config" select="document('../common/config.xml')"/>
	<xsl:variable name="styles" select="$config/config/styles"/>

	


	<xsl:template match="documents">
		<project default="latexpdfproduction">
			
			<target name="latexpdfproduction">
				<xsl:if test="$request/sessionpreferences/draftmode/@active='true' or $request/sessionpreferences/options/option[@name='draftmode']/@value='true'">
					<echo>### DRAFT MODE ACTIVATED ###</echo>
					<property name="draftmodeactive" value="true"/>
				</xsl:if>
				
				<xsl:if test="$request/sessionpreferences/productionentities/file[contains(@filename,'.zip')]">
					<echo>### ZIP REQUESTED ###</echo>
					<property name="ziprequested" value="true"/>
				</xsl:if>
				<!-- Encoding must be changed prior to checksum, otherwise rebuild would always take place -->
				<echo>Encoding</echo>
				<antcall target="encoding"/>
				
				<xsl:apply-templates select="document" mode="setproperties"/>
				
				<echo>Latex Pass 1</echo>
				<antcall target="latex"/>
				<xsl:if test="$config/config/bibtexml/@active='true'"><antcall target="bibtex"/></xsl:if>
				<antcall target="toc_all"/>
				<echo>Latex Pass 2</echo>
				<antcall target="latex"/>
				<antcall target="toc_all"/>
				<echo>Latex Pass 2.5</echo>
				<antcall target="latex"/>				
				<echo>Latex Pass 3</echo>
				<antcall target="latex"/>
				<antcall target="checksum"/>
				<echo>Creating PDF</echo>
				<antcall target="pdf"/>
				<echo>Packaging</echo>
				<antcall target="copy"/>
				<antcall target="package"/>
				<delete>
					<xsl:attribute name="dir"><xsl:value-of select="$workdir"/>/tmp</xsl:attribute>
				</delete>
			</target>
			
			<target name="encoding">
				<xsl:apply-templates select="document" mode="callencoding"/>
			</target>
			<xsl:apply-templates select="document" mode="encoding"/>
			<target name="latex">
				<xsl:apply-templates select="document" mode="calllatex"/>
			</target>
			<xsl:apply-templates select="document" mode="latex"/>
			<target name="bibtex">
				<xsl:apply-templates select="document" mode="callbibtex"/>
			</target>
			<xsl:apply-templates select="document" mode="bibtex"/>

			<target name="checksum">
				<echo>Building Checksums</echo>
				<xsl:apply-templates select="document" mode="checksum"/>
			</target>

			
			<target name="pdf">
				<xsl:apply-templates select="document" mode="callpdf"/>
			</target>
			<xsl:apply-templates select="document" mode="pdf"/>

	
			<target name="toc_all">
				<echo>Creating Overall TOCs and Index</echo>
				<concat>
					<xsl:attribute name="destfile"><xsl:value-of select="$workdir"/>/toc-course.toc</xsl:attribute>
					<fileset>
						<xsl:attribute name="dir"><xsl:value-of select="$workdir"/></xsl:attribute>
						<xsl:apply-templates select="document" mode="toc-course"/>
					</fileset>
				</concat>
				<concat>
					<xsl:attribute name="destfile"><xsl:value-of select="$workdir"/>/index-course.idx</xsl:attribute>
					<fileset>
						<xsl:attribute name="dir"><xsl:value-of select="$workdir"/></xsl:attribute>
						<xsl:apply-templates select="document" mode="index-course"/>
					</fileset>
				</concat>
				<concat>
					<xsl:attribute name="destfile"><xsl:value-of select="$workdir"/>/totable-course.totable</xsl:attribute>
					<fileset>
						<xsl:attribute name="dir"><xsl:value-of select="$workdir"/></xsl:attribute>
						<xsl:apply-templates select="document" mode="totab-course"/>
					</fileset>
				</concat>
				<concat>
					<xsl:attribute name="destfile"><xsl:value-of select="$workdir"/>/tofig-course.tofig</xsl:attribute>
					<fileset>
						<xsl:attribute name="dir"><xsl:value-of select="$workdir"/></xsl:attribute>
						<xsl:apply-templates select="document" mode="tofig-course"/>
					</fileset>
				</concat>
			</target>
			
			<target name="copy">
				<echo>Copying files</echo>
				<xsl:apply-templates select="document" mode="copy"/>
			</target>
			
			<target name="package" if="ziprequested">
				<echo>Packaging files</echo>
				<xsl:apply-templates select="document[not(@archive='true')]" mode="package"/>
				<xsl:apply-templates select="document[@archive='true']" mode="package"/>
			</target>
		</project>
	</xsl:template>

	
	<xsl:template match="document" mode="setproperties">
		<!-- unless only checks if property has been set, not if its true or false !! -->
		<xsl:variable name="file" select="normalize-space(filename)"/>
		
		<xsl:if test="$request/sessionpreferences/productionentities/file[@filename=concat(normalize-space(current()/filename),'.pdf')]
				or $request/sessionpreferences/productionentities/file[@filename=concat(normalize-space(current()/filename),'.zip')]">
			<property name="inrequest_{$file}" value="true"/>
		</xsl:if>
		<echo>inrequest <xsl:value-of select="$file"/>:   ${inrequest_<xsl:value-of select="$file"/>}</echo>

		
		<xsl:if test="not(@archive='true')">
			<!-- sets property if checksum is correct -->
			<condition property="isMD5ok_{$file}">	
				<checksum file="{$workdir}/{$file}.tex"/>
			</condition>
			<echo>isMD5ok <xsl:value-of select="$file"/>: ${isMD5ok_<xsl:value-of select="$file"/>}</echo>
		
			<xsl:if test="$request/sessionpreferences/productionentities/file[contains(@filename,'.zip')]">
				<property name="inzip_{$file}" value="true"/>
			</xsl:if>
			<echo>inzip <xsl:value-of select="$file"/>:   ${inzip_<xsl:value-of select="$file"/>}</echo>
			
			<condition property="pdfproductionrequired_{$file}">
				<!-- PDF muss auch dann produziert, wenn die Checksumme ok ist. -->
				<or>
					<isset property="inrequest_{$file}"/>
					<isset property="inzip_{$file}"/>
				</or>
			</condition>
			
			<condition property="latexproductionrequired_{$file}">
				<and>
					<not>
						<isset property="isMD5ok_{$file}"/>
					</not>
					<or>
						<not>
							<isset property="draftmodeactive"/>
						</not>
						<isset property="inrequest_{$file}"/>
						<isset property="inzip_{$file}"/>
					</or>
				</and>
			</condition>
			<echo>latexproduction <xsl:value-of select="$file"/>:  ${latexproductionrequired_<xsl:value-of select="$file"/>}</echo>
			<echo>### PRODUCE <xsl:value-of select="$file"/>: ${pdfproductionrequired_<xsl:value-of select="$file"/>} ###</echo>
		</xsl:if>
		<xsl:apply-templates select="content" mode="setproperties"/>
	</xsl:template>
	

	<xsl:template match="document" mode="callencoding">
		<xsl:variable name="file" select="normalize-space(filename)"/>
		<xsl:if test="not(@archive='true')">
		<antcall target="encoding_{$file}"/>
		</xsl:if>
		<xsl:apply-templates select="content" mode="callencoding"/>
	</xsl:template>

	
	<xsl:template match="document" mode="calllatex">
		<xsl:variable name="file" select="normalize-space(filename)"/>
		<xsl:if test="not(@archive='true')">
		<antcall target="latex_{$file}"/>
		</xsl:if>
		<xsl:apply-templates select="content" mode="calllatex"/>
	</xsl:template>
	
	<xsl:template match="document" mode="callpdf">
		<xsl:variable name="file" select="normalize-space(filename)"/>
		<xsl:if test="not(@archive='true')">
		<antcall target="pdf_{$file}"/>
		</xsl:if>
		<xsl:apply-templates select="content" mode="callpdf"/>
	</xsl:template>

	<xsl:template match="document" mode="callbibtex">
		<xsl:variable name="file" select="normalize-space(filename)"/>
		<xsl:if test="not(@archive='true')">
		<antcall target="bibtex_{$file}"/>
		</xsl:if>
		<xsl:apply-templates select="content" mode="callbibtex"/>
	</xsl:template>

	<!-- Alte Version mit Systemprogramm iconv 
	<xsl:template match="document" mode="encoding">
		<xsl:variable name="file" select="normalize-space(filename)"/>
		<xsl:if test="not(@archive='true')">
		<target name="encoding_{$file}">
			<echo>Changing encoding for <xsl:value-of select="$file"/>.tex to Latin1</echo>
			<exec executable="iconv" failonerror="true">
				<xsl:attribute name="dir"><xsl:value-of select="$workdir"/></xsl:attribute>
				<xsl:attribute name="output"><xsl:value-of select="$file"/>_enc.tex</xsl:attribute>
				<!-arg value="-c"/-> <!- this option does not help ->
				<arg value="-f"/>
				<arg value="utf-8"/>
				<arg value="-t"/>
				<arg value="latin1"/>
				<arg value="{$file}.tex"/>
			</exec>
			<delete file="{$file}.tex"/>
			<move file="{$file}_enc.tex" tofile="{$file}.tex"/>
			</target>
		</xsl:if>
		<xsl:apply-templates select="content" mode="encoding"/>
	</xsl:template>
	-->
	
	<!-- Testversion mit Transcode -->
	<xsl:template match="document" mode="encoding">
		<xsl:variable name="file" select="normalize-space(filename)"/>
		<xsl:if test="not(@archive='true')">
			<target name="encoding_{$file}">
				<echo>Changing encoding for <xsl:value-of select="$file"/>.tex to Latin1 (Transcode-Version)</echo>
				<exec executable="java" failonerror="true">
					<xsl:attribute name="dir"><xsl:value-of select="$workdir"/></xsl:attribute>
					<arg value="org.openfuxml.util.FileTranscoder"/>
					<arg value="{$file}.tex"/>
					<arg value="utf-8"/>
					<arg value="{$file}_enc.tex"/>
					<arg value="latin1"/>
				</exec>
				<delete file="{$file}.tex"/>
				<move file="{$file}_enc.tex" tofile="{$file}.tex"/>
			</target>
		</xsl:if>
		<xsl:apply-templates select="content" mode="encoding"/>
	</xsl:template>
	
	
	<xsl:template match="document" mode="latex">
		<xsl:variable name="file" select="normalize-space(filename)"/>
		<xsl:if test="not(@archive='true')">
		<target name="latex_{$file}" if="latexproductionrequired_{$file}">
			<!-- Index -->
			<!-- Index only defined for Kurseinheit so far -->
			<xsl:if test="@indexfiles">
			<concat destfile="{$workdir}/{$file}.idxx">
				<filelist>
					<xsl:attribute name="dir"><xsl:value-of select="$workdir"/></xsl:attribute>
					<xsl:attribute name="files"><xsl:value-of select="@indexfiles"/></xsl:attribute>
				</filelist>
			</concat>
			<exec executable="makeindex" timeout="60000">
				<xsl:attribute name="dir"><xsl:value-of select="$workdir"/></xsl:attribute>
				<arg value="-g"/>
				<arg value="-s"/>
				<arg value="fuxml.ist"/>
				<arg value="{$file}.idxx"/>
			</exec>
			</xsl:if>
			
			<exec executable="latex" timeout="60000"><!-- timeout after 1min -->
				<xsl:attribute name="dir"><xsl:value-of select="$workdir"/></xsl:attribute>
				<arg value="-interaction=batchmode"/>
				<arg value="{$file}.tex"/>
			</exec>
			
			<!-- Store last page information -->
			<loadfile>
				<xsl:attribute name="property"><xsl:value-of select="$file"/>_page</xsl:attribute>
				<xsl:attribute name="srcfile"><xsl:value-of select="$workdir"/>/<xsl:value-of select="$file"/>.log</xsl:attribute>
				<filterchain>
					<linecontains>
						<contains value="@lastpage:@"/>
					</linecontains>
					<replacetokens>
						<token key="lastpage:" value=""/>
					</replacetokens>
				</filterchain>
			</loadfile>
			<echo  append="false"><xsl:attribute name="file"><xsl:value-of select="$workdir"/>/<xsl:value-of select="$file"/>.lastpage.tex</xsl:attribute>\setcounter{page}{${<xsl:value-of select="$file"/>_page}}</echo>
		</target>
		
		</xsl:if>
		<xsl:apply-templates select="content" mode="latex"/>
	</xsl:template>
	
	<xsl:template match="document" mode="bibtex">
		<xsl:variable name="file" select="normalize-space(filename)"/>
		<xsl:if test="not(@archive='true')">
		<target name="bibtex_{$file}" if="latexproductionrequired_{$file}">
			<exec executable="bibtex">
				<xsl:attribute name="dir"><xsl:value-of select="$workdir"/></xsl:attribute>
				<arg value="{normalize-space(filename)}"/>
			</exec>
		</target>
		</xsl:if>
		<xsl:apply-templates select="content" mode="bibtex"/>
	</xsl:template>
	
	
	<xsl:template match="document" mode="checksum">
		<xsl:variable name="file" select="normalize-space(filename)"/>
		<!-- create checksum for tex files -->
		<xsl:if test="not(@archive='true')">
			<checksum>
				<xsl:attribute name="file"><xsl:value-of select="$workdir"/>/<xsl:value-of select="$file"/>.tex</xsl:attribute>
			</checksum>
		</xsl:if>
		<xsl:apply-templates select="content" mode="checksum"/>
	</xsl:template>

	
	
	<xsl:template match="document" mode="pdf">
		<xsl:variable name="file" select="normalize-space(filename)"/>
		
		<!--xsl:if test="not(@archive='true')"-->
			<target name="pdf_{$file}">
	
				<echo message="{$file}.pdf"></echo>
				<apply executable="dvips">
					<xsl:attribute name="dest"><xsl:value-of select="$workdir"/></xsl:attribute>
					<!--arg value="-Ppdf"/-->
					<arg line="-R0"/>
					<srcfile/>
					<fileset includes="{$file}.dvi">
						<xsl:attribute name="dir"><xsl:value-of select="$workdir"/></xsl:attribute>
					</fileset>
					<mapper type="glob" from="*.dvi" to="*.ps"/>
				</apply>
				<xsl:if test="$request/sessionpreferences/options/option[@name='psbook']/@value='true'">
					<echo message="{$file}.book.ps"></echo>
					<apply executable="psbook">
						<xsl:attribute name="dest"><xsl:value-of select="$workdir"/></xsl:attribute>
						<srcfile/>
						<targetfile/>
						<fileset includes="{$file}.ps">
							<xsl:attribute name="dir"><xsl:value-of select="$workdir"/></xsl:attribute>
						</fileset>
						<mapper type="glob" from="*.ps" to="*.book.ps"/>
					</apply>
					<delete file="{$file}.ps"/>
					<echo message="{$file}.nup.ps"></echo>
					<apply executable="psnup">
						<xsl:attribute name="dest"><xsl:value-of select="$workdir"/></xsl:attribute>
						<arg line="-2 -p a4"/>
						<srcfile/>
						<targetfile/>
						<fileset includes="{$file}.book.ps">
							<xsl:attribute name="dir"><xsl:value-of select="$workdir"/></xsl:attribute>
						</fileset>
						<mapper type="glob" from="*.book.ps" to="*.nup.ps"/>
					</apply>
					<echo message="{$file}.ps">|pstops</echo>
					
					<apply executable="pstops">
						<xsl:attribute name="dest"><xsl:value-of select="$workdir"/></xsl:attribute>
						<arg line="'2:0,1U(1w,1h)' -p a4"/>
						<srcfile/>
						<targetfile/>
						<fileset includes="{$file}.nup.ps">
							<xsl:attribute name="dir"><xsl:value-of select="$workdir"/></xsl:attribute>
						</fileset>
						<mapper type="glob" from="*.nup.ps" to="*.ps"/>
					</apply>
				</xsl:if>
				<!--ps2pdf12 (ps2pdf) fails for large bitmap images -->
				<!-- Hier wird der PS nach PDF Prozess fuer Unix und Windows jeweils verschieden realisiert -->
				<apply executable="ps2pdf13" osfamily="unix">
					<xsl:attribute name="dest"><xsl:value-of select="$workdir"/></xsl:attribute>
					<arg line="-dAutoRotatePages=/All -sPAPERSIZE=a4"/>
					<srcfile/>
					<fileset includes="{$file}.ps">
						<xsl:attribute name="dir"><xsl:value-of select="$workdir"/></xsl:attribute>
					</fileset>
					<mapper type="glob" from="*.ps" to="*.pdf"/>
				</apply>
			<apply executable="GSWin32c" osfamily="windows">
					<xsl:attribute name="dest"><xsl:value-of select="$workdir"/></xsl:attribute>
					<arg line="-sDEVICE=pdfwrite"/>
					<arg line="-dCompatibilityLevel=1.3"/>
					<arg line="-sOutputFile={$file}.pdf"/>
					<arg line="-dDEVICEWIDTHPOINTS=595"/>
					<arg line="-dDEVICEHEIGHTPOINTS=841"/>
					<arg line="-dNOPAUSE"/>
					<srcfile/>
					<fileset includes="{$file}.ps">
						<xsl:attribute name="dir"><xsl:value-of select="$workdir"/></xsl:attribute>
					</fileset>
					<mapper type="glob" from="*.ps" to="*.pdf"/>
					<arg line="-c quit"/>
				</apply>
			</target>
		<!--/xsl:if-->
	<xsl:apply-templates select="content" mode="pdf"/>
	</xsl:template>
	
	
	<!-- Copy files into output directory -->
	<xsl:template match="document[not(@archive='true')]" mode="copy">
		<xsl:variable name="file" select="normalize-space(filename)"/>
		<xsl:variable name="directory" select="normalize-space(directory[1])"/>
		<copy failonerror="false">
			<xsl:attribute name="file"><xsl:value-of select="$workdir"/>/<xsl:value-of select="$file"/>.pdf</xsl:attribute>
			<xsl:attribute name="todir"><xsl:value-of select="$workdir"/><xsl:if test="$directory!=''">/<xsl:value-of select="$directory"/></xsl:if></xsl:attribute>
		</copy>
		<xsl:apply-templates select="content" mode="copy"/>
	</xsl:template>
	
	<xsl:template match="document[@archive='true']" mode="copy"></xsl:template>

	<!-- Create packages -->
	<xsl:template match="document[@archive='true']" mode="package">
		<xsl:variable name="file" select="normalize-space(filename)"/>
		<xsl:variable name="directory" select="normalize-space(directory[1])"/>
		<mkdir dir="{$workdir}/{$directory}"/>
		<zip>
			<xsl:attribute name="destfile"><xsl:value-of select="$workdir"/>/<xsl:value-of select="$file"/>.zip</xsl:attribute>
			<xsl:attribute name="basedir"><xsl:value-of select="$workdir"/>/.<xsl:if test="$directory!=''">/<xsl:value-of select="$directory"/></xsl:if></xsl:attribute>
		</zip> 
	</xsl:template>

	<!-- Copy files into temporary directory for zipping -->
	<xsl:template match="document[not(@archive='true')]" mode="package">
		<!--xsl:variable name="file" select="normalize-space(filename)"/>
		<xsl:variable name="directory" select="normalize-space(directory[1])"/>
		<copy failonerror="false">
			<xsl:attribute name="file"><xsl:value-of select="$workdir"/>/<xsl:value-of select="$file"/>.pdf</xsl:attribute>
			<xsl:attribute name="todir"><xsl:value-of select="$workdir"/>/tmp/material</xsl:attribute>
		</copy>
		<xsl:apply-templates select="content" mode="package"/-->
	</xsl:template>

	
	<xsl:template match="document" mode="toc-course">
		<include name="{normalize-space(filename)}.toc"/>
		<xsl:apply-templates select="content" mode="toc-course"/>
	</xsl:template>
	
	<xsl:template match="document" mode="index-course">
		<include name="{normalize-space(filename)}.idx"/>
		<xsl:apply-templates select="content" mode="toc-course"/>
	</xsl:template>
	
	<xsl:template match="document" mode="totab-course">
		<include name="{normalize-space(filename)}.totable"/>
		<xsl:apply-templates select="content" mode="totab-course"/>
	</xsl:template>
	
	<xsl:template match="document" mode="tofig-course">
		<include name="{normalize-space(filename)}.tofig"/>
		<xsl:apply-templates select="content" mode="tofig-course"/>
	</xsl:template>
	
	
	
	
	<xsl:template match="content">
		<xsl:apply-templates select="document"/>
	</xsl:template>
	
	<xsl:template match="content" mode="encoding">
		<xsl:apply-templates select="document" mode="encoding"/>
	</xsl:template>
	
	<xsl:template match="content" mode="latex">
		<xsl:apply-templates select="document" mode="latex"/>
	</xsl:template>
	
	<xsl:template match="content" mode="callencoding">
		<xsl:apply-templates select="document" mode="callencoding"/>
	</xsl:template>

	<xsl:template match="content" mode="calllatex">
		<xsl:apply-templates select="document" mode="calllatex"/>
	</xsl:template>
	
	<xsl:template match="content" mode="bibtex">
		<xsl:apply-templates select="document" mode="bibtex"/>
	</xsl:template>

	<xsl:template match="content" mode="callbibtex">
		<xsl:apply-templates select="document" mode="callbibtex"/>
	</xsl:template>
	
	<xsl:template match="content" mode="callpdf">
		<xsl:apply-templates select="document" mode="callpdf"/>
	</xsl:template>
	
	<xsl:template match="content" mode="checksum">
		<xsl:apply-templates select="document" mode="checksum"/>
	</xsl:template>
	
	<xsl:template match="content" mode="setproperties">
		<xsl:apply-templates select="document" mode="setproperties"/>
	</xsl:template>


	
	<xsl:template match="content" mode="pdf">
		<xsl:apply-templates select="document" mode="pdf"/>
	</xsl:template>
	
	<xsl:template match="content" mode="copy">
		<xsl:apply-templates select="document" mode="copy"/>
	</xsl:template>
	
	<xsl:template match="content" mode="package">
		<xsl:apply-templates select="document" mode="package"/>
	</xsl:template>

	
	<xsl:template match="content" mode="toc-course">
		<xsl:apply-templates select="document" mode="toc-course"/>
	</xsl:template>
	
	<xsl:template match="content" mode="index-course">
		<xsl:apply-templates select="document" mode="index-course"/>
	</xsl:template>
	
	<xsl:template match="content" mode="totab-course">
		<xsl:apply-templates select="document" mode="totab-course"/>
	</xsl:template>
	
	<xsl:template match="content" mode="tofig-course">
		<xsl:apply-templates select="document" mode="tofig-course"/>
	</xsl:template>

	

	
	
</xsl:stylesheet>
