package mitin.backend.system.diet.model.menu;

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

    @Override
    public String toString() {
        return name;
    }
}
