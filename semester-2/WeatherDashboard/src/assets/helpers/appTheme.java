package assets.helpers;

import java.awt.*;
import java.io.InputStream;

public class appTheme {
    public static final int appHeight = 768;
    public static final int appWidth = 360;
    public static final String appTitle = "Dynamic Weather App";

    // Color palette based on your uploaded background (night sky + clouds)
    public static final Color HeaderColor = new Color(255, 255, 255); // pure white
    public static final Color BgBlack50 = new Color(0, 0, 0, 128); // semi-transparent black
    public static final Color BgWhite30 = new Color(255, 255, 255, 76); // search bar background
    public static final Color BgWhite80 = new Color(255, 255, 255, 204); // hover/active
    public static final Color gray = new Color(185, 195, 207); // light cloudy gray tone
    public static final Color softBlue = new Color(102, 125, 162); // used for focus/accents
    public static final Color softSky = new Color(66, 90, 131); // sky transition color
    public static final Color white = new Color(255, 255, 255);

    public static Font FontSizeBody;
    public static Font FontSizeHeader;

    static {
        Font fontMedium, fontRegular;
        try {
            InputStream fontStreamMedium = appTheme.class.getResourceAsStream("/assets/fonts/inter-medium.ttf");
            InputStream fontStreamRegular = appTheme.class.getResourceAsStream("/assets/fonts/inter-regular.ttf");

            assert fontStreamMedium != null : "inter-medium.ttf not found";
            assert fontStreamRegular != null : "inter-regular.ttf not found";

            fontMedium = Font.createFont(Font.TRUETYPE_FONT, fontStreamMedium);
            fontRegular = Font.createFont(Font.TRUETYPE_FONT, fontStreamRegular);

            FontSizeHeader = fontMedium.deriveFont(Font.BOLD, 24f);
            FontSizeBody = fontRegular.deriveFont(Font.PLAIN, 16f);
        } catch (Exception e) {
            FontSizeHeader = new Font("Arial", Font.BOLD, 24);
            FontSizeBody = new Font("Arial", Font.PLAIN, 16);
        }
    }
}
