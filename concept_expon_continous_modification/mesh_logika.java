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
	
	double ratio=0;
	double delta_x = 0;
	
	double hoh_talp_bal_old=0;
	double hoh_talp_jobb_old=0;
	
	double hoh_talp_bal_current=0;
	double hoh_talp_jobb_current=0;
	
	int i=0; //elso mesh adaptacional ne modositsuk meg az ertekeket -> itt kell egy puffer a gyujtogetes miatt
	

	mesh_logika(double ppb1_x, double ppb2_x, double x_kezdeti_pont, double x_vegpont, datas_for_new_mesh datas_for_new_mesh, double mesh_adap_faktor, double ratio, double delta_x){
		this.ppb1_x=ppb1_x;
		this.ppb2_x=ppb2_x;
		this.x_kezdeti_pont=x_kezdeti_pont;
		this.x_vegpont=x_vegpont;
		this.datas_for_new_mesh=datas_for_new_mesh;
		this.mesh_adap_faktor = mesh_adap_faktor;
		
		this.ratio=ratio;
		this.delta_x=delta_x;
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
		
		hoh_talp_bal_old = hoh_talp_bal_current;
		hoh_talp_jobb_old = hoh_talp_jobb_current;
		
		
		hoh_talp_bal_current = datas_for_new_mesh.get_talpbal();
		hoh_talp_jobb_current = datas_for_new_mesh.get_talpjobb();
		
		System.out.println("hoh_talp_bal_old="+hoh_talp_bal_old);
		System.out.println("hoh_talp_jobb_old="+hoh_talp_jobb_old);
		System.out.println("hoh_talp_bal_current="+hoh_talp_bal_current);
		System.out.println("hoh_talp_jobb_current="+hoh_talp_jobb_current);
		
		
		int irany=0;   // 0: balra, 1: jobbra
		if(irany==0){
			
			if(i > 0){
				
				System.out.println("2020_KORONA: if ag");
				double width_change = (hoh_talp_jobb_current - hoh_talp_bal_current) / (hoh_talp_jobb_old - hoh_talp_bal_old);
				
				//method:1
				/*
				mesh_adap_faktor = mesh_adap_faktor * width_change;
				
				//1x a kozeppontot kezdjuk el atrakni
				
				double central = (x_vegpont + x_kezdeti_pont)/2;
				double dist_szel = central - x_kezdeti_pont;
				double dist_ppb = central - ppb1_x;
				
				dist_szel = dist_szel * width_change;
				dist_ppb = dist_ppb * width_change;
				central = central - mesh_adap_faktor;
				
				x_kezdeti_pont = central - dist_szel;
				x_vegpont = central + dist_szel;
				ppb1_x = central - dist_ppb;
				ppb2_x = central + dist_ppb;
							
				ratio = ratio + 0;
				delta_x = delta_x + 0;
				
				System.out.println("width_change="+width_change);
				System.out.println("mesh_adap_faktor="+mesh_adap_faktor);
				System.out.println("central="+central);
				System.out.println("dist_szel="+dist_szel);
				System.out.println("dist_ppb="+dist_ppb);
				
				System.out.println("x_kezdeti_pont="+x_kezdeti_pont);
				System.out.println("x_vegpont="+x_vegpont);
				System.out.println("ppb1_x="+ppb1_x);
				System.out.println("ppb2_x="+ppb2_x);
				*/
				
				//method:2
				 //minden ugyanez, de a ratio-t is pontosan ugyanigy csokkentjuk
				/*
				mesh_adap_faktor = mesh_adap_faktor * width_change;
				
				//1x a kozeppontot kezdjuk el atrakni
				
				double central = (x_vegpont + x_kezdeti_pont)/2;
				double dist_szel = central - x_kezdeti_pont;
				double dist_ppb = central - ppb1_x;
				
				dist_szel = dist_szel * width_change;
				dist_ppb = dist_ppb * width_change;
				central = central - mesh_adap_faktor;
				
				x_kezdeti_pont = central - dist_szel;
				x_vegpont = central + dist_szel;
				ppb1_x = central - dist_ppb;
				ppb2_x = central + dist_ppb;
							
				ratio = ratio * width_change;
				delta_x = delta_x + 0;
				
				System.out.println("width_change="+width_change);
				System.out.println("mesh_adap_faktor="+mesh_adap_faktor);
				System.out.println("central="+central);
				System.out.println("dist_szel="+dist_szel);
				System.out.println("dist_ppb="+dist_ppb);
				
				System.out.println("x_kezdeti_pont="+x_kezdeti_pont);
				System.out.println("x_vegpont="+x_vegpont);
				System.out.println("ppb1_x="+ppb1_x);
				System.out.println("ppb2_x="+ppb2_x);
							
				*/
				
				
				
				//method:3
				// minden ugyanez, de a ratio-t is pontosan ugyanigy csokkentjuk
				
				mesh_adap_faktor = mesh_adap_faktor * width_change;
				
				//1x a kozeppontot kezdjuk el atrakni
				
				double central = (x_vegpont + x_kezdeti_pont)/2;
				double dist_szel = central - x_kezdeti_pont;
				double dist_ppb = central - ppb1_x;
				
				dist_szel = dist_szel * width_change;
				dist_ppb = dist_ppb * width_change;
				central = central - mesh_adap_faktor;
				
				x_kezdeti_pont = central - dist_szel;
				x_vegpont = central + dist_szel;
				ppb1_x = central - dist_ppb;
				ppb2_x = central + dist_ppb;
							
				ratio = ratio * width_change;
				delta_x = delta_x * width_change;
				
				System.out.println("width_change="+width_change);
				System.out.println("mesh_adap_faktor="+mesh_adap_faktor);
				System.out.println("central="+central);
				System.out.println("dist_szel="+dist_szel);
				System.out.println("dist_ppb="+dist_ppb);
				
				System.out.println("x_kezdeti_pont="+x_kezdeti_pont);
				System.out.println("x_vegpont="+x_vegpont);
				System.out.println("ppb1_x="+ppb1_x);
				System.out.println("ppb2_x="+ppb2_x);
							
				
				
				
				
			}
			else{
				
				System.out.println("2020_KORONA: else ag");
				mesh_adap_faktor = mesh_adap_faktor + 0;
			
				x_kezdeti_pont = x_kezdeti_pont - mesh_adap_faktor;  //eredeti 0.002 mm athelyezes; 0.0005 mm-rel probaltam meg
				x_vegpont = x_vegpont - mesh_adap_faktor;
				ppb1_x = ppb1_x - mesh_adap_faktor;
				ppb2_x = ppb2_x - mesh_adap_faktor;
			
				ratio = ratio + 0;
				delta_x = delta_x + 0;
			}
			
			
		}	
		else if(irany==1){



		}
		else{System.out.println("Valami gebasz van a getStrategy()-vel");}
		
		i = i +1;
			
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
