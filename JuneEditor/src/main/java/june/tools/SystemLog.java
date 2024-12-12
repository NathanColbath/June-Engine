package june.tools;

import imgui.ImGui;
import imgui.ImVec4;
import imgui.flag.ImGuiCol;
import june.core.EngineServices;
import june.core.ImGuiFactory;
import june.util.Log;
import june.util.LogEvent;
import june.util.LogLevel;

public class SystemLog implements ImGuiFactory {

    private Log logRef;

    private int consoleEntry = 0;

    public SystemLog(){
        logRef = EngineServices.log();
    }

    public void onImGui(){



        ImGui.begin("System Log");
            for(int index = 0; index < logRef.getLogEvents().size(); index++){
                LogEvent event = logRef.getLogEvents().get(index);

                if(event.getLevel() == LogLevel.ERROR_LEVEL){
                    ImVec4 color = new ImVec4(1.0f, 0f, 0f, 1.0f);

                    ImGui.pushStyleColor(ImGuiCol.Text,color.x, color.y, color.z, color.w);

                    ImGui.text(event.getLogTime().toString().split("\\.")[0] + " [ERROR]: "+ event.getMessage());
                    ImGui.popStyleColor();

                }else if(event.getLevel() == LogLevel.LOG_LEVEL){
                    ImGui.text(event.getLogTime().toString().split("\\.")[0] + " [INFO]: "+ event.getMessage());

                }else if(event.getLevel() == LogLevel.WARN_LEVEL){
                    ImVec4 color = new ImVec4(0.769f, 0.757f, 0.012f, 1.0f);

                    ImGui.pushStyleColor(ImGuiCol.Text,color.x, color.y, color.z, color.w);

                    ImGui.text(event.getLogTime().toString().split("\\.")[0] + " [WARN]: "+ event.getMessage());
                    ImGui.popStyleColor();

                    if(index == logRef.getLogEvents().size() - 1){
                        if(consoleEntry != logRef.getLogEvents().size()){
                            ImGui.setScrollHereY();
                            consoleEntry++;
                        }
                    }
                }




            }

        ImGui.end();
    }
}
