package com.xinzy.library.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by YANGSHAOZENG227 on 2017-03-22.
 */
public abstract class NestedAdapterView extends ViewGroup
{
    public NestedAdapterView(Context context)
    {
        super(context);
    }

    public NestedAdapterView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public NestedAdapterView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs)
    {

    }
}
