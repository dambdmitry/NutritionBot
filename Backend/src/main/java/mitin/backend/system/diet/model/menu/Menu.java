package mitin.backend.system.diet.model.menu;

import mitin.backend.system.diet.model.diet.DietType;
import mitin.backend.system.diet.model.menu.day.MealPlanForTheDay;
import mitin.backend.system.diet.model.menu.day.WeekDay;
import mitin.backend.system.diet.model.menu.day.time.Ingestion;
import mitin.backend.system.diet.model.menu.day.time.nutrition.Nutrition;

import java.util.*;
import java.util.stream.Collectors;

public class Menu {

    private Map<WeekDay, MealPlanForTheDay> weekPlan;
    private DietType type;

    public DietType getType() {
        return type;
    }

    public Menu(DietType type) {
        this.type = type;
        weekPlan = new HashMap<>();
    }

    public void setDayPlan(WeekDay day, MealPlanForTheDay dayPlan){
        if (weekPlan.containsKey(day)){
            weekPlan.remove(day);
        }
        weekPlan.put(day, dayPlan);
    }

    public List<Nutrition> getMeals(){
        Collection<MealPlanForTheDay> values = weekPlan.values();
        List<Nutrition> meals = new LinkedList<>();
        for (MealPlanForTheDay plan : values){
            meals.addAll(plan.getMealPlan()
                    .stream().map(Ingestion::getNutrition).collect(Collectors.toList()));
        }
        return meals;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Тип Меню: ").append(type.toString()).append("\n")
                .append("ПОНЕДЕЛЬНИК\n").append(weekPlan.get(WeekDay.MONDAY)).append("\n")
                .append("ВТОРНИК\n").append(weekPlan.get(WeekDay.TUESDAY)).append("\n")
                .append("СРЕДА\n").append(weekPlan.get(WeekDay.WEDNESDAY)).append("\n")
                .append("ЧЕТВЕРГ\n").append(weekPlan.get(WeekDay.THURSDAY)).append("\n")
                .append("ПЯТНИЦА\n").append(weekPlan.get(WeekDay.FRIDAY)).append("\n")
                .append("СУББОТА\n").append(weekPlan.get(WeekDay.SATURDAY)).append("\n")
                .append("ВОСКРЕСЕНЬЕ\n").append(weekPlan.get(WeekDay.SUNDAY));

        return result.toString();
    }

    public MealPlanForTheDay getDayPlan(WeekDay day) {
        return weekPlan.get(day);
    }
}
