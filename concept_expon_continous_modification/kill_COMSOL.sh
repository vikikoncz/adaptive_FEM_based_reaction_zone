#!/bin/sh
#ki kell nyirnia a terminalbol inditott harom comsol verziot
#meg kell tudni a PID-et, amelyik sorra a java_run2.sh, comsol es comsollaucher illeszkedik

#ps | grep java_run2.sh | grep ^\D
#ps | grep comsol

#ps | grep gedit | sed -n -e 's/^.*stalled: //p'
#MY_LINE=$(ps | grep gedit)
#echo ${MY_LINE:0:5} 
#fenti sor lehet h csak bash-ben mukodik


MY_LINE=$(ps | grep java_run2.sh | sed 's/^\(.....\).*/\1/;q')
echo ${MY_LINE}
kill -9 ${MY_LINE} 


MY_LINE=$(ps | grep comsollauncher | sed 's/^\(.....\).*/\1/;q')
#MY_LINE=$(ps | grep gedit | sed 's/^\(([0-9])....\).*/\1/;q')
echo ${MY_LINE}
kill -9 ${MY_LINE}


MY_LINE=$(ps | grep comsol | sed 's/^\(.....\).*/\1/;q')
echo ${MY_LINE}
kill -9 ${MY_LINE} 

echo "COMSOL-JAVA IS KILLED!"



