package Game;

import Game.Client.GameClientThread;
import Game.Server.GameServerThread;

public class Main {
    public static void main(String[] args) {
        GameServerThread serverThread = new GameServerThread();
        serverThread.start();

        /*
        while (!serverThread.isOpen()) {
            System.out.println("Server is not open");
        }
        */
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GameClientThread clientThread1 = new GameClientThread("Client 1", "Phyllis");
        GameClientThread clientThread2 = new GameClientThread("Client 2", "Ophelia");

        clientThread1.start();
        clientThread2.start();

        try {
            Thread.sleep(10000);

            clientThread1.interrupt();
            clientThread2.interrupt();
            clientThread1.join();
            clientThread2.join();
            
            Thread.sleep(1000);
            serverThread.interrupt();
            serverThread.join();

            System.out.println("Game terminated");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}