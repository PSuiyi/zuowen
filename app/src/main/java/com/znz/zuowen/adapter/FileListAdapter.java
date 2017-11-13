package com.znz.zuowen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.znz.zuowen.R;
import com.znz.zuowen.event.EventGoto;
import com.znz.zuowen.event.EventTags;
import com.znz.zuowen.ui.home.week.FileListAct;
import com.znz.zuowen.utils.StaticValues;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileListAdapter extends BaseAdapter {
    //填充数据的List
    List<Map<String, Object>> list = new ArrayList<>();

    //用来导入布局
    private LayoutInflater inflater = null;
    private FileListAct mainActivity;
    private int listMode;

    //构造器
    public FileListAdapter(List<Map<String, Object>> list, Context context, int listMode) {
        this.list = list;
        this.mainActivity = (FileListAct) context;
        this.listMode = listMode;
        inflater = LayoutInflater.from(context);
    }

    private int width;
    private int height;
    private ViewHolder holder;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        int id = (int) list.get(position).get("id");
        return new Long(id);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            switch (listMode) {
                case StaticValues.MODE_LIST:
                    convertView = inflater.inflate(R.layout.item_lv_file, null);
                    holder.name = (TextView) convertView.findViewById(R.id.name);
                    holder.path = (TextView) convertView.findViewById(R.id.path);
                    holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                    holder.name.setText(list.get(position).get("name").toString());
                    holder.path.setText(list.get(position).get("path").toString());
                    break;

            }
            //为view设置标签
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        switch ((int) list.get(position).get("type")) {
            case StaticValues.FILE_ITEM_TYPE_DIRECTORY:
                holder.icon.setImageResource(R.mipmap.file);
                break;
            case StaticValues.FILE_ITEM_TYPE_FILE:
                holder.icon.setImageResource(R.mipmap.word);
                break;
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int type = (int) list.get(position).get("type");
                switch (type) {
                    case StaticValues.FILE_ITEM_TYPE_DIRECTORY:
                        mainActivity.load(list.get(position).get("path").toString());
                        break;

                    case StaticValues.FILE_ITEM_TYPE_FILE:
                        EventBus.getDefault().post(new EventGoto(EventTags.GOTO_FILE_UPLOAD, list.get(position).get("path").toString()));
                        break;
                }

            }
        });
        width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        convertView.measure(width, height);
        height = convertView.getMeasuredHeight();
        width = convertView.getMeasuredWidth();
        return convertView;
    }

    static class ViewHolder {
        ImageView icon;
        TextView name;
        TextView path;
    }
}
