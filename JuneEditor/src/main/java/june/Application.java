package june;

import june.core.Core;
import june.core.EngineServices;
import june.graphics.SpriteSheet;
import june.graphics.Texture;
import june.graphics.Window;
import june.scripting.JuneScript;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Application {

    public static void main(String args[]){
        Core core = new Core();

        core.setWindow(Window.create(1920,1080,"Autumn"));

        core.initCore();

        EngineServices.resourceManager().loadResource(Texture.class, "engineFileLogo", new Texture("EngineAssets/fileLogo.png"));
        EngineServices.resourceManager().loadResource(Texture.class, "engineScriptLogo", new Texture("EngineAssets/script.png"));
        EngineServices.resourceManager().loadResource(Texture.class, "engineFolderLogo", new Texture("EngineAssets/folder.png"));
        EngineServices.resourceManager().loadResource(Texture.class, "engineBackLogo", new Texture("EngineAssets/back.png"));
        EngineServices.resourceManager().loadResource(Texture.class, "engineShaderLogo", new Texture("EngineAssets/shader.png"));
        EngineServices.resourceManager().loadResource(Texture.class, "engineJsonLogo", new Texture("EngineAssets/json.png"));
        EngineServices.resourceManager().loadResource(Texture.class, "engineCodeLogo", new Texture("EngineAssets/code.png"));
        findAndGenerateTextures();

        EngineServices.resourceManager().loadResource(Texture.class, "testImage", new Texture("Assets/Images/testImage.png"));
        EngineServices.resourceManager().loadResource(Texture.class, "testImage2", new Texture("Assets/Images/testImage2.png"));
        EngineServices.resourceManager().loadResource(SpriteSheet.class,"spriteSheet",new SpriteSheet("Assets/Images/spritesheet.png",16,16,26,0));



        EngineServices.sceneManager().registerScene("LevelLayer",new LevelScene());
        EngineServices.sceneManager().registerScene("LevelEditorLayer",new LevelEditorScene());


        EngineServices.sceneManager().selectScene("LevelEditorLayer");

        core.run();


    }


    private static void generateTextures(Object o){
        File f = new File(String.valueOf((Path)o));

        if(f.getName().split("\\.")[1].equals("png") || f.getName().split("\\.")[1].equals("jpg")){

            String path = f.getParentFile().getAbsolutePath();
            String name = f.getName();



            EngineServices.resourceManager().loadResource(Texture.class, name, new Texture(path + "/" + name,false));

        }
    }

    private static void findAndGenerateTextures(){
        try (Stream<Path> paths = Files.walk(Path.of(EngineServices.resourceManager().getResourceDirectory()))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(Application::generateTextures);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
