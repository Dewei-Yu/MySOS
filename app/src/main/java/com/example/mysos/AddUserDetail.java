package com.example.mysos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddUserDetail extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_detail);
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
                    db.deleteUser(1);
                    isInserted = db.insertUser(nameInput, phoneInput);
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
        Toast.makeText(AddUserDetail.this, message, Toast.LENGTH_SHORT).show();
    }
}
