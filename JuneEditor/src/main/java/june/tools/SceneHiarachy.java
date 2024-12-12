package june.tools;

import imgui.ImGui;
import june.core.EngineServices;
import june.core.ImGuiFactory;
import june.entity.Entity;
import june.entity.EntityManager;

public class SceneHiarachy implements ImGuiFactory {

    private Inspecter inspecter;
    private EntityManager em;
    private int selected;

    private boolean selectedBool;

    public SceneHiarachy(Inspecter inspecter) {
        this.inspecter = inspecter;
        em = EngineServices.entityManager();




    }



    public void onImGui(){


        ImGui.begin("Scene Hiarachy");


        if(ImGui.isMouseClicked(1) && ImGui.isWindowHovered()){
            System.out.println("1 was clicked");
            ImGui.openPopup("addMenu");
        }

        if(ImGui.beginPopup("addMenu")){
            ImGui.text("Add");
            ImGui.separator();

            if(ImGui.button("Add Entity")){
                Entity e = EngineServices.entityManager().generateEntity();
                em.addEntity(e);

                ImGui.closeCurrentPopup();
            }

            ImGui.endPopup();
        }



        for(int index = 0; index < em.getEntities().size(); index++){
            Entity temp = em.getEntities().get(index);

            ImGui.pushID(temp.ID());

                if(index == selected){
                    selectedBool = true;
                }else {
                    selectedBool = false;
                }

                if(ImGui.selectable(temp.getRawName(),selectedBool)){
                    inspecter.setSelectedEntity(temp);

                    selected = index;

                }


                ImGui.popID();

        }


        ImGui.end();
    }
}
