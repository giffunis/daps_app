package MemoryTest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.giffunis.dapsapp.R;
import com.orm.SugarRecord;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleChoiseFragment extends Fragment {

    SingleChoiseArrayAdapter adapter;
    ListView listView;

    public SingleChoiseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.listview_layout, container, false);

        adapter = new SingleChoiseArrayAdapter(getContext(), quizesList);
        listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        return view;
    }

}
