package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket ss;
    Socket s;
    DataInputStream din;
    DataOutputStream dout;

    public static void main(String[] args) {
        new Server();
    }
    public Server() {
        try {
            ss = new ServerSocket(3333);
            s = ss.accept();
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            listenForData();
        } catch(IOException ioex) {
            ioex.printStackTrace();
        }
    }

    public void listenForData() {
        while(true) {
            try {
                while (din.available() == 0) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                String dataIn = din.readUTF();

                dout.writeUTF(dataIn);
            } catch (IOException ioex) {
                ioex.printStackTrace();
                break;
            }
        }
        try {
            din.close();
            dout.close();
            s.close();
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
