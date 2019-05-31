package com.devicedev.socialwave.ui.utils;

import java.util.regex.Pattern;

public class PasswordPatterns {
    public static final Integer MIN_CHARACTERS = 8;

    public static final Integer MAX_CHARACTERS = 20;

    //    At least 1 digit
    public static final Pattern DIGIT =
            Pattern.compile("[0-9]");
    //    At least 1 upper case character
    public static final Pattern UPPER_CASE =
            Pattern.compile("[A-Z]");
    //    At least 1 lower case character
    public static final Pattern LOWER_CASE =
            Pattern.compile("[a-z]");
    //    No white spaces
//    public static final Pattern WHITE_SPACES =
//            Pattern.compile("^\\S*$");
    //    At least MIN_CHARACTERS characters
    public static final Pattern MIN =
            Pattern.compile("^" +
                    ".{" + MIN_CHARACTERS + ",}" +
                    "$");
    //    Max MAX_CHARACTERS characters
    public static final Pattern MAX =
            Pattern.compile("^" +
                    ".{1," + MAX_CHARACTERS + "}" +
                    "$");
}
