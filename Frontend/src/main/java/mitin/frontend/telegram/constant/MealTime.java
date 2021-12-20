package mitin.frontend.telegram.constant;

import java.util.List;

public class MealTime {
    public static final String BREAKFAST = "Завтрак";
    public static final String FIRST_SNACK = "Первый перекус";
    public static final String DINNER = "Обед";
    public static final String SECOND_SNACK = "Второй перекус";
    public static final String EVENING_MEAL = "Ужин";

    public static final List<String> MEALS = List.of(BREAKFAST, FIRST_SNACK, DINNER, SECOND_SNACK, EVENING_MEAL, NavigationMenu.GO_BACK, NavigationMenu.GO_MAIN_MENU);
}