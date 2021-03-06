package cs.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileService {

  private static Logger logger = LoggerFactory.getLogger(FileService.class);

  public String getStringFrom(String path) throws URISyntaxException, IOException {
    String format = String.format("Path: %s", path);
    logger.debug(format);
    String collectStr;
    try (Stream<String> lines = Files.lines(Paths.get(getUri(path)))) {
      collectStr = lines.parallel().map(String::trim).collect(Collectors.joining());
    }
    return collectStr;
  }

  private URI getUri(String path) throws URISyntaxException, IOException {
    URL resource = getClass().getClassLoader().getResource(path);

    URI uri = resource.toURI();
    if("jar".equals(uri.getScheme())){
      logger.debug("Uri schema {}", uri.getScheme());
      for (FileSystemProvider provider: FileSystemProvider.installedProviders()) {
        if (provider.getScheme().equalsIgnoreCase("jar")) {
          try {
            logger.debug("Current uri: {}", uri);
            provider.getFileSystem(uri);
          } catch (FileSystemNotFoundException e) {
            // in this case we need to initialize it first:
            logger.debug(e.toString());
            provider.newFileSystem(uri, Collections.emptyMap());
            getUri(path);
          }
        }
      }
    }
    return uri;
  }
}
