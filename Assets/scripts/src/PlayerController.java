import java.awt.event.KeyEvent;

import org.joml.Vector2f;

import june.Listeners.Input;
import june.components.SpriteRenderer;
import june.components.Transform;
import june.scripting.ScriptableComponent;

public class PlayerController extends ScriptableComponent{

    
    public float moveSpeed = 2;
    public float moveY = 2;


    @Override
    public void start() {
       
       Transform transform = gameObject.transform;
       transform.position.x = 15;

       System.out.println("Init from script");
       
       

    
    }

    @Override
    public void update(float dt) {
       
      gameObject.transform.position.x += moveSpeed * dt;
      gameObject.transform.position.y -= moveY * dt;

      if(Input.isKeyPressed(KeyEvent.VK_Q)){
       
      }

      


    }

}
