#!/bin/bash
#ez az en verziom
#COMSOL=/opt/comsol40a/bin/comsol
#JDK=/usr/lib64/jvm/java-1.6.0-sun/
#cd /home/matlab/Viki

#torolni .classt, ellenorzom, hogy letrejott-e

#rm -f $1.class

#${COMSOL} compile -jdkroot ${JDK} $1.java

#echo
#if [ -f $1.class ]
#then
#	echo "OK, done"
#else
#	echo "ERROR"
#fi

#Laci verzioja
if [ $# -ne 1 ]
then
	echo "Error: The number of arguments does not equal to 1."
	echo "Usage: $0 file[.java]"
	exit 1
fi

FILENAME=${1%.*}
if [ ! -f ${FILENAME}.java ]
then
	echo "${FILENAME}.java: No such file"
	exit 1
fi

#4.1 version
#COMSOL=/opt/comsol41/bin/comsol  
#JDK=/usr/lib64/jvm/java-1.6.0-sun/  

#4.2 version
#COMSOL=/opt/comsol42/bin/comsol
#Ez az openSUSE javajanak a helye szerintem. Kellene a Debianos is.
#JDK=/usr/lib64/jvm/java-1.6.0-sun/  SUSE Java 
#JDK=/usr/lib64/jvm/java-6-sun-1.6.0.26/	Debian Java

#4.2a version
#COMSOL=/opt/comsol42a/bin/comsol
#JDK=/usr/lib64/jvm/java-6-sun-1.6.0.26/
#JDK=/usr/lib/jvm/java-6-openjdk-amd64

#JDK for PHYNDI2 (under GIBBS)
JDK=/usr/lib/jvm/java-6-oracle

#4.3 version for PHYNDI and MY_SUSE and PHYNDI2
COMSOL=/opt/comsol43/bin/comsol


rm -f ${FILENAME}.class

#${COMSOL} compile -jdkroot ${JDK} ${FILENAME}.java
${COMSOL} compile -jdkroot ${JDK} *.java

echo
if [ -f ${FILENAME}.class ]
then
	echo "OK, done"
else
	echo "ERROR"
fi
