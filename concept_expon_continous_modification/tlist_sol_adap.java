import com.comsol.model.*;
import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;


/*
Feladata a kovetkezo tlist-et meghatarozni, amivel a megallitas utan a solver-t ujra kell inditani. 
Tartalmaznia kell az elso elemet a t_sol-bol (solver ket megallitas kozott ezeket a pontokat szamolta ki). Tovabba azon erteketet tlist1-bol, melyek nagyobbak a t_sol utolsonal.
Szerintem tlist_eddig-et (szamolja, hogy tlist1_bol hany elem lett mar megoldva) erdemes lenne elhagyni. (Ebbol volt mindig a baj, meg a double-okbol.) Ennek a meghatarozasa kicsit bajos, mert COMSOL naha mast ad vissza t_sol-kent, mint amit papiron kene neki. 
*/

public class tlist_sol_adap{
	double[] tlist_next; //kovetkezo korben ezzek a tlist-tel indul ujra a solver
		
tlist_sol_adap(){
			
			tlist_next=null;	
	}	
	
void set(double [] tlist1, double [] t_sol, int tlist_eddig){
		    	//int x=tlist1.length-tlist_eddig+1;
			int tlist_next_index=this.tlist1_next_index(tlist1, t_sol);
			
			
			int x=tlist1.length-tlist_next_index+1;
			tlist_next=new double [x];
			
			tlist_next[0]=t_sol[t_sol.length-1];
			
			for (int i=1; i<x;i++){
				tlist_next[i]=tlist1[i+tlist_next_index-1];
			}
		}


int tlist1_next_index(double [] tlist1, double [] t_sol){
			double t_solved_last=t_sol[t_sol.length-1];
			int i=0;
			int limit=tlist1.length-1;
			
			while(t_solved_last>tlist1[i] && i<limit){
				i++;
			}
			
			//Most i fog tlist-bol arra az elemre mutatni, melyet elsokent be kell rakni a listaba
			return i;		

} 	
	
	
}	
