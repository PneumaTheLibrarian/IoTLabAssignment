package com.example.myapplication.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Domain.domain;
import com.example.myapplication.R;
import com.majorik.sparklinelibrary.SparkLineLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class pin_screen extends RecyclerView.Adapter<pin_screen.Viewholder> {
    ArrayList<domain> dataList;
    DecimalFormat formatter;

    public pin_screen(ArrayList<domain> dataList) {
        this.dataList = dataList;
        formatter = new DecimalFormat("###,###,###,###.##");
    }

    @NonNull
    @Override
    public pin_screen.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.pin_screen,parent,false);
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull pin_screen.Viewholder holder, int position) {
        holder.sensorName.setText(dataList.get(position).getName());
        holder.currentValue.setText(formatter.format(dataList.get(position).getCurrent()));
        holder.changeAmount.setText(dataList.get(position).getChangeAmount());
        holder.lineChart.setData(dataList.get(position).getLineData());
        if (Integer.parseInt(dataList.get(position).getChangeAmount()) > 0){
            holder.changeAmount.setTextColor(Color.parseColor("#12c737"));
            holder.lineChart.setSparkLineColor(Color.parseColor("#12c737"));
        }else if (Integer.parseInt(dataList.get(position).getChangeAmount()) < 0){
            holder.changeAmount.setTextColor(Color.parseColor("#fc0000"));
            holder.lineChart.setSparkLineColor(Color.parseColor("#fc0000"));
        }else{
            holder.changeAmount.setTextColor(Color.parseColor("#ffffff"));
            holder.lineChart.setSparkLineColor(Color.parseColor("#ffffff"));
        }

        if (position == 0) {
            holder.mainLayout.setBackgroundResource(R.drawable.purple_background);
            holder.sensorName.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.white));
            holder.currentValue.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.white));
            holder.changeAmount.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.white));
        }
        String picName = "";
        switch (dataList.get(position).getName()){
            case "light":{
                picName = "light";
                break;
            }
            case "temperature":{
                picName = "temperature";
                break;
            }
            case "humidity":{
                picName = "humidity";
                break;
            }
        }
        int drawableResourceId = holder.itemView.getContext().getResources()
                .getIdentifier(picName, "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.logo);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView sensorName, currentValue, changeAmount;
        ImageView logo;
        SparkLineLayout lineChart;
        ConstraintLayout mainLayout;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            sensorName = itemView.findViewById(R.id.detailSensorName);
            currentValue = itemView.findViewById(R.id.pincurrentValue);
            changeAmount = itemView.findViewById(R.id.pinchangeAmount);
            logo = itemView.findViewById(R.id.logoImgDetail);
            lineChart = itemView.findViewById(R.id.lineGraph1);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
