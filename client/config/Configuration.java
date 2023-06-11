package client.config;

import java.util.*;

import org.json.*;

import java.io.*;

public class Configuration {
    private File file;
    public Map<String, Object> options;

    public Configuration(final File file, final Map<String, Object> options) {
        this.file = file;
        this.options = options;
    }

    public Configuration(final File file) {
        this.file = file;
        this.options = new HashMap<String, Object>();
    }

    public Object get(final String key) {
        return this.options.get(key);
    }

    public void set(final String key, final Object value) {
        this.options.put(key, value);
    }

    public void save() throws IOException {
        final JSONObject jsonObject = new JSONObject(this.options);
        this.file.createNewFile();
        final FileWriter fileWriter = new FileWriter(this.file);
        fileWriter.write(jsonObject.toString());
        fileWriter.flush();
        fileWriter.close();
    }
}
