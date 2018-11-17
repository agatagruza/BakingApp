package com.udacity.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.bakingapp.data.IngredientList;
import com.udacity.bakingapp.data.Recipe;
import com.udacity.bakingapp.data.stepsArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {

    Context mContext = InstrumentationRegistry.getTargetContext();

    stepsArrayList stepTEST = new stepsArrayList(4,
            "Brief Description TEST",
            "Full Description TEST",
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/" +
                    "April/58ffdcc8_5-mix-wet-and-cry-batter-together-brownies/5-mix-wet-and-cry-batter-together-brownies.mp4", "");

    IngredientList ingredientsTEST = new IngredientList(2, "TSP", "Sugar");

    ArrayList<stepsArrayList> stepsTEST_ArrayList = new ArrayList<stepsArrayList>();
    ArrayList<IngredientList> ingredientsTEST_ArrayList = new ArrayList<IngredientList>();

    @Rule
    public ActivityTestRule<DetailActivity> myActivityRule =
            new ActivityTestRule(DetailActivity.class, false, false);

    @Test
    public void testStep() {
        ingredientsTEST_ArrayList.add(ingredientsTEST);
        stepsTEST_ArrayList.add(stepTEST);
        Recipe testRecipe =
                new Recipe("Cake", 8, ingredientsTEST_ArrayList, stepsTEST_ArrayList, "");

        Intent INTENT_TEST = new Intent();
        INTENT_TEST.putExtra(mContext.getString(R.string.recipe_inst), testRecipe);
        myActivityRule.launchActivity(INTENT_TEST);
        onView(withContentDescription(stepTEST.getBriefDescription()))
                .perform().check(matches(isDisplayed()));
    }

    @Test
    public void checkIfStepOpen() {
        ingredientsTEST_ArrayList.add(ingredientsTEST);
        stepsTEST_ArrayList.add(stepTEST);
        Recipe testRecipe =
                new Recipe("Cake", 8, ingredientsTEST_ArrayList, stepsTEST_ArrayList, "");
        Intent testIntent = new Intent();
        testIntent.putExtra(mContext.getString(R.string.recipe_inst), testRecipe);
        myActivityRule.launchActivity(testIntent);
        onView(withContentDescription(stepTEST.getBriefDescription()))
                .perform(click());
        onView(withId(R.id.frame_layout_fragment)).check(matches(isDisplayed()));
    }

    @Test
    public void testIngredientsView() {
        ingredientsTEST_ArrayList.add(ingredientsTEST);
        stepsTEST_ArrayList.add(stepTEST);
        Recipe testRecipe =
                new Recipe("Cake", 8, ingredientsTEST_ArrayList, stepsTEST_ArrayList, "");
        Intent testIntent = new Intent();
        testIntent.putExtra(mContext.getString(R.string.recipe_inst), testRecipe);
        myActivityRule.launchActivity(testIntent);
        onView(withId(R.id.ingredients_text)).perform().check(matches(isDisplayed()));
    }
}
