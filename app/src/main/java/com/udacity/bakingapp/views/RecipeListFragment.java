package com.udacity.bakingapp.views;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakingapp.MainActivity;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.adapter.FullRecipeAdapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment {

    @BindView(R.id.recyclerView_rv)
    RecyclerView mCardRecyclerView;
    private RecyclerView.Adapter mCardAdapter;
    private RecyclerView.LayoutManager mCardLayoutManager;

    //Mandatory constructor for instantiating the fragment
    public RecipeListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    //onCreateView is called when fragment that we just created is inflated
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle BUNDLE) {
        final View myView =
                inflater.inflate(R.layout.fragment_recipe_list, viewGroup, false);
        ButterKnife.bind(this, myView);

        mCardRecyclerView.setHasFixedSize(true);

        //If tablet present have 3 buttons per row.
        if (MainActivity.tablet_bool) {
            mCardLayoutManager = new GridLayoutManager(getContext(), 3,
                    LinearLayoutManager.VERTICAL, false);
            mCardRecyclerView.setLayoutManager(mCardLayoutManager);
        } else {
            mCardLayoutManager = new LinearLayoutManager(getContext());
            mCardRecyclerView.setLayoutManager(mCardLayoutManager);
        }
        new RefillRecipes().execute();
        return myView;
    }

    //Based on Udacity DisplayURL lesson
    public class RefillRecipes extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL recipeURL = recipeURL();
                HttpURLConnection urlConnection = (HttpURLConnection) recipeURL.openConnection();
                try {
                    InputStream in = urlConnection.getInputStream();
                    Scanner scanner = new Scanner(in);
                    scanner.useDelimiter("\\A");
                    boolean hasInput = scanner.hasNext();
                    if (hasInput) {
                        return scanner.next();
                    } else {
                        return null;
                    }
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public URL recipeURL() {
            Uri recipeURL = Uri.parse(MainActivity.recipeURL);
            try {
                return new URL(recipeURL.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String jsonString) {
            mCardAdapter = new FullRecipeAdapter(getContext(), jsonString);
            mCardRecyclerView.setAdapter(mCardAdapter);
        }
    }
}
