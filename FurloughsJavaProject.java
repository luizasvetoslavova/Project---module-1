package com.company;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class FurloughsJavaProject {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws ParseException {
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

    public static void processUserChoice(String userChoice) throws ParseException {
        while (!isUserChoiceValid(userChoice)) {
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
    }

    public static boolean isEmailValid(String email) {
        return email.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }

    public static void setUserID() {
        System.out.print("Вашето ЕГН: ");
        String inputID = scanner.nextLine();

        while (!isNumeric(inputID) || inputID.length() != 10) {
            printIllegalInputMessage();
            inputID = scanner.nextLine();
        }
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

        System.out.println("Край на вашата отпуска във формат ДД-ММ-ГГГГ: ");
        String end = scanner.nextLine();

        while (!isDateValid(end)) {
            printIllegalInputMessage();
            end = scanner.nextLine();
        }

        Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(start);
        Date endDate = new SimpleDateFormat("dd-MM-yyyy").parse(end);

        while (startDate.compareTo(endDate) > 0 || !isDateValid(end)) {
            printIllegalInputMessage();
            end = scanner.nextLine();
            endDate = new SimpleDateFormat("dd-MM-yyyy").parse(end);
        }
    }

    public static boolean isDateValid(String input) {
        return input.matches("^(((0[1-9]|[12][0-9]|30)[-]?(0[13-9]|1[012])|31[-]?(0[13578]|1[02])|(0[1-9]|1[0-9]|2[0-8])[-]?02)[-]?[0-9]{4}|29[-]?02[-]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$");
    }

    public static void setFurloughType() {
        System.out.print("""
                Изберете тип отпуска (1-2):\s
                1. Платена\s
                2. Неплатена\s
                Вашият избор:\s""");

        String furloughType = scanner.nextLine();

        while (!isFurloughTypeValid(furloughType)) {
            printIllegalInputMessage();
            furloughType = scanner.nextLine();
        }
    }

    public static boolean isFurloughTypeValid(String furloughType) {
        return furloughType.equals("1") || furloughType.equals("2");
    }

    public static void seeAllFurloughs() {
    }

    public static void seeEmployeeFurlough() {
    }

    public static void changeFurloughStatus() {
    }

    public static void exitFurloughSystem() {
    }

}