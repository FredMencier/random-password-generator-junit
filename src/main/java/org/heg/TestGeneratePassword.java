package org.heg;

import org.heg.exception.BadPasswordException;
import org.heg.exception.InitializationException;

public class TestGeneratePassword {

    public static void main(String[] args) {
        try {
            RandomPassword randomPassword = new RandomPassword(6, 12);
            String pass = randomPassword.generatePassword(8);
            System.out.printf("My generated password %s%n", pass);

            String pass2 = randomPassword.generatePassword(10);
            System.out.printf("My generated password %s%n", pass2);

            randomPassword.generatePassword(50);
        } catch (InitializationException | BadPasswordException e) {
            System.out.println(e.getMessage());
        }

        try {
            RandomPassword randomPassword = new RandomPassword(6, 12);
            randomPassword.useNumbers(false);
            String pass = randomPassword.generatePassword(8);
            System.out.printf("My generated password %s%n", pass);

            randomPassword.useLetters(false);
            String pass2 = randomPassword.generatePassword(8);
            System.out.printf("My generated password %s%n", pass2);

            String base64EncodedPassword = randomPassword.generateBase64EncodedPassword(8);
            System.out.printf("My generated base64 password %s%n", base64EncodedPassword);

            randomPassword.useCapitalize(false);
            randomPassword.generatePassword(8);
        } catch (InitializationException | BadPasswordException e) {
            System.out.println(e.getMessage());
        }

        try {
            new RandomPassword(2, 100);
        } catch (InitializationException e) {
            System.out.println(e.getMessage());
        }
    }

}
