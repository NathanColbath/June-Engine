package june.graphics;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {

    private Texture texture;

    private List<Sprite> sprites;

    public SpriteSheet(String filePath, int spriteWidth, int spriteHeight, int numberOfSprites, int spacing) {
        sprites = new ArrayList<Sprite>();

        this.texture = new Texture(filePath);

        int currentX = 0;
        int currentY = texture.getHeight() - spriteHeight;

        for(int index = 0; index < numberOfSprites; index++) {
            float topY = (currentY + spriteHeight) / (float) texture.getHeight();
            float rightX = (currentX + spriteWidth) / (float) texture.getWidth();
            float leftX = currentX / (float) texture.getWidth();
            float bottomY = currentY / (float) texture.getHeight();


            Vector2f[] texCords =  new Vector2f[]{
                    new Vector2f(rightX,topY),
                    new Vector2f(rightX,bottomY),
                    new Vector2f(leftX,bottomY),
                    new Vector2f(leftX,topY)
            };

            Sprite sprite = new Sprite(texture,texCords);
            sprites.add(sprite);

            currentX += spriteWidth + spacing;
            if(currentX >= texture.getWidth()) {
                currentX = 0;
                currentY += spriteHeight + spacing;
            }

        }
    }

    public Sprite getSprite(int index) {
        return sprites.get(index);
    }
}
