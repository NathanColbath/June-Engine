package june.entity;

import june.components.SpriteRenderer;
import june.components.Transform;
import june.core.EngineServices;
import june.scripting.ScriptableComponent;
import org.joml.Vector2f;

import java.util.ArrayList;

public class Entity {




    public String tag;
    private String rawName;
    private int id;
    public Transform transform;
    private ArrayList<EntityComponent> components;


    public Entity() {

    }

    public Entity(int id) {
        init(id,"Entity","GameEntity");
    }

    public Entity(String rawName,int id){
        //this.id = id;

        init(id,rawName,"GameEntity");
    }

    private void init(int id, String rawName, String tag){
        this.rawName = rawName;
        this.id = id;
        this.tag = tag;

        components = new ArrayList<EntityComponent>();
        transform = new Transform();
        transform.scale = new Vector2f(64,64);

    }


    public void addComponent(EntityComponent component) {

        if(component == null){
            EngineServices.log().syserror("Entity [" + ID() + "]: Attempting to add a null component");
            return;
        }

        EngineServices.log().syslog("Entity [" + ID() + "]: Attaching Component: " + component);

        components.add(component);
        component.gameObject = this;
        component.start();

        if(component.getClass().getSuperclass().getName().equals(ScriptableComponent.class.getName())) {
           // component.start();
        }


        if(component.getClass().getName().equals(SpriteRenderer.class.getName())) {
            EngineServices.renderer().addSpriteToBatch((SpriteRenderer)component);
        }
    }

    public void removeComponent(Class componentClass) {
        for (EntityComponent component : components) {
            if (component.getClass().equals(componentClass)) {
                components.remove(component);
                return;
            }
        }
    }

    public boolean hasComponent(Class componentClass){
        for(EntityComponent component : components){
            if(component.getClass().equals(componentClass)){
                return true;
            }
        }


        return false;
    }

    public ArrayList<EntityComponent> getComponents() {
        return components;
    }

    public <T extends EntityComponent> T getComponent(Class<T> componentClass) {
        for (EntityComponent component : components) {
            if (componentClass.isInstance(component)) {
                return (T) component;
            }
        }

        return null;
    }

    public void update(float dt){
        for (EntityComponent component : components) {
            component.update(dt);
        }
    }

    public void initComponents(){
        for (EntityComponent component : components) {

            component.start();
        }
    }

    public String getRawName(){
        return rawName;
    }

    public void setRawName(String rawName){
        this.rawName = rawName;
    }


    public int ID(){
        return id;
    }
}
