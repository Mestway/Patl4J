#!/bin/bash

#       ********************************************************************
#		$Id: install_suse.sh,v 1.2 2007/02/16 11:53:11 hemmer Exp $
#		********************************************************************
#		*******************************************************************************
#		| openFuXML open source                                                       |
#		*******************************************************************************
#		| Copyright (c) 2002-2006 openFuXML open source, University of Hagen          |
#		|                                                                             |
#		| This program is free software; you can redistribute it and/or               |
#		| modify it under the terms of the GNU General Public License                 |
#		| as published by the Free Software Foundation; either version 2              |
#		| of the License, or (at your option) any later version.                      |
#		|                                                                             |
#		| This program is distributed in the hope that it will be useful,             |
#		| but WITHOUT ANY WARRANTY; without even the implied warranty of              |
#		| MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the               |
#		| GNU General Public License for more details.                                |
#		|                                                                             |
#		| You should have received a copy of the GNU General Public License           |
#		| along with this program; if not, write to the Free Software                 |
#		| Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. |
#		*******************************************************************************
#		******************************************************************** 
  




  rcdir='/etc/rc.d/rc'


# Formatierte Ausgabe des Namens einer neuen Untersektion des Skriptablaufs
function newsection()
{
  echo ""
  echo ""
  echo "---------------------------------------------------------------------"
  echo $1
  echo "---------------------------------------------------------------------"
  echo ""
  echo ""
}


# ----------------------------------------------
# BEGINN DES EIGENTLICHEN SKRIPTS
# ----------------------------------------------

newsection "openFuXML @@@openfuxml-version@@@ Installation"


#Benutzer wird angelegt
echo "Lege Benutzer fuxml mit Passwort fuxml und Home Verzeichnis an..."
echo ""
# Das Passwort ist ein MD5 Hash von fuxml
useradd fuxml -m -p '$1$7EUse1$CVELvd0jB1/nP2OoIdDUl.'
echo ""
echo ""

newsection "Überprüfe/Installiere benötigte Abhängigkeiten..."

yast -i java-1_5_0-sun-devel
yast -i tetex
yast -i te_latex
yast -i ghostscript-x11
yast -i ImageMagick
yast -i samba






newsection "Kopiere und Entpacke Dateien, setze Rechte und konfiguriere... "

cp ./openFuXML-System-@@@openfuxml-version@@@s.tgz /home/fuxml
#rm openFuXML-System-@@@openfuxml-version@@@s.tgz
cp ./uninstall.sh /home/fuxml
#rm unistall.sh
chmod +x /home/fuxml/uninstall.sh
cd /home/fuxml
su fuxml -c "tar -xzf openFuXML-System-@@@openfuxml-version@@@s.tgz"
cd openFuXML-@@@openfuxml-version@@@
su fuxml -c "chmod +x openFuXML.sh"
su fuxml -c "chmod +x openFuXML"
su fuxml -c "chmod +x lib/apache-ant-@@@ant-version@@@/bin/antRun"

newsection "Installiere tex4ht bzw. erstelle Backup der tex4ht Kontrolldateien"

rpm -i /home/fuxml/openFuXML-@@@openfuxml-version@@@/lib/-tex4ht-@@@tex4ht-version@@@.i586.rpm

if [ -f /usr/share/texmf/tex/generic/tex4ht/tex4ht.env ]; then
  cp /usr/share/texmf/tex/generic/tex4ht/tex4ht.env /usr/share/texmf/tex/generic/tex4ht/tex4ht_openFuXML_backup.env
else 
  echo "Backup nicht nötig, Datei nicht vorhanden.."
fi

if [ -f /usr/bin/htlatex ]; then
  cp /usr/bin/htlatex /usr/bin/htlatex_openFuXML_backup.env
else 
  echo "backup nicht nötig, Datei nicht vorhanden"
fi

cp applications/fuxml/formats/math/ht4t_files/tex4ht_fuxml.env /usr/share/texmf/tex/generic/tex4ht/tex4ht.env
cp applications/fuxml/formats/math/ht4t_files/htlatex /usr/bin/htlatex
chmod +x /usr/bin/htlatex

cp openFuXML /etc/init.d

echo "[openfuxml]
        comment = openFuXML-Share
        path = /home/fuxml/openFuXML-@@@openfuxml-version@@@/share
	writeable = yes
	browseable = yes" >> /etc/samba/smb.conf

newsection "Konfiguration des Samba Benutzers fuxml"

smbpasswd -a fuxml

if [ $1x = "noauto" ]; then
  echo""
  echo""
else
   newsection "Konfiguriere Runlevel Definitionen für automatischen Start/Stop..."
   echo ""
   cd ${rcdir}2.d
   ln -s ../init.d/openFuXML S22openFuXML
   cd ${rcdir}5.d
   ln -s ../init.d/openFuXML S22openFuXML
   cd ${rcdir}0.d
   ln -s ../init.d/openFuXML K22openFuXML
   echo ""
   echo ""
fi

newsection "Beende Installation, starte openFuXML Server..."

rm /home/fuxml/openFuXML-System-@@@openfuxml-version@@@s.tgz
cd /home/fuxml/openFuXML-@@@openfuxml-version@@@/
/etc/init.d/openFuXML start
#rm $0
