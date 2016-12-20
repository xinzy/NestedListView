package com.xinzy.library.widget;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xinzy on 16/12/18.
 */
public class ViewHolder
{

    private View              mConvertView;
    private SparseArray<View> mViews;

    private ViewHolder(View convertView)
    {
        mConvertView = convertView;
        mViews = new SparseArray<>();
    }

    public static ViewHolder get(View convertView, ViewGroup parent, LayoutInflater inflater, int layoutId)
    {
        if (convertView == null)
        {
            convertView = inflater.inflate(layoutId, parent, false);
            ViewHolder holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            return holder;
        } else
        {
            return (ViewHolder) convertView.getTag();
        }
    }

    public <T extends View> T getView(int id)
    {
        View view = mViews.get(id);
        if (view == null)
        {
            view = mConvertView.findViewById(id);
            mViews.put(id, view);
        }

        return (T) view;
    }

    public View getConvertView()
    {
        return mConvertView;
    }

}
