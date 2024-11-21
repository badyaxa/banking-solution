package com.badyaxa.banking_solution.util;

import java.security.SecureRandom;

public class UtilityMethods {

    public static String getRandomCode(int length) {
        SecureRandom random = new SecureRandom();
        return random.ints('0', '9' + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
