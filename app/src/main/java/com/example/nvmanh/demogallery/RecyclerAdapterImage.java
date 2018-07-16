package com.example.nvmanh.demogallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class RecyclerAdapterImage extends RecyclerView.Adapter<RecyclerAdapterImage.ViewHolder> {

    private List<Picture> mPictures;
    private Context mContext;

    public RecyclerAdapterImage(Context mContext, List<Picture> mPictures) {
        this.mContext = mContext;
        this.mPictures = mPictures;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_image_recycler, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picture p = mPictures.get(position);
        holder.text.setText(p.getName());
        Picasso.with(mContext).load(new File(p.getPathImage()))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mPictures.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text;
        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_demo);
            text = itemView.findViewById(R.id.text_name);
        }
    }
}
