package client.sync;

import client.Client;

import java.util.concurrent.ConcurrentLinkedDeque;

public class MessageHandler extends Client implements Messages {
    public void parseMessage(ConcurrentLinkedDeque<String> request) {
        String s = request.pop();
        switch (s) {
            case NO_SUCH_USER: {
                System.out.println("Enter a unique loginview, please/");
            }

            case CORRECT_PASS: {
                setIsAuthentified(true);
                System.out.println("Login and pass are correct");
            }

            case INCORRECT_PASS: {
                System.out.println("Login or password is incorrect. Try again");
            }

            case LOGIN_IS_OCCUPIED: {
                System.out.println("Login is occupied. Enter a unique login, please ");
            }

            case TRANSITION_COMPLETE: {
                System.out.println("File transition completed");
            }

            case GET_LIST : {
                System.out.println("Files list received");
            }

            default: {
                System.out.println("There are no such command as " + request);
            }
        }
    }
}
