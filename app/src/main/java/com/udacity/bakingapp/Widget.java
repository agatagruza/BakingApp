package com.udacity.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.udacity.bakingapp.data.IngredientList;

import java.util.ArrayList;

public class Widget extends AppWidgetProvider {

    private static ArrayList<IngredientList> myIngredientsArrayList = new ArrayList<IngredientList>();
    private static String myRecipe = "";

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {

        CharSequence recipe_widget = myRecipe;
        String full_Ingredients_list = "\n";
        for (IngredientList i : myIngredientsArrayList) {
            full_Ingredients_list += "\n" + "\u2022  " + i.getAmount() + " " + i.getMeasure()
                    + "\t\t" + i.getIngredientTag();
        }

        RemoteViews remoteViews =
                new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        if (!myRecipe.equals("") && !myIngredientsArrayList.isEmpty()) {

            remoteViews.setTextViewCompoundDrawables(R.id.widget_icon, R.drawable.ic_cake_gold_56dp, 0, 0, 0);
            remoteViews.setTextViewText(R.id.recipe_widget_tv, recipe_widget);
            remoteViews.setTextViewText(R.id.ingredients_widget_tv, full_Ingredients_list);
        }
        //Update all widget instances because user can have multiple instances of the same widget
        //on teh same screen. Always assume multiple instances do exist and update all of them.
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent INTENT) {
        super.onReceive(context, INTENT);
        String recipeTitle = context.getString(R.string.recipe_title);
        String ingredientsTitle = context.getString(R.string.ingredients_label);

        if (INTENT.hasExtra(recipeTitle) && INTENT.hasExtra(ingredientsTitle)) {
            myRecipe = INTENT.getStringExtra(recipeTitle);
            myIngredientsArrayList = INTENT.getParcelableArrayListExtra(ingredientsTitle);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context.getPackageName(),
                    Widget.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);

            onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //Update all widget instances because user can have multiple instances of the same widget
        //on teh same screen. Always assume multiple instances do exist and update all of them.
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}

