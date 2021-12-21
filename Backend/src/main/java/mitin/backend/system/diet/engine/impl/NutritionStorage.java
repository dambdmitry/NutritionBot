package mitin.backend.system.diet.engine.impl;

import mitin.backend.system.util.Parser;
import mitin.backend.system.diet.model.menu.day.time.nutrition.Nutrition;

import java.util.List;

public class NutritionStorage {

    List<Nutrition> nutritions;
    private final String PATH = "Backend/src/main/resources/menu/nutritions.json";

    public NutritionStorage() {
        nutritions = Parser.getNutritions(PATH);
    }

    public List<Nutrition> getNutritions() {
        return nutritions;
    }

}
