package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility functions to handle Sandwich JSON data.
 */
public class JsonUtils {
    /**
     * This method parses one JSON string-array item from
     * {@link com.udacity.sandwichclub.R.array#sandwich_details}
     *
     * into a sandwich object
     * {@link com.udacity.sandwichclub.model.Sandwich}
     *
     * @param json JSON from string.xml
     *
     * @return Sandwich object with the parsed information
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static Sandwich parseSandwichJson(String json)
            throws JSONException{

        /* Sandwich name information. Different names are elements of "name" object */
        final String SANDWICH_NAME = "name";

        /* Main name */
        final String SANDWICH_MAIN_NAME = "mainName";

        /* All "Also know as" names are string elements of "alsoKnownAs" array */
        final String SANDWICH_ALSO_KNOWN_AS = "alsoKnownAs";

        /* Place of origin */
        final String SANDWICH_PLACE_OF_ORIGIN = "placeOfOrigin";

        /* Description */
        final String SANDWICH_DESCRIPTION = "description";

        /* Image url in string */
        final String SANDWICH_IMAGE = "image";

        /* all ingredients are string elements of "ingredients" array */
        final String SANDWICH_INGREDIENTS = "ingredients";



        JSONObject sandwichJson = new JSONObject(json);

        JSONObject nameObject = sandwichJson.getJSONObject(SANDWICH_NAME);

        String mainName = nameObject.getString(SANDWICH_MAIN_NAME);

        JSONArray alsoKnownAsJsonArray = nameObject.getJSONArray(SANDWICH_ALSO_KNOWN_AS);

        /* String Array list to store "Also known as" names */
        List<String> alsoKnownAsStringList = new ArrayList<>();

        for (int i=0;i < alsoKnownAsJsonArray.length() ;i++) {
            alsoKnownAsStringList.add(alsoKnownAsJsonArray.getString(i));
        }

        String placeOfOriginString = sandwichJson.getString(SANDWICH_PLACE_OF_ORIGIN);
        String descriptionString = sandwichJson.getString(SANDWICH_DESCRIPTION);
        String imageUrlString = sandwichJson.getString(SANDWICH_IMAGE);


        JSONArray ingredientsJsonArray = sandwichJson.getJSONArray(SANDWICH_INGREDIENTS);

        /* String Array list to store ingredients */
        List<String> ingredientsStringList = new ArrayList<>();

        for (int i=0;i < ingredientsJsonArray.length() ;i++) {
            ingredientsStringList.add(ingredientsJsonArray.getString(i));
        }


        return new Sandwich(mainName, alsoKnownAsStringList, placeOfOriginString,
                descriptionString, imageUrlString, ingredientsStringList);
    }
}
