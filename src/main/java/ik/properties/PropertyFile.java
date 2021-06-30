package ik.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertyFile {
    /**
     * @param filePath - file path relative to resource directory of the project.
     * @return object with properties read from file
     * @throws IOException when incorrect file path provided
     */
    public Properties readFromPath(String filePath) throws IOException {
        String propFilePath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(filePath)).getPath();
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(propFilePath)) {
            properties.load(inputStream);
        }
        return properties;
    }
}
