package com.example.mysos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    private  View currentView;
    private MySOSDB currentDB;
    public ContactFragment() {
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);
        currentView = rootView;
        super.onCreate(savedInstanceState);
        final MySOSDB db = new MySOSDB(this.getActivity());
        currentDB = db;
        updateContact(db,rootView);

        ImageButton deleteButton1 = (ImageButton) rootView.findViewById(R.id.delete1);
        deleteButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteRow(1);
                Intent intent = new Intent(getActivity(),AddContact.class);
                startActivity(intent);
            }
        });

        ImageButton deleteButton2 = (ImageButton) rootView.findViewById(R.id.delete2);
        deleteButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteRow(2);
                Intent intent = new Intent(getActivity(),AddContact.class);
                startActivity(intent);
            }
        });
        ImageButton deleteButton3 = (ImageButton) rootView.findViewById(R.id.delete3);
        deleteButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteRow(3);
                Intent intent = new Intent(getActivity(),AddContact.class);
                startActivity(intent);
            }
        });
        ImageButton deleteButton4 = (ImageButton) rootView.findViewById(R.id.delete4);
        deleteButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteRow(4);
                Intent intent = new Intent(getActivity(),AddContact.class);
                startActivity(intent);
            }
        });
        ImageButton deleteButton5 = (ImageButton) rootView.findViewById(R.id.delete5);
        deleteButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteRow(5);
                Intent intent = new Intent(getActivity(),AddContact.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    public void updateContact(MySOSDB db,View rootView){
        ArrayList<TextView> persons = new ArrayList<>();
        persons.add((TextView) rootView.findViewById(R.id.person1));
        persons.add((TextView) rootView.findViewById(R.id.person2));
        persons.add((TextView) rootView.findViewById(R.id.person3));
        persons.add((TextView) rootView.findViewById(R.id.person4));
        persons.add((TextView) rootView.findViewById(R.id.person5));

        Cursor allData = db.getAllContact();
        if(allData.getCount() != 0){
            int i=0;
            while(i<5 && allData.moveToNext()){
                persons.get(i).setText("Name: " + allData.getString(0) + "\n"+ "Phone Number: " +allData.getString(1));
                i++;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        updateContact(currentDB,currentView);
    }
}


