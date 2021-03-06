package mitin.backend.system.diet.engine.impl;

import mitin.backend.system.diet.engine.DietEngine;
import mitin.backend.system.diet.model.diet.DietType;
import mitin.backend.system.diet.model.menu.*;
import mitin.backend.system.diet.model.menu.day.MealPlanForTheDay;
import mitin.backend.system.diet.model.menu.day.WeekDay;
import mitin.backend.system.diet.model.menu.day.time.MealTime;
import mitin.backend.system.diet.model.menu.day.time.nutrition.Nutrition;

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
        List<Nutrition> nutritions = new NutritionStorage().getNutritions();
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

        Nutrition eveningMeal = getMaxPossibleNutrition(eveningMealCalorie, "????????????????", nutritions)
                .orElse(getMinPossibleNutrition(eveningMealCalorie, "????????????????", nutritions));
        float diff = eveningMealCalorie - eveningMeal.getCalories();

        Nutrition dinner = getMaxPossibleNutrition(dinnerCalorie + diff, "????????????????", nutritions)
                .orElse(getMinPossibleNutrition(dinnerCalorie, "????????????????", nutritions));
        diff = dinnerCalorie + diff - dinner.getCalories();

        Nutrition breakfast = getMaxPossibleNutrition(breakfastCalorie + diff, "??????????????", nutritions)
                .orElse(getMinPossibleNutrition(breakfastCalorie, "??????????????", nutritions));
        diff = breakfastCalorie + diff - breakfast.getCalories();

        Nutrition firstSnack = getMaxPossibleNutrition(snackCalorie + diff, "??????????????", nutritions)
                .orElse(getMinPossibleNutrition(snackCalorie, "??????????????", nutritions));
        diff = snackCalorie + diff - firstSnack.getCalories();

        Nutrition secondSnack = getMaxPossibleNutrition(snackCalorie + diff, "??????????????", nutritions)
                .orElse(getMinPossibleNutrition(snackCalorie, "??????????????", nutritions));

        diff = snackCalorie + diff - secondSnack.getCalories();

        if (diff > 100) {
            Nutrition second = getMaxPossibleNutrition(diff, "??????????????", nutritions).orElse(getMinPossibleNutrition(diff, "??????????????", nutritions));
            if (diff - second.allCalories() < 0) {
                second = getMaxPossibleNutrition(diff, "??????????????", nutritions).orElse(getMinPossibleNutrition(diff, "??????????????", nutritions));
            }
            dinner.setSecondNutrition(second);
            diff = diff - second.allCalories();
            if (diff > 100) {
                Nutrition secondBreakfast = getMaxPossibleNutrition(diff, "??????????????", nutritions).orElse(getMinPossibleNutrition(diff, "??????????????", nutritions));
                if (diff - secondBreakfast.allCalories() < 0) {
                    secondBreakfast = getMaxPossibleNutrition(diff, "??????????????", nutritions).orElse(getMinPossibleNutrition(diff, "??????????????", nutritions));
                }
                breakfast.setSecondNutrition(secondBreakfast);
                diff = diff - second.allCalories();
                if (diff > 100) {
                    Nutrition secondEveningMeal = getMaxPossibleNutrition(diff, "??????????????", nutritions).orElse(getMinPossibleNutrition(diff, "??????????????", nutritions));
                    eveningMeal.setSecondNutrition(secondEveningMeal);
                }
            }
        }
        plan.setIngestion(MealTime.BREAKFAST, breakfast);
        plan.setIngestion(MealTime.DINNER, dinner);
        plan.setIngestion(MealTime.SUPPER, eveningMeal);
        plan.setIngestion(MealTime.FIRST_SNACK, firstSnack);
        plan.setIngestion(MealTime.SECOND_SNACK, secondSnack);
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

}
