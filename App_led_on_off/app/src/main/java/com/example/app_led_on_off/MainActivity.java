package com.example.app_led_on_off;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener
{

    Button button1;
    Button button2;
    TextView textView;

    String address = null, name = null;

    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    Set<BluetoothDevice> pairedDevices;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {setw();} catch (Exception e) {e.printStackTrace();}
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setw() throws IOException
    {
        textView = (TextView) findViewById(R.id.textView1);
        bluetooth_connect_device();


        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        button1.setOnTouchListener((v, event) ->
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
                touch_button("A");
            if (event.getAction() == MotionEvent.ACTION_UP)
                touch_button("B");
            return true;
        });

        button2.setOnTouchListener((v, event) ->
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
                touch_button("C");
            if (event.getAction() == MotionEvent.ACTION_UP)
                touch_button("D");
            return true;
        });
    }

    private void bluetooth_connect_device() throws IOException
    {
        try
        {
            myBluetooth = BluetoothAdapter.getDefaultAdapter();
            address = myBluetooth.getAddress();
            pairedDevices = myBluetooth.getBondedDevices();
            if (pairedDevices.size() > 0)
            {
                for (BluetoothDevice bt : pairedDevices)
                {
                    address = bt.getAddress().toString();
                    name = bt.getName().toString();
                    Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();

                }
            }

        }
        catch (Exception we)
        {
            we.printStackTrace();
        }
        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
        btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
        btSocket.connect();

        try { textView.setText("BT Name: " + name + "\nBT Address: " + address); }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v)
    {
        try { }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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