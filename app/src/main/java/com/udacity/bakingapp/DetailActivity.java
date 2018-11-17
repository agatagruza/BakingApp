package com.udacity.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingapp.data.IngredientList;
import com.udacity.bakingapp.data.Recipe;
import com.udacity.bakingapp.data.stepsArrayList;
import com.udacity.bakingapp.views.DescriptionListFragment;
import com.udacity.bakingapp.views.InstructionFragment;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private String myRecipeLabel;
    private ArrayList<IngredientList> myIngredientArrayList;
    private ArrayList<stepsArrayList> myStepsArrayList;

    private int myLocation;

    private InstructionFragment instructionFragment;
    private DescriptionListFragment descriptionFragment;

    private boolean tablet_bool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        descriptionFragment = new DescriptionListFragment();
        instructionFragment = new InstructionFragment();

        //Getting all information from MainActivity
        Intent INTENT = getIntent();
        if (INTENT.hasExtra(getString(R.string.recipe_inst))) {
            Recipe recipe = INTENT.getParcelableExtra(getString(R.string.recipe_inst));
            myStepsArrayList = recipe.getStepaList();
            myIngredientArrayList = recipe.getIngredientsList();
            getSupportActionBar().setTitle(myRecipeLabel);
            myRecipeLabel = recipe.getRecipeTAG();
        }

        tablet_bool = getResources().getBoolean(R.bool.tabletSize);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_detail_layout, descriptionFragment,
                            getString(R.string.fragment_description))
                    .commit();
        }

        //Enabling back arrow "<--"
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_detail);
    }

    public String getRecipeTAG() {
        return myRecipeLabel;
    }

    public ArrayList<IngredientList> getIngredientArrayList() {
        return myIngredientArrayList;
    }

    public ArrayList<stepsArrayList> getStepsArrayList() {
        return myStepsArrayList;
    }

    public int getLocation() {
        return myLocation;
    }

    public InstructionFragment getInstructionFragment() {
        return instructionFragment;
    }

    public DescriptionListFragment getDescriptionFragment() {
        return descriptionFragment;
    }

    public void setRecipeTAG(String mRecipeName) {
        this.myRecipeLabel = mRecipeName;
    }

    public void setIngredientArrayList(ArrayList<IngredientList> mIngredientList) {
        this.myIngredientArrayList = mIngredientList;
    }

    public void setStepsArrayList(ArrayList<stepsArrayList> mRecipeStepList) {
        this.myStepsArrayList = mRecipeStepList;
    }

    public void setLocation(int mCurrentPosition) {
        this.myLocation = mCurrentPosition;
    }

    public void setInstructionFragment(InstructionFragment mInstructionFragment) {
        this.instructionFragment = mInstructionFragment;
    }

    public void setDescriptionFragment(DescriptionListFragment mDescriptionListFragment) {
        this.descriptionFragment = mDescriptionListFragment;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.position), myLocation);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        myLocation = savedInstanceState.getInt(getString(R.string.position));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
        return true;
    }

    public void openInstructionFragment() {
        if (tablet_bool) {
            InstructionFragment fragmentDetected = (InstructionFragment)
                    getSupportFragmentManager()
                            .findFragmentByTag(getString(R.string.fragment_instruction));

            if (fragmentDetected != null && fragmentDetected.isVisible()) {
                fragmentDetected.onPositionChanged(myLocation);
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.instruction_fragment_container, instructionFragment,
                                getString(R.string.fragment_instruction))
                        .commit();
            }

        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_detail_layout, instructionFragment,
                            getString(R.string.fragment_instruction))
                    .addToBackStack(null)
                    .commit();
        }
    }
}
