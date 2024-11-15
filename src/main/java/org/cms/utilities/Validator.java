package org.cms.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static Pattern pattern;
    private static Matcher matcher;

    public static boolean validateEmail(String email) {
        String emailPattern = "^(?:[a-zA-Z0-9\\S])+@([a-zA-Z0-9.-])+\\.[a-zA-Z0-9]{2,5}+$";

        pattern = Pattern.compile(emailPattern);
        matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        String phoneNumberPattern = "^09[0-9]{9}$";

        pattern = Pattern.compile(phoneNumberPattern);
        matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }

    public static boolean checkNumber(String number) {
        try {
            Long.parseLong(number);
        } catch(NumberFormatException numExc) {
            return false;
        }

        return true;
    }
}
