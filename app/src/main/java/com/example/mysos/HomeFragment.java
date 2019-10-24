package com.example.mysos;


import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import java.time.*;


import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    static final int REQUEST_VIDEO_CAPTURE = 1;
    static View fragmentView = null;

    MediaRecorder mediaRecorder = null;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        fragmentView = view;
        ImageButton sosButton = (ImageButton) view.findViewById(R.id.sosButton);
        Button videoRecordButton = (Button) view.findViewById(R.id.videoRecordButton);


        sosButton.setOnClickListener(new ButtonListener());
        videoRecordButton.setOnClickListener(new ButtonListener());

        checkPermissions();

        // Inflate the layout for this fragment
        return view;

    }


    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.sosButton:

                    sendSMS();
                    Toast.makeText(getActivity(), "Message sent! 20 seconds audio recording starts!", Toast.LENGTH_LONG).show();
                    recordAudio();

                    break;
                case R.id.videoRecordButton:

                    dispatchTakeVideoIntent();

                    Toast.makeText(getActivity(), "Click vidoe button", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }


    private void checkPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE

        };
        ArrayList<String> ungetPermissions = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getContext(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                ungetPermissions.add(permission);
            }
        }

        if (ungetPermissions.size() > 0) {
            String[] ungetPer = ungetPermissions.toArray(new String[ungetPermissions.size()]);
            ActivityCompat.requestPermissions(getActivity(), ungetPer, 1);
        }


    }

    private String getLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return "Permission denied";
        } else {
            GPSUtils gpsUtils = GPSUtils.getInstance(getContext());
            Location location = gpsUtils.getLocation();
            Double longitude = location.getLongitude();
            Double latitude = location.getLatitude();

            String result = "Longitude: " + String.format("%.2f", longitude) + " Latitude: " + String.format("%.2f", latitude);
            return result;
        }

    }

    private void sendSMS() {
        String location = getLocation();
        if (location.equals("Permission denied")) {
            Toast.makeText(getContext(), "Please repress and permit GPS access", Toast.LENGTH_LONG).show();
        } else {
            checkPermissions();
            MySOSDB mySOSDB = new MySOSDB(getActivity());
            ArrayList<String> contactList = mySOSDB.getAllContactNumbers();
            String myName = mySOSDB.getUserName();
            for (String number : contactList) {
                SmsManager sms = SmsManager.getDefault();
                PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 0, new Intent(), 0);
                String msm = "Emergency, " + myName + " is at " + location;
                sms.sendTextMessage(number, null, msm, pi, null);
            }
            MySOSDB historyDB = new MySOSDB(this.getActivity());
            updateHistory(historyDB);
            Toast.makeText(getContext(), "Messages are sent!", Toast.LENGTH_LONG).show();
        }
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData(); //wait for send

            //display the video recorded
//            VideoView videoView = (VideoView) fragmentView.findViewById(R.id.videoView);
//            videoView.setVideoURI(videoUri);
//            videoView.start();
        }
    }

    private void updateHistory(MySOSDB db){
        Cursor allContact = db.getAllContact();
        if(allContact.getCount() != 0){
            while(allContact.moveToNext()){
                Date dateTime =java.util.Calendar.getInstance().getTime();
                String location = getLocation();
                String name = allContact.getString(0);
                String phoneNumber = allContact.getString(1);
                String record = "Location: "+ location + "\n"+ "Sent to: "+
                        name + " (" + phoneNumber+") " + "\n" +"Time: "+dateTime;
                db.insertHistory(record);
            }
        }

    }

    private String getFileDirectory(){
        return (Environment.getExternalStorageDirectory().getAbsolutePath() + "/MySOS/");
    }

    private String getPath(String nameOfFile) {

//        String sdStatus = Environment.getExternalStorageState();
//        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
//            return;
//        }
        FileOutputStream b = null;
        File file = new File(getFileDirectory());
        file.mkdirs();
        String pathName = getFileDirectory() + nameOfFile;
        return pathName;

    }

    private void recordAudio(){
        String pathSave = getPath(System.currentTimeMillis()+ "audio.3gp");

        mediaRecorder = new MediaRecorder();


//                    String pathSave = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_audio_record.3gp";
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(pathSave);
        try {
            mediaRecorder.prepare();
            System.out.println("strat reccording, Path is " + pathSave);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaRecorder.start();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //stop
        mediaRecorder.stop();

    }

}
