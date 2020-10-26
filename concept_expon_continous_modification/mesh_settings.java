//import com.comsol.model.*;
//import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;

/*Ez fogja tudni a tenyleges mesh-t letrehozni

5 szakasz : kozepen ket egyenes m, M a meredekseg


*/

public class mesh_settings{


	//double ppb1_x=0.142; //jobboldali
	double ppb1_x;
	//double ppb2_x=0.2;	//baloldali
	double ppb2_x;
	
	double ppb1_x_old;
	double ppb2_x_old;

	//int N = 100000; //pontok szama -> ez csak a racsfuggveny rajzolashoz
	//int N = 18000;
	int N;
	
								//9999: ez a function_type legyen	
	//int function_type = 9999; //EXPONENTIAL -> mert ehhez van kedvem
	int function_type;	
	//double ratio = 0.8;
	double ratio;    
	
	//int n = 3;  //hatvany kitevo; csak paratlan lehet

	//double x_kezdeti_pont=0.14;    
	//double x_vegpont=0.1905;
	//double x_vegpont=0.202;

	double x_kezdeti_pont;    
	//double x_vegpont=0.1905;
	double x_vegpont;
	
	double x_kezdeti_pont_old;
	double x_vegpont_old;
	
//nem ezt hasznaljuk, hanem a ratio-t
	//double R = 0.9;   //ebbol lehet m, es M-et kiszamitani; pontok hany szazaleka keruljon be a suru mesh-es zonaba kb.; ez nem a ratio volt eddig?????

	//double r = 0.01; //ici-pici kor sugara, amivel lekerekul
	//double delta_x = 0.01;  //kb. ekkora lehet az atmeneti zona szelessege, ami alatt a derivaltnak erteket kell valtoztatnia	
	double delta_x;
	double mesh_adap_faktor;

/*
mesh_settings mesh_settings = new mesh_settings(function_type, ppb1_x, ppb2_x, ratio, x_kezdeti_pont, x_vegpont, N_mesh);
*/

	mesh_settings(int function_type, double ppb1_x, double ppb2_x, double ratio, double x_kezdeti_pont, double x_vegpont, int N_mesh, double delta_x, double mesh_adap_faktor){
		this.function_type = function_type;
		this.ppb1_x = ppb1_x;
		this.ppb2_x = ppb2_x;
		this.ratio = ratio;
		this.x_kezdeti_pont = x_kezdeti_pont;
		this.x_vegpont = x_vegpont;	
		this.N = N_mesh; 
		this.delta_x = delta_x;
		this.mesh_adap_faktor = mesh_adap_faktor;
	}

	void reset_mesh_parameters(double ppb1_x, double ppb2_x, double x_kezdeti_pont, double x_vegpont, double ratio, double delta_x, double mesh_adap_faktor)
	{	
		//Archive mesh_settings
		this.ppb1_x_old = this.ppb1_x;
		this.ppb2_x_old = this.ppb2_x;
		this.x_kezdeti_pont_old=this.x_kezdeti_pont;
		this.x_vegpont_old=this.x_vegpont;

		this.ppb1_x=ppb1_x;
		this.ppb2_x=ppb2_x;
		this.x_kezdeti_pont=x_kezdeti_pont;
		this.x_vegpont=x_vegpont;

		this.ratio=ratio;
		this.delta_x=delta_x;
		this.mesh_adap_faktor=mesh_adap_faktor;

		
	}


	
}
