package udp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {

    private DatagramSocket datagramSocket;
    private InetAddress serverAddress;
    private int port;

    public Client(String ip, int port) throws SocketException, UnknownHostException {
        datagramSocket = new DatagramSocket();
        serverAddress = InetAddress.getByName(ip);
        this.port = port;
    }

    public void sendText(String msg) throws IOException {
        byte[] data = msg.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, serverAddress, port);
        datagramSocket.send(datagramPacket);
    }

    public String receiveText() throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        datagramSocket.receive(datagramPacket);

        return new String(datagramPacket.getData(), 0, datagramPacket.getLength());
    }

    public void close() {
        datagramSocket.close();
    }

    public void sendFile(String fileName) throws IOException {
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("file is not exist");
            return;
        }

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int read;

        while ((read = fileInputStream.read(buffer)) != -1) {
            DatagramPacket datagramPacket = new DatagramPacket(buffer, read, serverAddress, port);
            datagramSocket.send(datagramPacket);
        }

        fileInputStream.close();
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.print("IP 주소를 입력하세요: ");
        String ipAddress = sc.nextLine();
        System.out.print("PORT 번호를 입력하세요: ");
        int port = sc.nextInt();
        sc.nextLine();

        Client c = new Client(ipAddress, port);

        c.sendText("Greeting");
        System.out.print("파일 이름을 입력하세요: ");
        String fileName = sc.nextLine();
        c.sendText("FILENAME:" + fileName);

        String response = c.receiveText();
        if (response.equals("OK")) {
            c.sendFile(fileName);
            c.sendText("Finish");

            String done = c.receiveText();
            if (done.equals("WellDone")) {
                c.close();
            }
        }

    }
}
