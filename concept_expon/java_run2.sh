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
function_type=56
ppb1_x=0.1965
ppb2_x=0.1985

ratio=0.8
x_kezdeti_pont=0.196
x_vegpont=0.199
delta_x=0.01
mesh_adap_faktor=0.0005

N_mesh[0]=500
N_mesh[1]=1000
N_mesh[2]=2000
N_mesh[3]=3000
N_mesh[4]=4000
N_mesh[5]=5000
N_mesh[6]=7500
N_mesh[7]=10000 


#meg egyszer futtassuk le ezt
function_type[0]=1
#ppb1_x[0]=0.1925
#ppb2_x[0]=0.2025
ratio[0]=0.8
#x_kezdeti_pont[0]=0.192
#x_vegpont[0]=0.203
delta_x[0]=0.01
mesh_adap_faktor[0]=0.002
N_mesh[0]=10000

function_type[1]=12
#ppb1_x[1]=0.192
#ppb2_x[1]=0.203
ratio[1]=0.8
#x_kezdeti_pont[1]=0.1915
#x_vegpont[1]=0.2035
delta_x[1]=0.01
mesh_adap_faktor[1]=0.0005
N_mesh[1]=300


function_type[2]=11
#ppb1_x[2]=0.1915
#ppb2_x[2]=0.2035
ratio[2]=0.8
#x_kezdeti_pont[2]=0.191
#x_vegpont[2]=0.204
delta_x[2]=0.01
mesh_adap_faktor[2]=0.0005
N_mesh[2]=400

function_type[3]=10
#ppb1_x[3]=0.191
#ppb2_x[3]=0.204
ratio[3]=0.8
#x_kezdeti_pont[3]=0.1905
#x_vegpont[3]=0.2045
delta_x[3]=0.01
mesh_adap_faktor[3]=0.0005
N_mesh[3]=600


function_type[4]=9
#ppb1_x[4]=0.1905
#ppb2_x[4]=0.2045
ratio[4]=0.8
#x_kezdeti_pont[4]=0.19
#x_vegpont[4]=0.205
delta_x[4]=0.01
mesh_adap_faktor[4]=0.0005
N_mesh[4]=800

function_type[5]=8
#ppb1_x[5]=0.19
#ppb2_x[5]=0.205
ratio[5]=0.8
#x_kezdeti_pont[5]=0.1895
#x_vegpont[5]=0.2055
delta_x[5]=0.01
mesh_adap_faktor[5]=0.0005
N_mesh[5]=1000


function_type[6]=7
#ppb1_x[6]=0.1935
#ppb2_x[6]=0.2015
ratio[6]=0.8
#x_kezdeti_pont[6]=0.193
#x_vegpont[6]=0.202
delta_x[6]=0.01
mesh_adap_faktor[6]=0.0005
N_mesh[6]=1500

function_type[7]=6
#ppb1_x[7]=0.193
#ppb2_x[7]=0.202
ratio[7]=0.8
#x_kezdeti_pont[7]=0.1925
#x_vegpont[7]=0.2025
delta_x[7]=0.01
mesh_adap_faktor[7]=0.0005
N_mesh[7]=2500

x_kezdeti_pont=0.192
x_vegpont=0.202
ppb1_x=0.194
ppb2_x=0.2



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


for i in {0..0}
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
MPH=ugr_c_kcl_${c_kcl_base}_${c_kcl_acid}_c_kcl_t_${c_kcl_base_t}_${c_kcl_acid_t}_U_${U0}_${function_type[i]}_

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
${COMSOL} batch -Xmx6g -graphics -3drend sw -inputfile $1 ${c_kcl_base} ${c_kcl_acid} ${U0} ${c_kcl_base_t} ${c_kcl_acid_t} ${U0_t} ${function_type[i]} ${ppb1_x}  ${ppb2_x} ${ratio[i]} ${x_kezdeti_pont} ${x_vegpont} ${N_mesh[i]} ${delta_x[i]} ${mesh_adap_faktor[i]} -outputfile ${NAME}


done
