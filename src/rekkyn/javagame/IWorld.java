package rekkyn.javagame;

import java.util.List;

public interface IWorld {
    
    public boolean takeInput();
    
    public void takeInput(Boolean takeInput);
    
    public List<Entity> getEntities();
    
    public void add(Entity entity);
    
}
