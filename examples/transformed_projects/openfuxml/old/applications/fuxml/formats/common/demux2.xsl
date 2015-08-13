<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">

    <xsl:template match="node() | @*">
       <xsl:copy>
          <xsl:apply-templates select="node() | @*"/>
       </xsl:copy>
    </xsl:template>
    
    <xsl:template match="selbsttestaufgabe[ancestor::filecontent]|uebungsaufgabe[ancestor::filecontent]">
        <xsl:message>making sol_<xsl:value-of select="@id"/>.html</xsl:message>
        <!-- copy the full assignment-element -->
        <xsl:copy>
            <xsl:apply-templates select="node()[not(aufgabenloesung)] | @*"/>
        </xsl:copy>
        <!-- plus: do the solution in an extra file -->
        <file>
            <xsl:attribute name="filename">sol_<xsl:value-of select="aufgabenloesung/@id"/>.html</xsl:attribute>
            <xsl:attribute name="directory"/>
            <xsl:attribute name="Modus">solution</xsl:attribute>            
            <xsl:attribute name="design"/>
            <xsl:attribute name="integrate">no</xsl:attribute>
            <xsl:element name="{name(.)}">
                <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
                <xsl:attribute name="number"><xsl:value-of select="@number"/></xsl:attribute>                
                <xsl:copy-of select="aufgabenloesung"/>
            </xsl:element>
        </file>
    </xsl:template>
    <xsl:template match="document">
        <xsl:message>working on document</xsl:message>
        <xsl:copy>
            <xsl:apply-templates select="node() | @*"/>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>