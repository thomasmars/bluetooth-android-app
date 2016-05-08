package com.example.thomasmars.testapp;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private OutputStream os;
    private boolean connected = true;

    private void sendBtMessage(String msg2send) {
        if (!isConnected()) {
            return;
        }

        try {
            os.write(msg2send.getBytes());
        }
        catch (IOException e) {
            Toast.makeText(getApplicationContext(),
                    "Sending message failed: \"" + msg2send + "\"",
                    Toast.LENGTH_SHORT)
                    .show();
            Log.i("Navigation Activity", "sendBtMessage failed: " + msg2send);
        }

    }

    private boolean isConnected() {
        if (!connected) {
            Toast.makeText(getApplicationContext(), "You are not connected!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BluetoothSocket bs = ((MainApplication) getApplicationContext()).getBluetoothSocket();

        try {
            if (!bs.isConnected()) {
                connected = false;
                Toast.makeText(getApplicationContext(), "You are not connected!", Toast.LENGTH_SHORT).show();
            }
        }
        catch (NullPointerException e) {
            connected = false;
            Toast.makeText(getApplicationContext(), "You are not connected!", Toast.LENGTH_SHORT).show();
        }


        try {
            os = bs.getOutputStream();
        }
        catch (IOException e) {
            Log.i("Main Activity", "Could not get output stream: " + e);
            Toast.makeText(getApplicationContext(), "Could not get output stream", Toast.LENGTH_SHORT).show();
        }

        // Create buttons
        createBtButton(R.id.F, "forward");
        createBtButton(R.id.F_L, "forward_left");
        createBtButton(R.id.F_R, "forward_right");
        createBtButton(R.id.B, "backward");
        createBtButton(R.id.B_L, "backward_left");
        createBtButton(R.id.B_R, "backward_right");
    }

    private void createBtButton(int viewId, final String btMessage) {
        Log.i("Main activity", "createBtButton!!!");
        Button b = (Button) findViewById(viewId);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBtMessage(btMessage);
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
