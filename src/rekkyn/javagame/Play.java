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

    public Play(int state) {}

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {}

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Colour.background);
        g.fillRect(0, 0, Game.width, Game.height);

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

        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);

            e.update(container, game, delta);

            if (e.removed) {
                entities.remove(i--);
            }
        }
        
        if (input.isMousePressed(0)) {
            Rekkyn rekkyn = new Rekkyn(Mouse.getX() - Rekkyn.width / 2, Game.height - Mouse.getY() - Rekkyn.height / 2);
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
