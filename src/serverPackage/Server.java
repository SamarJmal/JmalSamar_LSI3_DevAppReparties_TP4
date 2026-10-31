package serverPackage;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Server {
    static final int PORT = 1234;


    public static void main(String[] args) {
        int numero = 0;
        try {
            DatagramSocket socket = new DatagramSocket(new InetSocketAddress(PORT));
            System.out.println("Serveur UDP en attente de messages sur le port " + PORT);

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket paquet = new DatagramPacket(buffer, buffer.length);
                socket.receive(paquet);

                String message = new String(paquet.getData(), 0, paquet.getLength(), StandardCharsets.UTF_8);
                System.out.println("De " + paquet.getAddress().getHostAddress() + ":" + paquet.getPort() + " -> " + message);
            }
        } catch (Exception e) {
            System.out.println("Erreur côté serveur: " + e.getMessage());
        }
    }
}
