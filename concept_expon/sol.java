import com.comsol.model.*;
import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;

public class sol{
	double c_koh;
	double c_hcl;
	double c_kcl_base;
	double c_kcl_acid;
	double U0;
	
	//Konstruktor

	sol(double c_koh, double c_hcl, double c_kcl_base, double c_kcl_acid, double U0){
		this.c_koh=c_koh;
		this.c_hcl=c_hcl;
		this.c_kcl_base=c_kcl_base;
		this.c_kcl_acid=c_kcl_acid;
		this.U0=U0;
	
	}

}
