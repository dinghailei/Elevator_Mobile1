package com.example.a57617.elevator_mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a57617.elevator_mobile.R;
import com.example.a57617.elevator_mobile.StoreySittingActivity;
import com.example.a57617.elevator_mobile.TimeSettingActivity;
import com.example.a57617.elevator_mobile.bean.StoreySitting;

import java.util.ArrayList;

public class StoreySittingAdapter extends BaseAdapter implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
    private LayoutInflater mInflater;
    private Context mContext;
    private int mLayoutId;
    private ArrayList<StoreySitting> StoreySittingArrayList;
    private int mBackground;

    private StoreySittingAdapter.ViewHolder holder = null;

    public StoreySittingAdapter(Context context, int layout_id, ArrayList<StoreySitting> activitiesArrayList, int background){
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mLayoutId = layout_id;
        this.StoreySittingArrayList = activitiesArrayList;
        mBackground = background;

    }

    @Override
    public int getCount() {
        return StoreySittingArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return StoreySittingArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new StoreySittingAdapter.ViewHolder();
            convertView = mInflater.inflate(mLayoutId,parent,false);
            holder.tv_time = convertView.findViewById(R.id.tv_starting_time);
            holder.tv_destination = convertView.findViewById(R.id.tv_destination);
            holder.tv_date = convertView.findViewById(R.id.tv_date);
            holder.ck_available = convertView.findViewById(R.id.ck_available);
            holder.btn_delete = convertView.findViewById(R.id.btn_delete);
            holder.iv_next = convertView.findViewById(R.id.iv_next);
            holder.ll_edit_storey_sitting = convertView.findViewById(R.id.ll_edit_storey_sitting);
            holder.ll_item = convertView.findViewById(R.id.ll_item);
            convertView.setTag(holder);
        } else {
            holder = (StoreySittingAdapter.ViewHolder) convertView.getTag();
        }
        final StoreySitting storeySitting = StoreySittingArrayList.get(position);
        convertView.setBackgroundResource(R.drawable.edit_text);
        holder.tv_time.setText(storeySitting.getTime());
        holder.tv_destination.setText(storeySitting.getDestination());
        holder.tv_date.setText(storeySitting.getDate());
        if (storeySitting.isAvailable()) {
            holder.ck_available.setChecked(true);
        } else {
            holder.ck_available.setChecked(false);
        }
        if (storeySitting.isEditing()) {
            holder.btn_delete.setVisibility(View.VISIBLE);
            holder.ck_available.setVisibility(View.GONE);
            holder.iv_next.setVisibility(View.VISIBLE);
            holder.ll_edit_storey_sitting.setClickable(true);
            holder.ll_edit_storey_sitting.setFocusable(true);
            holder.ll_edit_storey_sitting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击进入编辑楼层
                    Intent intent = new Intent(mContext,StoreySittingActivity.class);
                    mContext.startActivity(intent);
                }
            });
        } else {
            holder.btn_delete.setVisibility(View.GONE);
            holder.ck_available.setVisibility(View.VISIBLE);
            holder.iv_next.setVisibility(View.GONE);
            holder.ll_edit_storey_sitting.setClickable(false);
            holder.ll_edit_storey_sitting.setFocusable(false);
        }
        return convertView;
    }

    public final class ViewHolder {
        public TextView tv_time;
        public TextView tv_destination;
        public TextView tv_date;
        public CheckBox ck_available;
        public ImageButton btn_delete;
        public ImageView iv_next;
        public LinearLayout ll_edit_storey_sitting;
        public LinearLayout ll_item;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(mContext,"你选了第" + position + "个时间", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return true;
    }

}
