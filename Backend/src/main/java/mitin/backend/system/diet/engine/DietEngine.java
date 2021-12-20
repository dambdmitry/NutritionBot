package mitin.backend.system.diet.engine;

import mitin.backend.system.diet.model.diet.DietType;
import mitin.backend.system.diet.model.menu.Menu;

public interface DietEngine {

    Menu createMenu(Integer dailyCalorie, DietType dietType);

}
