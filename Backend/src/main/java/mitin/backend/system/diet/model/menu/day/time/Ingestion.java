package mitin.backend.system.diet.model.menu.day.time;

import mitin.backend.system.diet.model.nutrition.Nutrition;

public class Ingestion {
    private MealTime mealTime;
    private Nutrition nutrition;

    public Ingestion(MealTime mealTime) {
        this.mealTime = mealTime;
    }

    public MealTime getMealTime() {
        return mealTime;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setMealTime(MealTime mealTime) {
        this.mealTime = mealTime;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append(mealTime).append(":\n").append(nutrition);
        return result.toString();
    }


}
