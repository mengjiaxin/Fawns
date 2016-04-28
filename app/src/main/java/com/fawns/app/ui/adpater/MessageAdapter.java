package com.fawns.app.ui.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fawns.app.R;
import com.fawns.app.bean.MessageEntity;
import com.rockerhieu.emojicon.EmojiconTextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Project Fawns
 * Package com.fawns.app.ui.adpater
 * Author Mengjiaxin
 * Date 2016/4/22 18:16
 * Desc 请用一句话来描述作用
 */
public class MessageAdapter extends BaseAdapter {

    private List<MessageEntity> mData;
    private LayoutInflater mInflater;

    public MessageAdapter(Context context, List<MessageEntity> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public int getCount() {
        return this.mData.size();
    }

    @Override
    public MessageEntity getItem(int position) {
        return this.mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageEntity entity = this.mData.get(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            if (entity.isLeft()) {
                convertView = mInflater.inflate(R.layout.list_item_chat_left, null);
                holder.head = (CircleImageView) convertView.findViewById(R.id.chat_left_head_civ);
                holder.content = (EmojiconTextView) convertView.findViewById(R.id.chat_left_text_etv);
                holder.time = (TextView) convertView.findViewById(R.id.chat_left_time_tv);
            } else {
                convertView = mInflater.inflate(R.layout.list_item_chat_right, null);
                holder.head = (CircleImageView) convertView.findViewById(R.id.chat_right_head_civ);
                holder.content = (EmojiconTextView) convertView.findViewById(R.id.chat_right_text_etv);
                holder.time = (TextView) convertView.findViewById(R.id.chat_right_time_tv);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.content.setText(entity.getContent());
        holder.time.setText(entity.getTime());

        return convertView;
    }

    class ViewHolder {
        public CircleImageView head;
        public EmojiconTextView content;
        public TextView time;
    }
}
