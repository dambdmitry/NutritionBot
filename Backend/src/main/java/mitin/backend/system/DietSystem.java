package mitin.backend.system;

import mitin.backend.system.diet.model.diet.DietType;
import mitin.backend.system.diet.model.menu.Ingestion;
import mitin.backend.system.diet.model.menu.MealPlanForTheDay;
import mitin.backend.system.diet.model.menu.Menu;
import mitin.backend.system.diet.model.menu.WeekDay;

import java.util.List;

public interface DietSystem {

    Menu createMenu(Long chatId, DietType dietType);

    List<Menu> getUserDiets(Long chatId);

    /**
     * @param chatId id user
     * @return возращает норму Белков Жиров и Углеводов
     */
    Integer getDailyCalorie(Long chatId);

    void deleteDiets(Long chatId);

    MealPlanForTheDay getMenuByDay(Long chatId, WeekDay day, DietType type);
}
