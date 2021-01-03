package goo.gl.PrettyUrl.utils;

import goo.gl.PrettyUrl.exception.APIRequestResponseException;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Helper {

    @Value("${default.timeout.in.minute}")
    private static int defaultTimeOut;

//    @Value("${default.pretty.url.length}")
//    private static int defaultUrlLength;

    public static String generateRandomString(int length) {

        String asciiUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String asciiLowerCase = asciiUpperCase.toLowerCase();
        String digits = "1234567890";
        String asciiChars = asciiUpperCase + asciiLowerCase + digits;

        StringBuilder sb = new StringBuilder();
        int i = 0;
        Random rand = new Random();
        while (i < length) {
            sb.append(asciiChars.charAt(rand.nextInt(asciiChars.length())));
            i++;
        }
        return sb.toString();
    }

    public static Timestamp addMinuteToDate(Timestamp date, int minuteNum) {
        return new Timestamp(date.getTime() + 60000 * minuteNum);
    }

    public static boolean isUrlValid(String url)
    {
        try {
            new URL(url).toURI();
            return true;
        }
        catch (Exception e) {
            throw new APIRequestResponseException("Url is invalid");
        }
    }
}
