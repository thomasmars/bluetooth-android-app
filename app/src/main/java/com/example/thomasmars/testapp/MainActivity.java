package com.example.thomasmars.testapp;

import android.app.Application;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
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
    private BluetoothSocket bs;

    private void sendBtMessage(String msg2send) {

        Log.i("Main Activity", "Trying to send sendBtMessage: " + bs.toString());
        // Create socket
        try {
            if (!bs.isConnected()) {
                Log.i("Main Activity", "CONNECTED!");
                bs.connect();
            }
            else {
                Toast.makeText(getApplicationContext(), "You are not connected!", Toast.LENGTH_SHORT);
                return;
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
            Toast.makeText(getApplicationContext(), "Messaging error!", Toast.LENGTH_SHORT);
            Log.i("Main Activity", "Went to shit.." + e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bs = ((MainApplication) getApplicationContext()).getBluetoothSocket();

        // Create buttons
        createBtButton(R.id.F, "forward");
        createBtButton(R.id.F_L, "forward_left");
        createBtButton(R.id.F_R, "forward_right");
        createBtButton(R.id.B, "backward");
        createBtButton(R.id.B_L, "backward_left");
        createBtButton(R.id.B_R, "backward_right");
    }

    private void createBtButton(int viewId, final String btMessage) {
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
