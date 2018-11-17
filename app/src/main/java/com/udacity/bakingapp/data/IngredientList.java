package com.udacity.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class IngredientList implements Parcelable {

    private double myAmount;
    private String myMeasureType;
    private String myIngredientLabel;

    public IngredientList(double amount, String measureType, String ingredientTag) {
        myAmount = amount;
        myMeasureType = measureType;
        myIngredientLabel = ingredientTag;
    }

    public static final Parcelable.Creator<IngredientList> CREATOR
            = new Parcelable.Creator<IngredientList>() {

        @Override
        public IngredientList createFromParcel(Parcel source) {
            return new IngredientList(source);
        }

        @Override
        public IngredientList[] newArray(int size) {
            return new IngredientList[size];
        }
    };

    private IngredientList(Parcel in) {
        myAmount = in.readDouble();
        myMeasureType = in.readString();
        myIngredientLabel = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeDouble(myAmount);
        out.writeString(myMeasureType);
        out.writeString(myIngredientLabel);
    }

    public double getAmount() {
        return myAmount;
    }

    public String getMeasure() {
        return myMeasureType;
    }

    public String getIngredientTag() {
        return myIngredientLabel;
    }

    public void setAmount(double mQuantity) {
        this.myAmount = mQuantity;
    }

    public void setMeasure(String mMeasure) {
        this.myMeasureType = mMeasure;
    }

    public void setIngredientTag(String mIngredientName) {
        this.myIngredientLabel = mIngredientName;
    }
}
