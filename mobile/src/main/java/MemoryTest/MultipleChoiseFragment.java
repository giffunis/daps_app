package MemoryTest;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giffunis.dapsapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MultipleChoiseFragment extends Fragment {

    private static final String BODY_QUESTION = "question";
    private static final String ANSWERS_LIST = "answers";
    private String bodyQuestion_;
    private ArrayList<String> answersList_;


    public MultipleChoiseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multiple_choise, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bodyQuestion_ = getArguments().getString(BODY_QUESTION);
        answersList_ = getArguments().getStringArrayList(ANSWERS_LIST);
    }
}
