package cs.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class FileService {
    public File getFileFrom(String path) throws URISyntaxException {
        return new File(getUri(path));
    }

    public String getStringFrom (String path) throws URISyntaxException, IOException {
        return Files.lines(Paths.get(getUri(path))).parallel().map(String::trim).collect(Collectors.joining());
    }

    private URI getUri(String path) throws URISyntaxException {
        return getClass().getClassLoader().getResource(path).toURI();
    }
}
