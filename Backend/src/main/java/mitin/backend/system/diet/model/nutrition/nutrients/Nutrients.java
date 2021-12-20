package mitin.backend.system.diet.model.nutrition.nutrients;

public class Nutrients {
    private Integer calories;
    private Integer proteins;
    private Integer fats;
    private Integer carbohydrates;

    public Nutrients(Integer calories, Integer proteins, Integer fats, Integer carbohydrates) {
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public Nutrients() {
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getProteins() {
        return proteins;
    }

    public void setProteins(Integer proteins) {
        this.proteins = proteins;
    }

    public Integer getFats() {
        return fats;
    }

    public void setFats(Integer fats) {
        this.fats = fats;
    }

    public Integer getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(Integer carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("Калории: ").append(calories).append("\n")
                .append("Белки: ").append(proteins).append("\n")
                .append("Жиры: ").append(fats).append("\n")
                .append("Углеводы: ").append(carbohydrates).append("\n");
        return result.toString();
    }
}
