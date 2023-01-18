package Game.Client;

import Game.PlayerState;


public class GameClientThread extends Thread {
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
        while (!isInterrupted()) {
            try {
                playerState.advanceGame();
                messageSender.updateMessage(playerState);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //e.printStackTrace();
                interrupt();
            }
        }
        messageSender.interrupt();
        messageReceiver.interrupt();
        try {
            messageSender.join();
            messageReceiver.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(clientName + " terminated");
    }
}
