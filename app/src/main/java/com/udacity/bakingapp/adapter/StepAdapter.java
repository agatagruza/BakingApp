package com.udacity.bakingapp.adapter;

import android.content.Context;
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

import java.util.ArrayList;

public class StepAdapter
        extends RecyclerView.Adapter<StepAdapter.RecipeStepAdapterViewHolder> {

    private Context myContext;
    private String recipeLabel;
    private ArrayList<com.udacity.bakingapp.data.stepsArrayList> stepsArrayList;

    public class RecipeStepAdapterViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayoutSteps;
        public TextView steps_tv;
        public ImageView mThumbnailView;

        public RecipeStepAdapterViewHolder(LinearLayout view) {
            super(view);
            linearLayoutSteps = view;
            steps_tv = new TextView(myContext);
            mThumbnailView = new ImageView(myContext);

            linearLayoutSteps.addView(mThumbnailView);
            linearLayoutSteps.addView(steps_tv);
            linearLayoutSteps.setElevation(10);
        }
    }

    @NonNull
    @Override
    public RecipeStepAdapterViewHolder onCreateViewHolder
            (@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayoutSteps = new LinearLayout(myContext);
        LinearLayout.LayoutParams myLinearLayoutKEY =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutSteps.setLayoutParams(myLinearLayoutKEY);
        myLinearLayoutKEY.setMargins(20, 10, 20, 10);
        linearLayoutSteps.setBackgroundResource(R.color.colorPrimary);

        RecipeStepAdapterViewHolder recipeStep
                = new RecipeStepAdapterViewHolder(linearLayoutSteps);
        return recipeStep;
    }

    public StepAdapter(Context context, String recipeName,
                       ArrayList<com.udacity.bakingapp.data.stepsArrayList> recipeStepList) {
        myContext = context;
        recipeLabel = recipeName;
        stepsArrayList = recipeStepList;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepAdapterViewHolder holder, final int position) {
        final com.udacity.bakingapp.data.stepsArrayList stepLocation = stepsArrayList.get(position);

        holder.linearLayoutSteps.setContentDescription(stepLocation.getBriefDescription());

        LinearLayout.LayoutParams linearLayoutText =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutText.weight = 1f;
        linearLayoutText.gravity = Gravity.CENTER;
        holder.steps_tv.setLayoutParams(linearLayoutText);
        holder.steps_tv.setTextAppearance(myContext, R.style.StepCardText);
        holder.steps_tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        holder.steps_tv.setText(stepLocation.getBriefDescription());
        holder.steps_tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_navigate_next_black_56dp, 0);

        LinearLayout.LayoutParams thumbnailKEY =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        thumbnailKEY.weight = 3f;
        thumbnailKEY.gravity = Gravity.CENTER;
        holder.mThumbnailView.setLayoutParams(thumbnailKEY);
        Picasso.get()
                .load(Uri.parse(stepLocation.getThumbnailURL()))
                .placeholder(R.drawable.ic_cake_gold_56dp)
                .error(R.drawable.ic_cake_gold_56dp)
                .into(holder.mThumbnailView);


        holder.linearLayoutSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity currentActivity = (DetailActivity) myContext;
                currentActivity.setLocation(position);
                currentActivity.openInstructionFragment();
            }
        });
    }

    @Override
    public int getItemCount() {
        return stepsArrayList.size();
    }
}
