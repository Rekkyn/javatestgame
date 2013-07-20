package rekkyn.javagame;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Options extends BasicGameState {
    
    public static String[] fullscreen = { "Off", "On" };
    public static String[] options = { "" + Game.width, "" + Game.height, fullscreen[0] };
    public static int prevState;
    
    public Options(int state) {}
    
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {}
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(new Color(0.82F, 0.85F, 0.88F));
        g.fillRect(0, 0, Game.width, Game.height);
        
        g.setColor(new Color(0.42F, 0.54F, 0.68F));
        Font.centerText("OPTIONS", Game.width / 2 + 3, 28, 9, g);
        
        g.setColor(new Color(0.18F, 0.25F, 0.33F));
        Font.centerText("OPTIONS", Game.width / 2, 25, 9, g);
        
        Font.draw("Screen Width:", Game.width / 2 - 200, 100, 3, g);
        Font.draw("Screen Height:", Game.width / 2 - 200, 150, 3, g);
        Font.draw("Fullscreen:", Game.width / 2 - 200, 200, 3, g);
        
        g.setColor(new Color(0.42F, 0.54F, 0.68F));
        for (int i = 0; i < options.length; i++) {
            String s = options[i];
            int offset = 0;
            if (i == selected) {
                s = ">" + s + "<";
                offset = 12;
            }
            Font.draw(s, Game.width / 2 - offset, 100 + i * 50, 3, g);
        }
        
        g.setColor(new Color(0.47F, 0.35F, 0.17F));
        g.fillRect(Game.width / 2 - 47, Game.height - 72, 100, 50);
        
        g.setColor(new Color(0.56F, 0.44F, 0.25F));
        g.fillRect(Game.width / 2 - 50, Game.height - 75, 100, 50);
        
        g.setColor(new Color(0.78F, 0.65F, 0.45F));
        Font.centerText("APPLY", Game.width / 2, Game.height - 58, 3, g);
        
    }
    
    public int selected = 0;
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(prevState);
        }
        
        if (input.isKeyPressed(Input.KEY_DOWN)) selected++;
        if (input.isKeyPressed(Input.KEY_UP)) selected--;
        if (selected > options.length - 1) selected = options.length - 1;
        if (selected < 0) selected = 0;
        if (selected < 2) {
            options[selected] = Font.editString(options[selected], container);
            input.isKeyPressed(Input.KEY_ENTER);
        } else if (selected == 2 && input.isKeyPressed(Input.KEY_ENTER)) {
            if (options[2].equals(fullscreen[0])) {
                options[2] = fullscreen[1];
            } else if (options[2].equals(fullscreen[1])) {
                options[2] = fullscreen[0];
            }
        }
        
        int xPos = Mouse.getX();
        int yPos = Mouse.getY();
        if (xPos > Game.width / 2 - 50 && xPos < Game.width / 2 + 50 && yPos < 75 && yPos > 25) {
            if (input.isMousePressed(0)) {
                boolean full = false;
                if (options[2].equals(fullscreen[1])) {
                    full = true;
                    options[0] = "" + container.getScreenWidth();
                    options[1] = "" + container.getScreenHeight();
                }
                int newWidth = Integer.parseInt(options[0]);
                int newHeight = Integer.parseInt(options[1]);
                if (newWidth < 400) {
                    newWidth = 400;
                    options[0] = "400";
                }
                if (newHeight < 300) {
                    newHeight = 300;
                    options[1] = "300";
                }
                Game.width = newWidth;
                Game.height = newHeight;
                Game.appgc.setDisplayMode(newWidth, newHeight, full);
                game.enterState(Game.MENU);
            }
        }
    }
    
    @Override
    public int getID() {
        return Game.OPTIONS;
    }
    
}
