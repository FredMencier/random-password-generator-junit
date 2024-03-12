package org.heg;

import org.heg.exception.BadPasswordException;

public interface IRandomPassword {

    int MIN_LENGTH = 2;
    int MAX_LENGTH = 50;

    /**
     * Méthode de génération de mot de passe
     * @param length
     * @return
     * @throws BadPasswordException
     */
    String generatePassword(int length) throws BadPasswordException;

    /**
     * Donne un password encodé en base64
     * @param length
     * @return
     * @throws BadPasswordException
     */
    String generateBase64EncodedPassword(int length) throws BadPasswordException;
}
