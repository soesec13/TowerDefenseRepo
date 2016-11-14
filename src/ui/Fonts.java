package ui;

import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

/**
 * Created by sebi on 014 14/11/2016.
 */
public class Fonts {
    private static TrueTypeFont headline, text, info;

    public static TrueTypeFont getTextFont() {
        if (text == null) {
            Font f = new Font("Times New Roman", Font.BOLD, 30);
            text = new TrueTypeFont(f, true);
        }
        return text;
    }

    public static TrueTypeFont getInfoFont() {
        if (info == null) {
            Font f = new Font("Times New Roman", Font.ITALIC, 22);
            info = new TrueTypeFont(f, true);
        }
        return info;
    }

    public static TrueTypeFont getHeadlineFont() {
        if (headline == null) {
            Font f = new Font("Arial", Font.BOLD, 40);
            headline = new TrueTypeFont(f, true);
        }
        return headline;
    }
}
