import com.comsol.model.*;
import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;

public class data{
	double current_stat;
	double [][] current_time1;
		double [][][] result_stationary;
	double [][][] result_time1;
	
	double [] x_gel;
	
	//Konstruktor
	data(double current_stat,double [][] current_time1,double [][][] result_stationary,double [][][] result_time1,double [] x_gel){
			this.current_stat=current_stat;
			this.current_time1=current_time1;
			
			this.result_stationary=result_stationary;
			this.result_time1=result_time1;
			
			this.x_gel=x_gel;
}

	void set(double [][] current_time, double [][][] result_time){		
			current_time1=current_time;
			result_time1=result_time;
			
			}
		
}
