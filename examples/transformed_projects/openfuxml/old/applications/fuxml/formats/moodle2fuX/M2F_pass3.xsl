<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output  indent="yes" doctype-system="system/dtd/fernuni01.dtd"/>
    <xsl:strip-space elements="*"/>
    
    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="@*|node()">
        <xsl:copy>
                <xsl:apply-templates select="@*|node()" />
        </xsl:copy>
    </xsl:template>
    
    <xsl:template match="lehrtext/abschnitt[1]">
        <xsl:copy>
            <xsl:attribute name="id"><xsl:text>start</xsl:text></xsl:attribute>
                <xsl:apply-templates select="@*|node()" />
        </xsl:copy>
    </xsl:template>

<xsl:template match="lehrtext/abschnitt[position()=last()]">
        <xsl:copy>
            <xsl:attribute name="id"><xsl:text>end</xsl:text></xsl:attribute>
                <xsl:apply-templates select="@*|node()" />
        </xsl:copy>
    </xsl:template>    
    
    <xsl:template match="abschnitt[not(string(titel))]" priority="1">
        <xsl:choose>
            <xsl:when test="absatz">
                <abschnitt>
                    <xsl:if test=".[position()=last()]">
                        <xsl:attribute name="id"><xsl:text>end</xsl:text></xsl:attribute>                        
                    </xsl:if>                    
                <titel>
                    <xsl:value-of select="absatz[1]"/>
                </titel>
                    <xsl:apply-templates select="node()[not(self::absatz[1])]" />
                </abschnitt>                
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    
    <xsl:template match="absatz[descendant::aufzaehlungsliste|nummerierteliste]">
        <absatz><xsl:value-of select="text()"/></absatz>
        <xsl:apply-templates select="aufzaehlungsliste|nummerierteliste"/>
    </xsl:template>
    
</xsl:stylesheet>
