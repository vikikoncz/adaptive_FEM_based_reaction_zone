import com.comsol.model.*;
import com.comsol.model.util.*;
import java.io.*;
import java.lang.Math;
import java.util.*;


public class physics_Nernst_Planck{

void run(Model model){


model.physics().create("chds", "DilutedSpecies", "geom1");
    model.physics("chds").feature().create("reac1", "Reactions", 1);
    model.physics("chds").feature("reac1").selection().all();
    model.physics("chds").feature().create("conc1", "Concentration", 0);
    model.physics("chds").feature("conc1").selection().set(new int[]{1});
    model.physics("chds").feature().create("conc2", "Concentration", 0);
    model.physics("chds").feature("conc2").selection().set(new int[]{2});

	model.physics("chds").field("concentration").component(new String[]{"h", "oh", "k", "cl"});
    model.physics("chds").prop("ShapeProperty").set("order_concentration", "2");
    model.physics("chds").prop("EquationForm").set("form", "Stationary");
    model.physics("chds").prop("Migration").set("Migration", "1");
    model.physics("chds").prop("Convection").set("Convection", "0");
    model.physics("chds").prop("ConvectiveTerm").set("ConvectiveTerm", "cons");
    model.physics("chds").prop("MassConsistentStabilization").set("massStreamlineDiffusion", "0");
    model.physics("chds").feature("cdm1").set("V", "phi");
    model.physics("chds").feature("cdm1").set("z", new String[][]{{"1"}, {"-1"}, {"1"}, {"-1"}});
    model.physics("chds").feature("cdm1").set("um", new String[][]{{"D_h/R/T"}, {"D_oh/R/T"}, {"D_k/R/T"}, {"D_cl/R/T"}});
    model.physics("chds").feature("cdm1").set("D_0", new String[][]{{"D_h"}, {"0"}, {"0"}, {"0"}, {"D_h"}, {"0"}, {"0"}, {"0"}, {"D_h"}});
    model.physics("chds").feature("cdm1").set("D_1", new String[][]{{"D_oh"}, {"0"}, {"0"}, {"0"}, {"D_oh"}, {"0"}, {"0"}, {"0"}, {"D_oh"}});
    model.physics("chds").feature("cdm1").set("D_2", new String[][]{{"D_k"}, {"0"}, {"0"}, {"0"}, {"D_k"}, {"0"}, {"0"}, {"0"}, {"D_k"}});
    model.physics("chds").feature("cdm1").set("D_3", new String[][]{{"D_cl"}, {"0"}, {"0"}, {"0"}, {"D_cl"}, {"0"}, {"0"}, {"0"}, {"D_cl"}});
    model.physics("chds").feature("init1").set("h", "c_h_base+(c_h_acid-c_h_base)*x/L");
    model.physics("chds").feature("init1").set("oh", "c_oh_base+(c_oh_acid-c_oh_base)*x/L");
    model.physics("chds").feature("init1").set("k", "c_k_base+(c_k_acid-c_k_base)*x/L");
    model.physics("chds").feature("init1").set("cl", "c_cl_base+(c_cl_acid-c_cl_base)*x/L");
    model.physics("chds").feature("reac1").set("R", new String[][]{{"k_reak*(K-h*oh)*magick"}, {"k_reak*(K-h*oh)*magick"}, {"0"}, {"0"}});
    model.physics("chds").feature("conc1").set("c0", new String[][]{{"c_h_base"}, {"c_oh_base"}, {"c_k_base"}, {"c_cl_base"}});
    model.physics("chds").feature("conc1").set("constraintType", "unidirectionalConstraint");
    model.physics("chds").feature("conc1").set("species", new String[][]{{"1"}, {"1"}, {"1"}, {"1"}});
    model.physics("chds").feature("conc2").set("c0", new String[][]{{"c_h_acid"}, {"c_oh_acid"}, {"c_k_acid"}, {"c_cl_acid"}});
    model.physics("chds").feature("conc2").set("constraintType", "unidirectionalConstraint");
    model.physics("chds").feature("conc2").set("species", new String[][]{{"1"}, {"1"}, {"1"}, {"1"}});



}

}
