import com.comsol.model.*;
import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;
import java.text.SimpleDateFormat;
import java.util.*;


public class timing{

	ArrayList<Date> dates;
	ArrayList<String> comments;

	timing(){
		dates = new ArrayList<Date>();
		comments = new ArrayList<String>();
	}


	void add(String comment, Date date){
		dates.add(date);
		comments.add(comment);
	}	

	void create_file(String name) throws IOException {

		File f=new File(name);
		FileWriter ki_stream = new FileWriter(f,true );
		PrintWriter ki = new PrintWriter(ki_stream);

		int l1 = dates.size();
		int l2 = comments.size();


		if(l1 != l2){ki.println("Ket ArrayList merete nem egyezik");}
		else{
			int l = l1;
			//WRITE HEADER
			
			for(int i=0; i<l; i++){
				ki.print(comments.get(i)+"\t");
				ki.print((dates.get(i)).toString()+"\t");
				ki.print(diff_first(i)+"\t");
				ki.println(diff_previous(i));
			}		


		}  
		

		
		ki.close();
	}


	int diff_first(int i){

		if(i==0){return 0;}
		else{
			Date first = dates.get(0);
			Date current = dates.get(i);
			int diff = calculate_time_diff(first, current);
			return diff; 
		}
	}


	int diff_previous(int i){

		if(i==0){return 0;}
		else{
			Date previous = dates.get(i-1);
			Date current = dates.get(i);
			int diff = calculate_time_diff(previous, current);
			return diff; 
		}

	}


	int calculate_time_diff(Date date1, Date date2){
		
		String d1 = date1.toString();
		//System.out.println(d1);

		d1=d1.replaceAll(":"," ");	
		//System.out.println(d1);

		String [] tomb1 = d1.split(" ");
		//System.out.println(tomb1.length);
	

		String month1 = tomb1[1];
		int day1 = Integer.parseInt(tomb1[2]);
		int hour1 = Integer.parseInt(tomb1[3]);
		int min1 = Integer.parseInt(tomb1[4]);
		int sec1 = Integer.parseInt(tomb1[5]);
		int year1 = Integer.parseInt(tomb1[7]);
	
		//System.out.println("month: " + month1);
		//System.out.println("day: " + day1);
		//System.out.println("hour: " + hour1);
		//System.out.println("min: " + min1);
		//System.out.println("sec: " + sec1);
		//System.out.println("year: " + year1);

		String d2 = date2.toString();
	
		//System.out.println(d2);
	
		d2=d2.replaceAll(":"," ");	
		//System.out.println(d2);

		String [] tomb2 = d2.split(" ");
		//System.out.println(tomb2.length);	

		String month2 = tomb2[1];
		int day2 = Integer.parseInt(tomb2[2]);
		int hour2 = Integer.parseInt(tomb2[3]);
		int min2 = Integer.parseInt(tomb2[4]);
		int sec2 = Integer.parseInt(tomb2[5]);
		int year2 = Integer.parseInt(tomb2[7]);
	
		//System.out.println("month: " + month2);
		//System.out.println("day: " + day2);
		//System.out.println("hour: " + hour2);
		//System.out.println("min: " + min2);
		//System.out.println("sec: " + sec2);
		//System.out.println("year: " + year2);


		//Calculate difference
		int diff = 0;

		if((year2 - year1) <= 1){   //valszeg szilveszterkor nem lesznek szimulaciok
		if(month1.equals(month2)){
			if(day1 == day2){
				if(hour1 == hour2){
					if(min1 == min2){
						diff=sec2 - sec1;
					}
					else{
						diff=(min2 - min1 - 1)*60 + sec2 + (60-sec1);
					}
				}
				else{
					diff = (hour2 - hour1 - 1)*3600 + min2 * 60 + sec2 + (60-sec1) + (59 - min1)*60;  
				}

			}
			else{
				diff=(day2-day1-1)*24*3600 + hour2*3600 + min2*60 + sec2 + (23-hour1)*3600 + (59-min1)*60 + (60-sec1); 
			}
		}
		else{
			int maxday_1 = getmonth_day(month1, year1);
			int ds=24*3600;
			diff=(maxday_1-day1-1)*ds+(day2-1)*ds+hour2*3600+min2*60+sec2+(23-hour1)*3600+(59-min1)*60+(60-sec1); 
		}
		}

		//System.out.println("diff="+diff);

		return diff;
	}	


	int getmonth_day(String month, int year){

		int day = 0;
		if(month.equals("Jan")){day = 31; return day;} //Januar
		if(month.equals("Feb")){day = 28; if((year%4)==0){day=29;} return day;} //Februar     itt figyelni a szokoevre
		if(month.equals("Mar")){day = 31; return day;} //Marcius
		if(month.equals("Apr")){day = 30; return day;} //Aprilis
		if(month.equals("May")){day = 31; return day;} //Majus
		if(month.equals("Jun")){day = 30; return day;} //Junius
		if(month.equals("Jul")){day = 31; return day;} //Julius
		if(month.equals("Aug")){day = 31; return day;} //Augusztus
		if(month.equals("Sep")){day = 30; return day;} //Szeptember
		if(month.equals("Oct")){day = 31; return day;} //Oktober
		if(month.equals("Nov")){day = 30; return day;} //November
		if(month.equals("Dec")){day = 31; return day;} //December

		return day;
	}

}
