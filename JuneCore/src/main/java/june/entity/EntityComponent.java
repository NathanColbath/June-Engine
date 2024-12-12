package june.entity;

public abstract class EntityComponent {
    public transient Entity gameObject;

    public abstract void update(float dt);
    public abstract void start();
}
