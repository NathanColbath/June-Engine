package june.Listeners;

import june.core.EngineServices;
import june.graphics.Window;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Input {

    public Input(){

    }

    public static boolean isKeyPressed(int key){
        return KeyListener.isKeyPressed(key);
    }

    public static boolean isMouseButtonDown(int button){
        return MouseListener.isMouseButtonDown(button);
    }

    public static float getMouseX(){
        return MouseListener.getX();
    }

    public static float getMouseY(){
        return MouseListener.getY();
    }

    public static float getScrollX(){
        return MouseListener.getScrollX();
    }

    public static float getScrollY(){
        return MouseListener.getScrollY();
    }

    public static boolean isMouseDragging(){
        return MouseListener.isDragging();
    }

    public static float getDx(){
        return MouseListener.getDx();
    }

    public static float getDy(){
        return MouseListener.getDy();
    }

    public static Vector2f toWorldCord(Vector2f in){

        Vector2f toReturn;

        float positionX = in.x;
        float positionY = in.y;
        positionX = (positionX/ (float)EngineServices.window().getWidth()) * 2.0f -1.0f;
        positionY = (positionY/ (float)EngineServices.window().getHeight()) * 2.0f -1.0f;


        Vector4f tmpX = new Vector4f(positionX,0,0,1);
        tmpX.mul(EngineServices.getCurrentCamera().getInverseProjectionMatrix()).mul(EngineServices.getCurrentCamera().getViewMatrix());

        Vector4f tmpY = new Vector4f(0,positionY,0,1);
        tmpY.mul(EngineServices.getCurrentCamera().getInverseProjectionMatrix()).mul(EngineServices.getCurrentCamera().getViewMatrix());

        positionX = tmpX.x;
        positionY = tmpY.y;

        toReturn = new Vector2f(positionX, positionY);
        return toReturn;


    }

}
