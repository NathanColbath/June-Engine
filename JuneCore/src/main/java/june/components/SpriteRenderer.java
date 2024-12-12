package june.components;

import june.entity.EntityComponent;
import june.graphics.Sprite;
import june.graphics.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;


public class SpriteRenderer extends EntityComponent {

    private Vector4f color;
    private Sprite sprite;

    private boolean spriteChanged = false;

    private transient boolean dirty = false;
    private transient Transform lastTransform;

    public SpriteRenderer() {}

    public SpriteRenderer(Vector4f color) {
        this.color = color;
        this.sprite = new Sprite(null);
    }

    public SpriteRenderer(Sprite sprite){
        this.sprite = sprite;
        this.color = new Vector4f(1,1,1,1);
    }

    public Vector4f getColor() {
        return color;
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public Vector2f[] getTexCords() {
      return sprite.getTexCords();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.dirty = true;
    }

    public void setColor(Vector4f color) {
        this.color = color;
        dirty = true;
    }

    public boolean isDirty(){
        return dirty;
    }

    public void setDirty(boolean dirty){
        dirty = dirty;
    }

    public Sprite getSprite(){
        return sprite;
    }

    @Override
    public void update(float dt) {
        if(!lastTransform.equals(gameObject.transform)){
            this.gameObject.transform.copy(lastTransform);
            dirty = true;
        }
    }

    @Override
    public void start() {
        dirty = true;
        lastTransform = gameObject.transform.copy();
    }

    public boolean isSpriteChanged() {
        return spriteChanged;
    }

    public void setSpriteChanged(boolean spriteChanged) {
        this.spriteChanged = spriteChanged;
    }
}
