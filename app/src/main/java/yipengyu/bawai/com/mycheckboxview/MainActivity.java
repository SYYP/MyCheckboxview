package yipengyu.bawai.com.mycheckboxview;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import yipengyu.bawai.com.mycheckboxview.adapter.Myadapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerview;
    private List<String> list;
    private Myadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //添加视图
        initData();
        inintview();

    }

    private void inintview() {
        //获得资源id
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        findViewById(R.id.commit).setOnClickListener(this);
        //设置模式
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //设置适配器
        recyclerview.setHasFixedSize(true);
        adapter = new Myadapter(list, this);
        recyclerview.setAdapter(adapter);
        //添加分割线
        recyclerview.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        adapter.ItemClick(new Myadapter.RecyclerItem() {
            @Override
            public void ItemClick(View view, int position) {
                adapter.setSelectItem(position);
            }

            @Override
            public void ItemLongClick(View view, int position) {
                adapter.setShowBox();
                //设置选中的项
                adapter.setSelectItem(position);
                adapter.notifyDataSetChanged();

            }
        });
    }





    @Override
    public void onClick(View v) {
        Intent it=new Intent(MainActivity.this,Twoactivity.class);
        startActivity(it);
        //获取你选中的item
        Map<Integer, Boolean> map = adapter.getMap();
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i)) {
                Log.d("TAG", "你选了第：" + i + "项");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //全选
            case R.id.all:
                Map<Integer, Boolean> map = adapter.getMap();
                for (int i = 0; i < map.size(); i++) {
                    map.put(i, true);
                    adapter.notifyDataSetChanged();
                }
                break;
            //全不选
            case R.id.no_all:
                Map<Integer, Boolean> m = adapter.getMap();
                for (int i = 0; i < m.size(); i++) {
                    m.put(i, false);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 为列表添加测试数据
     */
    private void initData() {

        File directory = Environment.getExternalStorageDirectory();
        File[] files = directory.listFiles();
        list = new ArrayList<>();
        for (File file : files) {
            list.add(file.getName());
        }
    }

}
