package com.example.mysos;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import static android.widget.Toast.*;



public class AddContact extends AppCompatActivity {

    SQLiteDatabase.CursorFactory factory;
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "sample_database";
    public static final String TABLE_NAME = "contact";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";

    public AddContact() {

        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        final MySOSDB db = new MySOSDB(this);
        final EditText editName = (EditText) findViewById(R.id.addName);
        final EditText editPhone = (EditText) findViewById(R.id.addPhone);
        Button submitButton = (Button) findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameInput = editName.getText().toString();
                String phoneInput = editPhone.getText().toString();
                boolean isInserted = db.insertData(nameInput,phoneInput);
                if (isInserted){
                    showMessage("1");
                    System.out.println("!!!!");
                }else{
                    showMessage("0");
                }
            }
        });
    }

    private void showMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    private void saveToDB() {
        MySOSDB database = new MySOSDB(this);


    }



}

