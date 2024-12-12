package june.tools;

import imgui.ImGui;
import imgui.ImVec2;
import june.core.EngineServices;
import june.core.ImGuiFactory;
import june.graphics.Texture;

import java.io.File;

public class AssetBrowser implements ImGuiFactory {

    private File resourceDir;

    private String rootWorkingDir;
    private String lastDir;

    public AssetBrowser(){
        resourceDir = new File(EngineServices.resourceManager().getResourceDirectory());
        rootWorkingDir = resourceDir.getAbsolutePath();
        lastDir = rootWorkingDir;
    }

    private void changeDir(String newDir){

        resourceDir = new File(newDir);
        lastDir = resourceDir.getParentFile().getAbsolutePath();


    }

    public void onImGui(){
        ImGui.begin("Asset Browser");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);

        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);

        ImVec2 itemSpaceing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpaceing);

        float windowX2 = windowPos.x + windowSize.x;

        File[] files = resourceDir.listFiles();

        if(!resourceDir.getAbsolutePath().equals(rootWorkingDir)){
            File root = new File(lastDir);
            File[] newFiles = new File[files.length+1];
            newFiles[0] = root;
            for(int i = 0; i < files.length; i++){
                newFiles[i + 1] = files[i];
            }

            files = newFiles;
        }


        for(int index = 0; index < files.length; index++){
            File temp = files[index];
            float imageWidth = 32*4;
            float imageHeight = 32*4;
            int id = temp.hashCode();


            ImGui.beginGroup();


            ImGui.pushID(id);
            if(!temp.getName().substring(0,2).equals("\\.")) {
                if (temp.isDirectory() && !(temp.getAbsolutePath().equals(lastDir))) {
                    Texture texture = EngineServices.resourceManager().getResource(Texture.class, "engineFolderLogo");

                    if (ImGui.imageButton(texture.getTextureID(), imageWidth, imageHeight,0,1,1,0)) {
                        changeDir(temp.getAbsolutePath());
                        System.out.println("Asset browser changed to " + temp.getAbsolutePath());
                    }
                } else if (temp.getName().endsWith(".png") || temp.getName().endsWith(".jpg") || temp.getName().endsWith(".jpng")) {
                    Texture texture = EngineServices.resourceManager().getResource(Texture.class, temp.getName());

                    if(texture != null){
                        if (ImGui.imageButton(texture.getTextureID(), imageWidth, imageHeight,0,1,1,0)) {

                        }

                        if(ImGui.beginDragDropSource()){

                            String name =  temp.getName();

                            //EngineServices.resourceManager().getResource(Texture.class, )

                            ImGui.setDragDropPayloadObject("texture", EngineServices.resourceManager().getResource(Texture.class, name));
                            ImGui.text(name);
                            ImGui.endDragDropSource();
                        }
                    }





                } else if (temp.getName().endsWith(".txt") || temp.getName().endsWith(".md")) {
                    Texture texture = EngineServices.resourceManager().getResource(Texture.class, "engineFileLogo");

                    if (ImGui.imageButton(texture.getTextureID(), imageWidth, imageHeight,0,1,1,0)) {

                    }

                } else if (temp.getName().endsWith(".java")) {
                    Texture texture = EngineServices.resourceManager().getResource(Texture.class, "engineScriptLogo");

                    if (ImGui.imageButton(texture.getTextureID(), imageWidth, imageHeight,0,1,1,0)) {

                    }

                } else if(temp.getAbsolutePath().equals(lastDir)) {
                    Texture texture = EngineServices.resourceManager().getResource(Texture.class, "engineBackLogo");

                    if (ImGui.imageButton(texture.getTextureID(), imageWidth, imageHeight,0,1,1,0)) {
                        changeDir(lastDir);
                        System.out.println("Asset browser changed to " + temp.getAbsolutePath());
                    }
                } else if (temp.getName().endsWith(".glsl")) {
                    Texture texture = EngineServices.resourceManager().getResource(Texture.class, "engineShaderLogo");

                    if (ImGui.imageButton(texture.getTextureID(), imageWidth, imageHeight,0,1,1,0)) {

                    }
                }
                else if (temp.getName().endsWith(".json")) {
                    Texture texture = EngineServices.resourceManager().getResource(Texture.class, "engineJsonLogo");

                    if (ImGui.imageButton(texture.getTextureID(), imageWidth, imageHeight,0,1,1,0)) {

                    }
                }


            }


            ImGui.newLine();
            ImGui.text(temp.getName());
            ImGui.endGroup();




            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonPos.x + itemSpaceing.x + imageWidth;
            if(index + 1 < files.length && nextButtonX2 < windowX2){
                ImGui.sameLine();
            }

            ImGui.popID();
        }


        ImGui.end();
    }
}
