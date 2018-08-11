package cs.service;

import java.io.File;
import java.net.URISyntaxException;

public class FileService {
    public File getFileFrom(String path) throws URISyntaxException {
        return new File(getClass().getClassLoader().getResource(path).toURI());
    }
}
