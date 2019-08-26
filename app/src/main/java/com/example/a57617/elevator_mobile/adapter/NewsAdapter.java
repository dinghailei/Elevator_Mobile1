package com.example.a57617.elevator_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.a57617.elevator_mobile.R;
import com.example.a57617.elevator_mobile.bean.News;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

    private LayoutInflater mInflater;
    private Context mContext;
    private int mLayoutId;
    private ArrayList<News> newsArrayList;
    private int mBackground;

    private ViewHolder holder = null;

    public NewsAdapter(Context context, int layout_id, ArrayList<News> activitiesArrayList, int background){
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mLayoutId = layout_id;
        this.newsArrayList = activitiesArrayList;
        mBackground = background;

    }

    @Override
    public int getCount() {
        return newsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(mLayoutId,parent,false);
            holder.tv_content = convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final News news = newsArrayList.get(position);
        convertView.setBackgroundResource(R.drawable.edit_text);
        holder.tv_content.setText(news.getContent());
        return convertView;
    }

    public final class ViewHolder {
        public TextView tv_content;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return true;
    }

}
