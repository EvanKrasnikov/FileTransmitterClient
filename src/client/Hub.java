package client;

import client.sync.Sender;

import java.nio.channels.SocketChannel;

public class Hub {
    private SocketChannel channel;

    public Hub(SocketChannel channel) {
        this.channel = channel;
    }

    public void sendMessage(String message){
        Sender sender = new Sender(channel);
        sender.sendMessage(message);
    }
}
