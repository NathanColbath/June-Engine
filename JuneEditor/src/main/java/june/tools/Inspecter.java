package june.tools;

import imgui.ImGui;
import june.components.SpriteRenderer;
import june.components.Transform;
import june.core.EngineServices;
import june.core.ImGuiFactory;
import june.entity.Entity;
import june.entity.EntityComponent;
import june.graphics.Sprite;
import june.graphics.SpriteSheet;
import june.graphics.Texture;
import june.scripting.JuneScript;
import june.scripting.ScriptableComponent;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.lang.reflect.Field;

public class Inspecter implements ImGuiFactory {


    private Entity selectedEntity;


    public Inspecter(){

    }

    public void onImGui(){
        ImGui.begin("Inspecter");

        ImGui.text("Tag: " + selectedEntity.tag);



        //Drag drop






        if(ImGui.isMouseClicked(1) && ImGui.isWindowHovered()){
            System.out.println("1 was clicked");
            ImGui.openPopup("addComponentMenu");
        }



        if(ImGui.beginPopup("addComponentMenu")){
            ImGui.text("Add Component");
            ImGui.separator();

            if(ImGui.button("Sprite Renderer")){
                SpriteSheet s1 = EngineServices.resourceManager().getResource(SpriteSheet.class,"spriteSheet");
                SpriteRenderer renderer = new SpriteRenderer(s1.getSprite(15));
                selectedEntity.addComponent(renderer);

                ImGui.closeCurrentPopup();
            }

            if(ImGui.button("Script Component")){
                ImGui.openPopup("scriptList");

               // ImGui.closeCurrentPopup();
            }

            if(ImGui.beginPopup("scriptList")){
                ImGui.text("Scripts");
                for(JuneScript s : EngineServices.scriptEngine().getScripts()){
                    String scriptName = s.getFileName();
                    if(ImGui.button(scriptName)){
                        ScriptableComponent e = EngineServices.scriptEngine().loadScript(EngineServices.resourceManager().getResource(JuneScript.class, scriptName));
                        selectedEntity.addComponent(e);
                        ImGui.closeCurrentPopup();
                    }
                }
                ImGui.endPopup();
            }

            ImGui.endPopup();
        }




        if(ImGui.collapsingHeader("Transform")){
            Transform transform = selectedEntity.transform;


            float[] tr = {transform.position.x,transform.position.y};
            float[] sr = {transform.scale.x,transform.scale.y};

            if(ImGui.dragFloat2("Position",tr)){
                transform.position.set(tr[0],tr[1]);
            }

            if(ImGui.dragFloat2("Scale",sr)){
                transform.scale.set(sr[0],sr[1]);
            }
        }


        for(EntityComponent e : selectedEntity.getComponents()){

            if(e.getClass().getSimpleName().equals("SpriteRenderer")){

                if(ImGui.collapsingHeader("Sprite Renderer")){
                    SpriteRenderer temp = (SpriteRenderer)e;

                    float[] v = {temp.getColor().x,temp.getColor().y,temp.getColor().z,temp.getColor().w};

                    ImGui.indent();
                    if(ImGui.collapsingHeader("Color")){
                        if(ImGui.colorPicker4("Sprite Color",v)){
                            temp.setColor(new Vector4f(v));
                            temp.isDirty();
                        }
                    }
                    ImGui.unindent();


                    Vector2f[] tCord = temp.getSprite().getTexCords();
                    int id = temp.getSprite().getTexture().getTextureID();


                    ImGui.text("Texture");
                    ImGui.image(id,128,128,tCord[3].x,tCord[3].y,tCord[1].x,tCord[1].y);


                    if(ImGui.beginDragDropTarget()){
                        Object payload = ImGui.acceptDragDropPayloadObject("texture");
                        if(payload != null){
                            if(payload.getClass().isAssignableFrom(Texture.class)){
                                temp.setSprite(new Sprite((Texture)payload));
                                temp.setSpriteChanged(true);
                            }
                        }

                        ImGui.endDragDropTarget();
                    }
                    


                }


            }


            if(e.getClass().getSuperclass().getSimpleName().equals("ScriptableComponent")){
               ScriptableComponent temp = (ScriptableComponent)e;

                if(ImGui.collapsingHeader("JuneScript: " + temp.scriptName)){
                    ImGui.text(e.getClass().getCanonicalName());
                    Field[] fields = e.getClass().getFields();



                    for(Field f : fields){

                        System.out.println(f.getType().getSimpleName());

                        try{
                            if(f.getType().getSimpleName().equals("float")){
                                float[] v = {f.getFloat(e)};

                                ImGui.dragFloat(f.getName(),v);
                                f.setFloat(e,v[0]);
                            }

                            if(f.getType().getSimpleName().equals("int")){
                                int[] v = {f.getInt(e)};

                                ImGui.dragInt(f.getName(),v);
                                f.setInt(e,v[0]);
                            }

                            if(f.getType().getSimpleName().equals("boolean")){
                                boolean[] v = {f.getBoolean(e)};

                                boolean change = f.getBoolean(e);

                                change = (ImGui.checkbox(f.getName(),change));
                                f.setBoolean(e,change);


                            }



                        }catch (Exception ex){

                        }

                    }
                }
            }
        }


        ImGui.end();

    }

    public void setSelectedEntity(Entity e){
        selectedEntity = e;
    }
}
