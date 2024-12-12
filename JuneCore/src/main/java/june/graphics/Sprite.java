package june.graphics;

import org.joml.Vector2f;

public class Sprite {

    private Texture texture;
    private Vector2f[] texCords;


    public Sprite() {

    }

    public Sprite(Texture texture) {

        this.texture = texture;
           texCords =  new Vector2f[]{
                    new Vector2f(1,1),
                    new Vector2f(1,0),
                    new Vector2f(0,0),
                    new Vector2f(0,1)
            };

    }

    public Sprite(Texture tex, Vector2f[] texCords) {
        this.texture = tex;
        this.texCords = texCords;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2f[] getTexCords() {
        return texCords;
    }
}
