package mitin.backend.system.diet.model.menu.day;

import mitin.backend.system.diet.model.menu.day.time.Ingestion;
import mitin.backend.system.diet.model.menu.day.time.MealTime;
import mitin.backend.system.diet.model.nutrition.Nutrition;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MealPlanForTheDay {
    private List<Ingestion> mealPlan;

    public MealPlanForTheDay(){
        initPlan();
    }

    public void setIngestion(MealTime mealTime, Nutrition nutrition){
        if (mealTime == null) return;

        mealPlan.stream()
                .filter(ing -> ing.getMealTime().equals(mealTime))
                .findAny()
                .ifPresent(ingestion1 -> ingestion1.setNutrition(nutrition));
    }

    public boolean isMenuFull(){
        return mealPlan.stream().noneMatch(ingestion -> ingestion.getNutrition() == null);
    }

    private void initPlan(){
        mealPlan = new LinkedList<>();
        mealPlan.add(new Ingestion(MealTime.BREAKFAST));
        mealPlan.add(new Ingestion(MealTime.SECOND_SNACK));
        mealPlan.add(new Ingestion(MealTime.DINNER));
        mealPlan.add(new Ingestion(MealTime.FIRST_SNACK));
        mealPlan.add(new Ingestion(MealTime.SUPPER));
    }

    public Ingestion getIngestion(MealTime time){
        Optional<Ingestion> ingestion = mealPlan
                .stream()
                .filter(ing -> ing.getMealTime().equals(time))
                .findAny();
        if (ingestion.isPresent()){
            return ingestion.get();
        }
        System.out.println("Нет такого приема пищи");
        return null;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for(Ingestion ingestion : mealPlan){
            result.append(ingestion.getMealTime().toString()).append(": \n")
                    .append(ingestion.getNutrition());
        }
        return result.toString();
    }

    public List<Ingestion> getMealPlan() {
        return mealPlan;
    }
}
