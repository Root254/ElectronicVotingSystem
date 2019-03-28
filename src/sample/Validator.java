package sample;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static Pattern pwdPattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{4,8})");

    public static Boolean validatePwd(String password) {
        Matcher matcher = pwdPattern.matcher(password);
        if (matcher.matches()) {
            return true;
        }
        else
            return false;
    }
}
