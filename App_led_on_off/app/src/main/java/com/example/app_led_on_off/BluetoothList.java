package com.example.app_led_on_off;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BluetoothList extends RecyclerView.Adapter<BluetoothList.MyViewHolder>
{
    private Context mContext;
    private ArrayList<Bluetooths> bluetoothsArrayList;

    public BluetoothList(Context mContext, ArrayList<Bluetooths> bluetoothsArrayList)
    {
        this.mContext = mContext;
        this.bluetoothsArrayList = bluetoothsArrayList;
    }

    @NonNull
    @Override
    public BluetoothList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bluetooths, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BluetoothList.MyViewHolder holder, int position)
    {
        String bt_name_text = bluetoothsArrayList.get(position).getBt_name();
        String bt_adress_text = bluetoothsArrayList.get(position).getBt_adress();
        if (bt_name_text.length() > 26)
            bt_name_text = bt_name_text.substring(0, 26) + "...";

        if (bt_adress_text.length() > 20)
            bt_adress_text = bt_adress_text.substring(0, 20) + "...";

        holder.bt_name.setText(bt_name_text);
        holder.bt_address.setText(bt_adress_text);
        holder.itemView.setOnClickListener(v ->
        {
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.putExtra("position", position);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount()
    {
        return bluetoothsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView bt_name, bt_address;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            bt_name = itemView.findViewById(R.id.bt_name);
            bt_address = itemView.findViewById(R.id.bt_address);
        }
    }
}
