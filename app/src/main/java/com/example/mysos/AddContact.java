package com.example.mysos;


import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddContact extends FragmentActivity {


    public AddContact() {

        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        final MySOSDB db = new MySOSDB(this);
        final EditText editName =  findViewById(R.id.addUserName);
        final EditText editPhone =  findViewById(R.id.addUserPhone);
        Button submitButton = findViewById(R.id.submitUser);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameInput = editName.getText().toString();
                String phoneInput = editPhone.getText().toString();
                boolean isInserted;
                if(isValidPhone(phoneInput)) {
                    isInserted = db.insertData(nameInput, phoneInput);
                    if (isInserted){
                        showMessage("Insertion is successful!");
                    }else{
                        showMessage("Fails!");
                    }
                    onBackPressed();
                }else {
                    showMessage("Wrong Input! Please enter a valid phone number!");
                }

            }
        });

    }

    private boolean isValidPhone(String phone){


        Pattern p = Pattern.compile("[0-9]{10}");
        Matcher m = p.matcher(phone);
        return (m.find() && m.group().equals(phone));

    }

    private void showMessage(String message) {

        Toast.makeText(AddContact.this, message, Toast.LENGTH_SHORT).show();

    }

}

