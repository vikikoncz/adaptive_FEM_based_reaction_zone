import com.comsol.model.*;
import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;

public class parameters_string{

	String K;
	String k_reak;
	String L;
	String T;
	String D_h;
	String D_oh;
	String D_k;
	String D_cl;
	String K_fix;
	String k_fix;
	String c0_fa;
	String magick;
	String R;
	String F;

	String epsilon;

//Konstruktor

	parameters_string(parameters par){

		K=Double.toString(par.K)+par.K_m;
		k_reak=Double.toString(par.k_reak)+par.k_reak_m;
		L=Double.toString(par.L)+par.L_m;
		T=Double.toString(par.T)+par.T_m;
		D_h=Double.toString(par.D_h)+par.D_h_m;
		D_oh=Double.toString(par.D_oh)+par.D_oh_m;
		D_k=Double.toString(par.D_k)+par.D_k_m;
		D_cl=Double.toString(par.D_cl)+par.D_cl_m;
		K_fix=Double.toString(par.K_fix)+par.K_fix_m;
		k_fix=Double.toString(par.k_fix)+par.k_fix_m;
		c0_fa=Double.toString(par.c0_fa)+par.c0_fa_m;
		magick=Double.toString(par.magick)+par.magick_m;
		R=Double.toString(par.R)+par.R_m;
		F=Double.toString(par.F)+par.F_m;

		epsilon=Double.toString(par.epsilon)+par.epsilon_m;
	}


}
