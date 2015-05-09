#!/bin/bash

#       ********************************************************************
#		$Id: uninstall_suse.sh,v 1.1 2007/02/13 23:38:22 hemmer Exp $
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


  userdel -r fuxml

