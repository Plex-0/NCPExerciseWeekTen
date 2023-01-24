package Game.Server;

import Game.PlayerState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.AbstractQueue;

public class ClientMessageReceiverThread extends Thread{
    private Socket socket;
    private AbstractQueue<Socket> connectedClients;
    private AbstractQueue<PlayerState> connectedPlayers;

    public ClientMessageReceiverThread(Socket socket, AbstractQueue<Socket> connectedClients, AbstractQueue<PlayerState> connectedPlayers) {
        this.socket = socket;
        this.connectedClients = connectedClients;
        this.connectedPlayers = connectedPlayers;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            while (!isInterrupted()) {
                PlayerState receivedState = (PlayerState) inputStream.readObject();
                connectedPlayers.add(receivedState);
                for (Socket s : connectedClients) {
                    ObjectOutputStream outputStream = new ObjectOutputStream(s.getOutputStream());
                    outputStream.writeObject(receivedState);
                    outputStream.flush();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
        }
    }
}