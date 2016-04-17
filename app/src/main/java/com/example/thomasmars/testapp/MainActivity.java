package com.example.thomasmars.testapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private BluetoothSocket bs;

    private void sendBtMessage(String msg2send) {

        // Create socket
        try {

            if (!bs.isConnected()) {
                Log.i("Main Activity", "CONNECTED!");
                bs.connect();
            }
            Log.i("Main Activity", "Message to send: " + msg2send);

            /*int maxPkt = bs.getMaxReceivePacketSize();
            Log.i("Main Activity", "max receive packet size ?" + maxPkt);
            int maxTPkt = bs.getMaxTransmitPacketSize();
            Log.i("Main Activity", "max transmit packet size ? " + maxTPkt);*/

/*            InputStream is = bs.getInputStream();
            Log.i("Main Activity", "input stream " + is);*/
            OutputStream os = bs.getOutputStream();
            Log.i("Main Activity", "output stream " + os);

            os.write(msg2send.getBytes());

        } catch (IOException e) {
            Log.i("Main Activity", "Went to shit.." + e);
        }
    }

    private void createBluetooth() {
        // Get bluetooth adapter ?
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
/*
        // What is our name ?
        String name = ba.getName();
        Log.i("Main Activity", "Our name is: " + name);

        int state = ba.getState();
        Log.i("Main Activity", "state ? " + state);

        // Is enabled ?
        boolean isEnabled = ba.isEnabled();
        Log.i("Main Activity", "We are enabled ? " + isEnabled);

        //Check bonded devices
        Set<BluetoothDevice> devices = ba.getBondedDevices();
        for (BluetoothDevice device : devices) {
            Log.i("Main Activity", "Device addr: " + device);
        }*/

        // Find bluetooth addresses


        // Set our address
        String macAddr = "00:1A:7D:DA:71:11";
        Log.i("Main Activity", "Connecting to addr: " + macAddr);


        // Validate address
        BluetoothDevice remote = ba.getRemoteDevice(macAddr);
        Log.i("Main Activity", "did we find remote ?" + remote);


        UUID uuid = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");
        Log.i("Main Activity", "Using uuid: " + uuid);
        try {
            bs = remote.createInsecureRfcommSocketToServiceRecord(uuid);
        }
        catch (Exception e) {
            Log.i("Main Activity", "Failed opening socket: " + e);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createBluetooth();

        Button forward = (Button) findViewById(R.id.button);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Main Activity", "Derp logging!");
                Snackbar.make(view, "derpderp", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                sendBtMessage("asdf");
            }
        });

        Button asdf = (Button) findViewById(R.id.button2);
        asdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    bs.close();
                }
                catch (Exception e) {
                    Log.i("Main Actvitiy", "Could not close!");
                }
            }
        });

        Button drive = (Button) findViewById(R.id.button3);
        drive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBtMessage("drive");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
