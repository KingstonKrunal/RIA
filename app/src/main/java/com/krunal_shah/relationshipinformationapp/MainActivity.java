package com.krunal_shah.relationshipinformationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.krunal_shah.relationshipinformationapp.Fragments.FragmentHome;
import com.krunal_shah.relationshipinformationapp.Fragments.FragmentOne;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Email", "");

        if (name.isEmpty()) {
            FragmentOne f1 = new FragmentOne();
            fragmentTransaction.add(R.id.fragment_container, f1);
            fragmentTransaction.commit();
        } else {
            FragmentHome f1 = new FragmentHome();
            fragmentTransaction.add(R.id.fragment_container, f1);
            fragmentTransaction.commit();
        }
    }
}
