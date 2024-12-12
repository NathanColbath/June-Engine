package june.scripting;

public class JuneScript {

    private boolean compiled;
    private String src;
    private String fileName;

    private ScriptableComponent scriptInstance;


    public JuneScript(String src, String fileName){
        this.fileName = fileName;
        this.src = src;
    }


    public boolean isCompiled(){
        return compiled;
    }

    public void setCompiled(boolean compiled){
        this.compiled = compiled;
    }


    public ScriptableComponent getScriptInstance(){
        return scriptInstance;
    }

    public String getSrc(){
        return src;
    }

    public String getFileName(){
        return fileName;
    }
}
