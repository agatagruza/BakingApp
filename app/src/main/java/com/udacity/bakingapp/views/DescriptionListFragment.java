package com.udacity.bakingapp.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.DetailActivity;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.adapter.StepAdapter;
import com.udacity.bakingapp.data.IngredientList;
import com.udacity.bakingapp.data.stepsArrayList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DescriptionListFragment extends Fragment {

    @BindView(R.id.ingredients_text)
    TextView mIngredientsText;
    @BindView(R.id.description_list_rv)
    RecyclerView mRecipeStepRecyclerView;
    private StepAdapter myStepAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    //Mandatory constructor for instantiating the fragment
    public DescriptionListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    //onCreateView is called when fragment that we just created is inflated
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        final View myView =
                inflater.inflate(R.layout.description_fragment, viewGroup, false);
        ButterKnife.bind(this, myView);

        extractIngredients();

        mRecipeStepRecyclerView.setHasFixedSize(true);

        ArrayList<stepsArrayList> stepArrayList =
                ((DetailActivity) getActivity()).getStepsArrayList();
        String recipeLabel = ((DetailActivity) getActivity()).getRecipeTAG();

        myStepAdapter = new StepAdapter(getContext(), recipeLabel, stepArrayList);
        mRecipeStepRecyclerView.setAdapter(myStepAdapter);

        myLayoutManager = new LinearLayoutManager(getContext());
        mRecipeStepRecyclerView.setLayoutManager(myLayoutManager);

        return myView;
    }

    public void extractIngredients() {
        ArrayList<IngredientList> ingredients = ((DetailActivity) getActivity()).getIngredientArrayList();
        //mIngredientsText.setTypeface(null, Typeface.BOLD);
        //mIngredientsText.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        mIngredientsText.setText(getString(R.string.ingredients_label) + "\n");
        for (int i = 0; i < ingredients.size(); i++) {
            IngredientList addIngredient = ingredients.get(i);
            String ingredientLine =
                    "\n" + "\u2022  " + addIngredient.getAmount() + " "
                            + addIngredient.getMeasure() + "\t\t"
                            + addIngredient.getIngredientTag();
            mIngredientsText.append(ingredientLine);
            mIngredientsText.setMovementMethod(new ScrollingMovementMethod());
        }
    }
}
