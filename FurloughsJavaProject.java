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
        System.out.print("Добре дошли в системата за отпуски! \n" +
                "Изберете една от следните опциии (1-5): \n" +
                "-------------------------------------- \n" +
                "1. Заявете отпуска \n" +
                "2. Вижте всички отпуски \n" +
                "3. Вижте отпуска за служител \n" +
                "4. Променете статус на отпуска \n" +
                "5. Изход \n" +
                "-------------------------------------- \n" +
                "Вашият избор: ");
    }

    public static void processUserChoice(String userChoice) {
        switch (userChoice) {
            case "1":
                stateAFurlough();
                break;
            case "2":
                seeAllFurloughs();
                break;
            case "3":
                seeEmployeeFurlough();
                break;
            case "4":
                changeFurloughStatus();
                break;
            case "5":
                exitFurloughSystem();
                break;
            default:
                do {
                    System.out.println("Моля, изберете валидна опция (1-5): ");
                    userChoice = scanner.nextLine();
                } while (!userChoice.equals("1") && !userChoice.equals("2") &&
                        !userChoice.equals("3") && !userChoice.equals("4") && !userChoice.equals("5"));
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
        System.out.print("Моля, въведете крайна дата на вашата отпуска в следния формат - ДД/ММ/ГГ - : ");
        String furloughEndDate = scanner.nextLine();
        // TODO
    }

    private static void setFurloughStartDate() {
        System.out.print("Моля, въведете начална дата на вашата отпуска: \n" +
                "Ден - ");
        String furloughStartDay = scanner.nextLine();
        System.out.print("Месец - ");
        String furloughStartMonth = scanner.nextLine();
        System.out.print("Година - ");
        String furloughStartYear = scanner.nextLine();

        while (!furloughStartDay.matches("[+-]?\\d*(\\.\\d+)?") ||
                !furloughStartMonth.matches("[+-]?\\d*(\\.\\d+)?") ||
                !furloughStartYear.matches("[+-]?\\d*(\\.\\d+)?")) {
            System.out.print("Моля, въведете валидна дата: ");
            insertDate(furloughStartDay, furloughStartMonth, furloughStartYear);
        }

        Integer.parseInt(furloughStartDay);
        Integer.parseInt(furloughStartMonth);
        Integer.parseInt(furloughStartYear);

//        checkInputDate(furloughStartDay, furloughStartMonth, furloughStartYear);
        //TODO
    }

    private static void checkInputDate(int day, int month, int year) {
//        if(day < 1 && day > 31 &&
//                month != 1 && month != 3 && month != 5 &&
//                month != 7 && month != 8 &&
//                month != 10 && month != 12) {
//            System.out.println("Моля, въведете валидна дата: ");
//        }
    }


    public static void insertDate(String day, String month, String year) {
        System.out.println("Ден - ");
        day = scanner.nextLine();
        System.out.print("Месец - ");
        month = scanner.nextLine();
        System.out.print("Година - ");
        year = scanner.nextLine();
    }

    public static void setUserID() {
        System.out.print("Вашето ЕГН: ");
        String inputID = scanner.nextLine();

        while (inputID.isBlank() || !inputID.matches("[+-]?\\d*(\\.\\d+)?")
                || inputID.length() != 10) {
            System.out.println("Моля, въведете валидно ЕГН: ");
            inputID = scanner.nextLine();
        }
    }

    public static void setUserEmail() {
        System.out.print("Вашият e-mail: ");
        String userEmail = scanner.nextLine();

        // luizasvetoslavova16@gmail.com
// TODO
        while (userEmail.isBlank()) {
            System.out.println("Моля, въведете валиден e-mail: ");
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
            System.out.println("Моля, въведете валидно име! \n" +
                    "Име: ");
            userFirstName = scanner.nextLine();
            System.out.println("Фамилия: ");
            userLastName = scanner.nextLine();
        }
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
//inputID.matches("[+-]?\\d*(\\.\\d+)?"