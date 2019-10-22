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


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    SQLiteDatabase.CursorFactory factory;
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "sample_database";
    public static final String TABLE_NAME = "contact";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";

    public ContactFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);
        super.onCreate(savedInstanceState);
        final MySOSDB db = new MySOSDB(this.getActivity());
        final EditText editName = (EditText) rootView.findViewById(R.id.name1);
        final EditText editPhone = (EditText) rootView.findViewById(R.id.phone1);
        Button submitButton = (Button) rootView.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameInput = editName.getText().toString();
                String phoneInput = editPhone.getText().toString();
                boolean isInserted = db.insertData(nameInput,phoneInput);
                if (isInserted){
                    showMessage("1");
                }else{
                    showMessage("0");
                }
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    private void showMessage(String message) {

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }

    private void saveToDB() {
        MySOSDB database = new MySOSDB(getActivity());


    }



}


