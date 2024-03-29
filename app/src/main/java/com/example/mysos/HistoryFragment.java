package com.example.mysos;


import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


    public HistoryFragment() {
    }

    private View currentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        currentView = rootView;
        super.onCreate(savedInstanceState);
        final MySOSDB db = new MySOSDB(this.getActivity());
        ArrayList<String> history = new ArrayList<>();
        Cursor allHistory = db.getAllHistory();
        if (allHistory.getCount() != 0) {
            while (allHistory.moveToNext()) {
                history.add("Record " + allHistory.getString(0) + ": "+"\n" + allHistory.getString(1));
            }
        }
        String[] newHistory = history.toArray(new String[history.size()]);
        if(history.size()!=0) {
            ArrayAdapter adapter = new ArrayAdapter<String>(currentView.getContext(), android.R.layout.simple_list_item_1, newHistory);
            ListView listView = currentView.findViewById(R.id.history);
            listView.setAdapter(adapter);
        }
        return rootView;
    }

}
