package rekkyn.javagame;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Play extends BasicGameState implements IWorld {
    
    public static List<Entity> entities = new ArrayList<Entity>();
    public boolean disco;
    public boolean AI;
    public boolean follow;
    public int[] size = { 3, 9, 15, 21, 27, 33, 39, 45, 51 };
    private int currentSize = 4;

    public Play(int state) {}

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {}

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        if (disco) {
        g.setColor(Colour.changeHue(Colour.background, (int) (System.currentTimeMillis() % 1000 * 0.36)));
        } else {
            g.setColor(Colour.background);
        }
        g.fillRect(0, 0, Game.width, Game.height);
        
        if (disco) {
        g.setColor(Colour.changeHue(Colour.lighter, (int) (System.currentTimeMillis() % 1000 * 0.36)));
        } else {
            g.setColor(Colour.lighter);
        }
        Font.draw("[A] AI: "+ (AI ? "on" : "off"), Game.width - 200, 10, 2, g);
        Font.draw("[S] Follow Mouse: "+ (follow ? "on" : "off"), Game.width - 200, 25, 2, g);
        Font.draw("[D] Disco: "+ (disco ? "on" : "off"), Game.width - 200, 40, 2, g);
        Font.draw("[1 to 9] Size: " + (currentSize + 1), Game.width - 200, 55, 2, g);

        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);

            e.render(container, game, g);
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(Game.OPTIONS);
            Options.prevState = getID();
        }
        if (input.isKeyDown(Input.KEY_C)) {
            for (int i = 0; i < entities.size(); i++) {
                Entity e = entities.get(i);
                e.remove();
            }
        }
        if (input.isKeyPressed(Input.KEY_D)) {
            disco = !disco;
        }
        if (input.isKeyPressed(Input.KEY_A)) {
            AI = !AI;
        }
        if (input.isKeyPressed(Input.KEY_S)) {
            follow = !follow;
        }
        
        if (input.isKeyPressed(Input.KEY_1)) {
            currentSize = 0;
        }
        if (input.isKeyPressed(Input.KEY_2)) {
            currentSize = 1;
        }
        if (input.isKeyPressed(Input.KEY_3)) {
            currentSize = 2;
        }
        if (input.isKeyPressed(Input.KEY_4)) {
            currentSize = 3;
        }
        if (input.isKeyPressed(Input.KEY_5)) {
            currentSize = 4;
        }
        if (input.isKeyPressed(Input.KEY_6)) {
            currentSize = 5;
        }
        if (input.isKeyPressed(Input.KEY_7)) {
            currentSize = 6;
        }
        if (input.isKeyPressed(Input.KEY_8)) {
            currentSize = 7;
        }
        if (input.isKeyPressed(Input.KEY_9)) {
            currentSize = 8;
        }



        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);

            e.update(container, game, delta);

            if (e.removed) {
                entities.remove(i--);
            }
        }
        
        if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            for (int i = 0; i < entities.size(); i++) {
                Entity e = entities.get(i);
                int mouseX = Mouse.getX();
                int mouseY = Game.height - Mouse.getY();
                if (mouseX >= e.x && mouseX <= e.x + e.width && mouseY >= e.y && mouseY <= e.y + e.height) {
                    e.onRightClicked();
                }
                System.out.println(e);
            }
        }
        
        if (input.isMousePressed(0)) {
            Rekkyn rekkyn = new Rekkyn(Mouse.getX(), Game.height - Mouse.getY(), size[currentSize], size[currentSize]);
            rekkyn.x -= rekkyn.width / 2;
            rekkyn.y -= rekkyn.height / 2;
            rekkyn.playerControlled = false;
            rekkyn.isFree = true;
            add(rekkyn);
        }

    }
    
    public void add(Entity entity) {
        entity.removed = false;
        entities.add(entity);
    }

    @Override
    public int getID() {
        return Game.PLAY;
    }

    @Override
    public List<Entity> getEntities() {
        return entities;
    }

}
