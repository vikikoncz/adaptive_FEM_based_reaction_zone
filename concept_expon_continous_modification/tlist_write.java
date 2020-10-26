import com.comsol.model.*;
import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;

public class tlist_write{
	double[] tlist_solved_in_this_step;  // a kiirando tlist alapjan donti el
	int fajlba_irni;
	int tlist_eddig;
	int vege;
	
tlist_write(){
						
			tlist_solved_in_this_step=null;
			fajlba_irni=0;	
			tlist_eddig=0;			//tlist-ben elso olyan elemre mutat, ami meg nincs kiirva, megoldva!
			vege=0;
			}	
	
void set(double [] tlist1, double [] t_sol, double [] tlist_next)	{
		int x=t_sol.length;
		int y=tlist1.length;
		
		int l=0;
		/*Allapotgep-szeru*/
		//Elso kor
		if(tlist_eddig==0 ){
			if(t_sol[x-1]<tlist1[y-1]){
				System.out.println("ag:1");
			
				l=x-1;
				tlist_solved_in_this_step=new double [l];
				for (int i=0; i<l ;i++){
					tlist_solved_in_this_step[i]=t_sol[i];
				}
				tlist_eddig=tlist_eddig+l;
				System.out.println("l:"+l);
				fajlba_irni=1;
			
				if(t_sol[x-1]>tlist1[tlist_eddig]){
					System.out.println("ag:1_hiba");
					l=x-1;
					tlist_solved_in_this_step=new double [l];
											
					for (int i=0; i<l ;i++){
					tlist_solved_in_this_step[i]=t_sol[i+1];
					}
					System.out.println("l:"+l);
					
					tlist_eddig=tlist_eddig+1;
				}
				
			int hiba=0;
			while(t_sol[x-1]>tlist1[tlist_eddig]){
				tlist_eddig=tlist_eddig+1;
				String hiba_s=Integer.toString(hiba);
				System.out.println("ag:1_hiba"+hiba_s);
				hiba ++;
			}				
				
		}
			
					
			else{
			l=x;
			System.out.println("ag:2");
			tlist_solved_in_this_step=new double [l];
			for (int i=0; i<l ;i++){
			tlist_solved_in_this_step[i]=t_sol[i];
			}
			System.out.println("l:"+l);
			tlist_eddig=tlist_eddig+l;
		    fajlba_irni=1;
			vege=1;
			}
			
		}
		
		//t_sol-ban ket allapot, elso nemm kell, utolso sem! ket t_list ertek kozott megallt
		else if(x==2){
			int z=tlist_next.length;
			if(x-1!=z && t_sol[x-1]<tlist_next[z-1]){
			System.out.println("ag:3");
			tlist_solved_in_this_step=null;
			System.out.println("l:"+l);
			fajlba_irni=0;
			
			if(t_sol[x-1]>tlist1[tlist_eddig]){
					System.out.println("ag:3_hiba");
					l=x-1;
					tlist_solved_in_this_step=new double [l];
											
					for (int i=0; i<l ;i++){
					tlist_solved_in_this_step[i]=t_sol[i+1];
					}
					System.out.println("l:"+l);
					
					tlist_eddig=tlist_eddig+1;
				}
				
				int hiba=0;
				while(t_sol[x-1]>tlist1[tlist_eddig]){
					tlist_eddig=tlist_eddig+1;
					String hiba_s=Integer.toString(hiba);
					System.out.println("ag:3_hiba"+hiba_s);
					hiba ++;
				}				
			
			
				}
			else{
			l=1;
			System.out.println("ag:4");
			tlist_solved_in_this_step=new double [l];
			tlist_solved_in_this_step[0]=t_sol[x-1];
			System.out.println("l:"+l);
			tlist_eddig=tlist_eddig+l;
			fajlba_irni=1;
			vege=1;
			}	
		}
		
		//normal eset t_sol-bol elso es utolso? nem kell!!!
		else{
			int z=tlist_next.length;
			if(x-1!=z && t_sol[x-1]<tlist_next[z-1]){
				l=x-2;
				System.out.println("ag:5");
				tlist_solved_in_this_step=new double [l];
											
				for (int i=0; i<l ;i++){
					tlist_solved_in_this_step[i]=t_sol[i+1];
					}
					System.out.println("l:"+l);
				tlist_eddig=tlist_eddig+l;
				fajlba_irni=1;
				
				//Itt kell foglalkozni azzal a COMSOL hibaval, h neha hulye tomb ertekeket ad vissza
				//Ez akkor van, ha kozvetlenul atugrik egy lepest, majd azt rakja be a tombe
				
				if(t_sol[x-1]>tlist1[tlist_eddig]){
					System.out.println("ag:5_hiba");
					l=x-1;
					tlist_solved_in_this_step=new double [l];
											
					for (int i=0; i<l ;i++){
					tlist_solved_in_this_step[i]=t_sol[i+1];
					}
					System.out.println("l:"+l);
					
					tlist_eddig=tlist_eddig+1;
				}
				
				int hiba=0;
				while(t_sol[x-1]>tlist1[tlist_eddig]){
					tlist_eddig=tlist_eddig+1;
					String hiba_s=Integer.toString(hiba);
					System.out.println("ag:5_hiba"+hiba_s);
					hiba ++;
				}				
				
				
			}	
			else{
				l=x-1;
				System.out.println("ag:6");
				tlist_solved_in_this_step=new double [l];
				for (int i=0; i<l ;i++){
					tlist_solved_in_this_step[i]=t_sol[i+1];
					}
				System.out.println("l:"+l);	
				tlist_eddig=tlist_eddig+l;
				fajlba_irni=1;
				vege=1;
			
			}
		}
		
		
}
		
}	
