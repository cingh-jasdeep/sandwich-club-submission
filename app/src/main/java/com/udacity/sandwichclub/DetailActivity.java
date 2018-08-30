package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    /*
     * Label variables are kept to display only when data is not null
     */

    public TextView mOriginLabelTextView;
    public TextView mDescriptionLabelTextView;
    public TextView mIngredientsLabelTextView;
    public TextView mAlsoKnownLabelTextView;

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
        mOriginLabelTextView= findViewById(R.id.origin_label_tv);
        mDescriptionLabelTextView = findViewById(R.id.description_label_tv);
        mIngredientsLabelTextView = findViewById(R.id.ingredients_label_tv);
        mAlsoKnownLabelTextView = findViewById(R.id.also_known_label_tv);

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
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich s) {

        String placeOfOrigin = s.getPlaceOfOrigin();
        if (placeOfOrigin!=null && !placeOfOrigin.equals("")) {
            mOriginTextView.setText(placeOfOrigin);

            mOriginLabelTextView.setVisibility(View.VISIBLE);
            mOriginTextView.setVisibility(View.VISIBLE);
        }

        String description = s.getDescription();
        if (description!=null && !description.equals("")) {
            mDescriptionTextView.setText(description);

            mDescriptionLabelTextView.setVisibility(View.VISIBLE);
            mDescriptionTextView.setVisibility(View.VISIBLE);

        }

        List<String> ingredientsList = s.getIngredients();

        if(ingredientsList!=null) {
            for (String ingredient : ingredientsList) {
                if(!ingredient.equals("")) { mIngredientsTextView.append((ingredient) + "\n"); }
            }

            /* Make sure there are items to show*/
            if(!(mIngredientsTextView.getText().toString()).equals("")) {
                mIngredientsLabelTextView.setVisibility(View.VISIBLE);
                mIngredientsTextView.setVisibility(View.VISIBLE);
            }
        }

        List<String> alsoKnownAsList = s.getAlsoKnownAs();

        if(alsoKnownAsList!=null) {
            for (String alsoKnownAs : alsoKnownAsList) {
                if(!alsoKnownAs.equals("")) { mAlsoKnownTextView.append((alsoKnownAs) + "\n"); }
            }

            /* Make sure there are items to show*/
            if(!(mAlsoKnownTextView.getText().toString()).equals("")) {
                mAlsoKnownLabelTextView.setVisibility(View.VISIBLE);
                mAlsoKnownTextView.setVisibility(View.VISIBLE);
            }

        }
    }
}
