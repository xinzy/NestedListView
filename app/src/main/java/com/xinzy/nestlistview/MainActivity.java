package com.xinzy.nestlistview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private static final String[] DATAS = {"Jack", "Tom", "Jim", "Jimy", "Mary", "John", "Lily", "Lucy", "Jerry", "Bob", "Wang"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onNested(View v)
    {
        startActivity(new Intent(this, NestedActivity.class));
    }

    public void onNestedGrid(View v)
    {
        startActivity(new Intent(this, NestedGridActivity.class));
    }

    public static List<String> getDatas()
    {
        return new ArrayList<>(Arrays.asList(DATAS));
    }
}
