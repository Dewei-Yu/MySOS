package com.example.mysos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.VideoView;

import java.util.ArrayList;

public class test extends AppCompatActivity {
//    static final int REQUEST_VIDEO_CAPTURE = 1;
//    private Uri videoUri = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);
//
//        String[] data = {"APPLE","BANNA"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(test.this, android.R.layout.simple_list_item_1, data);
//        ListView listView = (ListView) this.findViewById(R.id.test777);
//        listView.setAdapter(adapter);
//
//
//        Button testButton = (Button) findViewById(R.id.testButton);
//        testButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//                if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
//                }
//
//
//
//            }
//        });

    }

}
