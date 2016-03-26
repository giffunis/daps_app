package MemoryTest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.giffunis.dapsapp.R;

import java.util.ArrayList;


public class QuizesListFragment extends Fragment {

    private ListView listView;
    private QuizArrayAdapter adapter;
    private ArrayList<String> list;
    OnQuizesListSelectedListener mCallback;

    public QuizesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.listview_layout, container, false);

        list = getArguments().getStringArrayList("lista");

        adapter = new QuizArrayAdapter(getContext(), list);
        listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(this.adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.startQuizSelected(list.get(position));
            }
        });


        return view;

    }


    public interface OnQuizesListSelectedListener{
        public void startQuizSelected(String id);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mCallback = (OnQuizesListSelectedListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement OnQuizesListSelectedListener");
        }
    }
}
