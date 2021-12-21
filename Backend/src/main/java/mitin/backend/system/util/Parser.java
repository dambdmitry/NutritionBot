package mitin.backend.system.util;

import mitin.backend.system.diet.model.menu.day.time.nutrition.Nutrition;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
    public static List<Nutrition> getNutritions(String path) {
        String s = readFile(path);
        List<Nutrition> result = new LinkedList<>();
        JSONObject jsonObject = new JSONObject(s);
        JSONArray nutritions = jsonObject.getJSONArray("nutritions");
        int length = nutritions.length();
        for (int i = 0; i < length; i++){
            JSONObject jsonNutrition = nutritions.getJSONObject(i);
            result.add(getNutritionFromJson(jsonNutrition));
        }
        return result;
    }

    private static Nutrition getNutritionFromJson(JSONObject jsonNutrition) throws JSONException {
        String name = jsonNutrition.getString("name");
        String type = jsonNutrition.getString("type");
        Integer calories = jsonNutrition.getInt("calories");
        String path = jsonNutrition.getString("path");
        List<String> ingredients = getListStringFromJson(jsonNutrition.getJSONArray("ingredients"));
        List<String> recipe = getListStringFromJson(jsonNutrition.getJSONArray("recipe"));
        return new Nutrition(name, type, calories, ingredients, recipe, path);
    }

    private static List<String> getListStringFromJson(JSONArray array) throws JSONException {
        int length = array.length();
        List<String> result = new ArrayList<>();
        for (int i = 0; i < length; i++){
            result.add(array.getString(i));
        }
        return result;
    }

    private static String readFile(String path){
        try {
            List<String> collect = Files.lines(Paths.get(path)).collect(Collectors.toList());
            String result = "";
            for (String line : collect){
                result += line;
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
