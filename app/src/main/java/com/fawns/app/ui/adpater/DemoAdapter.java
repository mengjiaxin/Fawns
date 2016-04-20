package com.fawns.app.ui.adpater;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fawns.app.R;

import java.util.ArrayList;

/**
 * Project Fawns
 * Package com.fawns.app.ui.adpater
 * Author Mengjiaxin
 * Date 2016/4/19 17:14
 * Desc 请用一句话来描述作用
 */
public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder> {

    private ArrayList<String> mDataset = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.layout_item_demo_title);
        }
    }

    public DemoAdapter(ArrayList<String> dataset) {
        mDataset.clear();
        mDataset.addAll(dataset);
    }

    @Override
    public DemoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_demo, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset.get(position));

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}