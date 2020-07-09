package rest.mvc.example.rabbitmq;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleDataExtractor {

    private static final String emailPattern =
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)" + "*|\"" + "(?:[\\x01-\\x08\\x0b" +
                    "\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c" + "\\x0e" + "-\\x7f])" + "*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:" + "(?:25" + "[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" + "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9" + "-]*[a-z0-9]:" + "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09" + "\\x0b\\x0c\\x0e-\\x7f])" + "+)\\])";

    private static final String phoneNumberPattern =
            "(?:(?:\\+?([1-9]|[0-9][0-9]|[0-9][0-9][0-9])\\s*(?:[.-]\\s*)?)" + "?" + "(?:\\" + "(\\s*([2-9]1[02-9]|[2" +
                    "-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|" + "([0-9][1-9]|[0-9]1[02-9" + "]|[2-9" + "][02-8]1|[2-9" +
                    "][02-8][02-9]))\\s*(?:[.-]\\s*)?)?" + "([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2" + "})\\s*(?:[" + ".-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\" + ".?|extension)\\s*(\\d+))?";

    private static final String webLinkPattern = "(?i)\\b((?:[a-z][\\w-]+:(?:/{1,3}|[a-z0-9%])|www\\d{0,3}[" + ".]|[a" +
            "-z0-9" + ".\\-]+[" + ".][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(" +
            "([^\\s()<>]+|" + "(\\([^\\s()" + "<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))";

    public static List<String> extractEmails(final String target) {
        return extractData(target, emailPattern);
    }

    public static List<String> extractPhoneNumbers(final String target) {
        return extractData(target, phoneNumberPattern);
    }

    public static List<String> extractWebLinks(final String target) {
        return extractData(target, webLinkPattern);
    }

    private static List<String> extractData(final String target, final String targetPattern) {
        List<String> results = new ArrayList<>();
        Pattern pattern = Pattern.compile(targetPattern);
        Matcher matcher = pattern.matcher(target);

        while (matcher.find()) {
            results.add(matcher.group());
        }

        return results;
    }
}
