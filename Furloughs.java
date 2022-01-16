package com.company;

import javax.swing.text.LabelView;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Scanner;

public class Furloughs {

    public static Scanner scanner = new Scanner(System.in);
    public static String[][] furloughsTable = new String[8][1000];

    public static void main(String[] args) throws ParseException {

        furloughsTable[0][0] = "Имe";
        furloughsTable[1][0] = "Имейл";
        furloughsTable[2][0] = "ЕГН";
        furloughsTable[3][0] = "Начало";
        furloughsTable[4][0] = "Край";
        furloughsTable[5][0] = "Тип";
        furloughsTable[6][0] = "Статус";
        furloughsTable[7][0] = "Уникален номер на заявката";

        setOptions();
        processUserChoice();
    }

    public static void setOptions() {
        System.out.print("""
                                
                Добре дошли в системата за отпуски!\s
                Изберете една от следните опциии (1-5):\s
                -------------------------------\s
                1. Заявете отпуска\s
                2. Вижте всички отпуски\s
                3. Вижте отпуска за служител\s
                4. Променете статус на отпуска\s
                5. Изход\s
                -------------------------------\s
                Вашият избор:\s""");
    }

    public static void processUserChoice() throws ParseException {
        String userChoice = scanner.nextLine();
        while (!isUserChoiceValid(userChoice)) {
            printIllegalInputMessage();
            userChoice = scanner.nextLine();
        }

        switch (userChoice) {
            case "1" -> stateAFurlough();
            case "2" -> printAllFurloughs();
            case "3" -> printSingleEmployeeFurloughs();
            case "4" -> changeFurloughStatus();
            case "5" -> exitSystem();
        }
    }

    public static boolean isUserChoiceValid(String userChoice) {
        return userChoice.equals("1") || userChoice.equals("2") ||
                userChoice.equals("3") || userChoice.equals("4") || userChoice.equals("5");
    }

    public static void printIllegalInputMessage() {
        System.out.println("Невалиден вход! Опитайте отново: ");
    }

    public static void stateAFurlough() throws ParseException {
        setUserName();
        setUserEmail();
        setUserID();
        setFurloughPeriod();
        setFurloughType();
        setDefaultStatus();
        setUniqueFurloughNumber();
        printSuccessfulSettingMessage();
        exitSystem();
    }

    public static void setUserName() {
        System.out.print("Име: ");
        String firstName = scanner.nextLine();
        System.out.print("Фамилия: ");
        String lastName = scanner.nextLine();

        while (!isUserNameValid(firstName, lastName)) {
            printIllegalInputMessage();
            System.out.print("Име: ");
            firstName = scanner.nextLine();
            System.out.print("Фамилия: ");
            lastName = scanner.nextLine();
        }

        fillTable(0, firstName.concat(" ").concat(lastName));
    }

    public static void fillTable(int position, String result) {
        int counter = 0;
        for (int index = 1; index < furloughsTable.length - 2; index++) {
            counter++;
            if (furloughsTable[position][index] == null) {
                break;
            }
        }
        furloughsTable[position][counter] = result;
    }

    public static boolean isUserNameValid(String firstName, String lastName) {
        return !(firstName.isBlank() || lastName.isBlank() ||
                Character.isLowerCase(firstName.charAt(0)) || Character.isLowerCase(lastName.charAt(0)) ||
                isNumeric(firstName) || isNumeric(lastName));
    }

    public static void setUserEmail() {
        System.out.print("Вашият e-mail: ");
        String userEmail = scanner.nextLine();

        while (!isEmailValid(userEmail)) {
            printIllegalInputMessage();
            userEmail = scanner.nextLine();
        }

        int n = 0;
        for (int i = 1; i < 6; i++) {
            n++;
            if (furloughsTable[0][i] == null) {
                break;
            }
        }

        fillTable(1, userEmail);
    }

    public static boolean isEmailValid(String email) {
        return email.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }

    public static void setUserID() {
        System.out.print("Вашето ЕГН: ");
        String userID = scanner.nextLine();

        while (!isNumeric(userID) || userID.length() != 10) {
            printIllegalInputMessage();
            userID = scanner.nextLine();
        }

        fillTable(2, userID);
    }

    public static boolean isNumeric(String input) {
        return input.matches("^[-+]?\\d*\\.?\\d+$");
    }

    public static void setFurloughPeriod() throws ParseException {
        System.out.println("Начало на вашата отпуска във формат ДД-ММ-ГГГГ: ");
        String start = scanner.nextLine();

        while (!isDateValid(start)) {
            printIllegalInputMessage();
            start = scanner.nextLine();
        }

        fillTable(3, start);

        System.out.println("Край на вашата отпуска във формат ДД-ММ-ГГГГ: ");
        String end = scanner.nextLine();

        while (!isDateValid(end)) {
            printIllegalInputMessage();
            end = scanner.nextLine();
        }

        fillTable(4, end);

        Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(start);
        Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(end);

        while (!areDatesInCorrectOrder(startDate, endDate) || !isDateValid(end)) {
            printIllegalInputMessage();
            end = scanner.nextLine();
            endDate = new SimpleDateFormat("dd-MM-yyyy").parse(end);
        }
    }

    public static boolean areDatesInCorrectOrder(Date startDate, Date endDate) {
        return startDate.compareTo(endDate) <= 0;
    }

    public static boolean isDateValid(String date) {
        return date.matches("^(((0[1-9]|[12][0-9]|30)[-]?(0[13-9]|1[012])|31[-]?(0[13578]|1[02])|(0[1-9]|1[0-9]|2[0-8])[-]?02)[-]?[0-9]{4}|29[-]?02[-]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$");
    }

    public static void setFurloughType() {
        System.out.print("Изберете тип отпуска (платена/неплатена): ");
        String furloughType = scanner.nextLine();

        while (!isFurloughTypeValid(furloughType)) {
            printIllegalInputMessage();
            furloughType = scanner.nextLine();
        }

        fillTable(5, furloughType);
    }

    public static void printSuccessfulSettingMessage() {
        System.out.println("""
                -------------------------------------------------\s
                Вашата отпуска беше успешно въведена в системата.\s
                -------------------------------------------------""");

    }

    public static boolean isFurloughTypeValid(String furloughType) {
        return furloughType.equalsIgnoreCase("платена") ||
                furloughType.equalsIgnoreCase("неплатена");
    }

    public static void setDefaultStatus() {
        for (int index = 1; index < furloughsTable[6].length; index++) {
            if (furloughsTable[0][index] == null) {
                break;
            }
            furloughsTable[6][index] = "pending";
        }
    }

    private static void setUniqueFurloughNumber() {
        Random furloughNumber = new Random();
        int random = furloughNumber.nextInt(10000000);
        String number = Integer.toString(random);
        fillTable(7, number);
        //todo
    }

    public static void printAllFurloughs() throws ParseException {
        System.out.println();
        boolean loopBreaks = true;

        for (int row = 0; row < furloughsTable.length - 1; row++) {
            for (int column = 0; column < furloughsTable.length - 1; column++) {
                if (furloughsTable[column][row] != null) {
                    System.out.print(furloughsTable[column][row] + "\t \t \t");
                    loopBreaks = false;
                } else {
                    loopBreaks = true;
                }
            }
            if (loopBreaks) {
                break;
            }
            System.out.println();
        }
        System.out.println();
        exitSystem();
    }

    private static void exitSystem() throws ParseException {
        setOptions();
        processUserChoice();
    }

    private static void printSingleEmployeeFurloughs() throws ParseException {
        System.out.print("Име на служител: ");
        String employeeName = scanner.nextLine();

        System.out.println();
        for (int heading = 0; heading < furloughsTable.length - 1; heading++) {
            System.out.print(furloughsTable[heading][0] + " \t \t \t");
        }

        for (int row = 0; row < furloughsTable.length - 1; row++) {
            if (furloughsTable[0][row] == null) {
                break;
            }
            if (employeeName.equalsIgnoreCase(furloughsTable[0][row])) {
                System.out.println();
                for (int column = 0; column < furloughsTable.length - 1; column++) {
                    if (furloughsTable[column][row] != null) {
                        System.out.print(furloughsTable[column][row] + "\t \t \t");
                    }
                }
            }
        }
        System.out.println();
        exitSystem();
    }

    public static void changeFurloughStatus() throws ParseException {
        printFurloughsTableIncludingNumber();

        System.out.print("Номер на заявката: ");
        String furloughNumber = scanner.nextLine();

//        for (int index = 1; index < furloughsTable[7].length; index++) {
//            if (furloughNumber.equals(furloughsTable[7][index])) {
//                break;
//            }
//        }

        System.out.print("""
                Изберете статус (1-2)\s
                1. Одобрена\s
                2. Отхвърлена\s
                Вашият избор:\s""");
        String status = scanner.nextLine();

        while (!status.equals("1") || status.equals("2")) {
            printIllegalInputMessage();
            status = scanner.nextLine();
        }

        if (status.equals("1")) {
            status = "одобрена";
        } else if (status.equals("2")) {
            status = "отхвърлена";
        }

        int counter = 0;
        for (int index = 1; index < furloughsTable[7].length; index++) {
            counter++;
            if (furloughNumber.equals(furloughsTable[7][index])) {
                break;
            }
        }
        System.out.println();
        furloughsTable[6][counter] = status;
        exitSystem();
    }

    private static void printFurloughsTableIncludingNumber() {
        System.out.println();
        boolean loopBreaks = true;

        for (int row = 0; row < furloughsTable.length; row++) {
            for (int column = 0; column < furloughsTable.length; column++) {
                if (furloughsTable[column][row] != null) {
                    System.out.print(furloughsTable[column][row] + "\t \t \t");
                    loopBreaks = false;
                } else {
                    loopBreaks = true;
                }
            }
            if (loopBreaks) {
                break;
            }
            System.out.println();
        }
    }

}






