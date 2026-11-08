package ru.personal.finance;

import ru.personal.finance.app.App;

public class Main {
    public static void main(String[] args) {
        System.setErr(System.out);
        App app = new App();
        app.run();
    }
}