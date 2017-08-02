package com.example.tc2r.tinderhelptest.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tc2r.tinderhelptest.Models.ChapterModel;
import com.example.tc2r.tinderhelptest.Models.InformationModel;
import com.example.tc2r.tinderhelptest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.R.attr.description;import static android.R.attr.resource;
import static android.os.Build.MODEL;

/**
 * Created by TC2R on 8/1/17.
 */

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {

    private ArrayList<InformationModel> chapterInfoList;
    private Context context;
    private int i = 0;
    private int layoutId;

    public ChapterAdapter(ArrayList<InformationModel> chapterInfoList) {
        this.chapterInfoList = chapterInfoList;
    }

    public ChapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        if (i == 0){
            layoutId = R.layout.custom_row_1;
            i++;
        } else {
            layoutId = R.layout.custom_row_2;
            i = 0;
        }

        View view = inflater.inflate(layoutId, parent, false);
        ChapterViewHolder viewHolder = new ChapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChapterViewHolder holder, int position) {

// Gets the information Model to use for this row
        InformationModel currentModel = chapterInfoList.get(position);

        // parses the data from that model to the object views
            holder.description.setText(currentModel.getDescription());

            // effieciently loads the image with the provided url from the InformationModel of "currentModel"
            Picasso.with(context)
                    .load(currentModel.getImageUrl())
                    //.centerCrop()
                    .into(holder.chapterImage);
}

    @Override
    public int getItemCount() {

        return chapterInfoList.size();
    }

    public class ChapterViewHolder extends RecyclerView.ViewHolder {

        TextView description;
        ImageView chapterImage;

        public ChapterViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.textView);
            chapterImage = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

}
