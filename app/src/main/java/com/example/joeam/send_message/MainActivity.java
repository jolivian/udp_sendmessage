package com.example.joeam.send_message;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    String message = null;
    Button send_message;
    int port = 11000;
    String ip = "192.168.43.94";
    DatagramSocket udpSocket = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send_message = (Button) (findViewById(R.id.output));

        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = "Hello";

                BackgroundTask b1 = new BackgroundTask();
                b1.execute(message);

            }
        });


    }

    //    public static void main(String[] args) {
//        try {
//            DatagramSocket s = new DatagramSocket(null);
//            InetSocketAddress address = new InetSocketAddress("192.168.103.255", 3000);
//            s.bind(address);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }




    public static void main(String message) throws IOException
    {
        InetAddress ip=InetAddress.getByName("192.168.1.102");
        DatagramSocket socket=new DatagramSocket();
        byte[] outData = message.getBytes();

        DatagramPacket out = new DatagramPacket(outData,outData.length,ip ,2060);
        socket.send(out);
        System.out.println("Send >>> ");


    }
    class BackgroundTask extends AsyncTask<String, Void, Void> {

        //PrintWriter writer;

        @Override
        protected Void doInBackground(String... voids) {

            try {
                message = voids[0];
                udpSocket = new DatagramSocket(port);
                InetAddress serverAddr = InetAddress.getByName(ip);
                byte[] buf = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, port);
                udpSocket.send(packet);
//                udpSocket = new DatagramSocket(null);
//                udpSocket.setReuseAddress(true);
//                udpSocket.setBroadcast(true);
//                udpSocket.bind(new InetSocketAddress(port));
                udpSocket.close();
//                if (udpSocket == null) {
//                    udpSocket = new DatagramSocket(null);
//                    udpSocket.setReuseAddress(true);
//                    udpSocket.setBroadcast(true);
//                    udpSocket.bind(new InetSocketAddress(port));
//                }
                System.out.println("Send >>> ");
            } catch (SocketException e) {
                Log.e("Udp:", "Socket Error:", e);
            } catch (IOException e) {
                Log.e("Udp Send:", "IO Error:", e);
            }
            return null;


        }
    }
}
