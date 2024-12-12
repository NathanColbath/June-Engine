package june.core;

import june.IO.ResourceManager;
import june.Listeners.KeyListener;
import june.Listeners.MouseListener;

import june.entity.EntityManager;
import june.graphics.*;
import june.scene.SceneManager;
import june.scripting.JuneScript;
import june.scripting.ScriptEngine;
import june.util.Log;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;


public class Core {

    private Window renderWindow;
    private final EngineServices engineServices;
    private SceneManager sceneManager;
    private EntityManager entityManager;
    private Renderer renderer;
    private ScriptEngine scriptEngine;
    private ResourceManager resourceManager;
    private Log log;

    private ImGuiLayer imGuiLayer;

    private FrameBuffer currentFrameBuffer;


    public Core(){


        //renderWindow = Window.create(1600,900,"June");
      //  renderWindow = Window.get();
        engineServices = new EngineServices(this);

    }

    public void setWindow(Window renderWindow){
        this.renderWindow = renderWindow;
    }

    public void setLog(Log log){
        engineServices.setLog(log);
    }

    public void initCore(){

        log = new Log();
        engineServices.setLog(log);

        initGLFW();
        initCallbacks();



        sceneManager = new SceneManager();
        entityManager = new EntityManager();

        resourceManager = new ResourceManager();
        registerResourceTypes();

        renderer = new Renderer();
        scriptEngine = new ScriptEngine();

        imGuiLayer = new ImGuiLayer(renderWindow.getWindowPtr(),renderWindow);
        imGuiLayer.initImGui();


        engineServices.setEntityManager(entityManager);
        engineServices.setSceneManger(sceneManager);
        engineServices.setRenderer(renderer);
        engineServices.setScriptEngine(scriptEngine);
        engineServices.setResourceManager(resourceManager);
        engineServices.setImGuiLayer(imGuiLayer);
        engineServices.setWindow(renderWindow);

        KeyListener.setImGuiIO(imGuiLayer.getIO());
        MouseListener.setImGuiIO(imGuiLayer.getIO());



        String assetDir = resourceManager.getResourceDirectory();
        scriptEngine.setCompiledDir(assetDir);










    }

    private void registerResourceTypes(){
        log.syslog("registering Resource Types");
        resourceManager.registerResourceDirectory("Assets");
        resourceManager.registerType(Shader.class);
        resourceManager.registerType(Texture.class);
        resourceManager.registerType(SpriteSheet.class);
        resourceManager.registerType(JuneScript.class);

        resourceManager.loadResource(Shader.class,"defaultShader",new Shader("Assets/Shaders/default.glsl"));

    }


    //Main Update loop
    //TODO remove render code from this
    public void run() {
        float beginTime = (float)glfwGetTime();
        float endTime = (float)glfwGetTime();
        float dt = -1;

        EngineServices.log().syslog("Core: Main Loop Start");

        while (!glfwWindowShouldClose(renderWindow.getWindowPtr())) {
            //Poll Events
            glfwPollEvents();


            //RENDER

            if(currentFrameBuffer != null)
                currentFrameBuffer.bind();

            glClearColor(0, 0, 0, 0.0f);
            glClear(GL_COLOR_BUFFER_BIT);


            renderer.render();


            if(dt > 0){

                sceneManager.update(dt);
                entityManager.update(dt);

                //System.out.println("" + (1.0f/dt) + "FPS");

            }

            if(currentFrameBuffer != null)
                currentFrameBuffer.unbind();

            imGuiLayer.update(dt);

            glfwSwapBuffers(renderWindow.getWindowPtr());
            endTime = (float)glfwGetTime();
            dt = endTime - beginTime;

            beginTime = endTime;


        }

        cleanUp();
    }



    //Init GLFW
    public void initGLFW(){
        log.syslog("Initializing GLFW");
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        renderWindow.init();

        glfwMakeContextCurrent(renderWindow.getWindowPtr());
        //glfwSwapInterval(1);

        //Make the window visible
        glfwShowWindow(renderWindow.getWindowPtr());

        //Create the lowlevel bindings for opengl
        GL.createCapabilities();
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
    }


    //Set all of our callbacks
    public void initCallbacks(){
        log.syslog("Initializing Window Callbacks");
        glfwSetCursorPosCallback(renderWindow.getWindowPtr(), MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(renderWindow.getWindowPtr(), MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(renderWindow.getWindowPtr(), MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(renderWindow.getWindowPtr(), KeyListener::keyCallback);
        glfwSetWindowSizeCallback(renderWindow.getWindowPtr(), (window, width, height) -> {
            renderWindow.setWidth(width);
            renderWindow.setHeight(height);
        });


    }

    //Clean memory
    public void cleanUp(){
        //Free memory
        glfwFreeCallbacks(renderWindow.getWindowPtr());
        glfwDestroyWindow(renderWindow.getWindowPtr());


        //Terminate GLFW
        glfwTerminate();
        glfwSetErrorCallback(null).free();

        scriptEngine.clean();
        sceneManager.clean();
    }

    public void setFrameBuffer(FrameBuffer frameBuffer){
        currentFrameBuffer = frameBuffer;
    }

}
