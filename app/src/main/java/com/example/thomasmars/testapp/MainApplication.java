package com.example.thomasmars.testapp;

import android.app.Application;
import android.bluetooth.BluetoothSocket;

/**
 * Created by thoma_000 on 07.05.2016.
 */
public class MainApplication extends Application {
    public int rowId = 15;

    private BluetoothSocket bluetoothSocket;

    public BluetoothSocket getBluetoothSocket() {
        return bluetoothSocket;
    }

    public void setBluetoothSocket(BluetoothSocket bs) {
        bluetoothSocket = bs;
    }
}
