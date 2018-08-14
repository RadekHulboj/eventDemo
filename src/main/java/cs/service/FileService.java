package cs.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileService {

  private static Logger logger = LoggerFactory.getLogger(FileService.class);

  public File getFileFrom(String path) throws URISyntaxException, IOException {
    return new File(getUri(path));
  }

  public String getStringFrom(String path) throws URISyntaxException, IOException {
    String format = String.format("Path: %s", path);
    logger.info(format);
    String collectStr;
    try (Stream<String> lines = Files.lines(Paths.get(getUri(path)))) {
      collectStr = lines.parallel().map(String::trim).collect(Collectors.joining());
    }
    return collectStr;
  }

  private URI getUri(String path) throws URISyntaxException, IOException {
    URL resource = getClass().getClassLoader().getResource(path);
    return resource.toURI();
  }
}
