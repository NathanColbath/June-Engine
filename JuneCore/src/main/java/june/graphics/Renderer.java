package june.graphics;

import june.components.SpriteRenderer;
import june.core.EngineServices;

import java.util.ArrayList;
import java.util.List;

public class Renderer {

    private final int MAX_BATCH_SIZE = 1000;
    private List<RenderBatch> batches;

    public Renderer(){
        batches = new ArrayList<RenderBatch>();
        EngineServices.log().syslog("Renderer Created");
    }

    public void addSpriteToBatch(SpriteRenderer spriteRenderer){

        EngineServices.log().syslog("Renderer: Draw Call: " + spriteRenderer);

        boolean added = false;
        for(RenderBatch batch : batches){
          if(batch.hasRoom()){
              System.out.println(batch.size());
              Texture tex = spriteRenderer.getTexture();
              if(tex == null || (batch.hasTexture(tex) || batch.hasTextureRoom())){
                  batch.addSpriteToBatch(spriteRenderer);
                  added = true;
                  break;
              }

          }
        }

        if(!added){
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE);
            newBatch.initGPU();
            batches.add(newBatch);
            newBatch.addSpriteToBatch(spriteRenderer);
            EngineServices.log().syslog("Renderer: Creating New Render Batch");
        }
    }

    public void render(){
        for(RenderBatch batch : batches){
            batch.render();
        }



       // System.out.println("BATCH SIZE: "+ batches.size());
    }
}
