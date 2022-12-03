package com.panjohnny.advent;

import com.panjohnny.advent.util.Day;

import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- [ADVENT OF CODE] ---");
        System.out.print("Enter day number: ");
        int dayNumber = scanner.nextInt();
        try {
            Class<?> dayClass = Class.forName("com.panjohnny.advent.days.Day" + dayNumber);
            System.out.printf("--- [ DAY %d ] ---%n", dayNumber);
            Day day = (Day) dayClass.getConstructors()[0].newInstance();
            day.run();
            System.out.println();
            System.out.println("Do you want to continue? (y/yes)");
            String prompt = scanner.next();
            if (prompt.equalsIgnoreCase("y") || prompt.equalsIgnoreCase("yes")) {
                // space out
                for (int i = 0; i < 50; ++i) System.out.println();
                // clean the command line (if supported tho)
                System.out.print("\033[H\033[2J");
                System.out.flush();
                main(null);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Not found please try again");
            main(null);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InputMismatchException exception) {
            System.err.println("Not a valid day number, try again");
            main(null);
        }
    }
}