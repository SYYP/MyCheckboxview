package yipengyu.bawai.com.mycheckboxview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yipengyu.bawai.com.mycheckboxview.R;

/**
 * date : ${Date}
 * author:衣鹏宇(ypu)
 */

public class Myadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
      private Context context;
      private List<String> list;
      private boolean isbox =false;//设置单选框是否显示
    private RecyclerItem re;
    //创建集合储存勾选
    private Map<Integer,Boolean> map=new HashMap<>();

    public Myadapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
        //初始化map
        ininmap();
    }

    private void ininmap() {
        for(int i=0;i<list.size();i++){
            map.put(i,false);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       //填充视图
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_item,null);
        ViewHolder ho=new ViewHolder(view);
        ho.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  re.ItemClick(v,(Integer) v.getTag());
            }
        });
        ho.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //不管显示隐藏，清空状态
                ininmap();
                re.ItemLongClick(v,(Integer) v.getTag());
                return true;
            }
        });
        return ho;
    }
    //设置是否显示CheckBox
    public void setShowBox() {
        //取反
        isbox = !isbox;
    }

    //点击item选中CheckBox
    public void setSelectItem(int position) {
        //对当前状态取反
        if (map.get(position)) {
            map.put(position, false);
        } else {
            map.put(position, true);
        }
        notifyItemChanged(position);
    }

    //返回集合给MainActivity
    public Map<Integer, Boolean> getMap() {
        return map;
    }
    /*
     *绑定视图
     */

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ViewHolder ho= (ViewHolder) holder;
            ho.tv.setText(list.get(position));
        //长按显示/隐藏
        if (isbox) {
            ho.box.setVisibility(View.VISIBLE);
        } else {
            ho.box.setVisibility(View.INVISIBLE);
        }
        Animation  anim= AnimationUtils.loadAnimation(context,R.anim.scalaction);
       //设置显示动画
        if(isbox){
            ho.box.startAnimation(anim);
            //设置Tag
            ho.itemView.setTag(position);

        }
       //设置checkbox监听
        ho.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                map.put(position,isChecked);
            }
        });
           //设置未选中的checkbox的状态
         if(map.get(position)==null){
             map.put(position,false);

         }
        ho.box.setChecked(map.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

     public static class ViewHolder extends RecyclerView.ViewHolder {
         TextView tv;
         CheckBox box;
         public ViewHolder(View itemView) {
             super(itemView);

             //获得资源ID
            tv= (TextView) itemView.findViewById(R.id.tv);
             box= (CheckBox) itemView.findViewById(R.id.cb);
         }
     }

      //创建一个接口
    public  interface RecyclerItem{
          void ItemClick(View view ,int position);
          void ItemLongClick( View view,int position);
      }

    public void ItemClick(RecyclerItem recy){
        this.re=recy;

    }
     public  void ItemLongClick(RecyclerItem recy){
         this.re=recy;

     }
}
