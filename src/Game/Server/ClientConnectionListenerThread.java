package Game.Server;

import Game.PlayerState;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.AbstractQueue;

public class ClientConnectionListenerThread extends Thread{
    public boolean isOpen;
    private AbstractQueue<Socket> connectedClients;
    private AbstractQueue<PlayerState> connectedPlayers;
    private ServerSocket serverSocket;
    private boolean open = false;

    public boolean isOpen() {
        return open;
    }

    public ClientConnectionListenerThread(AbstractQueue<Socket> connectedClients, AbstractQueue<PlayerState> connectedPlayers) {
        this.connectedClients = connectedClients;
        this.connectedPlayers = connectedPlayers;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(50000, 10, InetAddress.getByName("127.0.0.1"));
            System.out.println("Game Server is open");
            open = true;

            while (!isInterrupted()) {
                Socket socket = serverSocket.accept();
                connectedClients.add(socket);
                new ClientMessageReceiverThread(socket, connectedClients, connectedPlayers).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}