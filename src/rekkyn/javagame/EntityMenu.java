package rekkyn.javagame;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class EntityMenu {
    
    public boolean isOpen;
    public List<MenuOption> options;
    public List output = new ArrayList();
    float x1, y1, x2, y2;
    
    private Entity e;
    
    public EntityMenu(Entity e) {
        this.e = e;
    }
    
    public void update(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        if (!isOpen) return;
        final int SCALE = 3;
        g.pushTransform();
        g.translate(e.x + e.width / 2, e.y - 15);
        Image image = Game.scaleImage(Menu.menu, SCALE);
        
        int maxLeft = 27;
        int maxRight = 27;
        int maxHeight = -42;
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).getLeftWidth() + 9 > maxLeft) maxLeft = options.get(i).getLeftWidth() + 9;
            if (options.get(i).getRigthWidth() + 9 > maxRight) maxRight = options.get(i).getRigthWidth() + 9;
        }
        if (-20 - (options.size() - 1) * 20 - 10 < maxHeight) maxHeight = -20 - (options.size() - 1) * 20 - 10;
        
        while (maxLeft % SCALE != 0) {
            maxLeft++;
        }
        while (maxRight % SCALE != 0) {
            maxRight++;
        }
        while (maxHeight % SCALE != 0) {
            maxHeight--;
        }
        
        g.drawImage(image,                  //
                -maxLeft + 7 * SCALE,       //
                -7 * SCALE,                 //
                maxRight - 7 * SCALE,       //
                SCALE,                      //
                7 * SCALE, 11 * SCALE, 8 * SCALE, 19 * SCALE); // bottom
        
        g.setColor(Colour.altbackground);
        g.fillRect(-maxLeft + 7 * SCALE, maxHeight, maxRight - 8 * SCALE + SCALE - (-maxLeft + 7 * SCALE), 7 * SCALE); // top
        
        g.setColor(Colour.altbackground);
        g.fillRect(-maxLeft, maxHeight + 7 * SCALE, maxLeft + maxRight, -maxHeight - 14 * SCALE);// middle
        
        g.setColor(Colour.altdarker);
        g.fillRect(maxRight, maxHeight + 7 * SCALE, SCALE, -maxHeight - 14 * SCALE);// shadow
        
        g.drawImage(image,                      //
                -maxLeft,                       //
                -7 * SCALE,                     //
                -maxLeft + 7 * SCALE,           //
                SCALE,                          //
                0, 11 * SCALE, 7 * SCALE, 19 * SCALE); // bottom left corner
        g.drawImage(image,                      //
                maxRight - 8 * SCALE + SCALE,   //
                -7 * SCALE,                     //
                maxRight + SCALE,               //
                SCALE,                          //
                24 * SCALE, 11 * SCALE, 32 * SCALE, 19 * SCALE); // bottom right
                                                                 // corner
        g.drawImage(image,                      //
                -maxLeft,                       //
                maxHeight,                      //
                -maxLeft + 7 * SCALE,           //
                maxHeight + 7 * SCALE,          //
                0, 0, 7 * SCALE, 7 * SCALE); // top left corner
        g.drawImage(image,                      //
                maxRight - 8 * SCALE + SCALE,   //
                maxHeight,                      //
                maxRight + SCALE,               //
                maxHeight + 7 * SCALE,          //
                24 * SCALE, 0, 32 * SCALE, 7 * SCALE); // top right corner
        
        g.drawImage(image, -2.5F * SCALE, 0, 3.5F * SCALE, 4 * SCALE, // pointer
                13 * SCALE, 18 * SCALE, 19 * SCALE, 22 * SCALE);
        
        g.setColor(Colour.altmain);
        for (int i = 0; i < options.size(); i++) {
            options.get(i).render(-20 - i * 20, g);
        }
        
        g.popTransform();
        
        x1 = e.x + e.width / 2 - maxLeft;
        x2 = e.x + e.width / 2 + maxRight;
        y1 = e.y - 15 + maxHeight;
        y2 = e.y - 15;
        
        output.clear();
        for (int i = 0; i < options.size(); i++) {
            output.add(options.get(i).output());
        }
    }
    
    public void open() {
        isOpen = true;
    }
    
    public void close() {
        isOpen = false;
    }
    
    public class MenuOption {
        
        String title;
        
        void render(int yPos, Graphics g) throws SlickException {
            Font.draw(":", 0, yPos, 2, g);
            Font.draw(title, -Font.getWidth(title, 2), yPos, 2, g);
        }
        
        int getLeftWidth() {
            return Font.getWidth(title, 2);
        }
        
        int getRigthWidth() {
            return 2;
        }
        
        Object output() {
            return title;
        }
    }
    
    public class Radio extends MenuOption implements Clickable {
        
        boolean activated;
        
        public Radio(String title, boolean activated) {
            this.title = title;
            this.activated = activated;
        }
        
        @Override
        void render(int yPos, Graphics g) throws SlickException {
            super.render(yPos, g);
            g.fillRect(10, yPos, 10, 10);
            if (activated) {
                g.setColor(Colour.main);
                g.fillRect(12, yPos + 2, 6, 6);
            }
        }
        
        @Override
        int getLeftWidth() {
            return Font.getWidth(title, 2);
        }
        
        @Override
        int getRigthWidth() {
            return 20;
        }
        
        @Override
        Object output() {
            return activated;
        }
        
        @Override
        public void onClick(float mouseX, float mouseY) {
            if (mouseX >= 10 && mouseX <= 20 && mouseY >= 0 && mouseY <= 10) {
                activated = !activated;
            }
        }
    }
    
    public class Text extends MenuOption {
        
        public Text(String title) {
            this.title = title;
        }
        
        @Override
        void render(int yPos, Graphics g) throws SlickException {
            g.setColor(Colour.main);
            Font.centerText(title, 0, yPos, 2, g);
        }
        
        @Override
        int getLeftWidth() {
            return Font.getWidth(title, 2) / 2;
        }
        
        @Override
        int getRigthWidth() {
            return Font.getWidth(title, 2) / 2 - 3;
        }
        
    }
    
    public interface Clickable {
        public void onClick(float mouseX, float mouseY);
    }
    
    public void setOptions(List<MenuOption> options) {
        this.options = options;
    }
    
    public List getOutput() {
        return output;
    }
    
    public void clicked() {
        float mouseX = Mouse.getX() - (e.x + e.width / 2);
        float mouseY = Game.height - Mouse.getY() - (e.y - 15);
        for (int i = 0; i < options.size(); i++) {
            if (mouseX >= -options.get(i).getLeftWidth() && mouseX <= options.get(i).getRigthWidth() && mouseY >= -20 - i * 20
                    && mouseY <= -10 - i * 20 && options.get(i) instanceof Clickable) {
                ((Clickable) options.get(i)).onClick(mouseX, mouseY - i * 20 + 20);
            }
        }
    }
    
}
