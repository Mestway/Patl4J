#!/bin/bash

DIRNAME=`dirname $0`
FUXML_HOME=`cd $DIRNAME; pwd`

echo $FUXML_HOME

export ANT_HOME=$FUXML_HOME/lib/apache-ant-@@@ant-version@@@

#Erst Test mit alter Version
#export LOG4J=$FUXML_HOME/lib/@@@log4j@@@
export SAXON=$FUXML_HOME/lib/@@@saxon@@@

#export CLASSPATH=$CLASSPATH:$SAXON:$ANT_HOME/lib/ant.jar:$ANT_HOME/lib/ant-launcher.jar:$LOG4J:$FUXML_HOME/lib/@@@openFuXML.simple@@@
export CLASSPATH=$CLASSPATH:$SAXON:$ANT_HOME/lib/ant.jar:$ANT_HOME/lib/ant-launcher.jar:$FUXML_HOME/lib/@@@openFuXML.simple@@@

# start or stop the server
case "$1" in
	start) echo -n "Starting openFuXML Produktion Server"
		nohup java -jar $FUXML_HOME/lib/@@@openFuXML.simple@@@ >> $FUXML_HOME/logs/console.log 2>>$FUXML_HOME/logs/console.log &
		echo $! > $FUXML_HOME/fuxml.pid
		echo "."
		;;
	stop) echo -n "Stopping openFuXML Produktion Server"
		kill -TERM `cat $FUXML_HOME/fuxml.pid`
		echo "."
		;;
	*) echo "Usage: ./openfuxml.sh start|stop"
		exit 1 
		;;
	esac
exit 0


