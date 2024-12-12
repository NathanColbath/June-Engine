package june.tools;

import imgui.ImGui;
import june.core.ImGuiFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Stack;

public class DebugWindow implements ImGuiFactory{

    private ArrayList<Float> memoryDataPoints;

    public DebugWindow(){
        memoryDataPoints = new ArrayList<>();
    }

    public void updateData(){
        Runtime runtime = Runtime.getRuntime();

        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;


        int size = 256;

        if(memoryDataPoints.size() > size){
            memoryDataPoints.remove(size - 1);
        }



        long data = usedMemory / 1024 / 1024;

        memoryDataPoints.add(0,(float)data);
    }

    @Override
    public void onImGui() {
        updateData();
        ImGui.begin("Debug Info");

        float[] points = new float[memoryDataPoints.size()];
        Object[] data = memoryDataPoints.toArray();

        for(int i = 0; i < memoryDataPoints.size(); i++) {
            points[i] = (float) data[i];
        }




        ImGui.plotLines("Used Memory",points,memoryDataPoints.size(),0,"Used Memory",0,300,300,200);

        ImGui.end();
    }
}
