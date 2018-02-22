package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Client  {
    private Selector selector;
    private SocketChannel client;
    private static final String ADDRESS = "localhost";
    private static final int PORT = 8189;
    protected Boolean isAuthenticated = false;
    protected Boolean terminateConnection = false;

    public Client(){
        try {
            selector = Selector.open();
            client = SocketChannel.open();
            client.configureBlocking(false);
            client.connect(new InetSocketAddress(ADDRESS,PORT));
            client.register(selector, client.validOps());

            if (client.isConnected()){
                Receiver receiver = new Receiver(client);
                while (!terminateConnection){
                    receiver.receiveMessage();
                }
            }
        } catch (IOException e) {
            System.err.println("Client can't connect to the server");
        }
    }

    public void sendMessage(String message){
        Sender sender = new Sender(client);
        sender.sendMessage(message);
    }

    protected void setIsAuthenticated(Boolean isAuthenticated){
        this.isAuthenticated = isAuthenticated;
    }

    public SocketChannel getChannel() {
        return client;
    }
}
