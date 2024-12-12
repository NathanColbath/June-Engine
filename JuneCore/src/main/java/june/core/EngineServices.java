package june.core;

import june.IO.ResourceManager;
import june.Listeners.Input;
import june.entity.EntityManager;
import june.graphics.Camera;
import june.graphics.FrameBuffer;
import june.graphics.Renderer;
import june.graphics.Window;
import june.scene.SceneManager;
import june.scripting.ScriptEngine;
import june.util.Log;

public class EngineServices {

    private static EntityManager em;
    private static SceneManager lm;
    private static Renderer r;
    private static ScriptEngine se;
    private static ResourceManager rm;
    private static ImGuiLayer imGuiLayer;
    private static Log log;
    private static Core coreRef;
    private static Window windowRef;
    private static Camera currentCameraRef;

    public EngineServices(Core core){
        //Create statics
        Input input = new Input();
        coreRef = core;

    }

    public void setSceneManger(SceneManager lm){
        this.lm = lm;
    }

    public void setEntityManager(EntityManager em){
        this.em = em;
    }

    public void setRenderer(Renderer r){
        this.r = r;
    }

    public void setScriptEngine(ScriptEngine se){
        this.se = se;
    }

    public void setResourceManager(ResourceManager rm){
        this.rm = rm;
    }
    public void setImGuiLayer(ImGuiLayer imGuiLayer){
        this.imGuiLayer = imGuiLayer;
    }

    public void setLog(Log log){
        EngineServices.log = log;
    }

    public void setWindow(Window window){
        windowRef = window;
    }

    public static EntityManager entityManager(){
        return em;
    }

    public static SceneManager sceneManager(){
        return lm;
    }

    public static Renderer renderer(){
        return r;
    }

    public static ScriptEngine scriptEngine(){return se;}

    public static ResourceManager resourceManager(){
        return rm;
    }

    public static ImGuiLayer imGuiLayer(){
        return imGuiLayer;
    }

    public static Log log(){
        return log;
    }

    public static void attachFrameBuffer(FrameBuffer buffer){
        coreRef.setFrameBuffer(buffer);
    }

    public static Window window(){
        return windowRef;
    }

    public static Camera getCurrentCamera(){
        return sceneManager().getCurrentScene().camera();
    }
}
