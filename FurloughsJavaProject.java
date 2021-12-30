package com.company;

import java.util.Scanner;

public class FurloughsJavaProject {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        setOptions();
        String userChoice = scanner.nextLine();
        processUserChoice(userChoice);
    }

    public static void setOptions() {
        System.out.print("""
                Добре дошли в системата за отпуски!\s
                Изберете една от следните опциии (1-5):\s
                --------------------------------------\s
                1. Заявете отпуска\s
                2. Вижте всички отпуски\s
                3. Вижте отпуска за служител\s
                4. Променете статус на отпуска\s
                5. Изход\s
                --------------------------------------\s
                Вашият избор:\s""");
    }

    public static void processUserChoice(String userChoice) {

        while (!userChoice.equals("1") && !userChoice.equals("2") &&
                !userChoice.equals("3") && !userChoice.equals("4") && !userChoice.equals("5")) {
            printIllegalInputMessage();
            userChoice = scanner.nextLine();
        }

        switch (userChoice) {
            case "1" -> stateAFurlough();
            case "2" -> seeAllFurloughs();
            case "3" -> seeEmployeeFurlough();
            case "4" -> changeFurloughStatus();
            case "5" -> exitFurloughSystem();
        }
    }

    public static void stateAFurlough() {
        setUserName();
        setUserEmail();
        setUserID();
        setFurloughPeriod();
        setFurloughType();
    }

    public static void setFurloughType() {
    }

    public static void setFurloughPeriod() {
        setFurloughStartDate();
        setFurloughEndDate();
    }

    private static void setFurloughEndDate() {

    }

    private static void setFurloughStartDate() {
        System.out.print("Моля, въведете начална дата на вашата отпуска! \n" +
                "Ден: ");
        String startDay = scanner.nextLine();
        System.out.print("Месец: ");
        String startMonth = scanner.nextLine();
        System.out.print("Година: ");
        String startYear = scanner.nextLine();

        checkIfInputIsNumerous(startDay, startMonth, startYear);
        checkDateValidity(startDay, startMonth, startYear);

        //TODO
    }

    private static void checkIfInputIsNumerous(String furloughStartDay, String furloughStartMonth, String furloughStartYear) {
        while (!furloughStartDay.matches("[+-]?\\d*(\\.\\d+)?") ||
                !furloughStartMonth.matches("[+-]?\\d*(\\.\\d+)?") ||
                !furloughStartYear.matches("[+-]?\\d*(\\.\\d+)?")) {
            printIllegalInputMessage();
            insertDate(furloughStartDay, furloughStartMonth, furloughStartYear);
        }
    }

    private static void checkDateValidity(String startDay, String startMonth, String startYear) {
        int day = Integer.parseInt(startDay);
        int month = Integer.parseInt(startMonth);
        int year = Integer.parseInt(startYear);

        checkForInputOutOfLimits(day, month);
        checkShortMonths(day, month);
        checkFebruary(day, month, year);
    }

    private static void checkForInputOutOfLimits(int day, int month) {
        if (day < 1 || day > 31 || month < 1 || month > 12) {
            printIllegalInputMessage();
        }
    }

    private static void checkFebruary(int day, int month, int year) {
        if (day == 28 && month == 2 && year % 4 == 0) {
            printIllegalInputMessage();

        } else if (day == 29 && month == 2 && year % 4 != 0) {
            printIllegalInputMessage();
        }
    }

    private static void checkShortMonths(int day, int month) {
        if (day == 31 && (month == 4 || month == 6 || month == 9 || month == 11 || month == 2)) {
            printIllegalInputMessage();
        }
    }

    public static void printIllegalInputMessage() {
        System.out.print("Невалиден вход! Опитайте отново: ");
    }

    public static void insertDate(String startDay, String startMonth, String startYear) {
        System.out.print("Ден: ");
        startDay = scanner.nextLine();
        System.out.print("Месец: ");
        startMonth = scanner.nextLine();
        System.out.print("Година: ");
        startYear = scanner.nextLine();
    }

    public static void setUserID() {
        System.out.print("Вашето ЕГН: ");
        String inputID = scanner.nextLine();

        while (inputID.isBlank() || !inputID.matches("[+-]?\\d*(\\.\\d+)?")
                || inputID.length() != 10) {
            printIllegalInputMessage();
            inputID = scanner.nextLine();
        }
    }

    public static void setUserEmail() {
        System.out.print("Вашият e-mail: ");
        String userEmail = scanner.nextLine();

        // luizasvetoslavova16@gmail.com
// TODO
        while (userEmail.isBlank()) {
            printIllegalInputMessage();
            userEmail = scanner.nextLine();
        }
    }

    public static void setUserName() {
        System.out.print("Име: ");
        String userFirstName = scanner.nextLine();
        System.out.print("Фамилия: ");
        String userLastName = scanner.nextLine();

        while (userFirstName.isBlank() || userLastName.isBlank() ||
                Character.isLowerCase(userFirstName.charAt(0)) || Character.isLowerCase(userLastName.charAt(0)) ||
                userFirstName.matches("[+-]?\\d*(\\.\\d+)?") || userLastName.matches("[+-]?\\d*(\\.\\d+)?")) {
            System.out.println("Невалиден вход! \n" +
                    "Име: ");
            userFirstName = scanner.nextLine();
            System.out.println("Фамилия: ");
            userLastName = scanner.nextLine();
        }
    }

    private static void exitFurloughSystem() {
    }

    private static void changeFurloughStatus() {
    }

    private static void seeEmployeeFurlough() {
    }

    private static void seeAllFurloughs() {
    }

}
//inputID.matches("[+-]?\\d*(\\.\\d+)?")














