package MemoryTest;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.giffunis.dapsapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleChoiseFragment extends Fragment {

    private static final String BODY_QUESTION = "question";
    private static final String ANSWERS_LIST = "answers";
    private static final String USER_ANSWER = "userAnswer";

    ArrayList<String> answersList_;
    String bodyQuestion_;
    OnSingleChoiseSelectListener mCallback;

    public SingleChoiseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.single_choise_layout, container, false);
        SingleChoiseArrayAdapter adapter = new SingleChoiseArrayAdapter(getContext(), answersList_);

        TextView bodyQuestion = (TextView) view.findViewById(R.id.body_question);
        bodyQuestion.setText(bodyQuestion_);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.singleChoiseResult(bodyQuestion_,answersList_.get(position));
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Resources resources = context.getResources();
        answersList_ = getArguments().getStringArrayList(ANSWERS_LIST);
        bodyQuestion_ = getArguments().getString(BODY_QUESTION);

        try{
            mCallback = (OnSingleChoiseSelectListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement OnQuizesListSelectedListener");
        }

    }

    public interface OnSingleChoiseSelectListener{
        public void singleChoiseResult(String bodyQuestion, String userAnswer);
    }
}
