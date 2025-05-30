package udp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Server {

    private DatagramSocket datagramSocket;
    private String fileName;
    private InetAddress clientAddress;
    private int clientPort;
    private FileOutputStream fileOutputStream;

    public Server(int port) throws SocketException {
        datagramSocket = new DatagramSocket(port);
    }

    public void start() throws IOException {
        byte[] buffer = new byte[1024];
        boolean receivingFile = false;

        while (true) {
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(datagramPacket);

            String msg = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
            clientAddress = datagramPacket.getAddress();
            clientPort = datagramPacket.getPort();

            if (!receivingFile) {
                if (msg.startsWith("FILENAME:")) {
                    fileName = "received_" + msg;
                    fileOutputStream = new FileOutputStream(fileName);
                    sendText("OK");
                    System.out.println("OK");
                    receivingFile = true;
                }
            } else {
                if (msg.equals("Finish")) {
                    fileOutputStream.close();
                    sendText("WellDone");
                    break;
                } else {
                    fileOutputStream.write(datagramPacket.getData(), 0, datagramPacket.getLength());
                    sendText("");
                }
            }
        }
    }

    public void sendText(String msg) throws IOException {
        byte[] buffer = msg.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
        datagramSocket.send(datagramPacket);
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.print("PORT 번호를 입력하세요: ");
        int port = sc.nextInt();

        Server s = new Server(port);
        s.start();
    }
}
