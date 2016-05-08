package com.example.thomasmars.testapp;

import android.app.Application;
import android.bluetooth.BluetoothSocket;

public class MainApplication extends Application {
    private BluetoothSocket bluetoothSocket;

    BluetoothSocket getBluetoothSocket() {
        return bluetoothSocket;
    }

    void setBluetoothSocket(BluetoothSocket bs) {
        bluetoothSocket = bs;
    }
}
