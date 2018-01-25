package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Client  {
    private Selector selector;
    private SocketChannel channel;
    private static final String ADRESS = "localhost";
    private static final int PORT = 8189;

    private static final int MESSAGE_BUFFER = 64;
    protected Boolean isAuthentified = false;
    private ByteBuffer buffer = ByteBuffer.allocate(MESSAGE_BUFFER);

    public Client(){
        try {
            selector = Selector.open();
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(ADRESS,PORT));
            channel.register(selector, SelectionKey.OP_CONNECT, SelectionKey.OP_READ);
            if (channel.isConnected()){
                MessageHandler messageHandler = new MessageHandler();
                while (true){
                    messageHandler.parseMessage(receiveMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void setIsAuthentified(Boolean isAuthentified){
        this.isAuthentified = isAuthentified;
    }

    private String receiveMessage() throws IOException{
        channel.write(buffer);
        return buffer.toString();
    }

    protected void sendMessage(String message) throws IOException{
        byte[] bytes =  message.getBytes();
        buffer.get(bytes);
        channel.write(buffer);
        buffer.clear();
    }
}
