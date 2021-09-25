package mitin.backend.system;

import mitin.backend.system.diet.DietSystemImpl;
import mitin.backend.system.diet.model.diet.DietType;

import java.util.List;

public interface DietSystem {

    DietSystemImpl createMenu(String userLogin, DietType dietType);

    List<DietSystemImpl> getUserDiets(String userLogin);
}
