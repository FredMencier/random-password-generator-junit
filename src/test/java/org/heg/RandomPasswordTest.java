package org.heg;


import org.apache.commons.codec.binary.Base64;
import org.heg.exception.BadPasswordException;
import org.heg.exception.InitializationException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class RandomPasswordTest {

    private static RandomPassword randomPassword;

    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";


    @BeforeClass
    public static void init() throws InitializationException {
        randomPassword = new RandomPassword(IRandomPassword.MIN_LENGTH, IRandomPassword.MAX_LENGTH);
    }

    @AfterClass
    public static void tearDown() {
        randomPassword = null;
    }


    @Test(expected = InitializationException.class)
    public void testInitializationExceptionMinLength() throws InitializationException {
        new RandomPassword(IRandomPassword.MIN_LENGTH - 1, IRandomPassword.MAX_LENGTH);
    }

    @Test(expected = InitializationException.class)
    public void testInitializationExceptionMaxLength() throws InitializationException {
        new RandomPassword(IRandomPassword.MIN_LENGTH, IRandomPassword.MAX_LENGTH + 1);
    }

    @Test(expected = BadPasswordException.class)
    public void testBadPasswordExceptionLength() throws BadPasswordException {
        randomPassword.generatePassword(IRandomPassword.MAX_LENGTH + 1);
    }

    @Test
    public void generatePassword() throws BadPasswordException {
        String pass = randomPassword.generatePassword(8);
        Assert.assertEquals(8, pass.length());
    }

    @Test
    public void generatePasswordNotEmpty() throws BadPasswordException {
        String pass = randomPassword.generatePassword(8);
        Assert.assertNotNull(pass);
        Assert.assertFalse(pass.isEmpty());
    }

    @Test(expected = BadPasswordException.class)
    public void generatePasswordBadPwd() throws BadPasswordException {
        randomPassword.useLetters(false);
        randomPassword.useNumbers(false);
        randomPassword.useCapitalize(false);

        randomPassword.generatePassword(8);
    }


    @Test public void generatePasswordLettersNumbersCapital() throws BadPasswordException {
        randomPassword.useLetters(true);
        randomPassword.useNumbers(true);
        randomPassword.useCapitalize(true);

        String pass = randomPassword.generatePassword(8);

        Assert.assertTrue(containCharacterFromList(pass, NUMBERS));
        Assert.assertTrue(containCharacterFromList(pass, LETTERS.toUpperCase()));
        Assert.assertTrue(containCharacterFromList(pass, LETTERS));

    }

    @Test
    public void generatePasswordLetters() throws BadPasswordException {
        randomPassword.useLetters(true);
        randomPassword.useNumbers(false);
        randomPassword.useCapitalize(false);

        String pass = randomPassword.generatePassword(8);

        Assert.assertTrue(containCharacterFromList(pass, LETTERS));
    }

    @Test
    public void generatePasswordNumbers() throws BadPasswordException {
        randomPassword.useLetters(false);
        randomPassword.useNumbers(true);
        randomPassword.useCapitalize(false);

        String pass = randomPassword.generatePassword(8);

        Assert.assertTrue(containCharacterFromList(pass, NUMBERS));
    }

    @Test
    public void generatePasswordCapitalize() throws BadPasswordException {
        randomPassword.useLetters(false);
        randomPassword.useNumbers(false);
        randomPassword.useCapitalize(true);

        String pass = randomPassword.generatePassword(8);

        Assert.assertTrue(containCharacterFromList(pass, LETTERS.toUpperCase()));
    }

    @Test
    public void generatePasswordBase64() throws BadPasswordException {
        randomPassword.useLetters(true);
        randomPassword.useNumbers(true);
        randomPassword.useNumbers(true);

        String encodedPassword = randomPassword.generateBase64EncodedPassword(10);

        Assert.assertNotNull(encodedPassword);
        Assert.assertTrue(Base64.isBase64(encodedPassword));
    }


    /**
     * Utility method
     * @param pass
     * @param list
     * @return
     */
    private boolean containCharacterFromList(final String pass, final String list) {
        for (char c : pass.toCharArray()) {
            if (list.contains(String.valueOf(c))) {
                return true;
            }
        }
        return false;
    }
}