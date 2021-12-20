package mitin.backend.system.diet.engine.impl;

import mitin.backend.system.diet.engine.DietEngine;
import mitin.backend.system.diet.model.diet.DietType;
import mitin.backend.system.diet.model.menu.*;
import mitin.backend.system.diet.model.nutrition.Nutrition;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DietEngineImpl implements DietEngine {

    private final float BREAKFAST = 0.3f;
    private final float SNACK = 0.1f;
    private final float DINNER = 0.3f;
    private final float EVENING_MEAL = 0.2f;

    public DietEngineImpl() {
    }

    @Override
    public Menu createMenu(Integer dailyCalorie, DietType dietType) {
        List<Nutrition> nutritions = new AllNutritions().getNutritions();
        List<WeekDay> week = WeekDay.getWeek();
        Menu menu = new Menu(dietType);
        Integer range = 0;
        if (dietType == DietType.WEIGHT_GAIN){
            range = 100;
        } else if (dietType == DietType.WEIGHT_LOSS) {
            range = -100;
        }

        for (WeekDay day : week){
            MealPlanForTheDay plan = createMealPlan(dailyCalorie + range, nutritions);
            menu.setDayPlan(day, plan);

            List<Nutrition> meals = menu.getMeals();
            nutritions.removeAll(meals.stream().collect(Collectors.groupingBy(Function.identity())).entrySet()
                    .stream().filter(e -> e.getValue().size() > 1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList()));
        }
        return menu;
    }

    private MealPlanForTheDay createMealPlan(Integer dailyCalorie, List<Nutrition> nutritions) {
        MealPlanForTheDay plan = new MealPlanForTheDay();
        float breakfastCalorie = dailyCalorie * BREAKFAST;
        float snackCalorie = dailyCalorie * SNACK;
        float dinnerCalorie = dailyCalorie * DINNER;
        float eveningMealCalorie = dailyCalorie * EVENING_MEAL;

        Nutrition eveningMeal = getMaxPossibleNutrition(eveningMealCalorie, "Основное", nutritions)
                .orElse(getMinPossibleNutrition(eveningMealCalorie, "Основное", nutritions));
        float diff = eveningMealCalorie - eveningMeal.getCalories();

        Nutrition dinner = getMaxPossibleNutrition(dinnerCalorie + diff, "Основное", nutritions)
                .orElse(getMinPossibleNutrition(dinnerCalorie, "Основное", nutritions));
        diff = dinnerCalorie + diff - dinner.getCalories();

        Nutrition breakfast = getMaxPossibleNutrition(breakfastCalorie + diff, "Завтрак", nutritions)
                .orElse(getMinPossibleNutrition(breakfastCalorie, "Завтрак", nutritions));
        diff = breakfastCalorie + diff - breakfast.getCalories();

        Nutrition firstSnack = getMaxPossibleNutrition(snackCalorie + diff, "Перекус", nutritions)
                .orElse(getMinPossibleNutrition(snackCalorie, "Перекус", nutritions));
        diff = snackCalorie + diff - firstSnack.getCalories();

        Nutrition secondSnack = getMaxPossibleNutrition(snackCalorie + diff, "Перекус", nutritions)
                .orElse(getMinPossibleNutrition(snackCalorie, "Перекус", nutritions));

        diff = snackCalorie + diff - secondSnack.getCalories();

        if (diff > 100) {
            Nutrition second = getMaxPossibleNutrition(diff, "Завтрак", nutritions).orElse(getMinPossibleNutrition(diff, "Завтрак", nutritions));
            if (diff - second.allCalories() < 0) {
                second = getMaxPossibleNutrition(diff, "Перекус", nutritions).orElse(getMinPossibleNutrition(diff, "Перекус", nutritions));
            }
            dinner.setSecondNutrition(second);
            diff = diff - second.allCalories();
            if (diff > 100) {
                Nutrition secondBreakfast = getMaxPossibleNutrition(diff, "Завтрак", nutritions).orElse(getMinPossibleNutrition(diff, "Завтрак", nutritions));
                if (diff - secondBreakfast.allCalories() < 0) {
                    secondBreakfast = getMaxPossibleNutrition(diff, "Перекус", nutritions).orElse(getMinPossibleNutrition(diff, "Перекус", nutritions));
                }
                breakfast.setSecondNutrition(secondBreakfast);
                diff = diff - second.allCalories();
                if (diff > 100) {
                    Nutrition secondEveningMeal = getMaxPossibleNutrition(diff, "Перекус", nutritions).orElse(getMinPossibleNutrition(diff, "Перекус", nutritions));
                    eveningMeal.setSecondNutrition(secondEveningMeal);
                }
            }
        }
        plan.setIngestion(MealTime.BREAKFAST, breakfast);
        plan.setIngestion(MealTime.DINNER, dinner);
        plan.setIngestion(MealTime.SUPPER, eveningMeal);
        plan.setIngestion(MealTime.FIRST_SNACK, firstSnack);
        plan.setIngestion(MealTime.SECOND_SNACK, secondSnack);


//        System.out.println(eveningMeal.getCalories() + dinner.getCalories() + breakfast.getCalories()
//                + firstSnack.getCalories() + secondSnack.getCalories()+ "\n\n");

        return plan;
    }

    private Nutrition getMinPossibleNutrition(float coefficient, String type, List<Nutrition> nutritions) {
        return nutritions.stream()
                .filter(nutrition -> nutrition.getType().equals(type))
                .filter(nutrition -> nutrition.getCalories() >= coefficient)
                .min(Comparator.comparing(Nutrition::getCalories)).orElse(null);
    }

    private Optional<Nutrition> getMaxPossibleNutrition(float coefficient, String type, List<Nutrition> nutritions){
        return nutritions.stream()
                .filter(nutrition -> nutrition.getType().equals(type))
                .filter(nutrition -> nutrition.getCalories() <= coefficient)
                .max(Comparator.comparing(Nutrition::getCalories));
    }

    public static void main(String[] args) {
        //Menu menu = new DietEngineImpl().createMenu(4000, DietType.NORMAL);
        //System.out.println(menu);

        for (int i = 1500; i <= 4000; i++){
            Menu menu = new DietEngineImpl().createMenu(i, DietType.NORMAL);
            List<Integer> collect = menu.getMeals().stream().map(Nutrition::allCalories).collect(Collectors.toList());
            Integer result = 0;
            for (Integer cal : collect){
                result += cal;
            }
            System.out.println("Для i = " + i + " норма на неделю = " + result);
        }
    }
}
