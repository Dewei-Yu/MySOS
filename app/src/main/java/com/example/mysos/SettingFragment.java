package com.example.mysos;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {


    public SettingFragment() {
        // Required empty public constructor
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        super.onCreate(savedInstanceState);
        final MySOSDB db = new MySOSDB(this.getActivity());
        final TextView userNameInput = (TextView) rootView.findViewById(R.id.userName);
        final TextView userPhoneInput = (TextView) rootView.findViewById(R.id.userPhone);
        Cursor allData = db.getUserDetail();
        if(allData.getCount() != 0){

            int i=0;
            while(i<1 && allData.moveToNext()){
                userNameInput.setText("My Name: " + allData.getString(0));
                userPhoneInput.setText("My Phone Number: " + allData.getString(1));
                i++;
            }
        }

        Button editButton = (Button) rootView.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteUser(1);
                Intent intent = new Intent(getActivity(),AddUserDetail.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

}
