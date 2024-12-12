package june.IO;

import com.google.gson.*;
import june.entity.EntityComponent;

import java.awt.*;
import java.lang.reflect.Type;

public class ComponentDeserializer implements JsonSerializer<EntityComponent>, JsonDeserializer<EntityComponent> {
    @Override
    public EntityComponent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }

    @Override
    public JsonElement serialize(EntityComponent component, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        System.out.println("Working");

        result.add("type",new JsonPrimitive(component.getClass().getCanonicalName()));
        result.add("properties",jsonSerializationContext.serialize(component,component.getClass()));


        return result;
    }
}
