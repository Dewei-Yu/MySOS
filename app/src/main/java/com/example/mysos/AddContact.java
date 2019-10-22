package com.example.mysos;


import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;


public class AddContact extends FragmentActivity {


    public AddContact() {

        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        final MySOSDB db = new MySOSDB(this);
        final EditText editName = (EditText) findViewById(R.id.addUserName);
        final EditText editPhone = (EditText) findViewById(R.id.addUserPhone);
        Button submitButton = (Button) findViewById(R.id.submitUser);
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

        Toast.makeText(AddContact.this, message, Toast.LENGTH_SHORT).show();

    }

    private void saveToDB() {
        MySOSDB database = new MySOSDB(this);


    }






    }

