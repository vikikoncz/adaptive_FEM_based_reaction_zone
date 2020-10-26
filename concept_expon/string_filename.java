import com.comsol.model.*;
import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;
import java.util.*;
import java.text.SimpleDateFormat;

public class string_filename{

	String get_file_name(sol sol, sol sol_t, double [] tlist1){
		int x=tlist1.length;
		double t=tlist1[x-1];
		String s=new String("c_kcl_b_"+Double.toString(sol.c_kcl_base)+"c_kcl_a_"+Double.toString(sol.c_kcl_acid)+"_U_"+Double.toString(sol.U0)+"V");
		s+="t_"+Double.toString(sol_t.c_kcl_base)+"_"+Double.toString(sol_t.c_kcl_acid)+"_"+Double.toString(sol_t.U0)+"V";
		s+="t_"+Double.toString(t);
		return s;
	}



}