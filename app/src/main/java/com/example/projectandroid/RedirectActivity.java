package com.example.projectandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class RedirectActivity extends AppCompatActivity implements NavigationHost {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redirect);
        if(savedInstanceState==null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.conteiner2, new FrontPageFragment())
                    .commit();
        }
    }


    @Override
    public void navigateTo(Fragment fragment, boolean addToBackStack)
    {
        FragmentTransaction transaction=
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.conteiner2,fragment);
        if(addToBackStack)
        {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}