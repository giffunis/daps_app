package MemoryTest;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.giffunis.dapsapp.R;

import at.markushi.ui.CircleButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumberFragment extends Fragment {
    private static final String BODY_QUESTION = "question";
    private String bodyQuestion_;
    OnNumberListener mCallback_;

    public NumberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_number, container, false);

        TextView bodyQuestion = (TextView) view.findViewById(R.id.body_question);
        final TextView answer = (TextView) view.findViewById(R.id.answer);
        ImageButton btn_minus  = (ImageButton) view.findViewById(R.id.btn_minus);
        ImageButton btn_plus = (ImageButton) view.findViewById(R.id.btn_plus);
        CircleButton btn_accept = (CircleButton) view.findViewById(R.id.btn_accept);

        bodyQuestion.setText(bodyQuestion_);
        answer.setText("0");

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getContext(), "+1", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 60);
                toast.show();
                int aux = Integer.valueOf(answer.getText().toString()) + 1;
                answer.setText(Integer.toString(aux));
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast;

                switch (answer.getText().toString()) {
                    case "0":
                        toast = Toast.makeText(getContext(), "0", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 60);
                        toast.show();
                        break;
                    default:
                        toast = Toast.makeText(getContext(), "-1", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 60);
                        toast.show();
                        int aux = Integer.valueOf(answer.getText().toString()) - 1;
                        answer.setText(Integer.toString(aux));
                        break;
                }
            }
        });

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback_.numberResult(bodyQuestion_,Integer.valueOf(answer.getText().toString()));
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bodyQuestion_ = getArguments().getString(BODY_QUESTION);

        try{
            mCallback_ = (OnNumberListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement OnNumberListener");
        }
    }

    public interface OnNumberListener{
        public void numberResult(String bodyQuestion, int userAnswer);
    }
}
