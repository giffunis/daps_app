package MemoryTest;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.giffunis.dapsapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {
    private static final String BODY_QUESTION = "question";
    private static final String ANSWERS_LIST = "answers";
    private TextView bodyText_;
    private RecyclerView recyclerView_;
    private String bodyQuestion_;
    private ArrayList<String> imagesUrl_;
    OnImageListener mCallback_;

    public ImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image, container, false);

        bodyText_ = (TextView) view.findViewById(R.id.body_question);
        bodyText_.setText(bodyQuestion_);

        //Inicialización RecyclerView
        recyclerView_ = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView_.setHasFixedSize(true);

        final ImagesAdapter adapter = new ImagesAdapter(imagesUrl_);
        adapter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("pulsada la imagen: " + recyclerView_.getChildAdapterPosition(v));
            }
        });
        recyclerView_.setAdapter(adapter);
        recyclerView_.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bodyQuestion_ = getArguments().getString(BODY_QUESTION);
        imagesUrl_ = getArguments().getStringArrayList(ANSWERS_LIST);

        try{
            mCallback_ = (OnImageListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement OnImageListener");
        }
    }

    public interface OnImageListener{
        public void imageResult(String bodyQuestion, String userAnswer);
    }
}
