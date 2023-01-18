package Game.Server;

import Game.PlayerState;

import java.net.Socket;
import java.util.AbstractQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameServerThread extends Thread{
    private AbstractQueue<Socket> connectedClients = new ConcurrentLinkedQueue<>();
    private AbstractQueue<PlayerState> connectedPlayers = new ConcurrentLinkedQueue<>();
    private boolean open = false;
    public boolean isOpen(){
        return open;
    }
    @Override
    public void run() {
        ClientConnectionListenerThread listener = new ClientConnectionListenerThread(connectedClients, connectedPlayers);
        listener.start();

        /*
        while (!listener.isOpen) {
            open = false;
        }
        open = true;
        */

        while (!isInterrupted()) {
            //update loop
            for (PlayerState state : connectedPlayers) {
                System.out.println("Game Server State: \n\t\t" + state);
                connectedPlayers.remove(state);
            }
        }
        listener.interrupt();
        try {
            listener.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Server terminated");
    }
}