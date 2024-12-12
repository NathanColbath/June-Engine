package june.scene;

import june.core.EngineServices;

import java.util.HashMap;

;

public class SceneManager {

    private HashMap<String, Scene> scenes;
    private Scene currentScene;



    public SceneManager(){
        scenes =new HashMap<String, Scene>();
        EngineServices.log().syslog("Scene Manager Created");
    }

    public void registerScene(String name, Scene scene){

        EngineServices.log().syslog("SceneManager: Registering Scene: " + scene);
        scenes.put(name, scene);
        EngineServices.log().syslog("SceneManager: Attaching Object List");
        EngineServices.entityManager().attachCurrentScene(scene);
        if(!scene.created){

            scene.onSceneBegin();
            scene.created = true;
        }

    }

    public void unregisterScene(String name){
        scenes.remove(name);
    }

    public void selectScene(String name){
        if(currentScene != null){
            currentScene.onSceneEnd();
        }
        currentScene = scenes.get(name);
        EngineServices.log().syslog("SceneManager: Selecting Scene: " + currentScene);
        EngineServices.entityManager().attachCurrentScene(currentScene);

    }

    public Scene getScene(String name){
        return scenes.get(name);
    }

    public Scene getCurrentScene(){
        return currentScene;
    }

    public void update(float dt){
        if(currentScene != null){
            currentScene.onUpdate(dt);
        }

    }

    public void clean(){
        currentScene.onSceneEnd();
    }

}
