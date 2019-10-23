package com.example.mysos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                db.deleteUser(1);
                String nameInput = editName.getText().toString();
                String phoneInput = editPhone.getText().toString();
                boolean isInserted = db.insertUser(nameInput,phoneInput);
                if (isInserted){
                    showMessage("User detail is updated!");
                }else{
                    showMessage("Update fails");
                }
                onBackPressed();
            }
        });

    }

    private void showMessage(String message) {
        Toast.makeText(AddUserDetail.this, message, Toast.LENGTH_SHORT).show();
    }
}
