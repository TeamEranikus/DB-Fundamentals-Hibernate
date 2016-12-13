package escape.code.core.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONParserImpl implements JSONParser {

    private Gson gson;

    public JSONParserImpl() {
        this.setGson(new GsonBuilder().setPrettyPrinting().create());
    }

    private Gson getGson() {
        return this.gson;
    }

    private void setGson(Gson gson) {
        this.gson = gson;
    }

    @Override
    public <E> E[] read(Class<E[]> classes, String filePath) {
        String fileData;
        E[] entities = null;
        try {
            fileData = new String(Files.readAllBytes(Paths.get(filePath)));
            entities = this.getGson().fromJson(fileData, classes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entities;
    }

    @Override
    public <E> void write(E entity, String filePath) {
        String json = this.getGson().toJson(entity);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
