package edu.vogu.mitin.console;

import java.util.Scanner;

public class Console {
    private Menu menu;
    static Scanner in = new Scanner(System.in);

    public Console(Menu menu){
        this.menu = menu;
    }

    public void runConsoleApp(){
        System.out.println(menu.showMenu());
        System.out.println("Введите действие");
        String action = in.nextLine();
        if (checkAction(action)){
            
        }
    }

    private boolean checkAction(String action) {
        return true;
    }
}
