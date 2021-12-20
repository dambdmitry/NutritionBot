package mitin.backend.system.diet.model.nutrition;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class Nutrition {
    private String name;
    private String type;
    private List<String> ingredients;
    private List<String> recipe;
    private Integer calories;
    private String path;
    private Nutrition secondNutrition;

    private File picture;//todo

    public String getPath() {
        return path;
    }

    public Nutrition getSecondNutrition() {
        return secondNutrition;
    }

    public void setSecondNutrition(Nutrition secondNutrition) {
        this.secondNutrition = secondNutrition;
    }

    public Nutrition(String name, String type, Integer calories, List<String> ingredients, List<String> recipe, String path) {
        this.name = name;
        this.type = type;
        this.calories = calories;
        this.ingredients = ingredients;
        this.recipe = recipe;
        this.path = path;
        this.secondNutrition = null;
    }

    public void setType(String type) {
        this.type = type;
    }


    public void setPicture(File picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getRecipePresentation() {
        return recipe.toString();
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getRecipe() {
        return recipe;
    }

    public Integer getCalories() {
        return calories;
    }

    public File getPicture() {
        return picture;
    }

    public Integer allCalories(){
        if(secondNutrition != null){
            return secondNutrition.getCalories() + calories;
        }
        return calories;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nutrition that = (Nutrition) o;

        if(name.equals(that.name)){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return name.hashCode();
    }

    @Override
    public String toString(){
        String ing = ingredients.stream().collect(Collectors.joining("\n\t\t-"));
        String rec = recipe.stream().collect(Collectors.joining("\n\t\t-"));
        StringBuilder result = new StringBuilder();
        result.append(name).append("\n")
                .append("Калорийность: ").append(calories).append("\n")
                .append("Ингридиенты:\n\t\t-").append(ing).append("\n")
                .append("Рецепт:\n\t\t-").append(rec);
        if (secondNutrition != null) {
            result.append("\nВторое блюдо:\n").append(secondNutrition);
        }
        return result.toString();
    }
}
