package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Client  {
    private Selector selector;
    private SocketChannel channel;
    private static final String ADDRESS = "localhost";
    private static final int PORT = 8189;
    protected Boolean isAuthenticated = false;
    private ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
    private static int BUFFER_SIZE = 1024;

    public Client(){
        try {
            selector = Selector.open();
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(ADDRESS,PORT));
            channel.register(selector, channel.validOps());

            if (channel.isConnected()){
                while (true)
                    receiveMessage();
            }
        } catch (IOException e) {
            System.err.println("Client can't connect to the server");
        }
    }

    protected void sendMessage(byte[] bytes){
        try {
            buffer.get(bytes);
            channel.write(buffer);
            buffer.clear();
        } catch (IOException e) {
            System.err.println("Can't send message");
        }
    }

    private void receiveMessage(){ // Receive a message
        String result = "";
        StringBuilder stringBuilder = new StringBuilder();

        try {
            while (channel.read(buffer) > 0){
                buffer.flip();
                channel.read(buffer);
                stringBuilder.append(result);
                stringBuilder.append(new String(buffer.array()).trim());
                buffer.clear();
            }
        } catch (IOException e) {
            System.err.println("Can't send message");
        }

        String[] strings = result.split("\\s");
        ConcurrentLinkedDeque<String> queue = new ConcurrentLinkedDeque<>();

        for (String s: strings){
            queue.push(s);
        }

        BaseHandler handler = new BaseHandler();
        handler.parseMessage(queue);
    }

    protected void setIsAuthenticated(Boolean isAuthenticated){
        this.isAuthenticated = isAuthenticated;
    }

    protected SocketChannel getChannel() {
        return channel;
    }
}
