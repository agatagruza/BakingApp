package com.udacity.bakingapp.adapter;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.bakingapp.DetailActivity;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.Widget;
import com.udacity.bakingapp.data.IngredientList;
import com.udacity.bakingapp.data.Recipe;
import com.udacity.bakingapp.data.stepsArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FullRecipeAdapter extends RecyclerView.Adapter<FullRecipeAdapter.RecipeAdapterViewHolder> {

    public Context myContext;
    private ArrayList<Recipe> myList;

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout myLinearLayout;
        public ImageView myImageView;
        public TextView myTitle_tv;

        public RecipeAdapterViewHolder(LinearLayout view) {
            super(view);

            myLinearLayout = view;
            myTitle_tv = new TextView(myContext);
            myImageView = new ImageView(myContext);

            myLinearLayout.addView(myImageView);
            myLinearLayout.addView(myTitle_tv);
            myLinearLayout.setElevation(20);
        }
    }

    public FullRecipeAdapter(Context context, String jsonString) {
        myContext = context;
        myList = new ArrayList<>();
        extractRecipes(jsonString);
    }

    @NonNull
    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayoutRecipe = new LinearLayout(myContext);
        LinearLayout.LayoutParams myLayoutParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        myLayoutParams.setMargins(20, 20, 20, 20);
        linearLayoutRecipe.setPadding(0, 20, 0, 20);
        linearLayoutRecipe.setBackgroundResource(R.color.colorPrimary);
        linearLayoutRecipe.setLayoutParams(myLayoutParams);

        RecipeAdapterViewHolder newRecipe = new RecipeAdapterViewHolder(linearLayoutRecipe);
        return newRecipe;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapterViewHolder holder, final int position) {
        final Recipe recipeLocation = myList.get(position);

        LinearLayout.LayoutParams linearLayoutText =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        holder.myTitle_tv.setLayoutParams(linearLayoutText);
        holder.myTitle_tv.setText(recipeLocation.getRecipeTAG());
        holder.myTitle_tv.setTypeface(null, Typeface.BOLD);
        holder.myTitle_tv.setTextAppearance(myContext, R.style.RecipeCardText);
        holder.myTitle_tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        holder.myTitle_tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_navigate_next_black_56dp, 0);
        linearLayoutText.weight = 2f;
        linearLayoutText.gravity = Gravity.CENTER;

        LinearLayout.LayoutParams linearLayoutImage =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.myImageView.setLayoutParams(linearLayoutImage);
        Picasso.get()
                .load(Uri.parse(recipeLocation.getImageURL()))
                .placeholder(R.drawable.ic_cake_gold_56dp)
                .error(R.drawable.ic_cake_gold_56dp)
                .into(holder.myImageView);
        linearLayoutImage.weight = 1f;
        linearLayoutImage.gravity = Gravity.CENTER;

        holder.myLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myINTENTdetails = new Intent(myContext, DetailActivity.class);
                myINTENTdetails.putExtra
                        (myContext.getString(R.string.recipe_inst), recipeLocation);
                Intent myINTENT = new Intent(myContext, Widget.class);
                myINTENT.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                myINTENT.putExtra
                        (myContext.getString
                                (R.string.ingredients_label), recipeLocation.getIngredientsList());
                myINTENT.putExtra
                        (myContext.getString(R.string.recipe_title), recipeLocation.getRecipeTAG());

                myContext.sendBroadcast(myINTENT);
                myContext.startActivity(myINTENTdetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public void extractRecipes(String jsonResponse) {
        try {
            JSONArray recipeJsonArray = new JSONArray(jsonResponse);
            for (int i = 0; i < recipeJsonArray.length(); i++) {
                String recipeName = recipeJsonArray.getJSONObject(i)
                        .getString(myContext.getString(R.string.name_KEY));
                int servingSize = recipeJsonArray.getJSONObject(i)
                        .getInt(myContext.getString(R.string.servings_KEY));

                JSONArray ingredientsJsonArray
                        = recipeJsonArray.getJSONObject(i)
                        .getJSONArray(myContext.getString(R.string.ingredients_KEY));
                ArrayList<IngredientList> myIngredientList = new ArrayList<>();
                for (int j = 0; j < ingredientsJsonArray.length(); j++) {
                    JSONObject position = ingredientsJsonArray.getJSONObject(j);
                    double quantity =
                            position.getDouble(myContext.getString(R.string.quantity_KEY));
                    String measureType =
                            position.getString(myContext.getString(R.string.measure_KEY));
                    String ingredientLabel =
                            position.getString(myContext.getString(R.string.ingredient_KEY));
                    IngredientList ingredient
                            = new IngredientList(quantity, measureType, ingredientLabel);
                    myIngredientList.add(ingredient);
                }

                JSONArray stepsJsonArray
                        = recipeJsonArray.getJSONObject(i)
                        .getJSONArray(myContext.getString(R.string.steps_KEY));
                ArrayList<stepsArrayList> stepsArrayList = new ArrayList<>();
                for (int k = 0; k < stepsJsonArray.length(); k++) {
                    JSONObject position = stepsJsonArray.getJSONObject(k);
                    int id =
                            position.getInt(myContext.getString(R.string.id_KEY));
                    String briefDescription =
                            position.getString
                                    (myContext.getString(R.string.brief_description_KEY));
                    String description =
                            position.getString(myContext.getString(R.string.description_KEY));
                    String videoURL =
                            position.getString(myContext.getString(R.string.video_KEY));
                    String thumbnailURL =
                            position.getString(myContext.getString(R.string.thumbnail_KEY));
                    com.udacity.bakingapp.data.stepsArrayList step
                            = new stepsArrayList(id, briefDescription,
                            description, videoURL, thumbnailURL);
                    stepsArrayList.add(step);
                }
                String imageURL = recipeJsonArray.getJSONObject(i)
                        .getString(myContext.getString(R.string.image_KEY));
                myList.add(new Recipe
                        (recipeName, servingSize,
                                myIngredientList, stepsArrayList, imageURL));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
