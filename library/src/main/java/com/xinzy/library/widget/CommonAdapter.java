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
public abstract class CommonAdapter<T> extends BaseAdapter
{
    private final Object   mLock = new Object();
    private List<T>        mDatas;
    private LayoutInflater mInflater;

    public CommonAdapter(List<T> mDatas)
    {
        this.mDatas = mDatas;
    }

    @Override
    public int getCount()
    {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public T getItem(int position)
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
        if (mInflater == null)
        {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        ViewHolder holder = ViewHolder.get(convertView, parent, mInflater, getLayout());
        onBindData(holder, mDatas.get(position), position);
        return holder.getConvertView();
    }

    public abstract int getLayout();
    public abstract void onBindData(ViewHolder holder, T item, int position);

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
        mDatas = lists;

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
        }
    }
}
