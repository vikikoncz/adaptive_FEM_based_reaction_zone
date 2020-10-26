//import com.comsol.model.*;
//import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;

public class parameters{
	final double K=1e-14;
	final double k_reak=1.3e11;
	final double L=1;
	final double T=298;
	final double D_h=9.31e-9;
	final double D_oh=5.28e-9;
	final double D_k=1.96e-9;
	final double D_cl=2.04e-9;
	final double K_fix=1e-4;
	final double k_fix=6e9;
	final double c0_fa=4e-3;  //volt, hogy 4e-4-gyel szamoltam, de 4e-3 a default!!!!
	final double magick=1;
	final double R=8.3145;
	final double F=96485;	

	final double epsilon=6.954e-10;

	//Stringkent a mertekegysegek tarolva
	final String K_m=" [mol^2/dm^6]";
	final String k_reak_m=" [dm^3/mol/s]";
	final String L_m=" [mm]";
	final String T_m=" [K]";
	final String D_h_m=" [m^2/s]";
	final String D_oh_m=" [m^2/s]";
	final String D_k_m=" [m^2/s]";
	final String D_cl_m=" [m^2/s]";
	final String K_fix_m=" [mol/dm^3]";
	final String k_fix_m=" [dm^3/mol/s]";
	final String c0_fa_m=" [mol/dm^3]";
	final String magick_m="";
	final String R_m=" [J/mol/K]";
	final String F_m=" [s*A/mol]";

	final String epsilon_m=" [A*s/V/m]";	

	}
