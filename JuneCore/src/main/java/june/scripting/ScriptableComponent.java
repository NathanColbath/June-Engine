package june.scripting;

import june.core.EngineServices;
import june.entity.Entity;
import june.entity.EntityComponent;
import org.joml.Vector2f;

import java.lang.reflect.InvocationTargetException;

public abstract class ScriptableComponent extends EntityComponent {

    public String scriptName;

    public <T extends EntityComponent> T getComponent(Class className){
        return (T)gameObject.getComponent(className);
    }

    public void instantiate(Vector2f position, float rotation, Entity prefab){
        try {
            EngineServices.entityManager().addEntity(prefab.getClass().getDeclaredConstructor().newInstance());
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy(Entity e){
        EngineServices.entityManager().removeEntity(e);
    }


}
