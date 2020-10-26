import com.comsol.model.*;
import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;

public class proba_normalis {

  public  double [][] run(sol sol,parameters par) {
    Model model = ModelUtil.create("Model");
    

    //model.modelPath("/home/matlab/Viki_nyar");

    //model.name("hatarfeltetel_proba.mph");

//Parameters:
double K=par.K;
double R=par.R;
double F=par.F;
double T=par.T;
double c_koh=sol.c_koh;
double c_hcl=sol.c_hcl;
double c_kcl_acid=sol.c_kcl_acid;
double c_kcl_base=sol.c_kcl_base;

double c_h_acid=c_hcl+1e-7;
double c_oh_acid=K/c_h_acid;
double c_k_acid=c_kcl_acid;
double c_cl_acid=c_hcl+c_kcl_acid;
double c_oh_base=c_koh+1e-7;
double c_h_base=K/c_oh_base;
double c_k_base=c_koh+c_kcl_base;
double c_cl_base=c_kcl_base;

//Parameters
    
    model.param().set("c_h_acid", c_h_acid);
    model.param().set("c_oh_acid", c_oh_acid);
    model.param().set("c_k_acid", c_k_acid);
    model.param().set("c_cl_acid", c_cl_acid);
    model.param().set("c_h_base", c_h_base);
    model.param().set("c_oh_base", c_oh_base);
    model.param().set("c_k_base", c_k_base);
    model.param().set("c_cl_base", c_cl_base);
    model.param().set("K", K);
    model.param().set("Ks", par.K_fix);
    model.param().set("c0", par.c0_fa);
    model.param().set("a_acid", "c_k_acid/c_h_acid+1");
    model.param().set("b_acid", "c_k_acid/c_h_acid*Ks+Ks");
    model.param().set("c_acid", "-K-c_cl_acid*c_h_acid-Ks*c0");
    model.param().set("d_acid", "-K*Ks-c_cl_acid*c_h_acid*Ks");
    model.param().set("a_base", "Ks+Ks*c_cl_base/c_oh_base");
    model.param().set("b_base", "K+c_cl_base*c_h_base+Ks*c0");
    model.param().set("c_base", "-Ks*c_k_base*c_oh_base-K*Ks");
    model.param().set("d_base", "-K*c_k_base*c_oh_base-K^2");

    model.modelNode().create("mod1");

    
    model.physics().create("ge", "GlobalEquations");

    
//ODE-DAE
    model.physics("ge").feature("ge1")
         .set("name", new String[][]{{"x"}, {"y"}});
    model.physics("ge").feature("ge1")
         .set("equation", new String[][]{{"a_acid*x^3+b_acid*x^2+c_acid*x+d_acid"}, {"a_base*y^3+b_base*y^2+c_base*y+d_base"}});
    model.physics("ge").feature("ge1")
         .set("initialValueU", new String[][]{{"1"}, {"1"}});
    model.physics("ge").feature("ge1")
         .set("initialValueUt", new String[][]{{"1"}, {"1"}});
    //model.physics("ge").feature("ge1")
    //     .set("description", new String[][]{{"c_h_gel_sav"}, {"c_oh_gel_lug"}});

//STUDY
    model.study().create("std1");
    model.study("std1").feature().create("stat", "Stationary");

//SOLVER
    model.sol().create("sol1");
    model.sol("sol1").study("std1");
    model.sol("sol1").attach("std1");
    model.sol().create("sol2");
    model.sol("sol1").feature().create("st1", "StudyStep");
    model.sol("sol1").feature().create("v1", "Variables");
    model.sol("sol1").feature().create("s1", "Stationary");
    model.sol("sol1").feature("s1").feature().create("fc1", "FullyCoupled");
    model.sol("sol1").feature("s1").feature().remove("fcDef");

    
    

    model.sol("sol1").feature("st1").name("Compile Equations: Stationary");
    model.sol("sol1").feature("st1").set("studystep", "stat");
    model.sol("sol1").feature("v1").set("control", "stat");
    model.sol("sol1").feature("s1").feature("fc1").set("maxiter", "100");
    model.sol("sol1").runAll();

//Get the results
	String tomb[]={"mod1.x","mod1.y"};		 
	
	
	model.result().numerical().create("proba","Global");
	model.result().numerical("proba").set("expr", tomb);
	//model.result().numerical("proba").set("coord", vtx);
	//model.result().numerical("proba").set("solnum", parameters);
	
		
	double [][][] result = model.result().numerical("proba").getData();
	//System.out.println("result.length (expression)"+result.length);
	//System.out.println("result[0].length (solnum)"+result[0].length);
	//System.out.println("result[0][0].length (value)"+result[0][0].length);

	double c_h_acid_gel=result[0][0][0];
	double c_oh_base_gel=result[1][0][0];
	
	//System.out.println("c_h_acid_gel="+c_h_acid_gel);
	//System.out.println("c_oh_base_gel="+c_oh_base_gel);	
    
		
	double [][] gel=new double [2][5];
	/*Base*/
	gel[0][0]=K/c_oh_base_gel; //H+
	gel[0][1]=c_oh_base_gel; //OH-
	gel[0][2]=c_k_base*c_oh_base/gel[0][1]; //K+
	gel[0][3]=c_cl_base*c_h_base/gel[0][0]; //Cl-
	gel[0][4]=-R*T/F*Math.log(c_oh_base/c_oh_base_gel); 
	//gel[0][4]=-RT/F*ln(c_oh_old/c_oh_gel);     //u_base

	/*Acid*/
	gel[1][0]=c_h_acid_gel; //H+
	gel[1][1]=K/c_h_acid_gel; //OH-
	gel[1][2]=c_k_acid*c_oh_acid/gel[1][1]; //K+
	gel[1][3]=c_cl_acid*c_h_acid/gel[1][0];  //Cl-
	gel[1][4]=-R*T/F*Math.log(c_h_acid/c_h_acid_gel);
	//gel[1][4]=-RT/F*ln(c_h_old/c_h_gel);	//u_acid

	


  //System.out.println("Solved");

    return gel;
  }
  }


