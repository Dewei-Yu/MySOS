package com.example.mysos;


import android.Manifest;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    static final int REQUEST_VIDEO_CAPTURE = 1;
    static View fragmentView = null;
    MediaRecorder mediaRecorder = null;
    private StorageReference mStorageRef;
    private StorageReference audioStorageRef;
    private String link =null;
    private String pathSave = null;
    private ImageButton sosButton;
    private static final String USER_DETAIL = "user";
    private static final String CONTACT_TABLE = "contact";

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        fragmentView = view;
        sosButton = view.findViewById(R.id.sosButton);
        Button videoRecordButton =  view.findViewById(R.id.videoRecordButton);
        sosButton.setOnClickListener(new ButtonListener());
        videoRecordButton.setOnClickListener(new ButtonListener());
        mStorageRef = FirebaseStorage.getInstance().getReference("Videos");
        audioStorageRef = FirebaseStorage.getInstance().getReference("Audios");
        checkPermissions();
        return view;

    }


    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            MySOSDB db = new MySOSDB(getActivity());
            if(db.getNumOfRows(USER_DETAIL) >0 && db.getNumOfRows(CONTACT_TABLE) > 0) {
                switch (view.getId()) {
                    case R.id.sosButton:
                        checkPermissions();
                        sendSMS("", "message");
                        sosButton.setImageResource(R.drawable.record);
                        Toast.makeText(getActivity(), "You have 10 seconds to record audio", Toast.LENGTH_LONG).show();
                        new SMSThread().start();
                        break;
                    case R.id.videoRecordButton:
                        checkPermissions();
                        dispatchTakeVideoIntent();
                        Toast.makeText(getActivity(), "Click vidoe button", Toast.LENGTH_LONG).show();
                        break;
                }
            }else {
                Toast.makeText(getActivity(), "Please add contact and your information first!", Toast.LENGTH_LONG).show();
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
            String result = "Longitude is: " + String.format("%.2f", longitude) + " Latitude is: " + String.format("%.2f", latitude);
            return result;
        }
    }

    private String getLocationURI() {
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
            String locationURL = "http://www.google.com/maps/place/"+ latitude+","+ longitude;
            return locationURL;
        }

    }



    private void sendSMS(String sms, String type) {
        MySOSDB mySOSDB = new MySOSDB(getActivity());
        ArrayList<String> contactList = mySOSDB.getAllContactNumbers();
        if(type.equals("audio")) {
            String location = getLocation();
            if (location.equals("Permission denied")) {
                Toast.makeText(getContext(), "Please repress and permit GPS access", Toast.LENGTH_LONG).show();
            } else {
                checkPermissions();
                for (String number : contactList) {
                    SmsManager smsManager = SmsManager.getDefault();
                    PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 0, new Intent(), 0);
                    smsManager.sendTextMessage(number, null, "Click the audio below, please help!", pi, null);
                    smsManager.sendTextMessage(number, null, sms, pi, null);
                }
                Toast.makeText(getContext(), "An audio has been sent!", Toast.LENGTH_LONG).show();
            }
        }else if(type.equals("video")){
            String location = getLocation();
            if (location.equals("Permission denied")) {
                Toast.makeText(getContext(), "Please repress and permit GPS access", Toast.LENGTH_LONG).show();
            } else {
                checkPermissions();
                for (String number : contactList) {
                    SmsManager smsManager = SmsManager.getDefault();
                    PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 0, new Intent(), 0);
                    smsManager.sendTextMessage(number, null, "Click the video below, please help!", pi, null);
                    smsManager.sendTextMessage(number, null, sms, pi, null);
                }
                Toast.makeText(getContext(), "A video has been sent!", Toast.LENGTH_LONG).show();
            }
        }else{
            String location = getLocation();
            if (location.equals("Permission denied")) {
                Toast.makeText(getContext(), "Please repress and permit GPS access", Toast.LENGTH_LONG).show();
            } else {
                checkPermissions();
                String myName = mySOSDB.getUserName();
                for (String number : contactList) {
                    SmsManager smsManager = SmsManager.getDefault();
                    PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 0, new Intent(), 0);
                    String message = myName + " has an emergency situation, location link is at below. Help!!!";
                    smsManager.sendTextMessage(number, null,message, pi, null);
                    smsManager.sendTextMessage(number, null,getLocationURI(), pi, null);
                }
                MySOSDB historyDB = new MySOSDB(this.getActivity());
                updateHistory(historyDB);

            }
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
            UploadVideo(videoUri);
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
        FileOutputStream b = null;
        File file = new File(getFileDirectory());
        file.mkdirs();
        String pathName = getFileDirectory() + nameOfFile;
        return pathName;

    }

    private void recordAudio(){

        pathSave = getPath(System.currentTimeMillis()+ "audio.3gp");
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(pathSave);
        try {
            mediaRecorder.prepare();
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

    private void UploadAudio(Uri audioUri) {
        // given file a name
        StorageReference Ref = audioStorageRef.child(System.currentTimeMillis() + "." + getExtension(audioUri));
        Ref.putFile(audioUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful());
                        Uri downloadUrl = urlTask.getResult();
                        link = downloadUrl.toString();
                        sendSMS(link, "audio");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                });
    }

    private void UploadVideo(Uri videoUri) {
        // given file a name
        StorageReference Ref = mStorageRef.child(System.currentTimeMillis() + "." + getExtension(videoUri));
        Ref.putFile(videoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), "Video Uploaded Successfully!", Toast.LENGTH_LONG).show();
                        // Get a URL to the uploaded content
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful());
                        Uri downloadUrl = urlTask.getResult();
                        link = downloadUrl.toString();
                        sendSMS(link, "video");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });
    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private class SMSThread extends Thread{
        @Override
        public void run() {

            recordAudio();
            File audiofile = new File(pathSave);
            Uri audioUri = Uri.fromFile(audiofile);
            UploadAudio(audioUri);
            sosButton.post(new Runnable() {
                @Override
                public void run() {
                    sosButton.setImageResource(R.drawable.sos);
                }
            });
        }
    }
}
