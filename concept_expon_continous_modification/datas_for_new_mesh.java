//import com.comsol.model.*;
//import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;

/*
Tulajdonkeppen egy tarolo osztaly, ami az uj MESH-hez szukseges lekert adatokat tarolja
mesh_logika osztaly ez alapjan update-eli a mesh_settings.java osztalyt  	

*/

public class datas_for_new_mesh{
	double [][] hoh;
	int hoh_max;  //ezek azert INT-ek, mert egyfajta mutatok a hoh tomb megfelelo elemere
	int hoh_talp_bal;
	int hoh_talp_jobb;
	int hoh_fel_bal;
	int hoh_fel_jobb;
	

	datas_for_new_mesh(){}

	
	void set_hoh(double [][][] result_mesh, double [][] vtx_real) throws IOException {
		// MEMO : about data query from new mesh
		/*
		System.out.println("result_mesh.length (expression)"+result_mesh.length);
		System.out.println("result_mesh[0].length (t_list))"+result_mesh[0].length);
		System.out.println("result_mesh[0][0].length (coordinates)"+result_mesh[0][0].length);
		*/
		//expr: h_szor_oh
		//copy h_szor_oh
		int l=vtx_real[0].length;
		hoh=new double [2][l];
		for(int i=0; i<l;i++){
			hoh[0][i]=vtx_real[0][i];
			hoh[1][i]=result_mesh[0][0][i];

		}
		this.update();
		//this.write_RF_hoh();
	}

	void update(){
		this.calculate_hoh_max();
		this.calculate_felertek();
		this.calculate_talpertek();
		
	}

	void calculate_hoh_max(){
		int l = hoh[0].length;
		double current_max=0;
		int max_i = 0;
		for(int i=0; i<l; i++){
			if(hoh[1][i] > current_max){
				current_max = hoh[1][i];
				max_i = i;
			}
		}
		
		hoh_max = max_i;
	}
	
	
	void calculate_felertek(){
		double alap = 1e-8; //TODO ezen lehetne valtoztatni
		double half_value = (hoh[1][hoh_max] - alap) / 2;
		int bal_good = 0; //atbillen 1-be
		int jobb_good = 0; 

		int l=hoh[0].length;		
		for(int i=0; i<l; i++){
			if(hoh[1][i] > half_value && bal_good == 0) {bal_good = 1; hoh_fel_bal = i;}
			if(hoh[1][i] < half_value && bal_good == 1 && jobb_good == 0) {jobb_good = 1; hoh_fel_jobb = i-1;} 
		}	

	}


	void calculate_talpertek(){
		double talp_value = 2e-8; // mit definialunk talperteknek?
		int bal_good = 0; //atbillen 1-be
		int jobb_good = 0; 

		int l=hoh[0].length;		
		for(int i=0; i<l; i++){
			if(hoh[1][i] > talp_value && bal_good == 0) {bal_good = 1; hoh_talp_bal = i;}
			if(hoh[1][i] < talp_value && bal_good == 1 && jobb_good == 0) {jobb_good = 1; hoh_talp_jobb = i-1;} 
		}			

	}
	
	double get_talpbal(){
		return hoh[0][hoh_talp_bal];
	}
	
	double get_talpjobb(){
		return hoh[0][hoh_talp_jobb];
	}
	
	double get_felbal(){
		return hoh[0][hoh_fel_bal];
	}
	
	double get_feljobb(){
		return hoh[0][hoh_fel_jobb];
	}
	
	double get_hoh_max_value(){
		return hoh[1][hoh_max];
	}

	double get_hoh_max_position(){
		return hoh[0][hoh_max];

	}

	void write_RF_hoh() throws IOException {
		
		String name="RF_hoh.dat";	
		File f=new File(name);
		FileWriter ki_stream = new FileWriter(f, true); //igy appendolja
		PrintWriter ki = new PrintWriter(ki_stream);

		ki.print(get_hoh_max_value()+"\t");
		ki.print(get_talpbal()+"\t");
		ki.print(get_talpjobb()+"\t");
		ki.print(get_felbal()+"\t");
		ki.print(get_feljobb()+"\n");
		

		ki.close();

	}

}
