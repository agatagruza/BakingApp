package com.udacity.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {
    private String myRecipeLabel;
    private int myServingPortion;
    private ArrayList<IngredientList> ingredientsArrayList;
    private ArrayList<stepsArrayList> stepArrayList;
    private String imageURL;

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public Recipe(String recipeTAG, int servingPortion, ArrayList<IngredientList> ingredientsList,
                  ArrayList<stepsArrayList> recipeStepList, String image_URL) {
        myRecipeLabel = recipeTAG;
        myServingPortion = servingPortion;
        ingredientsArrayList = ingredientsList;
        stepArrayList = recipeStepList;
        imageURL = image_URL;
    }

    public String getRecipeTAG() {
        return myRecipeLabel;
    }

    public int getServingPortion() {
        return myServingPortion;
    }

    public ArrayList<IngredientList> getIngredientsList() {
        return ingredientsArrayList;
    }

    public ArrayList<stepsArrayList> getStepaList() {
        return stepArrayList;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setRecipeTAG(String mRecipeName) {
        this.myRecipeLabel = mRecipeName;
    }

    public void setServingPortion(int mServingSize) {
        this.myServingPortion = mServingSize;
    }

    public void setIngredientsList(ArrayList<IngredientList> mIngredientsList) {
        this.ingredientsArrayList = mIngredientsList;
    }

    public void setStepsList(ArrayList<stepsArrayList> mRecipeStepList) {
        this.stepArrayList = mRecipeStepList;
    }

    public void setImageURL(String mImageUrl) {
        this.imageURL = mImageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //Based on www.parcelabler.com
    private Recipe(Parcel in) {
        myRecipeLabel = in.readString();
        myServingPortion = in.readInt();
        imageURL = in.readString();
        if (in.readByte() == 0x01) {
            stepArrayList = new ArrayList<>();
            in.readList(stepArrayList, stepsArrayList.class.getClassLoader());
        } else {
            stepArrayList = null;
        }
        if (in.readByte() == 0x01) {
            ingredientsArrayList = new ArrayList<>();
            in.readList(ingredientsArrayList, IngredientList.class.getClassLoader());
        } else {
            ingredientsArrayList = null;
        }
    }

    //Based on www.parcelabler.com
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(myRecipeLabel);
        out.writeInt(myServingPortion);
        out.writeString(imageURL);
        if (stepArrayList == null) {
            out.writeByte((byte) (0x00));
        } else {
            out.writeByte((byte) (0x01));
            out.writeList(stepArrayList);
        }
        if (ingredientsArrayList == null) {
            out.writeByte((byte) (0x00));
        } else {
            out.writeByte((byte) (0x01));
            out.writeList(ingredientsArrayList);
        }
    }
}