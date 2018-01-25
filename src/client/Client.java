package client;

import client.sync.Receiver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Client  {
    private Selector selector;
    private SocketChannel channel;
    private static final String ADRESS = "localhost";
    private static final int PORT = 8189;
    protected Boolean isAuthentified = false;
    protected Boolean terminateConnection = false;

    public Client(){
        try {
            selector = Selector.open();
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(ADRESS,PORT));
            channel.register(selector, SelectionKey.OP_CONNECT, SelectionKey.OP_READ);
            if (channel.isConnected()){
                new Hub(channel);
                Receiver receiver = new Receiver(channel);
                while (!terminateConnection){
                    receiver.receiveMessage();
                }
            }
        } catch (IOException e) {
            System.err.println("Client can't connect to the server");
        }
    }

    protected void setIsAuthentified(Boolean isAuthentified){
        this.isAuthentified = isAuthentified;
    }
}
