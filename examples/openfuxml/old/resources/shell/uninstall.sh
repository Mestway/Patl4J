#!/bin/bash

#       ********************************************************************
#		$Id: uninstall.sh,v 1.4 2007/04/22 15:54:33 hemmer Exp $
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



# Hier werden die für debian Sarge geltenden Werte für die Variablen gesetzt,
# um die Variablen zu initialisieren. Später werden sie dann ggf. geändert, um
# sie an andere Distributionen bzw. Versionen anzupassen.
function initvariables()
{
  instcall='apt-get remove'
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

newsection "openFuXML Deinstallationsroutine"

/etc/init.d/openFuXML stop
initvariables
setvariables

newsection "Entferne Abhängigkeiten..."

installpackage $latex
installpackage $tex4ht
installpackage $gs
installpackage $imagemagick
installpackage $samba
installpackage $sun

newsection "Entferne Skripte und stelle Einstellungen wieder her"

 rm /etc/init.d/openFuXML
 rm ${rcdir}2.d/S22openFuXML
 rm ${rcdir}5.d/S22openFuXML
 rm ${rcdir}0.d/K22openFuXML


if [ -f /usr/bin/htlatex/htlatex ]; then
   cp /usr/bin/htlatex_openFuXML_backup /usr/share/htlatex
   rm /usr/bin/htlatex_openFuXML_backup
fi

if [ -f /usr/share/texmf/tex/generic/tex4ht/tex4ht.env ]; then
   cp /usr/share/texmf/tex/generic/tex4ht/tex4ht_openFuXML_backup.env /usr/share/texmf/tex/generic/tex4ht/tex4ht.env
   rm /usr/share/texmf/tex/generic/tex4ht/tex4ht_openFuXML_backup.env
fi

if [ -f /etc/samba/smb.conf ]; then
   cp /etc/samba/smb.conf /etc/samba/smb_openFuXML_backup.conf
   sed '/openfuxml/,/browseable/d' /etc/samba/smb.conf > /etc/samba/smb_without_openfuxml.conf
   cp /etc/samba/smb_without_openfuxml.conf /etc/samba/smb.conf
fi

if [ $1 = "userfiles" ]; then
  newsection "Entferne Benutzer fuxml mit all seinen Dateien aus /home/fuxml"
  userdel -r fuxml

else
  newsection "Entferne Benutzer fuxml, die Daten des Users bleiben erhalten."
  userdel fuxml

fi
