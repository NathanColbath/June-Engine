package june.Listeners;

import imgui.ImGui;
import imgui.ImGuiIO;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {

    private static MouseListener instance;

    private double scrollX, scrollY;
    private double xPos,yPos,lastX,lastY;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean isDragging;
    private static ImGuiIO io;

    private MouseListener(){
        scrollX = 0;
        scrollY = 0;
        xPos = 0;
        yPos = 0;
        lastX = 0;
        lastY = 0;

    }

    public static void setImGuiIO(ImGuiIO io){
        MouseListener.io = io;
    }

    public static MouseListener get(){
        if(instance == null){
            instance = new MouseListener();
        }

        return instance;
    }

    public static void mousePosCallback(long window, double xpos, double ypos){
        get().lastX = get().xPos;
        get().lastY = get().yPos;

        get().xPos = xpos;
        get().yPos = ypos;



        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods){

        if(action == GLFW_PRESS){
            if(button < get().mouseButtonPressed.length){
                get().mouseButtonPressed[button] = true;
            }

        }else if(action  == GLFW_RELEASE){
            if(button < get().mouseButtonPressed.length){
                get().mouseButtonPressed[button] = false;
            }
            get().isDragging = false;
        }

        io.setMouseDown(get().mouseButtonPressed);

        if (!io.getWantCaptureMouse() && get().mouseButtonPressed[1]) {
            ImGui.setWindowFocus(null);
        }

    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset){
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    public static void cleanUp(){
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static float getX(){
        return (float)get().xPos;
    }

    public static float getY(){
        return (float)get().yPos;
    }

    public static float getDx(){
        return (float)(get().lastX - getX());
    }

    public static float getDy(){
        return (float)(get().lastY - getY());
    }

    public static float getScrollX(){
        return (float)get().scrollX;
    }

    public static float getScrollY(){
        return (float)get().scrollY;
    }

    public static boolean isDragging(){
        return get().isDragging;
    }

    public static boolean isMouseButtonDown(int button){
        return get().mouseButtonPressed[button];
    }
}
