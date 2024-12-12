package june.IO;

import june.core.EngineServices;

import java.util.HashMap;

public class ResourceManager {


    HashMap<String, HashMap<String,Object>> dataPool;

    private String resourceDirectory;

    public ResourceManager(){
        dataPool = new HashMap<>();
        EngineServices.log().syslog("Resource Manager Created");
    }

    public void registerType(Class type){
        if(dataPool.get(type.getName()) == null){
            dataPool.put(type.getName(),new HashMap<String, Object>());
            EngineServices.log().syslog("Resource Manager: Registering Type: " + type.getName());
        }else{
            EngineServices.log().syswarn("Resource Manager: Attempting to register an existing type");
        }
    }

    public void registerResourceDirectory(String directory){
        EngineServices.log().syslog("Resource Manager: Registering Resource Directory: " + directory);
        this.resourceDirectory = directory;
    }

    public void loadResource(Class type,String name,Object O){
        if(dataPool.get(type.getName()) == null){
            //registerType(type);
            EngineServices.log().syserror("Resource Manager: Unregistered Resource Type: " + type.getName());
        }

        EngineServices.log().syslog("Resource Manager: Loading Resource to memory -> " + name + " of type: " + type.getName());

        dataPool.get(type.getName()).put(name,O);
    }


    public <T> T getResource(Class type, String name){

        if(dataPool.get(type.getName()) == null){
            EngineServices.log().syserror("Resource Manager: Attempting to load an unknown resource type: " + type.getName());
            return null;
        }else if(!dataPool.get(type.getName()).containsKey(name)){
            EngineServices.log().syserror("Resource Manager: Attempting to find an unknown resource name: " + name);
            return null;
        }else {
            Object obj = dataPool.get(type.getName()).get(name);
            return (T)obj;
        }


    }

    public String getResourceDirectory(){
        return resourceDirectory;
    }

}
