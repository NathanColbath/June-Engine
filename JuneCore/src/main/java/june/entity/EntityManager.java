package june.entity;

import june.components.SpriteRenderer;
import june.core.EngineServices;
import june.scene.Scene;

import java.util.ArrayList;

public class EntityManager {

    private ArrayList<Entity> entities;
    private long currentLastID;

    public EntityManager() {
        entities = new ArrayList<>();
        EngineServices.log().syslog("EntityManager Created");
    }

    public void init(){
        for(Entity e : entities){

            e.initComponents();
        }

        currentLastID = 0;


    }

    public Entity generateEntity(){
        Entity e = new Entity((int)currentLastID++);
        e.setRawName("Entity");
        EngineServices.log().syslog("EntityManager: Generating entity: " + e);
        return e;
    }
    public Entity generateEntity(String rawName){
        Entity e = new Entity((int)currentLastID++);
        e.setRawName(rawName);
        EngineServices.log().syslog("EntityManager: Generating entity: " + e);
        return e;
    }


    public void update(float dt){
        for(int index = 0; index < entities.size(); index++){
            Entity e = entities.get(index);

            e.update(dt);

            if(e.hasComponent(SpriteRenderer.class) && ((SpriteRenderer)e.getComponent(SpriteRenderer.class)).isSpriteChanged()){
                SpriteRenderer sr = e.getComponent(SpriteRenderer.class);
                EngineServices.renderer().addSpriteToBatch(sr);
                sr.setSpriteChanged(false);
            }
        }
    }

    public void attachCurrentScene(Scene scene){
        entities = scene.getEntityList();

        init();
    }

    public void addEntity(Entity entity){
        entities.add(entity);



    }

    public ArrayList<Entity> getEntities(){
        return entities;
    }

    public void removeEntity(Entity entity){
        entities.remove(entity);
    }

}
