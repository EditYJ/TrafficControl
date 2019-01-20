package com.edityj.trafficcontrol.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.edityj.trafficcontrol.R;
import com.edityj.trafficcontrol.adapter.MyAdapter;
import com.edityj.trafficcontrol.pojo.ITEMDATA;
import com.edityj.trafficcontrol.util.InitItemData;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    private RecyclerView.LayoutManager mLayoutManager;
//    private List<String> mData = new ArrayList<String>();
    private List<ITEMDATA> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data=InitItemData.getStartInstance().getInitItemDatas();
        Log.e("m读取数据d:", data.toString());
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //菜单点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.start:
                //Toast.makeText(this,"获取电量", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add:
                
                //Toast.makeText(this,"添加", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                //Toast.makeText(this,"删除", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    //TODO EditYJ 获取电量
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Toast.makeText(this, "选项菜单显示之前onPrepareOptionsMenu方法会被调用，你可以用此方法来根据打当时的情况调整菜单", Toast.LENGTH_LONG).show();
        // 如果返回false，此方法就把用户点击menu的动作给消费了，onCreateOptionsMenu方法将不会被调用
        return true;
    }

    private void init(){
        recyclerView = findViewById(R.id.recycle);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        myAdapter = new MyAdapter(data);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                ImageView isSelect = view.findViewById(R.id.im_is_true);
//                TextView textView = view.findViewById(R.id.text_traffic_code);
//
//
//                TextView textView_copy = findViewById(R.id.screen_one);
//                textView_copy.setText(textView.getText());
//
//
//                if(isSelect.getVisibility()==View.GONE){
//                    isSelect.setVisibility(View.VISIBLE);
//                    //Log.e("设置图片","显示");
//                }else{
//                    isSelect.setVisibility(View.GONE);
//                    //Log.e("设置图片","消失");
//                }
            }
        });


    }
}
