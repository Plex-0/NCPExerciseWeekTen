package Game.Server;

import Game.PlayerState;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.AbstractQueue;
import java.util.LinkedList;
import java.util.List;

public class ClientConnectionListenerThread extends Thread{
    public boolean isOpen;
    private AbstractQueue<Socket> connectedClients;
    private AbstractQueue<PlayerState> connectedPlayers;
    private ServerSocket serverSocket;
    private boolean open = false;

    private List<Thread> messageReceiverList = new LinkedList<>();

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
                Thread thread = new ClientMessageReceiverThread(socket, connectedClients, connectedPlayers);
                messageReceiverList.add(thread);
                thread.start();
            }
            for (Thread thread : messageReceiverList) {
                thread.interrupt();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}