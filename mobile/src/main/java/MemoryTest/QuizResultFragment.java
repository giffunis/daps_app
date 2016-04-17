package MemoryTest;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.giffunis.dapsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizResultFragment extends Fragment {
    private static final String HITS = "hits";
    private static final String N_QUESTIONS = "nQuestions";
    private int n_;
    private int hits_;

    public QuizResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz_result, container, false);

        TextView result = (TextView) view.findViewById(R.id.result);
        result.setText("Tus resultados son: " + hits_ + "/" + n_);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        hits_ = getArguments().getInt(HITS);
        n_ = getArguments().getInt(N_QUESTIONS);
    }
}
