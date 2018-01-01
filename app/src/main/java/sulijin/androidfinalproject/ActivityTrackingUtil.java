package sulijin.androidfinalproject;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Suli Jin on 12/31/2017.
 */

public class ActivityTrackingUtil {
    private static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault());
    public static SimpleDateFormat getDateFormatter() {
        return FORMATTER;
    }

    public static int getPosition(String type) {
        switch (type) {
            case "Running":
            case "撒鸭子":  return 0;
            case "Walking":
            case "走道": return 1;
            case "Biking":
            case "骑车子": return 2;
            case "Swimming":
            case "游泳": return 3;
            case "Skating":
            case "滑出溜": return 4;
        }
        return 0;
    }
}
