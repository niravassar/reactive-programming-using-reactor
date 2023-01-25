package io.javabrains.reactiveworkshop;

import java.io.IOException;

public class Exercise2 {

    public static void main(String[] args) throws IOException {

        // Use ReactiveSources.intNumbersFlux() and ReactiveSources.userFlux()

        // Print all numbers in the ReactiveSources.intNumbersFlux stream
        ReactiveSources.intNumbersFlux().subscribe( n -> {
            System.out.println("number is: " + n);
        });

        // Print all users in the ReactiveSources.userFlux stream
        ReactiveSources.userFlux().subscribe( user -> {
            System.out.println("User : " + user.toString());
        });

        System.out.println("Press a key to end");
        System.in.read();
    }

}
