package com.example.mysos;


import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
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
                    String location = getLocation();
                    if ( location.equals("Permission denied")){
                        Toast.makeText(getContext(), "Please repress and permit GPS access", Toast.LENGTH_LONG).show();
                    }else{

                        MySOSDB mySOSDB = new MySOSDB(getActivity());
                        ArrayList<String> contactList = mySOSDB.getAllContactNumbers();
                        String myName = mySOSDB.getUserName();
                        for (String number : contactList){
                            SmsManager sms = SmsManager.getDefault();
                            PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 0, new Intent(), 0);
                            sms.sendTextMessage(number, null, "This is a emergency message sent from " + myName, pi,null);
                        }
                        Toast.makeText(getContext(), location, Toast.LENGTH_LONG).show();
                    }

                    break;
                case R.id.videoRecordButton:
                    Toast.makeText(getActivity(), "Click vidoe button", Toast.LENGTH_LONG).show();

                    break;
            }
        }
    }

    private void checkPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_PHONE_STATE
        };
        ArrayList<String> ungetPermissions = new ArrayList<>();

        for (String permission : permissions){
            if (ContextCompat.checkSelfPermission(getContext(), permission)
                    != PackageManager.PERMISSION_GRANTED){
                ungetPermissions.add(permission);
            }
        }

        if (ungetPermissions.size()>0){
            String[] ungetPer =ungetPermissions.toArray(new String[ungetPermissions.size()]);
            ActivityCompat.requestPermissions(getActivity(), ungetPer, 1);
        }


    }

    private String getLocation(){
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return "Permission denied";
        }else{
            GPSUtils gpsUtils = GPSUtils.getInstance(getContext());
            Location location = gpsUtils.getLocation();
            Double longitude = location.getLongitude();
            Double latitude = location.getLatitude();

            String result = "Longitude: " +  String.format("%.2f", longitude ) + " Latitude: " +  String.format("%.2f", latitude );
            return result;
        }

    }

}
