package com.example.thomasmars.testapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.UUID;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public BluetoothSocket bs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Switch connectionSwitch = (Switch) findViewById(R.id.switch1);
        connectionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    // Connect to bluetooth
                    Log.i("Navigation activity", "onCheckedChanged: TRUE");
                    createBluetooth();
                }
                else {
                    // Disconnect bluetooth
                    Log.i("Navigation activity", "onCheckedChanged: FALSE");
                }
            }
        });
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
            ((MainApplication) getApplicationContext()).setBluetoothSocket(bs);
            Log.i("Main Activity", "createBluetooth: Set bluetooth socket " + bs.toString());

        }
        catch (Exception e) {
            Log.i("Main Activity", "Failed opening socket: " + e);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Try to get the public member of application
            BluetoothSocket bs = ((MainApplication) getApplicationContext()).getBluetoothSocket();
            Log.i("Navigation activity", "onNavigationItemSelected: " + bs.toString());
        } else if (id == R.id.nav_slideshow) {
            // Create an intent and start it
            Intent manualActivity = new Intent(this, MainActivity.class);
            startActivity(manualActivity);
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
