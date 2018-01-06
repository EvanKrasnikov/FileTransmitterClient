package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class LoginChecker {
    private Scanner scanner;
    private String url = "localhost:9029";
    private Socket socket;
    private Boolean isAuthentified = false;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String login;
    private String pass;

    void authDialog() {
        socket = new Socket();

        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        scanner = new Scanner(System.in);
        System.out.println("Enter login and password");

        while (!isAuthentified) {
            login = scanner.next();
            pass = scanner.next();
            //System.out.println("/login " + login + " " + pass);  //проверка ввода
            try {                                                     // ввод логина и пароля
                out.writeUTF("/login " + login + " " + pass);
            } catch (IOException e) {
                System.err.println("Problems with connection. Can not possible to send a message");
            }
            System.out.println("Waiting for a answer...");
            parseMessage();
        }
    }

    void parseMessage() {
        String request = "";

        try {
            request = in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (request) {
            case "/nosuchuser": {
                System.out.println("Enter a unique login, please/");
            }

            case "/correctpass": {
                isAuthentified = true;
                System.out.println("Login and pass are correct");
            }

            case "/incorrectpass": {
                System.out.println("Login or password is incorrect. Try again");
            }

            case "/loginisoccupied": {
                System.out.println("Login is occupied. Enter a unique login, please ");
            }

            default: {
                System.out.println("There are no such command as " + request);
            }
        }
    }
}
