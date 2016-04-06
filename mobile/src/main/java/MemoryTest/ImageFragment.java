package MemoryTest;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.giffunis.dapsapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {
    private static final String BODY_QUESTION = "question";
    private static final String ANSWERS_LIST = "answers";

    private String bodyQuestion_;
    private ArrayList<String> imagesUrl_;
    private ImageView image;

    public ImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image, container, false);

        for (int i = 0; i < imagesUrl_.size(); i++){
            switch (i){
                case 0:
                    image = (ImageView) view.findViewById(R.id.image1);
                    break;
                case 1:
                    image = (ImageView) view.findViewById(R.id.image2);
                    break;
                case 2:
                    image = (ImageView) view.findViewById(R.id.image3);
                    break;
                case 3:
                    image = (ImageView) view.findViewById(R.id.image4);
                    break;
            }

            Glide
                    .with(this)
                    .load(imagesUrl_.get(i))
                    .into(image);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bodyQuestion_ = getArguments().getString(BODY_QUESTION);
        imagesUrl_ = getArguments().getStringArrayList(ANSWERS_LIST);
    }
}
