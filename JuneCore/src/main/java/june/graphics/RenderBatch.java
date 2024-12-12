package june.graphics;

import june.components.SpriteRenderer;
import june.core.EngineServices;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL15;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL20.*;

public class RenderBatch {
    //Vertex Layout
    //pos              Color                         text cords    texID
    //float, float    float, float, float, float     float, float   float
    private final int POS_SIZE = 2;
    private final int COLOR_SIZE = 4;
    private final int TEX_CORDS_SIZE = 2;
    private final int TEX_ID_SIZE = 1;

    private final int POS_OFFSET = 0;
    private final int COLOR_OFFSET = POS_OFFSET + POS_SIZE * Float.BYTES;
    private final int TEX_CORD_OFFSET = COLOR_OFFSET + COLOR_SIZE * Float.BYTES;
    private final int TEX_ID_OFFSET = TEX_CORD_OFFSET + TEX_CORDS_SIZE * Float.BYTES;
    private final int VERTEX_SIZE = 9;
    private final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;



    private SpriteRenderer[] sprites;
    private int numSprites;
    private boolean hasRoom;
    private float[] vertices;
    private int vaoID,vboID;
    private int maxBatchSize;
    private Shader shader;
    private int[] texSlots = {0,1,2,3,4,5,6,7};

    private List<Texture> textures;

    public RenderBatch(int maxBatchSize){
        this.maxBatchSize = maxBatchSize;
        shader = EngineServices.resourceManager().getResource(Shader.class,"defaultShader");
        shader.compile();
        sprites = new SpriteRenderer[maxBatchSize];

        //4 vertices quads
        vertices = new float[maxBatchSize*4*VERTEX_SIZE];

        numSprites = 0;
        hasRoom = true;

        textures = new ArrayList<Texture>();

    }

    public void initGPU(){
        //Generate and bind
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        //Allocate space for vertices
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,vboID);
        glBufferData(GL_ARRAY_BUFFER, vertices.length * Float.BYTES,GL_DYNAMIC_DRAW);


        //Create and upload indices
        int eboID = glGenBuffers();
        int[] indices = generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,indices,GL_STATIC_DRAW);

        //Enable buffer attrib
        glVertexAttribPointer(0,POS_SIZE,GL_FLOAT,false,VERTEX_SIZE_BYTES,POS_OFFSET);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1,COLOR_SIZE,GL_FLOAT,false,VERTEX_SIZE_BYTES,COLOR_OFFSET);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2,TEX_CORDS_SIZE,GL_FLOAT,false,VERTEX_SIZE_BYTES, TEX_CORD_OFFSET);
        glEnableVertexAttribArray(2);

        glVertexAttribPointer(3,TEX_ID_SIZE,GL_FLOAT,false,VERTEX_SIZE_BYTES, TEX_ID_OFFSET);
        glEnableVertexAttribArray(3);

    }

    public void render(){

        boolean rebufferData = false;

        for(int index = 0; index < numSprites; index++){
            SpriteRenderer sprite = sprites[index];

            if(sprite.isDirty()){
                loadVertexProp(index);
                sprite.setDirty(false);
                rebufferData = true;
            }
        }


        if(rebufferData){
            GL15.glBindBuffer(GL_ARRAY_BUFFER,vboID);
            glBufferSubData(GL_ARRAY_BUFFER,0,vertices);
        }



        //Use Shader
        shader.use();
        shader.uploadMat4f("uProjMatix", EngineServices.sceneManager().getCurrentScene().camera().getProjectionMatrix());
        shader.uploadMat4f("uView", EngineServices.sceneManager().getCurrentScene().camera().getViewMatrix());
        for(int index = 0; index < textures.size(); index++){
            glActiveTexture(GL_TEXTURE0+index + 1);
            textures.get(index).bind();
        }

        shader.uploadIntArray("uTextures",texSlots);


        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES,numSprites*6,GL_UNSIGNED_INT,0);


        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        for(int index = 0; index < textures.size(); index++){
            //glActiveTexture(GL_TEXTURE0+index);
            textures.get(index).unbind();
        }

        shader.detach();
    }

    public void addSpriteToBatch(SpriteRenderer spriteRenderer){
        //Get the index
        int index = numSprites;
        sprites[index] = spriteRenderer;
        numSprites++;

        if(spriteRenderer.getTexture() != null){
            if(!textures.contains(spriteRenderer.getTexture())){
                textures.add(spriteRenderer.getTexture());
            }
        }

        //Add prop to local vertices array
        loadVertexProp(index);

        if(numSprites >= maxBatchSize){
            this.hasRoom = false;
        }
    }

    private void loadVertexProp(int index){
        SpriteRenderer spriteRenderer = sprites[index];

        //Find the offset within array
        int offset = index * 4 * VERTEX_SIZE;

        Vector4f color = spriteRenderer.getColor();
        Vector2f[] texCord = spriteRenderer.getTexCords();

        int texID = 0;

        if(spriteRenderer.getTexture() != null){
            for(int i = 0; i < textures.size(); i++){
                if(textures.get(i) == spriteRenderer.getTexture()){
                    texID = i + 1;
                    break;
                }
            }
        }


        //Add vertices with their prop

        float xAdd = 1.0f;
        float yAdd = 1.0f;


        for(int i = 0; i < 4; i++){


            if(i == 1){
                yAdd = 0.0f;
            }else if(i == 2){
                xAdd = 0.0f;
            }else if(i == 3){
                yAdd = 1.0f;
            }


            //Load position
            vertices[offset] = spriteRenderer.gameObject.transform.position.x +(xAdd * spriteRenderer.gameObject.transform.scale.x);
            vertices[offset + 1] = spriteRenderer.gameObject.transform.position.y +(yAdd * spriteRenderer.gameObject.transform.scale.y);

            //Load the color

            vertices[offset + 2] = color.x;
            vertices[offset + 3] = color.y;
            vertices[offset + 4] = color.z;
            vertices[offset + 5] = color.w;

            //Load texture cord
            vertices[offset + 6] = texCord[i].x;
            vertices[offset + 7] = texCord[i].y;

            //Load texture ID
            vertices[offset + 8] = texID;

            offset += VERTEX_SIZE;

        }
    }

    private int[] generateIndices(){
        // 6 indices per quad
        int[] elements = new int[6 * maxBatchSize];
        for(int i = 0; i < maxBatchSize - 1; i++){
            loadElementIndices(elements,i);
        }

        return elements;
    }

    private void loadElementIndices(int[] elements, int index){
        int offsetArrayIndex = 6 * index;
        int offset = 4 * index;

        //Create tringle
        elements[offsetArrayIndex] = offset + 3;
        elements[offsetArrayIndex + 1] = offset + 2;
        elements[offsetArrayIndex + 2] = offset + 0;

        //Create Triangle 2
        elements[offsetArrayIndex + 3] = offset + 0;
        elements[offsetArrayIndex + 4] = offset + 2;
        elements[offsetArrayIndex + 5] = offset + 1;
    }

    public int size(){
        return numSprites;
    }

    public boolean hasRoom(){
        return hasRoom;
    }

    public boolean hasTextureRoom(){
        return textures.size() < 8;
    }

    public boolean hasTexture(Texture tex){
        return this.textures.contains(tex);
    }
}
