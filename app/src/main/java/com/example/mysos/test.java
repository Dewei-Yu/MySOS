package com.example.mysos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.VideoView;

import java.util.ArrayList;

public class test extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String[] data = {"APPLE","BANNA"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(test.this, android.R.layout.simple_list_item_1, data);
        ListView listView = (ListView) this.findViewById(R.id.test777);
        listView.setAdapter(adapter);


    }
}
