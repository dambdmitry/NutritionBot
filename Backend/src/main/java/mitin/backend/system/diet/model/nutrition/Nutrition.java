package mitin.backend.system.diet.model.nutrition;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Nutrition {
    private String name;
    private String description;
    private List<Ingredient> ingredients;
    private Recipe recipe;

    private Integer calories;
    private Integer proteins;
    private Integer fats;
    private Integer carbohydrates;

    private File picture;//todo

    public Nutrition(String name){
        ingredients = new LinkedList<>();
        recipe = new Recipe();
        this.name = name;
    }

    public Nutrition(String name, String description, Integer calories, Integer proteins, Integer fats, Integer carbohydrates) {
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public void addStepForCook(String actionDescription){
        recipe.addStep(new Step(actionDescription));
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public void setProteins(Integer proteins) {
        this.proteins = proteins;
    }

    public void setFats(Integer fats) {
        this.fats = fats;
    }

    public void setCarbohydrates(Integer carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getRecipePresentation() {
        return recipe.toString();
    }

    public Recipe getRecipe(){
        return recipe;
    }

    public Integer getCalories() {
        return calories;
    }

    public Integer getProteins() {
        return proteins;
    }

    public Integer getFats() {
        return fats;
    }

    public Integer getCarbohydrates() {
        return carbohydrates;
    }

    public File getPicture() {
        return picture;
    }
}
