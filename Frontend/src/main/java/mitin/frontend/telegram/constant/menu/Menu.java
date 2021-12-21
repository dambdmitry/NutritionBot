package mitin.frontend.telegram.constant.menu;

import java.util.List;

public abstract class Menu {
    public static final String MY_PARAMETERS = "Мои параметры";
    public static final String MY_DIETS = "Мои рационы";
    public static final String CREATE_MENU = "Создать рацион";
    public static final String REMOVE_ME = "Сбросить параметры";
    public static final String MY_DAILY_CALORIE = "Моя суточная норма калорий";

    public static final List<String> MENU_COMPONENTS = List.of(MY_PARAMETERS, MY_DIETS, CREATE_MENU, REMOVE_ME, MY_DAILY_CALORIE);
}
