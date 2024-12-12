package june.Listeners;

import imgui.ImGuiIO;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SUPER;

public class KeyListener {

    private static KeyListener instance;
    private boolean keyPressed[] = new boolean[360];
    private static ImGuiIO imIo;

    public KeyListener() {

    }

    public static void setImGuiIO(ImGuiIO io){
        imIo = io;
    }


    public static KeyListener get(){
        if(instance == null){
            instance = new KeyListener();
        }

        return instance;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if(action == GLFW_PRESS){
            get().keyPressed[key] = true;
        }else if(action == GLFW_RELEASE){
            get().keyPressed[key] = false;
        }

        if (action == GLFW_PRESS) {
            imIo.setKeysDown(key, true);
        } else if (action == GLFW_RELEASE) {
            imIo.setKeysDown(key, false);
        }

        imIo.setKeyCtrl(imIo.getKeysDown(GLFW_KEY_LEFT_CONTROL) || imIo.getKeysDown(GLFW_KEY_RIGHT_CONTROL));
        imIo.setKeyShift(imIo.getKeysDown(GLFW_KEY_LEFT_SHIFT) || imIo.getKeysDown(GLFW_KEY_RIGHT_SHIFT));
        imIo.setKeyAlt(imIo.getKeysDown(GLFW_KEY_LEFT_ALT) || imIo.getKeysDown(GLFW_KEY_RIGHT_ALT));
        imIo.setKeySuper(imIo.getKeysDown(GLFW_KEY_LEFT_SUPER) || imIo.getKeysDown(GLFW_KEY_RIGHT_SUPER));
    }

    public static boolean isKeyPressed(int key){
        return get().keyPressed[key];
    }


}
