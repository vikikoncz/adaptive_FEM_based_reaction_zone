/*
 * impulzus_prototipus2.java
 */
//ADAPTIVE MESH!!!
 
import com.comsol.model.*;
import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;
import java.text.SimpleDateFormat;
import java.util.*;


/** Model exported on Jun 19 2012, 11:58 by COMSOL 4.2.1.166. */
public class ugras_prototipus2 {

  public static void main(String[] args) throws IOException {

//****************** TIMING - kezdet
timing timing = new timing();
timing.add("Kezdet", new Date());

	//Parameters
double c_koh=0.1;
double c_hcl=0.1;
double c_kcl_base=Double.parseDouble(args[0]);
double c_kcl_acid=Double.parseDouble(args[1]);
double U0=Double.parseDouble(args[2]);

System.out.println("HELLO");

double c_koh_t=0.1;
double c_hcl_t=0.1;
double c_kcl_base_t=Double.parseDouble(args[3]);
double c_kcl_acid_t=Double.parseDouble(args[4]);
double U0_t=Double.parseDouble(args[5]);
int function_type = Integer.parseInt(args[6]);
double ppb1_x = Double.parseDouble(args[7]);
double ppb2_x = Double.parseDouble(args[8]);
double ratio = Double.parseDouble(args[9]);
double x_kezdeti_pont = Double.parseDouble(args[10]);
double x_vegpont = Double.parseDouble(args[11]);
int N_mesh = Integer.parseInt(args[12]);
double delta_x = Double.parseDouble(args[13]);
double mesh_adap_faktor = Double.parseDouble(args[14]);

mesh_settings mesh_settings = new mesh_settings(function_type, ppb1_x, ppb2_x, ratio, x_kezdeti_pont, x_vegpont, N_mesh, delta_x, mesh_adap_faktor);

//Debug
System.out.println("c_kcl_base:"+c_kcl_base);
System.out.println("c_kcl_acid:"+c_kcl_acid);
System.out.println("U0:"+U0);
System.out.println("U0:"+Math.round(U0));
System.out.println("c_kcl_base_t:"+c_kcl_base_t);
System.out.println("c_kcl_acid_t:"+c_kcl_acid_t);
System.out.println("U0_t:"+U0_t);
System.out.println("function_type:"+function_type);
System.out.println("ppb1_x:"+ppb1_x);
System.out.println("ppb2_x:"+ppb2_x);
System.out.println("ratio:"+ratio);
System.out.println("x_kezdeti_pont:"+x_kezdeti_pont);  
System.out.println("x_vegpont:"+x_vegpont);
System.out.println("N_mesh:"+N_mesh);
System.out.println("delta_x:"+delta_x);
System.out.println("mesh_adap_faktor:"+mesh_adap_faktor);

  parameters par=new parameters();	
//solba csak azer van benne az U0, h konnyen ki lehessen irni fajlba
sol sol=new sol(c_koh,c_hcl,c_kcl_base,c_kcl_acid, U0);
sol sol_t=new sol(c_koh_t,c_hcl_t,c_kcl_base_t,c_kcl_acid_t, U0_t);

double [][] gel=new proba_normalis().run(sol,par);
double [][] gel_t=new proba_normalis().run(sol_t,par);
  
//U-kat meg itt kell modositani
// U=U0-u_base+u_acid
	double U=U0-gel[0][4]+gel[1][4];
	double U_t=U0_t-gel_t[0][4]+gel_t[1][4];

//*********************** TIMING - hatarfeltelek szamitasa
timing.add("Hatarfeltetelek szamitasa", new Date());

run(gel,gel_t,par, sol, sol_t,U, U_t, timing, mesh_settings);
  
//**************  TIMING - FINITO
timing.add("Finito", new Date());

//kiszamitani TIMING - ok alapjan az egeszet     //TODO file-nevet valahogy megoldani
timing.create_file("timing_log.dat");  
  }

  public static Model run(double [][] gel, double [][] gel_t,parameters par, sol sol, sol sol_t, double U, double U_t, timing timing, mesh_settings mesh_settings) throws IOException {
//Atiranyitas a standard kimenetrol file-be
//ez nem kell, amugysem mukodik
		


//Model
    Model model = ModelUtil.create("Model");
	
    model.modelNode().create("mod1");
    model.modelNode("mod1").sorder("quadratic");
try{

//Geometry
    model.geom().create("geom1", 1);		
    model.geom("geom1").lengthUnit("mm");
    model.geom("geom1").scaleUnitValue(true);
    model.geom("geom1").feature().create("i1", "Interval");
	model.geom("geom1").feature("i1").set("intervals", "one");
    model.geom("geom1").feature("i1").set("p1", "0");
    model.geom("geom1").feature("i1").set("p2", par.L);	
    model.geom("geom1").run();


    //Setting parameters and variables
init_constants_and_variables cons_and_vars=new init_constants_and_variables();
cons_and_vars.run(model, gel, gel_t, par, U, U_t);


//View
/*
    model.view("view1").set("showlabels", true);
    model.view("view1").axis().set("xmin", "-0.48786139488220215");
    model.view("view1").axis().set("xmax", "1.4878612756729126");
    model.view("view1").axis().set("xextra", new String[]{});
*/

	System.out.println("PhD_2");



//MESH
    //MESH 
/*System.out.println("function_type:"+function_type);
//System.out.println("ppb1_x:"+ppb1_x);
System.out.println("ppb2_x:"+ppb2_x);
System.out.println("ratio:"+ratio);
System.out.println("x_kezdeti_pont:"+x_kezdeti_pont);  
System.out.println("x_vegpont:"+x_vegpont);
System.out.println("N_mesh:"+N_mesh);
*/

//TODO: itt fogjuk majd atadni a parametereket: 
	
	mesh mesh = new mesh(mesh_settings, par);
	mesh.generate();

	System.out.println("BARCELONA!!!");		

//Mesh2 to the stationary solver 
	
 	
	model.mesh().create("mesh2", "geom1");
    	model.mesh("mesh2").data().setElem("edg", mesh.edge);
	model.mesh("mesh2").data().setVertex(mesh.vtx_real);
	model.mesh("mesh2").data().createMesh();   

	//mesh mesh_current=new mesh();
	//mesh_current=mesh2;

	System.out.println("PhD_3");
	
//Physics
//Nernst-Planck
    
	physics_Nernst_Planck NP=new physics_Nernst_Planck();
	NP.run(model);

//Poisson
    	physics_Poisson P=new physics_Poisson();
	P.run(model);

	
	model.probe().create("pdom1", "DomainPoint");
    model.probe("pdom1").model("mod1");
    model.probe("pdom1").setIndex("coords1", mesh_settings.ppb1_x, 0, 0);
    model.probe("pdom1").feature("ppb1").set("expr", "abs(h*oh)");
	
	model.probe().create("pdom2", "DomainPoint");
    model.probe("pdom2").model("mod1");
	model.probe("pdom2").setIndex("coords1", mesh_settings.ppb2_x, 0, 0);
    model.probe("pdom2").feature("ppb2").set("expr", "abs(h*oh)");
	

/***********************************************************************************************************
************************************************************************************************************
************************************************************************************************************
************************************************************************************************************/

//Study - Study-steps

	model.study().create("std1");

//Step 1 : Stationary (to get the initial conditions of the time-dependent simulation)

double [] plist={1e-10, 1.01e-10, 1.1e-10, 1e-9, 1e-8, 1e-7, 2e-7, 3e-7, 1e-6, 1.01e-6, 1e-5, 1e-4, 2e-4, 4e-4, 6e-4, 8e-4 ,1e-3, 1e-2, 1e-1, 0.12, 0.14, 0.16, 0.18, 0.2, 0.22, 0.24, 0.3, 0.4, 0.5, 0.55, 0.6, 0.8, 1};

//double [] plist={1e-10, 1.01e-10, 1.1e-10, 1e-9};    


    model.study("std1").feature().create("stat1", "Stationary");
    model.study("std1").feature("stat1").set("geomselection", "geom1");
    model.study("std1").feature("stat1").set("mesh", new String[]{"geom1", "mesh2"});
    model.study("std1").feature("stat1").set("useparam", "on");
    model.study("std1").feature("stat1").set("pname", new String[]{"magick"});
    model.study("std1").feature("stat1").set("plist", plist);
    model.study("std1").feature("stat1").set("plot", "on");
    model.study("std1").feature("stat1").set("probesel", "manual");
	
	System.out.println("PhD_5");

//Step 2 : Time-dependent 

  double [] tlist1={0, 0.001, 0.005, 0.01, 0.02, 0.04, 0.06, 0.08, 0.1, 0.15, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.81, 0.82, 0.83, 0.84, 0.85, 0.86, 0.87, 0.88, 0.89, 0.9, 0.91, 0.92, 0.93, 0.94, 0.95, 0.96, 0.97, 0.98, 0.99, 1, 1.005, 1.01, 1.015, 1.02, 1.025, 1.03, 1.035, 1.04, 1.045, 1.05, 1.055, 1.06, 1.065, 1.07, 1.075, 1.08, 1.085, 1.09, 1.095, 1.1, 1.105, 1.11, 1.115, 1.12, 1.125, 1.13, 1.135, 1.14, 1.145, 1.15, 1.155, 1.16, 1.165, 1.17, 1.175, 1.18, 1.185, 1.19, 1.195, 1.2, 1.205, 1.21, 1.215, 1.22, 1.225, 1.23, 1.235, 1.24, 1.245, 1.25, 1.255, 1.26, 1.265, 1.27, 1.275, 1.28, 1.285, 1.29, 1.295, 1.3, 1.305, 1.31, 1.315, 1.32, 1.325, 1.33, 1.335, 1.34, 1.345, 1.35, 1.355, 1.36, 1.365, 1.37, 1.375, 1.38, 1.385, 1.39, 1.395, 1.4, 1.425, 1.45, 1.475, 1.5, 1.525, 1.55, 1.575, 1.6, 1.625, 1.65, 1.675, 1.7, 1.725, 1.75, 1.775, 1.8, 1.825, 1.85, 1.875, 1.9, 1.925, 1.95, 1.975, 2, 2.2, 2.4, 2.6, 2.8, 3, 3.2, 3.4, 3.6, 3.8, 4, 4.2, 4.4, 4.6, 4.8, 5, 5.5, 6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10, 10.5, 11, 11.5, 12, 12.5, 13, 13.5, 14, 14.5, 15, 15.5, 16, 16.5, 17, 17.5, 18, 18.5, 19, 19.5, 20, 20.5, 21, 21.5, 22, 22.5, 23, 23.5, 24, 24.5, 25, 25.5, 26, 26.5, 27, 27.5, 28, 28.5, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100, 105, 110, 115, 120, 125, 130, 135, 140, 145, 150};
//	double [] tlist1={0, 0.001, 0.005, 0.01, 0.02, 0.04, 0.06, 0.08, 0.1, 0.15, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1, 1.2, 1.4, 1.6, 1.8, 2, 2.2, 2.4, 2.6, 2.8, 3, 3.2, 3.4, 3.6, 3.8, 4, 4.2, 4.4, 4.6, 4.8, 5, 5.5, 6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10, 10.5, 11, 11.5, 12, 12.5, 13, 13.5, 14, 14.5, 15, 15.5, 16, 16.5, 17, 17.5, 18, 18.5, 19, 19.5, 20, 20.5, 21, 21.5, 22, 22.5, 23, 23.5, 24, 24.5, 25, 25.5, 26, 26.5, 27, 27.5, 28, 28.5, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100, 105, 110, 115, 120, 125, 130, 135, 140, 145, 150};	
 //double [] tlist1={0, 1.5};

int tlist1_length=tlist1.length;

    model.study("std1").feature().create("time", "Transient");
    model.study("std1").feature("time").set("geomselection", "geom1");
    
    model.study("std1").feature("time").set("tlist", tlist1);

	System.out.println("PhD_6");
	
	
//Solvers
//Solver3 : contains steps 1, 2

    model.sol().create("sol3");
    model.sol("sol3").study("std1");
    model.sol("sol3").attach("std1");

//Stationary solver
    model.sol("sol3").feature().create("st1", "StudyStep");
    model.sol("sol3").feature().create("v1", "Variables");
    model.sol("sol3").feature().create("s1", "Stationary");
    model.sol("sol3").feature("s1").feature().create("fc1", "FullyCoupled");
    model.sol("sol3").feature("s1").feature().create("d1", "Direct");
    model.sol("sol3").feature("s1").feature().create("p1", "Parametric");
    model.sol("sol3").feature("s1").feature().remove("fcDef");
    

    model.sol("sol3").feature("st1").name("Compile Equations: Stationary 1");
    model.sol("sol3").feature("st1").set("studystep", "stat1");
    model.sol("sol3").feature("v1").set("control", "stat1");
    model.sol("sol3").feature("s1").set("nonlin", "on");
    model.sol("sol3").feature("s1").feature("dDef").set("linsolver", "pardiso");
    model.sol("sol3").feature("s1").feature("dDef").set("pardreorder", "nd");
    model.sol("sol3").feature("s1").feature("fc1").set("dtech", "hnlin");
    model.sol("sol3").feature("s1").feature("fc1").set("maxiter", "100");
    model.sol("sol3").feature("s1").feature("fc1").set("ntolfact", "5");
    model.sol("sol3").feature("s1").feature("fc1").set("probesel", "manual");
    model.sol("sol3").feature("s1").feature("d1").set("linsolver", "pardiso");
    model.sol("sol3").feature("s1").feature("p1").set("control", "stat1");
    model.sol("sol3").feature("s1").feature("p1").set("pname", new String[]{"magick"});
    model.sol("sol3").feature("s1").feature("p1").set("plist", plist);
    model.sol("sol3").feature("s1").feature("p1").set("plot", "on");
    model.sol("sol3").feature("s1").feature("p1").set("probesel", "manual");
    
	System.out.println("PhD_7");

//Time-dependent solver
model.sol("sol3").feature().create("st2", "StudyStep");
    model.sol("sol3").feature().create("v2", "Variables");

model.sol("sol3").feature().create("t1", "Time");
    model.sol("sol3").feature("t1").feature().create("taDef", "TimeAdaption");    

model.sol("sol3").feature("st2").name("Compile Equations: Time Dependent (2)");
    model.sol("sol3").feature("st2").set("studystep", "time");
    model.sol("sol3").feature("v2").set("initmethod", "sol");
    model.sol("sol3").feature("v2").set("initsol", "sol3");
    model.sol("sol3").feature("v2").set("notsolmethod", "sol");
    model.sol("sol3").feature("v2").set("notsol", "sol3");
    model.sol("sol3").feature("t1").set("control", "time");
//Majd mindig a tlist1-et kell atirni	
    model.sol("sol3").feature("t1").set("tlist", tlist1);
    model.sol("sol3").feature("t1").feature("dDef").set("linsolver", "spooles");

	model.sol("sol3").feature("t1").feature("fcDef").set("dtech", "auto");
    model.sol("sol3").feature("t1").feature().remove("taDef");
/**************************************************************/
//Here you can modify the tolerance values of the TD-solver... just try!
//with previous mesh
	model.sol("sol3").feature("t1").set("atolglobal", "0.000010");
    model.sol("sol3").feature("t1").set("fieldselection", "mod1_oh");

    //model.name("fixch_strong_ez_talan_OK2_Model.mph");




	
	System.out.println("PhD_8");
	
	
	
	
/*
************************************************************************************
********************************************************************************
*/
//Must solve the model to get results

//Settings about the profiles to have figuers
int p=500; //number of points
double h=par.L/500;

double [] x_gel=new double[p+1];
for(int i=0; i<=p; i++){
	x_gel[i]=i*h;
}

//Step 1
//Solve the stationary model

	System.out.println("PhD_9");
//**************** TIMING - comsol modell epites
timing.add("COMSOL modell epites", new Date());
	
	model.sol("sol3").run("s1");

//**************** TIMING - stacionarius solver
timing.add("Stacionarius solver", new Date());

//Getting the stationary results (the initial condition of the transient analysis) 1st time-dependent solver uses the same sol3 (resrite the datas must be avoided)

	/*Arrays for the interpolation*/
	String tomb[]={"h","oh","k","cl","c_fa","phi"};	
	int [] parameters={plist.length-1};  //the last magic factor

	model.result().numerical().create("stationary","Interp");
	model.result().numerical("stationary").set("expr", tomb);
	model.result().numerical("stationary").set("coord", x_gel);
	model.result().numerical("stationary").set("solnum", parameters);
	
	
	double [][][] result_stationary = model.result().numerical("stationary").getData();

	System.out.println("result_stationary.length (expression)"+result_stationary.length);
	System.out.println("result_stationary[0].length (t_list))"+result_stationary[0].length);
	System.out.println("result_stationary[0][0].length (coordinates)"+result_stationary[0][0].length);

	

	//Currents (integration)
	model.result().numerical().create("int1", "IntLine");
    	
	model.result().numerical("int1").set("innerinput", "manual");
    	model.result().numerical("int1").set("solnum", parameters);

	model.result().numerical("int1").set("expr", "J");
    	//model.result().numerical("int1").set("unit", "A/m");
    	//model.result().numerical("int1").set("descractive", true);
    	//model.result().numerical("int1").set("descr", "postint");
    	model.result().numerical("int1").selection().all();
	model.result().numerical("int1").run();

	double [][] current=model.result().numerical("int1").getReal();

	double current_stat=current[0][0]*1000; //ez a stat aram micro_amperben

	System.out.println("current_stat"+current_stat);
	

	//Get the variables for the proper MESH adjustment
	String tomb_mesh[]={"h_szor_oh"};
	
	model.result().numerical().create("for_mesh","Interp");
	model.result().numerical("for_mesh").set("expr", tomb_mesh);
	//These must be reset after each evaluation
	model.result().numerical("for_mesh").set("coord", mesh.vtx_real);
	model.result().numerical("for_mesh").set("solnum", parameters);
	
	
	double [][][] result_mesh = model.result().numerical("for_mesh").getData();

	System.out.println("result_mesh.length (expression)"+result_mesh.length);
	System.out.println("result_mesh[0].length (t_list))"+result_mesh[0].length);
	System.out.println("result_mesh[0][0].length (coordinates)"+result_mesh[0][0].length);
	
	datas_for_new_mesh datas_for_new_mesh = new datas_for_new_mesh();
	datas_for_new_mesh.set_hoh(result_mesh, mesh.vtx_real);

	
	
	model.mesh().create("mesh1", "geom1");


	//Get the variables to track the derivatives in the rection zone
	String tomb_derivatives[]={"hx","ohx","kx","clx","phix","phixx","hxx","ohxx","kxx","clxx","ht","oht","kt","clt","phit","h","oh","k","cl","c_fa","phi"}; 
	
	model.result().numerical().create("derivatives","Interp");
	model.result().numerical("derivatives").set("expr", tomb_derivatives);
	//These must be reset after each evaluation
	model.result().numerical("derivatives").set("coord", mesh.vtx_real);
	model.result().numerical("derivatives").set("solnum", parameters);


	double [][][] result_derivatives = model.result().numerical("derivatives").getData();

	System.out.println("result_derivatives(expression)"+result_derivatives.length);
	System.out.println("result_derivatives[0].length (tlist)"+result_derivatives[0].length);
	System.out.println("result_derivatives[0][0].length (coordinates)"+result_derivatives[0][0].length);

	reaction_front_derivatives reaction_front_derivatives = new reaction_front_derivatives();
	reaction_front_derivatives.set_derivatives(result_derivatives, mesh.vtx_real, mesh_settings.x_kezdeti_pont, mesh_settings.x_vegpont);
	

	//ide be lehetne szurni egy reaction_front_write.write-ot!!! t=0 -val!!!

	System.out.println("Siargao 0");

	mesh_write mesh_write=new mesh_write(mesh_settings, mesh);
	reaction_front_write reaction_front_write=new reaction_front_write(datas_for_new_mesh, mesh_settings, reaction_front_derivatives);
	derivatives_profile_write derivatives_profile_write=new derivatives_profile_write(datas_for_new_mesh, mesh_settings, reaction_front_derivatives);

	System.out.println("Siargao 2");

        //TODO: stac: ezt ertelmesen atirni
	mesh_write.start(tlist1, sol, sol_t, par);
	reaction_front_write.start(tlist1, sol, sol_t, par);
	derivatives_profile_write.start(tlist1, sol, sol_t, par);

	System.out.println("Siargao 3");
	
	mesh_write.write(0);
	reaction_front_write.write(0);
        derivatives_profile_write.write(0);


	System.out.println("PhD_10_TD");
	
	
/***********************************
MESH to the TD solver
************************************/	

	
//Mesh1 to the time-dependent solvers


//Insert vertices and edges and create mesh
	model.mesh("mesh1").data().setElem("edg", mesh.edge);
	model.mesh("mesh1").data().setVertex(mesh.vtx_real);
	model.mesh("mesh1").data().createMesh();

	model.study("std1").feature("time").set("mesh", new String[]{"geom1", "mesh1"});

//Step 2
//Solve the time-dependent model
//Reset the boundary conditions
    model.physics("chds").feature("conc2").set("c0", 1, "c_h_t_acid");
    model.physics("chds").feature("conc2").set("c0", 2, "c_oh_t_acid");
    model.physics("chds").feature("conc2").set("c0", 3, "c_k_t_acid");
    model.physics("chds").feature("conc2").set("c0", 4, "c_cl_t_acid");

    model.physics("chds").feature("conc1").set("c0", 1, "c_h_t_base");
    model.physics("chds").feature("conc1").set("c0", 2, "c_oh_t_base");
    model.physics("chds").feature("conc1").set("c0", 3, "c_k_t_base");
    model.physics("chds").feature("conc1").set("c0", 4, "c_cl_t_base");
	
	model.physics("poeq").feature("dir2").set("r", "u_acid_t");

//IDE kell adaptive mesh!!!	
//Kell ketto darab point probe expr. (suru mesh ket oldalara), ezek h*oh abszoluterteket figyelik
//Stop condition: helyes megfogalmazas: VAGY
//Ha megall a solver, akkor:
/*
	1: utolso eredmenyek kinyerese? melyik data set? last time, ezt a mesh vtx-enek
		megfelelo pontokban kell
	2: eredmenyek alapjan uj mesh generalas: ez lesz a tenyleges adaptive mesh-es algo.
		uj mesh eredmenyeit is kell log-olni
	3: mesh reset
	4: ha ebben a lepesben volt olyan t, ami benne volt az eredeti tlist-ben
		akkor ki kell irni fajlba az eredmenyeket (ez csak 500 pont!)
	5: ezt a solver-t klonozni kell
	6: 	solver ujrainditasa!!!
*/ 
//ezt addig ismetelni amig last t nem nagyobb a tlist utolso t-jenel 
//Mentes bolondbiztositasa! Default mappa
//result-ban a fajlokat majd hozzafuzesre kell megnyitni!
//ez alapjan kene log file is


//Start

	//MEsh current-et a time_solver MESH-ere kell allitani
	//mesh_current=mesh1;

	System.out.println("Vagyok");
	model.sol("sol3").feature("t1").feature().create("st1", "StopCondition");
	model.sol("sol3").feature("t1").feature("st1").set("stopcond", "if(mod1.ppb1>2*1e-8 || mod1.ppb2>2*1e-8, -1, 1)");
	System.out.println("Vagyok");
/**************************************
switch off the time dependent solver 
**************************************/
//*******************  TIMING - reset BD before TD study 
timing.add("Reset BD for TD study, management", new Date());

	model.sol("sol3").runFrom("st2");
//*******************  TIMING - TD solver 1.
timing.add("TD solver 1", new Date());

	boolean td_solv=true;  //true: szamol, false: nem szamol

	
	//Get the last timestep:
	double [] t_sol=model.sol("sol3").getPVals();
	int t_sol_length=t_sol.length;
	for (int i=0; i<t_sol_length; i++){
		System.out.println(t_sol[i]);
	}
	
	double tsolver_last=t_sol[t_sol_length-1];
	double tlist1_last=tlist1[tlist1_length-1];
	
	//int tlist_eddig=0;
	
	tlist_write tlist_write=new tlist_write();
	tlist_sol_adap tlist_sol_adap=new tlist_sol_adap();

	System.out.println("Siargao 4");	

	tlist_write.set(tlist1, t_sol, tlist_sol_adap.tlist_next);

	System.out.println("Siargao 5");	

	//tlist_eddig=tlist_eddig+t_sol_length;	
			
//Get results: 
		//to write file
	model.result().numerical().create("time","Interp");
	model.result().numerical("time").set("expr", tomb);
	model.result().numerical("time").set("coord",x_gel);
	model.result().numerical("time").set("t", tlist_write.tlist_solved_in_this_step); 

	System.out.println("Siargao 6");

////*******************
// DEBUG 
////*******************	
//	for (int i=0; i<tlist_write.tlist_solved_in_this_step.length; i++){
		//System.out.println(tlist_write.tlist_solved_in_this_step[i]);
//	}
	
	System.out.println("Vagyok");
	double [][][] result_time1 = model.result().numerical("time").getData();
	
	//Currents (integration) of time_dependent solver (step2)
	model.result().numerical().create("int2", "IntLine");
    	
	model.result().numerical("int2").set("innerinput", "manual");
    	
	model.result().numerical("int2").set("expr", "J");
	model.result().numerical("int2").set("t", tlist_write.tlist_solved_in_this_step);
    	
    	model.result().numerical("int2").selection().all();
	model.result().numerical("int2").run();

	double [][] current_time1=model.result().numerical("int2").getReal();
	
	//To write file
	
	data data=new data(current_stat, current_time1,result_stationary,result_time1, x_gel);
	
	current_write current_write=new current_write();
	profile_write profile_write=new profile_write();
	
	System.out.println("Siargao 6a");
	
	//	
	t_sol=model.sol("sol3").getPVals();
	t_sol_length=t_sol.length;
	tsolver_last=t_sol[t_sol_length-1];


	current_write.start(data, tlist1, tlist_write.tlist_solved_in_this_step, sol, sol_t, par);
	profile_write.start(data, tlist1, tlist_write.tlist_solved_in_this_step, sol, sol_t, par);
	//TODO: ezek itt mar ne start-tal legyenek, hanem write-tal

        //mesh_write.start(tlist1, tlist_write.tlist_solved_in_this_step, sol, sol_t, par);
	//reaction_front_write.start(tlist1, tlist_write.tlist_solved_in_this_step, sol, sol_t, par);
	//derivatives_profile_write.start(tlist1, tlist_write.tlist_solved_in_this_step, sol, sol_t, par);

	System.out.println("Siargao 6b");
	
	model.result().numerical("derivatives").set("t", tsolver_last);
	model.result().numerical("derivatives").set("coord", mesh.vtx_real);			

	System.out.println("Siargao 6b - manila1");
	result_derivatives = model.result().numerical("derivatives").getData();

	System.out.println("Siargao 6b - manila2");
	reaction_front_derivatives.set_derivatives(result_derivatives, mesh.vtx_real, mesh_settings.x_kezdeti_pont, mesh_settings.x_vegpont);

	System.out.println("Siargao 6c");

	model.result().numerical("for_mesh").set("t", tsolver_last);
	model.result().numerical("for_mesh").set("coord", mesh.vtx_real);
	result_mesh = model.result().numerical("for_mesh").getData();
	datas_for_new_mesh.set_hoh(result_mesh, mesh.vtx_real);	


	System.out.println("Siargao 7");


	mesh_write.write(tsolver_last);
	reaction_front_write.write(tsolver_last);
	derivatives_profile_write.write(tsolver_last);
	
	//Get ppb1 and ppb2 values!!!
	model.result().numerical().create("gev1", "EvalGlobal");
	model.result().numerical("gev1").set("expr", "mod1.ppb1");
	double [][] results_ppb1=model.result().numerical("gev1").getReal();

	//ppb2
	model.result().numerical().create("gev2", "EvalGlobal");
	model.result().numerical("gev2").set("expr", "mod1.ppb2");
	double [][] results_ppb2=model.result().numerical("gev2").getReal();

	int length_y_ppb=results_ppb1[0].length-1;
	
	double h_oh_ppb1=results_ppb1[0][length_y_ppb];
	double h_oh_ppb2=results_ppb2[0][length_y_ppb];
	
	
	
	System.out.println("Vagyok");
	
//Define variables which are necessary in the next cicle	
	//mesh_adap mesh_adap=new mesh_adap(mesh1.vtx, ppb1_x, ppb2_x, kezdeti_pont, veg_pont, par);
	mesh_adap mesh_adap = new mesh_adap(mesh_settings, mesh, datas_for_new_mesh);
	mesh_adap.set_ppb_values(h_oh_ppb1, h_oh_ppb2);
	
	//ebbe kell set fgv.!!! nem a konstruktor allitja be a set es a vtx dolgokat!!!
	//ezen belul szerintem 2db fgv kell	
	
	
	
//csak integereket lehet osszehasonlitani!!!!
	System.out.println("tlist1_length:"+tlist1_length);
	System.out.println("tlist_eddig:"+tlist_write.tlist_eddig);

	System.out.println("Siargao 8");

	int i_w=0;
	
while(tlist_write.vege!=1 && td_solv) 	{
	System.out.println("Vagyok_while");
	System.out.println("tlist1_length:"+tlist1_length);
	System.out.println("Vagyok_while0");
	//System.out.println("tlist_eddig:"+tlist_eddig);
	//get results: for new mesh (vtx eredmenyek vissza, ill hogy kell stop expr. atirni!) 
	//model.result().numerical().create("time","Interp");
	System.out.println("Vagyok_while0");
	model.result().numerical("time").set("expr", tomb_mesh);
	System.out.println("Vagyok_while0");
	model.result().numerical("time").set("coord",mesh.vtx_real);
	System.out.println("Vagyok_while0");
	model.result().numerical("time").set("t", tsolver_last);
	
	System.out.println("Vagyok_while1");
	
	double [][][] result_time1_mesh = model.result().numerical("time").getData();


//PPB values for the getStrategy() method
	results_ppb1=model.result().numerical("gev1").getReal();
	results_ppb2=model.result().numerical("gev2").getReal();

	length_y_ppb=results_ppb1[0].length-1;
	
	h_oh_ppb1=results_ppb1[0][length_y_ppb];
	h_oh_ppb2=results_ppb2[0][length_y_ppb];
	
	mesh_adap.set_ppb_values(h_oh_ppb1, h_oh_ppb2);

	
	System.out.println("Vagyok_while2");
		/*ezt atadni egy fuggvenynek elozo mesh-sel egyutt*/
	i_w++;
		
	//for_mesh_current_results.set(result_time1_mesh, mesh_adap.vtx);
	datas_for_new_mesh.set_hoh(result_time1_mesh, mesh.vtx_real);
	
	mesh_adap.step();


	//ebbol mesh gener.  	
	System.out.println("Vagyok_while2a");
	String mesh_m="mesh_"+Integer.toString(i_w);

			
	model.mesh().create(mesh_m, "geom1");
	model.mesh(mesh_m).data().setElem("edg", mesh.edge);
	System.out.println("Vagyok_while2b");
	model.mesh(mesh_m).data().setVertex(mesh.vtx_real);
	System.out.println("Vagyok_while2c");
	
	/*
	int n_vtx=mesh_adap.vtx[0].length;
	for (int i=0;i<n_vtx;i++){
		System.out.println("vtx["+i+"]:"+mesh_adap.vtx[0][i]);
	}
	*/
	
	model.mesh(mesh_m).data().createMesh();
	
	System.out.println("Vagyok_while3");
	
	//clone solver
		
		String i_w_s=Integer.toString(i_w);
		System.out.println("Viki_Manila:copy solver");
		model.sol("sol3").copySolution("Copy"+i_w_s); //ennek is kell egyedi nev
	//reset mesh
		System.out.println("Vagyok_while2d");
		model.study("std1").feature("time").set("mesh", new String[]{"geom1", mesh_m});
	//reset point probe expr:
		//suru mesh zona hataratol x-re
	
		System.out.println("Vagyok_while4");
	
		model.probe("pdom1").setIndex("coords1", mesh_settings.ppb1_x, 0, 0);
		model.probe("pdom2").setIndex("coords1", mesh_settings.ppb2_x, 0, 0);
		System.out.println("mesh_adap.ppb1_x:"+mesh_settings.ppb1_x);
		System.out.println("mesh_adap.ppb2_x:"+mesh_settings.ppb2_x);
	//reset tlist
		tlist_sol_adap.set(tlist1, t_sol, tlist_write.tlist_eddig);	
		model.sol("sol3").feature("t1").set("tlist", tlist_sol_adap.tlist_next);
		
		for (int i=0; i<tlist_sol_adap.tlist_next.length; i++){
		System.out.println("tlist_next:"+tlist_sol_adap.tlist_next[i]);
			}
			
		System.out.println("tlist_eddig:"+tlist_write.tlist_eddig);	
/***************************************************		
	//restart the solver
*****************************************************/
//******************* TIMING - TD solver management i-1 
timing.add("TD management" + i_w_s, new Date());

		model.sol("sol3").runFrom("st2");
//******************* TIMING - TD solver run i
timing.add("TD solver" + Integer.toString(i_w+1), new Date());

	//get tsolver_last
		t_sol=model.sol("sol3").getPVals();
		t_sol_length=t_sol.length;
		tsolver_last=t_sol[t_sol_length-1];
		
		for (int i=0; i<t_sol_length; i++){
				System.out.println(t_sol[i]);
			}
	
	//tlist_eddig es tlist_solved_in_this_step atirasa	
			tlist_write.set(tlist1, t_sol, tlist_sol_adap.tlist_next);
			
	if(tlist_write.fajlba_irni==1){		
	//get results
			model.result().numerical("time").set("expr", tomb);
			model.result().numerical("time").set("coord",x_gel);
			model.result().numerical("time").set("t", tlist_write.tlist_solved_in_this_step);
	
			result_time1 = model.result().numerical("time").getData();
			
	//get currents data
			model.result().numerical("int2").set("innerinput", "manual");
			model.result().numerical("int2").set("expr", "J");
			model.result().numerical("int2").set("t", tlist_write.tlist_solved_in_this_step);
    		model.result().numerical("int2").selection().all();
			model.result().numerical("int2").run();

			current_time1=model.result().numerical("int2").getReal();
			
			data.set(current_time1, result_time1);
			
			current_write.write(data, tlist_write.tlist_solved_in_this_step);
			profile_write.write(data, tlist_write.tlist_solved_in_this_step);
			}


			///Derivaltak lekerese	
			model.result().numerical("derivatives").set("t", tsolver_last);
			model.result().numerical("derivatives").set("coord", mesh.vtx_real);	
			result_derivatives = model.result().numerical("derivatives").getData();
			reaction_front_derivatives.set_derivatives(result_derivatives, mesh.vtx_real, mesh_settings.x_kezdeti_pont, mesh_settings.x_vegpont);

			model.result().numerical("for_mesh").set("t", tsolver_last);
			model.result().numerical("for_mesh").set("coord", mesh.vtx_real);
			
			result_mesh = model.result().numerical("for_mesh").getData();
			datas_for_new_mesh.set_hoh(result_mesh, mesh.vtx_real);	

			mesh_write.write(tsolver_last);  //tsolver_last: last time_step; when the solver was stopped
			reaction_front_write.write(tsolver_last);
			derivatives_profile_write.write(tsolver_last);
}

			current_write.finish();
			profile_write.finish();
			mesh_write.finish();
			reaction_front_write.finish();
			derivatives_profile_write.finish();
			

}
catch(Exception e){
	System.out.println(e.getClass());
	System.out.println(e.getMessage());
}

return model;
    
  }

}
