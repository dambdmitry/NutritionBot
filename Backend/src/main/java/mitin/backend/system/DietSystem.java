package mitin.backend.system;

import mitin.backend.system.diet.model.diet.DietType;
import mitin.backend.system.diet.model.menu.day.MealPlanForTheDay;
import mitin.backend.system.diet.model.menu.Menu;
import mitin.backend.system.diet.model.menu.day.WeekDay;

import java.util.List;

public interface DietSystem {

    /**
     * @param chatId идентификатор пользователя
     * @param dietType тип меню (похудение, набор массы, поддержание формы)
     * @return недельное меню.
     */
    Menu createMenu(Long chatId, DietType dietType);

    /**
     * @param chatId идентификатор пользователя
     * @return список меню, которые созданы для пользователя.
     */
    List<Menu> getUserDiets(Long chatId);

    /**
     * @param chatId идентификатор пользователя
     * @return возращает суточную норм калорий.
     */
    Integer getDailyCalorie(Long chatId);

    /**
     * @param chatId идентификатор пользователя
     * Удаляет все меню пользователя.
     */
    void deleteDiets(Long chatId);

    /**
     * @param chatId идентификатор пользователя
     * @param day день недели
     * @param type тип меню (похудение, набор массы, поддержание формы)
     * @return план меню на день.
     */
    MealPlanForTheDay getMenuByDay(Long chatId, WeekDay day, DietType type);
}
