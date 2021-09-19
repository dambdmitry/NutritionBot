package mitin.frontend.consoleapp;

import mitin.frontend.consoleapp.communication.UserCommunication;
import mitin.frontend.consoleapp.communication.impl.UserCommunicationImpl;

import java.util.Scanner;

public class ConsoleApp {
    static Scanner in = new Scanner(System.in);

    private final String DESCRIPTION_COMMAND = "description";

    private final UserCommunication communication;

    public ConsoleApp() {
        this.communication = new UserCommunicationImpl();
    }

    public void runConsoleApp(){
        communication.executeCommand(DESCRIPTION_COMMAND);
        String command = communication.requestCommand();
        while (!"exit".equals(command)){
            communication.executeCommand(command);
            command = communication.requestCommand();
        }
    }
}
