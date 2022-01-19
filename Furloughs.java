package com.company;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Scanner;

public class Furloughs {

    public static Scanner SCANNER = new Scanner(System.in);
    public static String[][] FURLOUGHS_DATA = new String[8][1000000];
    public static int[] UNIQUE_NUMBERS = new int[1000000];

    public static void main(String[] args) throws ParseException {
        setTableHeadings();
        printOptions();
        processUserChoice();
    }

    private static void setTableHeadings() {
        for (int rowIndex = 0; rowIndex <= 7; rowIndex++) {
            switch (rowIndex) {
                case 0 -> FURLOUGHS_DATA[rowIndex][0] = "|Име|";
                case 1 -> FURLOUGHS_DATA[rowIndex][0] = "|E-mail|";
                case 2 -> FURLOUGHS_DATA[rowIndex][0] = "|ЕГН|";
                case 3 -> FURLOUGHS_DATA[rowIndex][0] = "|Начало на отпуска|";
                case 4 -> FURLOUGHS_DATA[rowIndex][0] = "|Край на отпуска|";
                case 5 -> FURLOUGHS_DATA[rowIndex][0] = "|Тип|";
                case 6 -> FURLOUGHS_DATA[rowIndex][0] = "|Статус|";
                case 7 -> FURLOUGHS_DATA[rowIndex][0] = "|Номер на заявка|";
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

    public static void printNewLine() {
        System.out.println();
    }

    public static void processUserChoice() throws ParseException {
        String userChoice = SCANNER.nextLine();

        switch (setValidChoice(userChoice)) {
            case "1" -> stateAFurlough();
            case "2" -> printAllFurloughs();
            case "3" -> printSingleEmployeeFurloughs();
            case "4" -> changeFurloughStatus();
        }
    }

    public static String setValidChoice(String userChoice) {
        while (!isUserChoiceValid(userChoice)) {
            printIllegalInputMessage();
            userChoice = SCANNER.nextLine();
        }
        return userChoice;
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

    public static void setDefaultStatus() {
        for (int index = 1; index < FURLOUGHS_DATA[6].length; index++) {
            if (isCellEmpty(0, index)) {
                break;
            }
            FURLOUGHS_DATA[6][index] = "в изчакване";
        }
    }

    private static void setFurloughNumber() {
        Random random = new Random();
        int furloughNumber = random.nextInt(Integer.MAX_VALUE);

        String number = Integer.toString(furloughNumber);
        fillDataTable(7, number);
    }

    public static void setUserName() {
        System.out.print("Име: ");
        String firstName = SCANNER.nextLine();
        firstName = setValidName(firstName);

        System.out.print("Фамилия: ");
        String lastName = SCANNER.nextLine();

        fillDataTable(0, firstName.concat(" ").concat(setValidName(lastName)));
    }

    public static String setValidName(String name) {
        while (!isUserNameValid(name)) {
            printIllegalInputMessage();
            name = SCANNER.nextLine();
        }
        return name;
    }

    public static boolean isUserNameValid(String userName) {
        return !(userName.isBlank() || Character.isLowerCase(userName.charAt(0)) || isNumeric(userName));
    }

    public static void fillDataTable(int row, String data) {
        int rowIndex = 0;
        for (int index = 1; index < FURLOUGHS_DATA.length - 2; index++) {
            rowIndex++;
            if (FURLOUGHS_DATA[row][rowIndex] == null) {
                break;
            }
        }
        FURLOUGHS_DATA[row][rowIndex] = data;
    }

    public static void setUserEmail() {
        System.out.print("E-mail: ");
        String userEmail = SCANNER.nextLine();

        fillDataTable(1, setValidEmail(userEmail));
    }

    public static String setValidEmail(String userEmail) {
        while (!isEmailValid(userEmail)) {
            printIllegalInputMessage();
            userEmail = SCANNER.nextLine();
        }
        return userEmail;
    }

    public static boolean isEmailValid(String email) {
        return email.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }

    public static void setUserID() {
        System.out.print("ЕГН: ");
        String userID = SCANNER.nextLine();

        fillDataTable(2, setValidID(userID));
    }

    public static String setValidID(String userID) {
        while (!isIDValid(userID)) {
            printIllegalInputMessage();
            userID = SCANNER.nextLine();
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
        printDateSettingInstruction("Начало");

        String inputStart = SCANNER.nextLine();
        String start = setValidDate(inputStart);
        fillDataTable(3, start);

        printDateSettingInstruction("Край");
        String inputEnd = SCANNER.nextLine();
        String end = setValidDate(inputEnd);

        fillDataTable(4, checkDatesOrder(parseStringToDate(start), parseStringToDate(end), end));
    }

    public static void printDateSettingInstruction(String sequence) {
        System.out.println(sequence + " на отпуската във формат ДД-ММ-ГГГГ: ");
    }

    public static String setValidDate(String date) {
        while (!isDateValid(date)) {
            printIllegalInputMessage();
            date = SCANNER.nextLine();
        }
        return date;
    }

    public static Date parseStringToDate(String sequence) throws ParseException {
        Date date = new SimpleDateFormat("dd-MM-yyyy").parse(sequence);
        return date;
    }

    public static String checkDatesOrder(Date startDate, Date endDate, String end) throws ParseException {
        while (!areDatesInCorrectOrder(startDate, endDate) || !isDateValid(end)) {
            printIllegalInputMessage();
            end = SCANNER.nextLine();
            endDate = new SimpleDateFormat("dd-MM-yyyy").parse(end);
        }
        return end;
    }

    public static boolean areDatesInCorrectOrder(Date start, Date end) {
        return start.compareTo(end) <= 0;
    }

    public static boolean isDateValid(String date) {
        return dateMatchesRequiredFormat(date) && isYearCurrent(date);
    }

    public static boolean dateMatchesRequiredFormat(String date) {
        return date.matches("^(((0[1-9]|[12][0-9]|30)[-](0[13-9]|1[012])|31[-](0[13578]|1[02])|(0[1-9]|1[0-9]|2[0-8])[-]02)[-][0-9]{4}|29[-]02[-]([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$");
    }

    public static boolean isYearCurrent(String date) {
        return date.charAt(6) == '2' && date.charAt(7) == '0' && date.charAt(8) == '2' && date.charAt(9) == '2';
    }

    public static void setFurloughType() {
        printTypeSettingInstructions();
        String furloughType = SCANNER.nextLine();

        fillDataTable(5, setValidType(furloughType));
    }

    private static void printTypeSettingInstructions() {
        printNewLine();
        System.out.print("Изберете тип отпуска (1-2)\n" +
                "1. Платена\n" +
                "2. Неплатена\n" +
                "Избор: ");
    }

    public static String setValidType(String furloughType) {
        while (!isFurloughTypeValid(furloughType)) {
            printIllegalInputMessage();
            furloughType = SCANNER.nextLine();
        }
        return convertTypeNumber(furloughType);
    }

    public static String convertTypeNumber(String furloughType) {
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

    public static void printAllFurloughs() throws ParseException {
        printNewLine();
        boolean loopBreaks = true;

        for (int rowIndex = 0; rowIndex < FURLOUGHS_DATA.length - 1; rowIndex++) {

            for (int columnIndex = 0; columnIndex < FURLOUGHS_DATA.length - 1; columnIndex++) {

                if (!isCellEmpty(columnIndex, rowIndex)) {
                    printTableCell(columnIndex, rowIndex);
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

    public static boolean isCellEmpty(int firstIndex, int secondIndex) {
        return FURLOUGHS_DATA[firstIndex][secondIndex] == null;
    }

    public static void exitSystem() throws ParseException {
        printOptions();
        processUserChoice();
    }

    public static void printTableCell(int columnIndex, int rowIndex) {
        System.out.print(FURLOUGHS_DATA[columnIndex][rowIndex] + "\t \t \t \t");
    }

    public static void printSingleEmployeeFurloughs() throws ParseException {
        System.out.print("Име на служител: ");
        String employeeName = SCANNER.nextLine();

        printNewLine();
        printTableHeadings();

        for (int rowIndex = 0; rowIndex < FURLOUGHS_DATA.length - 1; rowIndex++) {

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
        for (int columnIndex = 0; columnIndex < FURLOUGHS_DATA.length - 1; columnIndex++) {

            if (!isCellEmpty(columnIndex, rowIndex)) {
                printTableCell(columnIndex, rowIndex);
            }
        }
    }

    public static boolean searchMatchesUserName(String search, int rowIndex) {
        return search.equalsIgnoreCase(FURLOUGHS_DATA[0][rowIndex]);
    }

    private static void printTableHeadings() {
        for (int heading = 0; heading < FURLOUGHS_DATA.length - 1; heading++) {
            printTableCell(heading, 0);
        }
    }

    public static void changeFurloughStatus() throws ParseException {
        printFurloughsTable();
        printNewLine();

        System.out.print("Номер на заявка: ");
        String furloughNumber = SCANNER.nextLine();
        checkNumberFromTable(furloughNumber);

        printStatusOptions();
        String statusInput = SCANNER.nextLine();
        String status = setValidStatus(statusInput);

        FURLOUGHS_DATA[6][findStatusIndex(furloughNumber)] = setStatusValue(status);
        printSuccessfulStatusChangeMessage(setStatusValue(status));
        exitSystem();
    }

    public static String setValidStatus(String status) {
        while (!isStatusValid(status)) {
            printIllegalInputMessage();
            status = SCANNER.nextLine();
        }
        return status;
    }

    public static void checkNumberFromTable(String furloughNumber) {
        printValidNumber(furloughNumber, findSearchedNumberIndex(furloughNumber));
    }

    private static int findSearchedNumberIndex(String furloughNumber) {
        int index = -1;
        for (int currentIndex = 0; currentIndex < FURLOUGHS_DATA[7].length - 1; currentIndex++) {
            index++;
            if (searchMatchesFurloughNumber(furloughNumber, currentIndex)) {
                break;
            }
        }
        return index;
    }

    public static void printValidNumber(String furloughNumber, int index) {

        while (!searchMatchesFurloughNumber(furloughNumber, index)) {
            printIllegalInputMessage();
            furloughNumber = SCANNER.nextLine();

            int counter = -1;
            for (int rowIndex = 0; rowIndex < FURLOUGHS_DATA[7].length - 1; rowIndex++) {
                counter++;

                if (searchMatchesFurloughNumber(furloughNumber, rowIndex)) {
                    break;
                }
            }
            index = counter;
        }
    }

    public static void printStatusOptions() {
        printNewLine();
        System.out.print("Изберете статус (1-2)\n" +
                "1. Одобрена\n" +
                "2. Отхвърлена\n" +
                "Избор: ");
    }

    public static String setStatusValue(String status) {
        if (status.equals("1")) {
            return "одобрена";
        } else if (status.equals("2")) {
            return "отхвърлена";
        }
        return "в изчакване";
    }

    public static int findStatusIndex(String furloughNumber) {
        int index = -1;
        for (int currentIndex = 0; currentIndex < FURLOUGHS_DATA[7].length; currentIndex++) {
            index++;
            if (furloughNumber.equals(FURLOUGHS_DATA[7][currentIndex])) {
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

    public static boolean searchMatchesFurloughNumber(String search, int index) {
        return search.equals(FURLOUGHS_DATA[7][index]);
    }

    public static void printFurloughsTable() {
        printNewLine();
        boolean loopBreaks = true;

        for (int rowIndex = 0; rowIndex < FURLOUGHS_DATA.length; rowIndex++) {

            for (int columnIndex = 0; columnIndex < FURLOUGHS_DATA.length; columnIndex++) {

                if (!isCellEmpty(columnIndex, rowIndex)) {
                    printTableCell(columnIndex, rowIndex);
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