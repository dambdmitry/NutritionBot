package mitin.frontend.telegram.engine.diets;

import mitin.backend.system.DietSystem;
import mitin.backend.system.diet.DietSystemImpl;
import mitin.backend.system.diet.model.diet.DietType;
import mitin.backend.system.diet.model.menu.Menu;
import mitin.backend.system.diet.model.menu.day.MealPlanForTheDay;
import mitin.backend.system.diet.model.menu.day.WeekDay;
import mitin.backend.system.diet.model.menu.day.time.Ingestion;
import mitin.backend.system.diet.model.menu.day.time.MealTime;

import java.util.List;
import java.util.stream.Collectors;

public class DietService {
    private DietSystem system;

    private static DietService dietService;

    private DietService() {
        system = DietSystemImpl.createDietSystem();
    }

    public static DietService createDietService() {
        if (dietService == null) {
            dietService = new DietService();
            return dietService;
        }
        return dietService;
    }

    public List<String> getUserDiets(Long chatId) {
        List<Menu> userDiets = system.getUserDiets(chatId);

        return userDiets
                .stream()
                .map(menu -> menu.getType().toString())
                .collect(Collectors.toList());
    }

    public void deleteDiets(Long chatId) {
        system.deleteDiets(chatId);
    }

    public String getDailyCalorie(Long chatId) {
        return system.getDailyCalorie(chatId).toString();
    }

    public boolean hasUserTypedMenu(Long chatId, DietType type) {
        return system.getUserDiets(chatId)
                .stream()
                .anyMatch(menu -> menu.getType() == type);
    }

    public void createMenu(Long chatId, DietType type) {
        system.createMenu(chatId, type);
    }

    public String getMeal(Long chatId, WeekDay weekDay, MealTime mealTime, DietType type) {
        MealPlanForTheDay plan = system.getMenuByDay(chatId, weekDay, type);
        Ingestion ingestion = plan.getIngestion(mealTime);
        return ingestion.toString();
    }

    public String getMealPhoto(Long chatId, WeekDay weekDay, MealTime mealTime, DietType type) {
        MealPlanForTheDay plan = system.getMenuByDay(chatId, weekDay, type);
        Ingestion ingestion = plan.getIngestion(mealTime);
        return ingestion.getNutrition().getPath();
    }
}
