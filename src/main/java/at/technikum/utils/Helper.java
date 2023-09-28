package at.technikum.utils;

public class Helper {

    public static boolean stringIsInteger(String str) {
        if (str == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String removeSlash(String input) {
        if (input != null && input.startsWith("/")) {
            return input.substring(1); // Remove the first character (which is '/')
        }
        return input; // Return the input string as is if it doesn't start with '/'
    }
}
