#!/bin/bash

FUXML_HOME=`pwd`

echo $FUXML_HOME

export ANT_HOME=$FUXML_HOME/lib/apache-ant-@@@ant-version@@@
export SAXON=$FUXML_HOME/lib/@@@saxon@@@

export CLASSPATH=$CLASSPATH:$SAXON:$ANT_HOME/lib/ant.jar:$ANT_HOME/lib/ant-launcher.jar:$FUXML_HOME/lib/@@@openFuXML.simple@@@

$FUXML_HOME/lib/apache-ant-@@@ant-version@@@/bin/ant -buildfile $FUXML_HOME/applications/fuxml/formats/latexpdf/build.xml -Dilona.home=$FUXML_HOME/ -Dilona.contentstore=$FUXML_HOME/share/repository/fuxml -Dsrc.dir=$FUXML_HOME/share/repository/fuxml/$1/ -Dilona.output=$FUXML_HOME/share/output/fuxml -Dxsltemp.dir=$FUXML_HOME/share/output/fuxml/$1/xsl -Dapplication=fuxml -Dcoursename=$1 -Dproject=$1 -Dmasterfile=$2.xml -Dformat=latexpdf -Dusername=hemmer >> fuxml.log

