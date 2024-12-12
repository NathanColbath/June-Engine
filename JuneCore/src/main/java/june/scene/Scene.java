package june.scene;

import june.entity.Entity;
import june.graphics.Camera;

import java.util.ArrayList;

public abstract class Scene {

    private Camera mainCamera;

    public boolean created = false;

    private ArrayList<Entity> entityList;
    protected SceneSettings settings;

    public Scene(){
        entityList = new ArrayList<Entity>();
    }

    public void setMainCamera(Camera camera){
        this.mainCamera = camera;
    }

    public void onSceneBegin(){}
    public void onSceneEnd(){};

    public abstract void onUpdate(float dt);

    public Camera camera(){
        return this.mainCamera;
    }

    public ArrayList<Entity> getEntityList(){
        return this.entityList;
    }
}
