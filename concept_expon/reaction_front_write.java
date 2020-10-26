//import com.comsol.model.*;
//import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;
import java.util.*;
import java.text.SimpleDateFormat;


/*Feladata: reaction front-hoz tartozo parameterek file-ba mentese: H*OH, derivaltak szelsoertekei, ezek helyei stb.
Teljesen az eddigi WRITE osztalyok mintajara keszult
*/



public class reaction_front_write{

	String name;   // ez lesz a fajnev eleresi uttal, ahova menteni kell majd
	datas_for_new_mesh datas_for_new_mesh;
	mesh_settings mesh_settings;
	reaction_front_derivatives reaction_front_derivatives;


	double diff_hx_min = 0;
	double diff_hx_max = 0;
	double diff_ohx_min = 0;
	double diff_ohx_max = 0;
	double diff_kx_min = 0;
	double diff_kx_max = 0;
	double diff_clx_min = 0;
	double diff_clx_max = 0;
	double diff_phix_min = 0;
	double diff_phix_max = 0;
	double diff_hxx_min = 0;
	double diff_hxx_max = 0;
	double diff_ohxx_min = 0;
	double diff_ohxx_max = 0;
	double diff_kxx_min = 0;
	double diff_kxx_max = 0;
	double diff_clxx_min = 0;
	double diff_clxx_max = 0;
	double diff_phixx_min = 0;
	double diff_phixx_max = 0;		



	reaction_front_write(datas_for_new_mesh datas_for_new_mesh, mesh_settings mesh_settings, reaction_front_derivatives reaction_front_derivatives){
		this.datas_for_new_mesh=datas_for_new_mesh;
		this.mesh_settings=mesh_settings;
		this.reaction_front_derivatives=reaction_front_derivatives;
	
	}

	void start(double [] tlist1, sol sol, sol sol_t, parameters par) throws IOException
	{	
		machine_settings ms=new machine_settings();
		String dirnev_elo=ms.getDIR_for_reaction_front();   

		string_subdir string_subdir=new string_subdir();
	
		String subdir=string_subdir.get_subdir_name(sol, sol_t, tlist1);
	
		String dirnev=dirnev_elo+subdir;
		System.out.println(dirnev);
		System.out.println(dirnev_elo);
		System.out.println(subdir);
	
		string_filename string_filename=new string_filename();
		String s=string_filename.get_file_name(sol, sol_t, tlist1);
	
		Date dateNow = new Date ();
		String d1=dateNow.toString();
		String d2=d1.replaceAll(" ","_");
		String d3=d2.replaceAll(":",".");
		/*System.out.println(dateNow);
		System.out.println(d3);
		*/
		System.out.println("reaction_front");
	
		name=dirnev+s+"_"+d3+".dat";

		File f=new File(name);
		FileWriter ki_stream = new FileWriter(f);
		PrintWriter ki = new PrintWriter(ki_stream);
	
		ki.println("%Results of the time-dependent simulation of acid-base diode\n%Reaction Front");

		ki.println("%Stationary settings and concentrations:");
		ki.println("%c_koh="+sol.c_koh);
		ki.println("%c_hcl="+sol.c_hcl);
		ki.println("%c_kcl_base="+sol.c_kcl_base);
		ki.println("%c_kcl_acid="+sol.c_kcl_acid);
		ki.println("%U0="+sol.U0);
		ki.println("%");
	
		ki.println("%Time-dependent settings and concentrations");
		ki.println("%c_koh_t="+sol_t.c_koh);
		ki.println("%c_hcl_t="+sol_t.c_hcl);
		ki.println("%c_kcl_base_t="+sol_t.c_kcl_base);
		ki.println("%c_kcl_acid_t="+sol_t.c_kcl_acid);
		ki.println("%U0_t="+sol_t.U0);
		ki.println("%");

		int x=tlist1.length;
		//int y=tlist_solved_in_this_step.length;	 //szerintem ennek itt es most nincs relevanciaja
	
	
		ki.println("%Time_settings");
		ki.println("%Duration of the modeified concentration (peak/impulse)="+tlist1[x-1]);
		ki.println("%Time lists");
	
		ki.print("%tlist1=");
		for(int i=0; i<x; i++){
			ki.print(tlist1[i]+";");
		}
		ki.print("\n");

		ki.println("%");
		ki.println("%");
	
		ki.println("%Parameters used in the simulation");
		ki.println("%K(vizionszorzat)="+par.K);
		ki.println("%k_reak(viz disszociacio sebessegi allando)="+par.k_reak);
		ki.println("%L="+par.L);
		ki.println("%T="+par.T);
		ki.println("%D_h="+par.D_h);
		ki.println("%D_oh="+par.D_oh);
		ki.println("%D_k="+par.D_k);
		ki.println("%D_cl="+par.D_cl);
		ki.println("%K_fix="+par.K_fix);
		ki.println("%k_fix="+par.k_fix);
		ki.println("%c0_fa="+par.c0_fa);
		ki.println("%magick="+par.magick);
		ki.println("%R="+par.R);
		ki.println("%F="+par.F);
	
		ki.println("%");
		ki.println("%");

		ki.println("%MESH SETTINGS");
		ki.println("%n_mesh_real="+mesh_settings.N);
		//ki.println("%n_lepcso_ossz_arany="+mesh_settings.n_lepcso_ossz_arany);
		ki.println("%function_type="+mesh_settings.function_type);

		ki.println("%");
		ki.println("%");

		//REKORDOK nevet ide kene majd kiirni    
		ki.print("%t_last"+"\t");
		ki.print("x_hoh_max"+"\t");
		ki.print("hoh_max"+"\t");
		ki.print("x_talpbal"+"\t");
		ki.print("x_talpjobb"+"\t");
		ki.print("x_felbal"+"\t");
		ki.print("x_feljobb"+"\t");

		ki.print("x_hx_max"+"\t");
		ki.print("hx_max"+"\t");
		ki.print("x_hx_min"+"\t");
		ki.print("hx_min"+"\t");

		ki.print("x_ohx_max"+"\t");
		ki.print("ohx_max"+"\t");
		ki.print("x_ohx_min"+"\t");
		ki.print("ohx_min"+"\t");

		ki.print("x_kx_max"+"\t");
		ki.print("kx_max"+"\t");
		ki.print("x_kx_min"+"\t");
		ki.print("kx_min"+"\t");

		ki.print("x_clx_max"+"\t");
		ki.print("clx_max"+"\t");
		ki.print("x_clx_min"+"\t");
		ki.print("clx_min"+"\t");

		ki.print("x_phix_max"+"\t");
		ki.print("phix_max"+"\t");
		ki.print("x_phix_min"+"\t");
		ki.print("phix_min"+"\t");

		ki.print("x_phixx_max"+"\t");
		ki.print("phixx_max"+"\t");
		ki.print("x_phixx_min"+"\t");
		ki.print("phixx_min"+"\t");

		//NEW records
		ki.print("x_hxx_max"+"\t");
		ki.print("hxx_max"+"\t");
		ki.print("x_hxx_min"+"\t");
		ki.print("hxx_min"+"\t");

		ki.print("x_ohxx_max"+"\t");
		ki.print("ohxx_max"+"\t");
		ki.print("x_ohxx_min"+"\t");
		ki.print("ohxx_min"+"\t");

		ki.print("x_kxx_max"+"\t");
		ki.print("kxx_max"+"\t");
		ki.print("x_kxx_min"+"\t");
		ki.print("kxx_min"+"\t");

		ki.print("x_clxx_max"+"\t");
		ki.print("clxx_max"+"\t");
		ki.print("x_clxx_min"+"\t");
		ki.print("clxx_min"+"\t");

		ki.print("x_ht_max"+"\t");
		ki.print("ht_max"+"\t");
		ki.print("x_ht_min"+"\t");
		ki.print("ht_min"+"\t");

		ki.print("x_oht_max"+"\t");
		ki.print("oht_max"+"\t");
		ki.print("x_oht_min"+"\t");
		ki.print("oht_min"+"\t");

		ki.print("x_kt_max"+"\t");
		ki.print("kt_max"+"\t");
		ki.print("x_kt_min"+"\t");
		ki.print("kt_min"+"\t");

		ki.print("x_clt_max"+"\t");
		ki.print("clt_max"+"\t");
		ki.print("x_clt_min"+"\t");
		ki.print("clt_min"+"\t");

		ki.print("x_phit_max"+"\t");
		ki.print("phit_max"+"\t");
		ki.print("x_phit_min"+"\t");
		ki.print("phit_min"+"\t");



		ki.print("diff_hx_min" + "\t");
		ki.print("diff_hx_max" + "\t");
		ki.print("diff_ohx_min" + "\t");
		ki.print("diff_ohx_max" + "\t");
		ki.print("diff_kx_min" + "\t");
		ki.print("diff_kx_max" + "\t");
		ki.print("diff_clx_min" + "\t");
		ki.print("diff_clx_max" + "\t");
		ki.print("diff_phix_min" + "\t");
		ki.print("diff_phix_max" + "\t");
		ki.print("diff_hxx_min" + "\t");
		ki.print("diff_hxx_max" + "\t");
		ki.print("diff_ohxx_min" + "\t");
		ki.print("diff_ohxx_max" + "\t");
		ki.print("diff_kxx_min" + "\t");
		ki.print("diff_kxx_max" + "\t");
		ki.print("diff_clxx_min" + "\t");
		ki.print("diff_clxx_max" + "\t");
		ki.print("diff_phixx_min" + "\t");
		ki.print("diff_phixx_max" + "\t");

		
		ki.print("\n");



		ki.close();
	}


	void write(double tsolver_last)throws IOException
	{
		File f=new File(name);
		FileWriter ki_stream = new FileWriter(f,true );
		PrintWriter ki = new PrintWriter(ki_stream);

		//Egy rekord kiirasa 
		ki.print(tsolver_last + "\t");
		ki.print(datas_for_new_mesh.get_hoh_max_position()+"\t");
		ki.print(datas_for_new_mesh.get_hoh_max_value()+"\t");
		ki.print(datas_for_new_mesh.get_talpbal()+"\t");
		ki.print(datas_for_new_mesh.get_talpjobb()+"\t");
		ki.print(datas_for_new_mesh.get_felbal()+"\t");
		ki.print(datas_for_new_mesh.get_feljobb()+"\t");

		ki.print(reaction_front_derivatives.hx[0][reaction_front_derivatives.hx_max]+"\t");
		ki.print(reaction_front_derivatives.hx[1][reaction_front_derivatives.hx_max]+"\t");
		ki.print(reaction_front_derivatives.hx[0][reaction_front_derivatives.hx_min]+"\t");
		ki.print(reaction_front_derivatives.hx[1][reaction_front_derivatives.hx_min]+"\t");

		ki.print(reaction_front_derivatives.ohx[0][reaction_front_derivatives.ohx_max]+"\t");
		ki.print(reaction_front_derivatives.ohx[1][reaction_front_derivatives.ohx_max]+"\t");
		ki.print(reaction_front_derivatives.ohx[0][reaction_front_derivatives.ohx_min]+"\t");
		ki.print(reaction_front_derivatives.ohx[1][reaction_front_derivatives.ohx_min]+"\t");


		ki.print(reaction_front_derivatives.kx[0][reaction_front_derivatives.kx_max]+"\t");
		ki.print(reaction_front_derivatives.kx[1][reaction_front_derivatives.kx_max]+"\t");
		ki.print(reaction_front_derivatives.kx[0][reaction_front_derivatives.kx_min]+"\t");
		ki.print(reaction_front_derivatives.kx[1][reaction_front_derivatives.kx_min]+"\t");


		ki.print(reaction_front_derivatives.clx[0][reaction_front_derivatives.clx_max]+"\t");
		ki.print(reaction_front_derivatives.clx[1][reaction_front_derivatives.clx_max]+"\t");
		ki.print(reaction_front_derivatives.clx[0][reaction_front_derivatives.clx_min]+"\t");
		ki.print(reaction_front_derivatives.clx[1][reaction_front_derivatives.clx_min]+"\t");


		ki.print(reaction_front_derivatives.phix[0][reaction_front_derivatives.phix_max]+"\t");
		ki.print(reaction_front_derivatives.phix[1][reaction_front_derivatives.phix_max]+"\t");
		ki.print(reaction_front_derivatives.phix[0][reaction_front_derivatives.phix_min]+"\t");
		ki.print(reaction_front_derivatives.phix[1][reaction_front_derivatives.phix_min]+"\t");

		ki.print(reaction_front_derivatives.phixx[0][reaction_front_derivatives.phixx_max]+"\t");
		ki.print(reaction_front_derivatives.phixx[1][reaction_front_derivatives.phixx_max]+"\t");
		ki.print(reaction_front_derivatives.phixx[0][reaction_front_derivatives.phixx_min]+"\t");
		ki.print(reaction_front_derivatives.phixx[1][reaction_front_derivatives.phixx_min]+"\t");


		ki.print(reaction_front_derivatives.hxx[0][reaction_front_derivatives.hxx_max]+"\t");
		ki.print(reaction_front_derivatives.hxx[1][reaction_front_derivatives.hxx_max]+"\t");
		ki.print(reaction_front_derivatives.hxx[0][reaction_front_derivatives.hxx_min]+"\t");
		ki.print(reaction_front_derivatives.hxx[1][reaction_front_derivatives.hxx_min]+"\t");

		ki.print(reaction_front_derivatives.ohxx[0][reaction_front_derivatives.ohxx_max]+"\t");
		ki.print(reaction_front_derivatives.ohxx[1][reaction_front_derivatives.ohxx_max]+"\t");
		ki.print(reaction_front_derivatives.ohxx[0][reaction_front_derivatives.ohxx_min]+"\t");
		ki.print(reaction_front_derivatives.ohxx[1][reaction_front_derivatives.ohxx_min]+"\t");

		ki.print(reaction_front_derivatives.kxx[0][reaction_front_derivatives.kxx_max]+"\t");
		ki.print(reaction_front_derivatives.kxx[1][reaction_front_derivatives.kxx_max]+"\t");
		ki.print(reaction_front_derivatives.kxx[0][reaction_front_derivatives.kxx_min]+"\t");
		ki.print(reaction_front_derivatives.kxx[1][reaction_front_derivatives.kxx_min]+"\t");

		ki.print(reaction_front_derivatives.clxx[0][reaction_front_derivatives.clxx_max]+"\t");
		ki.print(reaction_front_derivatives.clxx[1][reaction_front_derivatives.clxx_max]+"\t");
		ki.print(reaction_front_derivatives.clxx[0][reaction_front_derivatives.clxx_min]+"\t");
		ki.print(reaction_front_derivatives.clxx[1][reaction_front_derivatives.clxx_min]+"\t");

		ki.print(reaction_front_derivatives.ht[0][reaction_front_derivatives.ht_max]+"\t");
		ki.print(reaction_front_derivatives.ht[1][reaction_front_derivatives.ht_max]+"\t");
		ki.print(reaction_front_derivatives.ht[0][reaction_front_derivatives.ht_min]+"\t");
		ki.print(reaction_front_derivatives.ht[1][reaction_front_derivatives.ht_min]+"\t");

		ki.print(reaction_front_derivatives.oht[0][reaction_front_derivatives.oht_max]+"\t");
		ki.print(reaction_front_derivatives.oht[1][reaction_front_derivatives.oht_max]+"\t");
		ki.print(reaction_front_derivatives.oht[0][reaction_front_derivatives.oht_min]+"\t");
		ki.print(reaction_front_derivatives.oht[1][reaction_front_derivatives.oht_min]+"\t");

		ki.print(reaction_front_derivatives.kt[0][reaction_front_derivatives.kt_max]+"\t");
		ki.print(reaction_front_derivatives.kt[1][reaction_front_derivatives.kt_max]+"\t");
		ki.print(reaction_front_derivatives.kt[0][reaction_front_derivatives.kt_min]+"\t");
		ki.print(reaction_front_derivatives.kt[1][reaction_front_derivatives.kt_min]+"\t");

		ki.print(reaction_front_derivatives.clt[0][reaction_front_derivatives.clt_max]+"\t");
		ki.print(reaction_front_derivatives.clt[1][reaction_front_derivatives.clt_max]+"\t");
		ki.print(reaction_front_derivatives.clt[0][reaction_front_derivatives.clt_min]+"\t");
		ki.print(reaction_front_derivatives.clt[1][reaction_front_derivatives.clt_min]+"\t");

		ki.print(reaction_front_derivatives.phit[0][reaction_front_derivatives.phit_max]+"\t");
		ki.print(reaction_front_derivatives.phit[1][reaction_front_derivatives.phit_max]+"\t");
		ki.print(reaction_front_derivatives.phit[0][reaction_front_derivatives.phit_min]+"\t");
		ki.print(reaction_front_derivatives.phit[1][reaction_front_derivatives.phit_min]+"\t");

		calculate_differences();

		//Differenciak, amiket kalkulalunk
		ki.print(diff_hx_min + "\t");
		ki.print(diff_hx_max + "\t");
		ki.print(diff_ohx_min + "\t");
		ki.print(diff_ohx_max + "\t");
		ki.print(diff_kx_min + "\t");
		ki.print(diff_kx_max + "\t");
		ki.print(diff_clx_min + "\t");
		ki.print(diff_clx_max + "\t");
		ki.print(diff_phix_min + "\t");
		ki.print(diff_phix_max + "\t");
		ki.print(diff_hxx_min + "\t");
		ki.print(diff_hxx_max + "\t");
		ki.print(diff_ohxx_min + "\t");
		ki.print(diff_ohxx_max + "\t");
		ki.print(diff_kxx_min + "\t");
		ki.print(diff_kxx_max + "\t");
		ki.print(diff_clxx_min + "\t");
		ki.print(diff_clxx_max + "\t");
		ki.print(diff_phixx_min + "\t");
		ki.print(diff_phixx_max + "\t");	
		

		ki.print("\n");  	
			
		ki.close();  
	}

	void finish() throws IOException
	{

		File f=new File(name);
		FileWriter ki_stream = new FileWriter(f, true ); //append-del lehet hozzafuzni
		PrintWriter ki = new PrintWriter(ki_stream);
	
		ki.println("%FINITO");
	
		ki.close();

	}


	void calculate_differences(){

		double x_hoh = datas_for_new_mesh.get_hoh_max_position();
	
		diff_hx_min = x_hoh - reaction_front_derivatives.hx[0][reaction_front_derivatives.hx_min]; 
		diff_hx_max = x_hoh - reaction_front_derivatives.hx[0][reaction_front_derivatives.hx_max];
		diff_ohx_min = x_hoh - reaction_front_derivatives.ohx[0][reaction_front_derivatives.ohx_min];
		diff_ohx_max = x_hoh - reaction_front_derivatives.ohx[0][reaction_front_derivatives.ohx_max];
		diff_kx_min = x_hoh - reaction_front_derivatives.kx[0][reaction_front_derivatives.kx_min];
		diff_kx_max = x_hoh - reaction_front_derivatives.kx[0][reaction_front_derivatives.kx_max];
		diff_clx_min = x_hoh - reaction_front_derivatives.clx[0][reaction_front_derivatives.clx_min];
		diff_clx_max = x_hoh - reaction_front_derivatives.clx[0][reaction_front_derivatives.clx_max];
		diff_phix_min = x_hoh - reaction_front_derivatives.phix[0][reaction_front_derivatives.phix_min];
		diff_phix_max = x_hoh - reaction_front_derivatives.phix[0][reaction_front_derivatives.phix_max];
		diff_hxx_min = x_hoh - reaction_front_derivatives.hxx[0][reaction_front_derivatives.hxx_min];
		diff_hxx_max = x_hoh - reaction_front_derivatives.hxx[0][reaction_front_derivatives.hxx_max];
		diff_ohxx_min = x_hoh - reaction_front_derivatives.ohxx[0][reaction_front_derivatives.ohxx_min];
		diff_ohxx_max = x_hoh - reaction_front_derivatives.ohxx[0][reaction_front_derivatives.ohxx_max];
		diff_kxx_min = x_hoh - reaction_front_derivatives.kxx[0][reaction_front_derivatives.kxx_min];
		diff_kxx_max = x_hoh - reaction_front_derivatives.kxx[0][reaction_front_derivatives.kxx_max];
		diff_clxx_min = x_hoh - reaction_front_derivatives.clxx[0][reaction_front_derivatives.clxx_min];
		diff_clxx_max = x_hoh - reaction_front_derivatives.clxx[0][reaction_front_derivatives.clxx_max];
		diff_phixx_min = x_hoh - reaction_front_derivatives.phixx[0][reaction_front_derivatives.phixx_min];
		diff_phixx_max = x_hoh - reaction_front_derivatives.phixx[0][reaction_front_derivatives.phixx_max];	
		
		

	}


}
