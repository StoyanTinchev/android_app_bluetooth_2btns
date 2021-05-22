package com.example.app_led_on_off;

public class Bluetooths
{
    private final String bt_name;
    private final String bt_adress;

    public String getBt_name()
    {
        return bt_name;
    }

    public String getBt_adress()
    {
        return bt_adress;
    }

    public Bluetooths(String bt_name, String bt_adress)
    {
        this.bt_name = bt_name;
        this.bt_adress = bt_adress;
    }
}
