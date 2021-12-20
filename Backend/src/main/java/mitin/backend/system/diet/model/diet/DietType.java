package mitin.backend.system.diet.model.diet;

public enum DietType {
    WEIGHT_LOSS("Похудение"),
    WEIGHT_GAIN("Набор массы"),
    NORMAL("Поддержание формы");

    String name;

    DietType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
