<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
   
    <xsl:output use-character-maps="entities" indent="yes" doctype-system="system/dtd/fernuni01.dtd" />
    <xsl:strip-space elements="*"/>
    <xsl:character-map name="entities">
        <xsl:output-character character="&#38;" string="&amp;" />
        <xsl:output-character character="&#63;" string="&amp;" />        
    </xsl:character-map>

   <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:choose>
                <xsl:when test="descendant::*|descendant::node()">
                    <xsl:apply-templates select="@*|node()[not(self::glossar | self::metadata | self::einsendeaufgaben)]" />                    
                </xsl:when>
                <xsl:when test="self::colspec">
                    <xsl:apply-templates select="@*|node()[not(self::glossar | self::metadata | self::einsendeaufgaben)]" />                    
                </xsl:when>
            </xsl:choose>
        </xsl:copy>
    </xsl:template>
 
    <xsl:template match="kurs">
        <xsl:copy>
            <xsl:apply-templates select="//metadata" />            
            <xsl:apply-templates select="@*|node()" />

        <fs-elemente>
            <xsl:apply-templates select="//glossar" />
        </fs-elemente>
            <kurseinheiten>
                <kurseinheit>
                    <ke-lehrtext bereich-start="#start" bereich-ende="#end"/>
                    <titelseite>
                        <kurstitel>Moodle2FuXML Convert</kurstitel>
                        <ke-titel><xsl:value-of select="//metadata/kurs-titel"></xsl:value-of></ke-titel>
                        <a-bezeich>Autorin und Autoren:</a-bezeich>
                        <autoren>Gebhard und Steinkamp GbR</autoren>
                        <m-bezeich>unter Mitarbeit von:</m-bezeich>
                        <mitarbeiter>Salomon, dem Server</mitarbeiter>
                        <g-bezeich>Gestaltung:</g-bezeich>
                        <gestalter>Nobody</gestalter>
                        <copyright>© 2006 Gebhard und Steinkamp GbR</copyright>
                    </titelseite>
                    <xsl:apply-templates select="//einsendeaufgaben" />                    
                </kurseinheit>
            </kurseinheiten>            
        </xsl:copy>        
    </xsl:template>
    
  
    <xsl:template match="font|div|img|span|style|a">
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="p[parent::abschnitt][1]">
        <titel>
        <xsl:apply-templates/>
        </titel>            
    </xsl:template>
    
    <xsl:template match="p">
           <xsl:choose>
               <xsl:when test="parent::absatz or parent::titel">
                   <xsl:apply-templates/>                   
               </xsl:when>
               <xsl:otherwise>
                   <absatz>
                       <xsl:apply-templates/>
                   </absatz>            
               </xsl:otherwise>
           </xsl:choose>
   </xsl:template>
    
    
    <xsl:template match="ul">
        <aufzaehlungsliste>
            <xsl:apply-templates/>
        </aufzaehlungsliste>
    </xsl:template>
    
    <xsl:template match="ol">
        <nummerierteliste>
            <xsl:apply-templates/>
        </nummerierteliste>
    </xsl:template>    
    
    <xsl:template match="li">
        <eintrag><absatz>
            <xsl:apply-templates/>
        </absatz></eintrag>
    </xsl:template> 
    
    <xsl:template match="br">
    </xsl:template>
    
    <xsl:template match="strong">
        <fett>
            <xsl:apply-templates/>
        </fett>
    </xsl:template>

</xsl:stylesheet>
