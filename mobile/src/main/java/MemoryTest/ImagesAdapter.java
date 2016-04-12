package MemoryTest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.giffunis.dapsapp.R;

import java.util.ArrayList;

/**
 * Created by drcaspa on 6/4/16.
 * email: giffunis@gmail.com
 */
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> implements View.OnClickListener {

    private ArrayList<String> urls_;
    private View.OnClickListener listener_;

    public ImagesAdapter(ArrayList<String> urls) {
        urls_ = urls;
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.quiz_images_item, viewGroup, false);

        itemView.setOnClickListener(this);
        ImagesViewHolder ivh = new ImagesViewHolder(itemView);
        return ivh;
    }

    public void setOnClickListener(View.OnClickListener listener){
        listener_ = listener;
    }

    @Override
    public void onBindViewHolder(ImagesViewHolder holder, int position) {
        String item = urls_.get(position);
        holder.bindImage(item);
    }

    @Override
    public int getItemCount() {
        return urls_.size();
    }

    @Override
    public void onClick(View v) {
        if (listener_ != null)
            listener_.onClick(v);
    }

    public class ImagesViewHolder extends RecyclerView.ViewHolder {

        private ImageView image_;
        private Context context_;

        public ImagesViewHolder(View itemView) {
            super(itemView);
            context_ = itemView.getContext();
            image_ = (ImageView) itemView.findViewById(R.id.image);
        }

        public void bindImage(String url){
            Glide
                    .with(context_)
                    .load(url)
                    .into(image_);
        }
    }
}
