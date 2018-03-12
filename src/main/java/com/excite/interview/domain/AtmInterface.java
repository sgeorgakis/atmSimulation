package com.excite.interview.domain;

import com.excite.interview.exception.AmountNotAvailableException;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import static com.excite.interview.Constants.FIFTY_NOTE;
import static com.excite.interview.Constants.TWENTY_NOTE;

public class AtmInterface {

    private static final String QUIT_CHARACTER = "q";

    private AtmCalculator atmCalculator;

    private Scanner scanner;

    public AtmCalculator getAtmCalculator() {
        return atmCalculator;
    }

    public void setAtmCalculator(AtmCalculator atmCalculator) {
        this.atmCalculator = atmCalculator;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public AtmInterface(AtmCalculator atmCalculator) {
        new AtmInterface();
        this.atmCalculator = atmCalculator;
    }

    public AtmInterface() {
        this.scanner = new Scanner(System.in);
    }

    public void getRequestedAmount(int requestedAmount) {
        System.out.println(String.format("%s dollars requested. Processing...", requestedAmount));
        try {
            Map<Integer, Integer> returnedNotes = atmCalculator.calculateNotesReturned(requestedAmount);
            int twentyNotes = returnedNotes.get(TWENTY_NOTE);
            int fiftyNotes = returnedNotes.get(FIFTY_NOTE);
            if (twentyNotes > 0) {
                System.out.println(String.format("Returned %s twenty notes.", twentyNotes));
            }
            if (fiftyNotes > 0) {
                System.out.println(String.format("Returned %s fifty notes.", fiftyNotes));
            }
        } catch (AmountNotAvailableException e) {
            System.out.println("Error while processing.");
            System.out.println(e.getMessage());
        }
    }

    public boolean getMainMenu() {
        System.out.println("Insert the amount to withdraw, or 'q' to quit.");
        String input = scanner.next();
        if (QUIT_CHARACTER.equals(input)) {
            return false;
        }
        try {
            int requestedAmount = Integer.parseInt(input);
            getRequestedAmount(requestedAmount);
        } catch (NumberFormatException e) {
            System.out.println(String.format("Invalid input: %s", input));
        }
        return true;
    }

    public void init() {
        System.out.println("Initializing.");
        boolean result;
        do {
            result = getValidInitParameters();
        } while (!result);
    }

    private boolean getValidInitParameters() {
        try {
            System.out.println("Insert the amount of twenty dollar bills:");
            int twentyNotesAmount = scanner.nextInt();
            System.out.println("Insert the amount of fifty dollar bills:");
            int fiftyNotesAmount = scanner.nextInt();
            if (twentyNotesAmount < 0 || fiftyNotesAmount < 0) {
                System.out.println("Invalid input. Please insert valid integers");
                return false;
            }
            atmCalculator = new AtmCalculator(twentyNotesAmount, fiftyNotesAmount);
            return true;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please insert valid integers");
            return false;
        }
    }
}
