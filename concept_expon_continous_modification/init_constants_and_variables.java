import com.comsol.model.*;
import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;
import java.util.*;

/*Modell-t megkapva elvegzi a beallitasokat*/
public class init_constants_and_variables{


/**/
void run(Model model, double [][] gel, double [][] gel_t, parameters par, double U, double U_t){

	double c_h_base=gel[0][0]*1000;
double c_oh_base=gel[0][1]*1000;
double c_k_base=gel[0][2]*1000;
double c_cl_base=gel[0][3]*1000;

double c_h_acid=gel[1][0]*1000;
double c_oh_acid=gel[1][1]*1000;
double c_k_acid=gel[1][2]*1000;
double c_cl_acid=gel[1][3]*1000;

double c_h_t_base=gel_t[0][0]*1000;
double c_oh_t_base=gel_t[0][1]*1000;
double c_k_t_base=gel_t[0][2]*1000;
double c_cl_t_base=gel_t[0][3]*1000;

double c_h_t_acid=gel_t[1][0]*1000;
double c_oh_t_acid=gel_t[1][1]*1000;
double c_k_t_acid=gel_t[1][2]*1000;
double c_cl_t_acid=gel_t[1][3]*1000;

parameters_string par_s=new parameters_string(par);

	//Parameters

	
	model.param().set("k_reak", par_s.k_reak );
    model.param().set("magick",par_s.magick );
    model.param().set("K", par_s.K);
    model.param().set("L", par_s.L, "g\u00e9l (\u00f6sszek\u00f6t\u0151elem hossza)");
    model.param().set("R", "R_const");
    model.param().set("T", par_s.T);
	
	model.param().set("D_h", par_s.D_h);
    model.param().set("D_oh", par_s.D_oh);
    model.param().set("D_k", par_s.D_k);
    model.param().set("D_cl", par_s.D_cl);
	

//from U to string
	String u_acid=Double.toString(U)+" [V]";
	String u_acid_t=Double.toString(U_t)+" [V]";	
    //model.param().set("u_acid", "10 [V]");
	//model.param().set("u_acid_t", "10 [V]");
	model.param().set("u_acid", u_acid);
	model.param().set("u_acid_t", u_acid_t);
    model.param().set("u_base", "0 [V]");
	
	System.out.println("u_acid="+u_acid);
	System.out.println("u_acid_t="+u_acid_t);

	//model.param().set("epsilon", "epsilon0_const");
    model.param().set("epsilon", par_s.epsilon);	
    model.param().set("F", "F_const");
    model.param().set("c0_fa", par_s.c0_fa, "total concentration of fix charge");
    model.param().set("K_fix", par_s.K_fix, "dissociation rate of fix charge");
    model.param().set("k_fix", par_s.k_fix);

	    
	model.param().set("c_h_acid", c_h_acid);
    model.param().set("c_oh_acid", c_oh_acid);
    model.param().set("c_k_acid", c_k_acid);
    model.param().set("c_cl_acid", c_cl_acid);
    
	model.param().set("c_h_t_base", c_h_t_base);
    model.param().set("c_oh_t_base", c_oh_t_base);
    model.param().set("c_k_t_base", c_k_t_base);
    model.param().set("c_cl_t_base", c_cl_t_base);
    
	model.param().set("c_h_base", c_h_base);
    model.param().set("c_oh_base", c_oh_base);
    model.param().set("c_k_base", c_k_base);
    model.param().set("c_cl_base", c_cl_base);
    
	model.param().set("c_h_t_acid", c_h_t_acid);
    model.param().set("c_oh_t_acid", c_oh_t_acid);
    model.param().set("c_k_t_acid", c_k_t_acid);
    model.param().set("c_cl_t_acid", c_cl_t_acid);

//	model.param().set("u_modulation_center", "(u_acid+u_acid_t)/2");
//    model.param().set("u_modulation_amplitude", "(u_acid-u_acid_t)/2");


//Variables
	model.variable().create("var1");
    model.variable("var1").model("mod1");
    model.variable("var1").set("c_fa", "K_fix*c0_fa/(h+K_fix)", "concentration of the fix charge");
    model.variable("var1").set("J", "F*(chds.tfluxx_h-chds.tfluxx_oh+chds.tfluxx_k-chds.tfluxx_cl)", "current");
	
	//model.variable("var1").set("k_reak", par.k_reak);

	//Variables for the Adaptive MESH
	model.variable("var1").set("h_szor_oh", "abs(h*oh)", "[H][OH]");
		
	
    model.variable("var1").selection().geom("geom1", 1);
		
    model.variable("var1").selection().all();
	model.variable("var1").name("Variables 1a");
	//model.variable("var1").name("Variables 1a");	


//Step function
//	model.func().create("step1", "Step");
//    model.func("step1").model("mod1");
//    model.func("step1").set("location", "0.05");
//    model.func("step1").set("from", "u_acid");
//    model.func("step1").set("to", "u_acid_t");
//	model.func("step1").set("smooth", "0.1");

//Wave function
//SIN modulation
//	model.func().create("wv1", "Wave");
//    model.func("wv1").model("mod1");
//	model.func("wv1").set("amplitude", "u_modualtion_amplitude");
//    model.func("wv1").set("freq", "2*pi/400");
//    model.func("wv1").set("phase", "pi/2");
//    model.func("wv1").set("amplitude", "u_modulation_amplitude");	
}
}
