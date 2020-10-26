import com.comsol.model.*;
import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;
import java.util.*;
import java.text.SimpleDateFormat;

public class profile_write{

	String name;
	
void start(data data, double [] tlist1, double [] tlist_solved_in_this_step,sol sol, sol sol_t, parameters par) throws IOException{

	//String dirnev_elo="/home/matlab/acidbase_transient_data/ugras/profile/";

	machine_settings ms=new machine_settings();

	String dirnev_elo=ms.getDIR_for_profile();
	
	string_subdir string_subdir=new string_subdir();
	
	String subdir=string_subdir.get_subdir_name(sol, sol_t, tlist1);
	
	String dirnev=dirnev_elo+subdir;
	
	string_filename string_filename=new string_filename();
	String s=string_filename.get_file_name(sol, sol_t, tlist1);
	
	Date dateNow = new Date ();
	String d1=dateNow.toString();
	String d2=d1.replaceAll(" ","_");
	String d3=d2.replaceAll(":",".");
	/*System.out.println(dateNow);
	System.out.println(d3);
	*/
	
	name=dirnev+s+"_"+d3+".dat";
	
	File f=new File(name);
	FileWriter ki_stream = new FileWriter(f);
	PrintWriter ki = new PrintWriter(ki_stream);

//Nem lehet mindent kiirni a fajlba, mert akkor tul sok lenne az adat
//Nem kell minden mesh pontba interpolalni
//De ezt ugyis az interpolacional (a results tomb legyartasanal fogom megvalositani)

	 ki.println("%Results of the time-dependent simulation of acid-base diode (strong-strong)\n%Profiles");
	ki.println("%x\th\toh\tk\tcl\tc_fa\tphi");
	
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
	int y=tlist_solved_in_this_step.length;
		
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
	ki.println("%x\th\toh\tk\tcl\tc_fa\tphi");

	int k=data.x_gel.length;

	//Stationary results
	ki.println("%Stationary results");
	ki.println("%current_stat="+data.current_stat);
	for(int i=0; i<k;i++){
		ki.print(data.x_gel[i]+"\t"+data.result_stationary[0][0][i]);
		ki.print("\t"+data.result_stationary[1][0][i]);
		ki.print("\t"+data.result_stationary[2][0][i]);
		ki.print("\t"+data.result_stationary[3][0][i]);
		ki.print("\t"+data.result_stationary[4][0][i]);
		ki.println("\t"+data.result_stationary[5][0][i]);
		
	}
	
	ki.println("\n\n");

	//Time-dependent results (tlist1)
	for(int i=0; i<y; i++){
	ki.println("%t="+tlist_solved_in_this_step[i]+"\tcurrent="+data.current_time1[0][i]*1000);

	for(int j=0; j<k; j++){
		ki.print(data.x_gel[j]+"\t"+data.result_time1[0][i][j]);
		ki.print("\t"+data.result_time1[1][i][j]);
		ki.print("\t"+data.result_time1[2][i][j]);
		ki.print("\t"+data.result_time1[3][i][j]);
		ki.print("\t"+data.result_time1[4][i][j]);
		ki.println("\t"+data.result_time1[5][i][j]);

	}

	ki.println("\n\n");


	}

	

	



	ki.close();





}	
	

void write(data data, double [] tlist_solved_in_this_step)throws IOException
{
	File f=new File(name);
	FileWriter ki_stream = new FileWriter(f, true ); //append-del lehet hozzafuzni
	PrintWriter ki = new PrintWriter(ki_stream);
		
	int y=tlist_solved_in_this_step.length;
	int k=data.x_gel.length;	
		
	//Time-dependent results (tlist1)
	for(int i=0; i<y; i++){
	ki.println("%t="+tlist_solved_in_this_step[i]+"\tcurrent="+data.current_time1[0][i]*1000);

	for(int j=0; j<k; j++){
		ki.print(data.x_gel[j]+"\t"+data.result_time1[0][i][j]);
		ki.print("\t"+data.result_time1[1][i][j]);
		ki.print("\t"+data.result_time1[2][i][j]);
		ki.print("\t"+data.result_time1[3][i][j]);
		ki.print("\t"+data.result_time1[4][i][j]);
		ki.println("\t"+data.result_time1[5][i][j]);

	}

	ki.println("\n\n");


	}

	ki.close();
}	

void finish() throws IOException{
		File f=new File(name);
		FileWriter ki_stream = new FileWriter(f, true ); //append-del lehet hozzafuzni
		PrintWriter ki = new PrintWriter(ki_stream);
	
		ki.println("%FINITO");
	
		ki.close();

}

}
