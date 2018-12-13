package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    /*
     * Adding local variables for Detail Activity UI
     */
    public TextView mOriginTextView;
    public TextView mDescriptionTextView;
    public TextView mIngredientsTextView;
    public TextView mAlsoKnownTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        /*
         * Using findViewById, to get reference to UI widgets declared above
         */
        mOriginTextView = findViewById(R.id.origin_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mAlsoKnownTextView = findViewById(R.id.also_known_tv);


        Intent intent = getIntent();
        int position = 0;

        if (intent == null) {
            closeOnError();
        } else {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        }

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;

        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
            closeOnError();
        }

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich s) {

        String placeOfOrigin = s.getPlaceOfOrigin();
        if (placeOfOrigin != null && !placeOfOrigin.equals("")) {
            mOriginTextView.append((placeOfOrigin) + "\n");
        } else {
            mOriginTextView.append(getString(R.string.data_error) + "\n");
        }

        String description = s.getDescription();
        if (description != null && !description.equals("")) {
            mDescriptionTextView.append((description) + "\n");
        } else {
            mDescriptionTextView.append(getString(R.string.data_error) + "\n");
        }

        String ingredientString = TextUtils.join("\n", s.getIngredients());
        if (ingredientString != null && !ingredientString.equals("")) {
            mIngredientsTextView.append(ingredientString + "\n");
        } else {
            mIngredientsTextView.append(getString(R.string.data_error) + "\n");
        }

        String alsoKnownAsString = TextUtils.join("\n", s.getAlsoKnownAs());
        if (alsoKnownAsString != null && !alsoKnownAsString.equals("")) {
            mAlsoKnownTextView.append(alsoKnownAsString + "\n");
        } else {
            mAlsoKnownTextView.append(getString(R.string.data_error) + "\n");
        }

    }
}
