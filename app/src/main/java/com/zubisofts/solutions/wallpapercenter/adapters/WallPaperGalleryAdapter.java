package com.zubisofts.solutions.wallpapercenter.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zubisofts.solutions.wallpapercenter.R;
import com.zubisofts.solutions.wallpapercenter.model.WallPaper;

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
    public void onBindViewHolder(@NonNull final WallPaperItemHolder holder, final int position) {

//        holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.down_to_up));

        if(wallPapers.get(position) != null){
            holder.txtName.setText(wallPapers.get(position).getName());
            Glide.with(holder.itemView.getContext())
                    .load(wallPapers.get(position).getImageResource())
                    .into(holder.image);
            holder.image.setImageResource(wallPapers.get(position).getImageResource());
            holder.image.setTransitionName("position_"+position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    wallPaperItemClickListener.onWallPaperSelected(position,holder.image);
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

    public interface WallPaperItemClickListener{
        public void onWallPaperSelected(int position, ImageView imageView);
    }
}
