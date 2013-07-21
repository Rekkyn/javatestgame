package rekkyn.javagame;

import org.newdawn.slick.Color;

public class Colour {
    
    public static final Color main = new Color(0.18F, 0.27F, 0.36F);
    public static final Color dark = new Color(0.18F, 0.25F, 0.33F);
    public static final Color darker = new Color(0.12F, 0.21F, 0.31F);
    public static final Color light = new Color(0.42F, 0.54F, 0.68F);
    public static final Color lighter = new Color(0.49F, 0.58F, 0.68F);
    public static final Color altmain = new Color(0.56F, 0.44F, 0.25F);
    public static final Color altdark = new Color(0.50F, 0.40F, 0.25F);
    public static final Color altdarker = new Color(0.47F, 0.35F, 0.17F);
    public static final Color altlight = new Color(0.78F, 0.65F, 0.45F);
    public static final Color altlighter = new Color(0.78F, 0.68F, 0.54F);
    
    public static final Color background = new Color(0.82F, 0.85F, 0.88F);
    
    public static Color changeHue(Color color, int amount) {
        //java.awt.Color newColor = new java.awt.Color(color.r, color.g, color.b);
        float[] hsv = new float[3];
        java.awt.Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv);
        hsv[0] += amount / 360F;
        return new Color(java.awt.Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]));
    }
}
