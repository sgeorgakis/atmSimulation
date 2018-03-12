package com.excite.interview.domain;

import com.excite.interview.exception.AmountNotAvailableException;

import java.util.HashMap;
import java.util.Map;

import static com.excite.interview.Constants.FIFTY_NOTE;
import static com.excite.interview.Constants.MINIMUM_NOTE;
import static com.excite.interview.Constants.TWENTY_NOTE;

public class AtmCalculator {

    private Map<Integer, Integer> notesAmountMap;

    private int totalAmount;

    public AtmCalculator(int twentyNotesAmount, int fiftyNotesAmount) {
        notesAmountMap = new HashMap<>();
        notesAmountMap.put(TWENTY_NOTE, twentyNotesAmount);
        notesAmountMap.put(FIFTY_NOTE, fiftyNotesAmount);
        calculateTotalAmount();
    }


    public Map<Integer, Integer> getNotesAmountMap() {
        return notesAmountMap;
    }

    public void setNotesAmountMap(Map<Integer, Integer> notesAmountMap) {
        this.notesAmountMap = notesAmountMap;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    private void calculateTotalAmount() {
        totalAmount = 0;
        notesAmountMap.forEach((k, v) -> totalAmount += k * v);
    }

    public Map<Integer, Integer> calculateNotesReturned(int requestedAmount) throws AmountNotAvailableException {
        if (!checkValidAmount(requestedAmount)) {
            throw new AmountNotAvailableException(String.format("The requested amount cannot be satisfied. Minimum amount %s. Maximum amount %s",
                    MINIMUM_NOTE, totalAmount));
        }

        int fiftyNotes = 0;
        int twentyNotes = 0;
        while (requestedAmount >= MINIMUM_NOTE) {
            int remainingFiftyNotes = notesAmountMap.get(FIFTY_NOTE) - fiftyNotes;
            int remainingTwentyNotes = notesAmountMap.get(TWENTY_NOTE) - twentyNotes;

            if (requestedAmount % FIFTY_NOTE == 0 && remainingFiftyNotes >= requestedAmount / FIFTY_NOTE) {
                // Check if the requested amount can be satisfied only by 50 dollar notes
                fiftyNotes += requestedAmount / FIFTY_NOTE;
                requestedAmount = 0;
            } else if (requestedAmount % TWENTY_NOTE == 0 && remainingTwentyNotes >= requestedAmount / TWENTY_NOTE) {
                // Check if the requested amount can be satisfied only by 20 dollar notes
                twentyNotes += requestedAmount / TWENTY_NOTE;
                requestedAmount = 0;
            } else if (remainingFiftyNotes > 0 && requestedAmount >= FIFTY_NOTE) {
                // Get a 50 dollar note
                fiftyNotes++;
                requestedAmount -= FIFTY_NOTE;
            } else if (remainingTwentyNotes > 0) {
                // If a 50 dollar note could not be retrieved,
                // get a 20 dollar note
                twentyNotes++;
                requestedAmount -= TWENTY_NOTE;
            } else {
                // If none of the above cannot be satisfied,
                // then the requested amount cannot be withdrawn
                break;
            }
        }
        if (requestedAmount > 0) {
            throw new AmountNotAvailableException("The requested amount cannot be satisfied. Insufficient notes.");
        }

        Map<Integer, Integer> notesReturned = new HashMap<>();
        notesReturned.put(TWENTY_NOTE, twentyNotes);
        notesReturned.put(FIFTY_NOTE, fiftyNotes);
        notesAmountMap.compute(FIFTY_NOTE, (k, v) -> v = v - notesReturned.get(FIFTY_NOTE));
        notesAmountMap.compute(TWENTY_NOTE, (k, v) -> v = v - notesReturned.get(TWENTY_NOTE));
        calculateTotalAmount();
        return notesReturned;
    }

    private boolean checkValidAmount(int requestedAmount) {
        return (requestedAmount >= MINIMUM_NOTE) // The requested amount is at least 20 dollars
                && ((requestedAmount % 10) == 0) // The requested amount is a multiple of 10
                && (((requestedAmount % TWENTY_NOTE) == 0) || (requestedAmount >= FIFTY_NOTE)) // The requested amount is not 30 dollars
                && (requestedAmount < totalAmount); // There are enough cash in the ATM
    }
}
