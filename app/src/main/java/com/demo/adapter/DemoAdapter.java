package com.demo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.been.DemoBeen;
import com.demo.main.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jugal on 7/4/16.
 */
public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder>{
    private ArrayList<DemoBeen> items;
    private Context context;

    public DemoAdapter(Context context,ArrayList<DemoBeen> items){
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_card_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DemoBeen item = items.get(position);

            holder.name.setText(item.get_title());
            Picasso.with(context)
                    .load(item.get_img_uri())
                    .into(holder.card);










        //All the thing you gonna show in the item
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView card;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_movie);
            card = (ImageView) itemView.findViewById(R.id.img_thumbnail);
        }
    }


}
