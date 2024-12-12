package june.components;


import june.entity.EntityComponent;
import org.joml.Vector2f;

import java.util.Objects;

public class Transform extends EntityComponent {

    public Vector2f position;
    public Vector2f scale;

    public Transform(){
        position = new Vector2f();
        scale = new Vector2f();
    }

    public Transform(Vector2f position, Vector2f scale){
        this.position = position;
        this.scale = scale;
    }

    public Transform copy(){
        return new Transform(new Vector2f(position), new Vector2f(scale));
    }

    public void copy(Transform other){
        other.position.set(position);
        other.scale.set(scale);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transform transform = (Transform) o;
        return Objects.equals(position, transform.position) && Objects.equals(scale, transform.scale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, scale);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void start() {

    }
}
