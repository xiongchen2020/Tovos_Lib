package com.example.filgthhublibrary.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.filgthhublibrary.R;
import com.example.filgthhublibrary.network.bean.TeamGetModel;
import com.example.filgthhublibrary.view.listener.OnTeamItemClickListener;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.MyTVHolder> {

    private LayoutInflater mLayoutInflater;
//    private Context mContext;
    private List<TeamGetModel>  mData = new ArrayList<>();
    private OnTeamItemClickListener onRecyclerItemClickListener;
//    private boolean checkModel = false;
//    private boolean is_all_selected = false;

    public TeamAdapter(Context context, List<TeamGetModel> mData, OnTeamItemClickListener onRecyclerItemClickListener) {
        mLayoutInflater = LayoutInflater.from(context);
//        mContext = context;
        this.mData = mData;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override
    public TeamAdapter.MyTVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeamAdapter.MyTVHolder(mLayoutInflater.inflate(R.layout.item_team_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final TeamAdapter.MyTVHolder holder, final int pos) {
         holder.team_name.setText(mData.get(pos).getTeamName());
         holder.team_name.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onRecyclerItemClickListener.onTaskItemClick(pos);
             }
         });
//        holder.checkBox.setChecked(mData.get(pos).isIschecked());
//        holder.name.setText(mData.get(pos).getName());
//        holder.index.setText(""+(pos+1));
//        String formatType = "yyyy-MM-dd HH:mm:ss";
//        Log.e("111111111","转化前开始时间:"+mData.get(pos).getStatrtime());
//        Log.e("111111111","转date:"+ TimeUtils.longToDate(mData.get(pos).getStatrtime(),formatType));
//        holder.lc.setText("里程:"+ NumberUtils.format4(mData.get(pos).getMileage()));
//        try {
//            holder.start.setText("开始时间:"+TimeUtils.longToString(mData.get(pos).getStatrtime(),formatType));
//            holder.end.setText("结束时间:"+TimeUtils.longToString(mData.get(pos).getEndtime(),formatType));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        holder.ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("111111111","点击item:");
//                if (checkModel){
//                    if (holder.checkBox.isChecked()){
//                        holder.checkBox.setChecked(false);
//                    }else {
//                        holder.checkBox.setChecked(true);
//                    }
//                    mData.get(pos).setIschecked(holder.checkBox.isChecked());
//                }else {
//                    onRecyclerItemClickListener.onTaskItemClick(pos);
//                }
//            }
//        });
//
//        holder.ll.setOnLongClickListener(new View.OnLongClickListener(){
//            @Override
//            public boolean onLongClick(View v) {
//                Log.e("111111111","长按事件:");
//                onRecyclerItemClickListener.onTaskItemLongClick(pos);
//                return true;
//            }
//        });
//
//        holder.exp_rl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(holder.ll_detail.getVisibility() == View.VISIBLE){
//                    holder.ll_detail.setVisibility(View.GONE);
//                }else {
//                    holder.ll_detail.setVisibility(View.VISIBLE);
//                }
//                if(holder.ll_detail.getVisibility() == View.VISIBLE){
//                    holder.im.setSelected(true);
//                }else {
//                    holder.im.setSelected(false);
//                }
//            }
//        });

    }

//    public void setCheckModel(boolean checkModel){
//        this.checkModel = checkModel;
//    }
//
//    public void setSelectedAllFalse(){
//        for (int i=0;i<mData.size();i++){
//            mData.get(i).setIschecked(false);
//        }
//        notifyDataSetChanged();
//    }
//
//    public void setSelectedAll(){
//        for (int i=0;i<mData.size();i++){
//            if (i == 0){
//                is_all_selected =  mData.get(i).isIschecked();
//            }else {
//                if (is_all_selected){
//                    is_all_selected =  mData.get(i).isIschecked();
//                }
//            }
//        }
//
//        if (is_all_selected){
//            for (int i=0;i<mData.size();i++){
//                mData.get(i).setIschecked(false);
//            }
//        }else {
//            for (int i=0;i<mData.size();i++){
//                mData.get(i).setIschecked(true);
//            }
//        }
//        notifyDataSetChanged();


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class MyTVHolder extends RecyclerView.ViewHolder {
        TextView team_name;

        MyTVHolder(View itemView) {
            super(itemView);
            team_name = (TextView) itemView.findViewById(R.id.team_name);

        }
    }

}

