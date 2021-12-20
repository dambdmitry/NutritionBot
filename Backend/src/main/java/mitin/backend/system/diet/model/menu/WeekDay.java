package mitin.backend.system.diet.model.menu;

import java.util.List;

public enum WeekDay {
    MONDAY("Понедельник"),
    TUESDAY("Вторник"),
    WEDNESDAY("Среда"),
    THURSDAY("Четверг"),
    FRIDAY("Пятница"),
    SATURDAY("Суббота"),
    SUNDAY("Воскресенье");

    String name;
    private WeekDay(String day){
        name = day;
    }

    public static List<WeekDay> getWeek(){
        return List.of(MONDAY, WEDNESDAY, TUESDAY , FRIDAY, THURSDAY, SATURDAY, SUNDAY);
    }

    @Override
    public String toString() {
        return name;
    }
}
