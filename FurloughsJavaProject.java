package com.company;
import jdk.internal.access.JavaNetHttpCookieAccess;
import java.time.LocalDate;
import java.util.Date;
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

    private static void setFurloughStartDate() {
        System.out.println("Моля, въведете начало на вашата отпуска във формат ДД-ММ-ГГ: ");
        String inputDate = scanner.nextLine();


//        while () {
//            printIllegalInputMessage();
//        }
    }

    private static void setFurloughEndDate() {
        System.out.println("Моля, въведете край на вашата отпуска във формат ДД-ММ-ГГ: ");

    }

    public static boolean isEndDateInLimit(int day, int month, int year) {
        LocalDate currentdate = LocalDate.now();
        return !(day < currentdate.getDayOfMonth() ||
                month < currentdate.getMonthValue() || year != currentdate.getYear());
    }

    private static boolean isStartDateInLimit(int day, int month, int year) {
        LocalDate currentdate = LocalDate.now();
        return !(day < currentdate.getDayOfMonth() ||
                month < currentdate.getMonthValue() || year != currentdate.getYear());
    }

    public static void printIllegalInputMessage() {
        System.out.print("Невалиден вход! Опитайте отново: ");
    }

    public static void setUserID() {
        System.out.print("Вашето ЕГН: ");
        String inputID = scanner.nextLine();

        while (!isNumerous(inputID) || inputID.length() != 10) {
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
        String firstName = scanner.nextLine();
        System.out.print("Фамилия: ");
        String lastName = scanner.nextLine();

        while (firstName.isBlank() || lastName.isBlank() ||
                Character.isLowerCase(firstName.charAt(0)) || Character.isLowerCase(lastName.charAt(0)) ||
                isNumerous(firstName) || isNumerous(lastName)) {
            printIllegalInputMessage();
            System.out.print("Име: ");
            firstName = scanner.nextLine();
            System.out.print("Фамилия: ");
            lastName = scanner.nextLine();
        }
    }

    public static boolean isNumerous(String input) {
        return input.matches("[+-]?\\d*(\\.\\d+)?");
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














