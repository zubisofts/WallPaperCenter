package com.zubisofts.solutions.wallpapercenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WallPaperGalleryAdapter extends RecyclerView.Adapter<WallPaperGalleryAdapter.WallPaperItemHolder> {

    private ArrayList<WallPaper> wallPapers;
    private WallPaperItemClickListener wallPaperItemClickListener;


    public WallPaperGalleryAdapter() {
    }

    @NonNull
    @Override
    public WallPaperItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaper_list_item, parent, false);
        return new WallPaperItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallPaperItemHolder holder, final int position) {
        if(wallPapers.get(position) != null){
            holder.txtName.setText(wallPapers.get(position).getName());
            holder.image.setImageResource(wallPapers.get(position).getImageResource());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    wallPaperItemClickListener.onWallPaperSelected(wallPapers.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return wallPapers.size();
    }

    public void setWallPaperItemClickListener(WallPaperItemClickListener wallPaperItemClickListener) {
        this.wallPaperItemClickListener = wallPaperItemClickListener;
    }

    public void setWallPapers(ArrayList<WallPaper> wallPapers) {
        this.wallPapers = wallPapers;
        notifyDataSetChanged();
    }

    static class WallPaperItemHolder extends RecyclerView.ViewHolder{

        private TextView txtName;
        private ImageView image;

        public WallPaperItemHolder(@NonNull View itemView) {
            super(itemView);

            txtName=itemView.findViewById(R.id.txtName);
            image=itemView.findViewById(R.id.image);

        }
    }

    interface WallPaperItemClickListener{
        public void onWallPaperSelected(WallPaper wallPaper);
    }
}
