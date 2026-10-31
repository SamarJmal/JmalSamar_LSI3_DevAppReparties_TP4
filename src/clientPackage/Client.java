package clientPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    static final String IP_SERVEUR = "localhost";
    static final int PORT = 1234;

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress adresse = InetAddress.getByName(IP_SERVEUR);
            Scanner clavier = new Scanner(System.in);

            System.out.print("Donnez un nom d'utilisateur: ");
            String nom = clavier.nextLine().trim();

            while (true) {
                System.out.print("Message: ");
                String texte = clavier.nextLine();
                if (texte.equalsIgnoreCase("quit")) {
                    break;
                }

                String message = nom + ":" + texte;
                byte[] donnees = message.getBytes();

                DatagramPacket paquet = new DatagramPacket(donnees, donnees.length, adresse, PORT);
                socket.send(paquet);
            }

            clavier.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Erreur côté client: " + e.getMessage());
        }
    }
}