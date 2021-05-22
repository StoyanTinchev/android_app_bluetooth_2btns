package com.example.app_led_on_off;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BluetoothActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        RecyclerView recyclerView = findViewById(R.id.bluetooth_names);
        recyclerView.setHasFixedSize(true);
        BluetoothList bluetoothList = new BluetoothList(this, MainActivity.bluetooth);
        recyclerView.setAdapter(bluetoothList);
        recyclerView.setLayoutManager(new LinearLayoutManager
                (this, RecyclerView.VERTICAL, false));
    }
}