package client.config;

import org.apache.commons.io.*;
import org.json.*;

import java.io.*;

public class ConfigurationAPI {
    public static Configuration loadExistingConfiguration(final File file) throws IOException {
        final JSONObject jsonObject = new JSONObject(FileUtils.readFileToString(file, Charsets.UTF_8));
        return new Configuration(file, jsonObject.toMap());
    }

    public static Configuration newConfiguration(final File file) {
        return new Configuration(file);
    }
}
