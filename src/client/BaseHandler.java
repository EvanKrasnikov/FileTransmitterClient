package client;

import controllers.LoginController;
import utils.*;

import java.util.concurrent.ConcurrentLinkedDeque;

public class BaseHandler extends Client  {
    public void parseMessage(ConcurrentLinkedDeque<String> request) {
        String s = request.pop();
        switch (s) {
            case Messages.NO_SUCH_USER:
                LoginController.showNotification(ClientMessages.NO_SUCH_USER);

            case Messages.CORRECT_PASS: {
                setIsAuthenticated(true);
                LoginController.showNotification(ClientMessages.CORRECT_PASS);
            }

            case Messages.INCORRECT_PASS:
                LoginController.showNotification(ClientMessages.INCORRECT_PASS);

            case Messages.LOGIN_IS_OCCUPIED:
                LoginController.showNotification(ClientMessages.LOGIN_IS_OCCUPIED);

            case Messages.TRANSITION_COMPLETE:
                LoginController.showNotification(ClientMessages.TRANSITION_COMPLETE);

            case Messages.GET_LIST :
                LoginController.showNotification(ClientMessages.GET_FILES);
        }
    }
}
