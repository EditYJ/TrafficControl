package com.edityj.trafficcontrol.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edityj.trafficcontrol.R;
import com.edityj.trafficcontrol.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> mData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mData.add("禁止通行");
        mData.add("禁止通行");
        mData.add("禁止通行");
        mData.add("禁止通行");
        mData.add("禁止通行");
        mData.add("禁止通行");
        mData.add("禁止通行");
        mData.add("前方事故");
        mData.add("前方事故");
        mData.add("前方事故");
        mData.add("前方事故");
        mData.add("前方事故");
        mData.add("前方事故");
        mData.add("前方事故");
        mData.add("前方事故");
        mData.add("前方事故");
        mData.add("慢");
        mData.add("60");
        mData.add("70");


        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void init(){
        recyclerView = findViewById(R.id.recycle);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        myAdapter = new MyAdapter(mData);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView isSelect = view.findViewById(R.id.im_is_true);
                TextView textView = view.findViewById(R.id.text_traffic_code);


                TextView textView_copy = findViewById(R.id.screen_one);
                textView_copy.setText(textView.getText());


                if(isSelect.getVisibility()==View.GONE){
                    isSelect.setVisibility(View.VISIBLE);
                    //Log.e("设置图片","显示");
                }else{
                    isSelect.setVisibility(View.GONE);
                    //Log.e("设置图片","消失");
                }
            }
        });


    }
}
