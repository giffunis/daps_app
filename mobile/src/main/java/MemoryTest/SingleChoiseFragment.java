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

import com.giffunis.dapsapp.QuizesActivity;
import com.giffunis.dapsapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleChoiseFragment extends Fragment {

    private static final String CURRENT_QUESTION_ID = "qId";
    private static final String BODY_QUESTION = "question";
    private static final String ANSWERS_LIST = "answers";
    private static final String USER_ANSWER = "userAnswer";

    ArrayList<String> answersList;
    String bodyQuestion;
    int quizId;

    OnSingleChoiseSelectListener mCallback;

    public SingleChoiseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.listview_layout, container, false);
        SingleChoiseArrayAdapter adapter = new SingleChoiseArrayAdapter(getContext(), answersList);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.singleChoiseResult(quizId,answersList.get(position),position + 1);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        final Resources resources = context.getResources();
        answersList = getArguments().getStringArrayList(ANSWERS_LIST);
        bodyQuestion = getArguments().getString(BODY_QUESTION);
        quizId = getArguments().getInt(CURRENT_QUESTION_ID);

        try{
            mCallback = (OnSingleChoiseSelectListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement OnQuizesListSelectedListener");
        }

    }

    public interface OnSingleChoiseSelectListener{
        public void singleChoiseResult(int qId, String userAnswer, int aId);
    }
}
