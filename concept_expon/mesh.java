//import com.comsol.model.*;
//import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;

/*Ez fogja tudni a tenyleges mesh-t letrehozni; Racsfuggveny alljon most 5 szakaszbol
*/

public class mesh{

	double [][] vtx_ekvi;   //ezek lesznek az ekvidisztans pontok
	double [][] vtx_real;  //ezek a valodi attranszformalt mesh pontok  
	int [][] edge;   

	mesh_settings mesh_settings;
	parameters par;

	double A=0;  //ket szelen a ket linearis szakasz
	double B=0; 
	
	double m=0;   //segedvaltozok a ket egyenes szakaszhoz
	double b_m=0;	

	double M = 0;
	double b_M = 0; 


	//ezeket a calculate_parameters_EXPON() fuggveny szamitja ki
	//A ponthoz tartozo EXPON
	

	double yA_x = 0;  // Y es Z pontok koordinatai  
	double yA_y = 0;  // exponencialis es a ket erinto szakasz metszespontja
	double zA_x = 0;  // ezek kozott kell a kor fuggvenyebol venni a racsfuggvenyt
	double zA_y = 0;

	// exp parameterei
	double A_expA = 0;
	double c_expA = 0;
	double d_expA = 0;

	

	//B ponthoz tartozo EXPON
	

	double yB_x = 0;    
	//double yB_y = 0;  
	double zB_x = 0;  
	//double zB_y = 0;
		
	// exp parameterei
	double A_expB = 0;
	double c_expB = 0;
	double d_expB = 0;


	//szamlalok az egyes szakaszokhoz, hova hany pont jut majd ugye :)))
	double lin_1 = 0;
	double lin_2 = 0;
	double lin_3 = 0;
	double exp_1 = 0;
	double exp_2 = 0;

	int f_SM_type = 1;  //1: parabolikus 2: exponencialis	
	int szelen_vagyunk = 0; //mesh hany szakaszbol all majd 0: nem vagyunk a szelen -> 5 szakasz; 1: szelen vagyunk -> 3 szakasz 2: szelen vagyunk, 4 szakasz

	double a1 = 0;
	double a2 = 0;

	double z_Ax_kalap1 = 0;
	double z_Ax_kalap2 = 0; 
	double zA_x_kalap = 0;	

	
	double x_1 = 0; //mesh_settings.x_kezdeti_pont
	double x_2 = 0; //mesh_settings.x_vegpont

	

//Konstruktor
	mesh(mesh_settings mesh_settings, parameters par){
		this.mesh_settings=mesh_settings;
		this.par=par;
		this.generate_vtx_ekvi();
	}

//Functions
	void generate_vtx_ekvi(){
		
		vtx_ekvi=new double [1][mesh_settings.N+1]; 
		
		vtx_ekvi[0][0]=0;
		vtx_ekvi[0][mesh_settings.N]=par.L; 

		double h = (double) par.L / mesh_settings.N ; 
		
		for(int i=1; i < mesh_settings.N ; i++){
			vtx_ekvi[0][i] = (double) h*i;			

		}

	}

	void generate() throws IOException{
		
		
		
		
		this.transform();
		this.generate_edge();
		this.write_mesh_vtx_FILE(); //for debugging
	}

	void transform(){

		vtx_real=new double [1][mesh_settings.N+1]; 
		
		szelen_vagyunk = 0;

		//nullazzuk a szamlalokat
		lin_1 = 0;
		lin_2 = 0;
		lin_3 = 0;
		exp_1 = 0;
		exp_2 = 0;
		
		vtx_real[0][0]=0;
		vtx_real[0][mesh_settings.N]=par.L; 

		//Figure out scaling paramteres

		A = mesh_settings.x_kezdeti_pont * (1-mesh_settings.ratio) / (1-mesh_settings.x_vegpont + mesh_settings.x_kezdeti_pont);
		B = mesh_settings.ratio + A;

		

		//segedvaltozok a ket egyenes szakaszhoz
		m = mesh_settings.x_kezdeti_pont / A;
		b_m = 1-m;
		
		M = (mesh_settings.x_vegpont - mesh_settings.x_kezdeti_pont) / (B-A);
		b_M = (mesh_settings.x_kezdeti_pont*B - mesh_settings.x_vegpont*A) / (B-A);

		if(A <= 0) {
			A = 0;
			B = mesh_settings.ratio + A;

			szelen_vagyunk = 1;
			x_1 = 0;
			x_2 = mesh_settings.x_vegpont - mesh_settings.x_kezdeti_pont;

			m =  (1 - x_2) / (1 - B);
			b_m = 1-m;
			
			M = (x_2 - x_1) / (B-A);
		    	b_M = 0;


		}



		//segedvaltozok a kozepso egyeneshez
		
		

		System.out.println("A="+A);
		System.out.println("B="+B);
		System.out.println("x_kezdeti_pont="+mesh_settings.x_kezdeti_pont);
		System.out.println("x_vegpont="+mesh_settings.x_vegpont);
		System.out.println("m="+m);
		System.out.println("b_m="+b_m);
		System.out.println("M="+M);
		System.out.println("b_M="+b_M);
	
	
		//if(mesh_settings.function_type == 9999) {

			this.calculate_parameters_EXPON();
			
			if (szelen_vagyunk == 0){
				System.out.println("Mesh 5 szakaszbol all!!!");
			}
			else if (szelen_vagyunk == 1){
				System.out.println("Mesh 3 szakaszbol all!!!");
			}
			else if (szelen_vagyunk == 2){
				System.out.println("Mesh 4 szakaszbol all !!!");
			}
			

			for(int i=1; i < mesh_settings.N ; i++){

				vtx_real[0][i] = Grid_Function_EXPON(vtx_ekvi[0][i]);			
			}

		//}

		
		
		

		//else {System.out.println("Unknown function type");}

	}

	double Grid_Function_EXPON(double x){

		double y = 0;
		int n = mesh_settings.N;

		if(szelen_vagyunk == 0){
		
			//System.out.println("Mesh 5 szakaszbol all!!!");	
			//5 szakasz van
			if (x <= yA_x){ y = m*x; lin_1++; }
			else if (yA_x < x && x < zA_x){
			
				y = A_expA * Math.exp(c_expA * (x-zA_x)) + d_expA;	
				exp_1++;
				/*if(y<0){y = m*x;
					lin_1++; exp_1--;
				}
				*/ //szerintem erre most itt mar nincs szukseg
			

			}
			else if (zA_x <= x && x <= yB_x){ y = M*x + b_M; lin_2++;}
			else if (yB_x < x && x < zB_x){
			
				y = A_expB * Math.exp(c_expB * (x-zB_x)) + d_expB;
				exp_2++;
			}
			else if (zB_x <= x){ y = m*x + b_m; lin_3++;}
			else {}		
		}

		else if (szelen_vagyunk == 1){
			//3 szakasz
			//System.out.println("Mesh 3 szakaszbol all!!!");	

			if (x <= yB_x){ y = M*x + b_M; lin_2++;}
			else if (yB_x < x && x < zB_x){
			
				y = A_expB * Math.exp(c_expB * (x-zB_x)) + d_expB;
				exp_2++;
			}
			else if (zB_x <= x){ y = m*x + b_m; lin_3++;}
			else {}		

		}


		else if (szelen_vagyunk == 2 && f_SM_type == 1){
			//4 szakasz - SM: parabolikus
			//System.out.println("Mesh 4 szakaszbol all!!!");	



			if (x < zA_x){ y = a2 *x *x + a1 *x;}
			else if (zA_x <= x && x <= yB_x){ y = M*x + b_M; lin_2++;}
			else if (yB_x < x && x < zB_x){
			
				y = A_expB * Math.exp(c_expB * (x-zB_x)) + d_expB;
				exp_2++;
			}
			else if (zB_x <= x){ y = m*x + b_m; lin_3++;}
			else {}		

		}


		else if (szelen_vagyunk == 2 && f_SM_type == 2){
			//4 szakasz - SM: exp -> Taylor sorba fejtve
			//System.out.println("Mesh 4 szakaszbol all!!!");	



			if (x < zA_x_kalap){ y = M*c_expA/2*(x-zA_x_kalap) * (x-zA_x_kalap) - M*(x-zA_x_kalap) + A_expA + d_expA;}
			else if (zA_x_kalap <= x && x <= yB_x){ y = M*x + b_M; lin_2++;}
			else if (yB_x < x && x < zB_x){
			
				y = A_expB * Math.exp(c_expB * (x-zB_x)) + d_expB;
				exp_2++;
			}
			else if (zB_x <= x){ y = m*x + b_m; lin_3++;}
			else {}		

		}
		
		
		
		

		else {System.out.println("Valami gubanc lett a mesh-sel. Hany szakaszbol rakjuk ossze???");}

		return y;

}

	void calculate_parameters_EXPON(){
		
		//A oldal
		c_expA = Math.log(M / m) / mesh_settings.delta_x;
		A_expA = M / c_expA;

		yA_x = (A_expA * (Math.exp(c_expA * (-1) * mesh_settings.delta_x) - 1) + M * mesh_settings.delta_x + b_M ) / (m - M);
		zA_x = yA_x + mesh_settings.delta_x;

		d_expA = M * zA_x + b_M - A_expA;
		
		//4 szakasz
		if(yA_x < 0 && szelen_vagyunk == 0){
			
			szelen_vagyunk = 2;

			//ekkor a koztes simogato fuggveny tipus -> legyen parabolikus			
			if (f_SM_type == 1){

				//f_SM = a1*x + a2*x^2				
				a2 = - b_M / zA_x / zA_x;
				a1 = M + 2 * b_M / zA_x;

				System.out.println("a1="+a1);
				System.out.println("a2="+a2);

			}						


			//exponencialis fuggvenyt fix A,c parameterekkel Taylor-sorba fejtjuk (masodfokuba), 
			if (f_SM_type == 2){

				z_Ax_kalap1 = (M - b_M + Math.sqrt((M-b_M)*(M-b_M) - 2*M*M*c_expA)) / M /c_expA;
				z_Ax_kalap2 = (M - b_M - Math.sqrt((M-b_M)*(M-b_M) - 2*M*M*c_expA)) / M /c_expA;
				
				System.out.println("z_Ax_kalap1="+z_Ax_kalap1);
				System.out.println("z_Ax_kalap2="+z_Ax_kalap2);
				
				zA_x_kalap = z_Ax_kalap2;
				
				d_expA = b_M * zA_x_kalap + M - A_expA;
				

			}				


			
		}
		
		

		System.out.println("yA_x="+yA_x);
		System.out.println("zA_x="+zA_x);
		System.out.println("c_expA="+c_expA);
		System.out.println("A_expA="+A_expA);
		System.out.println("d_expA="+d_expA);
		System.out.println("yA_y="+(m*yA_x));
		System.out.println("zA_y="+(M*yA_y + b_M));
	

		//B oldal
		c_expB = (-1) * Math.log(M / m) / mesh_settings.delta_x;
		A_expB = m / c_expB;

		yB_x = (A_expB * (Math.exp(c_expB * (-1) * mesh_settings.delta_x) - 1) + m * mesh_settings.delta_x + b_m - b_M) / (M - m); 
		zB_x = yB_x + mesh_settings.delta_x;
		
		//szerintem most a jobb oldalra nem nagyon fog jutni a mesh soha
		if(zB_x > 1) {zB_x = 1;}
		
		d_expB = m * zB_x + b_m - A_expB;		
		
		System.out.println("yB_x="+yB_x);
		System.out.println("zB_x="+zB_x);
		System.out.println("c_expB="+c_expB);
		System.out.println("A_expB="+A_expB);
		System.out.println("d_expB="+d_expB);
	
		
	}

	

	//Edge generalas - ez mindig igy mukodik
	void generate_edge(){
		int n_pont=vtx_real[0].length-1;
		edge=new int [2][n_pont];
		for(int i=0;i<edge[0].length;i++){
			for(int j=0;j<edge.length;j++){
				if(j==0) edge[j][i]=i;			
				else edge[j][i]=i+1;			
			}
		}
	}

	
	//for DEBUGGING purposes

	void write_mesh_vtx_FILE() throws IOException{
		String name="mesh.dat";	
		File f=new File(name);
		FileWriter ki_stream = new FileWriter(f);
		PrintWriter ki = new PrintWriter(ki_stream);

		//HEADER
		ki.print("%Szakasz_index"+"\t");
		ki.print("VTX_ekvi"+"\t");
		ki.print("VTX_real"+"\t");
		ki.print("mesh_density"+"\n"); //L=1

		int n=vtx_real[0].length-1;
		
		for (int i=0; i<n; i++){
			double mesh_density=1/(vtx_real[0][i+1] - vtx_real[0][i]); 
			double x_coord=vtx_real[0][i];
			double x_ekvi=vtx_ekvi[0][i];
			ki.println(i+"\t"+x_ekvi+"\t"+x_coord+"\t"+mesh_density);

		}
		
		ki.println(n+"\t"+vtx_ekvi[0][n]+"\t"+vtx_real[0][n]);	
		
		ki.print("\n");
		ki.print("\n");


		ki.close();
		
		
	}
	
	
	void write_mesh_vtx_density_FILE() throws IOException{
		String name="mesh_edge_denstity_1.dat";	
		File f=new File(name);
		FileWriter ki_stream = new FileWriter(f);
		PrintWriter ki = new PrintWriter(ki_stream);

		//HEADER
		ki.print("%Szakasz_index"+"\t");
		ki.print("x_coord"+"\t");
		ki.print("mesh_density"+"\n"); //L=1

		int n=vtx_real[0].length-1;
		
		for (int i=0; i<n; i++){
			double mesh_density=1/(vtx_real[0][i+1] - vtx_real[0][i]); 
			double x_coord=(vtx_real[0][i+1] + vtx_real[0][i]) / 2;
			ki.println(i+"\t"+x_coord+"\t"+mesh_density);

		}	
		
		ki.print("\n");
		ki.print("\n");

		ki.close();
	}
	
	
	
	
	
	void check_vtx(){
		int n=vtx_real[0].length-1;
		
		int proba=0;

		for (int i=0; i<n; i++){
			if(vtx_real[0][i+1]<vtx_real[0][i]){proba=1;}	
		}

		if(proba==0){System.out.println("VTX SZIG MON NO");}
		else{System.out.println("VTX NOT GOOD");}
	}


}
