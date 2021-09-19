package mitin.frontend.consoleapp.communication.impl;

import mitin.frontend.consoleapp.communication.UserCommunication;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.Set;

public class UserCommunicationImpl implements UserCommunication {
    static Scanner in = new Scanner(System.in);
    static PrintStream out = System.out;

    private final Set<String> allCommands = Set.of("create menu", "description", "exit");
    @Override
    public Set<String> getCommands() {
        return allCommands;
    }

    @Override
    public void executeCommand(String command) {
        switch (command){
            case "create menu" : createMenu();     break;
            case "description" : getDescription(); break;
            case "exit"        : exit();           break;
            default: throw new RuntimeException("Incorrect command");
        }
    }

    @Override
    public String requestCommand() {
        return in.nextLine();
    }

    private void exit() {

    }

    private void getDescription() {
        out.println("Приложение составляет меню по вашем параметам");
    }

    private void createMenu() {
        out.println("Ваш пол");
        String sex = in.nextLine();
        out.println("Ваш вес");
        String weight = in.nextLine();
        out.println("Возраст");
        String age = in.nextLine();
        out.println("Рост");
        String height = in.nextLine();
        System.out.println(sex + " " + weight + " " + age + " " + height);
    }




}
