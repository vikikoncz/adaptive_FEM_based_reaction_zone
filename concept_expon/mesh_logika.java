//import com.comsol.model.*;
//import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;

/*
Lekert H*OH es egyeb adatokkal ez talaja ki tulajdonkeppen az uj mesh_settings parametereket

*/

public class mesh_logika{
	
	double ppb1_x;
	double ppb2_x;
	double x_kezdeti_pont;
	double x_vegpont;
	datas_for_new_mesh datas_for_new_mesh;

	double ppb1_hoh=0;  //baloldali
	double ppb2_hoh=0;  //jobboldali	
	double mesh_adap_faktor;

	mesh_logika(double ppb1_x, double ppb2_x, double x_kezdeti_pont, double x_vegpont, datas_for_new_mesh datas_for_new_mesh, double mesh_adap_faktor){
		this.ppb1_x=ppb1_x;
		this.ppb2_x=ppb2_x;
		this.x_kezdeti_pont=x_kezdeti_pont;
		this.x_vegpont=x_vegpont;
		this.datas_for_new_mesh=datas_for_new_mesh;
		this.mesh_adap_faktor = mesh_adap_faktor;
	}

	void set_ppb_values(double h_oh_ppb1, double h_oh_ppb2){
		this.ppb1_hoh=h_oh_ppb1;
		this.ppb2_hoh=h_oh_ppb2;
	}


	void run(){
		System.out.println("MESH_LOGIKA_RUN!!");
		double hoh_max = datas_for_new_mesh.get_hoh_max_value();
		double hoh_talp_bal = datas_for_new_mesh.get_talpbal();
		double hoh_talp_jobb = datas_for_new_mesh.get_talpjobb();
		double hoh_fel_bal = datas_for_new_mesh.get_felbal();
		double hoh_fel_jobb = datas_for_new_mesh.get_feljobb();
		
		int irany=0;   // 0: balra, 1: jobbra
		if(irany==0){
			x_kezdeti_pont = x_kezdeti_pont - mesh_adap_faktor;  //eredeti 0.002 mm athelyezes; 0.0005 mm-rel probaltam meg
			x_vegpont = x_vegpont - mesh_adap_faktor;
			ppb1_x = ppb1_x - mesh_adap_faktor;
			ppb2_x = ppb2_x - mesh_adap_faktor;
		}	
		else if(irany==1){



		}
		else{System.out.println("Valami gebasz van a getStrategy()-vel");}
			
	}

	int getStrategy(){
		if(ppb1_hoh>=(1.5*1e-8) && ppb2_hoh<(2*1e-8)){return 0;}
		else if(ppb2_hoh>=(1.5*1e-8) && ppb1_hoh<(2*1e-8)){return 1;}
		else{return -1;}	

	}

}

//PPB erteket hogyan lehet lekerni a COMSOL-tol
/*
//Get ppb1 and ppb2 values!!!
	model.result().numerical().create("gev1", "EvalGlobal");
	model.result().numerical("gev1").set("expr", "mod1.ppb1");
	double [][] results_ppb1=model.result().numerical("gev1").getReal();

//ppb2
	model.result().numerical().create("gev2", "EvalGlobal");
	model.result().numerical("gev2").set("expr", "mod1.ppb2");
	double [][] results_ppb2=model.result().numerical("gev2").getReal();


//get new HOH values at ppb1_x and ppb2_x

			results_ppb1=model.result().numerical("gev1").getReal();
			results_ppb2=model.result().numerical("gev2").getReal();
			
			length_y_ppb=results_ppb1[0].length-1;

			mesh_adap.h_oh_ppb1=results_ppb1[0][length_y_ppb];
			mesh_adap.h_oh_ppb2=results_ppb2[0][length_y_ppb];

*/
