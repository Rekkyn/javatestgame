package rekkyn.javagame;

import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {

    static AppGameContainer appgc;

    public static final String GAMENAME = "Java Test Game";
    public static final int MENU = 0;
    public static final int OPTIONS = 1;
    public static final int PLAY = 2;
    public static int width = 640;
    public static int height = 360;
    
    public static Random rand = new Random();

    public Game(String name) {
        super(name);
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        addState(new Menu(MENU));
        addState(new Play(PLAY));
        addState(new Options(OPTIONS));
    }

    public static void main(String[] args) {
        try {
            appgc = new AppGameContainer(new Game(GAMENAME));
            appgc.setDisplayMode(width, height, false);
            appgc.setVSync(true);
            appgc.setShowFPS(false);
            appgc.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static Image scaleImage(Image image, int scale) {
        if (image != null) {
            image.setFilter(Image.FILTER_NEAREST);
            return image.getScaledCopy(scale);
        }
        return image;
    }

}
