package com.xinzy.nestlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xinzy.library.widget.NestedGridView;

import java.util.ArrayList;
import java.util.List;

public class NestedGridActivity extends AppCompatActivity implements NestedGridView.OnItemClickListener
{
    private static final String TAG = "NestedGridActivity";
    private NestedGridView mGridView;
    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_grid);

        mGridView = (NestedGridView) findViewById(R.id.gridView);
        mGridView.setOnItemClickListener(this);
        mAdapter = new Adapter(MainActivity.getDatas());
        mGridView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(NestedGridView parent, View itemView, int position)
    {
        Log.d(TAG, "onItemClick: position = " + position);
    }

    public void onAdd(View v)
    {
        mAdapter.add("Fuck");
    }

    public void onLose(View v)
    {
        mAdapter.remove(0);
    }

    public void onClear(View v)
    {
        mAdapter.clear();
    }

    public void onReplace(View v)
    {
        List<String> list = new ArrayList<>();
        list.add("New");
        list.add("Old");
        list.add("Good");

        mAdapter.replace(list);
    }

    class Adapter extends NestedGridView.NestedGridAdapter<String>
    {
        public Adapter(List<String> mDatas)
        {
            super(mDatas);
        }

        @Override
        public int getLayout()
        {
            return R.layout.item_grid;
        }

        @Override
        protected void onBindData(View convertView, String item, int position)
        {
            TextView text = (TextView) convertView.findViewById(R.id.text);
            text.setText(item);
        }
    }
}
