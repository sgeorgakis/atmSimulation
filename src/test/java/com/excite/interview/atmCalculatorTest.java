package com.excite.interview;

import com.excite.interview.domain.AtmCalculator;
import com.excite.interview.exception.AmountNotAvailableException;
import org.junit.Test;

import java.util.Map;

import static com.excite.interview.Constants.FIFTY_NOTE;
import static com.excite.interview.Constants.TWENTY_NOTE;
import static org.assertj.core.api.Assertions.assertThat;

public class atmCalculatorTest {

    private AtmCalculator atmCalculator;

    @Test
    public void requestBiggerThanAvailableAmount() {
        atmCalculator = new AtmCalculator(2, 1);
        int totalAmountBefore = atmCalculator.getTotalAmount();
        int twentyNotesBefore = atmCalculator.getNotesAmountMap().get(TWENTY_NOTE);
        int fiftyNotesBefore = atmCalculator.getNotesAmountMap().get(FIFTY_NOTE);
        try {
            int requestedAmount = 100;
            atmCalculator.calculateNotesReturned(requestedAmount);
        } catch (AmountNotAvailableException e) {
            assertThat(totalAmountBefore).isEqualTo(atmCalculator.getTotalAmount());
            assertThat(twentyNotesBefore).isEqualTo(atmCalculator.getNotesAmountMap().get(TWENTY_NOTE));
            assertThat(fiftyNotesBefore).isEqualTo(atmCalculator.getNotesAmountMap().get(FIFTY_NOTE));
        }
    }

    @Test
    public void requestAmountSmallerThanMinimum() {
        atmCalculator = new AtmCalculator(10, 10);
        int totalAmountBefore = atmCalculator.getTotalAmount();
        int twentyNotesBefore = atmCalculator.getNotesAmountMap().get(TWENTY_NOTE);
        int fiftyNotesBefore = atmCalculator.getNotesAmountMap().get(FIFTY_NOTE);
        try {
            int requestedAmount = 10;
            atmCalculator.calculateNotesReturned(requestedAmount);
        } catch (AmountNotAvailableException e) {
            assertThat(totalAmountBefore).isEqualTo(atmCalculator.getTotalAmount());
            assertThat(twentyNotesBefore).isEqualTo(atmCalculator.getNotesAmountMap().get(TWENTY_NOTE));
            assertThat(fiftyNotesBefore).isEqualTo(atmCalculator.getNotesAmountMap().get(FIFTY_NOTE));
        }
    }

    @Test
    public void requestInsufficientNotes() {
        atmCalculator = new AtmCalculator(10, 0);
        int totalAmountBefore = atmCalculator.getTotalAmount();
        int twentyNotesBefore = atmCalculator.getNotesAmountMap().get(TWENTY_NOTE);
        int fiftyNotesBefore = atmCalculator.getNotesAmountMap().get(FIFTY_NOTE);
        try {
            int requestedAmount = 70;
            atmCalculator.calculateNotesReturned(requestedAmount);
        } catch (AmountNotAvailableException e) {
            assertThat(totalAmountBefore).isEqualTo(atmCalculator.getTotalAmount());
            assertThat(twentyNotesBefore).isEqualTo(atmCalculator.getNotesAmountMap().get(TWENTY_NOTE));
            assertThat(fiftyNotesBefore).isEqualTo(atmCalculator.getNotesAmountMap().get(FIFTY_NOTE));
        }
    }

    @Test
    public void requestNotMultipleOfTen() {
        atmCalculator = new AtmCalculator(10, 10);
        int totalAmountBefore = atmCalculator.getTotalAmount();
        int twentyNotesBefore = atmCalculator.getNotesAmountMap().get(TWENTY_NOTE);
        int fiftyNotesBefore = atmCalculator.getNotesAmountMap().get(FIFTY_NOTE);
        try {
            int requestedAmount = 75;
            atmCalculator.calculateNotesReturned(requestedAmount);
        } catch (AmountNotAvailableException e) {
            assertThat(totalAmountBefore).isEqualTo(atmCalculator.getTotalAmount());
            assertThat(twentyNotesBefore).isEqualTo(atmCalculator.getNotesAmountMap().get(TWENTY_NOTE));
            assertThat(fiftyNotesBefore).isEqualTo(atmCalculator.getNotesAmountMap().get(FIFTY_NOTE));
        }
    }

    @Test
    public void requestValidAmount() throws AmountNotAvailableException {
        atmCalculator = new AtmCalculator(10, 10);
        int totalAmountBefore = atmCalculator.getTotalAmount();
        int twentyNotesBefore = atmCalculator.getNotesAmountMap().get(TWENTY_NOTE);
        int fiftyNotesBefore = atmCalculator.getNotesAmountMap().get(FIFTY_NOTE);

        int requestedAmount = 90;
        Map<Integer, Integer> returnedNotes = atmCalculator.calculateNotesReturned(requestedAmount);

        int totalAmountAfter = atmCalculator.getTotalAmount();
        Map<Integer, Integer> noteMapAfter = atmCalculator.getNotesAmountMap();
        assertThat(totalAmountBefore - totalAmountAfter).isEqualTo(requestedAmount);
        assertThat(twentyNotesBefore - noteMapAfter.get(TWENTY_NOTE)).isEqualTo(returnedNotes.get(TWENTY_NOTE));
        assertThat(fiftyNotesBefore - noteMapAfter.get(FIFTY_NOTE)).isEqualTo(returnedNotes.get(FIFTY_NOTE));
    }
}
