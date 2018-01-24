package client;

public class MessageHandler extends Client {
    public void parseMessage(String request) {
        switch (request) {
            case "/nosuchuser": {
                System.out.println("Enter a unique loginview, please/");
            }

            case "/correctpass": {
                setIsAuthentified(true);
                System.out.println("Login and pass are correct");
            }

            case "/incorrectpass": {
                System.out.println("Login or password is incorrect. Try again");
            }

            case "/loginisoccupied": {
                System.out.println("Login is occupied. Enter a unique login, please ");
            }

            case "/transitioncomplete": {
                System.out.println("File transition completed");
            }

            default: {
                System.out.println("There are no such command as " + request);
            }
        }
    }
}
