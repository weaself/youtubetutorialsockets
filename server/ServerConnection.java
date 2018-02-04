package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerConnection extends Thread {

    Socket socket;
    DataInputStream din;
    DataOutputStream dout;
    boolean shouldRun = true;

    public ServerConnection(Socket socket, Server server) {
        super("ServerConnectionThread");
        this.socket = socket;
    }

    public void sendStringToClient(String text) {

    }

    public void sendStringToAllClients(String text) {

    }

    public void run() {
        try {
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());

            while(shouldRun) {
                while (din.available() == 0) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                String textIn = din.readUTF();
                sendStringToAllClients(textIn);
            }
            din.close();
            dout.close();
            socket.close();

        } catch(IOException ioex) {
            ioex.printStackTrace();
        }
    }
}
