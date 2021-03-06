//import com.comsol.model.*;
//import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;
import java.util.*;
import java.text.SimpleDateFormat;


public class derivatives_profile_write{

	String name;
	datas_for_new_mesh datas_for_new_mesh;
	mesh_settings mesh_settings;
	reaction_front_derivatives reaction_front_derivatives;


	derivatives_profile_write(datas_for_new_mesh datas_for_new_mesh, mesh_settings mesh_settings, reaction_front_derivatives reaction_front_derivatives){
		this.datas_for_new_mesh=datas_for_new_mesh;
		this.mesh_settings=mesh_settings;
		this.reaction_front_derivatives=reaction_front_derivatives;
	}


	void start(double [] tlist1, sol sol, sol sol_t, parameters par) throws IOException
	{	
		machine_settings ms=new machine_settings();
		String dirnev_elo=ms.getDIR_for_derivatives_profile();   

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
		System.out.println("derivatives_profile");
	
		name=dirnev+s+"_"+d3+".dat";

		File f=new File(name);
		FileWriter ki_stream = new FileWriter(f);
		PrintWriter ki = new PrintWriter(ki_stream);
	
		ki.println("%Results of the time-dependent simulation of acid-base diode\n%Derivatives profile");

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
		ki.print("%x"+"\t");
		ki.print("hoh"+"\t");  
		ki.print("hx"+"\t");
		ki.print("ohx"+"\t");
		ki.print("kx"+"\t");
		ki.print("clx"+"\t");
		ki.print("phix"+"\t");
		ki.print("phixx"+"\t");

		ki.print("hxx"+"\t");
		ki.print("ohxx"+"\t");
		ki.print("kxx"+"\t");
		ki.print("clxx"+"\t");
		ki.print("ht"+"\t");
		ki.print("oht"+"\t");
		ki.print("kt"+"\t");
		ki.print("clt"+"\t");
		ki.print("phit"+"\t");

		ki.print("h"+"\t");
		ki.print("oh"+"\t");
		ki.print("k"+"\t");
		ki.print("cl"+"\t");
		ki.print("c_fa"+"\t");
		ki.print("phi"+"\t");	

		ki.print("\n");
		// 1 blokk kiirasa -> az elso
		

		
		ki.close();

	}

	void write(double tsolver_last)throws IOException
	{
		File f=new File(name);
		FileWriter ki_stream = new FileWriter(f,true );
		PrintWriter ki = new PrintWriter(ki_stream);
	
		// 1 blokk kiirasa
		ki.println("%t="+tsolver_last);

		
		int l = datas_for_new_mesh.hoh[0].length;

		for(int i=0; i<l; i++){
			ki.print(datas_for_new_mesh.hoh[0][i]+"\t");  //x
			ki.print(datas_for_new_mesh.hoh[1][i]+"\t");  //hoh	
			ki.print(reaction_front_derivatives.hx[1][i]+"\t");  //hx
			ki.print(reaction_front_derivatives.ohx[1][i]+"\t");  //ohx
			ki.print(reaction_front_derivatives.kx[1][i]+"\t");  //kx
			ki.print(reaction_front_derivatives.clx[1][i]+"\t");  //clx
			ki.print(reaction_front_derivatives.phix[1][i]+"\t");  //phix
			ki.print(reaction_front_derivatives.phixx[1][i]+"\t");  //phixx

			ki.print(reaction_front_derivatives.hxx[1][i]+"\t");  //hxx
			ki.print(reaction_front_derivatives.ohxx[1][i]+"\t");  //ohxx
			ki.print(reaction_front_derivatives.kxx[1][i]+"\t");  //kxx
			ki.print(reaction_front_derivatives.clxx[1][i]+"\t");  //clxx
			ki.print(reaction_front_derivatives.ht[1][i]+"\t");  //ht
			ki.print(reaction_front_derivatives.oht[1][i]+"\t");  //oht
			ki.print(reaction_front_derivatives.kt[1][i]+"\t");  //kt
			ki.print(reaction_front_derivatives.clt[1][i]+"\t");  //clt
			ki.print(reaction_front_derivatives.phit[1][i]+"\t");  //phit

			ki.print(reaction_front_derivatives.h[1][i]+"\t"); 
			ki.print(reaction_front_derivatives.oh[1][i]+"\t"); 
			ki.print(reaction_front_derivatives.k[1][i]+"\t"); 
			ki.print(reaction_front_derivatives.cl[1][i]+"\t"); 
			ki.print(reaction_front_derivatives.c_fa[1][i]+"\t"); 
			ki.print(reaction_front_derivatives.phi[1][i]+"\n"); 
			

		}
				
		
		ki.print("\n");  	
		
		ki.println("\n\n");	
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
}
