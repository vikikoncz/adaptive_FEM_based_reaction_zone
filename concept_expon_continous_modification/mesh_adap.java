//import com.comsol.model.*;
//import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;



/*Egesz MESH athelyezes keretet adja, miutan STOP CONDITION miatt megallt a solver
1. ez alapjan logolas
	- mesh_adap_logolas.java : logolas megfelelo adatainak az osszevadaszasa -> tombben atadja mesh_write.java osztalynak
	- mesh_write.java : tenyleges, a tombben megkapott, levadaszott parameterek kiirasat fajlba 
2. H*OH-bol irany-t meg kell hatarozni
3. mesh_logika -> uj parameterek meghatarozasa
4. ezekkel mesh_settings updatelese
5. mesh_settings parameterei alapjan uj MESH generalas : mesh.java

6. Ezen az osztalyon kivul (ugras_prototipus.java) kell az uj parameterekkel a COMSOL Model-t RESET-elni 
*/

/*LOG: valamilyen parameterekkel meddig futott a solver
igy ez kell, h legyen az 1. lepes!!! 
*/

public class mesh_adap {
	
	mesh_adap_logolas mesh_adap_logolas;  //ezekbol csak ez tart fent 1 peldanyt
	mesh_logika mesh_logika;	//ezekbol csak ez tart fent 1 peldanyt	
	mesh_settings mesh_settings;
	mesh mesh;	
	datas_for_new_mesh datas_for_new_mesh;

//Kontruktor	
mesh_adap(mesh_settings mesh_settings, mesh mesh, datas_for_new_mesh datas_for_new_mesh)
{
	this.mesh_settings=mesh_settings;
	this.mesh=mesh;
	this.datas_for_new_mesh=datas_for_new_mesh;	
	mesh_logika=new mesh_logika(mesh_settings.ppb1_x, mesh_settings.ppb2_x, mesh_settings.x_kezdeti_pont, mesh_settings.x_vegpont, datas_for_new_mesh, mesh_settings.mesh_adap_faktor, mesh_settings.ratio, mesh_settings.delta_x);   
	mesh_adap_logolas=new mesh_adap_logolas();
}


void step() throws IOException{
	//1. LOGOLAS
	mesh_adap_logolas.new_log();   //TODO new_log(); metodus
	//3. uj MESH parametereinek a meghatarozasa
	mesh_logika.run();	//TODO run(); metodus
		//buta parametereket, amiket ez meghataroz, ezt o maga tarolja
	//4. mesh_settings update-elese 
	mesh_settings.reset_mesh_parameters(mesh_logika.ppb1_x, mesh_logika.ppb2_x, mesh_logika.x_kezdeti_pont, mesh_logika.x_vegpont, mesh_logika.ratio, mesh_logika.delta_x, mesh_logika.mesh_adap_faktor);
	//5. uj mesh settings parameterekkel uj mesh generalas
	mesh.generate();  //TODO mesh-nek legyen generate metodusa
	mesh.write_mesh_vtx_FILE();
	mesh.write_mesh_vtx_density_FILE();
	mesh.check_vtx();
}

void set_ppb_values(double h_oh_ppb1, double h_oh_ppb2){
	mesh_logika.set_ppb_values(h_oh_ppb1, h_oh_ppb2);
}


}
