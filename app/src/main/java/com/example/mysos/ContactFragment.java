package com.example.mysos;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);
        super.onCreate(savedInstanceState);
        TextView person = (TextView) rootView.findViewById(R.id.person1);
        MySOSDB db = new MySOSDB(this.getActivity());
        Cursor allData = db.getAllData();
        if(allData.getCount() != 0){
            StringBuffer buffer = new StringBuffer();
            int i=0;
            while(i<1 && allData.moveToNext()){
                person.setText("Name: " + allData.getString(0) + "\n"+ "Phone Number:" +allData.getString(1));
                i++;
            }
            System.out.println(buffer.toString());
        }

        Button submitButton = (Button) rootView.findViewById(R.id.button2);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddContact.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

}


