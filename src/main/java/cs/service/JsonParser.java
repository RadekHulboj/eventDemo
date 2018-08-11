package cs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs.domain.Event;


public class JsonParser {
    ObjectMapper objectMapper = new ObjectMapper();
    public Event[] createFromJson(String json) {
        return null;
    }
}
