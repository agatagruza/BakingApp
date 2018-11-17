package com.udacity.bakingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.udacity.bakingapp.views.RecipeListFragment;

public class MainActivity extends AppCompatActivity {

    public static final String recipeURL
            = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    public static boolean tablet_bool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (!isOnline(MainActivity.this)) buildDialog(MainActivity.this).show();
        else {
            tablet_bool = getResources().getBoolean(R.bool.tabletSize);
            if (savedInstanceState == null) {
                RecipeListFragment recipeList = new RecipeListFragment();
                getSupportFragmentManager()
                        .beginTransaction().add(R.id.main_layout, recipeList).commit();
            }
            setContentView(R.layout.activity_main);
        }
    }

    // Function to check internet connectivity
    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        } else {
            return ni.isConnected();
        }
    }

    // Pop up window when no internet access
    public AlertDialog.Builder buildDialog(Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(R.string.no_connection);
        builder.setMessage(R.string.exit);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        return builder;
    }

}
