package june.graphics;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private static Window window = null;

    private long windowPtr = 0;
    static int width,height;
    String title;

    private Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public static Window get(){
        if(Window.window == null){
            window = new Window(1920,1080,"June Core");
        }

        return Window.window;
    }

    public static Window create(int width, int height, String title){
        if(Window.window == null){
            window = new Window(width,height,title);
        }

        return Window.window;
    }

    public long getWindowPtr(){
        return windowPtr;
    }


    public void init(){

        //Create the window
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);


        windowPtr = glfwCreateWindow(width,height,title,NULL,NULL);

        if(windowPtr == NULL){
            throw new RuntimeException("Failed to create the GLFW window");
        }


    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }


    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public static float getAspectRatio(){
        return 16.0f/9.0f;
    }

}
