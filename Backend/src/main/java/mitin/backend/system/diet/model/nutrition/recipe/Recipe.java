package mitin.backend.system.diet.model.nutrition.recipe;

import java.util.ArrayList;

public class Recipe {
    private ArrayList<Step> cookStepByStep;

    public Recipe() {
        cookStepByStep = new ArrayList<>();
    }

    public void addStep(Step step){
        cookStepByStep.add(step);
    }

    @Override
    public String toString() {
        int index = 0;
        final int length = cookStepByStep.size();
        StringBuilder result = new StringBuilder();
        while (index < length){
            result.append(index + 1) // number for step
                    .append(". ")    // separator
                    .append(cookStepByStep.get(index).toString()) // action description
                    .append("\n"); // new line
            index++;
        }
        return result.toString();
    }
}
