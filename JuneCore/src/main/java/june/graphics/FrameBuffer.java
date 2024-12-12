package june.graphics;

import june.core.EngineServices;

import static org.lwjgl.opengl.GL30C.*;

public class FrameBuffer {

    private int fboID = 0;
    private Texture texture = null;

    public FrameBuffer(int width, int height) {
        fboID = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, fboID);

        // Create the texture to render the data to, and attach it to our framebuffer
        this.texture = new Texture(width, height);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D,
                this.texture.getTextureID(), 0);

        // Create renderbuffer store the depth info
        int rboID = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, rboID);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, width, height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rboID);

        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE){
            EngineServices.log().error("FrameBufferFactory: Framebuffer was not created");
        }
        glBindFramebuffer(GL_FRAMEBUFFER,0);

    }

    public int getID(){
        return texture.getTextureID();
    }

    public void bind(){
        glBindFramebuffer(GL_FRAMEBUFFER,fboID);
    }

    public void unbind(){
        glBindFramebuffer(GL_FRAMEBUFFER,0);
    }

    public int getFboID(){
        return fboID;
    }


}
