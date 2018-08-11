package cs.domain;

import javax.persistence.Entity;
import java.sql.Date;
import java.util.Objects;
/**
 * Created by radoslaw on 11.08.18.
 */
@Entity
public class Event {
    String id;
    String state;
    Date timestamp;
    TypeEnum type;
    String host;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id &&
                Objects.equals(state, event.state) &&
                Objects.equals(timestamp, event.timestamp) &&
                type == event.type &&
                Objects.equals(host, event.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state, timestamp, type, host);
    }
}
