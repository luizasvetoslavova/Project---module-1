package com.company;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Scanner;

public class Furloughs {

    public static Scanner scanner = new Scanner(System.in);
    public static String[][] furloughsData = new String[8][1000];

    public static void main(String[] args) throws ParseException {
        setTableHeadings();
        printOptions();
        processUserChoice();
    }

    private static void setTableHeadings() {
        for (int rowIndex = 0; rowIndex <= 7; rowIndex++) {
            switch (rowIndex) {
                case 0 -> furloughsData[rowIndex][0] = "Име";
                case 1 -> furloughsData[rowIndex][0] = "E-mail";
                case 2 -> furloughsData[rowIndex][0] = "ЕГН";
                case 3 -> furloughsData[rowIndex][0] = "Начало на отпуска";
                case 4 -> furloughsData[rowIndex][0] = "Край на отпуска";
                case 5 -> furloughsData[rowIndex][0] = "Тип";
                case 6 -> furloughsData[rowIndex][0] = "Статус";
                case 7 -> furloughsData[rowIndex][0] = "Номер на заявка";
            }
        }
    }

    public static void printOptions() {
        printNewLine();
        printNewLine();
        System.out.print("Добре дошли в системата за отпуски!\n" +
                "Изберете една от следните опциии (1-5):\n" +
                "-------------------------------\n" +
                "1. Заявете отпуска\n" +
                "2. Вижте всички отпуски\n" +
                "3. Вижте отпуска за служител\n" +
                "4. Променете статус на отпуска\n" +
                "5. Изход\n" +
                "-------------------------------\n" +
                "Избор: ");
    }

    public static void processUserChoice() throws ParseException {
        String userChoice = scanner.nextLine();
        while (!isUserChoiceValid(userChoice)) {
            printIllegalInputMessage();
            userChoice = scanner.nextLine();
        }

        switch (userChoice) {
            case "1" -> setAFurlough();
            case "2" -> printAllFurloughs();
            case "3" -> printSingleEmployeeFurloughs();
            case "4" -> changeFurloughStatus();
            case "5" -> exitSystem();
        }
    }

    public static void exitSystem() throws ParseException {
        printOptions();
        processUserChoice();
    }

    public static boolean isUserChoiceValid(String userChoice) {
        return userChoice.equals("1") || userChoice.equals("2") ||
                userChoice.equals("3") || userChoice.equals("4") || userChoice.equals("5");
    }

    public static void printIllegalInputMessage() {
        System.out.print("Невалиден вход! Опитайте отново: ");
    }

    public static void setAFurlough() throws ParseException {
        setUserName();
        setUserEmail();
        setUserID();
        setFurloughPeriod();
        setFurloughType();
        setDefaultStatus();
        setFurloughNumber();
        printSuccessfulSettingMessage();
        exitSystem();
    }

    public static void setUserName() {
        System.out.print("Име: ");
        String firstName = scanner.nextLine();
        while (!isUserNameValid(firstName)) {
            printIllegalInputMessage();
            firstName = scanner.nextLine();
        }

        System.out.print("Фамилия: ");
        String lastName = scanner.nextLine();
        while (!isUserNameValid(lastName)) {
            printIllegalInputMessage();
            lastName = scanner.nextLine();
        }

        fillTable(0, firstName.concat(" ").concat(lastName));
    }

    public static boolean isUserNameValid(String userName) {
        return !(userName.isBlank() || Character.isLowerCase(userName.charAt(0)) || isNumeric(userName));
    }

    public static void fillTable(int row, String data) {
        int rowIndex = 0;
        for (int index = 1; index < furloughsData.length - 2; index++) {
            rowIndex++;
            if (furloughsData[row][rowIndex] == null) {
                break;
            }
        }
        furloughsData[row][rowIndex] = data;
    }

    public static void setUserEmail() {
        System.out.print("E-mail: ");
        String userEmail = scanner.nextLine();

        while (!isEmailValid(userEmail)) {
            printIllegalInputMessage();
            userEmail = scanner.nextLine();
        }

        fillTable(1, userEmail);
    }

    public static boolean isEmailValid(String email) {
        return email.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }

    public static void setUserID() {
        System.out.print("ЕГН: ");
        String userID = scanner.nextLine();

        while (!isUserIDValid(userID)) {
            printIllegalInputMessage();
            userID = scanner.nextLine();
        }

        fillTable(2, userID);
    }

    public static boolean isUserIDValid(String userID) {
        return !(!isNumeric(userID) || userID.length() != 10);
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

        Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(start);
        Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(end);

        while (!areDatesInCorrectOrder(startDate, endDate) || !isDateValid(end)) {
            printIllegalInputMessage();
            end = scanner.nextLine();
            endDate = new SimpleDateFormat("dd-MM-yyyy").parse(end);
        }
        fillTable(4, end);
    }

    public static boolean areDatesInCorrectOrder(Date startDate, Date endDate) {
        return startDate.compareTo(endDate) <= 0;
    }

    public static boolean isDateValid(String date) {
        return date.matches("^(((0[1-9]|[12][0-9]|30)[-]?(0[13-9]|1[012])|31[-]?(0[13578]|1[02])|(0[1-9]|1[0-9]|2[0-8])[-]?02)[-]?[0-9]{4}|29[-]?02[-]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$");
    }

    public static void setFurloughType() {
        System.out.print("""
                Изберете тип отпуска (1-2)
                1. Платена
                2. Неплатена
                Избор:""");

        String furloughType = scanner.nextLine();

        while (!isFurloughTypeValid(furloughType)) {
            printIllegalInputMessage();
            furloughType = scanner.nextLine();
        }

        if (furloughType.equals("1")) {
            furloughType = "платена";
        } else {
            furloughType = "неплатена";
        }

        fillTable(5, furloughType);
    }

    public static boolean isFurloughTypeValid(String furloughType) {
        return furloughType.equals("1") || furloughType.equals("2");
    }

    public static void printSuccessfulSettingMessage() {
        System.out.println("""
                ----------------------------------------------\s
                Вашата отпуска e успешно въведена в системата.\s
                ----------------------------------------------""");

    }

    public static void setDefaultStatus() {
        for (int index = 1; index < furloughsData[6].length; index++) {
            if (isCellEmpty(0, index)) {
                break;
            }
            furloughsData[6][index] = "в изчакване";
        }
    }

    public static boolean isCellEmpty(int firstIndex, int secondIndex) {
        return furloughsData[firstIndex][secondIndex] == null;
    }

    private static void setFurloughNumber() {
        Random random = new Random();
        int furloughNumber = random.nextInt(1000000000);
        String number = Integer.toString(furloughNumber);
        fillTable(7, number);
        //todo
    }

    public static void printAllFurloughs() throws ParseException {
        boolean loopBreaks = true;

        for (int rowIndex = 0; rowIndex < furloughsData.length - 1; rowIndex++) {
            for (int columnIndex = 0; columnIndex < furloughsData.length - 1; columnIndex++) {
                if (!isCellEmpty(columnIndex, rowIndex)) {
                    System.out.print(furloughsData[columnIndex][rowIndex] + "\t \t \t");
                    loopBreaks = false;
                } else {
                    loopBreaks = true;
                }
            }
            printNewLine();
            if (loopBreaks) {
                break;
            }
        }
        exitSystem();
    }

    public static void printSingleEmployeeFurloughs() throws ParseException {
        System.out.print("Име на служител: ");
        String employeeName = scanner.nextLine();

        printNewLine();
        printTableHeadings();

        for (int rowIndex = 0; rowIndex < furloughsData.length - 1; rowIndex++) {
            if (isCellEmpty(0, rowIndex)) {
                break;
            } else if (searchMatchesUserName(employeeName, rowIndex)) {
                printNewLine();
                for (int columnIndex = 0; columnIndex < furloughsData.length - 1; columnIndex++) {
                    if (!isCellEmpty(columnIndex, rowIndex)) {
                        System.out.print(furloughsData[columnIndex][rowIndex] + "\t \t \t");
                    }
                }
            }
        }

        printNewLine();
        exitSystem();
    }

    public static boolean searchMatchesUserName(String search, int rowIndex) {
        return search.equalsIgnoreCase(furloughsData[0][rowIndex]);
    }

    private static void printTableHeadings() {
        for (int heading = 0; heading < furloughsData.length - 1; heading++) {
            System.out.print(furloughsData[heading][0] + " \t \t \t");
        }
    }

    public static void printNewLine() {
        System.out.println();
    }

    public static void changeFurloughStatus() throws ParseException {
        printFurloughsTable();
        printNewLine();

        System.out.print("Номер на заявка: ");
        String furloughNumber = scanner.nextLine();
        checkNumberFromTable(furloughNumber);

        setStatusOptions();
        String status = scanner.nextLine();

        while (!isStatusValid(status)) {
            printIllegalInputMessage();
            status = scanner.nextLine();
        }

        furloughsData[6][findStatusIndex(furloughNumber)] = status;
        printSuccessfulStatusChangeMessage(switchStatusValue(status));
        exitSystem();
    }

    public static String switchStatusValue(String status) {
        if (status.equals("1")) {
            return "одобрена";
        } else {
            return "отхвърлена";
        }
    }

    public static int findStatusIndex(String furloughNumber) {
        int index = 0;
        for (int currentIndex = 1; currentIndex < furloughsData[7].length; currentIndex++) {
            index++;
            if (furloughNumber.equals(furloughsData[7][currentIndex])) {
                break;
            }
        }
        return index;
    }

    public static void printSuccessfulStatusChangeMessage(String status) {
        System.out.println("------------------------------------------------- \n" +
                ("Статусът на отпуската е променен на \"").concat(status).concat("\". \n") +
                "-------------------------------------------------");
    }

    public static boolean isStatusValid(String status) {
        return status.equals("1") || status.equals("2");
    }

    public static void setStatusOptions() {
        printNewLine();
        System.out.print("Изберете статус (1-2)\n" +
                "1. Одобрена\n" +
                "2. Отхвърлена\n" +
                "Вашият избор: ");
    }

    public static void checkNumberFromTable(String furloughNumber) {
        int index = -1;
        for (int currentIndex = 0; currentIndex < furloughsData[7].length; currentIndex++) {
            if (furloughNumber.equals(furloughsData[7][currentIndex])) {
                break;
            }
            index++;
        }

        while (!searchMatchesFurloughNumber(index)) {
            printIllegalInputMessage();
            furloughNumber = scanner.nextLine();
            counter = -1;
            for (int index = 0; index < furloughsData[7].length; index++) {
                if (furloughNumber.equals(furloughsData[7][index])) {
                    break;
                }
                counter++;
            }
        }
        //todo
    }

    public static boolean searchMatchesFurloughNumber(int index) {
        return index < furloughsData[7].length - 1;
    }

    public static void printFurloughsTable() {
        printNewLine();
        boolean loopBreaks = true;

        for (int rowIndex = 0; rowIndex < furloughsData.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < furloughsData.length; columnIndex++) {
                if (isCellEmpty(columnIndex, rowIndex)) {
                    System.out.print(furloughsData[columnIndex][rowIndex] + "\t \t \t");
                    loopBreaks = false;
                } else {
                    loopBreaks = true;
                }
            }
            printNewLine();
            if (loopBreaks) {
                break;
            }
        }
    }

}






