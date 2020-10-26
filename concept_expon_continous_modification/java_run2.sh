#!/bin/bash


#cd /home/matlab/Viki
#
#comsol PHYNDI (myPHYNDI) + mySUSE + PHYNDI2
COMSOL=/opt/comsol43/bin/comsol


#comsol kissrv
#COMSOL=/d/inst/viki/comsol/comsol_install/comsol43/bin/comsol


c_kcl_base=0
c_kcl_acid=0
U0=10;

#c_kcl_base_t=0.06

c_kcl_base_t[0]=0.06
c_kcl_base_t[1]=0.015
c_kcl_base_t[2]=0.022
c_kcl_base_t[3]=0.02
c_kcl_base_t[4]=0.018
c_kcl_base_t[5]=0.024
c_kcl_base_t[6]=0.04
c_kcl_base_t[7]=0.07

c_kcl_base_t=0.06

#MEsh settings
#
#
function_type=61
ppb1_x=0.194
ppb2_x=0.2

ratio=0.8
x_kezdeti_pont=0.191
x_vegpont=0.203
delta_x=0.01
mesh_adap_faktor=0.002

N_mesh[0]=500
N_mesh[1]=1000
N_mesh[2]=2000
N_mesh[3]=3000
N_mesh[4]=4000
N_mesh[5]=5000
N_mesh[6]=7500
N_mesh[7]=10000 


U0_t=10;

c_kcl_acid_t[0]=0.001
c_kcl_acid_t[1]=0.002
c_kcl_acid_t[2]=0.005
c_kcl_acid_t[3]=0.01
c_kcl_acid_t[4]=0.015
c_kcl_acid_t[5]=0.02
c_kcl_acid_t[6]=0.025
c_kcl_acid_t[7]=0.03
c_kcl_acid_t[8]=0.035
c_kcl_acid_t[9]=0.04
c_kcl_acid_t[10]=0.045
c_kcl_acid_t[11]=0.05
c_kcl_acid_t[12]=0.055
c_kcl_acid_t[13]=0.08
c_kcl_acid_t[14]=0.25
c_kcl_acid_t=0

#atirni
#DIR=/home/matlab/acidbase_transient_data/ugras/mph_bin/

### UGRAS Adaptiv MESH-hez
#for kissrv
#DIR=/d/inst/viki/acidbase_transient_data/ugras/mph_bin/

#for phyndi (myPHYNDI)
#DIR=/home/matlab/acidbase_transient_data/ugras_adaptiv_mesh/mph_bin/

#for phyndi2 (under GIBBS)
#DIR=/home/comsol/acidbase_transient_data/impulzus/mph_bin/
DIR=/home/comsol/acidbase_transient_data/ugras_adaptiv_mesh/mph_bin/


for i in {7..7}
do

#itt ellenorizni kell, hogy az mph_bin-en belul letre vannak-e hozva az alkonyvtarak, ahova ezt menteni kell...
#ugyanolyan hierarchianak megfeleloen, mint a current es a profile
#attol fuggoen hogyan megy majd a bontas, lehet majd [i] indexekkel jatszani...     

DIRECTORY=${DIR}${U0}_V
echo $DIRECTORY

if [ ! -d "$DIRECTORY" ] 
then
  	echo "DIR DOES NOT EXIST!"
	#DIR must be done.
	mkdir ${DIRECTORY}
else
	echo "DIR EXISTS"
	#Don't do anything with the dir, just check for SUBDIR.	
fi


DIRECTORY=${DIRECTORY}/c_kcl_b_${c_kcl_base}/
echo ${DIRECTORY}

if [ ! -d "$DIRECTORY" ] 
then
	echo "SUBDIR DOES NOT EXIST!"
	#DIR must be done.
	mkdir ${DIRECTORY}
else
	echo "SUBDIR EXISTS"
fi


#MPH NEVE
MPH=ugr_c_kcl_${c_kcl_base}_${c_kcl_acid}_c_kcl_t_${c_kcl_base_t}_${c_kcl_acid_t}_U_${U0}_

NAME=${DIRECTORY}${MPH}$(date +%Y%m%d_%T).mph

#for kissrv
#TMP=/d/inst/viki/tmp/

#for phyndi (myPHYNDI)
#TMP=/home/matlab/

#for phyndi2 (under GIBBS)
#TMP=/home/comsol/comsol_trash/

#Itt meg a vegere lehet biggyeszteni:
#   -tmpdir ${TMP}

#FUTTATO
${COMSOL} batch -Xmx6g -graphics -3drend sw -inputfile $1 ${c_kcl_base} ${c_kcl_acid} ${U0} ${c_kcl_base_t} ${c_kcl_acid_t} ${U0_t} ${function_type} ${ppb1_x}  ${ppb2_x} ${ratio} ${x_kezdeti_pont} ${x_vegpont} ${N_mesh[i]} ${delta_x} ${mesh_adap_faktor} -outputfile ${NAME}


done
