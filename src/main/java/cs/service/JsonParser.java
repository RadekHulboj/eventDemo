package cs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.net.URISyntaxException;

public class JsonParser {

  Logger logger = LoggerFactory.getLogger(JsonParser.class);

  FileService fileService = new FileService();
  ObjectMapper mapperService = new ObjectMapper();

  public Event[] createFromJson(String path) throws URISyntaxException, IOException {
    logger.debug("Parsed path" + path);
    String stringFrom = fileService.getStringFrom(path);
    return stringFrom != null ? mapperService.readValue(stringFrom, Event[].class) : new Event[]{};
  }
}
