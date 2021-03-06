package com.zubisofts.solutions.wallpapercenter.fragments;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.zubisofts.solutions.wallpapercenter.R;
import com.zubisofts.solutions.wallpapercenter.model.WallPaper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class WallPaperDestinationFragment extends BottomSheetDialogFragment {

    OptionListener optionListener;

    public WallPaperDestinationFragment(OptionListener optionListener) {
        this.optionListener=optionListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wall_paper_destination_list_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String[] titles = new String[]{"Set As Home-Screen Only", "Set As Lock-Screen Only", "Set As Home-Screen And Lock-Screen"};
        int[] icons = new int[]{R.drawable.ic_home, R.drawable.ic_lock_screen, R.drawable.ic_add_home};
        ItemAdapter adapter = new ItemAdapter(titles, icons,optionListener);
        recyclerView.setAdapter(adapter);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        final TextView text;
        final ImageView icon;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_wall_paper_destination_list_dialog_item, parent, false));
            text = itemView.findViewById(R.id.text);
            icon = itemView.findViewById(R.id.icon);
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

        private int[] icons;
        private String[] titles;

        private OptionListener listener;

        public ItemAdapter(String[] titles, int[] icons, OptionListener listener) {
            this.titles = titles;
            this.icons = icons;
            this.listener=listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.text.setText(titles[position]);
            holder.icon.setImageResource(icons[position]);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onOptionSelected(position);
                    dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }

    }

    public interface OptionListener {
        public void onOptionSelected(int position);
    }

}