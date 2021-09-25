package mitin.frontend.consoleapp.communication.impl;

import mitin.frontend.consoleapp.communication.UserCommunication;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.Set;

public class UserCommunicationImpl implements UserCommunication {
    static Scanner in = new Scanner(System.in);
    static PrintStream out = System.out;

    private final Set<String> allCommands = Set.of("create diets", "description", "exit", "my diets", "choose diet", "my parameters", "sign up");
    @Override
    public Set<String> getCommands() {
        return allCommands;
    }

    @Override
    public void executeCommand(String command) {
        switch (command){
            case "create diets" : createDiets();    break;
            case "description"  : getDescription(); break;
            case "my diets"     : showDiets();      break;
            case "choose diet"  : chooseDiet();     break;
            case "sign up"      : signUp();         break;
            case "exit"         : exit();           break;
            default: throw new RuntimeException("Incorrect command");
        }
    }

    private void signUp() {
        out.print("Логин: ");
        String login = in.nextLine();
        out.print("Ваш пол (М/Ж): ");
        String sex = in.nextLine();
        out.print("Ваш вес");
        String weight = in.nextLine();
        out.print("Возраст");
        String age = in.nextLine();
        out.print("Рост");
        String height = in.nextLine();
        System.out.println(login + " " + sex + " " + weight + " " + age + " " + height);
    }

    private void chooseDiet() {

    }

    private void showDiets() {
        out.println("Ваши меню");
    }

    private void createDiets() {
        out.print("Какое меню вы хотите (Дефицит, Профицит, Поддержание формы): ");
        String menuType = in.nextLine();
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

    }




}
