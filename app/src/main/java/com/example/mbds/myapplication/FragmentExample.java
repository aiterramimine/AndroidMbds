package com.example.mbds.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


public class FragmentExample extends AppCompatActivity implements MasterFragment.OnArticleSelectedListener {

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_example);

        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment masterFragment = new MasterFragment();
        ft.add(R.id.fragment_container, masterFragment);
        ft.commit();
    }

    @Override
    public void onItemSelected(String itemName) {
        //Log.d(getLocalClassName(), "The id is : " + id);
        DetailsFragment detailsFragment = DetailsFragment.newInstance(itemName);
        fm.beginTransaction().replace(R.id.fragment_container, detailsFragment).commit();
        //detailsFragment.updateItemName();

    }

}
