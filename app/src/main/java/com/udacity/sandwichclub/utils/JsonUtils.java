package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Sandwich sandwitch=new Sandwich();

        JSONObject JSONSandwich=new JSONObject(json);

        if(JSONSandwich.length()==0) {
            return null;
        }

        JSONObject sandwichName=JSONSandwich.getJSONObject("name");

        String mainName=sandwichName.getString("mainName");
        sandwitch.setMainName(mainName);

        JSONArray JSONalsoKnownAs=sandwichName.getJSONArray("alsoKnownAs");
        List<String> alsoKnownAs=new LinkedList<String>();
        for(int i=0;i<JSONalsoKnownAs.length();++i) {
            alsoKnownAs.add(JSONalsoKnownAs.getString(i));
        }
        sandwitch.setAlsoKnownAs(alsoKnownAs);

        String placeOfOrigin=JSONSandwich.getString("placeOfOrigin");
        sandwitch.setPlaceOfOrigin(placeOfOrigin);

        String description=JSONSandwich.getString("description");
        sandwitch.setDescription(description);

        String image=JSONSandwich.getString("image");
        sandwitch.setImage(image);

        JSONArray JSONingredients=JSONSandwich.getJSONArray("ingredients");
        List<String> ingredients=new LinkedList<String>();
        for(int i=0;i<JSONingredients.length();++i) {
            ingredients.add(JSONingredients.getString(i));
        }
        sandwitch.setIngredients(ingredients);

        return sandwitch;
    }
}
