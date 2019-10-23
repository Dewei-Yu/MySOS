package com.example.mysos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(new HomeFragment()); //set initial fragment to home fragment

        ImageButton contactButton = (ImageButton) findViewById(R.id.contact_button);
        ImageButton homeButton = (ImageButton) findViewById(R.id.home_button);
        ImageButton historyButton = (ImageButton) findViewById(R.id.history_button);
        ImageButton settingButton = (ImageButton) findViewById(R.id.setting_button);
        Button testButton = (Button) findViewById(R.id.testButtonMain);          //-

        contactButton.setOnClickListener(new ButtonListener());
        historyButton.setOnClickListener(new ButtonListener());
        homeButton.setOnClickListener(new ButtonListener());
        settingButton.setOnClickListener(new ButtonListener());
        testButton.setOnClickListener(new ButtonListener());       //-
    }

    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.contact_button:
                    replaceFragment(new ContactFragment());
                    break;
                case R.id.home_button:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.history_button:
                    replaceFragment(new HistoryFragment());
                    break;
                case R.id.setting_button:
                    replaceFragment(new SettingFragment());
                    break;
                case R.id.testButtonMain:  //-
                    Intent intent = new Intent(getApplicationContext(),test.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame_layout, fragment);
        transaction.commit();
    }

}
