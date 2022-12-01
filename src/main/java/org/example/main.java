package org.example;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        operationLoop();

    }
    private static String consoleInput(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private static void operationLoop(){
        System.out.println("Enter String to Convert:");
        String input = consoleInput();
        while (!input.equals("exit")){

            StringToDate stringToDate = new StringToDate();
            try {  LocalDate date = stringToDate.getStringFromDate(input);
                System.out.println(date);
            }
            catch (DateTimeException dayOfWeekException){
                System.out.println("Error: Invalid string" + dayOfWeekException.getCause());
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }


            System.out.println("Enter String to Convert:");
            input = consoleInput();
        }
    }
}

