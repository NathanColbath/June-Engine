package june;

import june.Listeners.Input;
import june.components.SpriteRenderer;
import june.components.Transform;
import june.core.EngineServices;
import june.scene.Scene;
import june.scene.SceneSettings;
import june.scene.SceneUtils;
import june.entity.Entity;
import june.graphics.*;
import june.scripting.JuneScript;
import june.scripting.ScriptableComponent;
import june.tools.*;
import org.joml.Vector2f;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;


public class LevelEditorScene extends Scene {

    public Camera camera = new Camera(new Vector2f(0,0));

    private Entity test;

    Shader defaultShader = new Shader("Assets/Shaders/default.glsl");
    private Texture texture;

    Entity e1 = new Entity();

    private boolean compiled = false;


    private Inspecter inspecter;
    private SceneHiarachy sceneHiarachy;
    private AssetBrowser assetBrowser;
    private SystemLog systemLog;
    private GameViewPort gameViewPort;

    public LevelEditorScene() {

    }

    @Override
    public void onSceneBegin() {

        this.settings = new SceneSettings(){
            public int WINDOW_WIDTH = 800;
            public int WINDOW_HEIGHT = 600;

        };




        setMainCamera(camera);

        FrameBuffer frameBuffer = new FrameBuffer(1920,1080);

        EngineServices.attachFrameBuffer(frameBuffer);


        inspecter = new Inspecter();
        sceneHiarachy = new SceneHiarachy(inspecter);
        assetBrowser = new AssetBrowser();
        systemLog = new SystemLog();
        gameViewPort = new GameViewPort(frameBuffer);


        compileScriptsTesting();

        //CoreGlew.resourceManager().loadResource(JuneScript.class,"s1",new JuneScript("Assets/scripts/src","Test.java"));

        SpriteSheet spriteSheet = EngineServices.resourceManager().getResource(SpriteSheet.class,"spriteSheet");
        EngineServices.resourceManager().getResource(SpriteSheet.class,"spriteShseet");
        EngineServices.resourceManager().registerType(SpriteSheet.class);

        e1 = EngineServices.entityManager().generateEntity("Mario");
        e1.transform = new Transform(new Vector2f(100,100),new Vector2f(256,256));

        e1.addComponent(new SpriteRenderer(spriteSheet.getSprite(0)));
        //e1.addComponent(new SpriteRenderer(new Vector4f(1f,0f,1f,1f)));

        //CoreGlew.scriptEngine().compile("Assets/scripts/src/Test.java","Test.java");

        EngineServices.entityManager().addEntity(e1);




        Entity e2 = EngineServices.entityManager().generateEntity("Gumba");
        e2.transform = new Transform(new Vector2f(400,400),new Vector2f(256,256));
        SpriteRenderer e2Renderer = new SpriteRenderer(spriteSheet.getSprite(15));

        e2.addComponent(e2Renderer);
        e2.addComponent(e1.transform);

        EngineServices.entityManager().addEntity(e2);



        inspecter.setSelectedEntity(e1);

        //inspecter.onImGui();
        //sceneHiarachy.onImGui();
        //assetBrowser.onImGui();
        //console.onImGui();
        EngineServices.imGuiLayer().enableDockspace();
        EngineServices.imGuiLayer().addFactory(gameViewPort);
        EngineServices.imGuiLayer().addFactory(inspecter);
        EngineServices.imGuiLayer().addFactory(sceneHiarachy);
        EngineServices.imGuiLayer().addFactory(assetBrowser);
        EngineServices.imGuiLayer().addFactory(systemLog);





    }

    @Override
    public void onSceneEnd() {
        System.out.println(SceneUtils.createSceneFile(this));
    }

    private void checkAndAddScript(Object o){
        File f = new File(String.valueOf((Path)o));

        if(f.getName().split("\\.")[1].equals("java")){

            String path = f.getParentFile().getAbsolutePath();
            String name = f.getName();

            JuneScript workingScript = new JuneScript(path,f.getName());
            EngineServices.scriptEngine().compile(workingScript);
            EngineServices.scriptEngine().loadScript(workingScript);
            EngineServices.resourceManager().loadResource(JuneScript.class,f.getName(),workingScript);

        }
    }

    private void compileScriptsTesting(){
        try (Stream<Path> paths = Files.walk(Path.of(EngineServices.resourceManager().getResourceDirectory()))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(this::checkAndAddScript);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void onUpdate(float dt) {

        moveCamera(dt);

    }

    private void moveCamera(float dt){

        float moveSpeed = 150f;

        //e1.transform.position.x = Input.getMouseX();

        /*
        if(Input.isKeyPressed(KeyEvent.VK_SPACE)){
            if(!compiled){
              //  CoreGlew.scriptEngine().compile(CoreGlew.resourceManager().getResource(JuneScript.class,"s1"));
                ScriptableComponent script = EngineServices.scriptEngine().loadScript(EngineServices.resourceManager().getResource(JuneScript.class,"Test.java"));
                e1.addComponent(script);

                compiled = true;
            }
        }
         */




        if(Input.isKeyPressed(KeyEvent.VK_A)){
            camera.position.x -= moveSpeed * dt;
        }

        if(Input.isKeyPressed(KeyEvent.VK_D)){
            camera.position.x += moveSpeed * dt;
        }

        if(Input.isKeyPressed(KeyEvent.VK_W)){
            camera.position.y += moveSpeed * dt;
        }

        if(Input.isKeyPressed(KeyEvent.VK_S)){
            camera.position.y -= moveSpeed * dt;
        }

        if(Input.isMouseButtonDown(2) && Input.isMouseDragging()){
           float xDif = Input.getDx();
           float yDif = Input.getDy();

           float maxDif = 25;

           if(xDif > maxDif){
               xDif = maxDif;
           }

            if(xDif < -maxDif){
                xDif = -maxDif;
            }

           if(yDif > maxDif){
               yDif = maxDif;
           }

            if(yDif < -maxDif){
                yDif = -maxDif;
            }


           System.out.println(xDif);

           camera.position.x += xDif * dt * moveSpeed;
           camera.position.y -= (yDif * dt * moveSpeed);



        }

    }
}
