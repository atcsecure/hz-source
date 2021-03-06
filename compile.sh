#!/bin/sh
CP=conf/:classes/:lib/*
SP=src/java/

/bin/mkdir -p classes/

javac -sourcepath $SP -classpath $CP -d classes/ src/java/nhz/*.java src/java/nhz/*/*.java || exit 1

/bin/rm -f nhz.jar 
jar cf nhz.jar -C classes . || exit 1
/bin/rm -rf classes

echo "nhz.jar generated successfully"
