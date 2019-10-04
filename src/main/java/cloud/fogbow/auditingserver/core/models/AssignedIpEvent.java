package cloud.fogbow.auditingserver.core.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ip_event_table")
public class AssignedIpEvent {

    @Column
    private Long ipId;

    @Column
    private Long eventTimestamp;

    @Column
    @Id
    private String id;

    @Column
    private boolean upEvent;

    public AssignedIpEvent() {}

    public AssignedIpEvent(Long ipId, Long eventTimestamp, boolean upEvent) {
        this.ipId = ipId;
        this.eventTimestamp = eventTimestamp;
        this.upEvent = upEvent;
        this.id = ipId.toString() + eventTimestamp.toString();
    }

    public Long getIpId() {
        return ipId;
    }

    public void setIpId(Long ipId) {
        this.ipId = ipId;
    }

    public Long getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(Long eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    public boolean isUpEvent() {
        return upEvent;
    }

    public void setUpEvent(boolean upEvent) {
        this.upEvent = upEvent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignedIpEvent that = (AssignedIpEvent) o;
        return upEvent == that.upEvent &&
                ipId.equals(that.ipId) &&
                eventTimestamp.equals(that.eventTimestamp) &&
                id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipId, eventTimestamp, id, upEvent);
    }
}
