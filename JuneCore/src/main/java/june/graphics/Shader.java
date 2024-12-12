package june.graphics;

import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.File;
import java.nio.FloatBuffer;
import java.util.Scanner;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;

public class Shader {

    private String fileLocation;

    private String vertexShaderSrc;
    private String fragmentShaderSrc;

    private int vertexID,fragmentID,shaderProgram;


    public Shader(){

    }

    public Shader(String fileLocation){
        this.fileLocation = fileLocation;

        String data = "";

        try{
            File shaderFile = new File(fileLocation);
            Scanner myReader = new Scanner(shaderFile);
            while(myReader.hasNextLine()){
                String line = myReader.nextLine();
                data += line;
            }

            //System.out.println(data);

            String[] parts = data.split("#type");

            for(int i = 0; i < parts.length; i++){
                String shaderText = parts[i];

                if(shaderText.indexOf("vertex") != -1){
                    vertexShaderSrc = shaderText.split("vertex")[1];
                }

                if(shaderText.indexOf("fragment") != -1){
                    fragmentShaderSrc = shaderText.split("fragment")[1];
                }

            }



        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void compile(){
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(vertexID, vertexShaderSrc);
        glShaderSource(fragmentID, fragmentShaderSrc);
        glCompileShader(vertexID);
        glCompileShader(fragmentID);

        //Check for errors

        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);

        if(success == GL_FALSE){
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("Shader Compile Error");
            System.out.println(glGetShaderInfoLog(vertexID, len));
        }

        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);

        if(success == GL_FALSE){
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("Shader Compile Error");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
        }

        //Link shaders and check for errors

        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);


        //Check for link errors

        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if(success == GL_FALSE){
            int len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println("Shader Link Error");
            System.out.println(glGetProgramInfoLog(shaderProgram, len));
        }
    }

    public void use(){
        glUseProgram(shaderProgram);
    }

    public void detach(){
        glUseProgram(0);
    }

    public void uploadMat4f(String varName, Matrix4f mat){
        int varLocation = glGetUniformLocation(shaderProgram, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat.get(matBuffer);

        glUniformMatrix4fv(varLocation, false, matBuffer);
    }

    public void uploadMat3f(String varName, Matrix3f mat){
        int varLocation = glGetUniformLocation(shaderProgram, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9);
        mat.get(matBuffer);

        glUniformMatrix3fv(varLocation, false, matBuffer);
    }

    public void uploadVec4f(String varName, Vector4f vec4){
        int varLocation = glGetUniformLocation(shaderProgram, varName);
        use();
        glUniform4f(varLocation,vec4.x,vec4.y,vec4.z,vec4.w);
    }

    public void uploadFloat(String varName, float f){
        int varLocation =  glGetUniformLocation(shaderProgram, varName);
        use();
        glUniform1f(varLocation,f);
    }

    public void uploadInt(String varName, int i){
        int varLocation =  glGetUniformLocation(shaderProgram, varName);
        use();
        glUniform1i(varLocation,i);
    }

    public void uploadVec3f(String varName, Vector3f vec4){
        int varLocation = glGetUniformLocation(shaderProgram, varName);
        use();
        glUniform3f(varLocation,vec4.x,vec4.y,vec4.z);
    }

    public void uploadVec2f(String varName, Vector2f vec4){
        int varLocation = glGetUniformLocation(shaderProgram, varName);
        use();
        glUniform2f(varLocation,vec4.x,vec4.y);
    }

    public void uploadTexture(String varName, int slot){
        int varLocation =  glGetUniformLocation(shaderProgram, varName);
        use();
        glUniform1i(varLocation,slot);
    }

    public void uploadIntArray(String varName, int[] array){
        int varLocation =  glGetUniformLocation(shaderProgram, varName);
        use();
        glUniform1iv(varLocation,array);
    }
}
