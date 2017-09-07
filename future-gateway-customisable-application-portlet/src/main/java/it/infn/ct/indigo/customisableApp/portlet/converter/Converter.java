package it.infn.ct.indigo.customisableApp.portlet.converter;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Converter {
    private final String EMPTYJSONOBJ = "{}";
    private final String INPUTS = "inputs";
    private final String TOPOLOGY = "topology_template";
    JsonParser parser = new JsonParser();
    Gson gson = new GsonBuilder().serializeNulls().create();

    public JsonObject readYamlToJsonArray(final String yamlFile) {        
        JsonObject finalObject = new JsonObject();
        finalObject.add("parameters", parser.parse("{}").getAsJsonObject());
        String json = convertYamlToJson(yamlFile);
        JsonArray array = new JsonArray();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();

        if(jsonObject.has(TOPOLOGY)) {
            jsonObject = (JsonObject) jsonObject.get(TOPOLOGY);
        }
        else { return finalObject; } 

        if(jsonObject.has(INPUTS)) {
            jsonObject = (JsonObject) jsonObject.get(INPUTS);
        }
        else { return finalObject; }

        Set<Entry<String, JsonElement>> entrySet = jsonObject.entrySet();        
        for(Map.Entry<String,JsonElement> entry : entrySet){
            JsonElement el = entry.getValue();
            adaptToStandard(el,"default","value");
            adaptToStandard(el,"description","display");

            el.getAsJsonObject().addProperty("name", entry.getKey());
            array.add(el);
        }

        finalObject.add("parameters", array);

        return finalObject;
    }

    private void adaptToStandard(JsonElement el, String oldTag, String newTag) {
        JsonElement obj = el.getAsJsonObject().get(oldTag);
        if(obj == null) {
            obj = gson.toJsonTree("");
        }
        el.getAsJsonObject().add(newTag, obj);
        el.getAsJsonObject().remove(oldTag);
    }

    private String convertYamlToJson(final String yamlContent) {
        if((yamlContent == null) || (yamlContent.isEmpty())) {
            return EMPTYJSONOBJ;
        }
        try {
            ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
            Object obj = yamlReader.readValue(yamlContent, Object.class);            
            ObjectMapper jsonWriter = new ObjectMapper();
            return jsonWriter.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return EMPTYJSONOBJ;
    }
}
