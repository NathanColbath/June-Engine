package june.tools;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiWindowFlags;
import june.core.ImGuiFactory;
import june.graphics.FrameBuffer;
import june.graphics.Window;

public class GameViewPort implements ImGuiFactory {

    private FrameBuffer frameBuffer;

    public GameViewPort(FrameBuffer frameBuffer) {
        this.frameBuffer = frameBuffer;
    }


    public void onImGui() {

        ImGui.begin("Game Viewport", ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse );

        ImVec2 windowSize = getLargestSizeOfViewport();
        ImVec2 windowPos = getCenteredPositionForViewport(windowSize);

        ImGui.setCursorPos(windowPos.x, windowPos.y);
        int textureID = frameBuffer.getID();
        ImGui.image(textureID, windowSize.x, windowSize.y,0,1,1,0);
        ImGui.end();

    }

    ImVec2 getLargestSizeOfViewport(){
        ImVec2 windowSize = new ImVec2();
        ImGui.getContentRegionAvail(windowSize);
        windowSize.x -= ImGui.getScrollX();
        windowSize.y -= ImGui.getScrollY();

        float aspectWidth = windowSize.x;
        float aspectHeight = aspectWidth/ Window.getAspectRatio();
        if(aspectHeight > windowSize.y){
            aspectHeight = windowSize.y;
            aspectWidth = aspectHeight * Window.getAspectRatio();
        }

        return new ImVec2(aspectWidth, aspectHeight);
    }

    ImVec2 getCenteredPositionForViewport(ImVec2 aspectSize){
        ImVec2 windowSize = new ImVec2();
        ImGui.getContentRegionAvail(windowSize);
        windowSize.x -= ImGui.getScrollX();
        windowSize.y -= ImGui.getScrollY();

        float viewportX = (windowSize.x/2.0f) - (aspectSize.x / 2.0f);
        float viewportY = (windowSize.y/2.0f) - (aspectSize.y / 2.0f);

        return new ImVec2(viewportX, viewportY);
    }
}
