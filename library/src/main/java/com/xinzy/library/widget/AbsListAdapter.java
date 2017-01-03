package com.xinzy.library.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinzy on 16/12/18.
 */
public abstract class AbsListAdapter<T> extends BaseAdapter
{
    private final Object   mLock = new Object();
    private List<T>        mDatas;
    private LayoutInflater mInflater;

    public AbsListAdapter(List<T> mDatas)
    {
        this.mDatas = mDatas;
    }

    @Override
    public int getCount()
    {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public Object getItem(int position)
    {
        return mDatas != null ? mDatas.get(position) : null;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            if (mInflater == null)
            {
                mInflater = LayoutInflater.from(parent.getContext());
            }

            convertView = mInflater.inflate(getLayout(position), parent, false);
        }

        onBindData(position, convertView, mDatas.get(position));

        return convertView;
    }

    public void clear()
    {
        if (mDatas != null)
        {
            mDatas.clear();

            synchronized (mLock)
            {
                notifyDataSetChanged();
            }
        }
    }

    public void replace(List<T> lists)
    {
        if (mDatas != null)
        {
            mDatas.clear();
        } else
        {
            mDatas = lists;
        }

        synchronized (mLock)
        {
            notifyDataSetChanged();
        }
    }

    public void remove(int index)
    {
        if (index >= 0 && mDatas != null && index < mDatas.size())
        {
            mDatas.remove(index);

            synchronized (mLock)
            {
                notifyDataSetChanged();
            }
        }
    }

    public void addAll(List<T> lists)
    {
        if (lists != null)
        {
            if (mDatas == null)
            {
                mDatas = lists;
            } else
            {
                mDatas.addAll(lists);
            }

            synchronized (mLock)
            {
                notifyDataSetChanged();
            }
        }
    }

    public void add(T t)
    {
        if (mDatas == null)
        {
            mDatas = new ArrayList<>();
        }
        mDatas.add(t);

        synchronized (mLock)
        {
            notifyDataSetChanged();
        }
    }

    public void add(int index, T t)
    {
        if (index >= 0 && mDatas != null && mDatas.size() > index)
        {
            mDatas.add(index, t);

            synchronized (mLock)
            {
                notifyDataSetChanged();
            }
        } else
        {
            add(t);
        }
    }

    public abstract int getLayout(int position);

    public abstract void onBindData(int position, View convertView, T data);
}
