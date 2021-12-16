package com.company;

import java.util.Scanner;

public class Furloughs {
//static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        setOptions();
        Scanner scanner = new Scanner(System.in);
        String userChoice = scanner.nextLine();
        processUserChoice(userChoice);
        stateAFurlough(userChoice);
    }

    public static void setOptions() {
        System.out.print("Добре дошли в системата за отпуски! \n" +
                "Изберете една от следните опциии (1-5): \n" +
                "1. Заявете отпуска \n" +
                "2. Вижте всички отпуски \n" +
                "3. Вижте отпуска за служител \n" +
                "4. Променете статус на отпуска \n" +
                "5. Изход \n" +
                "Вашият избор: ");
    }

    public static int processUserChoice(String userChoice) {
        Scanner scanner = new Scanner(System.in);
        while (!userChoice.equals("1") &&
                !userChoice.equals("2") &&
                !userChoice.equals("3") &&
                !userChoice.equals("4") &&
                !userChoice.equals("5")) {

            System.out.println("Моля, изберете валидна опция (1-5): ");
            userChoice = scanner.nextLine();
        }
        int choiceNumber = Integer.parseInt(userChoice);
        return choiceNumber;
    }

    public static void stateAFurlough(String userChoice) {
        if (userChoice.equals("1")) {
            Scanner scanner = new Scanner(System.in);
            processUserName(scanner);
            processUserEmail(scanner);
            processUserID(scanner);
            setFurloughPeriod(scanner);
            setFurloughType();
        }
    }

    public static void processUserName(Scanner scanner) {
        System.out.println("Име: ");
        String userFirstName = scanner.nextLine();
        System.out.println(" Фамилия: ");
        String userLastName = scanner.nextLine();

        while (userFirstName.isBlank() || userLastName.isBlank() ||
                !Character.isUpperCase(userFirstName.charAt(0)) ||
                !Character.isUpperCase(userLastName.charAt(0))) {
            System.out.println("Моля, въведете валидно име! \n" +
                    "Име: ");
            userFirstName = scanner.nextLine();
            System.out.println("Фамилия: ");
            userLastName = scanner.nextLine();
        }
    }

    public static void processUserEmail(Scanner scanner) {
        System.out.println("Вашият e-mail: ");
        String userEmail = scanner.nextLine();

        while (userEmail.isBlank()) {
            System.out.println("Моля, въведете валиден e-mail: ");
            userEmail = scanner.nextLine();
        }

    }

    public static void processUserID(Scanner scanner) {
        System.out.println("Вашето ЕГН: ");
        String inputID = scanner.nextLine();
        while (inputID.isBlank()
                || !inputID.matches("[+-]?\\d*(\\.\\d+)?")
                || inputID.length() != 10) {
            System.out.println("Моля, въведете валидно ЕГН: ");
            inputID = scanner.nextLine();
        }
        long userID = Long.parseLong(inputID);
    }

    public static void setFurloughPeriod(Scanner scanner) {

    }

    public static void setFurloughType() {

    }

}
//inputID.matches("[+-]?\\d*(\\.\\d+)?"












