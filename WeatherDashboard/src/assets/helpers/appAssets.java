package assets.helpers;

import javax.swing.*;
import java.net.URL;

public class appAssets {

    public static final ImageIcon appIcon = new ImageIcon(getPNG("Icon"));
    public static final ImageIcon appBackground = new ImageIcon(getPNG("AppBackground"));
    public static final ImageIcon appSplashBackground = new ImageIcon(getPNG("SplashBackground"));
    public static final ImageIcon IconSearch = new ImageIcon(getPNG("search"));
    public static final ImageIcon IconSetting = new ImageIcon(getPNG("setting"));
    public static final ImageIcon IconSun = new ImageIcon(getPNG("sun"));
    public static final ImageIcon IconWind = new ImageIcon(getPNG("wind"));
    private static final String FontMedium;
    private static final String FontRegular;

    static {
        FontMedium = String.valueOf(getFONT("inter-medium"));
        FontRegular = String.valueOf(getFONT("inter-regular"));

    }

    private static URL get(String path, String filename, String format) {
        return appAssets.class.getResource(path + filename + format);
    }

    private static URL getPNG(String filename) {
        return get("/assets/images/", filename, ".png");
    }

    private static URL getFONT(String filename) {
        return get("/assets/fonts/", filename, ".ttf");
    }

    public static String getFontMedium() {
        return FontMedium;
    }

    public static String getFontRegular() {
        return FontRegular;
    }


}
