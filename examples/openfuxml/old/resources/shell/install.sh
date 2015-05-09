#!/bin/bash

#       ********************************************************************
#		$Id: install.sh,v 1.10 2007/04/22 15:54:33 hemmer Exp $
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
  


# ----------------------------------------------
# DEFINITION VON FUNKTIONEN UND VARIABLEN
# ----------------------------------------------


# Das JDK wird für openFuXML benötigt und es wird daher überprüft, ob in
# der Sources.list ein non-free Repository eingetragen ist (was bei einer Standard
# Debian Installation nicht der Fall ist) und ansonsten hinzugefügt, da sich
# dort das Sun JDK Paket befindet...

nonfree=`cat /etc/apt/sources.list | grep non-free`

if [ "$nonfree" == "" ]; then
  echo "deb http://ftp.de.debian.org/debian/ sarge non-free" >> /etc/apt/sources.list
  apt-get update
fi
 
# Hier werden die für debian Sarge geltenden Werte für die Variablen gesetzt,
# um die Variablen zu initialisieren. Später werden sie dann ggf. geändert, um
# sie an andere Distributionen bzw. Versionen anzupassen.
function initvariables()
{ 
  instcall='apt-get -y install'
  rcdir='/etc/rc'
  latex='tetex-base'
  latex2='tetex-extra'
  latex3='latex-ucs'
  tex4ht='tex4ht'
  gs='gs'
  imagemagick='imagemagick'
  samba='samba'
  sun='sun-java5-jdk'
}

# Hier wird die Distribution ermittelt und die Variablen ggf. angepasst.
function setvariables()
{
  if [ -f /etc/debian_version ]; then
     echo "Sie nutzen Debian in der Version "
     cat /etc/debian_version
     echo "Konfiguriere openFuXML für Debian"
     echo ""
  fi

  if [ -f /etc/SuSE-release ]; then
     echo "Sie nutzen "
     cat /etc/SuSE-release
     echo "Konfiguriere openFuXML für SuSE Linux"
     echo ""
     $instcall='yast -i'
     $rcdir='/etc/rc.d/rc'
  fi
}

# Installieren von Paketen
function installpackage()
{
#   if [$latex2 == "none"]; then
#    $instcall $latex
#      else
#    $instcall $latex
#    $instcall $latex2
#   fi


  echo ""
  echo $1
  echo ""
  $instcall $1
  echo ""
}

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

checkjava
initvariables
setvariables

#Benutzer wird angelegt
echo "Lege Benutzer fuxml mit Passwort fuxml und Home Verzeichnis an..."
echo ""
# Das Passwort ist ein MD5 Hash von fuxml
useradd fuxml -m -p '$1$7EUse1$CVELvd0jB1/nP2OoIdDUl.'
echo ""
echo ""

newsection "Überprüfe/Installiere benötigte Abhängigkeiten..."

installpackage $sun
installpackage $latex
installpackage $latex2
installpackage $latex3
installpackage $tex4ht
installpackage $gs
installpackage $imagemagick
installpackage $samba

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

newsection "Erstelle Backup der Tex for Hypertext Kontrolldateien"

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
