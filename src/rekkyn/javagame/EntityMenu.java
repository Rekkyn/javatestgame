package rekkyn.javagame;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class EntityMenu {
    
    public boolean isOpen;
    public List<MenuOption> options;
    
    private Entity e;
    
    public EntityMenu(Entity e) {
        this.e = e;
    }
    
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        if (!isOpen) return;
        int scale = 3;
        float xStart = e.x + e.width / 2 - 2.5F * scale;
        float yStart = e.y - 15;
        g.drawImage(Game.scaleImage(Menu.menu, scale), xStart, yStart, xStart + 6 * scale, yStart + 4 * scale, 13 * scale, 18 * scale,
                19 * scale, 22 * scale);
        
        g.setColor(Colour.altmain);
        for (int i = 0; i < options.size(); i++) {
            int xPos = (int) (e.x + e.width / 2);
            int yPos = (int) (e.y - 35);
            options.get(i).render(xPos, yPos, g);
            //g.setColor(Colour.darker);
            //g.drawRect(xPos - options.get(i).getLeftWidth(), yPos, options.get(i).getLeftWidth() + options.get(i).getRigthWidth(), 9);
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
        
        void render(int xPos, int yPos, Graphics g) throws SlickException {
            Font.draw(":", xPos, yPos, 2, g);
            Font.draw(title, xPos - Font.getWidth(title, 2), yPos, 2, g);
        }
        
        int getLeftWidth() {
            return Font.getWidth(title, 2);
        }
        
        int getRigthWidth() {
            return 2;
        }
    }
    
    public class Radio extends MenuOption {
        
        boolean activated;
        
        public Radio(String title, boolean activated) {
            this.title = title;
            this.activated = activated;
        }
        
        @Override
        void render(int xPos, int yPos, Graphics g) throws SlickException {
            super.render(xPos, yPos, g);
            g.fillRect(xPos + 10, yPos, 10, 10);
        }
        
        @Override
        int getLeftWidth() {
            return Font.getWidth(title, 2);
        }
        
        @Override
        int getRigthWidth() {
            return 20;
        }
        
    }
    
    public void setOptions(List<MenuOption> options) {
        this.options = options;
    }
    
}
