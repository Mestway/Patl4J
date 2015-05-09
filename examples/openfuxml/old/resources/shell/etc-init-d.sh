#!/bin/bash

FUXML_DIR=/home/fuxml/openFuXML-@@@openfuxml-version@@@
FUXML_USER=fuxml

# start or stop the server
case "$1" in
	start) echo -n "Calling openFuXML-Start"
		su - $FUXML_USER -c "$FUXML_DIR/openFuXML.sh start"
		;;
	stop) echo -n "Calling openFuXML-Stop"
		$FUXML_DIR/openFuXML.sh stop
		;;
	*) echo "Usage: /etc/init.d/openfuxml start|stop"
		exit 1
		;;
	esac
exit 0


