package mitin.backend.system.diet.model.menu;

public enum MealTime {
    BREAKFAST("Завтрак"),
    FIRST_SNACK("Первый перекус"),
    DINNER("Обед"),
    SECOND_SNACK("Второй перекус"),
    SUPPER("Ужин");

    String name;

    MealTime(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
