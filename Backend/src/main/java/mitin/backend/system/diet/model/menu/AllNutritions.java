package mitin.backend.system.diet.model.menu;

import mitin.backend.system.Parser;
import mitin.backend.system.diet.model.nutrition.Nutrition;

import java.util.List;

public class AllNutritions {
    List<Nutrition> nutritions;

    public AllNutritions() {
        nutritions = Parser.getNutritions("Backend/src/main/resources/menu/nutritions.json");
    }

    public List<Nutrition> getNutritions() {
        return nutritions;
    }

}
