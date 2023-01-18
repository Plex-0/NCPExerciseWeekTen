package Game.Client;

import Game.PlayerState;


public class GameClientThread extends Thread{
    private final String clientName;
    private final PlayerState playerState;

    public GameClientThread(String clientName, String playerName) {
        this.clientName = clientName;
        playerState = new PlayerState(playerName);
    }

    @Override
    public void run() {
        MessageSenderThread messageSender = new MessageSenderThread(clientName, playerState);
        MessageReceiverThread messageReceiver = new MessageReceiverThread(clientName);
        messageSender.start();
        messageReceiver.start();
        try {
            while (!isInterrupted()) {
                playerState.advanceGame();
                messageSender.updateMessage(playerState);
                Thread.sleep(1000);
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
