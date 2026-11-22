package serverPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Server {
    static final int PORT = 1234;

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(new InetSocketAddress(PORT));
            ArrayList<SocketAddress> clients = new ArrayList<>();
            byte[] buffer = new byte[1024];

            System.out.println("Serveur de chat UDP en attente sur le port " + PORT);

            while (true) {
                DatagramPacket paquet = new DatagramPacket(buffer, buffer.length);
                socket.receive(paquet);

                SocketAddress expediteur = paquet.getSocketAddress();
                String message = new String(paquet.getData(), 0, paquet.getLength(),
                        StandardCharsets.UTF_8);

                if (!clients.contains(expediteur)) {
                    clients.add(expediteur);
                    System.out.println("Nouveau client enregistré : " + expediteur
                            + " (" + clients.size() + " client(s))");
                }

                System.out.println("De " + expediteur + " -> " + message);

                byte[] donnees = message.getBytes(StandardCharsets.UTF_8);
                for (SocketAddress client : clients) {
                    if (!client.equals(expediteur)) {
                        socket.send(new DatagramPacket(donnees, donnees.length, client));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur côté serveur: " + e.getMessage());
        }
    }
}