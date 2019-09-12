package cloud.fogbow.auditingserver.core.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class IpGroup {

    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @ManyToMany
    private List<Ip> ips;

    public IpGroup(List<Ip> ips) {
        this.ips = ips;
    }

    public IpGroup() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpGroup ipGroup = (IpGroup) o;
        return Objects.equals(id, ipGroup.id) &&
                Objects.equals(ips, ipGroup.ips);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ips);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Ip> getIps() {
        return ips;
    }

    public void setIps(List<Ip> ips) {
        this.ips = ips;
    }
}
