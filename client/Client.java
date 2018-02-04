package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    Socket s;
    DataOutputStream dout;
    DataInputStream din;

    public static void main(String[] args) {
        new Client();
    }

    public Client() {
        try {
            Socket s = new Socket("172.16.0.40", 3333);
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            listenForInput();
        } catch (UnknownHostException uh) {
            uh.printStackTrace();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    public void listenForInput() {
        Scanner console = new Scanner(System.in);

        while (true) {
            while(!console.hasNextLine()) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException iex) {
                    iex.printStackTrace();
                }
            }

            String input = console.nextLine();

            if (input.toLowerCase().equals("quit")) {
                break;
            }

            try {
                dout.writeUTF(input);

                while(din.available() == 0) {
                    try {
                        Thread.sleep(1);
                    }catch (InterruptedException iex) {
                        iex.printStackTrace();
                    }

                    String reply = din.readUTF();
                    System.out.println(reply);
                }
            } catch (IOException ioex) {
                ioex.printStackTrace();
                break;
            }
        }
        try {
            s.close();
            din.close();
            dout.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        }

    }

