package rekkyn.javagame;

import java.util.List;

public interface IWorld {
    public List<Entity> getEntities();
    
    public void add(Entity entity);

}
