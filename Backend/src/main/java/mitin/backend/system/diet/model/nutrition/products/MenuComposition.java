package mitin.backend.system.diet.model.nutrition.products;

import java.util.HashSet;
import java.util.Set;

public class MenuComposition {
    private Set<Ingredient> ingredients;

    public MenuComposition() {
        ingredients = new HashSet<>();
    }

    public void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for (Ingredient ingredient : ingredients){
            result.append("- ").append(ingredient.getName()).append(": ").append(ingredient.getAmount()).append("\n");
        }
        return result.toString();
    }
}
