package clientPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    static final String IP_SERVEUR = "localhost";
    static final int PORT = 1234;

    static void envoyer(DatagramSocket socket, InetAddress adresse, String message) throws Exception {
        byte[] donnees = message.getBytes(StandardCharsets.UTF_8);
        socket.send(new DatagramPacket(donnees, donnees.length, adresse, PORT));
    }

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress adresse = InetAddress.getByName(IP_SERVEUR);
            Scanner clavier = new Scanner(System.in);

            System.out.print("Nom d'utilisateur : ");
            String nom = clavier.nextLine().trim();

            Thread reception = new Thread(() -> {
                byte[] buffer = new byte[1024];
                try {
                    while (true) {
                        DatagramPacket paquet = new DatagramPacket(buffer, buffer.length);
                        socket.receive(paquet);
                        System.out.println(new String(paquet.getData(), 0, paquet.getLength(),
                                StandardCharsets.UTF_8));
                    }
                } catch (Exception e) {

                }
            });
            reception.start();

            envoyer(socket, adresse, "[" + nom + "] a rejoint le chat");
            System.out.println("Tapez vos messages (quit pour quitter) :");

            while (true) {
                String texte = clavier.nextLine();
                if (texte.equalsIgnoreCase("quit")) {
                    break;
                }
                envoyer(socket, adresse, "[" + nom + "] : " + texte);
            }

            envoyer(socket, adresse, "[" + nom + "] a quitté le chat");
            socket.close();
            clavier.close();
        } catch (Exception e) {
            System.out.println("Erreur côté client: " + e.getMessage());
        }
    }
}