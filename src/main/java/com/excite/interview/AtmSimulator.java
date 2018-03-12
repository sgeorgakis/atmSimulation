package com.excite.interview;

import com.excite.interview.domain.AtmInterface;

public class AtmSimulator
{
    public static void main( String[] args ) {
        AtmInterface atmInterface = new AtmInterface();
        atmInterface.init();
        boolean repeat;
        do {
            repeat = atmInterface.getMainMenu();
        } while (repeat);
    }
}
