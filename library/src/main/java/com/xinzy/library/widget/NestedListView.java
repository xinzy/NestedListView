package com.xinzy.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinzy on 16/12/18.
 */
public class NestedListView extends LinearLayout
{
    private static final String TAG = "NestedListView";
    private static final boolean DEBUG = true;

    protected Context mContext;
    private NestedAdapter mAdapter;
    private List<View> mCachedItemViews;
    private LayoutInflater mInflater;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public static interface OnItemClickListener
    {
        void onItemClick(NestedListView listView, View itemView, int position);
    }

    public static interface OnItemLongClickListener
    {
        void onItemLongClick(NestedListView listView, View itemView, int position);
    }

    public NestedListView(Context context)
    {
        super(context);
        init(context);
    }

    public NestedListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public NestedListView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        mContext = context;
        setOrientation(VERTICAL);
        mInflater = LayoutInflater.from(mContext);
        mCachedItemViews = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener)
    {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public NestedAdapter getAdapter()
    {
        return mAdapter;
    }

    public void setAdapter(NestedAdapter adapter)
    {
        mAdapter = adapter;
        if (mAdapter != null)
        {
            mAdapter.setListView(this);
        }
        updateUI();
    }

    public void updateUI()
    {
        if (mAdapter == null || mAdapter.getCount() == 0)
        {
            mCachedItemViews.clear();
            removeAllViews();
        } else
        {
            final int size = mAdapter.getCount();
            final int childrenCount = getChildCount();
            int cacheSize = mCachedItemViews.size();
            if (size < childrenCount)
            {
                removeViews(size, childrenCount - size);
                while (cacheSize > size)
                {
                    mCachedItemViews.remove(cacheSize - 1);
                    cacheSize --;
                }
            }

            for (int i = 0; i < size; i++)
            {
                View convertView;
                if (cacheSize > i)
                {
                    convertView = mCachedItemViews.get(i);
                } else
                {
                    convertView = mInflater.inflate(mAdapter.getLayout(), this, false);
                    mCachedItemViews.add(convertView);
                }
                mAdapter.onBindData(convertView, i);
                convertView.setOnClickListener(new OnItemClick(convertView, i));
                convertView.setOnLongClickListener(new OnItemLongClick(convertView, i));

                if (convertView.getParent() == null)
                {
                    addView(convertView);
                }
            }
        }
    }

    public static abstract class NestedAdapter<T>
    {
        private final Object   mLock = new Object();
        private List<T>        mDatas;
        private NestedListView mListView;

        public NestedAdapter(List<T> mDatas)
        {
            this.mDatas = mDatas;
        }

        void setListView(NestedListView mListView)
        {
            this.mListView = mListView;
        }

        public int getCount()
        {
            return mDatas == null ? 0 : mDatas.size();
        }

        public T getItem(int position)
        {
            return mDatas != null ? mDatas.get(position) : null;
        }

        void onBindData(View convertView, int position)
        {
            onBindData(convertView, getItem(position), position);
        }

        public abstract int getLayout();

        public abstract void onBindData(View convertView, T item, int position);


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

        public void notifyDataSetChanged()
        {
            if (mListView != null)
            {
                mListView.updateUI();
            }
        }
    }

    private class OnItemClick implements OnClickListener
    {
        private View mConvertView;
        private int mPosition;

        public OnItemClick(View view, int position)
        {
            this.mConvertView = view;
            this.mPosition = position;
        }

        @Override
        public void onClick(View v)
        {
            if (mOnItemClickListener != null)
            {
                mOnItemClickListener.onItemClick(NestedListView.this, mConvertView, mPosition);
            }
        }
    }

    private class OnItemLongClick implements OnLongClickListener
    {
        private View mConvertView;
        private int mPosition;

        public OnItemLongClick(View view, int position)
        {
            this.mConvertView = view;
            this.mPosition = position;
        }

        @Override
        public boolean onLongClick(View v)
        {
            if (mOnItemLongClickListener != null)
            {
                mOnItemLongClickListener.onItemLongClick(NestedListView.this, mConvertView, mPosition);
                return true;
            }
            return false;
        }
    }

    private void debug(String msg)
    {
        if (DEBUG)
        {
            Log.d(TAG, "debug: " + msg);
        }
    }
}
