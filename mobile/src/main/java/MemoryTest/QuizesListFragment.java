package MemoryTest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.giffunis.dapsapp.R;
import org.json.JSONException;
import java.util.ArrayList;



public class QuizesListFragment extends Fragment {
    private static final String UNSOLVEDQUIZNAMES = "unsolvedQuizNames";
    private static final String UNSOLVEDQUIZIDS = "unsolvedQuizIds";
    private ListView listView;
    private QuizArrayAdapter adapter;
    SimpleQuizObject quizesList_;
    OnQuizesListSelectedListener mCallback;


    public QuizesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.listview_layout, container, false);

        adapter = new QuizArrayAdapter(getContext(), quizesList_.getQuizName_());
        listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(this.adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.startQuizSelected(position);
            }
        });
        return view;
    }


    public interface OnQuizesListSelectedListener{
        public void startQuizSelected(int position);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ArrayList<String> quizNames = getArguments().getStringArrayList(UNSOLVEDQUIZNAMES);
        ArrayList<String> quizIds = getArguments().getStringArrayList(UNSOLVEDQUIZIDS);
        try {
            quizesList_ = new SimpleQuizObject(quizNames,quizIds);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try{
            mCallback = (OnQuizesListSelectedListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement OnQuizesListSelectedListener");
        }
    }
}
