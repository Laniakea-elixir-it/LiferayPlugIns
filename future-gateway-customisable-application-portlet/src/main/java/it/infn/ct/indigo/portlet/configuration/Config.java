package it.infn.ct.indigo.portlet.configuration;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Configuration class.
 */
public class Config {
    /**
     * JSON File name.
     */
    private final String parametersJsonFile = "/parameters.json";
    /**
     * Template name.
     */
    private final String templateParametersJsonFile =
            "/template-parameters.json";
    /**
     * Property.
     */
    private final String property = "content";


    /**
     * Create param file.
     * @param path Path to store the file
     * @param json The content in json format
     */
    public final void createParamFile(final String path, final String json) {
        String filePath = path + parametersJsonFile;

        File file = new File(filePath);
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file);
            printWriter.print(json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

    /**
     * Read the file to a string.
     * @param path The path to the file
     * @return The file content
     */
    private String readFile(final String path) {
        String content = null;
        try {
            if (new File(path).isFile()) {
                content = new String(Files.readAllBytes(Paths.get(path)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * Read a file and check the json content.
     * @param path Path to the file
     * @return the file content
     */
    public final String readJsonFile(final String path) {
        if (path == null) {
            return null;
        }

        String jsonContent = readFile(path + templateParametersJsonFile);
        JsonObject obj = new JsonObject();
        if (jsonContent == null) {
            obj.add(property, null);
        } else {
            JsonParser parser = new JsonParser();
            try {
                obj.add(property, parser.parse(jsonContent).getAsJsonObject());
            } catch (com.google.gson.JsonSyntaxException e) {
                e.printStackTrace();
                obj.add(property, null);
            }
        }
        return obj.toString();
    }
}
