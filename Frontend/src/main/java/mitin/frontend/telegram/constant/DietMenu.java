package mitin.frontend.telegram.constant;

import java.util.List;

public class DietMenu {
    public static final String TODAY = "Меню на сегодня";
    public static final String MONDAY = "Понедельник";
    public static final String TUESDAY = "Вторник";
    public static final String WEDNESDAY = "Среда";
    public static final String THURSDAY = "Четверг";
    public static final String FRIDAY = "Пятница";
    public static final String SATURDAY = "Суббота";
    public static final String SUNDAY = "Воскресенье";

    public static final List<String> MENU_COMPONENTS = List.of(TODAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY, NavigationMenu.GO_BACK, NavigationMenu.GO_MAIN_MENU);
}