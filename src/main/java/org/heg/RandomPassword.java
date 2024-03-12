package org.heg;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base16;
import org.apache.commons.codec.binary.Base64;
import org.heg.exception.BadPasswordException;
import org.heg.exception.InitializationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class RandomPassword implements IRandomPassword{

    private static final List<Integer> NUMBERS = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

    private static final List<String> LETTERS = Arrays.asList("a", "b", "c", "d", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");

    private final int minLength;

    private final int maxLength;

    private boolean useLetter;

    private boolean useNumber;

    private boolean useCapitalize;

    public RandomPassword(int minLength, int maxLength) throws InitializationException {
        if (minLength < MIN_LENGTH) {
            throw new InitializationException("min length must be > %s".formatted(MIN_LENGTH));
        }
        if (maxLength > MAX_LENGTH) {
            throw new InitializationException("max length must < %s".formatted(MAX_LENGTH));
        }
        this.minLength = minLength;
        this.maxLength = maxLength;
        try {
            initConfig();
        } catch (IOException e) {
            throw new InitializationException("Unable to read property file");
        }
    }

    private void initConfig() throws IOException {
        Properties prop = new Properties();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("pwdGenerator.properties");
        prop.load(resourceAsStream);
        useLetter = Boolean.parseBoolean(prop.getProperty("useLetter"));
        useNumber = Boolean.parseBoolean(prop.getProperty("useNumber"));
        useCapitalize = Boolean.parseBoolean(prop.getProperty("useCapitalize"));
    }

    public String generateBase64EncodedPassword(int length) throws BadPasswordException {
        return Base64.encodeBase64String(generatePassword(length).getBytes());
    }


    @Override
    public String generatePassword(int length) throws BadPasswordException {
        if (length < minLength || length > maxLength) {
            throw new BadPasswordException("Generator cannot create password for this length %s".formatted(length));
        }
        if (!useNumber && !useCapitalize && !useLetter) {
            throw new BadPasswordException("Generator cannot create password without numbers and lettres");
        }
        StringBuilder sb = new StringBuilder();
        do {
            if (useNumber) {
                int randomNb = getRandomNumber(0, NUMBERS.size() - 1);
                sb.append(NUMBERS.get(randomNb));
            }

            if (useLetter) {
                int randomNbLetter = getRandomNumber(0, LETTERS.size() - 1);
            sb.append(LETTERS.get(randomNbLetter));
            }

            if (useCapitalize) {
                int randomNbLetterCap = getRandomNumber(0, LETTERS.size() - 1);
                sb.append(LETTERS.get(randomNbLetterCap).toUpperCase());
            }
        } while (sb.length() < length);
        return sb.substring(0, length);
    }

    private int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    /**
     * Utilise des nombres dans le password
     * @param useNbs
     */
    public void useNumbers(boolean useNbs) {
        this.useNumber = useNbs;
    }

    /**
     * Utilise des lettres dans le password
     * @param useLetters
     */
    public void useLetters(boolean useLetters) {
        this.useLetter = useLetters;
    }

    /**
     * Utilise des majuscules dans le password
     * @param useCapitalize
     */
    public void useCapitalize(boolean useCapitalize) {
        this.useCapitalize = useCapitalize;
    }
}
