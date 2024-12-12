package june.graphics;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {

    public Vector2f position;
    private Matrix4f projection,view,inverseView,inverseProjection;

    public Camera(Vector2f position){

        this.position = position;
        this.projection = new Matrix4f();
        this.view = new Matrix4f();
        this.inverseView = new Matrix4f();
        this.inverseProjection = new Matrix4f();
        adjustProjection();

    }

    public void adjustProjection(){
        projection.identity();
        projection.ortho(0.0f,32.0f * 40.0f,0.0f,32.0f * 21.0f,0,100);

        projection.invert(inverseProjection);


    }

    public Matrix4f getViewMatrix(){
        Vector3f cameraFront = new Vector3f(0.0f,0.0f,-1);
        Vector3f cameraUp = new Vector3f(0.0f,1.0f,0);
        this.view.identity();
        this.view = view.lookAt(new Vector3f(position.x,position.y,20f),cameraFront.add(position.x,position.y,0.0f),cameraUp);

        view.invert(inverseView);

        return this.view;
    }

    public Matrix4f getProjectionMatrix(){
        return this.projection;
    }

    public Matrix4f getInverseViewMatrix(){
        return this.inverseView;
    }

    public Matrix4f getInverseProjectionMatrix(){
        return this.inverseProjection;
    }

}
