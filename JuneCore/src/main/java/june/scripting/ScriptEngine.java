package june.scripting;

import june.core.EngineServices;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class ScriptEngine {

    File compiledDir;
    private boolean writeCompile = true;

    private ArrayList<JuneScript> scripts = new ArrayList<>();

    public ScriptEngine(){
        EngineServices.log().syslog("Script Engine Created");
    }

    public void setCompiledDir(String dir){
        EngineServices.log().syslog("ScriptEngine: Registering compiled Directory: " + dir);
        String assetDir = dir;
        compiledDir = new File(assetDir + "/.compiledScripts");

        if(!compiledDir.exists()){
            compiledDir.mkdirs();
        }

        if(!compiledDir.isHidden()){
            try {
                EngineServices.log().syslog("ScriptEngine: Setting Compile Dir To Hidden");
                Runtime.getRuntime().exec("attrib +H " + compiledDir.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setWriteCompile(boolean writeCompile){
        this.writeCompile = writeCompile;
    }


    public void compile(JuneScript jScript){



        if(!jScript.isCompiled()){
            EngineServices.log().syslog("ScriptEngine: Compiling Script: " + jScript.getFileName());
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            try{

                OutputStream output  = new OutputStream() {

                    private StringBuilder string = new StringBuilder();

                    @Override
                    public void write(int b) throws IOException {
                        string.append((char) b);
                    }

                    public String toString(){
                        return string.toString();
                    }
                };




                File sourceFile = new File(compiledDir + "/" + jScript.getFileName());
                //sourceFile.getParentFile().mkdirs();
                if(writeCompile) {
                    Files.write(sourceFile.toPath(), readFile(jScript.getSrc() +"/"+ jScript.getFileName()).getBytes(StandardCharsets.UTF_8));
                }
                int success = compiler.run(System.in,System.out,output,sourceFile.getPath());

                if(success == 0){
                    String fileName = jScript.getFileName().split("\\.")[0];
                    File javaFile = new File(compiledDir + "/" + fileName + ".java");
                    javaFile.delete();
                    jScript.setCompiled(true);
                }else {
                    EngineServices.log().syserror(output.toString());
                }



            }catch(Exception e){


            }


        }


    }

    public ScriptableComponent loadScript(JuneScript jScript){

        EngineServices.log().syslog("ScriptEngine: Loading Script: " + jScript.getFileName());
        String fileName = jScript.getFileName().split("\\.")[0];

        if(!scripts.contains(jScript)){
            scripts.add(jScript);
            EngineServices.log().syslog("ScriptEngine: Registering New Script: " + jScript.getFileName());
        }


        try {
            if(jScript.isCompiled()){
                URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {compiledDir.toURI().toURL()});
                Class<?> cls = Class.forName(fileName,true,classLoader);



                ScriptableComponent toReturn  = (ScriptableComponent)cls.getDeclaredConstructor().newInstance();
                toReturn.scriptName = jScript.getFileName();
                return toReturn;
            }




        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.err.println("Unable to find class");
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }


        return null;
    }

    public void clean(){

    }


    public String readFile(String filename){
        String data = "";

        try{
            File shaderFile = new File(filename);
            Scanner myReader = new Scanner(shaderFile);
            while(myReader.hasNextLine()){
                String line = myReader.nextLine();
                data += line + "\n";
            }

            System.out.println(data);
            return data;


        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<JuneScript> getScripts(){
        return scripts;
    }
}
