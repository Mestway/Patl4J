<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
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
	<!-- Diese Datei dient der leichten Anpassung von layoutspeziefischen Einstellungen bei der Nutzung von FuXML -->
	<!-- DIE MEISTEN EINSTELLUNGEN ZUM LAYOUT WERDEN JEDOCH IN DER CSS-DATEI VORGENOMMEN -->
	
	<!-- Globale Einstellungen -->
	<xsl:variable name="cssdateiname">html.css</xsl:variable>
	<!--xsl:param name="kurspfad"></xsl:param-->
	<xsl:param name="Kurseinheitname">kurseinheit</xsl:param>
	<xsl:param name="Kursname">design</xsl:param>
	<xsl:param name="designpfad">design/</xsl:param>
	
	<!-- Frameeinstellungen -->
	<xsl:variable name="haupttarget"></xsl:variable>			<!-- Frameziel den Hauptinformationsframe -->
	<xsl:variable name="aufgabentarget"></xsl:variable>		<!-- Frameziel für die Aufgaben -->
	<xsl:variable name="loesungentarget"></xsl:variable>		<!-- Frameziel für die Loesungen -->
	<xsl:variable name="fussnotentarget"></xsl:variable>		<!-- Frameziel für die Fussnoten -->
		
	<xsl:variable name="bildschirmseiten_automatik">an</xsl:variable>
	<xsl:variable name="schnittebene">2</xsl:variable>
	
	<!-- Filenamen -->
	<xsl:variable name="kurseinheit_filename">ke</xsl:variable>
	<xsl:variable name="einsendeaufgabe_filename">ea</xsl:variable>
	<xsl:variable name="musterloesung_filename">ml</xsl:variable>
	<xsl:variable name="korrektur_filename">kor</xsl:variable>

	<!-- ENDE Globale Einstellungen -->

	<!-- Nummerierung: 		Abschnittstei   abschnittstrennzeichen  Positionsteil 
		Beispiel: Definition 		1.1.2					-					3
	-->
	<!-- Typ: plain, italic, box, box-->
	<!-- font-family: serif, sans-serif, cursive, fantasy, monospace -->
	
	<!-- Einstellungen für Beispiel -->										   	
	<xsl:variable name="beispiel_bezeichner"></xsl:variable> 		
	<xsl:variable name="beispiel_abschnittstrennzeichen">-</xsl:variable>		<!-- deprecated -->
	<xsl:variable name="beispiel_trennzeichen">: </xsl:variable>				<!-- deprecated -->
	<xsl:variable name="beispiel_num_format_abschnitt">2</xsl:variable>		<!--Abschnittsteil der Nummerierung -->
	<xsl:variable name="beispiel_num_format_pos">1</xsl:variable>			<!-- Format -->
	<xsl:variable name="beispiel-font-family">cursive</xsl:variable>
	<xsl:variable name="beispiel-bg-color">#AAAAAA</xsl:variable>
	<xsl:variable name="beispiel-margin-top">2cm</xsl:variable>
	<xsl:variable name="beispiel-margin-bottom">2cm</xsl:variable>
	<xsl:variable name="beispiel-margin-left">2cm</xsl:variable>
	<xsl:variable name="beispiel-margin-right">2cm</xsl:variable>
	<xsl:variable name="beispiel-border-width">0pt</xsl:variable>
	<xsl:variable name="beispiel-border-color">#000000</xsl:variable>
	<xsl:variable name="css"><![CDATA[border-style: solid dotted]]></xsl:variable>

	
	
	
	<!-- Einstellungen zu Fussnoten -->

	<xsl:variable name="abschnitt_abschnittstrennzeichen">-</xsl:variable>		<!-- jedes Zeichen -->
	<xsl:variable name="abschnitt_trennzeichen">: </xsl:variable>					<!-- 	"	" -->

	<!-- Einstellungen für Abbildungen  und Grafiken -->							<!-- Mögliche Werte-->
	<xsl:variable name="bild_bezeichner">Abbildung </xsl:variable>				<!-- Abb, Abildung, Figure, etc. -->
	<xsl:variable name="bild_beschreibungstextposition">drunter</xsl:variable>	<!-- drunter, daneben -->
	<xsl:variable name="bild_abschnittstrennzeichen">-</xsl:variable>				<!-- jedes Zeichen -->
	<xsl:variable name="bild_trennzeichen">: </xsl:variable>						<!-- 	"	" -->
	<xsl:variable name="bild_num_format_abschnitt">2</xsl:variable>			<!--Abschnittsteil der Nummerierung -->
	<xsl:variable name="bild_num_format_pos">1</xsl:variable>
	<xsl:variable name="printgraphic">eps</xsl:variable> <!-- Endung bei Grafiken fuer Druck -->	
	
	<!-- Einstellungen für Animationen -->		
	<xsl:variable name="animation_bezeichner">Animation  </xsl:variable>				
	<xsl:variable name="animation_abschnittstrennzeichen">-</xsl:variable>				<!-- jedes Zeichen -->
	<xsl:variable name="animation_trennzeichen">: </xsl:variable>						<!-- 	"	" -->
	<xsl:variable name="animation_num_format_abschnitt">2</xsl:variable>			<!--Abschnittsteil der Nummerierung -->
	<xsl:variable name="animation_num_format_pos">1</xsl:variable>
	
					

	<!-- Einstellungen für Einsendeaufgaben -->										   	<!-- Mögliche Werte-->
	<xsl:variable name="einsendeaufgabe_bezeichner">Einsendeaufgabe </xsl:variable> 	<!-- ???, etc. -->
	<xsl:variable name="einsendeaufgabe_loesung_bezeichner">Lösung: </xsl:variable>
	<xsl:variable name="einsendeaufgabe_abschnittstrennzeichen">-</xsl:variable>		<!-- jedes Zeichen -->
	<xsl:variable name="einsendeaufgabe_trennzeichen">: </xsl:variable>					<!-- 	"	" -->
	<xsl:variable name="einsendeaufgabe_num_format_abschnitt"></xsl:variable>		<!--Abschnittsteil der Nummerierung -->
	<xsl:variable name="einsendeaufgabe_num_format_pos">1</xsl:variable>
	


	<!-- Einstellungen für Selbsttestaufgaben -->										   	<!-- Mögliche Werte-->
	<xsl:variable name="selbsttestaufgabe_bezeichner">Selbsttestaufgabe </xsl:variable> 	<!-- ???, etc. -->
	<xsl:variable name="selbsttestaufgabe_abschnittstrennzeichen">/</xsl:variable>		<!-- jedes Zeichen -->
	<xsl:variable name="selbsttestaufgabe_trennzeichen">: </xsl:variable>				<!-- 	"	" -->
	<xsl:variable name="selbsttestaufgabe_num_format_abschnitt">1</xsl:variable>		<!--Abschnittsteil der Nummerierung -->
	<xsl:variable name="selbsttestaufgabe_num_format_pos">1</xsl:variable>	

	<!-- Einstellungen für Uebungsaufgaben -->										   	<!-- Mögliche Werte-->
	<xsl:variable name="uebungsaufgabe_bezeichner">Uebungsaufgabe </xsl:variable> 		<!-- ???, etc. -->
	<xsl:variable name="uebungsaufgabe_abschnittstrennzeichen">-</xsl:variable>		<!-- jedes Zeichen -->
	<xsl:variable name="uebungsaufgabe_trennzeichen">: </xsl:variable>					<!-- 	"	" -->
	<xsl:variable name="uebungsaufgabe_num_format_abschnitt">1</xsl:variable>		<!--Abschnittsteil der Nummerierung -->
	<xsl:variable name="uebungsaufgabe_num_format_pos">1</xsl:variable>	
	
	<!-- Unteraufgabe -->
	<xsl:variable name="unteraufgabe_num_format_pos">a</xsl:variable>	
	
	<!-- Abschnittsnummerierung -->
	<xsl:variable name="num_typ">normal</xsl:variable> 					<!-- Mögliche Werte:normal, jura -->
	<xsl:variable name="num_depth">4</xsl:variable>
	<xsl:variable name="abschnitt_num_format">1.1.1.1.1.1.1.1.1.1.1</xsl:variable> 	<!-- 1, A, a, I, i -->
			
	
	<!-- Einstellungen für Formeln -->										   			<!-- Mögliche Werte-->
	<xsl:variable name="formel_bezeichner1">(</xsl:variable> 						<!-- vor der Nummerierung -->
	<xsl:variable name="formel_bezeichner2">)</xsl:variable> 						<!-- nach der Nummerierung -->
	<xsl:variable name="formel_abschnittstrennzeichen">/</xsl:variable>				<!-- jedes Zeichen -->
	<xsl:variable name="formel_abschnittstrennzeichenarray">.</xsl:variable>			<!-- jedes Zeichen -->	
	<xsl:variable name="formel_trennzeichen">: </xsl:variable>						<!-- 	"	" -->
	<xsl:variable name="formel_beschreibungstextposition">rechts</xsl:variable>		<!-- links, rechts  #neben der Formel -->
	<xsl:variable name="formel_num_format_abschnitt">1</xsl:variable>			<!--Abschnittsteil der Nummerierung -->
	<xsl:variable name="formel_num_format_pos">1</xsl:variable>					<!-- Formelteil der Nummerierung -->
	<xsl:variable name="formel_num_format_formelarray">1</xsl:variable>			<!-- Nummer innerhalb von Arrays -->
	<!-- Einheitliche Einzugsbreite bei Formelelementen -->
	
	<!-- div. Trennzeichen -->
	<xsl:variable name="video_abschnittstrennzeichen">-</xsl:variable>				<!-- jedes Zeichen -->
	<xsl:variable name="audio_abschnittstrennzeichen">-</xsl:variable>				<!-- jedes Zeichen -->
	<xsl:variable name="simulation_abschnittstrennzeichen">-</xsl:variable>			<!-- jedes Zeichen -->
	<xsl:variable name="textobjekt_abschnittstrennzeichen">. </xsl:variable>			<!-- jedes Zeichen -->
	
	
	<xsl:variable name="env_boxwidth">12cm</xsl:variable>
	<xsl:variable name="env_separator">: </xsl:variable>
	<xsl:param name="env_vspace">1.5ex</xsl:param>
	
	<xsl:variable name="fall_breite">12cm</xsl:variable>
	<xsl:variable name="norm_breite">12cm</xsl:variable>
	<xsl:variable name="kommentar_breite">12cm</xsl:variable>
	<xsl:variable name="rechtsprechung_breite">12cm</xsl:variable>
	<xsl:variable name="exkurs_breite">10cm</xsl:variable>
	<xsl:variable name="merksatz_breite">12cm</xsl:variable>
	<xsl:variable name="zusammenfassung_breite">10cm</xsl:variable>
	<xsl:variable name="abschnittstrennzeichen">-</xsl:variable>
	<xsl:variable name="trennzeichen">: </xsl:variable>			
	
	
	
		<!-- Einstellungen für Merksatz -->										   	
	<xsl:variable name="merksatz_bezeichner">Merksatz </xsl:variable> 		
	<xsl:variable name="merksatz_abschnittstrennzeichen">-</xsl:variable>		
	<xsl:variable name="merksatz_trennzeichen">: </xsl:variable>					
	<xsl:variable name="merksatz_num_format_abschnitt">2</xsl:variable>		<!--Abschnittsteil der Nummerierung -->
	<xsl:variable name="merksatz_num_format_pos">1</xsl:variable>	

	<!-- Einstellungen für Exkurs -->										   	
	<xsl:variable name="exkurs_bezeichner">Exkurs </xsl:variable> 		
	<xsl:variable name="exkurs_abschnittstrennzeichen">-</xsl:variable>		
	<xsl:variable name="exkurs_trennzeichen">: </xsl:variable>					
	<xsl:variable name="exkurs_num_format_abschnitt">2</xsl:variable>		<!--Abschnittsteil der Nummerierung -->
	<xsl:variable name="exkurs_num_format_pos">1</xsl:variable>	
	
	<!-- Einstellungen für Fall -->										   	
	<xsl:variable name="fall_bezeichner">Fall </xsl:variable> 		
	<xsl:variable name="fall_abschnittstrennzeichen">-</xsl:variable>		
	<xsl:variable name="fall_trennzeichen">: </xsl:variable>					
	<xsl:variable name="fall_num_format_abschnitt">2</xsl:variable>		<!--Abschnittsteil der Nummerierung -->
	<xsl:variable name="fall_num_format_pos">1</xsl:variable>

	<!-- Einstellungen für Kommentar -->										   	
	<xsl:variable name="kommentar_bezeichner"></xsl:variable> 		
	<xsl:variable name="kommentar_abschnittstrennzeichen"></xsl:variable>		
	<xsl:variable name="kommentar_trennzeichen"></xsl:variable>					
	<xsl:variable name="kommentar_num_format_abschnitt"></xsl:variable>		<!--Abschnittsteil der Nummerierung -->
	<xsl:variable name="kommentar_num_format_pos"></xsl:variable>
	
	<!-- Einstellungen für Norm -->										   	
	<xsl:variable name="norm_bezeichner"></xsl:variable> 		
	<xsl:variable name="norm_abschnittstrennzeichen"></xsl:variable>		
	<xsl:variable name="norm_trennzeichen"></xsl:variable>					
	<xsl:variable name="norm_num_format_abschnitt"></xsl:variable>		<!--Abschnittsteil der Nummerierung -->
	<xsl:variable name="norm_num_format_pos"></xsl:variable>
	
	<!-- Einstellungen für Rechtsprechung -->										   	
	<xsl:variable name="rechtsprechung_bezeichner">Rechtsprechung </xsl:variable> 		
	<xsl:variable name="rechtsprechung_abschnittstrennzeichen">-</xsl:variable>		
	<xsl:variable name="rechtsprechung_trennzeichen">: </xsl:variable>					
	<xsl:variable name="rechtsprechung_num_format_abschnitt">2</xsl:variable>		<!--Abschnittsteil der Nummerierung -->
	<xsl:variable name="rechtsprechung_num_format_pos">1</xsl:variable>

	<!-- Einstellungen für Zusammenfassung -->										   	
	<xsl:variable name="zusammenfassung_bezeichner">Zusammenfassung </xsl:variable> 		
	<xsl:variable name="zusammenfassung_abschnittstrennzeichen">-</xsl:variable>		
	<xsl:variable name="zusammenfassung_trennzeichen">: </xsl:variable>					
	<xsl:variable name="zusammenfassung_num_format_abschnitt">2</xsl:variable>		<!--Abschnittsteil der Nummerierung -->
	<xsl:variable name="zusammenfassung_num_format_pos">1</xsl:variable>	
	
	<!-- Einstellungen für Tabellen -->											<!-- Mögliche Werte-->
	<xsl:variable name="tab_bezeichner">Tabelle </xsl:variable>				<!-- Tab., Tabelle, etc. -->
	<xsl:variable name="tab_abschnittstrennzeichen">/</xsl:variable>			<!-- jedes Zeichen -->
	<xsl:variable name="tab_trennzeichen">: </xsl:variable>					<!-- 	"	" -->
	<xsl:variable name="tab_num_format_abschnitt">1</xsl:variable>		<!--Abschnittsteil der Nummerierung -->
	<xsl:variable name="tab_num_format_pos">1</xsl:variable>		
	<xsl:variable name="continued_from_previous_page">Fortsetzung von vorhergehender Seite</xsl:variable>				<xsl:variable name="continued_on_next_page">Fortsetzung auf naechster Seite</xsl:variable>
	<xsl:variable name="max.table.width">14</xsl:variable> <!--Maximale Breite einer Tabelle  in cm -->

	
	<xsl:variable name="math_abschnittstrennzeichen">-</xsl:variable>		
	<xsl:variable name="math_trennzeichen">: </xsl:variable>					
	<xsl:variable name="math_num_format_abschnitt">2</xsl:variable>		<!--Abschnittsteil der Nummerierung -->
	<xsl:variable name="math_num_format_pos">1</xsl:variable>
	
	<xsl:variable name="axiom_bezeichner">Axiom </xsl:variable> 

	<xsl:variable name="annahme_bezeichner">Annahme </xsl:variable> 
	
	<xsl:variable name="bemerkung_bezeichner">Bemerkung </xsl:variable>	
	
	<xsl:variable name="beweis_bezeichner">Beweis </xsl:variable>
		
	<xsl:variable name="corollar_bezeichner">Corollar </xsl:variable>
		
	<xsl:variable name="definition_bezeichner">Definition </xsl:variable> 		
	
	<xsl:variable name="erlaeuterung_bezeichner"></xsl:variable> 		
		
	<xsl:variable name="folgerung_bezeichner">Folgerung </xsl:variable>
				
	<xsl:variable name="hilfssatz_bezeichner">Hilfssatz </xsl:variable>
		
	<xsl:variable name="hauptsatz_bezeichner">Hauptsatz </xsl:variable>
			
	<xsl:variable name="lemma_bezeichner">Lemma </xsl:variable>
	
	<xsl:variable name="loesung_bezeichner">Loesung </xsl:variable>
			
	<xsl:variable name="problem_bezeichner">Problem </xsl:variable>

	<xsl:variable name="proposition_bezeichner">Proposition  </xsl:variable>
		
	<xsl:variable name="regel_bezeichner">Regel </xsl:variable>
		
	<xsl:variable name="satz_bezeichner">Satz </xsl:variable>
		
	<xsl:variable name="theorem_bezeichner">Theorem </xsl:variable>
			
	
	<!-- weitere Querverweisersetzungen -->
	<xsl:variable name="qaudio_bezeichner1">(hören: </xsl:variable>
	<xsl:variable name="qaudio_bezeichner2">)</xsl:variable>
	<xsl:variable name="qtext_bezeichner1">(Textblock <!-- Nummer wird immer eingefügt --></xsl:variable>
	<xsl:variable name="qtext_bezeichner2">)<!-- Nummer wird immer eingefügt --></xsl:variable>
	<xsl:variable name="qvideo_bezeichner1">(Video: <!-- Nummer wird immer eingefügt --></xsl:variable>
	<xsl:variable name="qvideo_bezeichner2">)</xsl:variable>
	<xsl:variable name="qsimu_bezeichner1">(siehe Simulation <!-- Nummer wird immer eingefügt --></xsl:variable>
	<xsl:variable name="qsimu_bezeichner2">)</xsl:variable>
	<xsl:variable name="qtab_bezeichner1">(Tabelle: <!-- Nummer wird immer eingefügt --></xsl:variable>
	<xsl:variable name="qtab_bezeichner2">)</xsl:variable>
	<xsl:variable name="qformel_bezeichner1">(Gleichung: <!-- Nummer wird immer eingefügt --></xsl:variable>
	<xsl:variable name="qformel_bezeichner2">)</xsl:variable>
	<xsl:variable name="qabschnitt_bezeichner1">(Abschnitt: <!-- Nummer wird immer eingefügt --></xsl:variable>
	<xsl:variable name="qabschnitt_bezeichner2">)</xsl:variable>

	<xsl:variable name="qdefault_bezeichner">(siehe hier)</xsl:variable>

	<!-- Einzugsbreiten der mathematischen Elemente -->
	<!--xsl:variable name="matheinzug">90</xsl:variable-->							<!-- integer(Pixel): z.B. 90 -->
	
	<!-- Art der Nummerierung bei numerierten Listen werden in der CSS eingestellt -->
	<!-- Art der Sonderzeichen bei Aufzählungslisten werden in der CSS eingestellt -->
	

</xsl:stylesheet>
