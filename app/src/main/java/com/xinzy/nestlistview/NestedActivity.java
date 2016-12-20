package com.xinzy.nestlistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xinzy.library.widget.NestedListView;

import java.util.List;

public class NestedActivity extends AppCompatActivity implements NestedListView.OnItemClickListener,
        NestedListView.OnItemLongClickListener
{
    private static final String TAG = "NestedActivity";

    private NestedListView mListView;
    private Adapter        mAdapter;
    private List<String>   mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested);
        mListView = (NestedListView) findViewById(R.id.nested);

        mDatas = MainActivity.getDatas();
        mAdapter = new Adapter(mDatas);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
    }

    public void onAdd(View v)
    {
        mAdapter.add("Fuck");
    }

    public void onLose(View v)
    {
        mAdapter.remove(0);
    }

    @Override
    public void onItemClick(NestedListView listView, View itemView, int position)
    {
        Log.d(TAG, "onItemClick: position = " + position);
    }

    @Override
    public void onItemLongClick(NestedListView listView, View itemView, int position)
    {
        Log.w(TAG, "onItemLongClick: position = " + position);
    }

    private class Adapter extends NestedListView.NestedAdapter<String>
    {

        public Adapter(List<String> mDatas)
        {
            super(mDatas);
        }

        @Override
        public int getLayout()
        {
            return R.layout.item_name;
        }

        @Override
        public void onBindData(View convertView, String item, int position)
        {
            TextView text = (TextView) convertView.findViewById(R.id.text);
            text.setText(item);
        }
    }
}
