package rekkyn.javagame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Entity {

    public float x;
    public float y;
    public static int width;
    public static int height;
    public float motionX;
    public float motionY;
    public boolean removed;
    Input input;

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {}

    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        input = container.getInput();
        x += motionX;
        y += motionY;
        

        if (x < 0) {
            x = 0;
            motionX = 0;
        }
        if (y < 0) {
            y = 0;
            motionY = 0;
        }
        if (x > Game.width - width) {
            x = Game.width - width;
            motionX = 0;
        }
        if (y > Game.height - height) {
            y = Game.height - height;
            motionY = 0;
        }
        
        for (int i = 0; i < Menu.entities.size(); i++) {
            Entity e = Menu.entities.get(i);
            if (e != this && intersects(e)) {
                float xOverlap = 0;
                float yOverlap = 0;
                if (e.x + e.width > x && x + width > e.x + e.width) {
                    xOverlap = e.x + e.width - x;
                }
                if (x + width > e.x && x < e.x) {
                    xOverlap = e.x - (x + width);
                }
                
                if (e.y + e.height > y && y + height > e.y + e.height) {
                    yOverlap = e.y +e.height - y;
                }
                if (y + height > e.y && y < e.y) {
                    yOverlap = e.y - (y + height);
                }
                if (Math.abs(xOverlap) < Math.abs(yOverlap)) {
                    x += xOverlap;
                    motionX = -motionX * 0.8F;
                } else {
                    y += yOverlap;
                    motionY = -motionY * 0.8F;
                }
            }
        }
    }

    public void remove() {
        removed = true;
    }
    
    public boolean intersects(Entity e) {
        int w1 = width;
        int w2 = e.width;
        int h1 = height;
        int h2 = e.height;
        
        w1 += x;
        w2 += e.x;
        if (e.x > w1 || x > w2) return false;
        h2 += e.y;
        h1 += y;
        if (e.y > h1 || y > h2) return false;
        return true;

    }

}
