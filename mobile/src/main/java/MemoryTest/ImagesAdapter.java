package MemoryTest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giffunis.dapsapp.R;

import java.util.ArrayList;

/**
 * Created by drcaspa on 6/4/16.
 * email: giffunis@gmail.com
 */
public class ImagesAdapter extends RecyclerView.Adapter<String> {

    ArrayList<String> imagesUrls_;

    public ImagesAdapter(ArrayList<String> imagesUrls){
        imagesUrls_ = imagesUrls;
    }

    @Override
    public String onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_images_item, parent, false);

    }

    @Override
    public void onBindViewHolder(String holder, int position) {

    }

    @Override
    public int getItemCount() {
        return imagesUrls_.size();
    }
}
