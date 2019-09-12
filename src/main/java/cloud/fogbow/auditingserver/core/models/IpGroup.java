package cloud.fogbow.auditingserver.core.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class IpGroup {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection
    private List<Ip> ips;

    public IpGroup(List<Ip> ips) {
        this.ips = ips;
    }

    public IpGroup() {

    }

    public List<Ip> getIps() {
        return ips;
    }

    public void setIps(List<Ip> ips) {
        this.ips = ips;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpGroup ipGroup = (IpGroup) o;
        return id.equals(ipGroup.id) &&
                Objects.equals(ips, ipGroup.ips);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ips);
    }
}
