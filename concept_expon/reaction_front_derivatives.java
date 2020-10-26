//import com.comsol.model.*;
//import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;

/*Ez egy tarolo osztaly, 

*/

public class reaction_front_derivatives{

	double [][] hx;
	double [][] ohx;
	double [][] kx;
	double [][] clx;
	double [][] phix;
	double [][] phixx;

	double [][] hxx;
	double [][] ohxx;
	double [][] kxx;
	double [][] clxx;
	double [][] ht;
	double [][] oht;
	double [][] kt;
	double [][] clt;
	double [][] phit;

	double [][] h;
	double [][] oh;
	double [][] k;
	double [][] cl;
	double [][] c_fa;
	double [][] phi;		

	double intervall_bal;
	double intervall_jobb;	

	int hx_min;
	int hx_max;
	int ohx_min;
	int ohx_max;
	int kx_min;
	int kx_max;
	int clx_min;
	int clx_max;
	int phix_min;
	int phix_max;
	int phixx_min;
	int phixx_max;

	int hxx_min;
	int hxx_max;
	int ohxx_min;
	int ohxx_max;
	int kxx_min;
	int kxx_max;
	int clxx_min;
	int clxx_max;
	int ht_min;
	int ht_max;
	int oht_min;
	int oht_max;
	int kt_min;
	int kt_max;
	int clt_min;
	int clt_max;
	int phit_min;
	int phit_max;

	

	//ezek szelsoertekei, es pozicioja

	reaction_front_derivatives(){}
	
	void set_derivatives(double [][][] result_derivatives, double [][] vtx_real, double intervall_bal, double intervall_jobb) throws IOException {

		this.intervall_bal = intervall_bal - 0.02;
		this.intervall_jobb = intervall_jobb + 0.02;

		int l=vtx_real[0].length;

		hx=new double [2][l];
		ohx=new double [2][l];
		kx=new double [2][l];
		clx=new double [2][l];
		phix=new double [2][l];
		phixx=new double [2][l];

		hxx=new double [2][l];
		ohxx=new double [2][l];
		kxx=new double [2][l];
		clxx=new double [2][l];
		ht=new double [2][l];
		oht=new double [2][l];
		kt=new double [2][l];
		clt=new double [2][l];
		phit=new double [2][l];

		h=new double [2][l];
		oh=new double [2][l];
		k=new double [2][l];
		cl=new double [2][l];
		c_fa=new double [2][l];
		phi=new double [2][l];

		for(int i=0; i<l;i++){
			hx[0][i]=vtx_real[0][i];
			ohx[0][i]=vtx_real[0][i];
			kx[0][i]=vtx_real[0][i];
			clx[0][i]=vtx_real[0][i];
			phix[0][i]=vtx_real[0][i];
			phixx[0][i]=vtx_real[0][i];

			hxx[0][i]=vtx_real[0][i];
			ohxx[0][i]=vtx_real[0][i];
			kxx[0][i]=vtx_real[0][i];
			clxx[0][i]=vtx_real[0][i];	
			ht[0][i]=vtx_real[0][i];
			oht[0][i]=vtx_real[0][i];
			kt[0][i]=vtx_real[0][i];
			clt[0][i]=vtx_real[0][i];
			phit[0][i]=vtx_real[0][i];

			h[0][i]=vtx_real[0][i];
			oh[0][i]=vtx_real[0][i];
			k[0][i]=vtx_real[0][i];
			cl[0][i]=vtx_real[0][i];
			c_fa[0][i]=vtx_real[0][i];
			phi[0][i]=vtx_real[0][i];
			
			hx[1][i]=result_derivatives[0][0][i];
			ohx[1][i]=result_derivatives[1][0][i];
			kx[1][i]=result_derivatives[2][0][i];
			clx[1][i]=result_derivatives[3][0][i];
			phix[1][i]=result_derivatives[4][0][i];
			phixx[1][i]=result_derivatives[5][0][i];


			hxx[1][i]=result_derivatives[6][0][i];
			ohxx[1][i]=result_derivatives[7][0][i];
			kxx[1][i]=result_derivatives[8][0][i];
			clxx[1][i]=result_derivatives[9][0][i];	
			ht[1][i]=result_derivatives[10][0][i];
			oht[1][i]=result_derivatives[11][0][i];
			kt[1][i]=result_derivatives[12][0][i];
			clt[1][i]=result_derivatives[13][0][i];
			phit[1][i]=result_derivatives[14][0][i];

			h[1][i]=result_derivatives[15][0][i];
			oh[1][i]=result_derivatives[16][0][i];
			k[1][i]=result_derivatives[17][0][i];
			cl[1][i]=result_derivatives[18][0][i];
			c_fa[1][i]=result_derivatives[19][0][i];
			phi[1][i]=result_derivatives[20][0][i];
			

		}


		
		this.update();
	}

	void update(){

		hx_min = calculate_min(hx);
		hx_max = calculate_max(hx);
		ohx_min = calculate_min(ohx);
		ohx_max = calculate_max(ohx);
		kx_min = calculate_min(kx);
		kx_max = calculate_max(kx);
		clx_min = calculate_min(clx);
		clx_max = calculate_max(clx);
		phix_min = calculate_min(phix);
		phix_max = calculate_max(phix);
		phixx_min = calculate_min(phixx);
		phixx_max = calculate_max(phixx);

		
		hxx_min = calculate_min(hxx);
		hxx_max = calculate_max(hxx);
		ohxx_min = calculate_min(ohxx);
		ohxx_max = calculate_max(ohxx);
		kxx_min = calculate_min(kxx);
		kxx_max = calculate_max(kxx);
		clxx_min = calculate_min(clxx);
		clxx_max = calculate_max(clxx);
		ht_min = calculate_min(ht);
		ht_max = calculate_max(ht);
		oht_min = calculate_min(oht);
		oht_max = calculate_max(oht);
		kt_min = calculate_min(kt);
		kt_max = calculate_max(kt);
		clt_min = calculate_min(clt);
		clt_max = calculate_max(clt);
		phit_min = calculate_min(phit);
		phit_max = calculate_max(phit);


	}

	int calculate_max(double [][] derivate){

		int l = derivate[0].length;
		double derivate_max=0;
		int max_i = 0;
		for(int i=0; i<l; i++){
			if(derivate[1][i] > derivate_max && derivate[0][i] > intervall_bal && derivate[0][i] < intervall_jobb){
				derivate_max = derivate[1][i];
				max_i = i;
			}
		}
		
		return max_i;

	}


	int calculate_min(double [][] derivate){

		int l = derivate[0].length;
		double derivate_min=0;
		int min_i = 0;
		for(int i=0; i<l; i++){
			if(derivate[1][i] < derivate_min && derivate[0][i] > intervall_bal && derivate[0][i] < intervall_jobb){
				derivate_min = derivate[1][i];
				min_i = i;
			}
		}
		
		return min_i;

	}	

}
