package com.example.app_led_on_off;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity
{
    private TextView textView;

    public static ArrayList<Bluetooths> bluetooth = new ArrayList<>();
    private int position = -1;

    private BluetoothAdapter myBluetooth = BluetoothAdapter.getDefaultAdapter(); // get the mobile bluetooth adapter
    private BluetoothSocket btSocket = null;
    private static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_bluetooth();
        sett();
        getIntentMethod();

        if (position != -1)
            bluetooth_connect_device(position);
    }

    private void getIntentMethod()
    {
        position = getIntent().getIntExtra("position", -1);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void sett()
    {
        textView = findViewById(R.id.textView1);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        button1.setOnTouchListener((v, event) ->
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
                touch_button("A");
            if (event.getAction() == MotionEvent.ACTION_UP)
                touch_button("0");
            return true;
        });

        button2.setOnTouchListener((v, event) ->
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
                touch_button("B");
            if (event.getAction() == MotionEvent.ACTION_UP)
                touch_button("0");
            return true;
        });
    }

    public void newActivity(View view)
    {
        Intent intent = new Intent(this, BluetoothActivity.class);
        startActivity(intent);
    }

    private void add_bluetooth()
    {
        try
        {
            myBluetooth = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> pairedDevices = myBluetooth.getBondedDevices();
            if (pairedDevices.size() > 0)
                for (BluetoothDevice bt : pairedDevices)
                    bluetooth.add(new Bluetooths(bt.getName(), bt.getAddress()));
        }
        catch (Exception we)
        {
            we.printStackTrace();
        }
    }

    private void bluetooth_connect_device(int possition)
    {
        String address = bluetooth.get(possition).getBt_adress();
        String name = bluetooth.get(possition).getBt_name();
        BluetoothDevice disposition = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
        try
        {
            btSocket = disposition.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
            btSocket.connect();

            String bt_information = "BT Name: " + name + "\nBT Address: " + address;
            textView.setText(bt_information);
            Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void touch_button(String i)
    {
        try
        {
            if (btSocket != null)
                btSocket.getOutputStream().write(i.getBytes());
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}