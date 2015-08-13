#!/bin/sh

#       ********************************************************************
#		$Id: deb-create.sh,v 1.9 2007/03/02 13:17:25 hemmer Exp $
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
  


# Dieses Skript erstellt aus den generierten Dateien und Verzeichnissen ein .deb Paket
# und bereitet dieses im zweiten Schritt als "Debian Repository" auf
# Danach wird der Pfad /usr/share/openfuxml als lokales Repository der sources.list
# hinzugefügt und die apt-get Datenbank aktualisiert.
# apt-get install openfuxml  und  apt-get remove openfuxml
# sorgen dann für die (De-)Installation.

#Installieren der benötigten Pakete für Etch
apt-get -y install binutils
apt-get -y install dpkg-dev
# Erzeugen des Pakets
dir=`pwd`
cd control
tar cfv control.tar *
gzip control.tar
mv control.tar.gz ../
echo "Steuerungsdateien vorbereitet..."
cd ..
cd data
tar cfv data.tar *
gzip data.tar
mv data.tar.gz ../
cd ..
echo "Installationsdaten vorbereitet..."
mkdir /usr/share/openfuxml/
mkdir /usr/share/openfuxml/binary
ar rc /usr/share/openfuxml/binary/openfuxml-@@@openfuxml-version@@@.deb debian-binary control.tar.gz data.tar.gz
echo "Installationspaket im /usr/share/openfuxml/binary Verzeichnis erstellt..."

# Löschen der Temporären Dateien
#cd $dir
#rm data.tar.gz
#rm control.tar.gz
#rm -r data
#rm -r control
#rm debian-binary
#echo "System bereinigt..."
echo "--------------------------------------------------------------------------"
echo "Paket für openfuxml wurde erstellt..."

# Erstellen der Packages.gz für das Repository
cd /usr/share/openfuxml/
dpkg-scanpackages binary /dev/null | gzip -9 > binary/Packages.gz
echo "deb file:///usr/share/openfuxml binary/" >> /etc/apt/sources.list
echo "Lokales Repository wurde unter /usr/share/openfuxml eingerichtet..."

apt-get update
echo "Datenbank des Paketverwaltungssystems aktualisiert..."

echo ""
echo "Das Paket kann nun durch Eingabe von"
echo "apt-get install openfuxml"
echo "installiert werden. Entfernt werden kann es mit
echo "apt-get remove openfuxml"
echo ""
echo "Viel Spass bei der Benutzung von openFuXML!"

#rm $0