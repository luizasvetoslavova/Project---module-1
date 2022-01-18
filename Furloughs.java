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
                case 0 -> furloughsData[rowIndex][0] = "|Име|";
                case 1 -> furloughsData[rowIndex][0] = "|E-mail|";
                case 2 -> furloughsData[rowIndex][0] = "|ЕГН|";
                case 3 -> furloughsData[rowIndex][0] = "|Начало на отпуска|";
                case 4 -> furloughsData[rowIndex][0] = "|Край на отпуска|";
                case 5 -> furloughsData[rowIndex][0] = "|Тип|";
                case 6 -> furloughsData[rowIndex][0] = "|Статус|";
                case 7 -> furloughsData[rowIndex][0] = "|Номер на заявка|";
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

        switch (setCorrectChoice(userChoice)) {
            case "1" -> stateAFurlough();
            case "2" -> printAllFurloughs();
            case "3" -> printSingleEmployeeFurloughs();
            case "4" -> changeFurloughStatus();
            case "5" -> exitSystem();
        }
    }

    public static String setCorrectChoice(String userChoice) {
        while (!isUserChoiceValid(userChoice)) {
            printIllegalInputMessage();
            userChoice = scanner.nextLine();
        }
        return userChoice;
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

    public static void stateAFurlough() throws ParseException {
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

        System.out.print("Фамилия: ");
        String lastName = scanner.nextLine();

        fillDataTable(0, setCorrectName(firstName).concat(" ").concat(setCorrectName(lastName)));
    }

    public static String setCorrectName(String name) {
        while (!isUserNameValid(name)) {
            printIllegalInputMessage();
            name = scanner.nextLine();
        }
        return name;
    }

    public static boolean isUserNameValid(String userName) {
        return !(userName.isBlank() || Character.isLowerCase(userName.charAt(0)) || isNumeric(userName));
    }

    public static void fillDataTable(int row, String data) {
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

        fillDataTable(1, setCorrectEmail(userEmail));
    }

    public static String setCorrectEmail(String userEmail) {
        while (!isEmailValid(userEmail)) {
            printIllegalInputMessage();
            userEmail = scanner.nextLine();
        }
        return userEmail;
    }

    public static boolean isEmailValid(String email) {
        return email.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }

    public static void setUserID() {
        System.out.print("ЕГН: ");
        String userID = scanner.nextLine();

        fillDataTable(2, setCorrectID(userID));
    }

    public static String setCorrectID(String userID) {
        while (!isIDValid(userID)) {
            printIllegalInputMessage();
            userID = scanner.nextLine();
        }
        return userID;
    }

    public static boolean isIDValid(String userID) {
        return !(!isNumeric(userID) || userID.length() != 10);
    }

    public static boolean isNumeric(String input) {
        return input.matches("^[-+]?\\d*\\.?\\d+$");
    }

    public static void setFurloughPeriod() throws ParseException {
        System.out.println("Начало на вашата отпуска във формат ДД-ММ-ГГГГ: ");
        String start = scanner.nextLine();
        fillDataTable(3, setCorrectDate(start));

        System.out.println("Край на вашата отпуска във формат ДД-ММ-ГГГГ: ");
        String end = scanner.nextLine();
        fillDataTable(4, setDatesInCorrectOrder(parseStringToDate(start), parseStringToDate(end), end));
    }

    public static Date parseStringToDate(String sequence) throws ParseException {
        Date date = new SimpleDateFormat("dd-MM-yyyy").parse(setCorrectDate(sequence));
        return date;
    }

    public static String setCorrectDate(String date) {
        while (!isDateValid(date)) {
            printIllegalInputMessage();
            date = scanner.nextLine();
        }
        return date;
    }

    public static String setDatesInCorrectOrder(Date startDate, Date endDate, String end) throws ParseException {
        while (!areDatesInCorrectOrder(startDate, endDate) || !isDateValid(end)) {
            printIllegalInputMessage();
            end = scanner.nextLine();
            endDate = new SimpleDateFormat("dd-MM-yyyy").parse(end);
        }
        return end;
    }

    public static boolean areDatesInCorrectOrder(Date startDate, Date endDate) {
        return startDate.compareTo(endDate) <= 0;
    }

    public static boolean isDateValid(String date) {
        return date.matches("^(((0[1-9]|[12][0-9]|30)[-]?(0[13-9]|1[012])|31[-]?(0[13578]|1[02])|(0[1-9]|1[0-9]|2[0-8])[-]?02)[-]?[0-9]{4}|29[-]?02[-]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$");
    }

    public static void setFurloughType() {
        printTypeSettingInstructions();
        String furloughType = scanner.nextLine();

        fillDataTable(5, setCorrectType(furloughType));
    }

    private static void printTypeSettingInstructions() {
        System.out.print("Изберете тип отпуска (1-2)\n" +
                "1. Платена\n" +
                "2. Неплатена\n" +
                "Избор: ");
    }

    public static String setCorrectType(String furloughType) {
        while (!isFurloughTypeValid(furloughType)) {
            printIllegalInputMessage();
            furloughType = scanner.nextLine();
        }
        return turnTypeNumberToWord(furloughType);
    }

    public static String turnTypeNumberToWord(String furloughType) {
        if (furloughType.equals("1")) {
            return "платена";
        } else {
            return "неплатена";
        }
    }

    public static boolean isFurloughTypeValid(String type) {
        return type.equals("1") || type.equals("2");
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
        fillDataTable(7, number);
        //todo
    }

    public static void printAllFurloughs() throws ParseException {
        printNewLine();
        boolean loopBreaks = true;

        for (int rowIndex = 0; rowIndex < furloughsData.length - 1; rowIndex++) {
            for (int columnIndex = 0; columnIndex < furloughsData.length - 1; columnIndex++) {
                if (!isCellEmpty(columnIndex, rowIndex)) {
                    printCurrentCell(columnIndex, rowIndex);
                    loopBreaks = false;
                } else {
                    loopBreaks = true;
                }
            }
            if (loopBreaks) {
                break;
            }
            printNewLine();
        }
        exitSystem();
    }

    public static void printCurrentCell(int columnIndex, int rowIndex) {
        System.out.print(furloughsData[columnIndex][rowIndex] + "\t \t \t");
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
                printEmployeeDataForCurrentRow(rowIndex);
            }
        }
        printNewLine();
        exitSystem();
    }

    public static void printEmployeeDataForCurrentRow(int rowIndex) {
        for (int columnIndex = 0; columnIndex < furloughsData.length - 1; columnIndex++) {
            if (!isCellEmpty(columnIndex, rowIndex)) {
                System.out.print(furloughsData[columnIndex][rowIndex] + "\t \t \t \t");
            }
        }
    }

    public static boolean searchMatchesUserName(String search, int rowIndex) {
        return search.equalsIgnoreCase(furloughsData[0][rowIndex]);
    }

    private static void printTableHeadings() {
        for (int heading = 0; heading < furloughsData.length - 1; heading++) {
            System.out.print(furloughsData[heading][0] + " \t \t \t \t \t");
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

        printStatusOptions();
        String status = scanner.nextLine();

        while (!isStatusValid(status)) {
            printIllegalInputMessage();
            status = scanner.nextLine();
        }

        furloughsData[6][findStatusIndex(furloughNumber)] = status;
        printSuccessfulStatusChangeMessage(setStatusValue(status));
        exitSystem();
    }

    public static void checkNumberFromTable(String furloughNumber) {
        // take index of searched number
        int index = -1;
        for (int currentIndex = 0; currentIndex < furloughsData[7].length - 1; currentIndex++) {
            index++;
            if (searchMatchesFurloughNumber(furloughNumber, currentIndex)) {
                break;
            }
        }

        while (!searchMatchesFurloughNumber(furloughNumber, index)) {
            printIllegalInputMessage();
            furloughNumber = scanner.nextLine();
            int counter = -1;
            for (int rowIndex = 0; rowIndex < furloughsData[7].length - 1; rowIndex++) {
                counter++;
                if (searchMatchesFurloughNumber(furloughNumber, rowIndex)) {
                    break;
                }
            }
            index = counter;
        }
    }

    public static String setStatusValue(String status) {
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

    public static void printStatusOptions() {
        printNewLine();
        System.out.print("Изберете статус (1-2)\n" +
                "1. Одобрена\n" +
                "2. Отхвърлена\n" +
                "Избор: ");
    }

    public static boolean searchMatchesFurloughNumber(String search, int index) {
        return search.equals(furloughsData[7][index]);
    }

    public static void printFurloughsTable() {
        printNewLine();
        boolean loopBreaks = true;

        for (int rowIndex = 0; rowIndex < furloughsData.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < furloughsData.length; columnIndex++) {
                if (!isCellEmpty(columnIndex, rowIndex)) {
                    System.out.print(furloughsData[columnIndex][rowIndex] + "\t \t \t \t");
                    loopBreaks = false;
                } else {
                    loopBreaks = true;
                }
            }
            if (loopBreaks) {
                break;
            }
            printNewLine();
        }
    }

}






