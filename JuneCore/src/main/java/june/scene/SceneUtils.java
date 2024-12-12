package june.scene;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import june.IO.ComponentDeserializer;
import june.entity.EntityComponent;

public class SceneUtils {

    public SceneUtils(){

    }

    public static String createSceneFile(Scene scene){

        String data =  "";

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(EntityComponent.class,new ComponentDeserializer())
                .create();

        data += gson.toJson(scene.getEntityList());



        return data;
    }
}
