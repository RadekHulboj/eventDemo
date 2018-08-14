package cs.business;

import cs.domain.Event;
import cs.service.JsonParser;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OperationalEvent {

  private JsonParser jsonParser = new JsonParser();
  private int thresholdInMs = 4;

  public List<Event> buildEvents() throws IOException, URISyntaxException {

    ArrayList<Event> mEvents = new ArrayList<>();
    Event[] fromJson = jsonParser.createFromJson("logs/log.json");
    Map<String, List<Event>> collect = Arrays.asList(fromJson).stream().collect(Collectors.groupingBy(Event::getId));
    collect.forEach((id, events) -> {
      int eventPairSizeAlwaysHaveToBe = 2;
      if (events.size() == eventPairSizeAlwaysHaveToBe) {
        Event event1 = events.get(0);
        Event event2 = events.get(1);
        Event mergeEvent = new Event();
        mergeEvent.setId(id);
        mergeEvent.setDuration(Math.abs(event1.getTimestamp().getTime() - event2.getTimestamp().getTime()));
        mergeEvent.setAlert(mergeEvent.getDuration() > thresholdInMs);
        mergeEvent.setHost(event1.getHost());
        mergeEvent.setState(event1.getState());
        mergeEvent.setType(event1.getType());
        mEvents.add(mergeEvent);
      }
    });
    return mEvents;
  }
}
