package com.example.mysos;

import android.media.MediaPlayer;
import android.media.MediaRecorder;

import android.os.Environment;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import android.os.Bundle;
import android.view.View;


import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;

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

        setContentView(R.layout.activity_main);

//        buttonStart = (Button) findViewById(R.id.button);
//        buttonStop = (Button) findViewById(R.id.button2);
//        buttonPlayLastRecordAudio = (Button) findViewById(R.id.button3);
//        buttonStopPlayingRecording = (Button)findViewById(R.id.button4);
//
//        buttonStop.setEnabled(false);
//        buttonPlayLastRecordAudio.setEnabled(false);
//        buttonStopPlayingRecording.setEnabled(false);
//
//        random = new Random();
//
//        buttonStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(checkPermission()) {
//
//                    AudioSavePathInDevice =
//                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
//                                    CreateRandomAudioFileName(5) + "AudioRecording.3gp";
//
//                    MediaRecorderReady();
//
//                    try {
//                        mediaRecorder.prepare();
//                        mediaRecorder.start();
//                    } catch (IllegalStateException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//
//                    buttonStart.setEnabled(false);
//                    buttonStop.setEnabled(true);
//
//                    Toast.makeText(test.this, "Recording started",
//                            Toast.LENGTH_LONG).show();
//                } else {
//                    requestPermission();
//                }
//
//            }
//        });
//
//        buttonStop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mediaRecorder.stop();
//                buttonStop.setEnabled(false);
//                buttonPlayLastRecordAudio.setEnabled(true);
//                buttonStart.setEnabled(true);
//                buttonStopPlayingRecording.setEnabled(false);
//
//                Toast.makeText(test.this, "Recording Completed",
//                        Toast.LENGTH_LONG).show();
//            }
//        });
//
//        buttonPlayLastRecordAudio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) throws IllegalArgumentException,
//                    SecurityException, IllegalStateException {
//
//                buttonStop.setEnabled(false);
//                buttonStart.setEnabled(false);
//                buttonStopPlayingRecording.setEnabled(true);
//
//                mediaPlayer = new MediaPlayer();
//                try {
//                    mediaPlayer.setDataSource(AudioSavePathInDevice);
//                    mediaPlayer.prepare();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                mediaPlayer.start();
//                Toast.makeText(test.this, "Recording Playing",
//                        Toast.LENGTH_LONG).show();
//            }
//        });
//
//        buttonStopPlayingRecording.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                buttonStop.setEnabled(false);
//                buttonStart.setEnabled(true);
//                buttonStopPlayingRecording.setEnabled(false);
//                buttonPlayLastRecordAudio.setEnabled(true);
//
//                if(mediaPlayer != null){
//                    mediaPlayer.stop();
//                    mediaPlayer.release();
//                    MediaRecorderReady();
//                }
//            }
//        });

    }


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


    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }
}