package cs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs.domain.Event;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class JsonParser {
    FileService fileService = new FileService();
    ObjectMapper mapperService = new ObjectMapper();

    public Event[] createFromJson(String path) throws URISyntaxException, IOException {
        File file = fileService.getFileFrom(path);
        return file != null ? mapperService.readValue(file, Event[].class) : new Event[]{};
    }
}
