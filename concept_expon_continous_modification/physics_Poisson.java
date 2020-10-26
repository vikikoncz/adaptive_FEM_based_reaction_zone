import com.comsol.model.*;
import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;
import java.util.*;


public class physics_Poisson{


void run(Model model){

	model.physics().create("poeq", "PoissonEquation", "geom1");
    model.physics("poeq").feature().create("dir1", "DirichletBoundary", 0);
    model.physics("poeq").feature("dir1").selection().set(new int[]{1});
    model.physics("poeq").feature().create("dir2", "DirichletBoundary", 0);
    model.physics("poeq").feature("dir2").selection().set(new int[]{2});
    
    model.physics("poeq").name("PDE");
    model.physics("poeq").field("dimensionless").component(new String[]{"phi"});
    model.physics("poeq").feature("peq1").set("f", "F*(h-oh+k-cl-c_fa)");
    model.physics("poeq").feature("peq1").set("c", "epsilon");
    model.physics("poeq").feature("init1").set("phi", "u_base+(u_acid-u_base)*x/L");
    model.physics("poeq").feature("dir1").set("r", "u_base");
    model.physics("poeq").feature("dir2").set("r", "u_acid");

}

}
